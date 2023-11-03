package com.bwagih.batch.steps;

import com.bwagih.batch.model.Person;
import com.bwagih.batch.readers.impl.CsvFileReader;
import com.bwagih.batch.steps.impl.DefaultStep;
import com.bwagih.batch.writers.impl.FileWriter;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.*;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.InputStream;
import java.util.Objects;

@Component("CsvSteps")
public class CsvSteps implements DefaultStep {

    @Autowired
    PlatformTransactionManager transactionManager;
    @Autowired
    JobRepository jobRepository;
    @Autowired
    @Qualifier("CsvPersonItemProcessor")
    ItemProcessor<Person, Person> personItemProcessor;
    @Autowired
    @Qualifier("CsvFileReaderImpl")
    CsvFileReader<Person> fileReader;

    @Autowired
    @Qualifier("CsvFileWriter")
    FileWriter<Person> writer;


    @Override
    public Step defaultStep(InputStream inputStream) {
        return new StepBuilder("csv.step1", jobRepository)
                .<Person, Person>chunk(10, transactionManager)
                .reader(Objects.requireNonNull(fileReader.read(inputStream)))
                .processor(personItemProcessor)
                .writer(writer.writer())
                .build();
    }

}
