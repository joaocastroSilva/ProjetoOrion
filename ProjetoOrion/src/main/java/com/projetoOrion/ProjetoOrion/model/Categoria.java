package com.projetoOrion.ProjetoOrion.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "categoria")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_Categoria;

    @NotNull
    @Size(max = 50)
    private String nome;

    @OneToMany(mappedBy = "categoria", cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties("categoria")
    private List<Produto> produtos;

    //GET E SET

    public long getId_Categoria() {
        return id_Categoria;
    }

    public void setId_Categoria(long id_Categoria) {
        this.id_Categoria = id_Categoria;
    }

    public @NotNull @Size(max = 50) String getNome() {
        return nome;
    }

    public void setNome(@NotNull @Size(max = 50) String nome) {
        this.nome = nome;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }
}
