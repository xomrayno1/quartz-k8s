package com.quartz.dto;

import java.io.Serializable;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimerInfo implements Serializable {
	
	private static final long serialVersionUID = 4502745407210692744L;
	
	private String jobName;
	private String jobGroup;
	private String jobDescription;

	//@Schema(description = "Tổng số lần job được phép thực thi.")
	private int totalFireCount;
	
	//@Schema(description = "Số lần còn lại để job thực thi. Thường dùng để đếm ngược.")
	private int remainingFireCount;
	
	//@Schema(description = "Nếu true, job sẽ chạy vô hạn (bỏ qua totalFireCount)")
	private boolean runForever;
	
	//@Schema(description = "Khoảng thời gian giữa các lần thực thi job (đơn vị: ms).")
	private long repeatIntervalMs;
	
	//@Schema(description = "Thời gian delay trước khi job được thực thi lần đầu tiên (đơn vị: ms). VD khi gọi tới job thì job sẽ được thực thi sau initialOffsetMs. ")
	private long initialOffsetMs;
	
	//@Schema(description = "Dữ liệu callback bổ sung – có thể dùng để truyền ID, URL, hoặc metadata nào đó.")
	private String callbackData;
	
	//@Schema(description = "Biểu thức Cron nếu job này là job định kỳ theo cron. Nếu null thì không phải cron.")
	private String cronExpression;
	
	//@Schema(description = "Map<String, Object>	Cho phép truyền dữ liệu động vào job – có thể dùng như context hoặc params.")
	private Map<String, Object> data;
	
	public boolean isCron() {
		return cronExpression != null && !cronExpression.isEmpty();
	}

}
