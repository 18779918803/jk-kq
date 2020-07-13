package com.jk.kq.enums;

public enum  ApprovalStatusEnum {

    NO_APPROVAL(0),//不审批
    IN_APPROVAL(1),//审批中
    AGREE(2),       //通过
    DISAGREE(3);    //不通过


    private Integer status;

    ApprovalStatusEnum(Integer stauts) {
        this.status = stauts;
    }

    public Integer getStatus() {
        return status;
    }

    public static ApprovalStatusEnum needApproval(boolean need) {
        return need ? IN_APPROVAL : AGREE;
    }

    public static ApprovalStatusEnum valueOf(Integer status) {
        for (ApprovalStatusEnum statusEnum : values()) {
            if (statusEnum.getStatus().equals(status)) {
                return statusEnum;
            }
        }
        return null;
    }
}
