package app.back;

public class Event
{
    public static final String WAITED = "Ожидает";
    public static final String DELETED = "Удалено";
    public static final String READY = "Утверждено";

    private int id;
    private String status;
    private String[] params;                 // значения параметров
    // 0 - name
    // 1 - description
    // 2 - university
    // 3 - type
    // 4 - photoRef
    // 5 - ref
    // 6 - date
    // 7 - time (необязательный)
    // 8 - place (необязательный)

    public static final int PARAMS_NUMBER = 9;

    public Event()
    {
        status = WAITED;
        params = new String[PARAMS_NUMBER];
    }

    public Event(String name, String status)
    {
        params = new String[PARAMS_NUMBER];
        params[0] = name;
        this.status = status;
    }

    /**
     * Инициализирует такими же значениями
     * @param params Значения полей мероприятия
     */
    public Event(String[] params)
    {
        if (params.length == PARAMS_NUMBER)
        {
            this.params = params;
        }
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public void setId(int id){
        this.id = id;
    }

    /**
     * Устанавливает значение параметра мероприятия
     * @param paramNumber Номер параметра
     * @param value       Значение параметра
     */
    public void setParam(int paramNumber, String value)
    {
        params[paramNumber] = value;
    }

    /**
     * Геттер
     * @return Передает поля мероприятия
     */
    public String[] getParams()
    {
        return params;
    }

    public String getParam(int paramNumber)
    {
        return params[paramNumber];
    }

    /**
     * Геттер
     * @return Название мероприятия
     */
    public String getName()
    {
        return params[0];
    }

    public int getId(){
        return id;
    }

    public String getStatus()
    {
        return status;
    }

}
