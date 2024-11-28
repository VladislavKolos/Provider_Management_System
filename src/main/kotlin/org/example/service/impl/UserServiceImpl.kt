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
    override fun getUserByEmail(email: String): User {
        return userRepository.findByEmail(email)
            ?: throw NoSuchElementException("User with this email: $email not found")
    }

    @Transactional(readOnly = true)
    override fun getUserByPhone(phone: String): User {
        return userRepository.findByPhone(phone)
            ?: throw NoSuchElementException("User with this phone number: $phone not found")
    }
}