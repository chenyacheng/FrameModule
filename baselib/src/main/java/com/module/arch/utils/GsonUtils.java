package com.module.arch.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Gson转换
 *
 * @author bd
 * @date 2021/10/08
 */
public class GsonUtils {

    private GsonUtils() {
    }

    /**
     * 对象实体json或分页的实体json，避免有空格或是空字符串时报错
     *
     * @param o    o
     * @param type new TypeToken<List<T>>() {}.getType()
     * @param <T>  泛型
     * @return 对象实体或分页的实体
     */
    public static <T> T removeSpaceFromJson(Object o, Class<T> type) {
        return fromJson(objectToString(o), type);
    }

    /**
     * 对象实体json或分页的实体json
     *
     * @param json json
     * @param type new TypeToken<List<T>>() {}.getType()
     * @param <T>  泛型
     * @return 对象实体或分页的实体
     */
    public static <T> T fromJson(String json, Class<T> type) {
        Gson gson = new Gson();
        return gson.fromJson(json, type);
    }

    public static String objectToString(Object o) {
        return new Gson().toJson(o);
    }

    /**
     * 字符串转Object
     *
     * @param json 字符串
     * @param type new TypeToken<Object>() {}.getType()
     * @param <T> 泛型
     * @return Object
     */
    public static <T> T stringToObject(String json, Type type) {
        Gson gson = new Gson();
        return gson.fromJson(json, type);
    }

    /**
     * 对象实体列表json
     *
     * @param json json
     * @param cls  类名
     * @param <T>  泛型
     * @return 对象实体列表
     */
    public static <T> List<T> listFromJson(String json, Class<T> cls) {
        List<T> list = new ArrayList<>();
        Gson gson = new Gson();
        JsonArray jsonArray = JsonParser.parseString(json).getAsJsonArray();
        for (JsonElement jsonElement : jsonArray) {
            list.add(gson.fromJson(jsonElement, cls));
        }
        return list;
    }
}
