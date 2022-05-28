package io.github.gleysongomes.auth.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;

import io.github.gleysongomes.auth.validation.LoginConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsuarioDto {

	@EqualsAndHashCode.Include
	@Size(min = 4, max = 50, groups = UsuarioView.UsuarioPost.class)
	@NotBlank(groups = UsuarioView.UsuarioPost.class)
	@JsonView(UsuarioView.UsuarioPost.class)
	@LoginConstraint(groups = UsuarioView.UsuarioPost.class)
	private String login;

	@JsonView({ UsuarioView.UsuarioPost.class, UsuarioView.UsuarioPut.class })
	private String nome;

	@Size(max = 50, groups = UsuarioView.UsuarioPost.class)
	@NotBlank(groups = UsuarioView.UsuarioPost.class)
	@Email(groups = UsuarioView.UsuarioPost.class)
	@JsonView(UsuarioView.UsuarioPost.class)
	private String email;

	@Size(min = 6, max = 20, groups = { UsuarioView.UsuarioPost.class, UsuarioView.SenhaPut.class })
	@NotBlank(groups = { UsuarioView.UsuarioPost.class, UsuarioView.SenhaPut.class })
	@JsonView({ UsuarioView.UsuarioPost.class, UsuarioView.SenhaPut.class })
	private String senha;

	@Size(min = 6, max = 20, groups = UsuarioView.SenhaPut.class)
	@NotBlank(groups = UsuarioView.SenhaPut.class)
	@JsonView(UsuarioView.SenhaPut.class)
	private String novaSenha;

	public interface UsuarioView {

		public static interface UsuarioPost {
		}

		public static interface UsuarioPut {
		}

		public static interface SenhaPut {
		}
	}
}
