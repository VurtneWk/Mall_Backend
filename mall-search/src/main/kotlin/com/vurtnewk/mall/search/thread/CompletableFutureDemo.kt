package com.vurtnewk.mall.search.thread

import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * CompletableFutureDemo
 * @author   vurtnewk
 * @since    2025/1/19 14:45
 */

fun main() {
    val executorService = Executors.newFixedThreadPool(10)
    val future1 = CompletableFuture.supplyAsync({
        println("任务1 -> 当前线程: ${Thread.currentThread().id}")
        Thread.sleep(3000)
        println("任务1 结束")
        1
    }, executorService)

    val future2 = CompletableFuture.supplyAsync({
        println("任务2 -> 当前线程: ${Thread.currentThread().id}")
        Thread.sleep(4000)
        println("任务2 结束")
        2
    }, executorService)

    val future3 = CompletableFuture.supplyAsync({
        println("任务3 -> 当前线程: ${Thread.currentThread().id}")
        Thread.sleep(2000)
        println("任务3 结束")
        3
    }, executorService)

//    val f = CompletableFuture.allOf(future1, future2, future3).get()
//    //这里f 是null
//    println("任务4===> $f , ${future1.get()} , ${future2.get()} , ${future3.get()}")

    val f = CompletableFuture.anyOf(future1, future2, future3).get()
//    f是谁先结束 获取到的就是谁的
    println("结果=> $f")
    //这里这么调用 会等所有结果都完成才打印
    println("结果2 => ${future1.get()} , ${future2.get()} , ${future3.get()}")
}

private fun test06(executorService: ExecutorService?) {
    val future1 = CompletableFuture.supplyAsync({
        println("任务1 -> 当前线程: ${Thread.currentThread().id}")
        val i = 10 / 2
        Thread.sleep(3000)
        println("任务1 结束")
        i
    }, executorService)

    val future2 = CompletableFuture.supplyAsync({
        println("任务2 -> 当前线程: ${Thread.currentThread().id}")
        Thread.sleep(4000)
        println("任务2 结束")
        666
    }, executorService)

    // 返回类型要一样
    val future3 = future1.applyToEither(future2) { t ->
        println("任务3-> $t") //谁先结束，获取到的就是谁的结果
        888//带返回值
    }
    // 其它方法：只要有一个结束
//    future1.acceptEither()
//    future1.runAfterEither()
    println(future3.get())
}

private fun test05(executorService: ExecutorService?) {
    val future1 = CompletableFuture.supplyAsync({
        println("任务1 -> 当前线程: ${Thread.currentThread().id}")
        val i = 10 / 2
        Thread.sleep(3000)
        println("任务1 结束")
        i
    }, executorService)

    val future2 = CompletableFuture.supplyAsync({
        println("任务2 -> 当前线程: ${Thread.currentThread().id}")
        Thread.sleep(2000)
        println("任务2 结束")
        "hello"
    }, executorService)
//    future1.thenCombine()
//    future1.thenAcceptBoth()
    //没有返回值
    val future3 = future1.runAfterBoth(future2) {
        println("任务3 -> 获取不到前面的结果")
    }

    println("future3 => ${future3.get()}")//null
}

private fun test04(executorService: ExecutorService?) {
    CompletableFuture.supplyAsync({
        val i = 10 / 2
        println("第一步运行结果: $i ")
        i
    }, executorService)
        .thenApply { t ->
            println("第二步接受的结果：$t")
            t * 3// t*3 的结果作为返回值
        }
        .thenAccept { t ->
            println("第三步接受的结果: $t")
        }
        .thenApply {
            println("第四步..无法感知结果")
        }
}

private fun test03(executorService: ExecutorService?) {
    val future = CompletableFuture.supplyAsync({
        println("当前线程: ${Thread.currentThread().id}")
        val i = 10 / 2
        println("运行结果: $i ")
        i
    }, executorService)
        .handle { t, u ->
            println("异步任务成功完成: 当前线程: ${Thread.currentThread().id} t是结果 = $t , u是异常 = $u")
            u ?: return@handle (t * 2) // u是空则没出现异常
            t ?: return@handle 0 //出现了异常，返回默认值
        }
    println("future => ${future.get()}")
}

private fun test02(executorService: ExecutorService?) {
    val future2 = CompletableFuture.supplyAsync({
        println("当前线程: ${Thread.currentThread().id}")
        val i = 10 / 2
        println("运行结果: $i ")
        i
    }, executorService)
        .whenCompleteAsync { t, u ->
            println("异步任务成功完成: 当前线程: ${Thread.currentThread().id} t是结果 = $t , u是异常 = $u")
        }
        .exceptionally {
            10 //如果出现异常，可返回默认值
        }

    //如果没有加 exceptionally ， 出现异常时 去获取结果，就会抛出异常
    println("future => ${future2.get()}")
}