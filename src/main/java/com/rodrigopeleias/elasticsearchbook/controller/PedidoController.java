package com.rodrigopeleias.elasticsearchbook.controller;

import com.rodrigopeleias.elasticsearchbook.domain.Pedido;
import com.rodrigopeleias.elasticsearchbook.domain.StatusPedido;
import com.rodrigopeleias.elasticsearchbook.dto.ItemPedidoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/pedido")
public class PedidoController {

    private static List<Pedido> pedidosMock = new ArrayList<>();
    private static long contadorErroCaotico;

    @GetMapping
    public List<Pedido> buscarPedido() {
        log.info("foram buscados todos os pedidos!");
        return pedidosMock;
    }

    @GetMapping("/pedido/{idCliente}")
    public List<Pedido> buscarPedidosPorCliente(@PathVariable("id") long idCliente) {
        List<Pedido> pedidos = pedidosMock.stream()
                .filter(pedido -> pedido.getIdCliente() == idCliente)
                .collect(Collectors.toList());

        log.info("cliente " + idCliente + "possui " + pedidos.size() + "pedidos");
        return pedidos;
    }

    @PostMapping("/item/adiciona")
    public void adicionaItemPedido(@RequestBody ItemPedidoDTO item) {
        contadorErroCaotico++;

        if ((contadorErroCaotico) % 7 == 0) {
            throw new RuntimeException("Ocorreu um erro caótico!");
        }
        long idCliente = 0;

        boolean pedidoNovo = true;

        for (Pedido pedido : pedidosMock) {
            if (pedido.getId() == item.getIdPedido()) {
                pedido.getItems().add(item.getItem());
                idCliente = pedido.getIdCliente();
                pedidoNovo = false;
            }
        }

        if (pedidoNovo) {
            Pedido pedido = Pedido.builder()
                    .id(item.getIdPedido())
                    .dataPedido(new Date())
                    .idCliente(item.getIdCliente())
                    .items(new ArrayList<>())
                    .status(StatusPedido.ABERTO)
                    .build();
            pedido.getItems().add(item.getItem());

            pedidosMock.add(pedido);
        }

        log.info("pedido " + item.getIdPedido() + " do cliente " + idCliente + " adicionou o produto "
                + item.getItem().getIdProduto());
    }

    @PostMapping("/item/remove")
    public void removeItemPedido(@RequestBody ItemPedidoDTO item) {
        long idCliente = 0;
        for (Pedido pedido : pedidosMock) {
            if (pedido.getId() == item.getIdPedido()) {
                pedido.getItems().remove(item.getItem());
                idCliente = pedido.getIdCliente();
            }

            log.info("pedido " + item.getIdPedido() + "do cliente " + idCliente + "removeu o produto "
                    + item.getItem().getIdProduto());
        }
    }

    @PutMapping("/{idPedido}")
    public void pagaPedido(@PathVariable("idPedido") long idPedido) {
        for (Pedido pedido: pedidosMock) {
            if (pedido.getId() == idPedido) {
                pedido.setStatus(StatusPedido.CONCLUIDO);
            }
        }
        log.info("pedido " + idPedido + " efetivado");
    }

    @DeleteMapping("/{idPedido}")
    public void cancelaPedido(@PathVariable("idPedido") long idPedido) {
        for (Pedido pedido: pedidosMock) {
            if (pedido.getId() == idPedido) {
                pedido.setStatus(StatusPedido.CANCELADO);
            }
            log.info("pedido " + idPedido + "cancelado");
        }
    }

}
