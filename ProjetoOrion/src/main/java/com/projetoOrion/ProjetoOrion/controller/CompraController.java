package com.projetoOrion.ProjetoOrion.controller;


import com.projetoOrion.ProjetoOrion.model.Compras;
import com.projetoOrion.ProjetoOrion.model.Produto;
import com.projetoOrion.ProjetoOrion.repository.ComprasRepository;
import com.projetoOrion.ProjetoOrion.service.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/compra")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CompraController {
    @Autowired
    private CompraService compraService;

    @Autowired
    private ComprasRepository comprasRepository;

    @GetMapping
    public ResponseEntity<List<Compras>> findAllCompras() {
        return ResponseEntity.ok(comprasRepository.findAll());
    }

    public ResponseEntity<Compras> findByIdCompra(@PathVariable long id) {
        Optional<Compras> compra = comprasRepository.findById(id);

        double total = compra.get().getValorTotal();
        NumberFormat formatter = new DecimalFormat("#0.00");
        compra.get().setValorTotal(Double.parseDouble(formatter.format(total).replace(",", ".")));

        compra.get().setMeuPedido(compraService.produtoDuplicado(compra));

        System.out.println(compra.get().getValorTotal());

        try {
            return ResponseEntity.ok(compra.get());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping("/produto_compra/compra/{idCompra}/frete/{frete}")
    public ResponseEntity<Boolean> putProduto(@RequestBody List<Produto> produtos, @PathVariable long idCompra, @PathVariable("frete") double frete) {
        boolean retorno = false;

        for (Produto p : produtos) {
            System.out.println("Quantidade Produto: " + p.getQtdPedido());
            for (int i = 0; i < p.getQtdPedido(); i++) {
                System.out.println(p.getNome());
                retorno = compraService.comprarProduto(p.getId_Produto(), idCompra);
                System.out.println("Retorno: " + retorno);
            }
        }
        return ResponseEntity.ok(retorno);
    }

    @PostMapping
    public ResponseEntity<Compras> postCompra(@RequestBody Compras compra) {
        compra.setNumPedido(compraService.NumeroCompra(compra));

        return ResponseEntity.status(HttpStatus.CREATED).body(comprasRepository.save(compra));
    }

    @GetMapping("/pagar-compra/{id}")
    public String pagarCompra(@PathVariable("id") long id) {
        Optional<Compras> compraExistente = comprasRepository.findById(id);

        if (compraExistente.get().getStatus().equals("Pedido realizado")) {
            compraExistente.get().setStatus("Pagamento realizado");
        }

        comprasRepository.save(compraExistente.get());
        return "Pagamento realizado";
    }
    @PutMapping
    public ResponseEntity<Compras> putCompra(@RequestBody Compras compra) {
        return ResponseEntity.ok(comprasRepository.save(compra));
    }

    @DeleteMapping("/{id}")
    public void deleteCompra(@PathVariable long id) {
        comprasRepository.deleteById(id);
    }
}
