package app.front;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
        Parent parent = FXMLLoader.load(getClass().getResource("/main.fxml"));
        primaryStage.setTitle("HSE.Life app");
        primaryStage.setMinWidth(300);
        primaryStage.setMinHeight(300);
        Scene scene = new Scene(parent);
        primaryStage.setScene(scene);

        primaryStage.show();
    }
}