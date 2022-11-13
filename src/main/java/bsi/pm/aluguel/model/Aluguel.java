package bsi.pm.aluguel.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Aluguel {

	private String ciclista;
	private String trancaInicio;
	private String bicicleta;
	private String horaInicio;
	private String trancaFim;
	private String horaFim;
	private String cobranca;
	
	public Aluguel() {}
	
	public Aluguel(String ciclista, String trancaInicio) {
		this.ciclista = ciclista;
		this.trancaInicio = trancaInicio;
	}
	
	public Aluguel(String ciclista, String trancaInicio, String horaInicio) {
		this.ciclista = ciclista;
		this.trancaInicio = trancaInicio;
		this.horaInicio = horaInicio;
	}
	
	public Aluguel(String ciclista, String trancaInicio, String bicicleta,
				   String horaInicio, String trancaFim, String horaFim,
				   String cobranca) {
		this.ciclista = ciclista;
		this.trancaInicio = trancaInicio;
		this.bicicleta = bicicleta;
		this.horaInicio = horaInicio;
		this.trancaFim = trancaFim;
		this.horaFim = horaFim;
		this.cobranca = cobranca;
	}
	
	public String getCiclista() {
		return ciclista;
	}
	public void setCiclista(String ciclista) {
		this.ciclista = ciclista;
	}
	public String getTrancaInicio() {
		return trancaInicio;
	}
	public void setTrancaInicio(String trancaInicio) {
		this.trancaInicio = trancaInicio;
	}
	public String getBicicleta() {
		return bicicleta;
	}
	public void setBicicleta(String bicicleta) {
		this.bicicleta = bicicleta;
	}
	public String getHoraInicio() {
		return horaInicio;
	}
	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}
	public String getTrancaFim() {
		return trancaFim;
	}
	public void setTrancaFim(String trancaFim) {
		this.trancaFim = trancaFim;
	}
	public String getHoraFim() {
		return horaFim;
	}
	public void setHoraFim(String horaFim) {
		this.horaFim = horaFim;
	}
	public String getCobranca() {
		return cobranca;
	}
	public void setCobranca(String cobranca) {
		this.cobranca = cobranca;
	}
	
}
