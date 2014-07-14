/**
 * NextVisual
 */
/**
 * NextVisual
 */
package com.sunrise22.galaxy.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 参数管理器
 * 
 * @version 1.0
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @since 外勤助手 3.0
 * 
 *        <pre>
 * 历史：
 *      建立: 2013-11-13 lexloo
 * </pre>
 */
public enum ParameterMgr {
    /**
     * 单例
     */
    INSTANCE;

    /**
     * 正则表达式判断
     */
    private Pattern regx = Pattern.compile("\\{(\\S*?)\\}");
    /**
     * 租户参数表
     */
    private Map<Integer, Map<String, String>> tenantParamMap = new HashMap<Integer, Map<String, String>>();

    /**
     * 抽取参数
     * 
     * @param source 原始数据
     * @return 参数列表
     */
    private List<String> extractParams(String source) {
        List<String> paramList = new ArrayList<String>();

        Matcher m = regx.matcher(source);
        while (m.find()) {
            paramList.add(m.group(1));
        }

        return paramList;
    }

    /**
     * 加载租户参数
     * 
     * @param tenantId 租戶Id
     * @param paramMap 参数表
     */
    public synchronized void loadTenantParams(int tenantId, Map<String, String> paramMap) {
        tenantParamMap.put(tenantId, paramMap);
    }

    /**
     * 用参数填充字符串，如果对应的参数不存在，则使用参数名代替参数值
     * 
     * @param source 源字符串
     * @param paramMap 参数表
     * @return 处理后的字符串
     */
    public String fillStringParams(String source, Map<String, String> paramMap) {
        String temp = source;

        List<String> paramList = this.extractParams(source);
        for (String param : paramList) {

            if (paramMap.containsKey(param)) {
                String replaceParam = this.replaceSpecialChar(param);

                temp = temp.replaceAll("\\{" + replaceParam + "\\}", paramMap.get(param));
            } else {
                temp = temp.replaceAll("\\{" + param + "\\}", param);
            }
        }

        return temp;
    }

    /**
     * 根据租户参数填充字符串
     * 
     * @param source 源字符串
     * @param tenantId 租户Id
     * @return 处理后的字符串
     */
    public String fillStringParams(String source, int tenantId) {
        return fillStringParams(source, tenantParamMap.get(tenantId));
    }

    /**
     * 替换特殊字符 $,(, ) ;将(store$info) 变为 \(store\$info\)
     * 
     * @param param param
     * @return 替换特殊字符
     */
    private String replaceSpecialChar(String param) {
        String temp = param;

        if (param.contains("(") || param.contains(")") || param.contains("$")) {
            String escapeCharacter = "\\\\";

            temp = temp.replaceAll("\\(", escapeCharacter + "(");
            temp = temp.replaceAll("\\)", escapeCharacter + ")");
            temp = temp.replaceAll("\\$", escapeCharacter + "$");

            return temp;
        }

        return param;

    }
}
