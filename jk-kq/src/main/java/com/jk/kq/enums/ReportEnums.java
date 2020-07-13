package com.jk.kq.enums;

public enum ReportEnums {

    //加班类型
    OVERTIME("blue"),
    //早退
    EARLY_QUIT("red"),
    //
    LATE("red"),
    //矿工
    QUIT("red"),
    //正常
    NORMAL("yellow");


    private String state;

    ReportEnums(String state) {
        this.state = state;
    }

    public String getStatus() {
        return state;
    }

}
