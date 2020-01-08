package com.haliri.israj.javajobscheduler.service;

import com.haliri.israj.javajobscheduler.App;
import com.haliri.israj.javajobscheduler.enumeration.TaskType;
import com.haliri.israj.javajobscheduler.job.BiffBuzzJob;
import com.haliri.israj.javajobscheduler.job.HelloWorldJob;
import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

@Service
public class JobService {

    @Autowired
    SchedulerFactoryBean schedulerFactoryBean;

    public void scheduleJob(TaskType taskType, Class<? extends org.quartz.Job> jobClass, String cronExpression) {
        App.getLogger(this).info("START SCHEDULER FOR : {}", taskType.name());

        JobDetailImpl jobDetail = new JobDetailImpl();
        jobDetail.setName(taskType.name());
        jobDetail.setJobClass(jobClass);

        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity(taskType.name() + "-TRIGGER")
                .withSchedule(
                        CronScheduleBuilder.cronSchedule(cronExpression))
                .build();

        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();

            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            App.getLogger(this).error("ERROR START SCHEDULER : {}", e.getLocalizedMessage());
        }
    }

    public void deleteJob(TaskType taskType) {
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();

            scheduler.deleteJob(new JobKey(taskType.name()));
            App.getLogger(this).info("JOB {} STOPPED", taskType.name());
        } catch (Exception ex) {
            App.getLogger(this).warn("JOB {} FAILED TO STOP : {}", taskType.name(), ex.getMessage());
        }
    }

    public Object getJobClass(TaskType taskType) {
        if (taskType.equals(TaskType.HELLO_WORLD)) {
            return HelloWorldJob.class;
        } else if (taskType.equals(TaskType.BIFF_BUZZ)) {
            return BiffBuzzJob.class;
        }

        return null;
    }
}
