package com.bwagih.batch.writers.impl;

import org.springframework.batch.item.database.ItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;

public interface FileWriter<T> {
    JdbcBatchItemWriter<T> writer();

    ItemSqlParameterSourceProvider<T> personItemSqlParameterSourceProvider();
}
