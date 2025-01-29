package com.vurtnewk.mall.ware.listener

import com.rabbitmq.client.Channel
import com.vurtnewk.common.dto.mq.StockLockedDto
import com.vurtnewk.common.utils.ext.logInfo
import com.vurtnewk.mall.ware.constants.MQConstants
import com.vurtnewk.mall.ware.service.WareSkuService
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.annotation.RabbitHandler
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Service

/**
 *
 * @author   vurtnewk
 * @since    2025/1/29 17:59
 */
@Service
@RabbitListener(queues = [MQConstants.Queue.STOCK_RELEASE_STOCK])
class StockReleaseListener(
    private val wareSkuService: WareSkuService
) {

    @RabbitHandler
    fun handleStockLockedRelease(stockLockedDto: StockLockedDto, message: Message, channel: Channel) {
        logInfo("收到库存消息=> ${stockLockedDto.stockLockedDetail}")
        runCatching {
            wareSkuService.unlockStock(stockLockedDto)
        }.onFailure {
            channel.basicReject(message.messageProperties.deliveryTag, true)
        }.onSuccess {
            channel.basicAck(message.messageProperties.deliveryTag, false)
        }
    }
}