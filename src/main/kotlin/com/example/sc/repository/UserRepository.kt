package com.example.sc.repository

import com.example.sc.domain.UserEntity
import java.time.Instant
import java.util.Optional
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Spring Data JPA repository for the [UserEntity] entity.
 */
@Repository
interface UserRepository : JpaRepository<UserEntity, Long> {

    fun findOneByActivationKey(activationKey: String): Optional<UserEntity>

    fun findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(dateTime: Instant): List<UserEntity>

    fun findOneByResetKey(resetKey: String): Optional<UserEntity>

    fun findOneByEmailIgnoreCase(email: String?): Optional<UserEntity>

    fun findOneByLogin(login: String): Optional<UserEntity>

    @EntityGraph(attributePaths = ["authorities"])
    fun findOneWithAuthoritiesByLogin(login: String): Optional<UserEntity>

    @EntityGraph(attributePaths = ["authorities"])
    fun findOneWithAuthoritiesByEmailIgnoreCase(email: String): Optional<UserEntity>

    fun findAllByLoginNot(pageable: Pageable, login: String): Page<UserEntity>
}
