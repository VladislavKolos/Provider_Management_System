package org.example.model

import jakarta.persistence.*
import jakarta.validation.constraints.Size

@Entity
@Table(name = "status")
class Status(

    @field:Size(min = 6, max = 8)
    @Column(name = "status_name")
    var name: String

) : BaseEntity()
