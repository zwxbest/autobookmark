package com.nizouba;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainPannel extends javafx.application.Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/mainPannel.fxml"));
        primaryStage.setTitle("ZMark-PDF书签生成工具");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.resizableProperty().setValue(Boolean.FALSE);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
