package app;

import app.back.database.DBWorker;
import app.front.MainPage;


public class Initializer
{
    public static void main(String[] args)
    {
        try
        {
            DBWorker.initialize();
            MainPage.start();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
