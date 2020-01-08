package com.haliri.israj.javajobscheduler.controller;

import com.haliri.israj.javajobscheduler.enumeration.TaskType;
import com.haliri.israj.javajobscheduler.service.JobService;
import org.quartz.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JobController {

    @Autowired
    private JobService jobService;

    @PostMapping("/jobs")
    public Object saveJob(@RequestParam String type) {
        jobService.scheduleJob(TaskType.taskTypeConvertor(type), (Class<? extends Job>) jobService.getJobClass(TaskType.taskTypeConvertor(type)), "0/5 * * * * ?");

        return true;
    }

    @DeleteMapping("/jobs")
    public Object deleteJob(@RequestParam String type) {
        jobService.deleteJob(TaskType.taskTypeConvertor(type));

        return true;
    }
}
