package ru.financialtechnology.exsecond;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;

public class HTwoMemory {
    public static void main(String[] args) {
        var url = "jdbc:h2:mem:";
        try (Connection con = DriverManager.getConnection(url);
             InputStream stream = HTwoMemory.class.getClassLoader().getResourceAsStream("create.sql");
             Statement stm = con.createStatement();) {
             String create = "CREATE TABLE students(id bigint auto_increment, name VARCHAR(255), sername VARCHAR(255), thrdname VARCHAR(255), bithdate TIME, groupnumber INT);"
                     + "INSERT INTO students(name, sername, thrdname, bithdate, groupnumber) VALUES ('Имя1', 'Фамилия1', 'Отчество1', current_date, 12);"
                     + "INSERT INTO students(name, sername, thrdname, bithdate, groupnumber) VALUES ('Имя2', 'Фамилия2', 'Отчество2', current_date, 12);"
                     + "INSERT INTO students(name, sername, thrdname, bithdate, groupnumber) VALUES ('Имя3', 'Фамилия3', 'Отчество3', current_date, 14);"
                     + "INSERT INTO students(name, sername, thrdname, bithdate, groupnumber) VALUES ('Имя4', 'Фамилия4', 'Отчество4', current_date, 15);"
                     + "INSERT INTO students(name, sername, thrdname, bithdate, groupnumber) VALUES ('Имя5', 'Фамилия5', 'Отчество5', current_date, 14);";

            stm.execute(create);
             ResultSet rs = stm.executeQuery("SELECT * FROM students");

            if (rs.next()) {
                System.out.println(rs.getString(3));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}