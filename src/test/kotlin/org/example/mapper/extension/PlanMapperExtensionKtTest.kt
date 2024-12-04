package org.example.mapper.extension

import org.example.model.Plan
import org.example.model.Tariff
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PlanMapperExtensionKtTest {

    @Test
    fun `should correctly map Plan to PlanResponseDto`() {
        val mockPlan = createMockPlan(
            name = "Premium Plan",
            description = "Includes unlimited data and calls",
            startDate = LocalDate.of(2024, 1, 1),
            endDate = LocalDate.of(2024, 12, 31)
        )

        val responseDto = mockPlan.toResponseDto()

        assertEquals(mockPlan.id, responseDto.id)
        assertEquals(mockPlan.name, responseDto.name)
        assertEquals(mockPlan.description, responseDto.description)
        assertEquals(mockPlan.startDate, responseDto.startDate)
        assertEquals(mockPlan.endDate, responseDto.endDate)
        assertEquals(mockPlan.tariff.toResponseDto(), responseDto.tariff)
    }

    @Test
    fun `should handle null Plan name`() {
        val mockPlan = createMockPlan(
            name = null,
            description = "Includes unlimited data and calls",
            startDate = LocalDate.of(2024, 1, 1),
            endDate = LocalDate.of(2024, 12, 31)
        )

        val result = mockPlan.toResponseDto()

        assertEquals(null, result.name)
    }

    @Test
    fun `should map Page of Plan to Page of PlanResponseDto`() {
        val mockPlanFirst = createMockPlan(
            name = "Premium Plan",
            description = "Includes unlimited data and calls",
            startDate = LocalDate.of(2024, 1, 1),
            endDate = LocalDate.of(2024, 12, 31)
        )

        val mockPLanSecond = createMockPlan(
            name = "Family PLan",
            description = "Family PLan with limited data",
            startDate = LocalDate.of(2024, 1, 1),
            endDate = LocalDate.of(2024, 12, 31),
        )

        val planPage = createPlanPage(
            plans = listOf(mockPlanFirst, mockPLanSecond),
            page = 0,
            size = 2,
            totalElements = 2
        )

        val result = planPage.toResponseDtoPage()

        assertEquals(2, result.content.size)
        assertEquals(2, result.totalElements)
        assertEquals(0, result.number)
        assertEquals(2, result.size)
    }

    @Test
    fun `should handle empty Page of Tariff`() {
        val planPage = createPlanPage(
            plans = emptyList(),
            page = 0,
            size = 2,
            totalElements = 0
        )

        val result = planPage.toResponseDtoPage()

        assertTrue(result.content.isEmpty())
        assertEquals(0, result.totalElements)
        assertEquals(0, result.number)
        assertEquals(2, result.size)
    }

    private fun createMockPlan(
        id: UUID = UUID.randomUUID(),
        name: String?,
        description: String,
        startDate: LocalDate,
        endDate: LocalDate,
        tariff: Tariff = createMockTariff(
            name = "Basic Tariff",
            description = "Basic Tariff with limited data",
            monthlyCost = BigDecimal("10.0"),
            dataLimit = 100.0,
            voiceLimit = 200.0
        )
    ): Plan {
        val mockPLan = mock<Plan>()

        whenever(mockPLan.id).thenReturn(id)
        whenever(mockPLan.name).thenReturn(name)
        whenever(mockPLan.description).thenReturn(description)
        whenever(mockPLan.startDate).thenReturn(startDate)
        whenever(mockPLan.endDate).thenReturn(endDate)
        whenever(mockPLan.tariff).thenReturn(tariff)

        return mockPLan
    }

    private fun createPlanPage(plans: List<Plan>, page: Int, size: Int, totalElements: Long): Page<Plan> {
        return PageImpl(plans, PageRequest.of(page, size), totalElements)
    }

    private fun createMockTariff(
        id: UUID = UUID.randomUUID(),
        name: String?,
        description: String,
        monthlyCost: BigDecimal,
        dataLimit: Double,
        voiceLimit: Double
    ): Tariff {
        val mockTariff = mock<Tariff>()

        whenever(mockTariff.id).thenReturn(id)
        whenever(mockTariff.name).thenReturn(name)
        whenever(mockTariff.description).thenReturn(description)
        whenever(mockTariff.monthlyCost).thenReturn(monthlyCost)
        whenever(mockTariff.dataLimit).thenReturn(dataLimit)
        whenever(mockTariff.voiceLimit).thenReturn(voiceLimit)

        return mockTariff
    }

}