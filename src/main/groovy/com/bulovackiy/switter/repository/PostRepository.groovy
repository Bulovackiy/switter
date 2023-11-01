package com.bulovackiy.switter.repository

import com.bulovackiy.switter.repository.model.Post
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface PostRepository extends MongoRepository<Post, String>{

    @Query("{id:'?0', parent:'?1'}")
    Optional<Post> findByIdAndParent(String id, String parent)

    @Query("{parent:'?0'}")
    Optional<List<Post>> findByParent(String parent)

    @Query("{id:'?0', reactions.parent:'?1'}")
    boolean existsReaction(String postId, String reactionParent)
}