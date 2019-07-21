package com.creatchen.util.data;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


/**
 * 数据脱敏加密工具类
 * Created by creatchen on 2019/4/22.
 */
public class SensitiveDataEncrypt {

    private static final Logger LOGGER = LoggerFactory.getLogger(SensitiveDataEncrypt.class);

    /**
     * 手机号码正则表达式<p>
     * 注:为减少错杀,只有确定是11为的数字时才进行匹配。即手机号码的前一位字符或后一位不能是数字。
     * $1 : 手机号码前面的一位字符
     * $2 : 前三位,号段<p>
     * $3 : 第二位和第三位,号段<p>
     * $4 : 中间四位<p>
     * $5 : 后四位
     * $6 : 手机号码后面的一位字符
     */
    private static final String PHONE_NUMBER_REGEX = "(^|\\D)(1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\\-?)(\\d{4})(\\-?\\d{4})(\\D|$)";

    /**
     * 身份证号码18位正则表达式<p>
     * $1 : 身份证号码18位前面的一位字符
     * $2 : 前三位 用于脱敏保留显示<p>
     * $3 : 年的前两位 18-39<p>
     * $4 : 月份<p>
     * $5 : 月份的日期 (01-31) 不限制大小月,2月<p>
     * $6 : 后两位 用于脱敏保留显示
     * $7 : 身份证号码18位后面的一位字符
     */
    private static final String ID_NUMBER_18_REGEX =
            "(^|\\D)([0-9]\\d{2})\\d{3}(18|19|[23]\\d)\\d{2}(0[1-9]|10|11|12)([0-2][1-9]|10|20|30|31)\\d{2}(\\d[0-9Xx])(\\D|$)";

    /**
     * 身份证号码15位正则表达式<p>
     * $1 : 身份证号码15位前面的一位字符<p>
     * $2 : 前三位 用于脱敏保留显示<p>
     * $3 : 月份<p>
     * $4 : 月份的日期 (01-31) 不限制大小月,2月<p>
     * $5 : 后两位 用于脱敏保留显示<p>
     * $6 : 身份证号码15位后面的一位字符<p>
     */
    private static final String ID_NUMBER_15_REGEX = "(^|\\D)([1-9]\\d{2})\\d{5}(0[1-9]|10|11|12)([0-2][1-9]|10|20|30|31)\\d(\\d[0-9Xx])(\\D|$)";

    // Pattern类

    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile(PHONE_NUMBER_REGEX);

    private static final Pattern ID_NUMBER_18_PATTERN = Pattern.compile(ID_NUMBER_18_REGEX);

    private static final Pattern ID_NUMBER_15_PATTERN = Pattern.compile(ID_NUMBER_15_REGEX);

    // 替换规则

    private static final String PHONE_NUMBER_REPLACEMENT = "$1$2****$5$6";

    private static final String ID_NUMBER_18_REPLACEMENT = "$1$2*************$6$7";

    private static final String ID_NUMBER_15_REPLACEMENT = "$1**********$4";

    /**
     * 脱敏加密key匹配正则表达式
     */
    private static final String SENSITIVE_DATA_KEY_REGEX = ".*(号码|手机号|备注|电话|身份证|说明|联系|联络|mobile|phone|meno|no|contact).*";

    public static void main(String[] args) {
        String str = "156-1189-7401,156-1189-7401的用户的身份证号码是44512218800101595X,15位的身份证是44512200010159X,重复一下:15611897401的用户的身份证号码是44512219000101595X,15位的身份证是44512200010159X";
        int size = 50000;
        List<String> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(str);
        }
        long startTime = System.currentTimeMillis();
//        for (int i = 0; i < size; i++) {
//            SensitiveDataEncrypt.encrypt(str);
//        }
        list.forEach(SensitiveDataEncrypt::encrypt);
//        list.parallelStream().forEach(SensitiveDataEncrypt::encrypt);
        LOGGER.info(JSONObject.toJSONString(SensitiveDataEncrypt.encrypt(str)));
        long costTime = System.currentTimeMillis() - startTime;
        LOGGER.info("执行 {} 次,耗时 {} ms",size,costTime);
    }

    /**
     * 查找需要脱敏加密的keys
     * @param keys keys
     * @return list
     */
    public static List<String> findSensitiveDataKeys(List<String> keys){
        if(CollectionUtils.isEmpty(keys)){
            return new ArrayList<>();
        }
        return keys.stream().filter(SensitiveDataEncrypt::isSensitiveDataKey).collect(Collectors.toList());
    }

    /**
     * 判断这个key是否需要加密
     * @param key key
     * @return true or false
     */
    public static boolean isSensitiveDataKey(String key){
        return key.matches(SENSITIVE_DATA_KEY_REGEX);
    }

    /**
     * 数据过滤加密
     * 1.身份证号码18位
     * 2.身份证号码15位
     * 3.手机号
     *
     * @param dataList dataList
     */
    public static void dataEncrypt(List<Map> dataList) {
        if (CollectionUtils.isEmpty(dataList)) {
            return;
        }
        dataList.parallelStream().forEach(SensitiveDataEncrypt::dataEncrypt);
    }

    /**
     * 数据过滤加密
     * 1.身份证号码18位
     * 2.身份证号码15位
     * 3.手机号
     *
     * @param dataList dataList
     * @param sensitiveDataKeys sensitiveDataKeys 需要被脱敏加密的数据key
     */
    public static void dataEncrypt(List<Map> dataList,List<String> sensitiveDataKeys) {
        if (CollectionUtils.isEmpty(dataList) || CollectionUtils.isEmpty(sensitiveDataKeys)) {
            return;
        }
        dataList.parallelStream().forEach(map -> dataEncrypt(map,sensitiveDataKeys));
    }

    /**
     * 数据过滤加密
     * 1.身份证号码18位
     * 2.身份证号码15位
     * 3.手机号
     *
     * @param data data
     * @param sensitiveDataKeys sensitiveDataKeys 需要被脱敏加密的数据key
     */
    public static void dataEncrypt(Map<String, Object> data,List<String> sensitiveDataKeys) {
        if (MapUtils.isEmpty(data) || CollectionUtils.isEmpty(sensitiveDataKeys)) {
            return;
        }
        sensitiveDataKeys.stream()
                .filter(key -> data.containsKey(key)) // 挑选包含该key的
                .forEach(key->{ // 脱敏加密
                    Object result = encrypt(MapUtils.getString(data,key,""));
                    data.put(key,result);
                });
    }

    /**
     * 数据过滤加密
     * 1.身份证号码18位
     * 2.身份证号码15位
     * 3.手机号
     *
     * @param data data
     */
    public static void dataEncrypt(Map<String, Object> data) {
        if (MapUtils.isEmpty(data)) {
            return;
        }
        data.forEach((k,v) -> data.put(k,encrypt(v)));
    }

    /**
     * 脱敏加密数据 顺序: 身份证号码18位,身份证号码15位,手机号码。
     * 脱敏加密顺序建议匹配长度最长的优先匹配。
     * 后面有新增的需要脱敏加密的类型可以使用策略类进行脱敏加密
     *
     * @param object object
     * @return object
     */
    private static Object encrypt(Object object) {
        // 脱敏加密
        SensitiveDataEncryptEnum[] sensitiveDataEncryptEnums = SensitiveDataEncryptEnum.values();
        for (SensitiveDataEncryptEnum sensitiveDataEncryptEnum : sensitiveDataEncryptEnums) {
            object = sensitiveDataEncryptEnum.encrypt(object);
        }
        return object;
    }

    /**
     * 数据脱敏加密枚举类
     */
    enum SensitiveDataEncryptEnum {
        /**
         * 身份证18位
         * 保留前三位后两位,其余用*替换
         */
        ID_NUMBER_18(ID_NUMBER_18_REGEX, ID_NUMBER_18_PATTERN, ID_NUMBER_18_REPLACEMENT),
        /**
         * 身份证15位
         * 保留前三位后两位,其余用*替换
         */
        ID_NUMBER_15(ID_NUMBER_15_REGEX, ID_NUMBER_15_PATTERN, ID_NUMBER_15_REPLACEMENT),
        /**
         * 手机号码
         * 保留前三位后四位,其余用*替换
         */
        PHONE_NUMBER(PHONE_NUMBER_REGEX, PHONE_NUMBER_PATTERN, PHONE_NUMBER_REPLACEMENT);

        private String regex;

        private Pattern pattern;

        private String replacement;

        SensitiveDataEncryptEnum(String regex, Pattern pattern, String replacement) {
            this.regex = regex;
            this.pattern = pattern;
            this.replacement = replacement;
        }

        /**
         * 数据脱敏加密
         *
         * @param object object
         * @return object
         */
        public Object encrypt(Object object) {
            String value = String.valueOf(object);
            // 加密身份证号码
            Matcher matcher = pattern.matcher(value);
            String result = "";
            while (matcher.find()) {
                // 因为在匹配的正则表达式中都加了(\\d|$)后缀,所以每次只能替换一个
                // 而且每次替换后需要reset重新匹配,避免漏掉连续的需要脱敏加密的数据
                // 比如 18812345678,18812345678
                result =  matcher.replaceFirst(replacement);
                matcher.reset(result);
            }
            if(!Strings.isNullOrEmpty(result)){
                return result;
            }
            return object;
        }
    }
}

