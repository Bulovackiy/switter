package com.bulovackiy.switter.repository.model

import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.MongoId

@Document("roles")
class Role {
    @MongoId
    private String id
    private ERole name

    Role() {
    }

    Role(ERole name) {
        this.name = name
    }

    String getId() {
        return id
    }

    void setId(String id) {
        this.id = id
    }

    ERole getName() {
        return name
    }

    void setName(ERole name) {
        this.name = name
    }
}
