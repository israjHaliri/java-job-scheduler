package com.haliri.israj.javajobscheduler.service;

import com.haliri.israj.javajobscheduler.App;
import com.haliri.israj.javajobscheduler.entity.Task;
import com.haliri.israj.javajobscheduler.entity.TaskType;
import com.haliri.israj.javajobscheduler.job.BiffBuzzJob;
import com.haliri.israj.javajobscheduler.job.HelloWorldJob;
import com.haliri.israj.javajobscheduler.repository.TaskRepository;
import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Service;

@Service
public class JobService {

    private Scheduler scheduler;

    private final TaskRepository taskRepository;

    public JobService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;

        SchedulerFactory factory = new StdSchedulerFactory();

        try {
            scheduler = factory.getScheduler();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

        init();
    }

    private void init() {
        App.getLogger(this).info("INIT SCHEDULER");

        taskRepository.findAll().forEach(task -> {
            Object clz = getJobClass(task.getType());

            if (clz != null) {
                runJob(task.getType(), (Class<? extends Job>) clz, task.getCronExpression());
            }
        });

    }

    public Object getJobClass(TaskType taskType) {
        if (taskType.equals(TaskType.HELLO_WORLD)) {
            return HelloWorldJob.class;
        }else if (taskType.equals(TaskType.BIFF_BUZZ)) {
            return BiffBuzzJob.class;
        }

        return null;
    }

    private void runJob(TaskType taskType, java.lang.Class<? extends org.quartz.Job> jobClass, String cronExpression) {
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
            scheduler.start();
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            App.getLogger(this).error("ERROR START SCHEDULER : {}", e.getLocalizedMessage());
        }
    }

    public void saveJob(Task task, java.lang.Class<? extends org.quartz.Job> jobClass, String cronExpression) {
        try {
            taskRepository.save(task);
        }catch (Exception e){
            App.getLogger(this).error("ERROR SAVE SCHEDULER : {}", e.getLocalizedMessage());

            return;
        }

        runJob(task.getType(), jobClass, cronExpression);
    }

    public void deleteAndStopJob(TaskType taskType) {
        try {
            Task task = taskRepository.findFirstByType(TaskType.valueOf(taskType.name()));

            if (task != null) {
                taskRepository.delete(task);

                scheduler.deleteJob(new JobKey(taskType.name()));

                App.getLogger(this).info("JOB {} STOPPED", taskType.name());
            } else {
                App.getLogger(this).warn("JOB {} FAILED TO STOP", taskType.name());
            }
        } catch (Exception ex) {
            App.getLogger(this).error(ex.getLocalizedMessage());
        }
    }
}
