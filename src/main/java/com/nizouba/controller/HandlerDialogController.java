package com.nizouba.controller;

import com.nizouba.Starter;
import com.nizouba.consts.RegexConsts;
import com.nizouba.core.ExtractRule;
import com.nizouba.core.LevelMode;
import com.nizouba.core.LevelMode.LevelModeEnum;
import com.nizouba.core.config.Config;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

/**
 * 打开文件之后的弹窗，显示处理选项
 *
 * @author zhangweixiao
 */
public class HandlerDialogController implements Initializable {

    @FXML
    private Label openLabel;

    @FXML
    private ComboBox extractCombox;

    @FXML
    private ComboBox levelCombox;

    @FXML
    private ComboBox bodyFontCombox;

    @FXML
    private TextField bodyFontField;

    @FXML
    private TextField levelField;

    @FXML
    private TextField extractField;

    private String textField = "[0-9.]+";

    @Setter
    private String fileName;


    @FXML
    private void extractComboxChanged(final ActionEvent event) {
        ComboBox combox = (ComboBox) event.getSource();
        int selectIndex = extractCombox.getSelectionModel().getSelectedIndex();
        extractField.setVisible(selectIndex == 1);
    }

    @FXML
    private void levelComboxChanged(final ActionEvent event) {
        ComboBox combox = (ComboBox) event.getSource();
        int selectIndex = levelCombox.getSelectionModel().getSelectedIndex();
        levelField.setVisible(selectIndex != 0);
    }

    @FXML
    private void bodyFontComboxChanged(final ActionEvent event) {
        ComboBox bodyFontCombox = (ComboBox) event.getSource();
        int selectIndex = bodyFontCombox.getSelectionModel().getSelectedIndex();
        bodyFontField.setVisible(selectIndex == 1);
    }

    @FXML
    private void handleConfirmAction(final ActionEvent event) throws Exception {
        if (bodyFontField.isVisible()) {
            String bodyFontSize = bodyFontField.getText();
            try {
                Config.bodySize = Float.valueOf(bodyFontSize);
            } catch (NumberFormatException e) {
                alertBodyFont();
                return;
            }
        }
        ExtractRule extractRule = new ExtractRule();
        if (extractCombox.getSelectionModel().getSelectedIndex() == 0) {
            extractRule.setAddMarkRegex(false);
        } else {
            extractRule.setAddMarkRegex(true);
            extractRule.setExtractRegex(extractField.getText());
        }
        Config.extractRule = extractRule;
        LevelMode levelMode = new LevelMode();
        int levelSelectedIndex = levelCombox.getSelectionModel().getSelectedIndex();
        if (levelSelectedIndex == 0) {
            levelMode.setLevelModeEnum(LevelMode.LevelModeEnum.FontSizeMode);
        } else if (levelSelectedIndex == 1) {
            levelMode.setLevelModeEnum(LevelModeEnum.ChapterMode);
            levelMode.setLevelRegex(levelField.getText());
        } else {
            levelMode.setLevelModeEnum(LevelModeEnum.MixMode);
            levelMode.setLevelRegex(levelField.getText());
        }
        Config.levelMode = levelMode;
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
        startHandle();
    }

    private void startHandle(){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(()->{
            try {
                String[] args = new String[]{"-f", Config.pdfFile.getPath()};
                Starter.main(args);
            }catch (Exception e){
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("出错了");
                alert.setHeaderText(null);
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        });

    }

    private void alertBodyFont() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("输入错误");
        alert.setHeaderText(null);
        alert.setContentText("请输入正确的字体大小");
        alert.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bodyFontField.focusedProperty().addListener((ov, oldV, newV) -> {
            if (!newV) {
                if (!bodyFontField.getText().matches(textField)) {
                    alertBodyFont();
                }
            }
        });
        bodyFontCombox.getSelectionModel().selectFirst();
        openLabel.setText(fileName);
        openLabel.setTooltip(new Tooltip(fileName));
        extractCombox.getItems().addAll("字号模式", "字号章节模式");
        extractCombox.getSelectionModel().select(1);
        extractField.setText(RegexConsts.BOOKMARK_START_REGEX);
        levelCombox.getItems().addAll("字号模式", "章节模式", "字号章节模式");
        levelCombox.getSelectionModel().select(1);
        levelField.setText(RegexConsts.LEVEL_REGEX);
    }
}
