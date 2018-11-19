package javafx;

import java.awt.Desktop;
import java.net.URL;
import java.util.ResourceBundle;
import core.config.Config;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;

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
    private void handleAboutAction (final ActionEvent event) throws Exception
    {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("ZMark");
        alert.setHeaderText(null);
        alert.setContentText("ZMark-PDF书签生成器\n\n@2018-2025 nizouba.com 版权所有");
        alert.showAndWait();
    }

    @FXML
    private void handleOnlineManualAction (final ActionEvent event) throws Exception
    {
        Desktop.getDesktop().browse(new URL("https://nizouba.com/tags/ZMark").toURI());

    }

    @FXML
    private void handleOpenAction (final ActionEvent event) throws Exception
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("打开PDF文件");
        fileChooser.getExtensionFilters().add( new FileChooser.ExtensionFilter("PDF", "*.pdf"));
        Config.pdfFile = fileChooser.showOpenDialog(menuBar.getScene().getWindow());
    }

    /**
     * Handle action related to input (in this case specifically only responds to
     * keyboard event CTRL-A).
     *
     * @param event Input event.
     */
    @FXML
    private void handleKeyInput(final InputEvent event)
    {
        if (event instanceof KeyEvent)
        {
            final KeyEvent keyEvent = (KeyEvent) event;
            if (keyEvent.isControlDown() && keyEvent.getCode() == KeyCode.A)
            {
                provideAboutFunctionality();
            }
        }
    }

    /**
     * Perform functionality associated with "About" menu selection or CTRL-A.
     */
    private void provideAboutFunctionality()
    {
        System.out.println("You clicked on About!");
    }


    @Override
    public void initialize(java.net.URL arg0, ResourceBundle arg1) {
        menuBar.setFocusTraversable(true);

    }
}
