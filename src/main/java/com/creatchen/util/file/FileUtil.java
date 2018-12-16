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
        return getFileCharset(f);
    }

    public static String getFileCharset(File file) {
        int ef = -17;
        int bb = -69;
        int bf = -65;
        String charset = Chartset.GBK;
        try {
            InputStream ios = new FileInputStream(file);
            byte[] b = new byte[3];
            ios.read(b);
            ios.close();
            if (b[0] == ef && b[1] == bb && b[2] == bf) {
                charset = Chartset.UTF_8;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return charset;
    }
}
