package com.projetoOrion.ProjetoOrion.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;


import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "compra")
public class Compras {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_compras;

    @NotNull
    private String numPedido;

    @ManyToMany(mappedBy = "compra", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"pedidos", "carrinho", "compra"})
    private List<Produto> meuPedido = new ArrayList<>();

    @ManyToOne
    @JsonIgnoreProperties("compras")
    private Usuario usuario;

    @NotNull
    private String status;

    private double valorTotal;

    public long getId_compras() {
        return id_compras;
    }

    public void setId_compras(long id_compras) {
        this.id_compras = id_compras;
    }

    public @NotNull String getNumPedido() {
        return numPedido;
    }

    public void setNumPedido(@NotNull String numPedido) {
        this.numPedido = numPedido;
    }

    public List<Produto> getMeuPedido() {
        return meuPedido;
    }

    public void setMeuPedido(List<Produto> meuPedido) {
        this.meuPedido = meuPedido;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public @NotNull String getStatus() {
        return status;
    }

    public void setStatus(@NotNull String status) {
        this.status = status;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }
}
