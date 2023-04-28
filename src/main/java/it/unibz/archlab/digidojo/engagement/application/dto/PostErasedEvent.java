package it.unibz.archlab.digidojo.engagement.application.dto;

import lombok.Getter;
import lombok.Setter;

public class PostErasedEvent {
    public class Payload {
        @Setter @Getter
        private String uuid;
    }

    @Setter @Getter
    private String type;

    @Setter @Getter
    private Payload payload;
}
