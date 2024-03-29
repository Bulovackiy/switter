package com.bulovackiy.switter.repository.model

import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.MongoId

@Document(collection = "posts")
class Post {

    @MongoId
    private String id
    private String content
    private String parent

    String getId() {
        return id
    }

    void setId(String id) {
        this.id = id
    }

    String getText() {
        return content
    }

    void setText(String text) {
        this.content = text
    }

    String getParent() {
        return parent
    }

    void setParent(String parent) {
        this.parent = parent
    }
}
