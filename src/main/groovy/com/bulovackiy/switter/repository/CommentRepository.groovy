package com.bulovackiy.switter.repository

import com.bulovackiy.switter.repository.model.Comment
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface CommentRepository extends MongoRepository<Comment, String>{

    @Query("{postId:'?0'}")
    Optional<List<Comment>> findByPostId(String postId)

    @Query("{id:'?0', postId:'?1'}")
    Optional<Comment> findByIdAndPostId(String id, String postId)

    @Query("{id:'?0', postId:'?1', parent:'?2'}")
    Optional<Comment> findByIdAndPostIdAndParent(String id, String postId, String parent)
}
