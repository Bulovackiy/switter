package com.bulovackiy.switter.service

import com.bulovackiy.switter.exception.NotFoundException
import com.bulovackiy.switter.exception.ValidationException
import com.bulovackiy.switter.model.SignupModel
import com.bulovackiy.switter.model.dto.UserDTO
import com.bulovackiy.switter.repository.UserRepository
import com.bulovackiy.switter.repository.model.User
import com.bulovackiy.switter.security.service.UserDetailsImpl
import org.apache.commons.lang3.StringUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class)

    private UserRepository userRepository
    private PasswordEncoder passwordEncoder

    UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository
        this.passwordEncoder = passwordEncoder
    }

    boolean checkUserAccess(String userId) {
        def principal = SecurityContextHolder.getContext().authentication.principal

        if (principal instanceof UserDetailsImpl) {
            return StringUtils.equals(principal.id, userId)
        }

        return false
    }

    UserDTO registerUser(SignupModel signup) {
        validateSignup(signup)

        User user = new User()
        user.username = signup.username
        user.email = signup.email
        user.firstName = signup.firstName
        user.lastName = signup.lastName
        user.phoneNumber = signup.phoneNumber
        user.password = passwordEncoder.encode(signup.password)

        return mapToUserDTO(userRepository.save(user))
    }

    UserDTO addFollowingToUser(String userId, String followingUserId) {
        if (!userRepository.existsById(followingUserId)) {
            throw new NotFoundException("Following user with id " + followingUserId + "not found")
        }
        def user = userRepository.findById(userId)
                .orElseThrow {new NotFoundException("User with id: " + userId + " not found")}

        if (user.following == null) {
            user.following = [followingUserId]
        } else {
            user.following.add(followingUserId)
        }

        return mapToUserDTO(userRepository.save(user))
    }

    UserDTO deleteFollowing(String userId, String followingUserId) {
        if (!userRepository.existsById(followingUserId)) {
            throw new NotFoundException("Following user with id " + followingUserId + "not found")
        }
        def user = userRepository.findById(userId)
                .orElseThrow {new NotFoundException("User with id: " + userId + " not found")}

        if (user.following != null) {
            user.following.remove(followingUserId)
        }

        return mapToUserDTO(userRepository.save(user))
    }

    UserDTO mapToUserDTO(User user) {
        def userDto = new UserDTO()
        userDto.username = user.username
        userDto.email = user.email
        userDto.phoneNumber = user.phoneNumber
        userDto.firstName = user.firstName
        userDto.lastName = user.lastName
        userDto.following = user.following

        return userDto
    }

    private void validateSignup(SignupModel signup) throws ValidationException {
        if (StringUtils.isBlank(signup.username)) {
            throw new ValidationException("Username is required")
        }
        if (userRepository.existsByUsername(signup.username)) {
            throw new ValidationException("User with such username already exists")
        }

        if (StringUtils.isBlank(signup.email)) {
            throw new ValidationException("Email is required")
        }
        if (userRepository.existsByEmail(signup.email)) {
            throw new ValidationException("User with such email already exists")
        }

        if (StringUtils.isBlank(signup.phoneNumber)) {
            throw new ValidationException("Phone Number is required")
        }

        if (!StringUtils.isNumeric(signup.phoneNumber)){
            throw new ValidationException("Phone Number must be numbers only")
        }

        if (userRepository.existsByPhoneNumber(signup.phoneNumber)) {
            throw new ValidationException("User with such phone number already exists")
        }

        if (StringUtils.isBlank(signup.password)) {
            throw new ValidationException("Password is required")
        }

        if (!signup.password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}\$")) {
             throw new ValidationException("Password must be minimum eight characters, one uppercase letter, " +
                     "one lowercase letter, one number and one special character")
        }
    }
}
