package com.bulovackiy.switter.model.dto

class PostDTO {

    private String id
    private String text
    private String parent
    private List<ReactionDTO> reactions

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

    List<ReactionDTO> getReactions() {
        return reactions
    }

    void setReactions(List<ReactionDTO> reactions) {
        this.reactions = reactions
    }

    String getParent() {
        return parent
    }

    void setParent(String parent) {
        this.parent = parent
    }
}
