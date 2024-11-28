package org.example.service.impl

import org.example.model.Role
import org.example.repository.RoleRepository
import org.example.service.RoleService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class RoleServiceImpl(
    private val roleRepository: RoleRepository

) : RoleService {

    @Transactional(readOnly = true)
    override fun getRoleEntityById(id: UUID): Role {
        return roleRepository.findRoleById(id)
            ?: throw NoSuchElementException("Role with ID: $id not found")
    }
}