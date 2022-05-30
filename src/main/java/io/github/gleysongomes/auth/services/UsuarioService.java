package io.github.gleysongomes.auth.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import io.github.gleysongomes.auth.models.UsuarioModel;

public interface UsuarioService {

	boolean existsByLogin(String login);

	boolean existsByEmail(String email);

	void salvar(UsuarioModel usuarioModel);

	Page<UsuarioModel> listar(Specification<UsuarioModel> usuarioModelSpec, Pageable pageable);

	Optional<UsuarioModel> buscar(UUID cdUsuario);

	void excluir(UsuarioModel usuarioModel);

}
