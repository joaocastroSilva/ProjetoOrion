package com.projetoOrion.ProjetoOrion.controller;


import com.projetoOrion.ProjetoOrion.model.Produto;
import com.projetoOrion.ProjetoOrion.model.Usuario;
import com.projetoOrion.ProjetoOrion.model.UsuarioLogin;
import com.projetoOrion.ProjetoOrion.repository.UsuarioRepository;
import com.projetoOrion.ProjetoOrion.service.ProdutoService;
import com.projetoOrion.ProjetoOrion.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsuarioController {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<List<Usuario>> findAll() {

        System.out.println("Recebida requisição GET /usuario");
        return ResponseEntity.ok(usuarioRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);

        if (usuario.isPresent()) {
            // Exemplo de formatação do valorTotal
            double total = usuario.get().getValorTotal();
            NumberFormat formatter = new DecimalFormat("#0.00");
            usuario.get().setValorTotal(Double.parseDouble(formatter.format(total).replace(",",".")));
            // Remover carrinho
            usuario.get().setCarrinho(null); // ou manipular de acordo com a necessidade
            return ResponseEntity.ok(usuario.get());
        }

        return ResponseEntity.notFound().build();
    }


    @PostMapping("/logar")
    public ResponseEntity<UsuarioLogin> Autenticar(@RequestBody Optional<UsuarioLogin> user) {
        System.out.println("Recebida requisição POST /usuario/logar");
        return usuarioService.Logar(user).map(resp -> ResponseEntity.ok(resp))
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Usuario> Post(@RequestBody Usuario usuarios){
        System.out.println("Recebida requisição POST /usuario/cadastrar");
        Optional<Usuario> user = usuarioService.CadastrarUsuario(usuarios);
        try{
            return ResponseEntity.ok(user.get());
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }

    }

    @PutMapping("/atualizar")
    public ResponseEntity<Usuario> Atualizar(@RequestBody Usuario usuarios){
        System.out.println("Recebida requisição PUT /usuario/atualizar");
        Optional<Usuario> user = usuarioService.novoUsuario(usuarios);
        try{
            return ResponseEntity.ok(user.get());
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
    @PutMapping
    public ResponseEntity<Usuario> putCliente(@RequestBody Usuario usuario) {
        System.out.println("Recebida requisição PUT /usuario");
        return ResponseEntity.ok(usuarioRepository.save(usuario));
    }

    @GetMapping("/produto_lista/produtos/{idProduto}/lista/{idCliente}")
    public ResponseEntity<Produto> removeLista(@PathVariable long idProduto, @PathVariable long idCliente) {
        System.out.println("Recebida requisição GET /usuario/produto_lista/produtos/" + idProduto + "/lista/" + idCliente);
        return ResponseEntity.ok(produtoService.removeProdutoLista(idProduto, idCliente));
    }
    @GetMapping("/produto_pedido/produtos/{idProduto}/pedidos/{idCliente}")
    public ResponseEntity<Produto> putProduto(@PathVariable long idProduto, @PathVariable long idCliente) {
        System.out.println("Recebida requisição GET /usuario/produto_pedido/produtos/" + idProduto + "/pedidos/" + idCliente);
        return ResponseEntity.ok(produtoService.deletarProduto(idProduto, idCliente));
    }
    @DeleteMapping("/{id}")
    public void deletaCliente(@PathVariable long id) {
        System.out.println("Recebida requisição DELETE /usuario/" + id);
        usuarioRepository.deleteById(id);
    }

}
