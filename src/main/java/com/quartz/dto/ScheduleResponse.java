package com.quartz.dto;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class ScheduleResponse {
	
	private Date date;
	
	private String jobName;
	
	private String jobGroup;

}
