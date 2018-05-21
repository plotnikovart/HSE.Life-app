package app.back.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBWorker
{
    public static void initialize() throws SQLException, ClassNotFoundException
    {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

        EnumTable.initialize(connection);
        EventsTable.initialize(connection);
    }


    private static final String URL = "jdbc:mysql://localhost:3306/hse_life_database?characterEncoding=utf8&autoReconnect=true&useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
}
