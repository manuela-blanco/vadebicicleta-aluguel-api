package bsi.pm.aluguel.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import bsi.pm.aluguel.utils.Funcao;

@JsonInclude(Include.NON_DEFAULT)
public class Funcionario {
	private String id;
	private String senha;
	private String email;
	private String nome;
	private int idade;
	private Funcao funcao;
	private String cpf;
	private String matricula;
	
	public Funcionario() {}
	
	public Funcionario(String senha, String email, String cpf, Funcao funcao,
					   int idade, String nome) {
		this.senha = senha;
		this.email = email;
		this.cpf = cpf;
		this.funcao = funcao;
		this.idade = idade;
		this.nome = nome;
	}
	
	public Funcionario(String senha, String email, String cpf, Funcao funcao,
			   int idade, String nome, String matricula) {
		this.senha = senha;
		this.email = email;
		this.cpf = cpf;
		this.funcao = funcao;
		this.idade = idade;
		this.nome = nome;
		this.matricula = matricula;
	}
	
	public Funcionario(String id, String senha, String email, String cpf, Funcao funcao,
			  		   String nome, String matricula) {
		this.id = id;
		this.senha = senha;
		this.email = email;
		this.cpf = cpf;
		this.funcao = funcao;
		this.nome = nome;
		this.matricula = matricula;
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
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getIdade() {
		return idade;
	}
	public void setIdade(int idade) {
		this.idade = idade;
	}
	public Funcao getFuncao() {
		return funcao;
	}
	public void setFuncao(Funcao funcao) {
		this.funcao = funcao;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
