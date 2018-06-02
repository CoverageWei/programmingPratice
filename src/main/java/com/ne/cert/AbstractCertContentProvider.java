package com.ne.cert;

import javafx.scene.control.Pagination;

import java.util.List;

/**
 * Created by weiwei on 2018/3/27.
 */
public abstract class AbstractCertContentProvider<MODEL_VO> {

    protected abstract List<Object> getFormatRow(MODEL_VO modelVo);

}
