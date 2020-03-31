package br.com.exemplo.kafka_scheduler;

import br.com.exemplo.kafka_scheduler.model.Transacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.UUID;

@SpringBootApplication
@EnableKafka
public class ExemploKafkaApplication implements CommandLineRunner {
	@Autowired
	private KafkaTemplate<String, Transacao> template;

	public static void main(String[] args) {
		SpringApplication.run(ExemploKafkaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		for(int j = 0; j< 1000; j++) {
			for (int i = 0; i < 10; i++) {
				template.send("teste2", "1" + i, new Transacao(UUID.randomUUID(), Math.random() * 1000.0));
				Thread.sleep(1000);
			}
			Thread.sleep(10000);
		}
	}
}
