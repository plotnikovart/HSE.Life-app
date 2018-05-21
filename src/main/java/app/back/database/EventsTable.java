package app.back.database;

import app.back.Event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

public class EventsTable
{
    static void initialize(Connection connection) throws SQLException
    {
//        getEventsNamesPS = connection.prepareStatement("SELECT name, description, university, type, photo, reference, DATE(datetime), TIME(datetime), place " +
//                "FROM events " +
//                "WHERE checked = false AND university = (SELECT id FROM university_list WHERE name = ?)");

        getEventsNamesPS = connection.prepareStatement("SELECT name " +
                "FROM events " +
                "WHERE checked = false AND university = (SELECT id FROM university_list WHERE name = ?)");
    }

    public static LinkedList<Event> getEvents(String university)
    {
        LinkedList<Event> names = new LinkedList<>();
        try
        {
            getEventsNamesPS.setString(1, university);

            ResultSet resultSet = getEventsNamesPS.executeQuery();
            while (resultSet.next())
            {
                String[] params = new String[Event.PARAMS_NUMBER];
                for (int i = 0; i < params.length; i++)
                {
                    params[i] = resultSet.getString(i + 1);
                }
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return names;
    }

    public static LinkedList<String> getEventsNames(String university)
    {
        LinkedList<String> names = new LinkedList<>();
        try
        {
            getEventsNamesPS.setString(1, university);

            ResultSet resultSet = getEventsNamesPS.executeQuery();
            while (resultSet.next())
            {
                names.add(resultSet.getString(1));
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return names;
    }

    private static PreparedStatement getEventsNamesPS;
}
