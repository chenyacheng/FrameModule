package com.module.common.utils;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * 保留两位小数
 *
 * @author BD
 * @date 2021/12/17
 */
public class DecimalFormatUtils {

    private DecimalFormatUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static String numberFormat(double number) {
        DecimalFormat format = new DecimalFormat("0.00");
        format.setMaximumFractionDigits(2);
        format.setGroupingSize(0);
        format.setRoundingMode(RoundingMode.FLOOR);
        return format.format(number);
    }

    public static String numberFormat1(double number) {
        DecimalFormat format = new DecimalFormat("0");
        format.setMaximumFractionDigits(0);
        format.setGroupingSize(0);
        format.setRoundingMode(RoundingMode.FLOOR);
        return format.format(number);
    }
}
