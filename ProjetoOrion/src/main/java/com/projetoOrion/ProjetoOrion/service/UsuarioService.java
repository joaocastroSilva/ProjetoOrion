package com.projetoOrion.ProjetoOrion.service;

import com.projetoOrion.ProjetoOrion.model.Usuario;
import com.projetoOrion.ProjetoOrion.model.UsuarioLogin;
import com.projetoOrion.ProjetoOrion.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Base64;
import java.nio.charset.StandardCharsets;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuariorepository;

    // Cadastrar Usuario
    public Optional<Usuario> CadastrarUsuario(Usuario usuario) {
        if (usuariorepository.findByEmail(usuario.getEmail()).isPresent() && usuario.getId_usuario() == 0) {
            return null;
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String senhaEncoder = encoder.encode(usuario.getSenha());
        usuario.setSenha(senhaEncoder);

        return Optional.of(usuariorepository.save(usuario));
    }

    // Logar Usuario
    public Optional<UsuarioLogin> Logar(Optional<UsuarioLogin> usuarioLogin) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Optional<Usuario> usuario = usuariorepository.findByEmail(usuarioLogin.get().getEmail());

        if (usuario.isPresent()) {
            if (encoder.matches(usuarioLogin.get().getSenha(), usuario.get().getSenha())) {
                String auth = usuarioLogin.get().getEmail() + ":" + usuarioLogin.get().getSenha();
                String authHeader = "Basic " + Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));

                usuarioLogin.get().setToken(authHeader);
                usuarioLogin.get().setLogin(usuario.get().getUsuario());
                usuarioLogin.get().setEmail(usuario.get().getEmail());
                usuarioLogin.get().setSenha(usuario.get().getSenha());
                usuarioLogin.get().setId_Login(usuario.get().getId_usuario());
                usuarioLogin.get().setPedido(usuario.get().getPedidos());
                usuarioLogin.get().setLista(usuario.get().getCarrinho());

                return usuarioLogin;
            }
        }
        return null;
    }

    public Optional<Usuario> novoUsuario(Usuario usuario) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String senhaEncoder = encoder.encode(usuario.getSenha());
        usuario.setSenha(senhaEncoder);

        return Optional.of(usuariorepository.save(usuario));
    }
}
