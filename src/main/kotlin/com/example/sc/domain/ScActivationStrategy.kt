@file:Suppress("PropertyName")

package com.example.sc.domain

import com.example.sc.config.CLOCK
import org.togglz.core.activation.Parameter
import org.togglz.core.activation.ParameterBuilder
import org.togglz.core.repository.FeatureState
import org.togglz.core.spi.ActivationStrategy
import org.togglz.core.user.FeatureUser
import java.time.Instant
import java.util.*

class ScActivationStrategy : ActivationStrategy {

    override fun getId(): String = _id

    override fun getName(): String = _name

    override fun isActive(featureState: FeatureState, user: FeatureUser): Boolean {

        val parameters = featureState.parameterMap
        val customerIds: String by parameters

        return featureState.isEnabled &&
            !parameters["inverted"].toBoolean() &&
            parameters["expiresOn"]?.let { Instant.parse(it).isBefore(Instant.now(CLOCK)) } ?: true &&
            System.lineSeparator() + user.name + System.lineSeparator() in System.lineSeparator() + customerIds + System.lineSeparator()
    }

    override fun getParameters(): Array<Parameter> = arrayOf(
        ParameterBuilder.create("customerIds").label("Customer Ids").optional().largeText()
            .description("Each line should contain single customer id"),
        ParameterBuilder.create("inverted").label("Inverted").optional().matching("true|false"),
        ParameterBuilder.create("expiresOn").label("Date").optional().matching("\\d{4}\\-\\d{2}\\-\\d{2}")
            .description("Expiration date of the feature. Format: 2022-12-31"),
        ParameterBuilder.create("archived").label("Archived").optional().matching("true|false"),
    )

    companion object {
        val _id: String by lazy { UUID.randomUUID().toString() }
        const val _name: String = "Swisscom Activation Strategy"
    }
}
