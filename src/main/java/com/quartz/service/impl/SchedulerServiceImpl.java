package com.quartz.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.stereotype.Service;

import com.quartz.dto.ScheduleRequest;
import com.quartz.dto.TimerInfo;
import com.quartz.repository.CustomerRepository;
import com.quartz.service.SchedulerService;
import com.quartz.service.job.CustomerJob;
import com.quartz.service.job.trigger.SimpleTriggerListener;
import com.quartz.utils.TimerUtils;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class SchedulerServiceImpl implements SchedulerService{
	
	private final Scheduler scheduler;
	
	//private final CustomerRepository customerRepository;
	
	@Override
	public Date schedule(ScheduleRequest scheduleRequest) {
		Class clazz = CustomerJob.class;
		TimerInfo info = TimerInfo.builder()
				.jobName(scheduleRequest.getJobName())
				.jobGroup(scheduleRequest.getJobGroup())
				.jobDescription(scheduleRequest.getJobDescription())
				.cronExpression(scheduleRequest.getCronExpression())
				.totalFireCount(scheduleRequest.getTotalFireCount())
				.runForever(scheduleRequest.isRunForever())
				.repeatIntervalMs(scheduleRequest.getRepeatIntervalMs())
				.build();
		
		
		if(isJobExists(info.getJobName(), info.getJobGroup())) {
			deleteTimer(info.getJobName(), info.getJobGroup());
		}
		
		
		final JobDetail jobDetail = TimerUtils.buildJobDetail(clazz, info);
		final Trigger trigger = TimerUtils.buildTrigger(clazz, info);
		try {
			//scheduler.getContext().put("customerRepository", customerRepository);
			return scheduler.scheduleJob(jobDetail, trigger);
		} catch (SchedulerException e) {
			log.error("Error scheduling job", e);
		}
		return null;
	}
	
	@Override
	public Date schedule(final Class clazz, final TimerInfo info) {
		final JobDetail jobDetail = TimerUtils.buildJobDetail(clazz, info);
		final Trigger trigger = TimerUtils.buildTrigger(clazz, info);
		try {
			return scheduler.scheduleJob(jobDetail, trigger);
		} catch (SchedulerException e) {
			log.error("Error scheduling job", e);
		}
		return null;
	}
	
	/**
	 * 
	 * **/
	@Override
	public List<TimerInfo> getAllRunningTimers(){
		log.info("Getting all running timers");
		try {
			return scheduler.getJobKeys(GroupMatcher.anyGroup())
					.stream()
					.map(jobKey -> {
						try {
							JobDetail jobDetail = scheduler.getJobDetail(jobKey);
							return (TimerInfo) jobDetail.getJobDataMap().get(jobKey.getName());
						} catch (SchedulerException e) {
							log.error("Error getting job detail", e);
							return null;
						}
					})
					.filter(Objects::nonNull)
					.collect(Collectors.toList());
		} catch (SchedulerException e) {
			log.error("Error getting running timers", e);
			return Collections.emptyList();
		}
	}
	
	@Override
	public TimerInfo getRunningTimer(String jobId) {
		log.info("Getting timer with ID: {}", jobId);
		try {
			JobDetail jobDetail = scheduler.getJobDetail(new JobKey(jobId));
			if (jobDetail == null) {
				return null;
			}
			return (TimerInfo) jobDetail.getJobDataMap().get(jobId);
		} catch (SchedulerException e) {
			log.error("Error getting timer info", e);
			return null;
		}
	}
	
	@Override
	public void updateTimer(final String timerId, final TimerInfo info) {
		log.info("Updating timer with ID: {}", timerId);
		try {
			JobDetail jobDetail = scheduler.getJobDetail(new JobKey(timerId));
			if (jobDetail == null) {
				log.error("Job detail not found for timerId: {}", timerId);
				return;
			}
			
			jobDetail.getJobDataMap().put(timerId, info);
		} catch (SchedulerException e) {
			log.error("Error updating timer", e);
		}
	}
	
	@Override
	public boolean deleteTimer(final String jobName, final String jobGroup) {
		log.info("Deleting timer with jobName: {}, jobGroup: {} ", jobName, jobGroup);
		try {
			return scheduler.deleteJob(new JobKey(jobName, jobGroup));
		} catch (SchedulerException e) {
			log.error("Error deleting timer", e);
		}
		return false;
	}
	
	@Override
	public void pauseTimer(final String timerId) {
		log.info("Pausing timer with ID: {}", timerId);
		try {
			scheduler.pauseJob(new JobKey(timerId));
		} catch (SchedulerException e) {
			log.error("Error pausing timer", e);
		}
	}
	
	@Override
	public void resumeTimer(final String timerId) {
		log.info("Resuming timer with ID: {}", timerId);
		try {
			scheduler.resumeJob(new JobKey(timerId));
		} catch (SchedulerException e) {
			log.error("Error resuming timer", e);
		}
	}
	
	@Override
	public boolean isJobExists(String jobId) {
		log.info("Checking if job exists with ID: {}", jobId);
		try {
			return scheduler.checkExists(new JobKey(jobId));
		} catch (SchedulerException e) {
			log.error("Error checking if job exists", e);
		}
		return false;
	}
	
	 
	@PostConstruct
	public void init() {
		try {
			//scheduler.start(); //chỉ sử dụng khi ko có cấu hình SchedulerFactoryBean để start quarz lên
			// nhưng nếu có SchedulerFactoryBean thì start spring sẽ tự lo 
			// nên cấu hình SchedulerFactoryBean setStartUp deplay để bảo đảm khi ứng dụng khởi động xong và đợi deplay nhất định mới chạy job bị missfire
			// bảo đảm các service khác đều đã khởi động xong, tránh service này khởi động xong, service khác chưa khởi động xong.
			scheduler.getListenerManager().addTriggerListener(new SimpleTriggerListener(this));
			log.info("Scheduler started successfully");
		} catch (SchedulerException e) {
			log.error("Error starting scheduler", e);
		}
	}
	
	@PreDestroy
	public void preDestroy() {
		try {
			scheduler.shutdown();
			log.info("Scheduler shutdown");
		} catch (SchedulerException e) {
			log.error("Error starting scheduler", e);
		}
	}

	@Override
	public boolean isJobExists(String jobName, String jobGroup) {
		log.info("Checking if job exists with jobName: {}, jobGroup: {}", jobName, jobGroup);
		try {
			return scheduler.checkExists(new JobKey(jobName, jobGroup));
		} catch (SchedulerException e) {
			log.error("Error checking if job exists", e);
		}
		return false;
	}
 
}
