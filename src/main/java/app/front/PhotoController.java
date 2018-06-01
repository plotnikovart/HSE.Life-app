package app.front;

import app.back.Photo;

import app.back.database.EnumTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

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

        List<Photo> photos = EnumTable.getPhotos();
        ObservableList<Photo> photosData = FXCollections.observableArrayList(photos);

        refColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        table.setItems(photosData);
    }

    public void changeRef(TableColumn.CellEditEvent<Photo, String> photoStringCellEditEvent)
    {
        Photo currentPhoto = table.getSelectionModel().getSelectedItem();
        String newRef = photoStringCellEditEvent.getNewValue();

        currentPhoto.setRef(newRef);
        EnumTable.updatePhoto(currentPhoto);
    }
}
