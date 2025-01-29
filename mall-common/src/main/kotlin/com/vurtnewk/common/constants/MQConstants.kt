package com.vurtnewk.common.constants

/**
 *
 * @author   vurtnewk
 * @since    2025/1/29 19:53
 */
object MQConstants {

    object Order{
        object Exchange {
            const val ORDER_EVENT_EXCHANGE = "order-event-exchange"
        }

        object Queue {
            const val ORDER_DELAY_QUEUE = "order.delay.queue"
            const val ORDER_RELEASE_ORDER_QUEUE = "order.release.order.queue"
        }

        object RoutingKey {
            const val ORDER_CREATE_ORDER = "order.create.order"
            const val ORDER_RELEASE_ORDER = "order.release.order"
            const val ORDER_RELEASE_OTHER_WILDCARD = "order.release.other.#"
        }
    }

    object Ware{
        object Exchange {
            /**
             * 交换机的名称
             */
            const val STOCK_EVENT = "stock-event-exchange"
        }

        object Queue {
            /**
             * 用于最终接受库存信息的队列
             */
            const val STOCK_RELEASE_STOCK = "stock.release.stock.queue"

            /**
             * 死信队列
             */
            const val STOCK_DELAY = "stock.delay.queue"

        }

        object RoutingKey {
            /**
             * 死信队列 交给 最终队列的 routing key
             */
            const val STOCK_RELEASE = "stock.release"

            /**
             * 交换机[Exchange.STOCK_EVENT] 和 队列[Queue.STOCK_RELEASE_STOCK] 绑定的Key
             */
            const val STOCK_RELEASE_WILDCARD = "stock.release.#"

            /**
             * 交换机[Exchange.STOCK_EVENT]和死信队列[Queue.STOCK_DELAY] 绑定的key
             */
            const val STOCK_LOCKED = "stock.locked"

        }
    }
}