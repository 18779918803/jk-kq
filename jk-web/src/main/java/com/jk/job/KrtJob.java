package com.jk.job;

import com.jk.common.annotation.KrtLog;
import com.jk.common.constant.GlobalConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 测试定时任务
 *
 * @author 缪隽峰
 * @version 1.0
 * @date 2018年07月10日
 */
@Slf4j
@Component
public class KrtJob {

    /**
     * 测试定时任务
     */
    @KrtLog(value = "测试定时任务", type = GlobalConstant.LogType.QUARTZ)
    public void job() {
        log.debug("================定时任务在执行============");
    }
}


