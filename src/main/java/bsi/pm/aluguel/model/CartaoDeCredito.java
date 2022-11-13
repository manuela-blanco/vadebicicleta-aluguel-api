package bsi.pm.aluguel.model;


public class CartaoDeCredito {

	private String id;
	private String nomeTitular;
	private String numero;
	private String validade;
	private String cvv;

	public CartaoDeCredito() {
	}

	public CartaoDeCredito(String id, String nomeTitular, String numero, String validade, String cvv) {
		this.id = id;
		this.nomeTitular = nomeTitular;
		this.numero = numero;
		this.validade = validade;
		this.cvv = cvv;
	}

	public CartaoDeCredito(String nomeTitular, String numero, String validade, String cvv) {
		this.nomeTitular = nomeTitular;
		this.numero = numero;
		this.validade = validade;
		this.cvv = cvv;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNomeTitular() {
		return nomeTitular;
	}

	public void setNomeTitular(String nomeTitular) {
		this.nomeTitular = nomeTitular;
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

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}
}
