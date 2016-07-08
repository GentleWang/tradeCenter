package com.jd.jr.tradeCenter.manager.biz;

import com.jd.jr.tradeCenter.dao.BaseMapperInterface;
import com.jd.jr.tradeCenter.model.BaseModel;

import java.util.List;

/**
 * Created by wangshuo7 on 2016/7/4.
 */
public abstract class AbstractBaseBusiness implements BaseBusinessInterface {

    @Override
    public boolean insert(BaseModel baseModel) {
        boolean flag = false;
        return flag;
    }

    @Override
    public int update(BaseModel baseModel) {
        return 0;
    }

    @Override
    public List<BaseModel> query(BaseModel baseModel) {
        return null;
    }

    @Override
    public boolean delete(BaseModel baseModel) {
        return false;
    }
}
