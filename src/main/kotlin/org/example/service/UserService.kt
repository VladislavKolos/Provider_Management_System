package org.example.service

import org.example.model.User
import org.springframework.stereotype.Component

@Component
interface UserService {

    fun save(user: User)

    fun getUserByUsername(username: String): User

    fun checkIfUserBanned(email: String, phone: String)
}