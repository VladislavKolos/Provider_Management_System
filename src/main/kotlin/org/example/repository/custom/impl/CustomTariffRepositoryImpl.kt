package org.example.repository.custom.impl

import org.example.model.Tariff
import org.example.repository.custom.CustomTariffRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import java.math.BigDecimal

class CustomTariffRepositoryImpl(
    private val jdbcTemplate: JdbcTemplate

) : CustomTariffRepository {

    override fun findTariffsByCostRange(minCost: BigDecimal, maxCost: BigDecimal, pageable: Pageable): Page<Tariff>? {
        val countQuery = """
            SELECT COUNT(*) FROM tariff     
            WHERE monthly_cost BETWEEN ? AND ?
            """.trimIndent()

        val totalCount = jdbcTemplate.queryForObject(countQuery, Long::class.java, minCost, maxCost) ?: 0

        if (totalCount == 0L) {
            return Page.empty(pageable)
        }

        val dataQuery = """
            SELECT * FROM tariff
            WHERE monthly_cost BETWEEN ? AND ?
            LIMIT ? OFFSET ?
        """.trimIndent()

        val tariffs = jdbcTemplate.query(
            dataQuery, tariffRowMapper, minCost, maxCost, pageable.pageSize, pageable.offset
        )
        return PageImpl(tariffs, pageable, totalCount)
    }

    override fun findTariffsWithActivePlans(pageable: Pageable): Page<Tariff>? {
        val countQuery = """
            SELECT COUNT(DISTINCT t.id) 
            FROM tariff t 
            JOIN plan p ON t.id = p.tariff_id
            WHERE p.end_date > CURRENT_DATE
        """.trimIndent()

        val totalCount = jdbcTemplate.queryForObject(countQuery, Long::class.java) ?: 0

        if (totalCount == 0L) {
            return Page.empty(pageable)
        }

        val dataQuery = """
            SELECT DISTINCT t.*
            FROM tariff t
            JOIN plan p ON t.id = p.tariff_id
            WHERE p.end_date > CURRENT_DATE
            LIMIT ? OFFSET ?
        """.trimIndent()

        val tariffs = jdbcTemplate.query(
            dataQuery, tariffRowMapper, pageable.pageSize, pageable.offset
        )
        return PageImpl(tariffs, pageable, totalCount)
    }

    private val tariffRowMapper = RowMapper { rs, _ ->
        Tariff(
            name = rs.getString("tariff_name"),
            description = rs.getString("description"),
            monthlyCost = rs.getBigDecimal("monthly_cost"),
            dataLimit = rs.getDouble("data_limit"),
            voiceLimit = rs.getDouble("voice_limit"),
            plans = emptyList()
        )
    }
}