package br.com.exemplo.kafka_scheduler;

import br.com.exemplo.kafka_scheduler.model.Transacao;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Component
public class ExemploListener {
    private static final Logger log = LoggerFactory.getLogger(ExemploListener.class);
    private static int timeoutMinutes = 1;

    private static Map<UUID, Transacao> transacaoMap = new HashMap<>();

    private static Timer timer = new Timer("scheduler-timer");

    @Autowired
    private KafkaManager kafkaManager;

    @KafkaListener(topics = "teste2", concurrency = "3")
    public void transacional(ConsumerRecord<String, Transacao> record) {
        log.info("Listener Transacional: {}, partição: {}, offset: {}, thread: {}", record.value(), record.partition(), record.offset(), Thread.currentThread().getId());
    }

    @KafkaListener(topics = "teste2", groupId = "scheduler")
    public void scheduler(ConsumerRecord<String, Transacao> record, Acknowledgment ack) throws InterruptedException {
        LocalDateTime timeout = LocalDateTime.ofInstant(record.value().getTimestamp().toInstant(), ZoneId.systemDefault()).plus(timeoutMinutes, ChronoUnit.MINUTES);

        if(LocalDateTime.now().isAfter(timeout)) {
            this.processarMensagemScheduler(record);
            ack.acknowledge();
        } else {
            ack.nack(100);
            kafkaManager.pause("scheduler");
            Date timeoutDate = Date.from(timeout.atZone(ZoneId.systemDefault()).toInstant());

            TimerTask task = new TimerTask() {
                public void run() {
                    kafkaManager.resume("scheduler");
                    log.info("Executando agendamento local: " + new Date() + "n" +
                            "Thread's name: " + Thread.currentThread().getName());
                }
            };
            timer.schedule(task, timeoutDate);
        }
    }

    private void processarMensagemScheduler(ConsumerRecord<String, Transacao> record) {
        log.info("Mensagem Scheduler: {}, partição: {}, offset: {}", record.value(), record.partition(), record.offset());
    }
}
