package io.github.gleysongomes.auth.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import io.github.gleysongomes.auth.dtos.UsuarioDto;
import io.github.gleysongomes.auth.enums.StatusUsuario;
import io.github.gleysongomes.auth.models.UsuarioModel;
import io.github.gleysongomes.auth.services.UsuarioService;

@RestController
@RequestMapping(path = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", maxAge = 3600)
public class AutenticacaoController {

	@Autowired
	private UsuarioService usuarioService;

	@PostMapping("/cadastro")
	public ResponseEntity<Object> cadastrar(
			@RequestBody @Validated(UsuarioDto.UsuarioView.UsuarioPost.class) @JsonView(UsuarioDto.UsuarioView.UsuarioPost.class) UsuarioDto usuarioDto) {
		if (usuarioService.existsByLogin(usuarioDto.getLogin())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Esse login já existe.");
		}
		if (usuarioService.existsByEmail(usuarioDto.getEmail())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Esse email já existe.");
		}
		var usuarioModel = new UsuarioModel();
		BeanUtils.copyProperties(usuarioDto, usuarioModel);
		usuarioModel.setStatusUsuario(StatusUsuario.ATIVO);
		usuarioModel.setDtCadastro(LocalDateTime.now(ZoneId.of("UTC")));
		usuarioService.save(usuarioModel);
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioModel);
	}
}
