package sample;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage){
        new MainGame().run();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
