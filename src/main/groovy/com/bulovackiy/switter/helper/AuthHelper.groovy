package com.bulovackiy.switter.helper

import com.bulovackiy.switter.security.service.UserDetailsImpl
import org.apache.commons.lang3.StringUtils
import org.springframework.security.core.Authentication

class AuthHelper {

    static String getUserId(Authentication authentication) {
        return ((UserDetailsImpl) authentication.principal).id
    }

    static boolean checkUserAccess(String userId, Authentication authentication) {
        def principalId = getUserId(authentication)
        return StringUtils.equals(userId, principalId)
    }
}
