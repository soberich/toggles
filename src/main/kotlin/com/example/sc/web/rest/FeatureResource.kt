package com.example.sc.web.rest

import com.example.sc.config.CLOCK
import com.example.sc.security.getCurrentUserLogin
import com.example.sc.web.rest.vm.FeatureNameVM
import com.example.sc.web.rest.vm.FeatureReportRequestVM
import com.example.sc.web.rest.vm.FeatureReportVM
import com.example.sc.web.rest.vm.FeatureVM
import java.time.Instant
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.togglz.core.context.FeatureContext
import org.togglz.core.manager.FeatureManager
import org.togglz.core.repository.FeatureState
import org.togglz.kotlin.FeatureManagerSupport

@RestController
@RequestMapping("/api/v1")
class FeatureResource(private val featureManager: FeatureManager) {

    @field:PersistenceContext
    protected lateinit var em: EntityManager

    @PostMapping
    @RequestMapping("/features")
    fun listForCustomer(@Valid @RequestBody featureReportRequest: FeatureReportRequestVM): FeatureReportVM {
        return FeatureContext.getFeatureManager()
            .features
            .map(featureManager::getFeatureState)
            .map {
                FeatureVM(
                    name = it.feature.name(),
                    active = it.isEnabled,
                    expired = Instant.parse(it.getParameter("expiresOn")).isBefore(Instant.now(CLOCK)),
                    inverted = it.getParameter("inverted").toBoolean()
                )
            }.let(::FeatureReportVM)
    }

    @PostMapping
    @RequestMapping("/features/create")
    fun createNewToggle(): FeatureState {
        val id = "${getCurrentUserLogin().get()} -- ${Instant.now(CLOCK)}" // just dummy key to be changed later by user
        FeatureManagerSupport.enable { id } // creates underneath.
        return FeatureContext.getFeatureManager().getFeatureState(FeatureNameVM(id))
    }
}
