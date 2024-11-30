package org.example.service.impl

import org.example.model.User
import org.example.repository.UserRepository
import org.example.service.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    private val userRepository: UserRepository

) : UserService {

    @Transactional
    override fun save(user: User) {
        userRepository.save(user)
    }

    @Transactional(readOnly = true)
    override fun getUserByUsername(username: String): User {
        return userRepository.findByUsernameField(username)
            ?: throw NoSuchElementException("User with this Username: $username not found")
    }

    @Transactional(readOnly = true)
    override fun isUserBannedByEmail(email: String, statusName: String): Boolean {
        return userRepository.findBannedUserByEmail(email, statusName)?.let {
            throw IllegalStateException("The email: $email is banned and cannot be used for registration")
        } ?: true
    }

    @Transactional(readOnly = true)
    override fun isUserBannedByPhone(phone: String, statusName: String): Boolean {
        return userRepository.findBannedUserByPhone(phone, statusName)?.let {
            throw NoSuchElementException("The phone: $phone is banned and cannot be used for registration")
        } ?: true
    }
}