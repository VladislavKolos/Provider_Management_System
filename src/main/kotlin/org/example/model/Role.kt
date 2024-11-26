package org.example.model

import jakarta.persistence.*
import jakarta.validation.constraints.Size

@Entity
@Table(name = "role")
class Role(

    @field:Size(min = 2, max = 50)
    @Column(name = "role_name")
    var name: String,

    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

)