package com.projetoOrion.ProjetoOrion.model;

import java.util.List;

public class UsuarioLogin {
    private long id_Login;
    private String login;
    private String email;
    private String senha;
    private String token;

    private List<Produto> pedido;
    private List<Produto> lista;

    public long getId_Login() {
        return id_Login;
    }

    public void setId_Login(long id_Login) {
        this.id_Login = id_Login;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<Produto> getPedido() {
        return pedido;
    }

    public void setPedido(List<Produto> pedido) {
        this.pedido = pedido;
    }

    public List<Produto> getLista() {
        return lista;
    }

    public void setLista(List<Produto> lista) {
        this.lista = lista;
    }
}
