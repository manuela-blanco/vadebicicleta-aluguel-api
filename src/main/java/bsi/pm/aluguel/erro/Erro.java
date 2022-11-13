package bsi.pm.aluguel.erro;

public class Erro {

	private String id;
	private String codigo;
	private String mensagem;
	
	public Erro(String id, String codigo, String mensagem) {
		this.id = id;
		this.codigo = codigo;
		this.mensagem = mensagem;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

}
