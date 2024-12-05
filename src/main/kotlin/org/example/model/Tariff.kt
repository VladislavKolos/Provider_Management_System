package org.example.model

import jakarta.persistence.*
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Size
import java.math.BigDecimal

@Entity
@Table(name = "tariff")
class Tariff(

    @Column(name = "tariff_name", unique = true)
    var name: String,

    @field:Size(max = 50)
    @Column(name = "description")
    var description: String,

    @field:DecimalMin(value = "4.99")
    @Column(name = "monthly_cost")
    var monthlyCost: BigDecimal,

    @field:Min(value = 50)
    @Max(value = 100000)
    @Column(name = "data_limit")
    var dataLimit: Double,

    @field:Min(value = 50)
    @field:Max(value = 10000)
    @Column(name = "voice_limit")
    var voiceLimit: Double,

    @OneToMany(mappedBy = "tariff")
    var plans: List<Plan>

) : BaseEntity()
