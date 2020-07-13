package com.jk.quartz.util;

import com.jk.quartz.entity.Quartz;
import org.quartz.Job;
import org.quartz.JobExecutionContext;


/**
 * @Description: 计划任务执行处 无状态
 * @author 缪隽峰
 * @date 2016年3月8日
 * @version 1.0
 */
public class QuartzFactory implements Job {

	@Override
	public void execute(JobExecutionContext context) {
		Quartz scheduleJob = (Quartz) context.getMergedJobDataMap().get("quartz");
		TaskUtils.invokMethod(scheduleJob,context);
	} 
}