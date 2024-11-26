package org.example.model

import jakarta.persistence.*
import jakarta.validation.constraints.Size

@Entity
@Table(name = "subscription")
class Subscription(

    @field:Size(min = 6, max = 20)
    @Column(name = "status")
    var status: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    var plan: Plan,

    @Id
    @Column(name = "subscription_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null
)
