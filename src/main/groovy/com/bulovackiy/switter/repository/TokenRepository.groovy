package com.bulovackiy.switter.repository

import com.bulovackiy.switter.repository.model.Token
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface TokenRepository extends MongoRepository<Token, String> {

    boolean existsByUsernameAndToken(String username, String token)

    Token findByUsername(String username)
}