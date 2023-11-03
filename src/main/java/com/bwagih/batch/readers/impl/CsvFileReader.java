package com.bwagih.batch.readers.impl;

import com.bwagih.batch.model.Person;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.LineTokenizer;

public interface CsvFileReader<T> extends FileReader{

    LineMapper<Person> createDefaultLineMapper();

    LineTokenizer createDefaultLineTokenizer();

    FieldSetMapper<Person> createDefaultInformationMapper();
}
