package com.quartz.service.job.trigger;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.Trigger.CompletedExecutionInstruction;
import org.quartz.TriggerListener;

import com.quartz.dto.TimerInfo;
import com.quartz.service.impl.SchedulerServiceImpl;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SimpleTriggerListener implements TriggerListener{
	
	private final SchedulerServiceImpl schedulerService;

	@Override
	public String getName() {
		return SimpleTriggerListener.class.getSimpleName();
	}

	@Override
	public void triggerFired(Trigger trigger, JobExecutionContext context) {
		final String timerId = trigger.getKey().getName();
		final JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
		
		Object object = jobDataMap.get(timerId);
		if(object != null && object instanceof TimerInfo) {
			final TimerInfo timerInfo = (TimerInfo) object;
			
			if (!timerInfo.isRunForever()) {
				int remainingFireCount = timerInfo.getRemainingFireCount();
				if(remainingFireCount == 0) {
					return;
				}
				timerInfo.setRemainingFireCount(remainingFireCount - 1);
			}
			schedulerService.updateTimer(timerId, timerInfo);
		}
 
	}

	@Override
	public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
		return false;
	}

	@Override
	public void triggerMisfired(Trigger trigger) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void triggerComplete(Trigger trigger, JobExecutionContext context,
			CompletedExecutionInstruction triggerInstructionCode) {
		// TODO Auto-generated method stub
		
	}

}
