package com.bwagih.batch.writers;

import com.bwagih.batch.db.PersonPreparedStatementSetter;
import com.bwagih.batch.model.Person;
import com.bwagih.batch.writers.impl.FileWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.ItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;


@Component("ExcelFileWriter")
public class ExcelFileWriter implements FileWriter<Person> {

    String query = "INSERT INTO person (first_name, last_name,email,age) VALUES (?,?,?,?)";
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    JdbcBatchItemWriter<Person> databaseItemWriter = new JdbcBatchItemWriter<Person>();

    @Override
    public JdbcBatchItemWriter<Person> writer() {

        databaseItemWriter.setJdbcTemplate(jdbcTemplate);
        databaseItemWriter.setSql(query);

        ItemSqlParameterSourceProvider<Person> sqlParameterSourceProvider = personItemSqlParameterSourceProvider();
        databaseItemWriter.setItemSqlParameterSourceProvider(sqlParameterSourceProvider);

        ItemPreparedStatementSetter<Person> studentPreparedStatementSetter = new PersonPreparedStatementSetter();
        databaseItemWriter.setItemPreparedStatementSetter(studentPreparedStatementSetter);

        return databaseItemWriter;
    }


    @Override
    public ItemSqlParameterSourceProvider<Person> personItemSqlParameterSourceProvider() {
        return new BeanPropertyItemSqlParameterSourceProvider<>();
    }


}
