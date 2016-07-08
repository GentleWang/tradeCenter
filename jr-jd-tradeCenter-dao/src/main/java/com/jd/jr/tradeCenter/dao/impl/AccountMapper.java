package com.jd.jr.tradeCenter.dao.impl;

import com.jd.jr.tradeCenter.dao.BaseMapperInterface;
import com.jd.jr.tradeCenter.model.Account;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by wangshuo7 on 2016/6/30.
 */
@Component("accountDao")
public interface AccountMapper extends BaseMapperInterface {

    /*
    *根据变量查询数据库记录
    * */
    public List<Account> queryByParam(Account account);
}
