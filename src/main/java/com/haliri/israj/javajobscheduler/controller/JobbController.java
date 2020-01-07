package com.haliri.israj.javajobscheduler.controller;

import com.haliri.israj.javajobscheduler.entity.Task;
import com.haliri.israj.javajobscheduler.entity.TaskType;
import com.haliri.israj.javajobscheduler.service.JobService;
import org.quartz.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JobbController {

    @Autowired
    private JobService jobService;

    @PostMapping("/jobs")
    public void saveJob(@RequestParam TaskType taskType) {
        Task task = new Task();
        task.setType(taskType);
        task.setCronExpression("0/10 * * * * ?");

        Object clz = jobService.getJobClass(task.getType());

        jobService.saveJob(task, (Class<? extends Job>) clz, "0/5 * * * * ?");
    }

    @DeleteMapping("/jobs")
    public void deleteJob(@RequestParam TaskType taskType) {
        jobService.deleteAndStopJob(taskType);
    }
}
