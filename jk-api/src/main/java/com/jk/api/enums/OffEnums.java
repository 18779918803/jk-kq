package com.jk.api.enums;

public enum OffEnums {
    THING_OFF("事假",1),
    YEAR_OFF("年假",2),
    MORNING_FREE("上午",1),
    NOON_FREE("下午",2),
    OFF("病假",3);


    private String typeName;
    private Integer typeId;

    OffEnums(String typeName, Integer typeId) {
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
