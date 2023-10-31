package com.bulovackiy.switter.security.service

import com.bulovackiy.switter.repository.UserRepository
import com.bulovackiy.switter.repository.model.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImlp implements UserDetailsService {
    UserRepository userRepository

    UserDetailsServiceImlp(UserRepository userRepository) {
        this.userRepository = userRepository
    }

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .or {userRepository.findByEmail(username)}
                .or {userRepository.findByPhoneNumber(username)}
                .orElseThrow {new UsernameNotFoundException("User not found")}

        return UserDetailsImpl.build(user, username)
    }
}
