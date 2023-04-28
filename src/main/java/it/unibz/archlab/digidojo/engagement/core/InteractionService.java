package it.unibz.archlab.digidojo.engagement.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InteractionService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ReactionCardRepository reactionCardRepository;

    public ReactionCard readReactionCard(String postId) {
        Optional<Post> maybePost = postRepository.findById(postId);

        if (maybePost.isEmpty()) {
            throw new IllegalArgumentException("Post not found - " + postId);
        }

        return maybePost.get().getReactionCard();
    }

    public void likePost(String postId) {
        Optional<Post> maybePost = postRepository.findById(postId);

        if (maybePost.isEmpty()) {
            throw new IllegalArgumentException("Post not found - " + postId);
        }

        Post post = maybePost.get();

        ReactionCard reactionCard = post.getReactionCard();
        reactionCard.like();

        reactionCardRepository.save(reactionCard);
    }

    public String sharePost(String postId) {
        Optional<Post> maybePost = postRepository.findById(postId);

        if (maybePost.isEmpty()) {
            throw new IllegalArgumentException("Post not found - " + postId);
        }

        Post post = maybePost.get();

        ReactionCard reactionCard = post.getReactionCard();
        reactionCard.share();

        reactionCardRepository.save(reactionCard);

        return post.getSlug();
    }
}
