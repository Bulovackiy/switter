package com.bulovackiy.switter.repository.model

import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.MongoId

@Document(collection = "tokens")
class Token {

    @MongoId
    private String id
    @Indexed(unique = true, expireAfter = "10s")
    private String username
    private String token
    private Date createDate

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

    String getToken() {
        return token
    }

    void setToken(String token) {
        this.token = token
    }

    Date getCreateDate() {
        return createDate
    }

    void setCreateDate(Date createDate) {
        this.createDate = createDate
    }
}
