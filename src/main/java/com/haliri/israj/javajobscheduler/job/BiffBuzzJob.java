package com.haliri.israj.javajobscheduler.job;

import com.haliri.israj.javajobscheduler.App;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class BiffBuzzJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        App.getLogger(this).info("BIFF BUZZ");
    }
}
