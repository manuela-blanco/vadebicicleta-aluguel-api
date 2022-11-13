package bsi.pm.aluguel.service;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import bsi.pm.aluguel.model.CartaoDeCredito;
import bsi.pm.aluguel.model.Ciclista;
import bsi.pm.aluguel.model.NovoEmail;
import bsi.pm.aluguel.utils.Nacionalidade;
import bsi.pm.aluguel.utils.StatusCiclista;

@Service
public class CiclistaService {

	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private CartaoDeCreditoService cartaoService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	private static final String URL_EXTERNOS = "https://externos.herokuapp.com";
	
	public Ciclista cadastrarCiclista(Map<String, Object> dadosCadastro) {
		if(Objects.nonNull(dadosCadastro)) {
			Ciclista ciclistaCadastrar = objectMapper.convertValue(dadosCadastro.get("ciclista"), Ciclista.class);
			CartaoDeCredito cartaoCadastrar = objectMapper.convertValue(dadosCadastro.get("meioDePagamento"), CartaoDeCredito.class);
			
			ciclistaCadastrar.setId(UUID.randomUUID().toString());
			ciclistaCadastrar.setStatus(StatusCiclista.AGUARDANDO_CONFIRMACAO);
			
			// integração com o módulo externos chamando o endpoint /validaCartaoDeCredito
			if(!this.cartaoService.validaCartao(cartaoCadastrar))
				return null;
			
			cartaoCadastrar.setId(UUID.randomUUID().toString());
			
			this.persistenciaDadosBD(ciclistaCadastrar);
			cartaoService.persistenciaDadosBD(cartaoCadastrar);
			
			//integração com o módulo externos chamando o endpoint /enviaEmail
			this.enviaEmail(ciclistaCadastrar.getEmail(), "Cadastro realizado!");
			
			return ciclistaCadastrar;

		}
		
		return null;
	}
	
	public boolean persistenciaDadosBD(Ciclista ciclista) {
			return Objects.nonNull(ciclista);
	}
	

	public Ciclista retornaCiclista(String idCiclista) {
		if(idCiclista.startsWith("0")) {
			return new Ciclista("jose.silva@email.com", Nacionalidade.BRASILEIRO, "1990-07-10", "Jose Silva",
								"123456", idCiclista);
		}
		
		return null;
	}
	
	public Ciclista retornaCiclistaEmail(String emailCiclista) {
		if(Strings.isNotBlank(emailCiclista)) {
			return new Ciclista("jose.silva@email.com", Nacionalidade.BRASILEIRO, "1990-07-10", "Jose Silva",
								"123456", UUID.randomUUID().toString());
		}
		
		return null;
	}

	public Ciclista atualizaCiclista(String idCiclista, Ciclista ciclista) {
		Ciclista ciclistaEncontrado = this.retornaCiclista(idCiclista);
		if(Objects.nonNull(ciclistaEncontrado)) {
			this.persistenciaDadosBD(ciclista);
			return ciclista;
		}
		return null;
	}

	public Ciclista ativarCiclista(String idCiclista) {
		Ciclista ciclistaEncontrado = this.retornaCiclista(idCiclista);
		if(Objects.nonNull(ciclistaEncontrado)) {
			ciclistaEncontrado.setStatus(StatusCiclista.ATIVO);
			this.persistenciaDadosBD(ciclistaEncontrado);
			return ciclistaEncontrado;
		}
		
		return null;
	}
	
	public boolean validaFormatoEmail(String email) {
		String regex = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
			
	}
	
	public void enviaEmail(String email, String mensagem) {
		HttpEntity<NovoEmail> requestEmail = new HttpEntity<>(new NovoEmail(email, mensagem));
		restTemplate.postForEntity(URL_EXTERNOS + "/enviarEmail/", requestEmail, NovoEmail.class);
	}

	public Ciclista verificaEmail(String email) {
		if(Objects.nonNull(email)) {
			Ciclista ciclistaEncontrado = this.retornaCiclistaEmail(email);
			if(Objects.nonNull(ciclistaEncontrado)) {
				return ciclistaEncontrado;
			}
			
			return null;
		}
		return null;
	}
}
