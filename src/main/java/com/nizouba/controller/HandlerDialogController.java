package com.nizouba.controller;

import com.alibaba.fastjson.JSON;
import com.nizouba.Starter;
import com.nizouba.config.LastConfig;
import com.nizouba.consts.Consts;
import com.nizouba.core.BodySizeMode;
import com.nizouba.core.BodySizeMode.BodySizeEnum;
import com.nizouba.core.ExtractRule;
import com.nizouba.core.LevelMode;
import com.nizouba.core.LevelMode.LevelModeEnum;
import com.nizouba.core.config.Config;
import com.nizouba.event.FxEventBus;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;

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

    @FXML
    private ComboBox compareCombox;

    private String textField = "[0-9.]+";

    @FXML
    private void extractComboxChanged(final ActionEvent event) {
        ComboBox combox = (ComboBox) event.getSource();
        int selectIndex = extractCombox.getSelectionModel().getSelectedIndex();
        if(selectIndex == 1){
            extractField.setVisible(true);
            extractField.setText(Consts.BOOKMARK_START_REGEX);
        }else {
            extractField.setVisible(false);
        }
    }

    @FXML
    private void levelComboxChanged(final ActionEvent event) {
        ComboBox combox = (ComboBox) event.getSource();
        int selectIndex = levelCombox.getSelectionModel().getSelectedIndex();
        if(selectIndex!=0){
            levelField.setVisible(true);
            levelField.setText(Consts.LEVEL_REGEX);
        }else {
            levelField.setVisible(false);
        }
    }

    @FXML
    private void bodyFontComboxChanged(final ActionEvent event) {
        ComboBox bodyFontCombox = (ComboBox) event.getSource();
        int selectIndex = bodyFontCombox.getSelectionModel().getSelectedIndex();
        bodyFontField.setVisible(selectIndex == 1);
    }

    @FXML
    private void handleConfirmAction(final ActionEvent event) throws Exception {
        //保存选择的项
        LastConfig.updateProperties("lastConfig", JSON.toJSONString(Config.configProperties));
        //正文字体选择
        int bodySizeIndex = bodyFontCombox.getSelectionModel().getSelectedIndex();
        BodySizeMode bodySizeMode = new BodySizeMode();
        if (bodySizeIndex == 0) {
            bodySizeMode.setBodySizeEnum(BodySizeEnum.AI);
        } else {
            bodySizeMode.setBodySizeEnum(BodySizeMode.BodySizeEnum.CUSTOM);
            String bodyFontSize = bodyFontField.getText();
            try {
                bodySizeMode.setBodySize(Float.valueOf(bodyFontSize));
            } catch (NumberFormatException e) {
                alertBodyFont();
                return;
            }
        }
        Config.configProperties.setBodySizeMode(bodySizeMode);
        //标签字体对照
        Config.configProperties.setCompareSelect(compareCombox.getSelectionModel().getSelectedIndex());
        ExtractRule extractRule = new ExtractRule();
        if (extractCombox.getSelectionModel().getSelectedIndex() == 0) {
            extractRule.setAddMarkRegex(false);
        } else {
            extractRule.setAddMarkRegex(true);
            extractRule.setExtractRegex(extractField.getText());
        }
        Config.configProperties.setExtractRule(extractRule);
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
        Config.configProperties.setLevelMode(levelMode);
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
        LastConfig.updateProperties("lastConfig",JSON.toJSONString(Config.configProperties));
        startHandle();
    }

    private void startHandle() {
        FxEventBus.eventBus.post(true);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            try {
                Starter.main(null);
            } catch (Exception e) {
                Platform.runLater(()->{
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("出错了");
                    alert.setHeaderText(null);
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                });
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
        bodyFontCombox.getSelectionModel().select(Config.configProperties.getBodySizeMode().getBodySizeEnum().getIndex());
        openLabel.setText(Config.configProperties.getPdfFile().getName());
        openLabel.setTooltip(new Tooltip(Config.configProperties.getPdfFile().getName()));
        bodyFontCombox.getSelectionModel().select(Config.configProperties.getBodySizeMode().getBodySizeEnum().getIndex());
        if(Config.configProperties.getBodySizeMode().getBodySizeEnum().equals(BodySizeEnum.CUSTOM)){
            bodyFontField.setVisible(true);
            bodyFontField.setText(String.valueOf(Config.configProperties.getBodySizeMode().getBodySize()));
        }
        extractCombox.getItems().addAll("字号模式", "字号章节模式");
        extractCombox.getSelectionModel().select(Config.configProperties.getExtractRule().isAddMarkRegex()?1:0);
        if(Config.configProperties.getExtractRule().addMarkRegex){
            extractField.setVisible(true);
            extractField.setText(Consts.BOOKMARK_START_REGEX);
        }else {
            extractField.setVisible(false);
        }
        levelCombox.getItems().addAll("字号模式", "章节模式", "字号章节模式");
        levelCombox.getSelectionModel().select(Config.configProperties.getLevelMode().getLevelModeEnum().getCode());
        if(Config.configProperties.getLevelMode().getLevelModeEnum().equals(LevelModeEnum.FontSizeMode)){
            levelField.setVisible(false);
        }else {
            levelField.setVisible(true);
            levelField.setText(Consts.LEVEL_REGEX);
        }
        compareCombox.getItems().addAll("首字字号","最大字号");
        compareCombox.getSelectionModel().select(Config.configProperties.getCompareSelect());
    }
}
