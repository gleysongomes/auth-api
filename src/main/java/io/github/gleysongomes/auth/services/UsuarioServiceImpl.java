package io.github.gleysongomes.auth.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.gleysongomes.auth.models.UsuarioModel;
import io.github.gleysongomes.auth.repositories.UsuarioRepository;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public boolean existsByLogin(String login) {
		return usuarioRepository.existsByLogin(login);
	}

	@Override
	public boolean existsByEmail(String email) {
		return usuarioRepository.existsByEmail(email);
	}

	@Override
	public void salvar(UsuarioModel usuarioModel) {
		usuarioRepository.save(usuarioModel);
	}

	@Override
	public Page<UsuarioModel> listar(Specification<UsuarioModel> usuarioModelSpec, Pageable pageable) {
		return usuarioRepository.findAll(usuarioModelSpec, pageable);
	}

	@Override
	public Optional<UsuarioModel> buscar(UUID cdUsuario) {
		return usuarioRepository.findById(cdUsuario);
	}

	@Override
	public void excluir(UsuarioModel usuarioModel) {
		usuarioRepository.delete(usuarioModel);
	}

}
