package app.back.database;

import app.back.Event;
import app.back.Photo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class EnumTable
{
    private static PreparedStatement getUniversitiesPS;
    private static PreparedStatement getEventsTypesPS;
    private static PreparedStatement getPhotosPS;
    private static PreparedStatement updatePhotoPS;

    static void initialize(Connection connection) throws SQLException
    {
        getUniversitiesPS = connection.prepareStatement("SELECT name FROM university_list ORDER BY name");
        getEventsTypesPS = connection.prepareStatement("SELECT name FROM event_type_list ORDER BY name");
        getPhotosPS = connection.prepareStatement("SELECT day_of_week, reference FROM everyday_images");
        updatePhotoPS = connection.prepareStatement("UPDATE everyday_images SET reference = ? WHERE day_of_week = ?");
    }

    public static LinkedList<String> getUniversities()
    {
        LinkedList<String> universities = new LinkedList<>();
        try
        {
            ResultSet resultSet = getUniversitiesPS.executeQuery();
            while (resultSet.next())
            {
                universities.add(resultSet.getString(1));
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return universities;
    }

    public static LinkedList<String> getEventsTypes()
    {
        LinkedList<String> types = new LinkedList<>();
        try
        {
            ResultSet resultSet = getEventsTypesPS.executeQuery();
            while (resultSet.next())
            {
                types.add(resultSet.getString(1));
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return types;
    }

    public static LinkedList<Photo> getPhotos()
    {
        LinkedList<Photo> photos = new LinkedList<>();
        try
        {
            ResultSet resultSet = getPhotosPS.executeQuery();

            while (resultSet.next())
            {
                int day = resultSet.getInt(1);
                String ref = resultSet.getString(2);

                photos.add(new Photo(day, ref));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return photos;
    }

    public static void updatePhoto(Photo photo)
    {
        try
        {
            updatePhotoPS.setString(1, photo.getRef());
            updatePhotoPS.setInt(2, photo.getDayId());
            updatePhotoPS.execute();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
