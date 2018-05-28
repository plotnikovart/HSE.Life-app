package app.front;

import app.back.Event;
import app.back.database.EnumTable;
import app.back.database.EventsTable;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


import java.time.LocalDate;
import java.util.LinkedList;
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

    void showWindow(Event event)
    {
        currentEvent = event;

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
            time.setText(timeS);
        }

        String placeS = event.getParam(8);
        place.setText(Objects.requireNonNullElse(placeS, ""));

        allowFlag.setSelected(false);
        deleteFlag.setSelected(false);

        stage.setWidth(currentWidth);
        stage.setHeight(currentHeight);
        stage.show();
    }

    public void showPost(MouseEvent mouseEvent)
    {
        mainPage.getHostServices().showDocument(ref.getText());
    }

    public void showPhoto(MouseEvent mouseEvent)
    {
        mainPage.getHostServices().showDocument(photoRef.getText());
    }

    public void cancel(MouseEvent mouseEvent)
    {
        currentWidth = stage.getWidth();
        currentHeight = stage.getHeight();

        stage.hide();
    }

    public void save(MouseEvent mouseEvent)
    {
        if (deleteFlag.isSelected())
        {
            //EventsTable.deleteEvent(currentEvent);
            currentEvent.setStatus("Удалено");
            eventsController.tableView.refresh();
        }

        if (allowFlag.isSelected())
        {
            System.out.println(2);
        }

        stage.hide();
    }
}