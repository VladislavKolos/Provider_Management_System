package org.example.mapper.extension

import org.example.model.Role
import org.example.model.Status
import org.example.model.User
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.util.*
import java.util.stream.Stream
import kotlin.test.assertEquals

class UserMapperExtensionKtTest {

    companion object {

        @JvmStatic
        fun userTestArguments(): Stream<Arguments> = Stream.of(
            Arguments.of("Vladislav_Client", "vlad1907@mail.ru", "+375334567891"),
            Arguments.of(null, "vlad1907@mail.ru", "+375334567891"),
            Arguments.of("Vladislav_Client", null, "+375334567891"),
            Arguments.of("Vladislav_Client", "vlad1907@mail.ru", null)
        )
    }

    @ParameterizedTest
    @MethodSource("userTestArguments")
    fun `should map User fields to UserResponseDto correctly`(nameField: String?, email: String?, phone: String?) {
        val mockUser = createMockUser(nameField = nameField, email = email, phone = phone)

        val responseDto = mockUser.toResponseDto()

        assertEquals(mockUser.id, responseDto.id)
        assertEquals(mockUser.usernameField, responseDto.username)
        assertEquals(mockUser.email, responseDto.email)
        assertEquals(mockUser.phone, responseDto.phone)
        assertEquals(mockUser.role.toResponseDto(), responseDto.role)
        assertEquals(mockUser.status.toResponseDto(), responseDto.status)
    }

    private fun createMockUser(
        id: UUID = UUID.randomUUID(),
        nameField: String?,
        email: String?,
        phone: String?,
        role: Role = createMockRole(name = "ROLE_CLIENT"),
        status: Status = createMockStatus(name = "active")
    ): User {
        val mockUser = mock<User>()

        whenever(mockUser.id).thenReturn(id)
        whenever(mockUser.usernameField).thenReturn(nameField)
        whenever(mockUser.email).thenReturn(email)
        whenever(mockUser.phone).thenReturn(phone)
        whenever(mockUser.role).thenReturn(role)
        whenever(mockUser.status).thenReturn(status)

        return mockUser
    }

    private fun createMockRole(id: UUID = UUID.randomUUID(), name: String): Role {
        val mockRole = mock<Role>()

        whenever(mockRole.id).thenReturn(id)
        whenever(mockRole.name).thenReturn(name)

        return mockRole
    }

    private fun createMockStatus(id: UUID = UUID.randomUUID(), name: String): Status {
        val mockStatus = mock<Status>()

        whenever(mockStatus.id).thenReturn(id)
        whenever(mockStatus.name).thenReturn(name)

        return mockStatus
    }
}