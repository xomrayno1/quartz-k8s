package com.quartz.utils;

import java.util.Date;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import com.quartz.dto.TimerInfo;

public class TimerUtils {
	
	private TimerUtils() {}
	 
	public static JobDetail buildJobDetail(final Class jobClass, final TimerInfo info) {
		final JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put(jobClass.getSimpleName(), info);
		return JobBuilder
				.newJob(jobClass)
				.withDescription(info.getJobDescription())
				.withIdentity(info.getJobName() != null ? info.getJobName() : jobClass.getSimpleName(), info.getJobGroup())
				.setJobData(jobDataMap)
				.build();
	}
	
	public static Trigger buildTrigger(final Class jobClass, final TimerInfo info) {
		if (info.isCron()) {
			return buildCronTrigger(jobClass, info);
		}
		return buildSimpleTrigger(jobClass, info);
	}
	
	private static Trigger buildSimpleTrigger(final Class jobClass, final TimerInfo info) {
		SimpleScheduleBuilder builder = SimpleScheduleBuilder
				.simpleSchedule()
				.withIntervalInMilliseconds(info.getRepeatIntervalMs())
				.repeatForever();
		
		if(info.isRunForever()) {
			builder = builder.repeatForever();
        } else {
            builder = builder.withRepeatCount(info.getTotalFireCount() - 1);
		}
		
		return TriggerBuilder
				.newTrigger()
				.withIdentity(info.getJobName() != null ? info.getJobName() :  jobClass.getSimpleName(), info.getJobGroup())
				.withSchedule(builder)
				.startAt(new Date(System.currentTimeMillis() + info.getInitialOffsetMs()))
				.build();
	}
	
	private static Trigger buildCronTrigger(final Class jobClass, final TimerInfo info) {
		return TriggerBuilder
				.newTrigger()
				.startNow()
				.withIdentity(info.getJobName() != null ? info.getJobName() :  jobClass.getSimpleName(), info.getJobGroup())
				.withSchedule(CronScheduleBuilder.cronSchedule(info.getCronExpression()))
				.build();
	}
	

}
