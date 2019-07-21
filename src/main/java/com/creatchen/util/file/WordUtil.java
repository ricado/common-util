package com.creatchen.util.file;

import org.apache.commons.collections4.MapUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author creatchen
 * @version 1.0
 * @date 2018/12/19
 */
public class WordUtil {

    public static void main(String[] args) throws IOException {

        List<String> fileNames = new ArrayList<>();
        fileNames.add("F:\\test\\报告_1.png");
        fileNames.add("F:\\test\\报告_2.png");
        fileNames.add("F:\\test\\报告_3.png");
        String reportFileName = "F:\\test\\报告" + System.currentTimeMillis() + ".png";
//        getReportWordFile(reportFileName, fileNames);
        long startTime = System.currentTimeMillis();
        ImageUtil.mergeImage(reportFileName,false,fileNames);
        long costTime = System.currentTimeMillis() - startTime;
        System.out.println("costTime = " + costTime);
    }

    private static void getReportWordFile(String reportFileName, List<String> fileNames)
            throws IOException {

        CustomXWPFDocument document = new CustomXWPFDocument();
        for (String fileName : fileNames) {
            try {
                FileInputStream fileInputStream = new FileInputStream(fileName);
                Map<String,Object> imageInfo = ImageUtil.getImageInfo(fileName);
//                XWPFPicture xwpfPicture = new XWPFPictureData();
                String ind = document.addPictureData(fileInputStream, XWPFDocument.PICTURE_TYPE_PNG);
                int id = document.getNextPicNameNumber(XWPFDocument.PICTURE_TYPE_PNG);
                int width = MapUtils.getInteger(imageInfo,ImageUtil.IMAGE_WIDTH);
                int height = MapUtils.getInteger(imageInfo,ImageUtil.IMAGE_HEIGHT);
                height = height * 550 / width;
                document.createPicture(ind, id, 550, height, document.createParagraph());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (InvalidFormatException e) {
                e.printStackTrace();
            }
        }
        File newfile = new File(reportFileName);
        FileOutputStream fos = new FileOutputStream(newfile);
        document.write(fos);
        fos.close();
    }
}
