package com.bulovackiy.switter.repository.model

import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.MongoId

@Document(collection = "users")
class User {
    @MongoId
    private String id
    private String username
    private String firstName
    private String lastName
    private String email
    private String phoneNumber
    private String password
    private Set<String> following
    @DBRef
    private Set<Role> roles = new HashSet<>()

    String getId() {
        return id
    }

    void setId(String id) {
        this.id = id
    }

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

    String getPassword() {
        return password
    }

    void setPassword(String password) {
        this.password = password
    }

    Set<Role> getRoles() {
        return roles
    }

    void setRoles(Set<Role> roles) {
        this.roles = roles
    }

    Set<String> getFollowing() {
        return following
    }

    void setFollowing(Set<String> following) {
        this.following = following
    }
}
