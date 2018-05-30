package app.back;

public class Photo
{
    private int id;
    private String day;
    private String ref;

    private String[] days = {"пн", "вт", "ср", "чт", "пт", "сб", "вс"};

    public Photo(){
        id = 1;
        ref = "dsaa";
    }

    public String getDay()
    {
        return days[id - 1];
    }

    public String getRef()
    {
        return ref;
    }
}
