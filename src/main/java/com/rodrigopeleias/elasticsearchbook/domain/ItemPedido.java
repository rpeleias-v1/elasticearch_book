package com.rodrigopeleias.elasticsearchbook.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class ItemPedido {

    private long idProduto;
    private long quantidade;
}
