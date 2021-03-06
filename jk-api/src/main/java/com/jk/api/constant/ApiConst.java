package com.jk.api.constant;

/**
 * 常量类，将一些常量定义在这个类中，方便使用
 *
 * @author 缪隽峰
 * @version 1.0
 * @date 2020年03月27日
 */
public class ApiConst {

    /**
     * 第三方Session的属性名
     */
    public static final String THIRD_SESSION = "thirdSession";

    /**
     * 完成标识
     */
    public static final int APP_YES = 1;
    public static final int APP_NO = 0;
    public static final int REJECT_NO = 2;


    /**
     * 上 下
     */
    public static final int MORNING_YES = 1;
    public static final int NOON_LATE = 2;
    public static final int ALL_YES = 3;

    public static final String DEVICE_ID = "Q10193310233";

    //打卡类型1上班卡2下班卡
    public static final Integer TYPE_1 = 1;
    public static final Integer TYPE_2 = 2;
    //打卡状态0正常1迟到2旷工3早退4加班
    public static final Integer STATE_0 = 0;
    public static final Integer STATE_1 = 1;
    public static final Integer STATE_2 = 2;
    public static final Integer STATE_3 = 3;
    public static final Integer STATE_4 = 4;
    //考勤机打卡用1 其他冲销用2
    public static final Integer WAY_1 = 1;
    public static final Integer WAY_2 = 2;


    /**
     * 核销上下班
     */
    public static final Integer WRITEOFFUP = 1;
    public static final Integer WRITEOFFULATE= 2;


}
