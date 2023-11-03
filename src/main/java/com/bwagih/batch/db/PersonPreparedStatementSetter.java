package com.bwagih.batch.db;

import com.bwagih.batch.model.Person;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PersonPreparedStatementSetter implements ItemPreparedStatementSetter<Person> {
    @Override
    public void setValues(Person item, PreparedStatement ps) throws SQLException {

        ps.setObject(1 , item.getFirstName());
        ps.setObject(2 , item.getLastName());
        ps.setObject(3 , item.getEmail());
        ps.setObject(4 , item.getAge());

    }
}
