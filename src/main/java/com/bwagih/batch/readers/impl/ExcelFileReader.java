package com.bwagih.batch.readers.impl;

import org.springframework.batch.item.excel.RowMapper;

import java.beans.PropertyEditor;
import java.util.HashMap;

public interface ExcelFileReader<T> extends FileReader{

    RowMapper<T> excelRowMapper();
    HashMap<Class, PropertyEditor> constructCustomEditors();

}
