package com.rodrigopeleias.elasticsearchbook.domain;

import lombok.ToString;

@ToString
public enum StatusPedido {

    ABERTO("ABERTO"), CONCLUIDO("CONCLUIDO"), CANCELADO("CANCELADO");

    private final String status;

    private StatusPedido(String statusPedido) {
        status = statusPedido;
    }

}
