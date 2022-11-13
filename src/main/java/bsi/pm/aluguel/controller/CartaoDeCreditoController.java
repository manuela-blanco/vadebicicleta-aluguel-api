package bsi.pm.aluguel.controller;

import java.util.Objects;
import java.util.UUID;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bsi.pm.aluguel.erro.Erro;
import bsi.pm.aluguel.model.CartaoDeCredito;
import bsi.pm.aluguel.model.Ciclista;
import bsi.pm.aluguel.service.CartaoDeCreditoService;
import bsi.pm.aluguel.service.CiclistaService;
import bsi.pm.aluguel.utils.MensagemErro;

@RestController
@RequestMapping("/cartaoDeCredito")
public class CartaoDeCreditoController {
	
	@Autowired
	private CiclistaService ciclistaService;
	
	@Autowired
	private CartaoDeCreditoService cartaoService;

	@GetMapping("/{idCiclista}")
	public ResponseEntity<Object> getCartaoCiclista(@PathVariable(required = true) String idCiclista) {
		if(Strings.isBlank(idCiclista))
			return ResponseEntity.status(422).body(new Erro(UUID.randomUUID().toString(), "422", MensagemErro.DADOS_INVALIDOS.getMensagem()));
		Ciclista ciclista = this.ciclistaService.retornaCiclista(idCiclista);
		if(Objects.nonNull(ciclista)) {
			CartaoDeCredito cartao = this.cartaoService.retornaCartao(ciclista);
			return ResponseEntity.status(HttpStatus.OK).body(cartao); 
		}
		
		return ResponseEntity.status(404).body(new Erro(UUID.randomUUID().toString(), "404", MensagemErro.NOT_FOUND.getMensagem()));
	}
	
	@PutMapping("/{idCiclista}")
	public ResponseEntity<Object> atualizarCartaoCiclista(@PathVariable(required = true) String idCiclista, @RequestBody CartaoDeCredito cartao) {
		if(Strings.isBlank(idCiclista))
			return ResponseEntity.status(422).body(new Erro(UUID.randomUUID().toString(), "422", MensagemErro.DADOS_INVALIDOS.getMensagem()));
		
		Ciclista ciclista = this.ciclistaService.retornaCiclista(idCiclista);
		if(Objects.nonNull(ciclista)) {
			this.cartaoService.atualizaCartao(ciclista, cartao);
			return ResponseEntity.status(HttpStatus.OK).body("Dados atualizados."); 
		}
		return ResponseEntity.status(404).body(new Erro(UUID.randomUUID().toString(), "404", MensagemErro.NOT_FOUND.getMensagem()));
	}
	
}
