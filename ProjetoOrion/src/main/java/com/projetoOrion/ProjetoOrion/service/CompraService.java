package com.projetoOrion.ProjetoOrion.service;


import com.projetoOrion.ProjetoOrion.model.Compras;
import com.projetoOrion.ProjetoOrion.model.Produto;
import com.projetoOrion.ProjetoOrion.repository.ComprasRepository;
import com.projetoOrion.ProjetoOrion.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CompraService {
    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ComprasRepository comprasRepository;

    @Autowired
    private ProdutoService produtoService;

    double a = 0;
    int posicao = 0;

    public boolean comprarProduto(long idProduto, long idCompra) {
        Optional<Produto> produtoEstoque = produtoRepository.findById(idProduto);
        Optional<Compras> compraExistente = comprasRepository.findById(idCompra);

        System.out.println("Produto: " + produtoEstoque.isPresent());
        System.out.println("Compra: " + compraExistente.isPresent());

        //Se tiver em estoque , e o pedido existir então entra nessa condição
        if (produtoEstoque.isPresent() && compraExistente.isPresent() && produtoEstoque.get().getEstoque() >= 0 && !(compraExistente.get().getMeuPedido().isEmpty())) {
            produtoService.deletarProduto(idProduto, compraExistente.get().getUsuario().getId_usuario());

            //Adicionar no carrinho do usuario
            produtoEstoque.get().getCompra().add(compraExistente.get());

            //Diminui no estoque sempre que um pedido vai para o carrinho
            produtoEstoque.get().setEstoque(produtoEstoque.get().getEstoque() - 1);

            int contadorProduto = 0;

            for (Produto produto : compraExistente.get().getMeuPedido()) {
                for (Produto produtoRepeticaco : compraExistente.get().getMeuPedido()) {
                    if (produto.getId_Produto() == produtoRepeticaco.getId_Produto()) {
                        contadorProduto = contadorProduto + 1;
                        produto.setQtdPedido(contadorProduto);

                        produtoEstoque.get().setQtdPedido(contadorProduto);
                    }
                }
            }
            compraExistente.get().setValorTotal(compraExistente.get().getValorTotal()-(produtoEstoque.get().getPreco() * contadorProduto));

            contadorProduto = contadorProduto + 1;

            compraExistente.get().setValorTotal(compraExistente.get().getValorTotal() + (produtoEstoque.get().getPreco()*contadorProduto));

            produtoRepository.save(produtoEstoque.get());
            comprasRepository.save(compraExistente.get());
            comprasRepository.save(compraExistente.get()).getValorTotal();

            return true;
        }
        return false;

    }

    public String NumeroCompra(Compras c) {
        List<Compras> listaCompras = comprasRepository.findAll();
        try{
            long numPedido;

            if(listaCompras.size() == 0){
                numPedido = 00000001;
            }else{
                numPedido = Long.parseLong(listaCompras.get((listaCompras.size()-1)).getNumPedido().replace("0"," "))+1;

            }
            switch (String.valueOf(numPedido).length()){
                case 1:
                    c.setNumPedido("0000000"+ String.valueOf(numPedido));
                    break;
                case 2:
                    c.setNumPedido("000000"+ String.valueOf(numPedido));
                    break;
                case 3:
                    c.setNumPedido("00000"+ String.valueOf(numPedido));
                    break;
                case 4:
                    c.setNumPedido("0000"+ String.valueOf(numPedido));
                    break;
                case 5:
                    c.setNumPedido("000"+ String.valueOf(numPedido));
                    break;
                case 6:
                    c.setNumPedido("00"+ String.valueOf(numPedido));
                    break;
                case 7:
                    c.setNumPedido("0"+ String.valueOf(numPedido));
                    break;
                case 8:
                    c.setNumPedido(String.valueOf(numPedido));
                    break;
                default:
                    c.setNumPedido(String.valueOf(numPedido));
                    break;
            }

        }catch (Exception e){

        }
        return c.getNumPedido();
    }

    public List<Produto> produtoDuplicado(Optional<Compras> compra){
        List<Produto> produtos = new ArrayList<>();

        int contadorProduto = 0;

        for (Produto produto: compra.get().getMeuPedido()){
            for(Produto produtoRepeticaco : compra.get().getMeuPedido()){
                if(produto.getId_Produto() == produtoRepeticaco.getId_Produto()){
                    contadorProduto = contadorProduto + 1;
                    produto.setQtdPedido(contadorProduto);
                }
            }

            if(!produtos.contains(produto)){
                produtos.add(produto);
            }

            contadorProduto = 0;
        }
        contadorProduto = 0;

        return produtos;
    }

}