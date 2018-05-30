package app.front;

import app.back.Event;
import app.back.database.EnumTable;
import app.back.database.EventsTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class EventsController
{
    private EditController editController;
    private short refreshed;

    @FXML private ChoiceBox<String> choiceBox;
    @FXML private TableView<Event> tableView;
    @FXML private TableColumn<Event, String> nameColumn;
    @FXML private TableColumn<Event, String> statusColumn;

    @FXML
    void initialize()
    {
        // Добавление списка университетов
        LinkedList<String> universities = EnumTable.getUniversities();
        choiceBox.getItems().addAll(universities);

        // Инициализация колонок таблицы
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        tableView.setEditable(true);
    }

    public void downloadEvents(ActionEvent actionEvent)
    {
        List<Event> events = EventsTable.getEvents(choiceBox.getValue());
        ObservableList<Event> eventsData = FXCollections.observableArrayList(events);

        tableView.setItems(eventsData);
    }


    public void tuneEvent(TableColumn.CellEditEvent cellEditEvent)
    {
        if (refreshed != 0)
        {
            refreshed--;
            return;
        }

        if (editController == null)
        {
            try
            {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/edit.fxml"));
                Parent parent = loader.load();
                Stage stage = new Stage();
                stage.setTitle("Мероприятие");
                stage.setMinWidth(365);
                stage.setMinHeight(450);
                Scene scene = new Scene(parent);
                stage.setScene(scene);
                stage.initModality(Modality.APPLICATION_MODAL);

                editController = loader.getController();
                editController.setStage(stage);
                EditController.eventsController = this;

                stage.setOnCloseRequest(event ->
                {
                    editController.setSize(stage.getWidth(), stage.getHeight());
                    stage.hide();
                });
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        Event event = (Event)cellEditEvent.getRowValue();
        editController.showWindow(event);
    }

    void updateTable()
    {
        tableView.refresh();
        refreshed = 2;
    }
}