package com.bulovackiy.switter.repository.model

import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.MongoId

@Document(collection = "reactions")
class Reaction {

    @MongoId
    private String id
    private EReaction type
    private String parent

    String getId() {
        return id
    }

    void setId(String id) {
        this.id = id
    }

    EReaction getType() {
        return type
    }

    void setType(EReaction type) {
        this.type = type
    }

    String getParent() {
        return parent
    }

    void setParent(String parent) {
        this.parent = parent
    }
}
