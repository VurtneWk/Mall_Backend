package com.vurtnewk.mall.seckill.scheduled

import com.vurtnewk.common.utils.ext.logInfo
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

/**
 * 定时任务使用
 * @author   vurtnewk
 * @since    2025/1/30 23:48
 *
 * 1. @EnableScheduling 开启定时任务
 * 2. @Scheduled 开启一个定时任务
 * 3. @EnableAsync 开启异步任务，@Async 给指定方法开启异步任务。配置类：TaskExecutionAutoConfiguration
 */
@Component
@EnableAsync
@EnableScheduling
class HelloSchedule {

    /**
     * ## Spring 和 Quartz 的区别
     * 1. Spring 中6位组成，不允许第7位的年
     * 2. 在周的位置: Spring 中 1-7 代表周一到周日 ， Quartz 中 1-7 是周日到周一
     * 3. 定时任务不应该阻塞。默认是阻塞的（当前任务执行完才能执行下一个）
     *      1. 如果需要有耗时的，可以使用异步
     *      2. 支持定时任务线程池 ,TaskSchedulingProperties 通过 spring.task.scheduling 进行配置
     *
     * 使用异步 + 定时任务来完成 定时任务不阻塞的功能
     */
//    @Async
//    @Scheduled(cron = "* * * * * 5")
//    fun hello(){
//        logInfo("hello...")
//        Thread.sleep(3000)
//    }

}