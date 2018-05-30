package app.front;

import app.back.Event;
import app.back.database.EnumTable;
import app.back.database.EventsTable;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.stage.Stage;


import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Objects;


public class EditController
{
    static MainPage mainPage;
    static EventsController eventsController;

    @FXML public TextField name;
    @FXML public TextArea description;
    @FXML public ChoiceBox university;
    @FXML public ChoiceBox type;
    @FXML public TextField photoRef;
    @FXML public TextField ref;
    @FXML public DatePicker date;
    @FXML public TextField time;
    @FXML public TextField place;

    public RadioButton allowFlag;
    public RadioButton deleteFlag;

    boolean isAdded = false;
    private Event currentEvent;
    private Stage stage;
    private double currentWidth, currentHeight;     // текущая ширина и высота (для сохранения размеров окна)

    @FXML
    void initialize()
    {
        // Добавление списка университетов
        LinkedList<String> universities = EnumTable.getUniversities();
        university.getItems().addAll(universities);

        // Добавление типов мероприятий
        LinkedList<String> eventsTypes = EnumTable.getEventsTypes();
        type.getItems().addAll(eventsTypes);
    }

    void setStage(Stage stage)
    {
        this.stage = stage;
    }

    void setSize(double width, double heigth)
    {
        currentWidth = width;
        currentHeight = heigth;
    }

    public void showPost(MouseEvent mouseEvent)
    {
        mainPage.getHostServices().showDocument(ref.getText());
    }

    public void showPhoto(MouseEvent mouseEvent)
    {
        mainPage.getHostServices().showDocument(photoRef.getText());
    }

    /**
     * Показ формы для редактирования мероприятия
     */
    void showWindow(Event event)
    {
        currentEvent = event;
        resetFields();
        isAdded = false;
        deleteFlag.setVisible(true);

        name.setText(event.getParam(0));
        description.setText(event.getParam(1));
        university.setValue(event.getParam(2));
        type.setValue(event.getParam(3));
        photoRef.setText(event.getParam(4));
        ref.setText(event.getParam(5));

        date.setValue(LocalDate.parse(event.getParam(6)));

        String timeS = event.getParam(7);
        if (timeS.equals("00:00:01"))
        {
            time.setText("");
        }
        else
        {
            time.setText(timeS.substring(0, 5));
        }

        String placeS = event.getParam(8);
        place.setText(Objects.requireNonNullElse(placeS, ""));

        allowFlag.setSelected(false);
        deleteFlag.setSelected(false);

        stage.setWidth(currentWidth);
        stage.setHeight(currentHeight);
        stage.show();
    }

    /**
     * Показ формы для добавления мероприятия
     */
    void showWindow()
    {
        currentEvent = new Event();
        resetFields();
        isAdded = true;
        deleteFlag.setVisible(false);

        stage.setWidth(currentWidth);
        stage.setHeight(currentHeight);
        stage.show();
    }

    public void save(MouseEvent mouseEvent)
    {
        if (deleteFlag.isSelected())
        {
            EventsTable.deleteEvent(currentEvent);
            currentEvent.setStatus(Event.DELETED);
        }
        else
        {
            if (!checkFields())
            {
                return;
            }

            // Перенос значений формы в event
            currentEvent.setParam(0, name.getText());
            currentEvent.setParam(1, description.getText());
            currentEvent.setParam(2, (String)university.getValue());
            currentEvent.setParam(3, (String)type.getValue());
            currentEvent.setParam(4, photoRef.getText());
            currentEvent.setParam(5, ref.getText());

            LocalDate localDate = date.getValue();
            String dateStr = localDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
            currentEvent.setParam(6, dateStr);

            if (time.getText().equals(""))
            {
                currentEvent.setParam(7, "00:00:01");
            }
            else
            {
                currentEvent.setParam(7, time.getText());
            }

            currentEvent.setParam(8, place.getText());

            // Если форма используется для добавления
            if (isAdded)
            {
                if (allowFlag.isSelected())
                {
                    EventsTable.addEvent(currentEvent, true);
                    currentEvent.setStatus(Event.READY);
                }
                else
                {
                    EventsTable.addEvent(currentEvent, false);
                    currentEvent.setStatus(Event.WAITED);
                }

                eventsController.addEventToTable(currentEvent);
            }
            else    // для редактирования
            {
                if (allowFlag.isSelected())
                {
                    EventsTable.updateEvent(currentEvent, true);
                    currentEvent.setStatus(Event.READY);
                }
                else
                {
                    EventsTable.updateEvent(currentEvent, false);
                    currentEvent.setStatus(Event.WAITED);
                }
            }
        }

        eventsController.updateTable();
        stage.hide();
    }

    public void cancel(MouseEvent mouseEvent)
    {
        currentWidth = stage.getWidth();
        currentHeight = stage.getHeight();

        stage.hide();
    }

    private boolean checkFields()
    {
        String errorColor = "-fx-background-color: LightCoral;";
        String goodColor = "-fx-background-color: white;";
        boolean returnValue = true;

        if (name.getText().equals(""))
        {
            name.setStyle(errorColor);
            returnValue = false;
        }
        else
        {
            name.setStyle(goodColor);
        }

        if (description.getText().equals(""))
        {
            description.setStyle(errorColor);
            returnValue = true;
        }
        else
        {
            description.setStyle(goodColor);
        }

        if (photoRef.getText().equals(""))
        {
            photoRef.setStyle(errorColor);
            returnValue = false;
        }
        else
        {
            photoRef.setStyle(goodColor);
        }

        if (ref.getText().equals(""))
        {
            ref.setStyle(errorColor);
            returnValue = false;
        }
        else
        {
            ref.setStyle(goodColor);
        }

        // Проверка формата ввода (ЧЧ:ММ)
        try
        {
            if (!time.getText().equals(""))
            {
                Time.valueOf(time.getText() + ":00");
            }
            time.setStyle(goodColor);
        }
        catch (IllegalArgumentException e)
        {
            time.setStyle(errorColor);
            returnValue = false;
        }

        return returnValue;
    }

    private void resetFields()
    {
        String goodColor = "-fx-background-color: white;";
        name.setStyle(goodColor);
        name.setText("");
        description.setStyle(goodColor);
        description.setText("");
        ref.setStyle(goodColor);
        ref.setText("");
        photoRef.setStyle(goodColor);
        photoRef.setText("");
        date.setValue(LocalDate.now());
        time.setStyle(goodColor);
        time.setText("");
        place.setStyle(goodColor);
        place.setText("");

        if (university.getValue() == null)
        {
            university.setValue(university.getItems().get(0));
        }
        if (type.getValue() == null)
        {
            type.setValue(type.getItems().get(0));
        }
    }
}