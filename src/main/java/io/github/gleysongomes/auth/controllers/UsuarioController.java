package io.github.gleysongomes.auth.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import io.github.gleysongomes.auth.dtos.UsuarioDto;
import io.github.gleysongomes.auth.models.UsuarioModel;
import io.github.gleysongomes.auth.services.UsuarioService;
import io.github.gleysongomes.auth.specifications.SpecificationTemplate;

@RestController
@RequestMapping(path = "/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", maxAge = 3600)
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@GetMapping
	public ResponseEntity<Page<UsuarioModel>> listar(SpecificationTemplate.UsuarioSpec usuarioSpec,
			@PageableDefault(page = 0, size = 10, sort = "cdUsuario", direction = Sort.Direction.ASC) Pageable pageable) {
		Page<UsuarioModel> usuarioModelPage = usuarioService.listar(usuarioSpec, pageable);
		if (!usuarioModelPage.isEmpty()) {
			for (UsuarioModel usuarioModel : usuarioModelPage.toList()) {
				usuarioModel.add(
						linkTo(methodOn(UsuarioController.class).buscar(usuarioModel.getCdUsuario())).withSelfRel());
			}
		}

		return ResponseEntity.status(HttpStatus.OK).body(usuarioModelPage);
	}

	@GetMapping("/{cdUsuario}")
	public ResponseEntity<Object> buscar(@PathVariable(value = "cdUsuario") UUID cdUsuario) {
		Optional<UsuarioModel> usuaurioModel = usuarioService.findById(cdUsuario);
		if (!usuaurioModel.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(usuaurioModel.get());
		}
	}

	@PutMapping("/{cdUsuario}")
	public ResponseEntity<Object> atualizar(@PathVariable(value = "cdUsuario") UUID cdUsuario,
			@RequestBody @Validated(UsuarioDto.UsuarioView.UsuarioPut.class) @JsonView(UsuarioDto.UsuarioView.UsuarioPut.class) UsuarioDto usuarioDto) {
		Optional<UsuarioModel> usuarioModelOptional = usuarioService.findById(cdUsuario);
		if (!usuarioModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
		} else {
			var usuarioModel = usuarioModelOptional.get();
			usuarioModel.setNome(usuarioDto.getNome());
			usuarioModel.setDtAtualizacao(LocalDateTime.now(ZoneId.of("UTC")));
			usuarioService.save(usuarioModel);
			return ResponseEntity.status(HttpStatus.OK).body(usuarioModel);
		}
	}

	@PutMapping("/{cdUsuario}/senha")
	public ResponseEntity<Object> atualizarSenha(@PathVariable(value = "cdUsuario") UUID cdUsuario,
			@RequestBody @Validated(UsuarioDto.UsuarioView.SenhaPut.class) @JsonView(UsuarioDto.UsuarioView.SenhaPut.class) UsuarioDto usuarioDto) {
		Optional<UsuarioModel> usuarioModelOptional = usuarioService.findById(cdUsuario);
		if (!usuarioModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
		} else if (!usuarioModelOptional.get().getSenha().equals(usuarioDto.getSenha())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("A senha atual não está correta.");
		} else {
			var usuarioModel = usuarioModelOptional.get();
			usuarioModel.setSenha(usuarioDto.getNovaSenha());
			usuarioModel.setDtAtualizacao(LocalDateTime.now(ZoneId.of("UTC")));
			usuarioService.save(usuarioModel);
			return ResponseEntity.status(HttpStatus.OK).body("A senha foi atualizada.");
		}
	}

	@DeleteMapping("/{cdUsuario}")
	public ResponseEntity<Object> excluir(@PathVariable(value = "cdUsuario") UUID cdUsuario) {
		Optional<UsuarioModel> usuarioModel = usuarioService.findById(cdUsuario);
		if (!usuarioModel.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
		} else {
			usuarioService.excluir(usuarioModel.get());
			return ResponseEntity.status(HttpStatus.OK).body("Usuário excluído com sucesso.");
		}
	}
}
