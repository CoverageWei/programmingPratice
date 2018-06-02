package com.ne.cert;

import java.util.List;

/**
 * Created by weiwei on 2018/3/27.
 */
public interface CertContentProvider {

    /**
     * 业务扩展口，业务自定义数据来源
     *
     * @return 正面数据字段
     */
    List<Object> getFrontCertPlaneData();

    /**
     * @return 反面数据字段Ø
     */
    List<Object> getBackendCertPlaneData();

}
