package com.nizouba;

import com.itextpdf.text.pdf.PdfReader;
import com.nizouba.consts.Consts;
import com.nizouba.controller.MainController;
import com.nizouba.core.config.Config;
import com.nizouba.dto.BookmarkWithLevel;
import com.nizouba.utils.PdfUtils;

import java.io.File;
import java.util.List;
import javafx.application.Platform;

/**
 * @author zhangweixiao
 */
public class Starter {

    public static void main(String[] args) throws Exception {
        File srcFile = Config.configProperties.getPdfFile();
        String dest =
                srcFile.getParent() + "\\" + srcFile.getName().replaceAll("\\.pdf", "").concat(
                        Consts.MARK_TEXT).concat(".pdf");

        PdfReader reader = new PdfReader(srcFile.getPath());
        List<BookmarkWithLevel> bookmarkWithLevels = PdfUtils.getBookmarkWithLevel(reader);
        //写入书签
        PdfUtils.createBookmarks(bookmarkWithLevels, reader, dest);
        System.out.println("添加书签成功");
        //完成
        Platform.runLater(() -> MainController.progressValue.set(1f));
    }
}
