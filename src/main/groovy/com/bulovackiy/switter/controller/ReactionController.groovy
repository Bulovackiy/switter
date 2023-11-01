package com.bulovackiy.switter.controller

import com.bulovackiy.switter.helper.AuthHelper
import com.bulovackiy.switter.model.dto.ReactionDTO
import com.bulovackiy.switter.service.ReactionService
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
class ReactionController {

    UserService userService
    ReactionService reactionService

    ReactionController(UserService userService, ReactionService reactionService) {
        this.userService = userService
        this.reactionService = reactionService
    }

    @PostMapping("/api/posts/{postId}/reactions")
    ReactionDTO addReaction(@PathVariable String postId, @RequestBody ReactionDTO reaction,
                        Authentication authentication) {
        def userId = AuthHelper.getUserId(authentication)

        reaction.parent = userId
        return reactionService.createReaction(postId, reaction)
    }

    @PatchMapping("/api/posts/{postId}/reactions/{reactionId}")
    ReactionDTO updateReaction(@PathVariable String postId, @PathVariable String reactionId,
                            @RequestBody ReactionDTO reaction, Authentication authentication) {
        def userId = AuthHelper.getUserId(authentication)
        reaction.id = reactionId
        reaction.parent = userId
        return reactionService.updateReaction(postId, reaction)
    }

    @DeleteMapping("/api/posts/{postId}/reactions/{reactionId}")
    void deleteReaction(@PathVariable String postId, @PathVariable String reactionId,
                        Authentication authentication) {
        def userId = AuthHelper.getUserId(authentication)

        reactionService.deleteReaction(postId, userId, reactionId)
    }

    @GetMapping("/api/posts/{postId}/reactions")
    List<ReactionDTO> getReactionsForPost(@PathVariable String postId) {
        return reactionService.getReactionsForPost(postId)
    }
}
