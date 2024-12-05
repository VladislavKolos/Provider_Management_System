package org.example.mapper.extension

import org.example.model.Status
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class StatusMapperExtensionKtTest {

    @Test
    fun `should correctly map Status to StatusResponseDto`() {
        val generatedId = UUID.randomUUID()

        val mockStatus = createMockStatus(generatedId, "active")

        val responseDto = mockStatus.toResponseDto()

        assertNotNull(generatedId)
        assertEquals(generatedId, responseDto.id)
        assertEquals("active", responseDto.name)
    }

    @Test
    fun `should handle null Status name`() {
        val mockStatus = createMockStatus(name = null)

        val result = mockStatus.toResponseDto()

        assertEquals(null, result.name)
    }

    private fun createMockStatus(id: UUID = UUID.randomUUID(), name: String?): Status {
        val mockStatus = mock<Status>()

        whenever(mockStatus.id).thenReturn(id)
        whenever(mockStatus.name).thenReturn(name)

        return mockStatus
    }


}