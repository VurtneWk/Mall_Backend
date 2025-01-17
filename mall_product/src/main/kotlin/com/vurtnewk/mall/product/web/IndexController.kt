package com.vurtnewk.mall.product.web

import com.vurtnewk.mall.product.service.CategoryService
import com.vurtnewk.mall.product.vo.Catalog2Vo
import org.redisson.api.RedissonClient
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.ResponseBody
import java.util.UUID
import java.util.concurrent.TimeUnit

/**
 * 首页
 * @author   vurtnewk
 * @since    2025/1/16 01:42
 */
@Controller
class IndexController(
    private val categoryService: CategoryService,
    private val mRedissonClient: RedissonClient,
    private val mRedisTemplate: StringRedisTemplate,
) {

    @GetMapping(value = ["/", "/index.html"])
    fun indexPage(model: Model): String {
        // 查出所有的1级分类
        val categoryEntities = categoryService.getTopLevelCategoryList()
        model.addAttribute("categories", categoryEntities)
        return "index"//已经有了默认前缀和后缀
    }

    @ResponseBody
    @GetMapping("/index/catalog.json")
    fun getCatalogJson(): Map<String, List<Catalog2Vo>> {
        return categoryService.getCatalogJson()
    }

    @ResponseBody
    @GetMapping("/hello")
    fun hello(): String {
        //1. 获取一把锁， 只要锁名一样，就是同一把锁
        val lock = mRedissonClient.getLock("myLock")
        //2. 加锁 有看门狗机制，可以自动续期
//        lock.lock()

        //10秒后自动解锁： 自动解锁时间一定要大于业务时间
        //锁时间到了以后，不会自动续期
        //最佳实战：使用不自动续期的，30S， 原因：业务都超30秒了 就已经是大问题了
        lock.lock(30, TimeUnit.SECONDS)

        //1)、锁的自动续期，如果业务超长，运行期间自动给锁续上心的30s，不用担心业务时间长，锁自动过期被删
        //2)、加锁的业务只要运行完成，就不会给当前续期，即使不手动解锁，锁默认在30s以后自动删除
        kotlin.runCatching {
            Thread.sleep(3000)
            println("执行业务逻辑...${Thread.currentThread().id}")
        }
        //解锁 假设解锁代码没有运行 redisson会不会出现死锁
        println("释放锁：${Thread.currentThread().id}")
        lock.unlock()

        return "hello"
    }


    //region 读写锁
    /**
     * # 读写锁
     * 保证一定能读到最新数据，修改期间，写锁是一个排他锁（互斥锁），读锁是一个共享锁
     * 写锁没释放 读就必须等待
     * 1. 读 + 读：相当于无锁
     * 2. 写 + 读：等待写锁释放
     * 3. 写 + 写：等待写锁释放
     * 4. 读 + 写：写锁等待读锁释放
     * 只要有写的存在，都必须等待
     */
    @GetMapping("/write")
    @ResponseBody
    fun writeValue(): String {
        val readWriteLock = mRedissonClient.getReadWriteLock("rw-lock")
        val writeLock = readWriteLock.writeLock()
        var uuid = ""
        kotlin.runCatching {
            //1. 改数据加写锁
            writeLock.lock()
            uuid = UUID.randomUUID().toString()
            Thread.sleep(30000)
            mRedisTemplate.opsForValue().set("writeValue", uuid)
        }
        writeLock.unlock()
        return uuid
    }

    @GetMapping("/read")
    @ResponseBody
    fun readValue(): String {
        val readWriteLock = mRedissonClient.getReadWriteLock("rw-lock")
        val readLock = readWriteLock.readLock()
        var uuid = ""
        kotlin.runCatching {
            readLock.lock()
            uuid = mRedisTemplate.opsForValue().get("writeValue").orEmpty()
        }
        readLock.unlock()
        return uuid
    }
    //endregion

    //region 闭锁 CountDownLatch
    /**
     * ## 闭锁
     * 模拟：放假、锁门
     * 1. 5个班全部走完，才可以锁大门
     */
    @GetMapping("/lockDoor")
    @ResponseBody
    fun lockDoor(): String {
        val door = mRedissonClient.getCountDownLatch("door")
        door.trySetCount(5)
        door.await()//等待闭锁完成
        return "锁门了"
    }

    @GetMapping("/go/{id}")
    @ResponseBody
    fun go(@PathVariable("id") id: Long): String {
        val door = mRedissonClient.getCountDownLatch("door")
        door.countDown() //计数减1
        return "$id 班的人都走了"
    }
    //endregion

    //region 信号量
    /**
     * ## 模拟 车库停车
     */
    @GetMapping("/park")
    @ResponseBody
    fun park(): String {
        val semaphore = mRedissonClient.getSemaphore("park")
//        semaphore.acquire()//占用一个车位
        val tryAcquire = semaphore.tryAcquire() //尝试占用一个，如果失败就直接过了，不阻塞等待
        return "car $tryAcquire"
    }

    @GetMapping("/level")
    @ResponseBody
    fun level(): String {
        val semaphore = mRedissonClient.getSemaphore("park")
        semaphore.release()//释放一个车位
        return "car level"
    }
    //endregion
}