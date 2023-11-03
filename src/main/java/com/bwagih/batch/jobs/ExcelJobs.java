package com.bwagih.batch.jobs;


import com.bwagih.batch.jobs.impl.DefaultJob;
import com.bwagih.batch.listener.JobCompletionNotificationListener;
import com.bwagih.batch.steps.impl.DefaultStep;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component("ExcelJobs")
public class ExcelJobs implements DefaultJob {

    @Autowired
    JobCompletionNotificationListener listener;

    @Autowired
    JobRepository jobRepository;

    @Autowired
    @Qualifier("ExcelSteps")
    DefaultStep step1;


    @Override
    public Job defaultDataJob(InputStream inputStream) {
        return new JobBuilder("excel.importUserJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1.defaultStep(inputStream))
                .end()
                .build();
    }


}
