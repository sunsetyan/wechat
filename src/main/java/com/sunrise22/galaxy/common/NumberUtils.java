package com.sunrise22.galaxy.common;

import java.text.DecimalFormat;

public final class NumberUtils {
    /**
     * 0值比较
     */
    private static final double E = 0.00000001D;

    /**
     * 精度0
     */
    private static final int PRECISION_0 = 0;
    /**
     * 精度1
     */
    private static final int PRECISION_1 = 1;
    /**
     * 精度2
     */
    private static final int PRECISION_2 = 2;
    /**
     * 精度3
     */
    private static final int PRECISION_3 = 3;
    /**
     * 精度4
     */
    private static final int PRECISION_4 = 4;
    /**
     * 精度5
     */
    private static final int PRECISION_5 = 5;
    /**
     * 精度6
     */
    private static final int PRECISION_6 = 6;

    /**
     * constructor
     */
    private NumberUtils() {
        // Utitliy class
    }

    /**
     * 对象转化为整型
     * 
     * @param o 对象
     * @return 整型
     */
    public static int c2int(Object o) {
        if (o != null) {
            return (int) Math.round(NumberUtils.c2double(o));
        } else {
            return 0;
        }
    }

    /**
     * 对象转化为双精度型
     * 
     * @param o 对象
     * @return 长整形
     */
    public static long c2long(Object o) {
        return Math.round(NumberUtils.c2double(o));
    }

    /**
     * 对象转化为浮点型
     * 
     * @param o 对象
     * @return 浮点型
     */
    public static float c2float(Object o) {
        return Float.parseFloat(StrUtils.c2str(o));
    }

    /**
     * 对象转化为双精度型
     * 
     * @param o 对象
     * @return 双精度型
     */
    public static double c2double(Object o) {
        return Double.parseDouble(StrUtils.c2str(o));
    }

    /**
     * 对象转化为整型
     * 
     * @param o 对象
     * @param defValue 出错时默认值
     * @return 整型
     */
    public static int c2int(Object o, int defValue) {
        try {
            // 若不判断，当o==null时，永远都返回0了
            if (o == null) {
                return defValue;
            }
            return NumberUtils.c2int(o);
        } catch (Exception ex) {
            return defValue;
        }
    }

    /**
     * 对象转化为双精度型
     * 
     * @param o 对象
     * @param defValue 出错时默认值
     * @return 长整形
     */
    public static long c2long(Object o, long defValue) {
        try {
            return NumberUtils.c2long(o);
        } catch (Exception ex) {
            return defValue;
        }
    }

    /**
     * 对象转化为浮点型
     * 
     * @param o 对象
     * @param defValue 出错时默认值
     * @return 浮点型
     */
    public static float c2float(Object o, float defValue) {
        try {
            return Float.parseFloat(StrUtils.c2str(o));
        } catch (Exception ex) {
            return defValue;
        }
    }

    /**
     * 对象转化为双精度型
     * 
     * @param o 对象
     * @param defValue 出错时默认值
     * @return 双精度型
     */
    public static double c2double(Object o, double defValue) {
        try {
            return Double.parseDouble(StrUtils.c2str(o));
        } catch (Exception ex) {
            return defValue;
        }
    }

    /**
     * 将double类型的数据四舍五入舍弃小数
     * 
     * @param value 待格式化的值
     * @return 返回值
     */
    public static String formatValueInt(double value) {
        DecimalFormat df = new DecimalFormat("0");
        return df.format(value);
    }

    /**
     * 格式化值为1位小数
     * 
     * @param value 待格式化的值
     * @return 返回值
     */
    public static String formatValue1(double value) {
        DecimalFormat df = new DecimalFormat("0.0");
        return df.format(value);
    }

    /**
     * 格式化值为2位小数
     * 
     * @param value 待格式化的值
     * @return 返回值
     */
    public static String formatValue2(double value) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(value);
    }

    /**
     * 格式化值为3位小数
     * 
     * @param value 待格式化的值
     * @return 返回值
     */
    public static String formatValue3(double value) {
        DecimalFormat df = new DecimalFormat("0.000");
        return df.format(value);
    }

    /**
     * 格式化值为4位小数
     * 
     * @param value 待格式化的值
     * @return 返回值
     */
    public static String formatValue4(double value) {
        DecimalFormat df = new DecimalFormat("0.0000");
        return df.format(value);
    }

    /**
     * 格式化值为5位小数
     * 
     * @param value 待格式化的值
     * @return 返回值
     */
    public static String formatValue5(double value) {
        DecimalFormat df = new DecimalFormat("0.00000");
        return df.format(value);
    }

    /**
     * 格式化值为6位小数
     * 
     * @param value 待格式化的值
     * @return 返回值
     */
    public static String formatValue6(double value) {
        DecimalFormat df = new DecimalFormat("0.000000");
        return df.format(value);
    }

    /**
     * 按指定精度格式化
     * 
     * @param value 值
     * @param precision 精度
     * @return 格式串
     */
    public static String formatValue(double value, int precision) {
        switch (precision) {
        case PRECISION_0:
            return formatValueInt(value);
        case PRECISION_1:
            return formatValue1(value);
        case PRECISION_2:
            return formatValue2(value);
        case PRECISION_3:
            return formatValue3(value);
        case PRECISION_4:
            return formatValue4(value);
        case PRECISION_5:
            return formatValue5(value);
        case PRECISION_6:
            return formatValue6(value);
        default:
            return formatValueInt(value);
        }
    }

    /**
     * 判断对象值是否为 0，针对浮点值，直接用0判断相等时有时不正确
     * 
     * @param value 值
     * @return 如果为0则为true
     */
    public static boolean isZeroValue(double value) {
        return Math.abs(value) < E;
    }

    /**
     * double值四舍五入
     * 
     * @param value 值
     * @return double值四舍五入
     */
    public static double round(double value) {
        return Math.round(value * 100) / 100.0;
    }
}
