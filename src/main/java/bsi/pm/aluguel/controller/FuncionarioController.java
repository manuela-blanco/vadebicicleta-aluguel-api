package bsi.pm.aluguel.controller;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bsi.pm.aluguel.erro.Erro;
import bsi.pm.aluguel.model.Funcionario;
import bsi.pm.aluguel.service.FuncionarioService;
import bsi.pm.aluguel.utils.MensagemErro;

@RestController
@RequestMapping("/funcionario")
public class FuncionarioController {

	@Autowired
	private FuncionarioService funcionarioService;
	
	@PostMapping("/")
	public ResponseEntity<Object> cadastrarFuncionario(@RequestBody Funcionario novoFuncionario) {
		Funcionario funcionarioCadastrado = this.funcionarioService.cadastrarFuncionario(novoFuncionario);
		if(Objects.nonNull(funcionarioCadastrado))
			return ResponseEntity.status(201).body(funcionarioCadastrado);
		return ResponseEntity.status(422).body(new Erro(UUID.randomUUID().toString(), "422", MensagemErro.DADOS_INVALIDOS.getMensagem()));
	}
	
	@GetMapping("/")
	public ResponseEntity<Object> getFuncionario() {		
		List<Funcionario> funcionarios = this.funcionarioService.retornaFuncionarios();
		return ResponseEntity.status(HttpStatus.OK).body(funcionarios); 
	}
	
	@GetMapping("/{idFuncionario}")
	public ResponseEntity<Object> getFuncionario(@PathVariable(required = true) String idFuncionario) {
		if(Strings.isBlank(idFuncionario))
			return ResponseEntity.status(422).body(new Erro(UUID.randomUUID().toString(), "422", MensagemErro.DADOS_INVALIDOS.getMensagem()));
		
		Funcionario funcionario = this.funcionarioService.retornaFuncionario(idFuncionario);
		if(Objects.nonNull(funcionario)) {
			return ResponseEntity.status(HttpStatus.OK).body(funcionario); 
		}
		return ResponseEntity.status(404).body(new Erro(UUID.randomUUID().toString(), "404", MensagemErro.NOT_FOUND.getMensagem()));
	}
	
	@PutMapping("/{idFuncionario}")
	public ResponseEntity<Object> atualizarFuncionario(@PathVariable(required = true) String idFuncionario, @RequestBody Funcionario funcionario) {
		if(Strings.isBlank(idFuncionario))
			return ResponseEntity.status(422).body(new Erro(UUID.randomUUID().toString(), "422", MensagemErro.DADOS_INVALIDOS.getMensagem()));
		Funcionario funcionarioAtualizado = this.funcionarioService.atualizaFuncionario(idFuncionario, funcionario);
		if(Objects.nonNull(funcionarioAtualizado)) {
			return ResponseEntity.status(HttpStatus.OK).body(funcionarioAtualizado); 
		}
		return ResponseEntity.status(404).body(new Erro(UUID.randomUUID().toString(), "404", MensagemErro.NOT_FOUND.getMensagem()));
	}
	
	@DeleteMapping("/{idFuncionario}")
	public ResponseEntity<Object> deletaFuncionario(@PathVariable(required = true) String idFuncionario) {
		if(Strings.isBlank(idFuncionario))
			return ResponseEntity.status(422).body(new Erro(UUID.randomUUID().toString(), "422", MensagemErro.DADOS_INVALIDOS.getMensagem()));
		
		Funcionario funcionario = this.funcionarioService.deletaFuncionario(idFuncionario);
		if(Objects.nonNull(funcionario)) {
			return ResponseEntity.status(HttpStatus.OK).body("Dados removidos."); 
		}
		return ResponseEntity.status(404).body(new Erro(UUID.randomUUID().toString(), "404", MensagemErro.NOT_FOUND.getMensagem()));
	}
}
