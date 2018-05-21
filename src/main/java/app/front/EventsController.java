package app.front;

import app.back.Event;
import app.back.database.EnumTable;
import app.back.database.EventsTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.util.LinkedList;
import java.util.List;

public class EventsController
{
    private ObservableList<Event> eventsData;

    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private TableView<Event> tableView;
    @FXML
    private TableColumn<Event, String> nameColumn;
    @FXML
    private TableColumn<Event, String> statusColumn;

    @FXML
    private void initialize()
    {
        // Добавление списка университетов
        LinkedList<String> universities = EnumTable.getUniversities();
        choiceBox.getItems().addAll(universities);

        // Инициализация колонок таблицы
        nameColumn.setCellValueFactory(new PropertyValueFactory<Event, String>("name"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<Event, String>("status"));

        tableView.setEditable(true);
    }


    // todo добавить время

    public void downloadEvents(ActionEvent actionEvent)
    {
        List<String> names = EventsTable.getEventsNames(choiceBox.getValue());
        eventsData = FXCollections.observableArrayList();

        for (String name : names)
        {
            eventsData.add(new Event(name, "Ожидает"));
        }

        tableView.setItems(eventsData);
    }


    public void tuneEvent(TableColumn.CellEditEvent cellEditEvent)
    {
        Event event = (Event)cellEditEvent.getRowValue();
        System.out.println("sd");
        System.out.println(event.getName());
    }
}
