package com.eson.common.core.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author dengxiaolin
 * @since 2021/01/15
 */
public class MoneyUtils {
    private static final BigDecimal DEFAULT_DECIMAL = new BigDecimal("100");

    public static long yuan2cent(double money) {
        return yuan2cent(String.valueOf(money));
    }


    public static long yuan2cent(String money) {
        if (money == null) {
            return 0L;
        }

        return new BigDecimal(money).multiply(DEFAULT_DECIMAL).setScale(0, RoundingMode.HALF_UP).longValue();
    }

    public static Double cent2Yuan(double money) {
        return cent2Yuan(String.valueOf(money), 0.00d);
    }

    public static Double cent2Yuan(String money, final double defaultValue) {
        if (money == null) {
            return defaultValue;
        }
        return new BigDecimal(money).divide(DEFAULT_DECIMAL, 2, RoundingMode.HALF_UP).doubleValue();
    }
}
