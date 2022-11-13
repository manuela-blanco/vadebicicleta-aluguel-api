package bsi.pm.aluguel.service;

import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import bsi.pm.aluguel.model.CartaoDeCredito;
import bsi.pm.aluguel.model.Ciclista;

@Service
public class CartaoDeCreditoService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	private static final String URL_EXTERNOS = "https://externos.herokuapp.com";
	
	public boolean persistenciaDadosBD(CartaoDeCredito cartaoDeCredito) {
		return Objects.nonNull(cartaoDeCredito);
	}
	
	public CartaoDeCredito retornaCartao(Ciclista ciclista) {
		if(Objects.nonNull(ciclista)) {
			return new CartaoDeCredito(UUID.randomUUID().toString(), ciclista.getNome(),
					"4024007153763191", "02/2024", "646");
		}
		
		return null;
	}
	
	public boolean validaCartao(CartaoDeCredito cartao) {
		HttpEntity<CartaoDeCredito> request = new HttpEntity<>(cartao);
		ResponseEntity<String> response = restTemplate
		  .postForEntity(URL_EXTERNOS + "/validaCartaoDeCredito/", request, String.class);
		
		return response.getStatusCodeValue() == 200;
	}
	
	public boolean atualizaCartao(Ciclista ciclista, CartaoDeCredito cartao) {
		CartaoDeCredito cartaoEncontrado = this.retornaCartao(ciclista);
		if(Objects.nonNull(cartaoEncontrado)) {
			this.persistenciaDadosBD(cartao);
			return true;
		}
		
		return false;
	}
}
