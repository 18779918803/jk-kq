package com.jk.sw.constant;

/**
 * 文件描述：
 *
 * @Author 温龙飞
 * @Date
 * @Version 1.0
 */
public class SwConst {
    public enum IsOrNot{
        IS(1, "是"),NOT(2, "否");

        private final Integer isOrNotCode;
        private final String isOrNotdesc;

        private IsOrNot(final Integer code,final String desc){
            this.isOrNotCode = code;
            this.isOrNotdesc = desc;
        }

        public static String getDesc(Integer value) {
            IsOrNot[] isOrNotEnums = values();
            for (IsOrNot isOrNotEnum : isOrNotEnums) {
                if (isOrNotEnum.code().equals(value)) {
                    return isOrNotEnum.desc();
                }
            }
            return null;
        }

        public static Integer getValue(String value) {
            IsOrNot[] isOrNotEnums = values();
            for (IsOrNot isOrNotEnum : isOrNotEnums) {
                if (isOrNotEnum.desc().equals(value)) {
                    return isOrNotEnum.code();
                }
            }
            return null;
        }

        public Integer code(){
            return this.isOrNotCode;
        }

        public String desc(){
            return this.isOrNotdesc;
        }
    }

    /**收文任务状态*/
    public enum TaskStatus{
        COMPLETED(1,"完成"),
        UNDERWAY(2,"进行中"),
        REJECT(3,"驳回结束"),
        UNSTART(4,"未开始"),
        STOP(5,"停止");

        private final Integer TaskStatusCode;
        private final String TaskStatusDesc;

        private TaskStatus(final Integer code,final String desc){
            this.TaskStatusCode = code;
            this.TaskStatusDesc = desc;
        }

        public static String getDesc(Integer value) {
            TaskStatus[] taskStatusEnums = values();
            for (TaskStatus taskStatusEnum : taskStatusEnums) {
                if (taskStatusEnum.code().equals(value)) {
                    return taskStatusEnum.desc();
                }
            }
            return null;
        }

        public static Integer getValue(String value) {
            TaskStatus[] taskStatusEnums = values();
            for (TaskStatus taskStatusEnum : taskStatusEnums) {
                if (taskStatusEnum.desc().equals(value)) {
                    return taskStatusEnum.code();
                }
            }
            return null;
        }

        public Integer code(){
            return this.TaskStatusCode;
        }

        public String desc(){
            return this.TaskStatusDesc;
        }
    }

    /**收文任务状态*/
    public enum ApprovalStatus{
        NO_INSTRUCTIONS(1,"未批示"),
        PASS(2,"同意"),
        REJECT(3,"驳回");

        private final Integer approvalStatusCode;
        private final String approvalStatusDesc;

        private ApprovalStatus(final Integer code,final String desc){
            this.approvalStatusCode = code;
            this.approvalStatusDesc = desc;
        }

        public static String getDesc(Integer value) {
            ApprovalStatus[] approvalStatusEnums = values();
            for (ApprovalStatus approvalStatusEnum : approvalStatusEnums) {
                if (approvalStatusEnum.code().equals(value)) {
                    return approvalStatusEnum.desc();
                }
            }
            return null;
        }

        public static Integer getValue(String value) {
            ApprovalStatus[] approvalStatusEnums = values();
            for (ApprovalStatus approvalStatusEnum : approvalStatusEnums) {
                if (approvalStatusEnum.desc().equals(value)) {
                    return approvalStatusEnum.code();
                }
            }
            return null;
        }

        public Integer code(){
            return this.approvalStatusCode;
        }

        public String desc(){
            return this.approvalStatusDesc;
        }
    }

    /**收文任务状态*/
    public enum UsableScope{
        READONLY(1,"只读"),
        READ_WRITE(2,"读写"),
        NOT_READ_WRITE(3,"不可读写");

        private final Integer usableScopeCode;
        private final String usableScopeDesc;

        private UsableScope(final Integer code,final String desc){
            this.usableScopeCode = code;
            this.usableScopeDesc = desc;
        }

        public static String getDesc(Integer value) {
            UsableScope[] usableScopeEnums = values();
            for (UsableScope usableScopeEnum : usableScopeEnums) {
                if (usableScopeEnum.code().equals(value)) {
                    return usableScopeEnum.desc();
                }
            }
            return null;
        }

        public static Integer getValue(String value) {
            UsableScope[] usableScopeEnums = values();
            for (UsableScope usableScopeEnum : usableScopeEnums) {
                if (usableScopeEnum.desc().equals(value)) {
                    return usableScopeEnum.code();
                }
            }
            return null;
        }

        public Integer code(){
            return this.usableScopeCode;
        }

        public String desc(){
            return this.usableScopeDesc;
        }
    }

    /**传阅类型*/
    public enum UsableType{
        CIRCULATION(2,"流转"),
        READ_SET(1,"传阅"),
        ALLOT(3,"分发");

        private final Integer usableTypeCode;
        private final String usableTypeDesc;

        private UsableType(final Integer code,final String desc){
            this.usableTypeCode = code;
            this.usableTypeDesc = desc;
        }

        public static String getDesc(Integer value) {
            UsableType[] usableScopeEnums = values();
            for (UsableType usableScopeEnum : usableScopeEnums) {
                if (usableScopeEnum.code().equals(value)) {
                    return usableScopeEnum.desc();
                }
            }
            return null;
        }

        public static Integer getValue(String value) {
            UsableType[] usableScopeEnums = values();
            for (UsableType usableScopeEnum : usableScopeEnums) {
                if (usableScopeEnum.desc().equals(value)) {
                    return usableScopeEnum.code();
                }
            }
            return null;
        }

        public Integer code(){
            return this.usableTypeCode;
        }

        public String desc(){
            return this.usableTypeDesc;
        }
    }
}
