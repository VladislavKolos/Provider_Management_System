package org.example.mapper.extension

import org.example.model.Role
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull


class RoleMapperExtensionKtTest {

    @Test
    fun `should correctly map Role to RoleResponseDto`() {

        val generatedId = UUID.randomUUID()

        val mockRole = createMockRole(generatedId, "ROLE_ADMIN")

        val responseDto = mockRole.toResponseDto()

        assertNotNull(generatedId)
        assertEquals(generatedId, responseDto.id)
        assertEquals("ROLE_ADMIN", responseDto.name)
    }

    @Test
    fun `should throw exception when Role name is null`() {

        val mockRole = createMockRole(name = null)

        assertFailsWith<NullPointerException> {
            mockRole.toResponseDto()
        }
    }

    private fun createMockRole(id: UUID = UUID.randomUUID(), name: String?): Role {

        val mockRole = mock<Role>()
        whenever(mockRole.id).thenReturn(id)
        whenever(mockRole.name).thenReturn(name)

        return mockRole
    }


}