package it.unibz.archlab.digidojo.engagement.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostSyncService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ReactionCardRepository reactionCardRepository;

    public void storePost(String uuid, String slug) {
        Post post = new Post();
        post.setUuid(uuid);
        post.setSlug(slug);

        ReactionCard card = new ReactionCard();
        card.setPost(post);

        try {
            reactionCardRepository.save(card);
        } catch (RuntimeException rte) {
            System.out.println("Duplicated post from event");
        }
    }

    public void deletePost(String uuid) {
        Optional<Post> maybePost = postRepository.findById(uuid);

        if (maybePost.isEmpty()) {
            System.out.println("Post not found - " + uuid);
        } else {
            Post post = maybePost.get();
            ReactionCard card = post.getReactionCard();
            reactionCardRepository.deleteById(card.getId());
            postRepository.deleteById(uuid);
        }
    }
}
