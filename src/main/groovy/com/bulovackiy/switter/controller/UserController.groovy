package com.bulovackiy.switter.controller

import com.bulovackiy.switter.exception.ForbiddenException
import com.bulovackiy.switter.helper.AuthHelper
import com.bulovackiy.switter.model.SignupModel
import com.bulovackiy.switter.model.UpdateUserModel
import com.bulovackiy.switter.model.dto.FollowingDTO
import com.bulovackiy.switter.model.dto.UserDTO
import com.bulovackiy.switter.service.UserService
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PatchMapping
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

    @PatchMapping("/api/users/{userId}")
    UserDTO updateUser(@PathVariable String userId, @RequestBody UpdateUserModel updateUser,
                       Authentication authentication) {
        if (!AuthHelper.checkUserAccess(userId, authentication)) {
            throw new ForbiddenException("User don't have permission to use this endpoint")
        }

        return userService.updateUser(updateUser, userId)
    }

    @PostMapping("/api/users/{userId}/following")
    UserDTO addFollowing(@PathVariable String userId, @RequestBody FollowingDTO following,
                         Authentication authentication) {
        if (!AuthHelper.checkUserAccess(userId, authentication)) {
            throw new ForbiddenException("User don't have permission to use this endpoint")
        }

        return userService.addFollowingToUser(userId, following.userId)
    }

    @DeleteMapping("/api/users/{userId}/following/{followingId}")
    UserDTO deleteFollowing(@PathVariable String userId, @PathVariable String followingId,
                            Authentication authentication) {
        if (!AuthHelper.checkUserAccess(userId, authentication)) {
            throw new ForbiddenException("User don't have permission to use this endpoint")
        }

        return userService.deleteFollowing(userId, followingId)
    }


}
