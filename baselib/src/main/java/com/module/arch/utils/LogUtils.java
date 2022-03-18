package com.module.arch.utils;

import android.util.Log;

/**
 * 自定义的日志工具
 * 发布App时，请将setLogEnable设置为false
 *
 * @author bd
 * @date 2021/09/30
 */
public class LogUtils {

    private static final int VERBOSE = 1;

    private static final int DEBUG = 2;

    private static final int INFO = 3;

    private static final int WARN = 4;

    private static final int ERROR = 5;

    private static final int NOTHING = 6;

    private static int level;

    private LogUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 设置日志是否打印出来
     *
     * @param enable 是否显示，false不显示，true显示
     */
    public static void setLogEnable(boolean enable) {
        if (enable) {
            level = VERBOSE;
        } else {
            level = NOTHING;
        }
    }

    public static void verbose(String tag, String msg) {
        if (level <= VERBOSE) {
            Log.v(tag, msg);
        }
    }

    public static void debug(String tag, String msg) {
        if (level <= DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void info(String tag, String msg) {
        if (level <= INFO) {
            Log.i(tag, msg);
        }
    }

    public static void warn(String tag, String msg) {
        if (level <= WARN) {
            Log.w(tag, msg);
        }
    }

    public static void error(String tag, String msg) {
        if (level <= ERROR) {
            Log.e(tag, msg);
        }
    }

    /**
     * 分段打印出较长log文本
     *
     * @param log       打印的日志
     * @param showCount 显示每段的长度
     * @param logLevel  日志级别
     * @param tag 标签
     */
    public static void showLogCompletion(String tag, String log, int showCount, String logLevel) {
        if (log.length() > showCount) {
            String show = log.substring(0, showCount);
            levelChoose(tag, show, logLevel);
            // 剩下的文本还是大于规定长度
            if ((log.length() - showCount) > showCount) {
                String partLog = log.substring(showCount);
                showLogCompletion(tag, partLog, showCount, logLevel);
            } else {
                String surplusLog = log.substring(showCount);
                levelChoose(tag, surplusLog, logLevel);
            }
        } else {
            levelChoose(tag, log, logLevel);
        }
    }

    private static void levelChoose(String tag, String show, String logLevel) {
        switch (logLevel) {
            case "debug":
                debug(tag, show);
                break;
            case "info":
                info(tag, show);
                break;
            case "warn":
                warn(tag, show);
                break;
            case "error":
                error(tag, show);
                break;
            default:
                verbose(tag, show);
                break;
        }
    }
}
