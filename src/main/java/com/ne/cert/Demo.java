package com.ne.cert;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by weiwei on 2018/3/27.
 */
public class Demo {
    public static void main(String[] args) {
        Long certId = null;
        Scope scope = null;

        /**
         * 1、待补充：这种CertContentProvider 支持数据来源是 list 方式；
         *      可以扩展 其他种类的 provider，如 map结构的数据方式 等，工具数据解析 方式不同而已
         * 2、分页用户生成证书支持
         */
        AbstractCertGenerator certGenerator = new AbstractCertGenerator(certId, scope, new CertContentProvider() {

            /**
             *                      (合格)证书
             * （张三）同学，在微专业（期次一）学期完毕，获得成绩（80）分，允许毕业。
             *                              (网易云课堂)
             *
             *
             * @return 每个list 代表一个用户在一个scope 下的证书
             */
            @Override
            public List<Object> getFrontCertPlaneData() {
                List<Object> frontContents = Lists.newArrayList();
                /* 使用业务接口获取 本张证书 数据，每张证书*/

                // 判断合格逻辑
                // ...
                frontContents.add("合格");

                frontContents.add("张三");
                frontContents.add("期次一");
                frontContents.add("80");
                frontContents.add("网易云课堂");
                return frontContents;
            }

            @Override
            public List<Object> getBackendCertPlaneData() {
                return null;
            }
        });
        certGenerator.generateCertModel();
    }
}
