package io.github.gleysongomes.auth.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import io.github.gleysongomes.auth.models.UsuarioModel;

public interface UsuarioRepository extends JpaRepository<UsuarioModel, UUID>, JpaSpecificationExecutor<UsuarioModel> {

	boolean existsByLogin(String login);

	boolean existsByEmail(String login);
}
