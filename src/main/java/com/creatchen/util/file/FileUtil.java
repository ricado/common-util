package com.creatchen.util.file;

import com.creatchen.util.constants.Chartset;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author creatchen
 * @version 1.0
 * @date 2018/12/8
 */
public class FileUtil {

    /**
     * 读取文件输入流
     *
     * @param filepath filepath
     * @return InputStream
     */
    public static InputStream readInputStrean(String filepath) throws FileNotFoundException, UnsupportedEncodingException {
        return readInputStream(new File(filepath));
    }

    /**
     * 读取文件输入流
     *
     * @param file file
     * @return InputStream
     */
    public static InputStream readInputStream(File file) throws FileNotFoundException, UnsupportedEncodingException {
        InputStreamReader inputStream = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
        return null;
    }


    public static String getFileCharset(String filepath) {
        File f = new File(filepath);
        int EF = -17;
        int BB = -69;
        int BF = -65;
        String charset = Chartset.GBK;
        try {
            InputStream ios = new FileInputStream(f);
            byte[] b = new byte[3];
            ios.read(b);
            ios.close();
            if (b[0] == EF && b[1] == BB && b[2] == BF) {
                charset = Chartset.UTF_8;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return charset;
    }

    public static void main(String[] args) {
        String filepath1 = "F:\\test\\UTF-8_BOM.txt";
        String filepath2 = "F:\\test\\UTF-8.txt";
        String filepath3 = "F:\\test\\GBK.txt";


        System.out.println(getFileCharset(filepath1));
        System.out.println(getFileCharset(filepath2));
        System.out.println(getFileCharset(filepath3));

    }


}
