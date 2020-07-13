package com.jk.sys.enums;

public enum CheckEnums {
    NO_CHECK("审批未提交"),//不审批
    IN_CHECK("审批提交"),//审批中
    AGREE("审批通过"),       //通过
    DISAGREE("审批不通过");    //不通过


    private String auth;

    CheckEnums(String stauts) {
        this.auth = stauts;
    }

    public String getStatus() {
        return auth;
    }

}
