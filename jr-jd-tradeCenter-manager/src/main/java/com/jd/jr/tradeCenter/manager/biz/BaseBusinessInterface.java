package com.jd.jr.tradeCenter.manager.biz;


import com.jd.jr.tradeCenter.model.BaseModel;

import java.util.List;

/**
 * Created by wangshuo7 on 2016/7/4.
 */
public interface BaseBusinessInterface {

    public boolean insert(BaseModel baseModel);
    public int update(BaseModel baseModel);
    public List<BaseModel> query(BaseModel baseModel);
    public boolean delete(BaseModel baseModel);

}
