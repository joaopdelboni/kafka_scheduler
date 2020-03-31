package br.com.exemplo.kafka_scheduler;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class TopicBean {
    @Bean
    public NewTopic createTopic() {
        return new NewTopic("teste2", 2, (short)1);
    }
}
