package com.ne.cert;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by weiwei on 2018/3/27.
 */
public class AbstractCertGenerator implements CertGenerator {

    Long certId;
    Scope scope;

    private CertMetaData metaData;

    private CertContentProvider contentProvider;


    public AbstractCertGenerator(Long certId, Scope scope, CertContentProvider contentProvider) {
        this.certId = certId;
        this.scope = scope;
        this.contentProvider = contentProvider;
    }

    public AbstractCertGenerator(CertMetaData metaData, CertContentProvider contentProvider) {
        this.metaData = metaData;
        this.contentProvider = contentProvider;
    }

    @Override
    public String generateCertModel(){
        CertMetaData certMetaData = getCertMetaData();
        // 校验逻辑

        String front_url = this.doGenerateCertModel(CertMetaData.PlaneType.FRONT);

        String backend_url = this.doGenerateCertModel(CertMetaData.PlaneType.BACKEND);

        return null;
    }

    private String doGenerateCertModel(Integer planeType){
        CertMetaData certMetaData = getCertMetaData();

        // 1、获取本页展示字段数据
        List<Object> contents = Lists.newArrayList();
        if(CertMetaData.PlaneType.FRONT.equals(planeType)) {
            contents = contentProvider.getFrontCertPlaneData();
        } else if(CertMetaData.PlaneType.BACKEND.equals(planeType)){
            contents = contentProvider.getBackendCertPlaneData();
        }

        // 2、渲染、生成图片、上传nos获取 url逻辑 ——>


        return null;
    }


    private CertMetaData getCertMetaData(){
        // 根据 certId + scope 获取证书元数据信息（样式）
        return null;
    }


}
