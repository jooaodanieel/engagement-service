package it.unibz.archlab.digidojo.engagement.core;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@RequiredArgsConstructor
public class ReactionCard {
    @Getter @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Getter @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    private Post post;

    @Getter @Setter
    private Long likes = 0L;

    @Getter @Setter
    private Long shares = 0L;

    public void like() {
        likes++;
    }

    public void share() {
        shares++;
    }
}
