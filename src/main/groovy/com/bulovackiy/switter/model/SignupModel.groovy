package com.bulovackiy.switter.model

import com.bulovackiy.switter.model.dto.UserDTO

class SignupModel extends UserDTO {

    private String password

    String getPassword() {
        return password
    }

    void setPassword(String password) {
        this.password = password
    }
}
