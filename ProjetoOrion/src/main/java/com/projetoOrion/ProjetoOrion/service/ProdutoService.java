package com.projetoOrion.ProjetoOrion.service;


import com.projetoOrion.ProjetoOrion.model.Produto;
import com.projetoOrion.ProjetoOrion.model.Usuario;
import com.projetoOrion.ProjetoOrion.repository.ProdutoRepository;
import com.projetoOrion.ProjetoOrion.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    double a = 0;
    int posicacao = 0; //armazenar a posicao do item no array
    @Autowired
    private UsuarioService usuarioService;

    public Produto comprarProduto(long idProduto, long idUsuario) {

        Optional<Produto> produtoExistente = produtoRepository.findById(idProduto);
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(idUsuario);

        if(produtoExistente.isPresent() && usuarioExistente.isPresent() && produtoExistente.get().getEstoque() >= 0 && !(usuarioExistente.get().getPedidos().isEmpty())){
            produtoExistente.get().getPedidos().add(usuarioExistente.get());

            produtoExistente.get().setEstoque(produtoExistente.get().getEstoque() - 1);

            int contadorProduto = 0;

            for (Produto produto : usuarioExistente.get().getPedidos()) {
                for (Produto produto1 : usuarioExistente.get().getPedidos()){
                    //verifica se existe uma repeticao do produto
                    if (produto.getId_Produto() == produto1.getId_Produto()){
                        contadorProduto = contadorProduto + 1;
                        produto.setQtdPedido(contadorProduto);//adiciona o valor no contador

                        //diminui um produto no estoque
                        produtoExistente.get().setQtdPedido(contadorProduto);
                    }
                }
            }

            //Exclui o valor existente do carrinho e recalcula
            usuarioExistente.get().setValorTotal(usuarioExistente.get().getValorTotal() - (produtoExistente.get().getPreco() * contadorProduto));

            contadorProduto = contadorProduto + 1;

            //Atualiza o valor do carrinho do usuario
            usuarioExistente.get().setValorTotal(usuarioExistente.get().getValorTotal() + (produtoExistente.get().getPreco() * contadorProduto));

            produtoRepository.save(produtoExistente.get());
            usuarioRepository.save(usuarioExistente.get());
            usuarioRepository.save(usuarioExistente.get()).getValorTotal();
            return produtoRepository.save(produtoExistente.get());
        }
    return null;
    }

    public List<Produto> excluirProdutoDuplicado(Optional<Usuario> usuario){
        List<Produto> produtos = new ArrayList<>();

        int contadorProduto = 0;

        for (Produto produto : usuario.get().getPedidos()){
            for (Produto produto1 : usuario.get().getPedidos()){
                System.out.println(produto1.getNome());
                if (produto.getId_Produto() == produto1.getId_Produto()){

                    contadorProduto = contadorProduto + 1;
                    produto.setQtdPedido(contadorProduto);

                }
            }
            if(produtos.contains(produto)){
                produtos.add(produto);
            }

            contadorProduto = 0;
        }

        contadorProduto = 0;

        return produtos;


    }
    public Produto deletarProduto(long idProduto, long idUsuario) {
        Optional<Produto> produtoExistente = produtoRepository.findById(idProduto);
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(idUsuario);

        if(produtoExistente.isPresent() && usuarioExistente.isPresent()){
            int contadorProduto = 0;

            for (Produto produto : usuarioExistente.get().getPedidos()){
                for (Produto produto1 : usuarioExistente.get().getPedidos()){
                    if (produto.getId_Produto() == produto1.getId_Produto()){
                        contadorProduto = contadorProduto + 1;
                        produto.setQtdPedido(contadorProduto);
                        produtoExistente.get().setQtdPedido(contadorProduto);
                    }
                }

                contadorProduto = 0;
            }

            produtoExistente.get().getPedidos().remove(usuarioExistente.get());
            usuarioExistente.get().getPedidos().remove(produtoExistente);//remove do usuario o produto

            produtoExistente.get().setEstoque(produtoExistente.get().getEstoque() + 1);

            System.out.println("Valor do Carrinho: "+ produtoExistente.get().getQtdPedido());

            System.out.println("Contador: "+ produtoExistente.get().getQtdPedido());
            System.out.println("Valor do Produto = "+ produtoExistente.get().getPreco() +" x contador = "+ 1);
            System.out.println("subtracao apliacada, preco do produto x contador: "+ produtoExistente.get().getPreco() * 1);

            double total = usuarioExistente.get().getValorTotal() - (produtoExistente.get().getPreco() * 1);
            NumberFormat formatter = new DecimalFormat("#0.00");
            usuarioExistente.get().setValorTotal(Double.parseDouble(formatter.format(total).replace(",", ".")));

            produtoRepository.save(produtoExistente.get());
            usuarioRepository.save(usuarioExistente.get());
            usuarioRepository.save(usuarioExistente.get()).getValorTotal();
            return produtoRepository.save(produtoExistente.get());

        }
        return null;
    }

    public Produto adicionarProdutoaoCarrinho(long idProduto, long idCiente) {
        Optional<Produto> produtoExistente = produtoRepository.findById(idProduto);
        Optional<Usuario> clienteExistente = usuarioRepository.findById(idCiente);

        if (produtoExistente.isPresent() && clienteExistente.isPresent() && !(produtoExistente.get().getCarrinhos().contains(clienteExistente.get()))) {

            System.out.println("Acessou o produto por id");
            produtoExistente.get().getCarrinhos().add(clienteExistente.get());
            System.out.println("Adicionou o produto a lista");
            produtoRepository.save(produtoExistente.get());
            System.out.println("Salvou o produto com o novo dado");
            return produtoRepository.save(produtoExistente.get());

        }

        return null;
    }

    public Produto removeProdutoLista(long idProduto, long idListaDeDesejo) {
        Optional<Produto> produtoExistente = produtoRepository.findById(idProduto);
        Optional<Usuario> clienteExistente = usuarioRepository.findById(idListaDeDesejo);

        if(produtoExistente.get().getCarrinhos().contains(clienteExistente.get())) {
            produtoExistente.get().getCarrinhos().remove(clienteExistente.get());
            produtoRepository.save(produtoExistente.get());

            return produtoRepository.save(produtoExistente.get());

        }

        return null;

    }

    //Pesquisa o produto por id na lista
    public List<Produto> pesquisapoID(long idlista, String nome) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(idlista);

        long[] vetor = new long[usuarioExistente.get().getPedidos().size()];

        for(int i = 0; i < vetor.length; i++) {

            if(usuarioExistente.get().getPedidos().get(i).getNome().contains(nome)) {

                return produtoRepository.findByNome(nome);
            }

        }

        return null;

    }

    //Pesquisa por produto na lista
    public List<Produto> pesquisalista(long idlista) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(idlista);

        if(usuarioExistente.isPresent()) {
            usuarioExistente.get().getPedidos();

            return usuarioRepository.save(usuarioExistente.get()).getPedidos();

        }

        return null;

    }
    public List<Produto> pesquisaProdutoCarrinho(long idUsuario) {
        Optional<Usuario> clienteExistente = usuarioRepository.findById(idUsuario);

        if(clienteExistente.isPresent()) {
            clienteExistente.get().getPedidos();

            return usuarioRepository.save(clienteExistente.get()).getPedidos();

        }

        return null;

    }
}
