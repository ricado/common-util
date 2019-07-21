package com.creatchen.util.file;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author creatchen
 * @version 1.0
 * @date 2018/12/19
 */
@Slf4j
public class ImageUtil {

    public static final String IMAGE_SIZE = "IMAGE_SIZE";
    public static final String IMAGE_WIDTH = "IMAGE_WIDTH";
    public static final String IMAGE_HEIGHT = "IMAGE_HEIGHT";


    public static Map<String, Object> getImageInfo(String filePath) {

        long startTime = System.currentTimeMillis();
        Map<String, Object> imageInfoMap = new HashMap<>(8);
        InputStream is = null;
        try {
            File file = new File(filePath);
            is = new FileInputStream(file);
            BufferedImage image = ImageIO.read(is);
            imageInfoMap.put(IMAGE_SIZE, file.length());
            imageInfoMap.put(IMAGE_WIDTH, image.getWidth());
            imageInfoMap.put(IMAGE_HEIGHT, image.getHeight());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close(); // 关闭流
                } catch (IOException e) {
                    log.error("getImageInfo I/O exception " + e.getMessage(), e);
                }
            }
        }
        long endTime = System.currentTimeMillis();
        log.info("take time: " + (endTime-startTime));
        return imageInfoMap;
    }

    public static void mergeImage(String fileName,boolean isHorizontal,List<String> fileNames) throws IOException {
        if(CollectionUtils.isEmpty(fileNames)){
            return;
        }
        BufferedImage bufferedImage = mergeImage(isHorizontal,fileNames);
        File outputfile = new File(fileName);
        ImageIO.write(bufferedImage, "png", outputfile);
    }

    public static BufferedImage mergeImage(boolean isHorizontal,List<String> fileNames) throws IOException {
        if(CollectionUtils.isEmpty(fileNames)){
           return null;
        }
        List<BufferedImage> images = new ArrayList<>();
        for (String fileName : fileNames) {
            BufferedImage image = ImageIO.read(new File(fileName));
            images.add(image);
        }
        return mergeImageByBufferedImage(isHorizontal,images);
    }

    /**
     * 合并任数量的图片成一张图片
     *
     * @param isHorizontal
     *            true代表水平合并，fasle代表垂直合并
     * @param imgs
     *            待合并的图片数组
     * @return
     * @throws IOException
     */
    private static BufferedImage mergeImageByBufferedImage(boolean isHorizontal, List<BufferedImage> imgs) throws IOException {
        // 生成新图片
        BufferedImage destImage = null;
        // 计算新图片的长和高
        int allw = 0, allh = 0, allwMax = 0, allhMax = 0;
        // 获取总长、总宽、最长、最宽
        for (BufferedImage img : imgs) {
            allw += img.getWidth();
            allh += img.getHeight();
            if (img.getWidth() > allwMax) {
                allwMax = img.getWidth();
            }
            if (img.getHeight() > allhMax) {
                allhMax = img.getHeight();
            }
        }
        // 创建新图片
        if (isHorizontal) {
            destImage = new BufferedImage(allw, allhMax, BufferedImage.TYPE_INT_RGB);
        } else {
            destImage = new BufferedImage(allwMax, allh, BufferedImage.TYPE_INT_RGB);
        }
        // 合并所有子图片到新图片
        int wx = 0, wy = 0;
        for (BufferedImage img : imgs) {
            int w1 = img.getWidth();
            int h1 = img.getHeight();
            // 从图片中读取RGB
            int[] imageArrayOne = new int[w1 * h1];
            // 逐行扫描图像中各个像素的RGB到数组中
            imageArrayOne = img.getRGB(0, 0, w1, h1, imageArrayOne, 0, w1);
            if (isHorizontal) {
                // 水平方向合并
                // 设置上半部分或左半部分的RGB
                destImage.setRGB(wx, 0, w1, h1, imageArrayOne, 0, w1);
            } else {
                // 垂直方向合并
                // 设置上半部分或左半部分的RGB
                destImage.setRGB(0, wy, w1, h1, imageArrayOne, 0, w1);
            }
            wx += w1;
            wy += h1;
        }
        return destImage;
    }
}
