package com.projetoOrion.ProjetoOrion.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "produto")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_Produto;

    @NotNull
    @Size(max = 50)
    private String nome;

    @NotNull
    private double preco;

    @NotNull
    private int estoque;

    private int qtdPedido;

    @ManyToOne
    @JsonIgnoreProperties("produtos")
    private Categoria categoria;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "produto_pedido",
            joinColumns = @JoinColumn(name = "produto_id"),
            inverseJoinColumns = @JoinColumn(name = "pedido_id")
    )
    @JsonIgnoreProperties({"pedidos", "carrinhos", "compras", "meuPedido"})
    private List<Usuario> pedidos = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "produto_lista",
            joinColumns = @JoinColumn(name = "produto_id"),
            inverseJoinColumns = @JoinColumn(name = "lista_id")
    )
    @JsonIgnoreProperties({"pedidos", "carrinhos"})
    private List<Usuario> carrinhos = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "produto_compra",
            joinColumns = @JoinColumn(name = "produto_id"),
            inverseJoinColumns = @JoinColumn(name = "compra_id")
    )
    @JsonIgnoreProperties({"meuPedido"})
    private List<Compras> compra = new ArrayList<>();

    // GETTERS E SETTERS

    public long getId_Produto() {
        return id_Produto;
    }

    public void setId_Produto(long id_Produto) {
        this.id_Produto = id_Produto;
    }

    public @NotNull @Size(max = 50) String getNome() {
        return nome;
    }

    public void setNome(@NotNull @Size(max = 50) String nome) {
        this.nome = nome;
    }

    @NotNull
    public double getPreco() {
        return preco;
    }

    public void setPreco(@NotNull double preco) {
        this.preco = preco;
    }

    public int getQtdPedido() {
        return qtdPedido;
    }

    public void setQtdPedido(int qtdPedido) {
        this.qtdPedido = qtdPedido;
    }

    @NotNull
    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(@NotNull int estoque) {
        this.estoque = estoque;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public List<Usuario> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Usuario> pedidos) {
        this.pedidos = pedidos;
    }

    public List<Usuario> getCarrinhos() {
        return carrinhos;
    }

    public void setCarrinhos(List<Usuario> carrinhos) {
        this.carrinhos = carrinhos;
    }

    public List<Compras> getCompra() {
        return compra;
    }

    public void setCompra(List<Compras> compra) {
        this.compra = compra;
    }
}
