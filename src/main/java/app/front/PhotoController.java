package app.front;

import app.back.Event;
import app.back.Photo;
import app.back.database.EventsTable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.LinkedList;
import java.util.List;

public class PhotoController
{
    @FXML public TableView<Photo> table;
    @FXML public TableColumn<Photo, String> dayColumn;
    @FXML public TableColumn<Photo, String> refColumn;

    @FXML
    void initialize()
    {
        // Инициализация колонок таблицы
        dayColumn.setCellValueFactory(new PropertyValueFactory<>("day"));
        refColumn.setCellValueFactory(new PropertyValueFactory<>("ref"));

        table.setEditable(true);

        List<Photo> photos = new LinkedList<>();
        photos.add(new Photo());
        ObservableList<Photo> photosData = FXCollections.observableArrayList(photos);

        table.setItems(photosData);
    }
}
