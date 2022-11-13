package bsi.pm.aluguel.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import bsi.pm.aluguel.utils.Nacionalidade;
import bsi.pm.aluguel.utils.StatusCiclista;

@JsonInclude(Include.NON_NULL)
public class Ciclista {

	private String id;
	private String nome;
	private String nascimento;
	private String cpf;
	private String email;
	private String senha;
	private Passaporte passaporte;
	private Nacionalidade nacionalidade;
	private StatusCiclista status;
	
	public Ciclista(String email, Nacionalidade nacionalidade, String nascimento, String nome, String senha, String cpf) {
		this.email = email;
		this.nacionalidade = nacionalidade;
		this.nascimento = nascimento;
		this.nome = nome;
		this.senha = senha;
		this.cpf = cpf;
	}

	public Ciclista(String email, Nacionalidade nacionalidade, String nascimento, String nome, String senha,
					String id, StatusCiclista status) {
		this.email = email;
		this.nacionalidade = nacionalidade;
		this.nascimento = nascimento;
		this.nome = nome;
		this.senha = senha;
		this.id = id;
		this.status = status;
	}

	public Ciclista() {}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNascimento() {
		return nascimento;
	}

	public void setNascimento(String nascimento) {
		this.nascimento = nascimento;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Passaporte getPassaporte() {
		return passaporte;
	}

	public void setPassaporte(Passaporte passaporte) {
		this.passaporte = passaporte;
	}

	public Nacionalidade getNacionalidade() {
		return nacionalidade;
	}

	public void setNacionalidade(Nacionalidade nacionalidade) {
		this.nacionalidade = nacionalidade;
	}

	public StatusCiclista getStatus() {
		return status;
	}

	public void setStatus(StatusCiclista status) {
		this.status = status;
	}

}
