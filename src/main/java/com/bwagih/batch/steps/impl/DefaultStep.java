package com.bwagih.batch.steps.impl;

import org.springframework.batch.core.Step;

import java.io.InputStream;

public interface DefaultStep {

    public abstract Step defaultStep(InputStream inputStream);

}
