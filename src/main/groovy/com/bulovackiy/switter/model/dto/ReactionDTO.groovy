package com.bulovackiy.switter.model.dto

import com.bulovackiy.switter.repository.model.EReaction

class ReactionDTO {

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
