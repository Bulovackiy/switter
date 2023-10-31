package com.bulovackiy.switter.repository.model

import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.MongoId

@Document(collection = "posts")
class Post {

    @MongoId
    private String id
    private String text

    @DBRef
    private List<Reaction> reactions
    private String parent

    String getId() {
        return id
    }

    void setId(String id) {
        this.id = id
    }

    String getText() {
        return text
    }

    void setText(String text) {
        this.text = text
    }

    List<Reaction> getReactions() {
        return reactions
    }

    void setReactions(List<Reaction> reactions) {
        this.reactions = reactions
    }

    String getParent() {
        return parent
    }

    void setParent(String parent) {
        this.parent = parent
    }
}
