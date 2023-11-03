package com.bwagih.batch.readers;

import com.bwagih.batch.model.Person;
import com.bwagih.batch.readers.impl.ExcelFileReader;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.excel.RowMapper;
import org.springframework.batch.item.excel.mapping.BeanWrapperRowMapper;
import org.springframework.batch.item.excel.poi.PoiItemReader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;


@Component("ExcelFileReaderImpl")
public class ExcelFileReaderImpl implements ExcelFileReader<Person> {

    PoiItemReader<Person> excelFileReader = new PoiItemReader<>();


    @Override
    public Resource toByteArrayResource(InputStream inputStream) {
        return ExcelFileReader.super.toByteArrayResource(inputStream);
    }


    @Override
    public ItemReader<Person> read(InputStream inputStream) throws UnexpectedInputException, ParseException, NonTransientResourceException {

        excelFileReader.setResource(toByteArrayResource(inputStream));
        excelFileReader.setLinesToSkip(1);
        excelFileReader.setRowMapper(excelRowMapper());

        return excelFileReader;
    }


    @Override
    public RowMapper<Person> excelRowMapper() {
        BeanWrapperRowMapper<Person> rowMapper = new BeanWrapperRowMapper<>();
        rowMapper.setTargetType(Person.class);  ////TODO config with RowMapper not class


        HashMap<Class, PropertyEditor> customEditors = constructCustomEditors();
        rowMapper.setCustomEditors(customEditors);

        return rowMapper;
    }


    @Override
    public HashMap<Class, PropertyEditor> constructCustomEditors() {
        HashMap<Class, PropertyEditor> customEditors = new HashMap<>();

        customEditors.put(LocalDateTime.class, getDateEditorSupport());
        customEditors.put(Integer.class, getNumberEditorSupport());

        return customEditors;

    }


    private static PropertyEditorSupport getDateEditorSupport() {
        return new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                setValue(LocalDateTime.parse(text, DateTimeFormatter.ISO_DATE_TIME));
            }
        };
    }


    private static PropertyEditorSupport getNumberEditorSupport() {
        return new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                if (text != null && text.contains("."))
                    text = text.subSequence(0, text.indexOf(".")).toString();

                setValue(text);
            }
        };
    }


}
