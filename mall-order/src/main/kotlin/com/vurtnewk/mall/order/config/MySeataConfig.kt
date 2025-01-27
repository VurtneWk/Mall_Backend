package com.vurtnewk.mall.order.config

import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.util.StringUtils
import io.seata.rm.datasource.DataSourceProxy
import javax.sql.DataSource

/**
 *
 * @author   vurtnewk
 * @since    2025/1/26 20:00
 */
@Configuration
class MySeataConfig {

    @Autowired
    lateinit var datasourceProperties: DataSourceProperties

    @Bean
    fun dataSource(datasourceProperties: DataSourceProperties): DataSource {
        val dataSource = datasourceProperties.initializeDataSourceBuilder().type(HikariDataSource::class.java).build()
        if (StringUtils.hasText(datasourceProperties.name)) {
            dataSource.poolName = datasourceProperties.name
        }
        return DataSourceProxy(dataSource)
    }

}