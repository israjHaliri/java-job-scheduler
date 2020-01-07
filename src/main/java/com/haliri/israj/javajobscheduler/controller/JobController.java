package com.haliri.israj.javajobscheduler.controller;

import com.haliri.israj.javajobscheduler.model.TaskType;
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
        Object clz = jobService.getJobClass(TaskTypeConvertor(type));

        jobService.scheduleJob(TaskTypeConvertor(type), (Class<? extends Job>) clz, "0/5 * * * * ?");

        return true;
    }

    @DeleteMapping("/jobs")
    public Object deleteJob(@RequestParam String type) {
        jobService.deleteJob(TaskTypeConvertor(type));

        return true;
    }

    private TaskType TaskTypeConvertor(String param) {
        TaskType taskType;

        try {
            taskType = TaskType.valueOf(param);
        } catch (IllegalArgumentException e) {
            taskType = null;
        }

        return taskType;
    }
}
