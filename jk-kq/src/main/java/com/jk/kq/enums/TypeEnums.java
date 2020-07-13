package com.jk.kq.enums;

public enum TypeEnums {
    //加班类型
    OVERTIME(1),
    //请假
    LEAVE(2),
    //核销
    WRITEOFF(3),
    //出差
    TRAVEL(4),

    //督察督办立项
    DBAPPLY(5),
    //督察督办延期（撤销）
    DBDELAY(6),
    //督察督办结办反馈
    FEEDBACK(7);


    private Integer state;

    TypeEnums(Integer state) {
        this.state = state;
    }

    public Integer getStatus() {
        return state;
    }
}
