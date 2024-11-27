package org.example.repository

import org.example.model.Subscription
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface SubscriptionRepository : JpaRepository<Subscription, UUID> {

    fun findByUserIdAndStatus(userId: UUID, status: String): Subscription?

    fun existsByUserIdAndPlanIdAndStatus(userId: UUID, planId: UUID, status: String): Boolean

    fun existsByUserId(userId: UUID): Boolean
}