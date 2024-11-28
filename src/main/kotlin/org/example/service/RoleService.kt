package org.example.service

import org.example.model.Role
import org.springframework.stereotype.Component
import java.util.*

@Component
interface RoleService {

    fun getRoleEntityById(id: UUID): Role
}