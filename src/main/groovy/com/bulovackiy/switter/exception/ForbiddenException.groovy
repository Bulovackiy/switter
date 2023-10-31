package com.bulovackiy.switter.exception

class ForbiddenException extends Exception {

    ForbiddenException() {
    }

    ForbiddenException(String message) {
        super(message)
    }
}
