package com.bulovackiy.switter.model.dto

class UserDTO {

    private String username
    private String firstName
    private String lastName
    private String email
    private String phoneNumber
    private Set<String> following

    String getUsername() {
        return username
    }

    void setUsername(String username) {
        this.username = username
    }

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

    Set<String> getFollowing() {
        return following
    }

    void setFollowing(Set<String> following) {
        this.following = following
    }
}
