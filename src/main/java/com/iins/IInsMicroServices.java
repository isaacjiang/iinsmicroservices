package com.iins;

import com.iins.services.ScheduleService;
import com.iins.services.WorkflowService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

@Configuration
@EnableScheduling
@EnableAsync
@SpringBootApplication
public class IInsMicroServices {

	public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(IInsMicroServices.class);

	}

    @Bean
    public WorkflowService workflowService(){ return new WorkflowService();}
    @Bean
    ScheduleService scheduleService() { return new ScheduleService(); }
    @Bean
    TaskScheduler taskScheduler() { return new ConcurrentTaskScheduler(); }

}
