package org.example.model

import jakarta.persistence.*
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.Size
import java.math.BigDecimal
import java.time.LocalDate

@Entity
@Table(name = "promotion")
class Promotion(

    @field:Size(max = 100)
    @Column(name = "title", unique = true)
    var title: String,

    @field:Size(max = 100)
    @Column(name = "description")
    var description: String,

    @field:DecimalMin(value = "5")
    @Column(name = "discount_percentage")
    var discountPercentage: BigDecimal,

    @Column(name = "start_date")
    var startDate: LocalDate,

    @Column(name = "end_date")
    var endDate: LocalDate,

    @Id
    @Column(name = "promotion_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

)
