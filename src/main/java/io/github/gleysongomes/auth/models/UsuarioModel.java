package io.github.gleysongomes.auth.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.github.gleysongomes.auth.enums.StatusUsuario;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "tb_usuario")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsuarioModel extends RepresentationModel<UsuarioModel> implements Serializable {

	private static final long serialVersionUID = -9199731096075382022L;

	@Id
	@Column(name = "cd_usuario")
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID cdUsuario;

	@Column(nullable = false, unique = true, length = 50)
	private String login;

	@Column(nullable = false, length = 150)
	private String nome;

	@Column(nullable = false, unique = true, length = 50)
	private String email;

	@JsonIgnore
	@Column(name = "senha", nullable = false, length = 255)
	private String senha;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
	@Column(name = "dt_cadastro", nullable = false)
	private LocalDateTime dtCadastro;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
	@Column(name = "dt_atualizacao")
	private LocalDateTime dtAtualizacao;

	@Column(name = "status_usuario", nullable = false)
	@Enumerated(EnumType.STRING)
	private StatusUsuario statusUsuario;

}
