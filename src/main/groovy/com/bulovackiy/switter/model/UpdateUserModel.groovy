package com.bulovackiy.switter.model

class UpdateUserModel {

    private String firstName
    private String lastName
    private String email
    private String phoneNumber

    private String oldPassword
    private String newPassword

    String getFirstName() {
        return firstName
    }

    void setFirstName(String firstName) {
        this.firstName = firstName
    }

    String getLastName() {
        return lastName
    }

    void setLastName(String lastName) {
        this.lastName = lastName
    }

    String getEmail() {
        return email
    }

    void setEmail(String email) {
        this.email = email
    }

    String getPhoneNumber() {
        return phoneNumber
    }

    void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber
    }

    String getOldPassword() {
        return oldPassword
    }

    void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword
    }

    String getNewPassword() {
        return newPassword
    }

    void setNewPassword(String newPassword) {
        this.newPassword = newPassword
    }
}
