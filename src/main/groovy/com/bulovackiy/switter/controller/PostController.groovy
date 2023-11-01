package com.bulovackiy.switter.controller

import com.bulovackiy.switter.exception.ForbiddenException
import com.bulovackiy.switter.helper.AuthHelper
import com.bulovackiy.switter.model.ContentModel
import com.bulovackiy.switter.model.dto.PostDTO
import com.bulovackiy.switter.service.PostService
import com.bulovackiy.switter.service.UserService
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class PostController {

    UserService userService
    PostService postService

    PostController(UserService userService, PostService postService) {
        this.userService = userService
        this.postService = postService
    }

    @GetMapping("/api/posts/{postId}")
    PostDTO getPost(@PathVariable String postId, Authentication authentication) {
        def userId = AuthHelper.getUserId(authentication)
        return postService.getPost(postId, userId)
    }

    @GetMapping("/api/users/{userId}/posts")
    List<PostDTO> getPostsForUser(@PathVariable String userId,
                         Authentication authentication) {
        if (!AuthHelper.checkUserAccess(userId, authentication)) {
            throw new ForbiddenException("User don't have permission to use this endpoint")
        }

        postService.getPostsForUser(userId)
    }

    @PostMapping("/api/posts")
    PostDTO createPost(@RequestBody PostDTO post, Authentication authentication) {
        def userId = AuthHelper.getUserId(authentication)
        return postService.createPost(post, userId)
    }

    @PatchMapping("/api/posts/{postId}/content")
    PostDTO updateText(@PathVariable String postId, @RequestBody ContentModel contentUpdate,
                       Authentication authentication) {
        def userId = AuthHelper.getUserId(authentication)

        return postService.updatePost(contentUpdate.content, userId, postId)
    }

    @DeleteMapping("/api/posts/{postId}")
    void deletePost(@PathVariable String postId, Authentication authentication) {
        def userId = AuthHelper.getUserId(authentication)

        postService.deletePost(userId, postId)
    }
}
