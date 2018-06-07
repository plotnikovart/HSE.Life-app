package app.front;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class MainPage extends Application
{
    public static void start()
    {
        Application.launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Parent parent = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));
        primaryStage.setTitle("HSE.Life app");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));
        primaryStage.setMinWidth(300);
        primaryStage.setMinHeight(300);
        Scene scene = new Scene(parent);
        primaryStage.setScene(scene);
        primaryStage.show();

        EditController.mainPage = this;
    }
}