package ru.financialtechnology.exsecond.actions;

import ru.financialtechnology.exsecond.Action;
import ru.financialtechnology.exsecond.Input;
import ru.financialtechnology.exsecond.Output;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ShowAll implements Action {
    private final Output out;
    public ShowAll(Output out) {
        this.out = out;
    }

    @Override
    public String name() {
        return "===Show all items ====";
    }

    @Override
    public boolean execute(Input input, Connection connection) throws SQLException {
        String sql  = "SELECT * FROM Students";
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sql);
        while (result.next()) {
            String value = String.format("Уникальный номер - %d; Имя - %s; Фамилия - %s; Отчество - %s; Дата рождения - %s; Группа - %d",
                    result.getInt("id"), result.getString("name"), result.getString("surname"),
                    result.getString("thirdname"), result.getDate("birthday"),
                    result.getInt("groupnumber"));
            out.println(value);
        }
        return true;
    }
}
