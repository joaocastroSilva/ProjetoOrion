package com.projetoOrion.ProjetoOrion.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_usuario;

    @NotNull
    @Size(max = 50)
    private String nome;

    @NotNull
    @Size(max = 50)
    private String usuario;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String senha;

    private double valorTotal;

    @ManyToMany(mappedBy = "pedidos", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"pedidos", "listaDesejos", "compra"})
    private List<Produto> pedidos = new ArrayList<>();

    @ManyToMany(mappedBy = "carrinhos", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"pedidos", "carrinhos", "compra"})
    private List<Produto> carrinho = new ArrayList<>();


    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("usuario")
    private List<Compras> compras;

    // GETTERS E SETTERS

    public long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public @NotNull @Size(max = 50) String getNome() {
        return nome;
    }

    public void setNome(@NotNull @Size(max = 50) String nome) {
        this.nome = nome;
    }

    public @NotNull @Email String getEmail() {
        return email;
    }

    public void setEmail(@NotNull @Email String email) {
        this.email = email;
    }

    public @NotNull String getSenha() {
        return senha;
    }

    public void setSenha(@NotNull String senha) {
        this.senha = senha;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public @NotNull @Size(max = 50) String getUsuario() {
        return usuario;
    }

    public void setUsuario(@NotNull @Size(max = 50) String usuario) {
        this.usuario = usuario;
    }

    public List<Produto> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Produto> pedidos) {
        this.pedidos = pedidos;
    }

    public List<Produto> getCarrinho() {
        return carrinho;
    }

    public void setCarrinho(List<Produto> carrinho) {
        this.carrinho = carrinho;
    }

    public List<Compras> getCompras() {
        return compras;
    }

    public void setCompras(List<Compras> compras) {
        this.compras = compras;
    }
}
