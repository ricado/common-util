package com.creatchen.util.data;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

/**
 * @author creatchen
 * @version 1.0
 * @date 2018/12/15
 */
@Slf4j
public class JSONUtil {

    /**
     * 获取jsonObject中键值为key的JSONObject对象，类型不对则返回null
     *
     * @param jsonObject jsonObject
     * @param key        key
     * @return JSONObject
     */
    public static JSONObject getJSONObject(JSONObject jsonObject, String key) {
        try {
            return jsonObject.getJSONObject(key);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取jsonObject中键值为key的JSONArray对象,类型不对则返回null
     *
     * @param jsonObject jsonObject
     * @param key        key
     * @return JSONArray
     */
    public static JSONArray getJSONArray(JSONObject jsonObject, String key) {
        try {
            return jsonObject.getJSONArray(key);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取jsonObject中键值为key的JSONObject对象，类型不对则返回所指定的defaultValue
     *
     * @param jsonObject jsonObject
     * @param key        key
     * @return JSONObject
     */
    public static JSONObject getJSONObject(JSONObject jsonObject, String key, JSONObject defaultValue) {
        try {
            JSONObject value = jsonObject.getJSONObject(key);
            if (null != value) {
                return value;
            }
        } catch (Exception e) {
            log.debug("jsonObject get error : {}", e.getMessage());
        }
        return defaultValue;
    }

    /**
     * 获取jsonObject中键值为key的JSONArray对象,类型不对则返回所指定的defaultValue
     *
     * @param jsonObject   jsonObject
     * @param key          key
     * @param defaultValue defaultValue
     * @return JSONArray
     */
    public static JSONArray getJSONArray(JSONObject jsonObject, String key, JSONArray defaultValue) {
        try {
            JSONArray value = jsonObject.getJSONArray(key);
            if (null != value) {
                return value;
            }
        } catch (Exception e) {
            log.debug("jsonObject get error : {}", e.getMessage());
        }
        return defaultValue;
    }

    /**
     * 获取jsonObject中键值为key的Integer对象,类型不对或没有这个key则返回所指定的defaultValue
     *
     * @param jsonObject   json
     * @param key          key
     * @param defaultValue 默认值
     * @return Integer
     */
    public static Integer getInteger(JSONObject jsonObject, String key, Integer defaultValue) {
        try {
            Integer value = jsonObject.getInteger(key);
            if (null != value) {
                return value;
            }
        } catch (Exception e) {
            log.debug("jsonObject get error : {}", e.getMessage());
        }
        return defaultValue;
    }

    /**
     * 获取jsonObject中键值为key的Byte对象,类型不对或没有这个key则返回所指定的defaultValue
     *
     * @param jsonObject   json
     * @param key          key
     * @param defaultValue 默认值
     * @return Byte
     */
    public static Byte getByte(JSONObject jsonObject, String key, Byte defaultValue) {
        try {
            Byte value = jsonObject.getByte(key);
            if (null != value) {
                return value;
            }
        } catch (Exception e) {
            log.debug("jsonObject get error : {}", e.getMessage());
        }
        return defaultValue;
    }

    /**
     * 获取jsonObject中键值为key的Long对象,类型不对或没有这个key则返回所指定的defaultValue
     *
     * @param jsonObject   json
     * @param key          key
     * @param defaultValue 默认值
     * @return Long
     */
    public static Long getLong(JSONObject jsonObject, String key, Long defaultValue) {
        try {
            Long value = jsonObject.getLong(key);
            if (null != value) {
                return value;
            }
        } catch (Exception e) {
            log.debug("jsonObject get error : {}", e.getMessage());
        }
        return defaultValue;
    }

    /**
     * 获取jsonObject中键值为key的Double对象,类型不对或没有这个key则返回所指定的defaultValue
     *
     * @param jsonObject   json
     * @param key          key
     * @param defaultValue 默认值
     * @return Double
     */
    public static Double getDouble(JSONObject jsonObject, String key, Double defaultValue) {
        try {
            Double value = jsonObject.getDouble(key);
            if (null != value) {
                return value;
            }
        } catch (Exception e) {
            log.debug("jsonObject get error : {}", e.getMessage());
        }
        return defaultValue;
    }

    /**
     * 获取jsonObject中键值为key的Float对象,类型不对或没有这个key则返回所指定的defaultValue
     *
     * @param jsonObject   json
     * @param key          key
     * @param defaultValue 默认值
     * @return Float
     */
    public static Float getFloat(JSONObject jsonObject, String key, Float defaultValue) {
        try {
            Float value = jsonObject.getFloat(key);
            if (null != value) {
                return value;
            }
        } catch (Exception e) {
            log.debug("jsonObject get error : {}", e.getMessage());
        }
        return defaultValue;
    }

    /**
     * 获取jsonObject中键值为key的String对象,类型不对或没有这个key则返回所指定的defaultValue
     *
     * @param jsonObject   json
     * @param key          key
     * @param defaultValue 默认值
     * @return String
     */
    public static String getString(JSONObject jsonObject, String key, String defaultValue) {
        try {
            String value = jsonObject.getString(key);
            if (null != value) {
                return value;
            }
        } catch (Exception e) {
            log.debug("jsonObject get error : {}", e.getMessage());
        }
        return defaultValue;
    }

    /**
     * 获取jsonObject中键值为key的Integer对象,类型不对或没有这个key则返回0
     *
     * @param jsonObject json
     * @param key        key
     * @return Integer
     */
    public static Integer getIntegerOrZero(JSONObject jsonObject, String key) {
        return getInteger(jsonObject, key, 0);
    }

    /**
     * 获取Integer
     *
     * @param jsonObject jsonObject
     * @param key        key
     * @return Integer
     */
    public static Integer getInteger(JSONObject jsonObject, String key) {
        return getInteger(jsonObject, key, null);
    }

    /**
     * 获取jsonObject中键值为key的Byte对象,类型不对或没有这个key则返回0
     *
     * @param jsonObject json
     * @param key        key
     * @return Byte
     */
    public static Byte getByteOrZero(JSONObject jsonObject, String key) {
        return getByte(jsonObject, key, Byte.valueOf("0"));
    }

    /**
     * 获取jsonObject中键值为key的Long对象,类型不对或没有这个key则返回0
     *
     * @param jsonObject json
     * @param key        key
     * @return Long
     */
    public static Long getLongOrZero(JSONObject jsonObject, String key) {
        return getLong(jsonObject, key, 0L);
    }

    /**
     * 获取jsonObject中键值为key的Double对象,类型不对或没有这个key则返回0
     *
     * @param jsonObject json
     * @param key        key
     * @return Double
     */
    public static Double getDoubleOrZero(JSONObject jsonObject, String key) {
        return getDouble(jsonObject, key, 0.0);
    }

    public static Double getDoubleOrNull(JSONObject jsonObject, String key) {
        return getDouble(jsonObject, key, null);
    }

    /**
     * 获取jsonObject中键值为key的Float对象,类型不对或没有这个key则返回0
     *
     * @param jsonObject json
     * @param key        key
     * @return Float
     */
    public static Float getFloatOrZero(JSONObject jsonObject, String key) {
        return getFloat(jsonObject, key, 0.0F);
    }

    /**
     * 获取jsonObject中键值为key的String对象,类型不对或没有这个key则返回空字符串
     *
     * @param jsonObject json
     * @param key        key
     * @return String String
     */
    public static String getStringOrEmpty(JSONObject jsonObject, String key) {
        return getString(jsonObject, key, "");
    }


    /**
     * 获取jsonObject中的 字符串
     *
     * @param jsonObject jsonObject
     * @param key        key
     * @return String
     */
    public static String getString(JSONObject jsonObject, String key) {
        return getString(jsonObject, key, null);
    }
}
