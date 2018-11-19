import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("ZMark-PDF书签生成工具");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.getIcons().add(new Image("nizouba.jpg"));

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
