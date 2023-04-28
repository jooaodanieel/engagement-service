package it.unibz.archlab.digidojo.engagement.application.dto;

import lombok.Getter;
import lombok.Setter;

public class PostWrittenEvent {
    public class Payload {
        @Setter @Getter
        private String uuid;

        @Setter @Getter
        private String title;

        @Setter @Getter
        private String content;

        @Setter @Getter
        private String slug;
    }

    @Setter @Getter
    private String type;

    @Setter @Getter
    private Payload payload;
}
