package com.creatchen.util.file;

import com.creatchen.util.constants.Chartset;
import com.csvreader.CsvReader;
import com.google.common.collect.Lists;
import org.apache.commons.io.input.BOMInputStream;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author creatchen
 * @version 1.0
 * @date 2018/12/8
 */
public class CsvUtils {

    /**
     * 读取csv文件
     *
     * @param file file
     * @return list
     * @throws IOException e
     */
    public static List<Map> readCsvReader(File file) throws IOException {
        CsvReader csvReader = getCsvReader(file);
        csvReader.readHeaders();
        String[] headers = csvReader.getHeaders();
        List headerList = Lists.newArrayList(headers);
        return readCsvReader(csvReader, headerList);
    }

    /**
     * 读取csv文件,获取指定的列数据
     *
     * @param csvReader csvReader
     * @param headers   headers
     * @return list
     * @throws IOException e
     */
    private static List<Map> readCsvReader(CsvReader csvReader, List<String> headers) throws IOException {
        List<Map> dataList = new ArrayList<>();
        while (csvReader.readRecord()) {
            Map<String, Object> data = new HashMap<>(headers.size() * 2);
            for (String header : headers) {
                data.put(header, csvReader.get(header));
            }
            dataList.add(data);
        }
        return dataList;
    }

    /**
     * 读取csv文件,获取列数据
     * @param filePath filePath
     * @return list
     * @throws IOException IOException
     */
    public static List<Map> readCsvReader(String filePath) throws IOException {
        return readCsvReader(new File(filePath));
    }

    /**
     * 获取csv文件数据
     *
     * @param filePath filePath
     * @return CsvReader
     * @throws FileNotFoundException        FileNotFoundException
     * @throws UnsupportedEncodingException UnsupportedEncodingException
     */
    public static CsvReader getCsvReader(String filePath)
            throws FileNotFoundException, UnsupportedEncodingException {
        File csvfile = new File(filePath);
        return getCsvReader(csvfile);
    }

    /**
     * 获取csv文件对象
     *
     * @param file file
     * @return CsvReader
     * @throws FileNotFoundException        FileNotFoundException
     * @throws UnsupportedEncodingException UnsupportedEncodingException
     */
    public static CsvReader getCsvReader(File file)
            throws FileNotFoundException, UnsupportedEncodingException {
        String charsetName = FileUtil.getFileCharset(file);
        InputStream inputStream = new FileInputStream(file);
        if (charsetName.equals(Chartset.UTF_8)) {
            inputStream = new BOMInputStream(inputStream);
        }
        InputStreamReader isr = new InputStreamReader(inputStream, charsetName);
        return new CsvReader(isr);
    }
}
