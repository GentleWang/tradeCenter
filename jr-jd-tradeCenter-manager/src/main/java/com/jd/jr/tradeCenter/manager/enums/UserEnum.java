package com.jd.jr.tradeCenter.manager.enums;

/**
 * Created by wangshuo7 on 2016/7/5.
 */
public enum UserEnum {

    inputer1("inputer1","登记员1","登记组","ipnuterGroup"),

    inputer2("inputer2","登记员2","登记组","ipnuterGroup"),

    operator1("operator1","运营专员1","运营组","Operaters"),

    operator2("operator2","运营专员2","运营组","Operaters"),

    risker1("risker1","风控审核员1","风控组","riskerGroup"),

    risker2("risker2","风控审核员2","风控组","riskerGroup");

    private String userID;

    private String userName;

    private String roles;

    private String rolesGroup;

    UserEnum(String userID, String userName,String roles,String rolesGroup) {
        this.userID = userID;
        this.userName = userName;
        this.roles = roles;
        this.rolesGroup = rolesGroup;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getRolesGroup() {
        return rolesGroup;
    }

    public void setRolesGroup(String rolesGroup) {
        this.rolesGroup = rolesGroup;
    }
}
