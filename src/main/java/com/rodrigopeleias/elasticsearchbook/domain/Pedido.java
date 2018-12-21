package com.rodrigopeleias.elasticsearchbook.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class Pedido {

    private long id;
    private Date dataPedido;
    private long idCliente;
    private List<ItemPedido> items;
    private StatusPedido status;
}
