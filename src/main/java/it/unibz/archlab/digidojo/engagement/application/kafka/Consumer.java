package it.unibz.archlab.digidojo.engagement.application.kafka;

import it.unibz.archlab.digidojo.engagement.application.dto.PostErasedEvent;
import it.unibz.archlab.digidojo.engagement.application.dto.PostWrittenEvent;
import it.unibz.archlab.digidojo.engagement.core.Post;
import it.unibz.archlab.digidojo.engagement.core.PostRepository;
import it.unibz.archlab.digidojo.engagement.core.PostSyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class Consumer {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostSyncService postSyncService;

    @KafkaListener(
            topics = "${it.unibz.archlab.digidojo.engagement.kafka.consumer.topics.messages}",
            groupId = "${it.unibz.archlab.digidojo.engagement.kafka.consumer.group_id}"
    )
    public void consume(String jsonMessage) {
        System.out.println("Received:" + jsonMessage);
    }

    @KafkaListener(
            containerFactory = "postWrittenEventKafkaListenerContainerFactory",
            topics = "${it.unibz.archlab.digidojo.engagement.kafka.consumer.topics.posts_written}",
            groupId = "${it.unibz.archlab.digidojo.engagement.kafka.consumer.group_id}"
    )
    public void syncPostWritten(PostWrittenEvent postWrittenEvent) {
        PostWrittenEvent.Payload payload = postWrittenEvent.getPayload();

        postSyncService.storePost(payload.getUuid(), payload.getSlug());
    }

    @KafkaListener(
            containerFactory = "postErasedEventKafkaListenerContainerFactory",
            topics = "${it.unibz.archlab.digidojo.engagement.kafka.consumer.topics.posts_erased}",
            groupId = "${it.unibz.archlab.digidojo.engagement.kafka.consumer.group_id}"
    )
    public void syncPostErased(PostErasedEvent postErasedEvent) {
        PostErasedEvent.Payload payload = postErasedEvent.getPayload();

        postSyncService.deletePost(payload.getUuid());
    }
}