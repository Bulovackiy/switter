package com.bulovackiy.switter.repository

import com.bulovackiy.switter.repository.model.Reaction
import org.springframework.data.mongodb.repository.MongoRepository

interface ReactionRepository extends MongoRepository<Reaction, String> {
}
