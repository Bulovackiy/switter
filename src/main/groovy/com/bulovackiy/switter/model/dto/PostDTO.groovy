package com.bulovackiy.switter.model.dto

class PostDTO {

    private String id
    private String text
    private String parent
    private Set<ReactionDTO> reactions
    private Set<CommentDTO> comments

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

    String getParent() {
        return parent
    }

    void setParent(String parent) {
        this.parent = parent
    }

    Set<ReactionDTO> getReactions() {
        return reactions
    }

    void setReactions(Set<ReactionDTO> reactions) {
        this.reactions = reactions
    }

    Set<CommentDTO> getComments() {
        return comments
    }

    void setComments(Set<CommentDTO> comments) {
        this.comments = comments
    }
}
