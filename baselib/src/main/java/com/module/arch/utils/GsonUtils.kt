package com.module.arch.utils

import com.google.gson.Gson
import com.google.gson.JsonParser
import java.lang.reflect.Type
import java.util.ArrayList

/**
 * Gson转换
 *
 * @author bd
 * @date 2021/10/08
 */
object GsonUtils {

    /**
     * 对象实体json或分页的实体json，避免有空格或是空字符串时报错
     *
     * @param o    o
     * @param type new TypeToken<List></List><T>>() {}.getType()
     * @param <T>  泛型
     * @return 对象实体或分页的实体
     */
    @JvmStatic
    fun <T> removeSpaceFromJson(o: Any?, type: Class<T>?): T {
        return fromJson(objectToString(o), type)
    }

    /**
     * 对象实体json或分页的实体json
     *
     * @param json json
     * @param type new TypeToken<List></List><T>>() {}.getType()
     * @param <T>  泛型
     * @return 对象实体或分页的实体
     */
    @JvmStatic
    fun <T> fromJson(json: String?, type: Class<T>?): T {
        val gson = Gson()
        return gson.fromJson(json, type)
    }

    @JvmStatic
    fun objectToString(o: Any?): String {
        return Gson().toJson(o)
    }

    /**
     * 字符串转Object
     *
     * @param json 字符串
     * @param type new TypeToken<Object>() {}.getType()
     * @param <T> 泛型
     * @return Object
     */
    fun <T> stringToObject(json: String?, type: Type?): T {
        val gson = Gson()
        return gson.fromJson(json, type)
    }

    /**
     * 对象实体列表json
     *
     * @param json json
     * @param cls  类名
     * @param <T>  泛型
     * @return 对象实体列表
     */
    fun <T> listFromJson(json: String?, cls: Class<T>?): List<T> {
        val list: MutableList<T> = ArrayList()
        val gson = Gson()
        val jsonArray = JsonParser.parseString(json).asJsonArray
        for (jsonElement in jsonArray) {
            list.add(gson.fromJson(jsonElement, cls))
        }
        return list
    }
}