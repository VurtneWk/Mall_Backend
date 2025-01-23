package com.vurtnewk.mall.order.service.impl

import org.springframework.stereotype.Service
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.rabbitmq.client.Channel
import com.vurtnewk.common.utils.PageUtils
import com.vurtnewk.common.utils.Query

import com.vurtnewk.mall.order.dao.OrderItemDao
import com.vurtnewk.mall.order.entity.OrderItemEntity
import com.vurtnewk.mall.order.entity.OrderReturnReasonEntity
import com.vurtnewk.mall.order.service.OrderItemService
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.annotation.RabbitHandler
import org.springframework.amqp.rabbit.annotation.RabbitListener

@RabbitListener(queues = ["hello-queue"])//也可以放到方法上
@Service("orderItemService")
class OrderItemServiceImpl : ServiceImpl<OrderItemDao, OrderItemEntity>(), OrderItemService {

    override fun queryPage(params: Map<String, Any>): PageUtils {
        val page = this.page(
            Query<OrderItemEntity>().getPage(params),
            QueryWrapper()
        )
        return PageUtils(page)
    }

    /**
     * - @RabbitListener 配置需要监听队列
     *      - queues 配置要监听的队列
     *
     * 参数可以写的类型
     */

    @RabbitHandler
    fun receiveMessage(
        message: Message,
        orderReturnReasonEntity: OrderReturnReasonEntity,
        channel: Channel, //当前传输数据的通道
    ) {
        // 有出入： 不然默认打印的是 类似 [B@132c193(byte[77]) 这样的内存地址
        println("消息具体内容： ${String(message.body, Charsets.UTF_8)}")
        println("接受到的消息 $message , 消息类型 ${message.javaClass}")

        println("orderReturnReasonEntity=>$orderReturnReasonEntity")

        /// 手动签收
//        channel.basicAck(message.messageProperties.deliveryTag, false)
        /// 拒绝签收
//        channel.basicNack(message.messageProperties.deliveryTag, false, true)

    }
}