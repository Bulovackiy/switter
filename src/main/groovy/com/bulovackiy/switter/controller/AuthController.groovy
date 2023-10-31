package com.bulovackiy.switter.controller

import com.bulovackiy.switter.model.SigninModel
import com.bulovackiy.switter.model.SignupModel
import com.bulovackiy.switter.security.jwt.JwtUtils
import com.bulovackiy.switter.security.service.UserDetailsImpl
import com.bulovackiy.switter.service.TokenService
import com.bulovackiy.switter.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController {

    private UserService userService
    private TokenService tokenService
    private AuthenticationManager authenticationManager
    private JwtUtils jwtUtils

    AuthController(UserService userService, TokenService tokenService, AuthenticationManager authenticationManager,
                   JwtUtils jwtUtils) {
        this.userService = userService
        this.tokenService = tokenService
        this.authenticationManager = authenticationManager
        this.jwtUtils = jwtUtils
    }

    @PostMapping("/api/auth/signup")
    private String signUp(@RequestBody SignupModel signup) {
        if (userService.registerUser(signup)) {
            return "User created successfully"
        } else {
            throw new Exception("Something going wrong!!! Please, wait, and try one more time!")
        }
    }

    @PostMapping("/api/auth/signin")
    private ResponseEntity<Map<String, String>> signIn(@RequestBody SigninModel signin) {
        def authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signin.username, signin.password))
        SecurityContextHolder.getContext().setAuthentication(authentication)
        def userDetails = (UserDetailsImpl) authentication.getPrincipal()
        def token = jwtUtils.createToken(userDetails)

        if (!tokenService.addToken(userDetails.username, token)) {
            throw new Exception("Some issue happened when trying to signin. Please try later")
        }

        return ResponseEntity.ok().body([token: token, userId: userDetails.getId()])
    }

    @PostMapping("/api/auth/signout")
    private void signOut() {
        def username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()
        tokenService.removeToken(username)
    }
}
