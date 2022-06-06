package ru.financialtechnology.exsecond;

import ru.financialtechnology.exsecond.actions.AddStudent;
import ru.financialtechnology.exsecond.actions.DeleteStudentById;
import ru.financialtechnology.exsecond.actions.ShowAll;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HTwoMemory {

    public static void main(String[] args) {
        Output output = new ConsoleOutput();
        Input validate = new ValidateInput(output, new ConsoleInput());
        var url = "jdbc:h2:mem:";
        try (Connection con = DriverManager.getConnection(url);
             InputStream stream = HTwoMemory.class.getClassLoader().getResourceAsStream("create.sql");
             Statement stm = con.createStatement();) {
             String create = "CREATE TABLE students(id bigint auto_increment, name VARCHAR(255), surname VARCHAR(255), thirdname VARCHAR(255), birthday DATE, groupnumber INT);"
                     + "INSERT INTO students(name, surname, thirdname, birthday, groupnumber) VALUES ('Имя1', 'Фамилия1', 'Отчество1', current_date, 12);"
                     + "INSERT INTO students(name, surname, thirdname, birthday, groupnumber) VALUES ('Имя2', 'Фамилия2', 'Отчество2', current_date, 12);"
                     + "INSERT INTO students(name, surname, thirdname, birthday, groupnumber) VALUES ('Имя3', 'Фамилия3', 'Отчество3', current_date, 14);"
                     + "INSERT INTO students(name, surname, thirdname, birthday, groupnumber) VALUES ('Имя4', 'Фамилия4', 'Отчество4', current_date, 15);"
                     + "INSERT INTO students(name, surname, thirdname, birthday, groupnumber) VALUES ('Имя5', 'Фамилия5', 'Отчество5', current_date, 14);";

            stm.execute(create);
            ResultSet rs = stm.executeQuery("SELECT * FROM students");

            HTwoMemory hTwoMemory = new HTwoMemory();

            List<Action> listAction = new ArrayList<>(Arrays.asList(new ShowAll(output), new AddStudent(output), new DeleteStudentById(output)));
            hTwoMemory.init(validate, con, listAction);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void showMenu(List<Action> actions) {
        int index = 0;
        System.out.println("Menu.");

        for (Action it : actions) {
            System.out.println(index + ". " + it.name());
            index++;
        }
    }

    public void init(Input input, Connection connection, List<Action> actions) throws SQLException {
        boolean run = true;
        while (run) {
            this.showMenu(actions);
            int select = input.askInt("Действие: ");
            if (select >= actions.size()) {
                System.out.println("Wrong input, you can select: 0 .. " + (actions.size() - 1));
                continue;
            }
            Action action = actions.get(select);
            run = action.execute(input, connection);
        }
    }
}