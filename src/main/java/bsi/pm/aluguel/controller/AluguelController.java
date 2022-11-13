package bsi.pm.aluguel.controller;

import java.text.ParseException;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bsi.pm.aluguel.erro.Erro;
import bsi.pm.aluguel.model.Aluguel;
import bsi.pm.aluguel.model.Devolucao;
import bsi.pm.aluguel.service.AluguelService;
import bsi.pm.aluguel.utils.MensagemErro;

@RestController
@RequestMapping("/")
public class AluguelController {
	
	@Autowired
	private AluguelService aluguelService;
	
	
	@PostMapping("/aluguel")
	public ResponseEntity<Object> realizarAluguel(@RequestBody Aluguel novoAluguel) {
		Aluguel aluguelRealizado = this.aluguelService.realizarAluguel(novoAluguel);
		if(Objects.isNull(aluguelRealizado)) {
			return ResponseEntity.status(422).body(new Erro(UUID.randomUUID().toString(), "422", MensagemErro.DADOS_INVALIDOS.getMensagem()));
		}
		return ResponseEntity.status(201).body(aluguelRealizado);
	}
	
	@PostMapping("/devolucao")
	public ResponseEntity<Object> realizarDevolucao(@RequestBody Devolucao devolucao) throws ParseException {
		if(Objects.isNull(devolucao))
			return ResponseEntity.status(422).body(new Erro(UUID.randomUUID().toString(), "422", MensagemErro.DADOS_INVALIDOS.getMensagem()));
		Aluguel devolucaoRealizada = this.aluguelService.realizarDevolucao(devolucao);
		return ResponseEntity.status(200).body(devolucaoRealizada);
	}
	
}
