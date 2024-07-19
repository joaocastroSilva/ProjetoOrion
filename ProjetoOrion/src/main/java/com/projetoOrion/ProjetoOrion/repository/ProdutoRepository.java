package com.projetoOrion.ProjetoOrion.repository;


import com.projetoOrion.ProjetoOrion.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    public List<Produto> findByNome(String nome);

    //public List<Produto> findByQtdPedidoProduto(int qtdPedidoProduto);

}
