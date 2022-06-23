package com.module.arch.utils

import android.util.Log

/**
 * 自定义的日志工具
 * 发布App时，请将setLogEnable设置为false
 *
 * @author bd
 * @date 2021/09/30
 */
class LogUtils private constructor() {

    init {
        throw IllegalStateException("Utility class")
    }

    companion object {
        private const val VERBOSE = 1
        private const val DEBUG = 2
        private const val INFO = 3
        private const val WARN = 4
        private const val ERROR = 5
        private const val NOTHING = 6
        private var level = 0

        /**
         * 设置日志是否打印出来
         *
         * @param enable 是否显示，false不显示，true显示
         */
        @JvmStatic
        fun setLogEnable(enable: Boolean) {
            level = if (enable) {
                VERBOSE
            } else {
                NOTHING
            }
        }

        @JvmStatic
        fun verbose(tag: String, msg: String) {
            if (level <= VERBOSE) {
                Log.v(tag, msg)
            }
        }

        @JvmStatic
        fun debug(tag: String, msg: String) {
            if (level <= DEBUG) {
                Log.d(tag, msg)
            }
        }

        @JvmStatic
        fun info(tag: String, msg: String) {
            if (level <= INFO) {
                Log.i(tag, msg)
            }
        }

        @JvmStatic
        fun warn(tag: String, msg: String) {
            if (level <= WARN) {
                Log.w(tag, msg)
            }
        }

        @JvmStatic
        fun error(tag: String, msg: String) {
            if (level <= ERROR) {
                Log.e(tag, msg)
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
        @JvmStatic
        fun showLogCompletion(tag: String, log: String, showCount: Int, logLevel: String) {
            if (log.length > showCount) {
                val show = log.substring(0, showCount)
                levelChoose(tag, show, logLevel)
                // 剩下的文本还是大于规定长度
                if (log.length - showCount > showCount) {
                    val partLog = log.substring(showCount)
                    showLogCompletion(tag, partLog, showCount, logLevel)
                } else {
                    val surplusLog = log.substring(showCount)
                    levelChoose(tag, surplusLog, logLevel)
                }
            } else {
                levelChoose(tag, log, logLevel)
            }
        }

        private fun levelChoose(tag: String, show: String, logLevel: String) {
            when (logLevel) {
                "debug" -> debug(tag, show)
                "info" -> info(tag, show)
                "warn" -> warn(tag, show)
                "error" -> error(tag, show)
                else -> verbose(tag, show)
            }
        }
    }
}