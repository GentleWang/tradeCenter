package com.jd.jr.tradeCenter.manager.enums;

/**
 * Created by wangshuo7 on 2016/7/5.
 */
public enum AssetsTypeEnum {

    SMALL_LOAN("1","小额贷款收益"),

    ACCOUNTS_RECEIVABLE("2","应收账款"),

    RENTAL_PRODUCTS("3","租赁资产收益"),

    BILL_PRODUCT("4","票据产品收益"),

    OTHER_PRODUCT("0","其他产品");

    private String code;

    private String name;

    private AssetsTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
