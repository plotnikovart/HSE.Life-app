package app.back;

public class Photo
{
    private int id;
    private String ref;

    private String[] days = {"пн", "вт", "ср", "чт", "пт", "сб", "вс"};

    public Photo(int id, String ref)
    {
        this.id = id;
        this.ref = ref;
    }

    public void setRef(String ref)
    {
        this.ref = ref;
    }

    public String getDay()
    {
        return days[id - 1];
    }

    public String getRef()
    {
        return ref;
    }

    public int getDayId()
    {
        return id;
    }
}
