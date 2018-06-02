package com.ne.cert;

/**
 * Created by weiwei on 2018/3/27.
 */
public class CertMetaData {
    /**
     * 证书id
     */
    private Long certId;

    /**
     * 正面样式
     */
    private String frontCertStyle;
    /**
     * 背面样式
     */
    private String backendCertStyle;

    /**
     *
     * 其他证书必须字段
     *
     */



    protected static class PlaneType {
        protected static final Integer FRONT = 1;
        protected static final Integer BACKEND = 2;
    }
}
