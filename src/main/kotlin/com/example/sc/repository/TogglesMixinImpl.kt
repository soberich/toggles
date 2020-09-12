// package com.example.sc.repository
//
// import com.example.sc.domain.FeatureEntity
// import com.example.sc.domain.FeatureToggleStateEntity
// import javax.persistence.EntityManager
// import org.springframework.data.jpa.repository.JpaContext
// import org.springframework.stereotype.Repository
// import org.togglz.core.Feature
// import org.togglz.core.repository.FeatureState
// import org.togglz.core.repository.StateRepository
//
// /**
// * Spring Data JPA repository for the [FeatureToggleStateEntity] entity.
// */
// @Repository
// class TogglesMixinImpl(
//    context: JpaContext,
//    private val em: EntityManager = context.getEntityManagerByManagedType(FeatureToggleStateEntity::class.java)
// ) : StateRepository {
//
//    override fun getFeatureState(feature: Feature?): FeatureState? =
//        FeatureState(FeatureEntity(""))
// //        em.createNamedQuery("Feature.getState", FeatureState::class.java)
// //            .setParameter(FeatureToggleStateEntity_.TECHNICAL_NAME, feature!!.name())
// //            .singleResult
//
//    override fun setFeatureState(state: FeatureState?) {
//        em.merge(state)
//    }
// }
