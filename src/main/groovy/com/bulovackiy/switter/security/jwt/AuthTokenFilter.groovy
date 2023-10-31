package com.bulovackiy.switter.security.jwt

import com.bulovackiy.switter.security.service.UserDetailsServiceImlp
import com.bulovackiy.switter.service.TokenService
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.servlet.HandlerMapping

class AuthTokenFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class)

    JwtUtils jwtUtils
    TokenService tokenService
    UserDetailsServiceImlp userDetailsService
    
    @Autowired
    AuthTokenFilter(JwtUtils jwtUtils, TokenService tokenService, UserDetailsServiceImlp userDetailsService) {
        this.jwtUtils = jwtUtils
        this.tokenService = tokenService
        this.userDetailsService = userDetailsService
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            var jwt = parseJwt(request)
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                var username = jwtUtils.getUserNameFromJwtToken(jwt)

                if (tokenService.tokenExists(username, jwt)) {
                    var userDetails = userDetailsService.loadUserByUsername(username)
                    var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities())
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request))
                    SecurityContextHolder.getContext().setAuthentication(authentication)
                }
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e)
        }

        filterChain.doFilter(request, response)
    }

    private String parseJwt(HttpServletRequest request) {
        return jwtUtils.resolveToken(request)
    }
}
