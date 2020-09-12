package com.example.sc.web.rest.vm

data class FeatureReportVM(
    val features: List<FeatureVM>
)

data class FeatureVM(
    val name: String,
    val active: Boolean,
    val expired: Boolean,
    val inverted: Boolean
)
