package com.bulovackiy.switter.service

import com.bulovackiy.switter.exception.NotFoundException
import com.bulovackiy.switter.helper.MapHelper
import com.bulovackiy.switter.model.dto.PostDTO
import com.bulovackiy.switter.repository.CommentRepository
import com.bulovackiy.switter.repository.PostRepository
import com.bulovackiy.switter.repository.ReactionRepository
import com.bulovackiy.switter.repository.UserRepository
import org.springframework.stereotype.Service

import java.util.stream.Collectors

@Service
class FeedService {

    PostRepository postRepository
    ReactionRepository reactionRepository
    CommentRepository commentRepository
    UserRepository userRepository

    FeedService(PostRepository postRepository, ReactionRepository reactionRepository, CommentRepository commentRepository, UserRepository userRepository) {
        this.postRepository = postRepository
        this.reactionRepository = reactionRepository
        this.commentRepository = commentRepository
        this.userRepository = userRepository
    }

    List<PostDTO> getUserFeed(String userId) {
        def posts = postRepository.findByParent(userId).orElse(Collections.emptyList())
        if (posts) {
            def postDTOs = posts.stream().map(MapHelper::mapToPostDTO).toList()
            postDTOs.forEach {
                it.reactions = reactionRepository.findByPostId(it.id)
                        .orElse([])
                        .stream().map(MapHelper::mapToReactionDTO).collect(Collectors.toSet())
                it.comments = commentRepository.findByPostId(it.id)
                        .orElse([])
                        .stream().map(MapHelper::mapToCommentDTO).collect(Collectors.toSet())
            }

            return postDTOs
        }

        return []
    }

    List<PostDTO> getPrincipalFeed(String userId) {
        def user = userRepository.findById(userId)
                .orElseThrow {new NotFoundException("user with id: " + userId + " not found")}
        def following = user.following
        def posts = postRepository.findByParent(userId).orElse(Collections.emptyList())

        if (following) {
            following.forEach {posts.addAll(postRepository.findByParent(it).orElse(Collections.emptyList()))}
        }

        if (posts) {
            def postDTOs = posts.stream().map(MapHelper::mapToPostDTO).toList()
            postDTOs.forEach {
                it.reactions = reactionRepository.findByPostId(it.id)
                        .orElse([])
                        .stream().map(MapHelper::mapToReactionDTO).collect(Collectors.toSet())
                it.comments = commentRepository.findByPostId(it.id)
                        .orElse([])
                        .stream().map(MapHelper::mapToCommentDTO).collect(Collectors.toSet())
            }

            return postDTOs
        }

        return []
    }
}
