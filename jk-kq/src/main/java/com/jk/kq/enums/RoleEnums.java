package com.jk.kq.enums;

public enum RoleEnums {
    PU_TONG("普通职员"),//不审批
    DEPT_LEADER("公司/部门负责人"),//审批中
    DEPT_AGENT("公司/部门经办人"),//审批中
    DIS_GUIDE("分管领导"),       //通过
    MANAGER("集团总经理"),       //通过
    CHAIR("董事长");    //不通过


    private String auth;

    RoleEnums(String stauts) {
        this.auth = stauts;
    }

    public String getStatus() {
        return auth;
    }

}
