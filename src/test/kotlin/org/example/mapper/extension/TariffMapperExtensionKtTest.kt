package org.example.mapper.extension

import org.example.model.Tariff
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import java.math.BigDecimal
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TariffMapperExtensionKtTest {

    @Test
    fun `should correctly map Tariff to TariffResponseDto`() {
        val mockTariff = createMockTariff(
            name = "Basic Tariff",
            description = "Basic Tariff with limited data",
            monthlyCost = BigDecimal("10.0"),
            dataLimit = 100.0,
            voiceLimit = 200.0
        )

        val responseDto = mockTariff.toResponseDto()

        assertEquals(mockTariff.id, responseDto.id)
        assertEquals(mockTariff.name, responseDto.name)
        assertEquals(mockTariff.description, responseDto.description)
        assertEquals(mockTariff.monthlyCost, responseDto.monthlyCost)
        assertEquals(mockTariff.dataLimit, responseDto.dataLimit)
        assertEquals(mockTariff.voiceLimit, responseDto.voiceLimit)
    }

    @Test
    fun `should handle null Tariff name`() {
        val mockTariff = createMockTariff(
            name = null,
            description = "Basic Tariff with limited data",
            monthlyCost = BigDecimal("10.0"),
            dataLimit = 100.0,
            voiceLimit = 200.0
        )

        val result = mockTariff.toResponseDto()

        assertEquals(null, result.name)
    }

    @Test
    fun `should map Page of Tariff to Page of TariffResponseDto`() {
        val mockTariffFirst = createMockTariff(
            name = "Basic Tariff",
            description = "Basic Tariff with limited data",
            monthlyCost = BigDecimal("10.0"),
            dataLimit = 100.0,
            voiceLimit = 200.0
        )

        val mockTariffSecond = createMockTariff(
            name = "Family Tariff",
            description = "Family Tariff with limited data",
            monthlyCost = BigDecimal("20.0"),
            dataLimit = 200.0,
            voiceLimit = 400.0
        )

        val tariffPage = createTariffPage(
            tariffs = listOf(mockTariffFirst, mockTariffSecond),
            page = 0,
            size = 2,
            totalElements = 2
        )

        val result = tariffPage.toResponseDtoPage()

        assertEquals(2, result.content.size)
        assertEquals(2, result.totalElements)
        assertEquals(0, result.number)
        assertEquals(2, result.size)
    }

    @Test
    fun `should handle empty Page of Tariff`() {
        val tariffPage = createTariffPage(
            tariffs = emptyList(),
            page = 0,
            size = 2,
            totalElements = 0
        )

        val result = tariffPage.toResponseDtoPage()

        assertTrue(result.content.isEmpty())
        assertEquals(0, result.totalElements)
        assertEquals(0, result.number)
        assertEquals(2, result.size)
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

    private fun createTariffPage(tariffs: List<Tariff>, page: Int, size: Int, totalElements: Long): Page<Tariff> {
        return PageImpl(tariffs, PageRequest.of(page, size), totalElements)
    }
}