package com.nizouba.utils;

import com.google.common.collect.Lists;
import com.google.common.io.Resources;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.itextpdf.text.pdf.parser.TextRenderInfo;
import com.itextpdf.text.pdf.parser.Vector;
import com.nizouba.consts.Consts;
import com.nizouba.controller.MainController;
import com.nizouba.core.BodySizeMode.BodySizeEnum;
import com.nizouba.core.BookmarkFilterHandler;
import com.nizouba.core.LevelMode.LevelModeEnum;
import com.nizouba.core.config.Config;
import com.nizouba.dto.BookmarkWithLevel;
import com.nizouba.itext.LineTextExtractionStrategy;
import com.nizouba.itext.LineTextPros;

import com.nizouba.level.FontSizeConverter;
import com.nizouba.level.LevelConverter;
import com.nizouba.level.LevelRegexConveter;
import com.nizouba.level.RegexFontLevelConverter;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.*;

import com.nizouba.strategy.TextExtractionStrategyFindBodySize;
import javafx.application.Platform;

/**
 * @author zhangweixiao
 */
public class PdfUtils {

    /**
     * 获取书签的List，包含FontSize,后续用来分层
     */
    public static List<BookmarkWithLevel> getBookmarkWithLevel(PdfReader reader)
        throws Exception {
        float bodySize;
        if (!Config.configProperties.getBodySizeMode().getBodySizeEnum().equals(BodySizeEnum.CUSTOM)) {
            bodySize = getBodyFontSize(reader);
        } else {
            bodySize = Config.configProperties.getBodySizeMode().getBodySize();
        }
        //总页数
        int numberOfPages = reader.getNumberOfPages();
        List<LineTextPros> filteredLineTextPros = Lists.newArrayList();
        for (int i = 1; i <= numberOfPages; i++) {
            //存储每页中的所有行
            List<LineTextPros> lineTextProsList = Lists.newArrayList();
            LineTextExtractionStrategy bookmarkExtractionStrategy = new LineTextExtractionStrategy(
                lineTextProsList, i);
            PdfTextExtractor.getTextFromPage(reader, i, bookmarkExtractionStrategy);
            //处理书签业务逻辑
            BookmarkFilterHandler handler = new BookmarkFilterHandler(lineTextProsList);
            filteredLineTextPros.addAll(handler.filter(lineTextPros -> {
                //是根据首字号还是最大字号
                boolean b =
                    Config.configProperties.getCompareSelect() == 0 ? lineTextPros.getFirstBlockFontSize() > bodySize
                        : lineTextPros.getMaxFontSize() > bodySize;
                if (Config.configProperties.getExtractRule().addMarkRegex) {
                    b &= lineTextPros.getLineText().matches(Consts.BOOKMARK_START_REGEX);
                }
                return b;
            }));
            //todo：调用progressBar发送进度
            float progress = Math.round(i / (float) (numberOfPages) * 100) / 100f - 0.01f;
            Platform.runLater(() -> MainController.progressValue.set(progress));
        }
        LevelConverter levelConverter;
        if (Config.configProperties.getLevelMode().getLevelModeEnum().equals(LevelModeEnum.FontSizeMode)) {
            levelConverter = new FontSizeConverter();
        } else if (Config.configProperties.getLevelMode().getLevelModeEnum().equals(LevelModeEnum.ChapterMode)) {
            levelConverter = new LevelRegexConveter();
        } else {
            levelConverter = new RegexFontLevelConverter();
        }
        return levelConverter.convertFontSize2Leve(filteredLineTextPros);
    }

    /**
     * 获取正文的文字大小，超过5行并且都是同样大小字体
     */
    private static float getBodyFontSize(PdfReader reader) throws Exception {
        //总页数
        int numberOfPages = reader.getNumberOfPages();
        //按照字体从大到小排列
        Map<Float, Integer> fontSizeCountMap = new TreeMap<>((a, b) -> -a.compareTo(b));
        //获取文字大小的策略
        TextExtractionStrategyFindBodySize strategyWithSize = new TextExtractionStrategyFindBodySize(
            fontSizeCountMap);
        //取5张作为样本
        for (int i = 0; i < 5; i++) {
            //跳过第一页
            int randomPage = new Random().nextInt(numberOfPages - 1) + 1;
            PdfTextExtractor.getTextFromPage(reader, randomPage, strategyWithSize);
        }
        Integer sumCount = fontSizeCountMap.values().stream().reduce(0, Integer::sum);
        //正文字体大小
        float bodyFontSize = -1f;
        for (Float key : fontSizeCountMap.keySet()) {
            float count = fontSizeCountMap.get(key);
            if (count / sumCount > 0.02) {
                bodyFontSize = key;
                break;
            }
        }
        return bodyFontSize;
    }

    public static void createBookmarks(List<BookmarkWithLevel> booksmarks, PdfReader reader,
        String dest) throws Exception {

        Document document = new Document();
        PdfCopy copy = new PdfCopy(document, new FileOutputStream(dest));
        document.open();

        PdfOutline root = copy.getRootOutline();

        copy.addDocument(reader);

        PdfAction action;
        copy.freeReader(reader);
        Map<BookmarkWithLevel, PdfOutline> maps = new HashMap<>();
        PdfOutline outline = null;
        PdfOutline parent = root;
        for (BookmarkWithLevel bookMark : booksmarks) {
            //*1.1f是想上面露一点，要不太贴着文字了。
            action = PdfAction.gotoLocalPage(bookMark.getPageNum(), new PdfDestination(
                PdfDestination.XYZ, -1, bookMark.getYOffset() * 1.05f, 0), copy);
            if (bookMark.getParent() != null) {
                parent = maps.get(bookMark.getParent());
            } else {
                parent = root;
            }
            outline = new PdfOutline(parent, action, bookMark.getTitle(), false);
            maps.put(bookMark, outline);
        }
        reader.close();
        copy.flush();
        copy.close();
        document.close();

    }

    /**
     * 生成书签之后进行过滤
     *
     * @param bookmarks x
     * @param regex x
     * @throws Exception x
     */
    public static void converterBookmarks(List<HashMap<String, Object>> bookmarks, String regex)
        throws Exception {

        List<HashMap<String, Object>> bookmarkToRemove = Lists.newArrayList();
        for (HashMap<String, Object> bookmark : bookmarks) {
            if (bookmark.get("Title").toString().matches(regex)) {
                bookmarkToRemove.add(bookmark);
            }
        }
        bookmarks.removeAll(bookmarkToRemove);
    }

    public static void genWaterMark(String inputFile, String outputFile, String imageFile) {
        try {
            PdfReader reader = new PdfReader(inputFile);
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(
                outputFile));
            int total = reader.getNumberOfPages() + 1;
            URL resource = Resources.getResource(imageFile);
            Image image = Image.getInstance(resource);

            int totalPage = reader.getNumberOfPages();
            float imageWith = reader.getPageSize(30).getWidth() * 0.12f;
            image.scaleToFit(imageWith, imageWith);
            // 图片位置

            PdfContentByte over;
            PdfGState gs1 = new PdfGState();
            for (int i = 1; i < total; i++) {
                image.setAbsolutePosition(
                    reader.getPageSize(i).getWidth() - image.getPlainWidth() * 1.1f,
                    0 + image.getPlainHeight() * 0.1f);
                over = stamper.getOverContent(i);
                over.setGState(gs1);
                over.addImage(image);
            }
            stamper.close();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取字体大小
     *
     * @param renderInfo x
     * @return 保留两位小数
     */
    public static float getFontSize(TextRenderInfo renderInfo) {
        //底线位置
        float yDesc = renderInfo.getDescentLine().getStartPoint().get(Vector.I2);
        //顶线位置
        float yTop = renderInfo.getAscentLine().getEndPoint().get(Vector.I2);
        float fontSize = yTop - yDesc;
        return Math.round(fontSize * 100) / 100f;
    }

}
