package com.bulovackiy.switter.service

import com.bulovackiy.switter.exception.NotFoundException
import com.bulovackiy.switter.helper.MapHelper
import com.bulovackiy.switter.model.dto.CommentDTO
import com.bulovackiy.switter.repository.CommentRepository
import com.bulovackiy.switter.repository.model.Comment
import org.springframework.stereotype.Service

import java.util.stream.Collectors

@Service
class CommentService {

    CommentRepository commentRepository

    CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository
    }

    CommentDTO createComment(String userId, String postId, CommentDTO commentDTO) {
        def comment = new Comment()
        comment.parent = userId
        comment.content = commentDTO.content
        comment.postId = postId

        return MapHelper.mapToCommentDTO(commentRepository.save(comment))
    }

    CommentDTO updateComment(String userId, String postId, String commentId, CommentDTO commentDTO) {
        def comment = commentRepository.findByIdAndPostIdAndParent(commentId, postId, userId)
                .orElseThrow {new NotFoundException("Comment with id: " + commentId + " not found")}

        comment.content = commentDTO.content

        return MapHelper.mapToCommentDTO(commentRepository.save(comment))
    }

    CommentDTO getComment(String commentId, String postId) {
        def comment = commentRepository.findByIdAndPostId(commentId, postId)
                .orElseThrow {new NotFoundException("Comment with id: " + commentId + " not found")}

        return MapHelper.mapToCommentDTO(comment)
    }

    void deleteComment(String commentId, String postId) {
        def comment = commentRepository.findByIdAndPostId(commentId, postId)
                .orElseThrow {new NotFoundException("Comment with id: " + commentId + " not found")}

        commentRepository.delete(comment)
    }

    List<CommentDTO> getPostComments(String postId) {
        return commentRepository.findByPostId(postId)
                .orElse(Collections.emptyList())
                .stream()
                .map(MapHelper::mapToCommentDTO)
                .collect(Collectors.toList())
    }
}
