package com.bulovackiy.switter.controller

import com.bulovackiy.switter.exception.ForbiddenException
import com.bulovackiy.switter.model.TextUpdateModel
import com.bulovackiy.switter.model.dto.PostDTO
import com.bulovackiy.switter.service.PostService
import com.bulovackiy.switter.service.UserService
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class PostController {

    UserService userService
    PostService postService

    @PostMapping("/api/users/{userId}/post")
    PostDTO createPost(@PathVariable String userId, @RequestBody PostDTO post) {
        if (!userService.checkUserAccess(userId)) {
            throw new ForbiddenException("User don't have permission to use this endpoint")
        }

        return postService.createPost(post, userId)
    }

    @PatchMapping("/api/users/{userId}/post/{postId}/text")
    PostDTO updateText(@PathVariable String userId, @PathVariable String postId,
                       @RequestBody TextUpdateModel newText) {
        if (!userService.checkUserAccess(userId)) {
            throw new ForbiddenException("User don't have permission to use this endpoint")
        }

        return postService.updatePost(newText.newText, userId, postId)
    }

}
