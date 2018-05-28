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
        getEventsNamesPS = connection.prepareStatement("SELECT name " +
                "FROM events " +
                "WHERE checked = false AND university = (SELECT id FROM university_list WHERE name = ?)");

        getEventsPS = connection.prepareStatement("SELECT " +
                "  name, " +
                "  description, " +
                "  (SELECT name FROM university_list WHERE id = university), " +
                "  (SELECT name FROM event_type_list WHERE id = type), " +
                "  photo, " +
                "  reference, " +
                "  DATE(datetime), " +
                "  TIME(datetime), " +
                "  place " +
                "FROM events " +
                "WHERE checked = false AND university = (SELECT id FROM university_list WHERE name = ?)");

        getEventPS = connection.prepareStatement("SELECT " +
                "  name, " +
                "  description, " +
                "  (SELECT name FROM university_list WHERE id = university), " +
                "  (SELECT name FROM event_type_list WHERE id = type), " +
                "  photo, " +
                "  reference, " +
                "  DATE(datetime), " +
                "  TIME(datetime), " +
                "  place " +
                "FROM events " +
                "WHERE name = ?");

        deleteEventPS = connection.prepareStatement("DELETE FROM events WHERE name = ?");
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

    public static Event getEvent(String eventName)
    {
        Event event = new Event();
        try
        {
            getEventsNamesPS.setString(1, eventName);
            ResultSet resultSet = getEventsNamesPS.executeQuery();

            resultSet.next();
            for (int i = 0; i < Event.PARAMS_NUMBER; i++)
            {
                event.setParam(i, resultSet.getString(i + 1));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return event;
    }

    public static LinkedList<Event> getEvents(String university)
    {
        LinkedList<Event> events = new LinkedList<>();
        try
        {
            getEventsPS.setString(1, university);
            ResultSet resultSet = getEventsPS.executeQuery();

            while (resultSet.next())
            {
                Event event = new Event();
                for (int i = 0; i < Event.PARAMS_NUMBER; i++)
                {
                    event.setParam(i, resultSet.getString(i + 1));
                }

                events.add(event);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return events;
    }

    public static void deleteEvent(Event event)
    {
        String name = event.getName();
        try
        {
            deleteEventPS.setString(1, name);
            deleteEventPS.execute();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    private static PreparedStatement getEventsNamesPS;
    private static PreparedStatement getEventsPS;
    private static PreparedStatement getEventPS;
    private static PreparedStatement deleteEventPS;
}
