package ru.financialtechnology.exsecond.actions;

import ru.financialtechnology.exsecond.Action;
import ru.financialtechnology.exsecond.Input;
import ru.financialtechnology.exsecond.Output;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class AddStudent implements Action {
    private final Output out;

    public AddStudent(Output out) {
        this.out = out;
    }

    @Override
    public String name() {
        return "===Add student ====";
    }

    @Override
    public boolean execute(Input input, Connection connection) throws SQLException {

        String name = "";
        String surname = "";
        String thirdname = "";
        LocalDate date = null;
        String groupnumber = "";


        int column = 1;
        while (column < 6) {
            switch (column) {
                case 1: name = input.askStr("Введите имя студента: ");
                    if (name.trim().isEmpty()) {
                        System.out.println("Пустое имя недопустимо!");
                    }  else {
                        column++;
                    }
                    break;

                case 2: surname = input.askStr("Введите фамилию студента: ");
                    if (surname.trim().isEmpty()) {
                        System.out.println("Пустая фамилия недопустима!");
                    } else {
                        column++;
                    }
                    break;

                case 3: thirdname = input.askStr("Введите отчество студента: ");
                    if (thirdname.trim().isEmpty()) {
                        thirdname = null;
                    }
                    column++;
                    break;

                case 4:
                    String birthdayString = "";
                     birthdayString = input.askStr("Введите день рождения студента в числовом формате День:Месяц:Год ");
                    boolean isDate = true;
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d:M:y", Locale.ENGLISH);
                    date = LocalDate.parse(birthdayString, formatter);
                    if (!isDate) {
                        System.out.println("Некорректная дата!");
                    } else {
                        column++;
                    }
                    break;

                case 5: groupnumber = input.askStr("Введите группу студента: ");
                    if (groupnumber.trim().isEmpty()) {
                        System.out.println("Пустая группа недопустима!");
                    } else {
                        column++;
                    }
                    break;

                default:
                    break;
            }
        }

        String sql = String.format("INSERT INTO students (name,"
                        + "surname, thirdname, birthday, groupnumber) VALUES "
                        + "('%s', '%s', '%s', '%s', '%s')",
                name, surname, thirdname, date, groupnumber);
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
        out.println(" Новая запись добавлена");
        return true;
    }
}
