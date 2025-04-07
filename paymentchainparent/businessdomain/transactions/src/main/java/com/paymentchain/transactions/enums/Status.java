package com.paymentchain.transactions.enums;

public enum Status {

    PENDIENTE("01"),
    LIQUIDADA("02"),
    RECHAZADA("03"),
    CANCELADA("04");

    private final String codigo;

    Status(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

}
