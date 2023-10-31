package com.bulovackiy.switter.model.dto

class ErrorDTO {

    private int errorCode
    private String description

    ErrorDTO(int errorCode, String description) {
        this.errorCode = errorCode
        this.description = description
    }

    int getErrorCode() {
        return errorCode
    }

    void setErrorCode(int errorCode) {
        this.errorCode = errorCode
    }

    String getDescription() {
        return description
    }

    void setDescription(String description) {
        this.description = description
    }
}
