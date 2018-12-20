package com.rodrigopeleias.elasticsearchbook.controller;

import com.rodrigopeleias.elasticsearchbook.domain.Cliente;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController("/api/cliente")
public class ClientRestController {

    public static long contadorErroCaotico;
    private static Map<Long, Cliente> clientes = new HashMap<>();

    static {
        Cliente cliente1 = new Cliente();
        cliente1.setId(1);
        cliente1.setNome("Cliente 1");
        cliente1.setEmail("customer1@gmail.com");

        Cliente cliente2 = new Cliente();
        cliente2.setId(2);
        cliente2.setNome("Cliente 2");
        cliente2.setEmail("customer2@gmail.com");
        Cliente cliente3 = new Cliente();
        cliente3.setId(3);
        cliente3.setNome("Cliente 3");

        cliente3.setEmail("customer3@gmail.com");
        Cliente cliente4 = new Cliente();
        cliente4.setId(4);
        cliente4.setNome("Cliente 4");
        cliente4.setEmail("customer4@gmail.com");

        Cliente cliente5 = new Cliente();
        cliente5.setId(5);
        cliente5.setNome("Cliente 5");
        cliente5.setEmail("customer5@gmail.com");

        clientes.put(cliente1.getId(), cliente1);
        clientes.put(cliente2.getId(), cliente2);
        clientes.put(cliente3.getId(), cliente3);
        clientes.put(cliente4.getId(), cliente4);
        clientes.put(cliente5.getId(), cliente5);
    }

    @GetMapping
    public Collection<Cliente> getCLientes() {
        log.info("Foram buscados " + clientes.values().size() + " clientes");
        return clientes.values();
    }

    @GetMapping("/api/cliente/{id}")
    public Cliente getCliente(@PathVariable("id") long id) {
        Optional<Cliente> optCliente = clientes.values()
                .stream()
                .filter(cliente -> cliente.getId() == id)
                .findAny();
        if (optCliente.isPresent()) {
            Cliente cliente = optCliente.get();
            log.info("Foi buscado o cliente " + cliente.getNome());
            return cliente;
        }
        return null;
    }

    @PostMapping
    public void addCliente(@RequestBody Cliente cliente) {
        log.warn("O cliente " + cliente.getId() + "foi inserido!");
        clientes.put(cliente.getId(), cliente);
    }

    @PutMapping
    public void mergeCliente(@RequestBody Cliente cliente) {
        contadorErroCaotico++;

        if ((contadorErroCaotico % 7) == 0) {
            throw new RuntimeException("Ocorreu um erro caótico!");
        }

        log.info("O cliente " + cliente.getId() + " foi alterado!");

        Cliente temp = clientes.get(cliente.getId());

        temp.setNome(cliente.getNome());
        temp.setEmail(cliente.getEmail());
    }

    @DeleteMapping("/api/cliente/{id}")
    public void delete(@PathVariable("id") long id) {
        log.info("O cliente " + id + "foi excluído");
        clientes.remove(id);
    }

}
