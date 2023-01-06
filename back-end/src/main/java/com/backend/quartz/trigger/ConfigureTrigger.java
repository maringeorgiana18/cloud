package com.backend.quartz.trigger;

import com.backend.quartz.job.StudentStatistics;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigureTrigger {

    @Bean
    public JobDetail jobADetails() {
        return JobBuilder.newJob(StudentStatistics.class).withIdentity("ParticipantStatisticsJob")
                .storeDurably().build();
    }

    @Bean
    public Trigger jobATrigger(JobDetail jobADetails) {

        return TriggerBuilder.newTrigger().forJob(jobADetails)
                .withIdentity("TriggerForParticipantStatisticsJob")
                .withSchedule(CronScheduleBuilder.cronSchedule("0/20 * * ? * * *"))
                .build();
    }

    @Bean
    public Trigger jobBTrigger(JobDetail jobBDetails) {

        return TriggerBuilder.newTrigger().forJob(jobBDetails)
                .withIdentity("TriggerB")
                .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * ? * * *"))
                .build();
    }

}
