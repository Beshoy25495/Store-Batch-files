package com.bwagih.batch.steps;

import com.bwagih.batch.model.Person;
import com.bwagih.batch.readers.impl.ExcelFileReader;
import com.bwagih.batch.steps.impl.DefaultStep;
import com.bwagih.batch.writers.impl.FileWriter;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.InputStream;
import java.util.Objects;

@Component("ExcelSteps")
public class ExcelSteps implements DefaultStep {

    @Autowired
    PlatformTransactionManager transactionManager;
    @Autowired
    JobRepository jobRepository;
    @Autowired
    @Qualifier("ExcelPersonItemProcessor")
    ItemProcessor<Person, Person> personItemProcessor;
    @Autowired
    @Qualifier("ExcelFileReaderImpl")
    ExcelFileReader<Person> fileReader;
    @Autowired
    @Qualifier("ExcelFileWriter")
    FileWriter<Person> writer;

    @Override
    public Step defaultStep(InputStream inputStream) {
        return new StepBuilder("excel.step1", jobRepository)
                .<Person, Person>chunk(10, transactionManager)
                .reader(Objects.requireNonNull(fileReader.read(inputStream)))
                .processor(personItemProcessor)
                .writer(writer.writer())
                .build();
    }


}
