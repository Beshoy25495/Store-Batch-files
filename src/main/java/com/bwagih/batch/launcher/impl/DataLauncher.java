package com.bwagih.batch.launcher.impl;

import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;

import java.lang.instrument.IllegalClassFormatException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public interface DataLauncher {

    public void jobLauncher(String path);
    public void validateFileType(String path) throws IllegalClassFormatException;

    default JobParameters newExecution() {
        Map<String, JobParameter<?>> parameters = new HashMap<>();

        JobParameter<Date> parameter = new JobParameter<>(new Date(), Date.class);
        parameters.put("currentTime", parameter);

        return new JobParameters(parameters);
    }

}
