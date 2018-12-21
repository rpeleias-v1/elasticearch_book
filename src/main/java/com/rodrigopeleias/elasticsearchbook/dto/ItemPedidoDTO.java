package com.rodrigopeleias.elasticsearchbook.dto;

import com.rodrigopeleias.elasticsearchbook.domain.ItemPedido;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoDTO {

    private long idPedido;
    private long idCliente;
    private ItemPedido item;
}
