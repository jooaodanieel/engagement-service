package it.unibz.archlab.digidojo.engagement.application;

import it.unibz.archlab.digidojo.engagement.application.dto.ReadReactionCardDTO;
import it.unibz.archlab.digidojo.engagement.core.InteractionService;
import it.unibz.archlab.digidojo.engagement.core.ReactionCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/posts/{pid}")
public class InteractionsController {
    @Autowired
    private InteractionService interactionService;

    @GetMapping(path = "/reactions")
    public ReadReactionCardDTO getCard(@PathVariable("pid") String pid) {
        ReactionCard reactionCard = interactionService.readReactionCard(pid);

        return new ReadReactionCardDTO(reactionCard.getLikes(), reactionCard.getShares());
    }

    @PutMapping(path = "/likes")
    public void addLike(@PathVariable("pid") String pid) {
        interactionService.likePost(pid);
    }

    @PutMapping(path = "/shares")
    public String share(@PathVariable("pid") String pid) {
        return interactionService.sharePost(pid);
    }
}
