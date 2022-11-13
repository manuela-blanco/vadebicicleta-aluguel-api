package bsi.pm.aluguel.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import bsi.pm.aluguel.utils.StatusCobranca;

@JsonInclude(Include.NON_DEFAULT)
public class Cobranca {

	private String id;
	private String ciclista;
	private int valor;
	private StatusCobranca status;
	private String horaSolicitacao;
	private String horaFinalizacao;
	
	
	public Cobranca(String ciclista, int valor) {
		this.ciclista = ciclista;
		this.valor = valor;
	}
	
	public Cobranca(String id, String ciclista, int valor) {
		this.id = id;
		this.ciclista = ciclista;
		this.valor = valor;
	}

	public Cobranca() {}

	
	public StatusCobranca getStatus() {
		return status;
	}

	public void setStatus(StatusCobranca status) {
		this.status = status;
	}

	public String getHoraSolicitacao() {
		return horaSolicitacao;
	}

	public void setHoraSolicitacao(String horaSolicitacao) {
		this.horaSolicitacao = horaSolicitacao;
	}

	public String getHoraFinalizacao() {
		return horaFinalizacao;
	}

	public void setHoraFinalizacao(String horaFinalizacao) {
		this.horaFinalizacao = horaFinalizacao;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCiclista() {
		return ciclista;
	}

	public void setCiclista(String ciclista) {
		this.ciclista = ciclista;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}
	
}

