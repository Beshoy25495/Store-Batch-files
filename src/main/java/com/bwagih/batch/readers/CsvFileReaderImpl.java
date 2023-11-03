package com.bwagih.batch.readers;

import com.bwagih.batch.model.Person;
import com.bwagih.batch.readers.impl.CsvFileReader;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.*;


@Component("CsvFileReaderImpl")
public class CsvFileReaderImpl implements CsvFileReader<Person> {

    FlatFileItemReader<Person> csvFileReader = new FlatFileItemReader<>();


    @Override
    public Resource toByteArrayResource(InputStream inputStream) {
        return CsvFileReader.super.toByteArrayResource(inputStream);
    }

    @Override
    public ItemReader<Person> read(InputStream inputStream) throws UnexpectedInputException, ParseException, NonTransientResourceException {

        csvFileReader.setResource(toByteArrayResource(inputStream));

        csvFileReader.setLineMapper(createDefaultLineMapper());

        return csvFileReader;
    }

    @Override
    public LineMapper<Person> createDefaultLineMapper() {
        DefaultLineMapper<Person> defaultLineMapper = new DefaultLineMapper<>();

        LineTokenizer personLineTokenizer = createDefaultLineTokenizer();
        defaultLineMapper.setLineTokenizer(personLineTokenizer);

        FieldSetMapper<Person> personInformationMapper = createDefaultInformationMapper();
        defaultLineMapper.setFieldSetMapper(personInformationMapper);

        return defaultLineMapper;
    }

    @Override
    public LineTokenizer createDefaultLineTokenizer() {
        DelimitedLineTokenizer defaultLineTokenizer = new DelimitedLineTokenizer();
        defaultLineTokenizer.setDelimiter(",");
        defaultLineTokenizer.setNames(new String[]{"firstName", "lastName", "email", "age"}); //TODO config
        return defaultLineTokenizer;
    }

    @Override
    public FieldSetMapper<Person> createDefaultInformationMapper() {
        BeanWrapperFieldSetMapper<Person> defaultInformationMapper = new BeanWrapperFieldSetMapper<>();
        defaultInformationMapper.setTargetType(Person.class);
        return defaultInformationMapper;
    }












}
