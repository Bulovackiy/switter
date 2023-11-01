package com.bulovackiy.switter.service

import com.bulovackiy.switter.exception.NotFoundException
import com.bulovackiy.switter.helper.MapHelper
import com.bulovackiy.switter.model.dto.ReactionDTO
import com.bulovackiy.switter.repository.ReactionRepository
import com.bulovackiy.switter.repository.model.Reaction
import org.springframework.stereotype.Service

import java.util.stream.Collectors

@Service
class ReactionService {

    ReactionRepository reactionRepository

    ReactionService(ReactionRepository reactionRepository) {
        this.reactionRepository = reactionRepository
    }

    ReactionDTO createReaction(String postId, ReactionDTO reactionDTO) {
        def reaction = new Reaction()
        reaction.type = reactionDTO.type
        reaction.parent = reactionDTO.parent
        reaction.postId = postId

        return MapHelper.mapToReactionDTO(reactionRepository.save(reaction))
    }

    ReactionDTO updateReaction(String postId, ReactionDTO reactionDTO) {
        def reaction = reactionRepository.findByIdAndParentAndPostId(reactionDTO.id, reactionDTO.parent, postId)
            .orElseThrow {new NotFoundException("Reaction for user id: " + reactionDTO.parent + " and post id: " + postId)}

        reaction.type = reactionDTO.type

        return MapHelper.mapToReactionDTO(reactionRepository.save(reaction))
    }

    void deleteReaction(String postId, String userId, String reactionId) {
        def reaction = reactionRepository.findByIdAndParentAndPostId(reactionId, userId, postId)
                .orElseThrow {new NotFoundException("Reaction for user id: " + userId + " and post id: " + postId)}

        reactionRepository.delete(reaction)
    }

    List<ReactionDTO> getReactionsForPost(String postId) {
        def reactions = reactionRepository.findByPostId(postId)
                .orElse(Collections.emptyList())

        return reactions.stream().map(MapHelper::mapToReactionDTO).collect(Collectors.toList())
    }
}
