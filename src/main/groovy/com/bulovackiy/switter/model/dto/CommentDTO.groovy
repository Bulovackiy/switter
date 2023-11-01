package com.bulovackiy.switter.model.dto

class CommentDTO {

    private String id

    private String postId
    private String content
    private String parent

    String getId() {
        return id
    }

    void setId(String id) {
        this.id = id
    }

    String getPostId() {
        return postId
    }

    void setPostId(String postId) {
        this.postId = postId
    }

    String getContent() {
        return content
    }

    void setContent(String content) {
        this.content = content
    }

    String getParent() {
        return parent
    }

    void setParent(String parent) {
        this.parent = parent
    }
}
