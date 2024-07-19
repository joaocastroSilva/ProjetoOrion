package com.projetoOrion.ProjetoOrion.controller;


import com.projetoOrion.ProjetoOrion.model.Produto;
import com.projetoOrion.ProjetoOrion.repository.ProdutoRepository;
import com.projetoOrion.ProjetoOrion.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutoController {
    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping
    public ResponseEntity<List<Produto>> findAllByProduts(){
        return ResponseEntity.ok(produtoRepository.findAll());

    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> findById(@PathVariable long id){
        return produtoRepository.findById(id)
                .map(resp -> ResponseEntity.ok(resp))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<Produto>> findAllByNomeProduto(@PathVariable String nome){
        return ResponseEntity.ok(produtoRepository.findByNome(nome));
    }
    @PostMapping
    public ResponseEntity<Produto> postProduto(@RequestBody Produto produto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(produtoRepository.save(produto));
    }
    @PutMapping
    public ResponseEntity<Produto> putProduto(@RequestBody Produto produto) {

        return ResponseEntity.ok(produtoRepository.save(produto));
    }
    @GetMapping("/produto_pedido/produtos/{idProduto}/pedidos/{idCliente}")
    public ResponseEntity<Produto> putProduto(@PathVariable long idProduto, @PathVariable long idCliente) {

        return ResponseEntity.ok(produtoService.comprarProduto(idProduto, idCliente));
    }
    @GetMapping("/produto_lista/produtos/{idProduto}/listaDesejos/{idCliente}")
    public ResponseEntity<Produto> adicionaProdutoListaDeDesejos(@PathVariable long idProduto, @PathVariable long idCliente) {

        return ResponseEntity.ok(produtoService.adicionarProdutoaoCarrinho(idProduto, idCliente));
    }
    @DeleteMapping("/{id}")
    public void deleteProduto(@PathVariable long id) {

        produtoRepository.deleteById(id);
    }
}
