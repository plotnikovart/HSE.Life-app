package app.back.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class EnumTable
{
    static void initialize(Connection connection) throws SQLException
    {
        getUniversitiesPS = connection.prepareStatement("SELECT name FROM university_list ORDER BY name");
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

    private static PreparedStatement getUniversitiesPS;
}
