package com.nizouba.controller;

import com.nizouba.config.LastConfig;
import java.awt.Desktop;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import com.nizouba.core.config.Config;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController implements Initializable {

    @FXML
    private MenuBar menuBar;

    @FXML
    private ProgressBar progressBar;

    /**
     * Handle action related to "About" menu item.
     *
     * @param event Event on "About" menu item.
     */
    @FXML
    private void handleAboutAction(final ActionEvent event) throws Exception {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("ZMark");
        alert.setHeaderText(null);
        alert.setContentText("ZMark-PDF书签生成器\n\n@2018-2025 nizouba.com 版权所有");
        alert.showAndWait();
    }

    @FXML
    private void handleOnlineManualAction(final ActionEvent event) throws Exception {
        Desktop.getDesktop().browse(new URL("https://nizouba.com/tags/ZMark").toURI());

    }


    @FXML
    private void handleOpenAction(final ActionEvent event) {
        String lastPath = LastConfig.lastPath;
        FileChooser fileChooser = new FileChooser();

        if (lastPath != null) {
            File initDir = new File(lastPath);
            if (initDir.exists() && initDir.isDirectory()) {
                fileChooser.setInitialDirectory(initDir);
            }
        }
        fileChooser.setTitle("打开PDF文件");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
        Config.pdfFile = fileChooser.showOpenDialog(menuBar.getScene().getWindow());
        if (Config.pdfFile != null) {
            //显示处理窗口
            FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getClassLoader().getResource("fxml/handlePannel.fxml"));
            ShareData.shareData.put(ShareData.PDF_FILE_NAME, null);
            HandlerDialogController handlerDialogController = new HandlerDialogController();
            handlerDialogController.setFileName(Config.pdfFile.getName());
            fxmlLoader.setController(handlerDialogController);
            //传递变量
            Parent parent = null;
            try {
                parent = fxmlLoader.load();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            Scene scene = new Scene(parent, 400, 300);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.resizableProperty().setValue(Boolean.FALSE);
            stage.showAndWait();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
