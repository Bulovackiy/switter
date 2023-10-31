package com.bulovackiy.switter.repository

import com.bulovackiy.switter.repository.model.User
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface UserRepository extends MongoRepository<User, String> {

    @Query("{email:'?0'}")
    Optional<User> findByEmail(String email)

    @Query("{username: '?0'}")
    Optional<User> findByUsername(String username)

    @Query("{phoneNumber: '?0'}")
    Optional<User> findByPhoneNumber(String phoneNumber)

    boolean existsByUsername(String username)
    boolean existsByEmail(String email)
    boolean existsByPhoneNumber(String phoneNumber)
}