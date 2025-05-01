package com.quartz.controller;

import java.util.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quartz.dto.ScheduleRequest;
import com.quartz.dto.ScheduleResponse;
import com.quartz.service.SchedulerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class SchedulerController {
	private final SchedulerService schedulerService;
	
	
	@PostMapping("/create")
	public ResponseEntity<ScheduleResponse> createJob(@RequestBody ScheduleRequest scheduleRequest){
		Date date = schedulerService.schedule(scheduleRequest);
		return ResponseEntity.ok(
				ScheduleResponse
				.builder()
				.date(date)
				.build()
		);
	}
	
	@PostMapping("/delete")
	public ResponseEntity<ScheduleResponse> deleteJob(@RequestParam String jobName, @RequestParam String jobGroup){
		schedulerService.deleteTimer(jobName, jobGroup);
		return ResponseEntity.ok(
				ScheduleResponse
				.builder()
				.date(new Date())
				.jobName(jobName)
				.jobGroup(jobGroup)
				.build()
		);
	}
	
}
