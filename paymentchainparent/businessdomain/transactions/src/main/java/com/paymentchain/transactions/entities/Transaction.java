package com.paymentchain.transactions.entities;

import com.paymentchain.transactions.enums.Channel;
import com.paymentchain.transactions.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String reference;
    private String ibanAccount;
    private LocalDateTime  date;
    private double amount;
    @DecimalMin(value = "0.0")
    private double fee;
    private String description;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private Channel channel;

    @PrePersist
    @PreUpdate
    public void aplicarValidaciones() {
        if (amount == 0) {
            throw new IllegalArgumentException("El monto de la transacción no puede ser 0");
        }

        if (fee > 0) {
            amount -= fee; // Deducir comisión
        }

        if (date.isAfter(LocalDateTime.now())) {
            this.status = Status.PENDIENTE;
        } else {
            this.status = Status.LIQUIDADA;
        }
    }

}
