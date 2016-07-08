package com.jd.jr.tradeCenter.manager.enums;

/**
 * Created by wangshuo7 on 2016/7/5.
 */
public enum  FinanceAssetsStatusEnum {

    INIT("0","初始态"),

    AUDITED("1","审核完毕"),

    PURCHASE_SUCCESS("3","申购成功"),

    TRANSFER_SUCCESS("4","转让成功");

    private String code;

    private String name;

    private FinanceAssetsStatusEnum(String code, String name) {
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
