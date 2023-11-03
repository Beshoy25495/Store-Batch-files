package com.bwagih.batch.launcher;

import com.bwagih.batch.launcher.impl.DataLauncher;
import com.bwagih.batch.jobs.impl.DefaultJob;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.IllegalClassFormatException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


@Component("ExcelFileLauncher")
public class ExcelFileLauncher implements DataLauncher {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    @Qualifier("ExcelJobs")
    DefaultJob excelJobs;


    @Override
    public void jobLauncher(String path) {

        InputStream inputStream = null;
        try {

            validateFileType(path);
            inputStream = Files.newInputStream(Paths.get(new ClassPathResource(path).getURI()));

            jobLauncher.run(excelJobs.defaultDataJob(inputStream), newExecution());

        } catch (IOException | JobInstanceAlreadyCompleteException | JobExecutionAlreadyRunningException |
                 JobParametersInvalidException | IllegalClassFormatException | JobRestartException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void validateFileType(String path) throws IllegalClassFormatException {
        if (!Objects.nonNull(path))
            throw new RuntimeException();
        String[] tokenizer = path.split("\\.");
        if(!tokenizer[tokenizer.length-1].toLowerCase(Locale.ROOT).equals("xlsx"))
            throw new IllegalClassFormatException();
    }


}
