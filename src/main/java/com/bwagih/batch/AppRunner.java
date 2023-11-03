package com.bwagih.batch;

import com.bwagih.batch.launcher.impl.DataLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements CommandLineRunner {


    @Autowired
    @Qualifier("CsvFileLauncher")
    DataLauncher csvDataLauncher;

    @Autowired
    @Qualifier("ExcelFileLauncher")
    DataLauncher excelDataLauncher;

    @Override
    public void run(String... args) throws Exception {
        csvDataLauncher.jobLauncher("persons.csv");
        excelDataLauncher.jobLauncher("persons.xlsx");
    }


}
