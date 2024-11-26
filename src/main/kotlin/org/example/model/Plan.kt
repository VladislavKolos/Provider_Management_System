package org.example.model

import jakarta.persistence.*
import jakarta.validation.constraints.Size
import java.time.LocalDate

@Entity
@Table(name = "plan")
class Plan(

    @field:Size(min = 1, max = 50)
    @Column(name = "plan_name", unique = true)
    var name: String,

    @field:Size(max = 50)
    @Column(name = "description")
    var description: String,

    @Column(name = "start_date")
    var startDate: LocalDate,

    @Column(name = "end_date")
    var endDate: LocalDate,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tariff_id")
    var tariff: Tariff,

    @Id
    @Column(name = "plan_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

)
