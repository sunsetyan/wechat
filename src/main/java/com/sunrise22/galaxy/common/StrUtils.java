package com.sunrise22.galaxy.common;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.sql.Clob;
import java.util.Iterator;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * 字符串处理实用类
 * 
 * @version 1.0 2010-06-20
 * @since CS 1.0
 * 
 */
public final class StrUtils {
    /**
     * 默认缓冲区大小
     */
    private static final int DEF_BUFFER_SIZE = 1024;

    /**
     * Constructor
     */
    private StrUtils() {
        // Utility class
    }

    /**
     * Object转化为字符串
     * 
     * @param o 输入对象
     * @return 字符串
     */
    public static String c2str(Object o) {
        if (o == null) {
            return "";
        }

        // TODO这个函数很多地方用，这个是不是效率有点低
        if (o instanceof Clob) {
            return StrUtils.clob2str((Clob) o);
        }

        return String.valueOf(o);
    }

    /**
     * Clob转换为字符串
     * 
     * @param clob clob对象
     * @return 字符串
     */
    private static String clob2str(Clob clob) {
        try {
            Reader inStreamDoc = clob.getCharacterStream();
            // 取得clob的长度
            char[] tempDoc = new char[(int) clob.length()];
            inStreamDoc.read(tempDoc);
            inStreamDoc.close();
            return new String(tempDoc);
        } catch (Exception e) {
            throw new InternalError("Clob转换错误");
        }
    }

    /**
     * 转化字符串，null以另一个字符串代替
     * 
     * @param value 原始字符串
     * @param nullValue 为空时字符串
     * @return 检查后的结果
     */
    public static String c2str(Object value, String nullValue) {
        return value == null ? nullValue : c2str(value);
    }

    /**
     * 对象转换成字符串，null值时为空
     * 
     * @param value 转换对象
     * @return 转换后的字符串
     */
    public static String obj2StrWithDef(Object value) {
        return StrUtils.c2str(value, "");
    }

    /**
     * 使用分隔符连接迭代器数据
     * <p>
     * 如： List<String> v = new ArrayList<String>();
     * <p>
     * v.add("A");
     * <p>
     * v.add("B");
     * <p>
     * v.add("C");
     * <p>
     * 则:StringUtils.join(v, ",") = "A,B,C"
     * 
     * @param c 迭代器
     * @param delimiter 分隔符
     * @return 分隔符连接的字符串
     */
    public static String join(Iterable<? extends Object> c, String delimiter) {
        Iterator<? extends Object> itr;

        if (c == null) {
            return "";
        } else {
            itr = c.iterator();
            if (!itr.hasNext()) {
                return "";
            }
        }

        StringBuilder sb = new StringBuilder(c2str(itr.next()));
        while (itr.hasNext()) {
            sb.append(delimiter).append(c2str(itr.next()));
        }

        return sb.toString();
    }

    /**
     * 数组转化为连接器字符串
     * <p>
     * 如:StringUtils.join(new String[]{"A","B","C"}, ",") = "A,B,C"
     * 
     * @param <T> 泛型对象
     * 
     * @param arrO 数组
     * @param delimiter 分割符
     * @return 增加了分隔符的字符串
     */
    public static <T> String join(T[] arrO, String delimiter) {
        int arrSize;

        arrSize = arrO.length;
        if (arrSize == 0) {
            return "";
        } else {
            StringBuilder sb = new StringBuilder(String.valueOf(arrO[0]));
            for (int i = 1; i < arrSize; i++) {
                sb.append(delimiter).append(arrO[i]);
            }

            return sb.toString();
        }
    }

    /**
     * 字符串转换为数组
     * 
     * @param source 源字符串
     * @param delimiter 分隔符
     * @return 字符串数组,如果源字符串为空或null，则返回String[0]
     */
    public static String[] toArray(String source, String delimiter) {
        if (isEmpty(source)) {
            return new String[0];
        } else {
            return source.split(delimiter);
        }
    }

    /**
     * 判断是字符串否为空或空白
     * 
     * @param value 值
     * @return 结果
     */
    public static boolean isEmpty(String value) {
        return value == null || value.length() == 0;
    }

    /**
     * 建立字符串数组
     * 
     * @param str 字符串
     * @param count 数组元素个数
     * @return 数组，包含count个str
     */
    public static String[] getStringArray(String str, int count) {
        String[] result = new String[count];

        for (int i = 0; i < count; i++) {
            result[i] = str;
        }

        return result;
    }

    /**
     * 建立字符数组
     * 
     * @param ch 字符
     * @param count 数组元素个数
     * @return 数组，包含count个ch
     */
    public static char[] getCharArray(char ch, int count) {
        char[] result = new char[count];

        for (int i = 0; i < count; i++) {
            result[i] = ch;
        }

        return result;
    }

    /**
     * Iso字符串转化为Utf8字符串
     * 
     * @param str 字符串
     * @return 转码后的字符串
     */
    public static String isoToUtf8(String str) {
        try {
            return new String(str.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    /**
     * 以指定字符填补字符串的左边
     * 
     * @param source 源字符串
     * @param padChar 指定字符
     * @param length 目标长度
     * @return 填充后的字符串
     */
    public static String padLeft(String source, char padChar, int length) {
        if (source.length() > length) {
            return source;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(StrUtils.getCharArray(padChar, length - source.length()));
        sb.append(source);

        return sb.toString();
    }

    /**
     * 以指定字符填补字符串的右边
     * 
     * @param source 源字符串
     * @param padChar 指定字符
     * @param length 目标长度
     * @return 填充后的字符串
     */
    public static String padRight(String source, char padChar, int length) {
        if (source.length() > length) {
            return source;
        }

        StringBuilder sb = new StringBuilder(source);
        sb.append(StrUtils.getCharArray(padChar, length - source.length()));

        return sb.toString();
    }

    /**
     * 修剪字符串
     * 
     * @param source 原始字符串
     * @return 修剪后的字符串
     */
    public static String trim(String source) {
        return source == null ? "" : source.trim();
    }

    /**
     * 修剪左边字符
     * 
     * @param source 源字符串
     * @param trimChar 修剪字符
     * @return 修剪后的字符串
     */
    public static String trimLeft(String source, char trimChar) {
        for (int i = 0, length = source.length(); i < length; i++) {
            if (source.charAt(i) != trimChar) {
                return source.substring(i);
            }
        }

        return "";
    }

    /**
     * 修剪右边字符
     * 
     * @param source 源字符串
     * @param trimChar 修剪字符
     * @return 修剪后的字符串
     */
    public static String trimRight(String source, char trimChar) {
        for (int size = source.length(), i = size - 1; i >= 0; i--) {
            if (source.charAt(i) != trimChar) {
                return source.substring(0, i + 1);
            }
        }

        return "";
    }

    /**
     * 字符串驼峰表示法
     * 
     * @param source 源字符串
     * @return 驼峰表示字符串
     */
    public static String camelize(String source) {
        String str = source;
        if (str.startsWith("_")) {
            str = StrUtils.capitalize(str);
        }

        String[] arrS = source.split("_");
        int len = arrS.length;

        if (len == 1) {
            return arrS[0];
        }

        String camelized = arrS[0];

        for (int i = 1; i < len; i++) {
            camelized += String.valueOf(arrS[i].charAt(0)).toUpperCase() + arrS[i].substring(1);
        }

        return camelized;
    }

    /**
     * 使字符串首字母大写
     * 
     * @param source 源字符串
     * @return 字符串
     */
    public static String capitalize(String source) {
        return String.valueOf(source.charAt(0)).toUpperCase() + source.substring(1);
    }

    /**
     * 首字母小写
     * 
     * @param source 源字符串
     * @return 字符串
     */
    public static String firstLower(String source) {
        return String.valueOf(source.charAt(0)).toLowerCase() + source.substring(1);
    }

    /**
     * 清除所有空格
     * 
     * @param source 源字符串
     * @return 清除空格后的字符串
     */
    public static String eraseAllSpace(String source) {
        if (isEmpty(source)) {
            return "";
        }

        return source.replaceAll("\\s*", "");
    }
    

    /**
     * 清除所有\r\n
     * 
     * @param source 源字符串
     * @return 清除\r\n后的字符串
     */
    public static String eraseAllNewline(String source) {
        if (isEmpty(source)) {
            return "";
        }

        return source.replaceAll("\\r*", "").replaceAll("\\n*", "");
    }

    /**
     * escape HTML
     * 
     * @param source 源字符串
     * @return escaped html
     */
    public static String escapeHtml(String source) {
        return StringEscapeUtils.escapeHtml(source);
    }

    /**
     * unescape HTML
     * 
     * @param source 源字符串
     * @return unescaped html
     */
    public static String unEscapeHtml(String source) {
        return StringEscapeUtils.unescapeHtml(source);
    }

    /**
     * 获取Reader内容
     * 
     * @param reader reader对象
     * @return Reader内容字符串
     */
    public static String readerToString(Reader reader) {
        StringBuilder result = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(reader);
            char[] buf = new char[DEF_BUFFER_SIZE];
            int r = 0;
            while ((r = br.read(buf)) != -1) {
                result.append(buf, 0, r);
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();
    }

    /**
     * InputStream 转换为字符串
     * 
     * @param is InputStream输入流
     * @return 字符串
     */
    public static String inputStreamToString(InputStream is) {
        ByteArrayOutputStream os = null;
        try {
            os = new ByteArrayOutputStream(DEF_BUFFER_SIZE);
            byte[] data = new byte[DEF_BUFFER_SIZE];
            int n = -1;
            while ((n = is.read(data)) != -1) {
                os.write(data, 0, n);
            }

            os.flush();

            return new String(os.toByteArray(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "";
    }
}
