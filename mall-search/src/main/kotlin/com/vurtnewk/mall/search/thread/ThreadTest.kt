package com.vurtnewk.mall.search.thread

import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.ThreadPoolExecutor

/**
 *
 * @author   vurtnewk
 * @since    2025/1/19 13:39
 */

fun main() {
    val thread01 = Thread01()
    thread01.start()
//    ThreadPoolExecutor
}

class Thread01 : Thread() {
    override fun run() {
        super.run()
        println("当前线程: ${currentThread().id}")
        val i = 10 / 2
        println("运行结果: $i ")
    }
}