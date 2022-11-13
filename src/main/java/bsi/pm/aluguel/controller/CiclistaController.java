package bsi.pm.aluguel.controller;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bsi.pm.aluguel.erro.Erro;
import bsi.pm.aluguel.model.Ciclista;
import bsi.pm.aluguel.service.CiclistaService;
import bsi.pm.aluguel.utils.MensagemErro;

@RestController
@RequestMapping("/ciclista")
public class CiclistaController {

	@Autowired
	private CiclistaService ciclistaService;
	
	@PostMapping("/")
	public ResponseEntity<Object> cadastrarCiciclista(@RequestBody Map<String, Object> cadastro) {
		Ciclista ciclistaCadastrado = this.ciclistaService.cadastrarCiclista(cadastro);
		if(Objects.nonNull(ciclistaCadastrado))
			return ResponseEntity.status(201).body(ciclistaCadastrado);
		return ResponseEntity.status(405).body(new Erro(UUID.randomUUID().toString(), "405", MensagemErro.DADOS_INVALIDOS.getMensagem()));
	}
	
	@PostMapping("/{idCiclista}/ativar")
	public ResponseEntity<Object> ativarCiclista(@PathVariable(required = true) String idCiclista, 
												   @RequestHeader("x-id-requisicao") String idRequisicao) {
		if(Strings.isBlank(idCiclista))
			return ResponseEntity.status(405).body(new Erro(UUID.randomUUID().toString(), "405", MensagemErro.DADOS_INVALIDOS.getMensagem()));
		Ciclista ciclistaAtivado = this.ciclistaService.ativarCiclista(idCiclista);
		if(Objects.nonNull(ciclistaAtivado))
			return ResponseEntity.status(201).body(ciclistaAtivado);
		return ResponseEntity.status(404).body(new Erro(UUID.randomUUID().toString(), "404", MensagemErro.NOT_FOUND.getMensagem()));
	}
	
	@GetMapping("/{idCiclista}")
	public ResponseEntity<Object> getCiclista(@PathVariable(required = true) String idCiclista) {
		if(Strings.isBlank(idCiclista))
			return ResponseEntity.status(422).body(new Erro(UUID.randomUUID().toString(), "422", MensagemErro.DADOS_INVALIDOS.getMensagem()));
		
		Ciclista ciclista = this.ciclistaService.retornaCiclista(idCiclista);
		if(Objects.nonNull(ciclista)) {
			return ResponseEntity.status(HttpStatus.OK).body(ciclista); 
		}
		return ResponseEntity.status(404).body(new Erro(UUID.randomUUID().toString(), "404", MensagemErro.NOT_FOUND.getMensagem()));
	}
	
	@GetMapping("/existeEmail/{email}")
	public ResponseEntity<Object> verificaEmail(@PathVariable(required = true) String email) {
		if(Strings.isBlank(email))
			return ResponseEntity.status(400).body(new Erro(UUID.randomUUID().toString(), "400", MensagemErro.DADOS_INVALIDOS.getMensagem()));
		if(!this.ciclistaService.validaFormatoEmail(email))
			return ResponseEntity.status(422).body(new Erro(UUID.randomUUID().toString(), "422", MensagemErro.DADOS_INVALIDOS.getMensagem()));
		Ciclista ciclistaEmail = this.ciclistaService.verificaEmail(email);
		return ResponseEntity.status(HttpStatus.OK).body(Objects.nonNull(ciclistaEmail));
	}
	
	@PutMapping("/{idCiclista}")
	public ResponseEntity<Object> atualizarCiclista(@PathVariable(required = true) String idCiclista, @RequestBody Ciclista ciclista) {
		if(Strings.isBlank(idCiclista))
			return ResponseEntity.status(405).body(new Erro(UUID.randomUUID().toString(), "405", MensagemErro.DADOS_INVALIDOS.getMensagem()));
		Ciclista ciclistaAtualizado = this.ciclistaService.atualizaCiclista(idCiclista, ciclista);
		if(Objects.nonNull(ciclistaAtualizado)) {
			return ResponseEntity.status(HttpStatus.OK).body(ciclistaAtualizado); 
		}
		return ResponseEntity.status(404).body(new Erro(UUID.randomUUID().toString(), "404", MensagemErro.NOT_FOUND.getMensagem()));
	}
}
