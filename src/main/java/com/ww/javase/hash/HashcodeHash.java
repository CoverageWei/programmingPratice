package com.ww.javase.hash;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by weiwei on 2018/4/25.
 */
public class HashcodeHash {

    /**
     * 验证普通 hash 对于增减节点，原有会不会出现移动。
     *
     * Obj{key='1'}
     Obj{key='2'}
     Obj{key='3'}
     Obj{key='4'}
     Obj{key='5'}
     ========== after  =============
     null
     null
     null
     null
     null
     *
     *
     * @param args
     */
    public static void main(String[] args) {
        NodeArray nodeArray = new NodeArray();

        Node[] nodes = {
                new Node("Node--> 1"),
                new Node("Node--> 2"),
                new Node("Node--> 3")
        };

        for (Node node : nodes) {
            nodeArray.addNode(node);
        }

        Obj[] objs = {
                new Obj("1"),
                new Obj("2"),
                new Obj("3"),
                new Obj("4"),
                new Obj("5")
        };

        for (Obj obj : objs) {
            nodeArray.put(obj);
        }

        validate(nodeArray, objs);

    }

    private static void validate(NodeArray nodeArray, Obj[] objs) {
        for (Obj obj : objs) {
            System.out.println(nodeArray.get(obj));
        }

        nodeArray.addNode(new Node("anything1"));
        nodeArray.addNode(new Node("anything2"));

        System.out.println("========== after  =============");

        for (Obj obj : objs) {
            System.out.println(nodeArray.get(obj));
        }
    }


}


/**
 * 实际存储对象，很简单的一个类，只需要获取他的 hash 值就好
 */
class Obj {
    String key;

    Obj(String key) {
        this.key = key;
    }
    @Override
    public int hashCode() {
        return key.hashCode();
    }
    @Override
    public String toString() {
        return "Obj{" +
                "key='" + key + '\'' +
                '}';
    }
}


/**
 * 缓存节点对象，用于存储实际对象：
 */
class Node {

    Map<Integer, Obj> node = new HashMap<>();
    String name;

    Node(String name) {
        this.name = name;
    }

    public void putObj(Obj obj) {
        node.put(obj.hashCode(), obj);
    }

    Obj getObj(Obj obj) {
        return node.get(obj.hashCode());
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}


/**
 * 缓存节点集合，用于保存有效的缓存节点
 * 内部一个数组，取数据时，通过取余机器数量获取缓存节点，再从节点中取出数据。
 */
class NodeArray {

    Node[] nodes = new Node[1024];
    int size = 0;

    public void addNode(Node node) {
        nodes[size++] = node;
    }

    Obj get(Obj obj) {
        int index = obj.hashCode() % size;
        return nodes[index].getObj(obj);
    }

    void put(Obj obj) {
        int index = obj.hashCode() % size;
        nodes[index].putObj(obj);
    }
}

