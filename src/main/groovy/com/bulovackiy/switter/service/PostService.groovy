package com.bulovackiy.switter.service

import com.bulovackiy.switter.exception.NotFoundException
import com.bulovackiy.switter.helper.MapHelper
import com.bulovackiy.switter.model.dto.PostDTO
import com.bulovackiy.switter.repository.PostRepository
import com.bulovackiy.switter.repository.model.Post
import org.springframework.stereotype.Service

import java.util.stream.Collectors

@Service
class PostService {

    PostRepository postRepository

    PostService(PostRepository postRepository) {
        this.postRepository = postRepository
    }

    PostDTO getPost(String postId, String userId) {
        def post = postRepository.findByIdAndParent(postId, userId)
                .orElseThrow {new NotFoundException("Post with id: " + postId + ", for user: " + userId + ", not found")}

        return MapHelper.mapToPostDTO(post)
    }

    List<PostDTO> getPostsForUser(String userId) {
        def posts = postRepository.findByParent(userId)
                .orElseThrow {new NotFoundException("Posts for user: " + userId + ", not found")}

        return posts.stream().map(MapHelper::mapToPostDTO).collect(Collectors.toList())
    }

    PostDTO createPost(PostDTO postDTO, String userId) {
        def post = new Post()
        post.text = postDTO.text
        post.parent = userId

        return MapHelper.mapToPostDTO(postRepository.save(post))
    }

    PostDTO updatePost(String text, String userId, String postId) {
        def post = postRepository.findByIdAndParent(postId, userId)
                .orElseThrow {new NotFoundException("Post with id: " + postId + ", for user: " + userId + ", not found")}

        post.text = text

        return MapHelper.mapToPostDTO(postRepository.save(post))
    }

    void deletePost(String userId, String postId) {
        def post = postRepository.findByIdAndParent(postId, userId)
                .orElseThrow {new NotFoundException("Post with id: " + postId + ", for user: " + userId + ", not found")}

        postRepository.delete(post)
    }
}
