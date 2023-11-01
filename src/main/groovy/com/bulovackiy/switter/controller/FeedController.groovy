package com.bulovackiy.switter.controller

import com.bulovackiy.switter.helper.AuthHelper
import com.bulovackiy.switter.model.dto.PostDTO
import com.bulovackiy.switter.service.FeedService
import org.apache.commons.lang3.StringUtils
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class FeedController {

    FeedService feedService

    FeedController(FeedService feedService) {
        this.feedService = feedService
    }

    @GetMapping("/api/users/{userId}/feed")
    List<PostDTO> getUserFeed(@PathVariable String userId, Authentication authentication) {
        def principalId = AuthHelper.getUserId(authentication)

        if (StringUtils.equals(principalId, userId)) {
            return feedService.getPrincipalFeed(principalId)
        } else {
            return feedService.getUserFeed(userId)
        }
    }
}
