package bsi.pm.aluguel.model;

public class Passaporte {

	private String numero;
	private String validade;
	private String pais;
	
	public Passaporte(String numero, String validade, String pais) {
		this.numero = numero;
		this.validade = validade;
		this.pais = pais;
	}
	
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getValidade() {
		return validade;
	}
	public void setValidade(String validade) {
		this.validade = validade;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}

}
