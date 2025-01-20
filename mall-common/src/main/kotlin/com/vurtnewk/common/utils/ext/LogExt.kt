package com.vurtnewk.common.utils.ext

import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * slf4j 支持
 * 扩展方法，不用导入注解 直接使用
 * https://blog.csdn.net/DCTANT/article/details/134007369
 * @author   vurtnewk
 * @since    2025/1/10 12:59
 */

fun <T : Any> T.logger(): Logger {
    return LoggerFactory.getLogger(this.javaClass)
}

fun <T : Any> T.logError(e: Throwable) {
    logger().error("", e)
}

fun <T : Any> T.logError(msg: String) {
    logger().error(msg)
}

fun <T : Any> T.logInfo(msg: String) {
    logger().info(msg)
}

fun <T : Any> T.logDebug(msg: String) {
    logger().debug(msg)
}
