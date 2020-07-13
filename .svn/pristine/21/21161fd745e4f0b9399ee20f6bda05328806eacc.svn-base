package com.jk.sw.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import static org.apache.commons.lang3.time.DateFormatUtils.format;

/**
 * 文件描述：
 *
 * @Author 温龙飞
 * @Date
 * @Version 1.0
 */
public class SwUtils {

    /**
     * 生成收文任务编号task_id
     */
    public static String generateTaskId() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        String timeStr = format(cal.getTime(), "yyyyMMddHHmmssSSS");
        return timeStr;
    }
}
