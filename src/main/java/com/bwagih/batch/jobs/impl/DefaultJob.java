package com.bwagih.batch.jobs.impl;

import org.springframework.batch.core.Job;

import java.io.InputStream;

public interface DefaultJob {

    Job defaultDataJob(InputStream inputStream);
}
