package com.bulovackiy.switter.controller

import com.bulovackiy.switter.helper.AuthHelper
import com.bulovackiy.switter.model.dto.CommentDTO
import com.bulovackiy.switter.service.CommentService
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@RestController
class CommentController {

    CommentService commentService

    CommentController(CommentService commentService) {
        this.commentService = commentService
    }

    @PostMapping("/api/posts/{postId}/comments")
    CommentDTO createComment(@PathVariable String postId, @RequestBody CommentDTO commentDTO,
                             Authentication authentication) {
        def userId = AuthHelper.getUserId(authentication)

        return commentService.createComment(userId, postId, commentDTO)
    }

    @PatchMapping("/api/posts/{postId}/comments/{commentId}")
    CommentDTO updateComment(@PathVariable String postId, @PathVariable String commentId,
                             @RequestBody CommentDTO commentDTO, Authentication authentication) {
        def userId = AuthHelper.getUserId(authentication)

        return commentService.updateComment(userId, postId, commentId, commentDTO)
    }

    @GetMapping("/api/posts/{postId}/comments")
    List<CommentDTO> getPostComments(@PathVariable String postId) {
        return commentService.getPostComments(postId)
    }

    @GetMapping("/api/posts/{postId}/comments/{commentId}")
    CommentDTO getComment(@PathVariable String postId, @PathVariable String commentId) {
        return commentService.getComment(commentId, postId)
    }

    @DeleteMapping("/api/posts/{postId}/comments/{commentId}")
    void deleteComment(@PathVariable String postId, @PathVariable String commentId) {
        commentService.deleteComment(commentId, postId)
    }


}
