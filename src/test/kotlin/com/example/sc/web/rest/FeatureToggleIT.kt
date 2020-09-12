package com.example.sc.web.rest

import com.example.sc.FeaturesApp
import com.example.sc.config.JacksonConfiguration
import com.example.sc.domain.FeatureToggleStateEntity
import com.example.sc.repository.UserRepository
import com.example.sc.security.ADMIN
import com.example.sc.service.mapper.UserMapper
import javax.persistence.EntityManager
import org.apache.commons.lang3.RandomStringUtils
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional

/**
 * TODO: Finish tests
 * Integration tests for the [UserResource] REST controller.
 */
@AutoConfigureMockMvc
@WithMockUser(authorities = [ADMIN])
@SpringBootTest(classes = [FeaturesApp::class, JacksonConfiguration::class])
class FeatureToggleIT {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var userMapper: UserMapper

    @Autowired
    private lateinit var em: EntityManager

    @Autowired
    private lateinit var restFeatureMockMvc: MockMvc

    private lateinit var feature: FeatureToggleStateEntity

    @BeforeEach
    fun initTest() {
        feature = createEntity(em)
        feature.apply {
            featureName = DEFAULT_FEATURE_NAME
            strategyId = DEFAULT_STRATEGY_ID
        }
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    fun createFeature() {
        val databaseSizeBeforeCreate = em.findAll<FeatureToggleStateEntity>().size

        val name =
        restFeatureMockMvc.perform(
            post("/api/v1/features/create")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk)
        .andDo { MockMvcResultHandlers.print() }
        .andReturn()
        .response
        .contentAsString
        .let { mapper.readTree(it).at("/feature/name").asText() }

        // Create the Feature
        //language=JSON
        val request = """
            {
              "featureRequest": {
                 "customerId": "1",
                  "features": [
                   {"name": "$name"},
                   {"name": "my-feature-b"},
                   {"name": "my-feature-c"},
                   {"name": "my-feature-d"}
                 ]
              }
            }
        """.trimIndent()

        restFeatureMockMvc.perform(
            post("/api/v1/features")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        ).andExpect(status().isOk)

        assertPersistedFeatures {
            // Validate the User in the database
            assertThat(this).hasSize(databaseSizeBeforeCreate + 1)
            val testUser = this.first { it.featureName == name }
            assertThat(testUser.featureName).isEqualTo(name)
            assertThat(testUser.strategyId).isNull()
            assertThat(testUser.archived).isNull()
            assertThat(testUser.enabled).isTrue()
        }
    }

    companion object {

        private const val DEFAULT_FEATURE_NAME = "johndoe"
        private const val UPDATED_LOGIN = "jhipster"

        private const val DEFAULT_ID = 1L

        private const val DEFAULT_PASSWORD = "passjohndoe"
        private const val UPDATED_PASSWORD = "passjhipster"

        private const val DEFAULT_STRATEGY_ID = "johndoe@localhost"
        private const val UPDATED_EMAIL = "jhipster@localhost"

        private const val DEFAULT_FIRSTNAME = "john"
        private const val UPDATED_FIRSTNAME = "jhipsterFirstName"

        private const val DEFAULT_LASTNAME = "doe"
        private const val UPDATED_LASTNAME = "jhipsterLastName"

        private const val DEFAULT_IMAGEURL = "http://placehold.it/50x50"
        private const val UPDATED_IMAGEURL = "http://placehold.it/40x40"

        private const val DEFAULT_LANGKEY = "en"
        private const val UPDATED_LANGKEY = "fr"

        /**
         * Create a User.
         *
         * This is a static method, as tests for other entities might also need it,
         * if they test an entity which has a required relationship to the User entity.
         */
        @JvmStatic
        fun createEntity(em: EntityManager?): FeatureToggleStateEntity {
            return FeatureToggleStateEntity().apply {
                featureName = DEFAULT_FEATURE_NAME + RandomStringUtils.randomAlphabetic(5)
                enabled = true
                strategyId = DEFAULT_STRATEGY_ID
                strategyParams = "customerIds=$DEFAULT_ID"
                archived = false
            }
        }
    }

    fun assertPersistedFeatures(featureAssertion: List<FeatureToggleStateEntity>.() -> Unit) {
        em.findAll<FeatureToggleStateEntity>().featureAssertion()
    }
}
