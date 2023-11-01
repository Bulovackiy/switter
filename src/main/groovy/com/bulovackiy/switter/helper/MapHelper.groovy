package com.bulovackiy.switter.helper

import com.bulovackiy.switter.model.dto.CommentDTO
import com.bulovackiy.switter.model.dto.PostDTO
import com.bulovackiy.switter.model.dto.ReactionDTO
import com.bulovackiy.switter.model.dto.UserDTO
import com.bulovackiy.switter.repository.model.Comment
import com.bulovackiy.switter.repository.model.Post
import com.bulovackiy.switter.repository.model.Reaction
import com.bulovackiy.switter.repository.model.User

class MapHelper {

    static PostDTO mapToPostDTO(Post post) {
        def postDTO = new PostDTO()
        postDTO.id = post.id
        postDTO.text = post.text
        postDTO.parent = post.parent

        return postDTO
    }

    static CommentDTO mapToCommentDTO(Comment comment) {
        def dto = new CommentDTO()
        dto.id = comment.id
        dto.parent = comment.parent
        dto.content = comment.content
        dto.postId = comment.postId

        return dto
    }

    static ReactionDTO mapToReactionDTO(Reaction reaction) {
        def dto = new ReactionDTO()
        dto.id = reaction.id
        dto.type = reaction.type
        dto.parent = reaction.parent

        return dto
    }

    static UserDTO mapToUserDTO(User user) {
        def userDto = new UserDTO()
        userDto.username = user.username
        userDto.email = user.email
        userDto.phoneNumber = user.phoneNumber
        userDto.firstName = user.firstName
        userDto.lastName = user.lastName
        userDto.following = user.following

        return userDto
    }
}
