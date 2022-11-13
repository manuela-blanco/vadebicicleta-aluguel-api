package bsi.pm.aluguel.model;

public class Devolucao {

	private String trancaFim;
	private String ciclista;
	
	public Devolucao(String ciclista, String trancaFim) {
		this.ciclista = ciclista;
		this.trancaFim = trancaFim;
	}
	
	public Devolucao() {}
	
	public String getTrancaFim() {
		return trancaFim;
	}
	public void setTrancaFim(String trancaFim) {
		this.trancaFim = trancaFim;
	}

	public String getCiclista() {
		return ciclista;
	}

	public void setCiclista(String ciclista) {
		this.ciclista = ciclista;
	}
	
}
