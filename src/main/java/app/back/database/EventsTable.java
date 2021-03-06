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
    private static PreparedStatement getEventsPS;
    private static PreparedStatement updateEventPS;
    private static PreparedStatement addEventPS;
    private static PreparedStatement deleteEventPS;

    static void initialize(Connection connection) throws SQLException
    {
        getEventsPS = connection.prepareStatement("SELECT " +
                "  id, " +
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
                "WHERE checked = FALSE AND university = (SELECT id FROM university_list WHERE name = ?) " +
                "ORDER BY datetime");


        updateEventPS = connection.prepareStatement("UPDATE events " +
                "SET " +
                "  checked = ?, " +
                "  name = ?, " +
                "  description = ?, " +
                "  university = (SELECT id FROM university_list WHERE university_list.name = ?), " +
                "  type = (SELECT id FROM event_type_list WHERE event_type_list.name = ?), " +
                "  photo = ?, " +
                "  reference = ?, " +
                "  datetime = CONCAT(?,' ',?), " +
                "  place = ? " +
                "WHERE id = ?");

        addEventPS = connection.prepareStatement("INSERT INTO events " +
                "(checked, name, description, university, type, photo, reference, datetime, place) " +
                "VALUES " +
                "  (?, ?, ?, " +
                "(SELECT id FROM university_list WHERE university_list.name = ?)," +
                "(SELECT id FROM event_type_list WHERE event_type_list.name = ?), " +
                "?, ?, CONCAT(?, ' ', ?), ?)");

        deleteEventPS = connection.prepareStatement("DELETE FROM events WHERE name = ?");
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
                event.setId(resultSet.getInt(1));
                for (int i = 0; i < Event.PARAMS_NUMBER; i++)
                {
                    event.setParam(i, resultSet.getString(i + 2));
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

    public static void updateEvent(Event event, boolean status)
    {
        try
        {
            updateEventPS.setBoolean(1, status);
            String[] params = event.getParams();
            for (int i = 0; i < params.length; i++)
            {
                updateEventPS.setString(i + 2, params[i]);
            }
            updateEventPS.setInt(params.length + 2, event.getId());

            updateEventPS.execute();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void addEvent(Event event, boolean status)
    {
        try
        {
            addEventPS.setBoolean(1, status);
            String[] params = event.getParams();
            for (int i = 0; i < params.length; i++)
            {
                addEventPS.setString(i + 2, params[i]);
            }

            addEventPS.execute();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
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
}
