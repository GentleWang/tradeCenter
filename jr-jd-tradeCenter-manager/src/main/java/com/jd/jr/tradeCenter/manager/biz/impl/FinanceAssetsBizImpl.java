package com.jd.jr.tradeCenter.manager.biz.impl;

import com.jd.jr.tradeCenter.dao.impl.FinanceAssetsMapper;
import com.jd.jr.tradeCenter.manager.biz.AbstractBaseBusiness;
import com.jd.jr.tradeCenter.model.FinanceAssets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by wangshuo7 on 2016/7/4.
 */
@Component("financeAssetsBiz")
public class FinanceAssetsBizImpl extends AbstractBaseBusiness {

    @Autowired
    private FinanceAssetsMapper financeAssetsMapper;

    public void assetInput(FinanceAssets financeAssets){

        financeAssetsMapper.insert(financeAssets);
    }
}
