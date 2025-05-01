package com.quartz.service.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import com.quartz.entity.Customer;
import com.quartz.repository.CustomerRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@DisallowConcurrentExecution
@Component
public class CustomerJob extends QuartzJobBean implements InterruptableJob{
	 
//	[2m2025-05-01T16:33:48.195+07:00[0;39m [32m INFO[0;39m [35m12454[0;39m [2m--- [quartz-demo-k8s] [uartzScheduler]] [0;39m[36mo.s.s.quartz.SchedulerFactoryBean       [0;39m [2m:[0;39m Starting Quartz Scheduler now, after delay of 20 seconds
//	[2m2025-05-01T16:33:48.231+07:00[0;39m [32m INFO[0;39m [35m12454[0;39m [2m--- [quartz-demo-k8s] [uartzScheduler]] [0;39m[36mo.s.s.quartz.LocalDataSourceJobStore    [0;39m [2m:[0;39m ClusterManager: detected 1 failed or restarted instances.
//	[2m2025-05-01T16:33:48.231+07:00[0;39m [32m INFO[0;39m [35m12454[0;39m [2m--- [quartz-demo-k8s] [uartzScheduler]] [0;39m[36mo.s.s.quartz.LocalDataSourceJobStore    [0;39m [2m:[0;39m ClusterManager: Scanning for instance "NON_CLUSTERED"'s failed in-progress jobs.
//	[2m2025-05-01T16:33:48.253+07:00[0;39m [32m INFO[0;39m [35m12454[0;39m [2m--- [quartz-demo-k8s] [uartzScheduler]] [0;39m[36morg.quartz.core.QuartzScheduler         [0;39m [2m:[0;39m Scheduler QuartzScheduler_$_NON_CLUSTERED started.
 
	@Autowired
	private CustomerRepository customerRepository;
	
	
	@Override
	public void executeInternal(JobExecutionContext context) throws JobExecutionException {
		log.info("CustomerJob Start................");
		
		Customer customer = customerRepository.findById(1l).orElse(null);
		customer.setPoints(customer.getPoints() + 10l);
		customerRepository.save(customer);
		
		try {
			log.info("start sleep 15s");
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		log.info("CustomerJob End................Point: {}", customer.getPoints());
		 
	}

	@Override
	public void interrupt() throws UnableToInterruptJobException {
		log.info("Interrupting CustomerJob");
		
	}
 
}
