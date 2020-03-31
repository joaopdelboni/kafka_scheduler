package br.com.exemplo.kafka_scheduler;

import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.stereotype.Component;

@Component
public class KafkaManager {
    private final KafkaListenerEndpointRegistry registry;

    public KafkaManager(KafkaListenerEndpointRegistry registry) {
        this.registry = registry;
    }
    public void pause(String consumerGroup) {
        registry.getListenerContainers().stream().filter(c -> c.getGroupId().equals(consumerGroup)).forEach(MessageListenerContainer::pause);
    }

//    public void setOffset(String consumerGroup, int partition, Long offset) {
//        registry.getListenerContainers().stream().filter(c -> c.getGroupId().equals(consumerGroup)).forEach(consumerGroup -> {
//            consumerGroup.getAssignedPartitions().stream().filter(p -> p.partition() == partition).forEach(p -> {
//                p.
//            });
//        });
//    }

    public void resume(String consumerGroup) {
        registry.getListenerContainers().stream().filter(c -> c.getGroupId().equals(consumerGroup)).forEach(MessageListenerContainer::resume);
    }
}
