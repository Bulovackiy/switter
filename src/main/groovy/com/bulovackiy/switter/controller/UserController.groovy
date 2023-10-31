package com.bulovackiy.switter.controller

import com.bulovackiy.switter.exception.ForbiddenException
import com.bulovackiy.switter.model.dto.FollowingDTO
import com.bulovackiy.switter.model.dto.UserDTO
import com.bulovackiy.switter.service.UserService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController {

    private UserService userService

    UserController(UserService userService) {
        this.userService = userService
    }

    @PostMapping("/api/users/{userId}/following")
    UserDTO addFollowing(@PathVariable String userId, @RequestBody FollowingDTO following) {
        if (!userService.checkUserAccess(userId)) {
            throw new ForbiddenException("User don't have permission to use this endpoint")
        }

        return userService.addFollowingToUser(userId, following.userId)
    }

    @DeleteMapping("/api/users/{userId}/following/{followingId}")
    UserDTO deleteFollowing(@PathVariable String userId, @PathVariable String followingId) {
        if (!userService.checkUserAccess(userId)) {
            throw new ForbiddenException("User don't have permission to use this endpoint")
        }

        return userService.deleteFollowing(userId, followingId)
    }


}
