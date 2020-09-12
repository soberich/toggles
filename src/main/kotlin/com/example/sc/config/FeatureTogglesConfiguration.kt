package com.example.sc.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectReader
import com.fasterxml.jackson.databind.ObjectWriter
import com.fasterxml.jackson.module.kotlin.jacksonTypeRef
import javax.sql.DataSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn
import org.springframework.context.annotation.Primary
import org.togglz.core.context.StaticFeatureManagerProvider
import org.togglz.core.manager.FeatureManager
import org.togglz.core.manager.FeatureManagerBuilder
import org.togglz.core.repository.StateRepository
import org.togglz.core.repository.jdbc.JDBCStateRepository
import org.togglz.core.repository.util.MapSerializer
import org.togglz.core.spi.FeatureProvider
import org.togglz.core.user.UserProvider
import org.togglz.kotlin.FeatureManagerSupport

@Configuration(proxyBeanMethods = false)
class FeatureTogglesConfiguration(objectMapper: ObjectMapper) {

    private val reader: ObjectReader = objectMapper.readerForMapOf(String::class.java)
    private val writer: ObjectWriter = objectMapper.writerFor(jacksonTypeRef<Map<String, String>>())

    @Bean
    @Primary
    fun featureManager(
        stateRepository: StateRepository,
        userProvider: UserProvider,
        featureProvider: FeatureProvider
    ): FeatureManager {

        val featureManager =
            FeatureManagerBuilder()
                .featureProvider(featureProvider)
                .stateRepository(stateRepository)
                .userProvider(userProvider)
                .build()

        FeatureManagerSupport.enableAllFeatures(featureManager)

        StaticFeatureManagerProvider.setFeatureManager(featureManager)
        return featureManager
    }

    @Bean
    @DependsOn("liquibase")
    fun stateRepository(dataSource: DataSource): StateRepository =
        JDBCStateRepository
            .newBuilder(dataSource)
            .tableName("feature_toggle_state")
            .createTable(false)
            .serializer(object : MapSerializer {
                override fun serialize(map: MutableMap<String, String>?): String = writer.writeValueAsString(map)
                override fun deserialize(data: String?): MutableMap<String, String> = reader.readValue(data)
            }).build()
}
