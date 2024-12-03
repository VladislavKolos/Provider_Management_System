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
            ?: throw NoSuchElementException("User with this Username: $username was not found in database")
    }

    @Transactional(readOnly = true)
    override fun checkIfUserBanned(email: String, phone: String) {

        if (userRepository.isUserBannedByEmail(email)) {
            throw IllegalStateException("The email: $email is banned and cannot be used for registration")
        }

        if (userRepository.isUserBannedByPhone(phone)) {
            throw IllegalStateException("The phone: $phone is banned and cannot be used for registration")
        }
    }
}