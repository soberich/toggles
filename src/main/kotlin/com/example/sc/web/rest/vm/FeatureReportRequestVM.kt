package com.example.sc.web.rest.vm

import org.togglz.core.Feature

data class FeatureReportRequestVM(
    val featureRequest: FeatureRequestVM
)

data class FeatureRequestVM(
    val customerId: String,
    val features: List<FeatureNameVM>
)

class FeatureNameVM(val name: String) : Feature {
    override fun name() = name
}
