package com.bulovackiy.switter.service

import com.bulovackiy.switter.exception.NotFoundException
import com.bulovackiy.switter.model.dto.PostDTO
import com.bulovackiy.switter.model.dto.ReactionDTO
import com.bulovackiy.switter.repository.PostRepository
import com.bulovackiy.switter.repository.model.Post
import com.bulovackiy.switter.repository.model.Reaction
import org.springframework.stereotype.Service

import java.util.stream.Collectors

@Service
class PostService {

    PostRepository postRepository

    PostService(PostRepository postRepository) {
        this.postRepository = postRepository
    }

    PostDTO createPost(PostDTO postDTO, String userId) {
        def post = new Post()
        post.text = postDTO.text
        post.parent = userId

        return mapToPostDTO(postRepository.save(post))
    }

    PostDTO updatePost(String text, String userId, String postId) {
        def post = postRepository.findByIdAndParent(postId, userId)
                .orElseThrow {new NotFoundException("Post with id: " + postId + ", for user: " + userId + ", not found")}

        post.text = text

        return mapToPostDTO(postRepository.save(post))
    }

    private PostDTO mapToPostDTO(Post post) {
        def postDTO = new PostDTO()
        postDTO.id = post.id
        postDTO.text = post.text
        postDTO.parent = post.parent

        def reactions = post.reactions.stream()
                .map(this::mapToReactionDTO).collect(Collectors.toList())

        postDTO.reactions = reactions

        return postDTO
    }

    private ReactionDTO mapToReactionDTO(Reaction reaction) {
        def reactionDTO = new ReactionDTO()
        reactionDTO.id = reaction.id
        reactionDTO.type = reaction.type
        reactionDTO.parent = reaction.parent

        return reactionDTO
    }
}
