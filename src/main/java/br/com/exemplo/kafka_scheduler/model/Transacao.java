package br.com.exemplo.kafka_scheduler.model;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.UUID;

public class Transacao {
    private UUID id;
    private double valor;
    private Date timestamp;

    public Transacao() {
    }

    public Transacao(UUID id, double valor) {
        this.id = id;
        this.valor = valor;
        this.timestamp = new Date();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Transacao{" +
                "id=" + id +
                ", valor=" + valor +
                ", timestamp=" + timestamp.toString() +
                '}';
    }
}
