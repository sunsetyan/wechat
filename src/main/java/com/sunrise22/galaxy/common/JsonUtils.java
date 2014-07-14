/**
 * 北京中航嘉城科技股份有限公司(2012)
 */
package com.sunrise22.galaxy.common;

import java.io.InputStream;
import java.util.List;

import com.alibaba.fastjson.JSON;

/**
 * Json处理实用类
 * 
 * @version 1.0
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @since 销售宝 2.0
 * 
 *        Date：2012-2-19
 * 
 *        <pre>
 * 2012 - 05 - 02 * 增加输入流转换为对象
 * </pre>
 */
public final class JsonUtils {
    /**
     * 构造函数
     */
    private JsonUtils() {
        // 不需要创建
    }

    /**
     * 对象转化为Json字符串
     * 
     * @param object 对象
     * @return Json字符串
     */
    public static String obj2Json(Object object) {
        return JSON.toJSONString(object);
    }

    /**
     * Json字符串转化为Java对象
     * 
     * @param <T> 泛型
     * @param clazz Java类
     * @param json Json字符串
     * @return 对象
     */
    public static <T> T json2Obj(Class<T> clazz, String json) {
        return JSON.parseObject(json, clazz);
    }

    /**
     * Json字符串转换为Java List
     * 
     * @param <T> 泛型
     * @param clazz 包含对象类
     * @param json json字符串
     * @return 列表对象
     */
    public static <T> List<T> json2List(Class<T> clazz, String json) {
        return JSON.parseArray(json, clazz);
    }

    /**
     * Json字符串流转换为Java对象
     * 
     * @param <T> 泛型
     * @param clazz Java类
     * @param is 字符串流
     * @return 对象
     */
    public static <T> T jsonInputStream2Obj(Class<T> clazz, InputStream is) {
        String json = StrUtils.inputStreamToString(is);

        return json2Obj(clazz, json);
    }
}
