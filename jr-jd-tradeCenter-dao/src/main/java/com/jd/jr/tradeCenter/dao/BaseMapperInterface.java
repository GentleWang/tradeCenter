package com.jd.jr.tradeCenter.dao;

import com.jd.jr.tradeCenter.model.BaseModel;
import com.jd.jr.tradeCenter.model.FinanceAssets;

import java.util.List;

/**
 * Created by wangshuo7 on 2016/7/4.
 */
public interface BaseMapperInterface<T extends BaseModel> {

    int deleteByPrimaryKey(String id);

    int insert(T record);

    T selectByPrimaryKey(String id);

    List<T> selectAll();

    int updateByPrimaryKey(T record);
}
