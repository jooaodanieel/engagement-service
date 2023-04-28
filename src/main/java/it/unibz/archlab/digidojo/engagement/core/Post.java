package it.unibz.archlab.digidojo.engagement.core;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Entity
public class Post {
    @Id
    @Setter
    @Getter
    private String uuid;

    @Setter
    @Getter
    private String slug;

    @Getter @Setter
    @OneToOne(mappedBy = "post")
    private ReactionCard reactionCard;
}
