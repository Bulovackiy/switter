package com.bulovackiy.switter.service

import com.bulovackiy.switter.exception.NotFoundException
import com.bulovackiy.switter.exception.ValidationException
import com.bulovackiy.switter.helper.MapHelper
import com.bulovackiy.switter.model.SignupModel
import com.bulovackiy.switter.model.UpdateUserModel
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

    Set<String> getUserFollowing(String userId) {
        def user = userRepository.findById(userId)
                .orElseThrow {new NotFoundException("User with id: " + userId + " not found")}
        return user.following
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

        return MapHelper.mapToUserDTO(userRepository.save(user))
    }

    UserDTO updateUser(UpdateUserModel userModel, String userId) {
        validateUpdateUserModel(userModel)
        def user = userRepository.findById(userId)
                .orElseThrow { new NotFoundException("User with id: " + userId + " not found")}

        user.firstName = userModel.firstName ? userModel.firstName : user.firstName
        user.lastName = userModel.lastName ? userModel.firstName : user.lastName
        user.email = userModel.email ? userModel.email : user.email
        user.phoneNumber = userModel.phoneNumber ? userModel.phoneNumber : user.phoneNumber

        if (userModel.newPassword && userModel.oldPassword) {
            if (passwordEncoder.matches(userModel.oldPassword, user.password)) {
                user.password = passwordEncoder.encode(userModel.newPassword)
            } else {
                throw new ValidationException("Old Password is not the same with current")
            }
        }

        return MapHelper.mapToUserDTO(userRepository.save(user))
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

        return MapHelper.mapToUserDTO(userRepository.save(user))
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

        return MapHelper.mapToUserDTO(userRepository.save(user))
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

    private void validateUpdateUserModel(UpdateUserModel model) throws ValidationException {
        if (!StringUtils.isBlank(model.email) && userRepository.existsByEmail(model.email)) {
            throw new ValidationException("User with such email already exists")
        }

        if (!StringUtils.isBlank(model.phoneNumber) && !StringUtils.isNumeric(model.phoneNumber)){
            throw new ValidationException("Phone Number must be numbers only")
        } else if (!StringUtils.isBlank(model.phoneNumber) && userRepository.existsByPhoneNumber(model.phoneNumber)) {
            throw new ValidationException("User with such phone number already exists")
        }


        if (!StringUtils.isBlank(model.newPassword) && !model.newPassword.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}\$")) {
            throw new ValidationException("Password must be minimum eight characters, one uppercase letter, " +
                    "one lowercase letter, one number and one special character")
        }
    }
}
