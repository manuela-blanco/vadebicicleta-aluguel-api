package bsi.pm.aluguel.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import bsi.pm.aluguel.model.Aluguel;
import bsi.pm.aluguel.model.Ciclista;
import bsi.pm.aluguel.model.Cobranca;
import bsi.pm.aluguel.model.Devolucao;
import bsi.pm.aluguel.model.NovoEmail;

@Service
public class AluguelService {
	
	private static final String URL_EQUIPAMENTO = "https://equipamento.herokuapp.com";
	private static final String URL_EXTERNOS = "https://externos.herokuapp.com";
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private SimpleDateFormat formatter;
	
	@Autowired
	private CiclistaService ciclistaService;
	
	public Aluguel realizarAluguel(Aluguel novoAluguel) {
		Cobranca cobranca = this.enviaCobranca(novoAluguel.getCiclista());
		if(Objects.nonNull(cobranca)) {
			novoAluguel.setCobranca(cobranca.getId());
			novoAluguel.setHoraInicio(this.formatter.format(new Date()));
			Ciclista ciclista = this.ciclistaService.retornaCiclista(novoAluguel.getCiclista());
			this.liberaTranca(novoAluguel.getTrancaInicio());
			this.enviaEmail(ciclista.getEmail(), "Aluguel realizado.");
			this.persistenciaDadosBD(novoAluguel);
			
			return novoAluguel;
		}
		
		return null;
	}
	
	public Aluguel retornaUltimoAluguelCiclista(String ciclistaId) {
		if(Strings.isNotBlank(ciclistaId)) {
		    Calendar calendar = Calendar.getInstance();
		    calendar.add(Calendar.HOUR_OF_DAY, -3);
			String inicio = this.formatter.format(calendar.getTime());
			return new Aluguel("001122", "883344", inicio);
			
		}
		
		return null;
	}
	
	public boolean persistenciaDadosBD(Aluguel aluguel) {
		return Objects.nonNull(aluguel);
	}
	
	public Cobranca enviaCobranca(String idCiclista) {
		// Chamar endpoint /cobranca de microsserviço Externo
		if(idCiclista.startsWith("0")) {
			HttpEntity<Cobranca> request = new HttpEntity<>(new Cobranca(UUID.randomUUID().toString(), idCiclista, 10));
			ResponseEntity<Cobranca> response = restTemplate
			  .postForEntity(URL_EXTERNOS + "/cobranca/", request, Cobranca.class);
			return response.getBody();
		}
		return null;
	}
	
	public boolean enviaFilaCobranca(Cobranca cobranca) {
		// Chamar endpoint /filaCobranca de microsserviço Externo
		HttpEntity<Cobranca> request = new HttpEntity<>(cobranca);
		ResponseEntity<Cobranca> response = restTemplate.postForEntity(URL_EXTERNOS + "/filaCobranca/", request, Cobranca.class);
		return response.getStatusCodeValue() == 200;
	}
	
	public void liberaTranca(String trancaInicio) {
		// Chamar endpoint /tranca/trancaInicio/status/LIVRE de microsserviço Equipamento
		String urlDestrancar = "/tranca/" + trancaInicio + "/status/LIVRE";
		restTemplate.postForEntity(URL_EQUIPAMENTO + urlDestrancar, "", String.class);

	}
	
	public void fechaTranca(String trancaFim) {
		// Chamar endpoint /tranca/trancaFim/status/OCUPADA de microsserviço Equipamento
		String urlTrancar = "/tranca/" + trancaFim + "/status/OCUPADA";
		restTemplate.postForEntity(URL_EQUIPAMENTO + urlTrancar, null, String.class);
	}
	
	public NovoEmail enviaEmail(String emailCiclista, String mensagem) {
		// Chamar endpoint /enviarEmail de microsserviço Externo
		if(Strings.isNotBlank(emailCiclista) && Strings.isNotBlank(mensagem)) {
			HttpEntity<NovoEmail> request = new HttpEntity<>(new NovoEmail(emailCiclista, mensagem));
			ResponseEntity<NovoEmail> response = restTemplate
			  .postForEntity(URL_EXTERNOS + "/enviarEmail/", request, NovoEmail.class);
			return response.getBody();
		}

		return null;
	}

	public Aluguel realizarDevolucao(Devolucao devolucao) throws ParseException {
		this.fechaTranca(devolucao.getTrancaFim());
		Aluguel aluguelFinalizado = this.finalizaAluguel(devolucao);
		Ciclista ciclistaCorrespondente = this.ciclistaService.retornaCiclista(devolucao.getCiclista());
		Cobranca custoExtra = this.calculaCustosAdicionais(aluguelFinalizado.getHoraInicio(), aluguelFinalizado.getHoraFim(), devolucao.getCiclista());
		if(Objects.nonNull(custoExtra)) {
			this.enviaFilaCobranca(custoExtra);
			this.enviaEmail(ciclistaCorrespondente.getEmail(), "Cobrança extra realizada de " + custoExtra.getValor());	
		}
		this.enviaEmail(ciclistaCorrespondente.getEmail(), "Devolução realizada.");
		return aluguelFinalizado;
	}
	
	public Aluguel finalizaAluguel(Devolucao devolucao) {
		String horaAtual = this.formatter.format(new Date());
		Aluguel aluguelCorrespondente = this.retornaUltimoAluguelCiclista(devolucao.getCiclista());
		aluguelCorrespondente.setHoraFim(horaAtual);
		aluguelCorrespondente.setTrancaFim(devolucao.getTrancaFim());
		return aluguelCorrespondente;
	}
	public Cobranca calculaCustosAdicionais(String horaInicio, String horaFim, String ciclista) throws ParseException {
		Date inicio = this.formatter.parse(horaInicio);
		Date fim = this.formatter.parse(horaFim);
		
		long diff = fim.getTime() - inicio.getTime();
		long diffHours = diff / (60 * 60 * 1000);
		if(diffHours > 2) {
			int taxa = 0;
			long diffMin = diff / (60 * 1000) % 60;
			if(diffMin >= 30) {
				taxa += 10;
			}
			
			long diffMenosHoras = diffHours - 2;
			taxa += (diffMenosHoras * 2) * 10;
			return new Cobranca(ciclista, taxa);
		}
		
		return null;
	}

}
