package org.example.model

import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Size
import java.time.LocalDateTime

@Entity
@Table(name = "email_token")
class EmailToken(

    @Column(name = "token")
    var token: String,

    @field:Email
    @Column(name = "email")
    var email: String,

    @field:Size(min = 3, max = 32)
    @Column(name = "username")
    var username: String,

    @field:Size(min = 10, max = 18)
    @Column(name = "phone")
    var phone: String,

    @Column(name = "expiry_date")
    var expiryDate: LocalDateTime,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User,

    @Id
    @Column(name = "email_token_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null
)
