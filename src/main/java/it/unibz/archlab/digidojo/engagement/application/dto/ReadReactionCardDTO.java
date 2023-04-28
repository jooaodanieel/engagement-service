package it.unibz.archlab.digidojo.engagement.application.dto;

import lombok.Getter;
import lombok.Setter;

public class ReadReactionCardDTO {
    @Getter @Setter
    private Long likes;

    @Getter @Setter
    private Long shares;

    public ReadReactionCardDTO(Long likes, Long shares) {
        this.likes = likes;
        this.shares = shares;
    }
}
