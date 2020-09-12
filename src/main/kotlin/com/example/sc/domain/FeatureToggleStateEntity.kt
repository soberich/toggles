package com.example.sc.domain

import javax.persistence.*
import javax.persistence.AccessType.PROPERTY
import kotlin.properties.Delegates
import org.hibernate.annotations.Type

@Entity
@Table(name = "feature_toggle_state")
@Access(PROPERTY)
class FeatureToggleStateEntity : AbstractAuditingEntity() {
    @get:Id
    @delegate:Transient
    var featureName: String by Delegates.notNull()

    @get:
    [Column(name = "feature_enabled") Type(type = "org.hibernate.type.NumericBooleanType")]
    var enabled: Boolean = false

    var strategyId: String? = null

    var strategyParams: String? = null

    var archived: Boolean? = false

    override fun equals(other: Any?): Boolean = when {
        this === other -> true
        other !is FeatureToggleStateEntity -> false
        other.featureName == null || featureName == null -> false
        else -> featureName == other.featureName
    }

    // constant value returned to avoid entity inequality to itself before and after it's update/merge
    override fun hashCode(): Int = 42
}
