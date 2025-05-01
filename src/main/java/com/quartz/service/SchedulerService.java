package com.quartz.service;

import java.util.Date;
import java.util.List;

import com.quartz.dto.ScheduleRequest;
import com.quartz.dto.TimerInfo;

public interface SchedulerService {

	Date schedule(final ScheduleRequest scheduleRequest);
	
	Date schedule(final Class clazz, final TimerInfo info);
	
	List<TimerInfo> getAllRunningTimers();
	
	TimerInfo getRunningTimer(String jobId);
	
	void updateTimer(final String timerId, final TimerInfo info);
	
	boolean deleteTimer(final String jobName, final String jobGroup);
	
	void pauseTimer(final String timerId);
	
	void resumeTimer(final String timerId);
	
	boolean isJobExists(String jobId);
	
	boolean isJobExists(String jobName, String jobGroup);
	
	
}
