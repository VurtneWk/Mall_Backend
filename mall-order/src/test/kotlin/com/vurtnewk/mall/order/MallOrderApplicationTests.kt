package com.vurtnewk.mall.order

import com.vurtnewk.mall.order.entity.OrderReturnReasonEntity
import kotlinx.coroutines.*
import org.junit.jupiter.api.Test
import org.springframework.amqp.core.AmqpAdmin
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.DirectExchange
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.*
import kotlin.system.measureTimeMillis

@SpringBootTest
class MallOrderApplicationTests {

    @Autowired
    private lateinit var amqpAdmin: AmqpAdmin

    @Autowired
    private lateinit var rabbitTemplate: RabbitTemplate

    /**
     * 1. 如何创建 exchange 、 queue 、 binding
     * 2. 如何收发消息
     */
    @Test
    fun testCreateExchange() {
        /**
         * - String name, 名字
         * - boolean durable,是否持久化
         * - boolean autoDelete,是否自动删除
         * - Map<String, Object> arguments 指定参数
         */
        val directExchange = DirectExchange("hello-java-exchange", true, false)
        amqpAdmin.declareExchange(directExchange)
        println("directExchange 创建成功")
    }

    @Test
    fun testCreateQueue() {
        // 注意导包是：org.springframework.amqp.core.Queue
        /**
         * - String name, 名字
         * - boolean durable, 是否持久化
         * - boolean exclusive, 如果声明的是独占队列（该队列将仅由声明者的连接使用），则为True。
         * - boolean autoDelete,是否自动删除
         * - @Nullable Map<String, Object> arguments 指定参数
         */
        val queue = Queue("hello-queue", true, false, false)
        amqpAdmin.declareQueue(queue)
        println("queue 创建成功")
    }

    @Test
    fun testCreateBinding() {
        /**
         * - String destination, 目的地
         * - DestinationType destinationType, 目的地类型
         * - String exchange, 交换机
         * - String routingKey, 路由键
         * - @Nullable Map<String, Object> arguments 自定义参数
         *
         * 将 exchange 指定的交换机和 destination 目的地进行绑定，使用routingKey作为指定的路由键
         */
        val binding = Binding("hello-queue", Binding.DestinationType.QUEUE, "hello-java-exchange", "hello.java", null)
        amqpAdmin.declareBinding(binding)
        println("binding 创建成功")
    }

    @Test
    fun testSendMessage() {
//        rabbitTemplate.convertAndSend("hello-java-exchange","hello.java","hello world!")
//        println("消息发送完成")

        // 如果发送的消息是个对象 ， 我们会使用序列化机制 ， 将对象写出去。 对象必须实现 Serializable 接口
        val reasonEntity = OrderReturnReasonEntity()
        reasonEntity.id = 1L
        reasonEntity.createTime = Date()
        reasonEntity.name = "哈哈"
        rabbitTemplate.convertAndSend("hello-java-exchange", "22hx22xx1", reasonEntity)
    }

    @Test
    fun testDelay() = runBlocking {
        val time = measureTimeMillis {
            coroutineScope {
                withContext(Dispatchers.IO) {
                    launch {
                        delay(3000)
                        println("1")
                    }
                    launch {
                        delay(2000)
                        println("2")
                    }
                }
            }
        }
        println("end => $time")
    }

    @Test
    fun test2() = runBlocking {
        //GlobalScope是守护协程,如果没有join，主线程结束时就直接结束了
        val myJob = GlobalScope.launch {
            repeat(200) { i ->
                println("hello: $i")
                delay(500)
            }
        }
        delay(1100)
        println("hello world")
        //默认情况下，也不会打印这个异常
//        myJob.cancel(CancellationException("just a try"))
//        myJob.join()

        // 不能先join
        // 如果 myJob 协程正在执行，并且你调用了 myJob.join()，当前线程将会挂起，直到 myJob 完成执行。
        // 在这种情况下，cancel() 只有在 myJob.join() 结束后才能执行
//    myJob.join()
//    myJob.cancel()

        //This is a shortcut for the invocation of cancel followed by join.
//        myJob.cancelAndJoin()

        println("welcome")
    }

    @Test
    fun test03() = runBlocking {
        val job1 = launch {
            repeat(5) { i ->
                println("Job 1 - $i")
                delay(1L)
            }
        }

        val job2 = launch {
            repeat(5) { i ->
                println("Job 2 - $i")
                yield() // 让出执行权
            }
        }

        joinAll(job1, job2)
        println("Main program ends")
    }

    @Test
    fun test04() = runBlocking {
        coroutineScope {
            launch {
                delay(1000)
                println("job 1")
            }
            println("coroutineScope")
        }
        println("end")
    }
}
