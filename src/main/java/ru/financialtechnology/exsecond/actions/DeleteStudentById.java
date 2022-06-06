package ru.financialtechnology.exsecond.actions;

import ru.financialtechnology.exsecond.Action;
import ru.financialtechnology.exsecond.Input;
import ru.financialtechnology.exsecond.Output;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DeleteStudentById implements Action {
    private final Output out;

    public DeleteStudentById(Output out) {
        this.out = out;
    }

    @Override
    public String name() {
        return "===Delete student ====";
    }

    @Override
    public boolean execute(Input input, Connection connection) throws SQLException {
        int idDelete = input.askInt("Введите id студента, которого будем удалять:");
        String sql = String.format("DELETE FROM Students WHERE id=%d", idDelete);
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
        out.println("Запись успешно удалена.");
        return true;
    }
}
