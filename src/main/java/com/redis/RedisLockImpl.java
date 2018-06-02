package com.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

import java.util.Collections;

/**
 *
 * 参考：https://www.jianshu.com/p/515d454266d2
 *
 * Created by weiwei on 2018/4/3.
 */
public class RedisLockImpl {

    public static long hold_time = 3000;
    public static ThreadLocal<String> expireHolder = new ThreadLocal<String>();
    public static Jedis jedis;
    public static String lock;


    //---------------------------------------------------------- 普通实现版 ----------------------------


    public boolean tryLock() {
        long currentTime = System.currentTimeMillis();
        String expires = String.valueOf(hold_time + currentTime);
        //设置互斥量
        if (jedis.setnx(lock, expires) > 0) {       // 获取锁成功
            //获取锁，设置超时时间
//            setLockStatus(expires);
            return true;
        } else {                                    // 没有获取到锁
            String currentLockTime = jedis.get(lock);
            //检查锁是否超时
            if (currentLockTime != null && Long.parseLong(currentLockTime) < currentTime) {
                //获取旧的锁时间并设置互斥量
                String oldLockTime = jedis.getSet(lock, expires);
                //旧值与当前时间比较
                //判断currentExpireTime与oldExpireTime 是否相等，如果相等，说明当前getset设置成功，获取到了锁。
                // 如果不相等，说明这个锁又被别的请求获取走了，那么当前请求可以直接返回失败，或者继续重试
                if (oldLockTime!= null && oldLockTime.equals(currentLockTime)) {
                    //获取锁，设置超时时间
//                    setLockStatus(expires);
                    return true;
                }
            }

            return false;
        }
    }




    /**
     * 可能问题：
     * 1、由于是客户端自己生成过期时间，所以需要强制要求分布式下每个客户端的时间必须同步。
     * 2、当锁过期的时候，如果多个客户端同时执行jedis.getSet()方法，那么虽然最终只有一个客户端可以加锁，但是这个客户端的锁的过期时间可能被其他客户端覆盖。
     * 3、锁不具备拥有者标识，即任何客户端都可以解锁。
     * @param lock
     */
    public static void acquire(String lock){
        //1.先尝试用setnx命令获取锁,key为参数lock,值为当前时间+要持有锁的时间hold_time
        while(jedis.setnx(lock, String.valueOf(System.currentTimeMillis() + hold_time)) == 0){
            //2.如果获取失败,先watch lock key
            jedis.watch(lock);
            //3.获取当前超时时间
            String expireTime = jedis.get(lock);
            if(expireTime != null && Long.parseLong(expireTime) < System.currentTimeMillis()){
                //4.如果超时时间小于当前时间,开事务准备更新lock值
                Transaction transaction = jedis.multi();
                Response<String> response = transaction.getSet(lock, String.valueOf(System.currentTimeMillis() + hold_time));
                //5.步骤2设置了watch,如果lock的值被其他线程修改,不是执行事务中的命令
                if(transaction.exec() != null){
                    String oldExpire = response.get();
                    if(oldExpire != null && Long.parseLong(expireTime) < System.currentTimeMillis()){
                        //6.如果setget命令返回的值依然是过期时间,认为获取锁成功(加了watch之后,这里返回的应该一直是超时时间)
                        break;
                    }
                }
            }else{
                //如果key未超时,解除watch
                jedis.unwatch();
            }
        }
        //设置客户端超时时间
        expireHolder.set(jedis.get(lock));
    }


    /**
     * 释放分布式锁【普通版】
     * 问题：并发的情况下两行代码之间有很高几率出现其它线程乱入的问题；
     * 如果调用jedis.del()方法的时候，这把锁已经不属于当前客户端的时候会解除他人加的锁。
     *
     * @param lock
     */
    public static void release(String lock){
        //比较客户端超时时间与lock值,判断是否还由自己持有锁
        if(jedis.get(lock).equals(expireHolder.get())){
            jedis.del(lock);
        }
        jedis.close();
    }


    //---------------------------------------------------------- 最终实现版 ----------------------------

    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";

    /**
     * 尝试获取分布式锁【最终版】
     *
     * 优化：
     * 1、通过requestId解决了分布式下不同客户端时间不统一问题；
     * 2、通过超期时间解决了多次getset覆盖问题；
     * 3、通过解锁时判断requestId解决了任何客户端都可以解锁问题。
     *
     *
     * @param jedis Redis客户端
     * @param lockKey 锁
     * @param requestId 客户端标识
     * @param expireTime 超期时间
     * @return 是否获取成功
     */
    public static boolean tryGetDistributedLock(Jedis jedis, String lockKey, String requestId, int expireTime) {

        String result = jedis.set(lockKey, requestId, "NX", "PX", expireTime);

        if (LOCK_SUCCESS.equals(result)) {
            return true;
        }
        return false;
    }

    /**
     * Set the string value as value of the key. The string can't be longer than 1073741824 bytes (1GB).
     * @param key
     * @param value
     * @param nxxx NX|XX,  NX -- Only set the key if it does not already exist. XX -- Only set the key
     *          if it already exist.
     * @param expx EX|PX, expire time units: EX = seconds; PX = milliseconds
     * @param time expire time in the units of {@param #expx}
     * @return Status code reply
     */
//        public String set(final String key, final String value, final String nxxx, final String expx, final long time) {...}



    /**
     * 常规解锁方式【存在线程乱入问题】
     *
     * @param jedis
     * @param lockKey
     * @param requestId
     */
    public static void wrongReleaseLock2(Jedis jedis, String lockKey, String requestId) {
        // 判断加锁与解锁是不是同一个客户端
        if (requestId.equals(jedis.get(lockKey))) {
            // 若在此时，这把锁突然不是这个客户端的，则会误解锁,解除他人加的锁
            jedis.del(lockKey);
        }
    }

    /**
     * 释放分布式锁【最终版】
     *
     * 解决问题：并发的情况下两行代码之间有很高几率出现其它线程乱入的问题；
     * 如果调用jedis.del()方法的时候，这把锁已经不属于当前客户端的时候会解除他人加的锁。
     * 比如客户端A加锁，一段时间之后客户端A解锁，在执行jedis.del()之前，锁突然过期了，此时客户端B尝试加锁成功，然后客户端A再执行del()方法，则将客户端B的锁给解除了。
     *
     * 实现思路：
     * 因为redis是单进程操作，再多的command都是one by one执行的；所以要避免线程乱入，就要保证整个操作的原子性！！！
     * 首先获取锁对应的value值，检查是否与requestId相等，如果相等则删除锁（解锁）。那么为什么要使用Lua语言来实现呢？因为要确保上述操作是原子性的。
     *
     * 实现：
     * 第一行代码，我们写了一个简单的Lua脚本代码,第二行代码，我们将Lua代码传到jedis.eval()方法里，
     * 并使参数KEYS[1]赋值为lockKey，ARGV[1]赋值为requestId。
     * eval()方法是将Lua代码交给Redis服务端执行。
     *
     * 总结：在eval命令执行Lua代码的时候，Lua代码将被当成一个命令去执行，并且直到eval命令执行完成，Redis才会执行其他命令。
     *
     * @param jedis
     * @param lockKey
     * @param requestId 请求标识
     * @return 是否释放成功
     */
    public static boolean releaseDistributedLock(Jedis jedis, String lockKey, String requestId) {
        final Long RELEASE_SUCCESS = 1L;

        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));

        if (RELEASE_SUCCESS.equals(result)) {
            return true;
        }
        return false;
    }

}
