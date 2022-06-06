package ru.financialtechnology.exsecond;

import java.sql.Connection;
import java.sql.SQLException;

public interface Action {
    String name();

    boolean execute(Input input, Connection connection) throws SQLException;
}
