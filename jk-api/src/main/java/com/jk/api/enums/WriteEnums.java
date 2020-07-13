package com.jk.api.enums;

public enum WriteEnums {

    MORNING_FREE("核销上班卡",1),
    NOON_FREE("核销下班卡",2);


    private String typeName;
    private Integer typeId;

    WriteEnums(String typeName, Integer typeId) {
        this.typeName = typeName;
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public Integer getTypeId() {
        return typeId;
    }
}
