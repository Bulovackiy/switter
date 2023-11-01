package com.bulovackiy.switter.repository

import com.bulovackiy.switter.repository.model.Reaction
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface ReactionRepository extends MongoRepository<Reaction, String> {

    @Query("{id:'?0', parent:'?1', postId:'?2'}")
    Optional<Reaction> findByIdAndParentAndPostId(String id, String parent, String postId)

    @Query("{postId:'?0'}")
    Optional<List<Reaction>> findByPostId(String postId)

    boolean existsByIdAndParentAndPostId(String id, String parent, String postId)
}
