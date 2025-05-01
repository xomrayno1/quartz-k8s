package com.quartz.dto;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ScheduleRequest {
	private String jobName;
	private String jobGroup;
	private String jobDescription;
	private String cronExpression;
	//@Schema(description = "Tổng số lần job được phép thực thi.")
	private int totalFireCount;
	
	//@Schema(description = "Nếu true, job sẽ chạy vô hạn (bỏ qua totalFireCount)")
	private boolean runForever;
	
	//@Schema(description = "Khoảng thời gian giữa các lần thực thi job (đơn vị: ms).")
	private long repeatIntervalMs;
	
	
	private Map<String, Object> params;

}
