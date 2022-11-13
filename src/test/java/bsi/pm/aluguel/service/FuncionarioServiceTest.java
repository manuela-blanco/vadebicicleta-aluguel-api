package bsi.pm.aluguel.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import bsi.pm.aluguel.model.Funcionario;
import bsi.pm.aluguel.utils.Funcao;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class FuncionarioServiceTest {
	
	@Autowired
	private FuncionarioService funcionarioService;
	
	@Mock
	private Funcionario funcionario;

	@Test
	void cadastrarFuncionarioNullTest() {
		assertEquals(null, this.funcionarioService.cadastrarFuncionario(null));
	}
	
	@Test
	void cadastrarFuncionarioTest() {
		Funcionario funcionarioCadastrado = new Funcionario("123456", "luispaulo.fernandes@email.com", "99999999999",
				                                            Funcao.REPARADOR, 32, "Luis Paulo", "33221122");
		assertEquals(funcionarioCadastrado, this.funcionarioService.cadastrarFuncionario(funcionarioCadastrado));
	}
	
	@Test
	void persistenciaDadosBDTrueTest() {
		assertEquals(true, this.funcionarioService.persistenciaDadosBD(funcionario));
	}
	
	@Test
	void persistenciaDadosBDFalseTest() {
		assertEquals(false, this.funcionarioService.persistenciaDadosBD(null));
	}
	
	@Test
	void deletaDadosBDTrueTest() {
		assertEquals(true, this.funcionarioService.deletaDadosBD(funcionario));
	}
	
	@Test
	void deletaDadosBDFalseTest() {
		assertEquals(false, this.funcionarioService.deletaDadosBD(null));
	}
	
	@Test
	void retornaFuncionariosTest() {
		List<Funcionario> funcionariosRetornados = this.funcionarioService.retornaFuncionarios();
		assertEquals(3, funcionariosRetornados.size());
		assertEquals(Funcao.ADMINISTRATIVO, funcionariosRetornados.get(0).getFuncao());
		assertEquals(Funcao.ADMINISTRATIVO, funcionariosRetornados.get(1).getFuncao());
		assertEquals(Funcao.REPARADOR, funcionariosRetornados.get(2).getFuncao());
	}
	
	@Test
	void retornaFuncionarioNullTest() {
		assertEquals(null, this.funcionarioService.retornaFuncionario("000111"));
	}
	
	@Test
	void atualizaFuncionarioTest() {
		assertEquals(funcionario, this.funcionarioService.atualizaFuncionario("200111", funcionario));
	}
	
	@Test
	void atualizaFuncionarioNullTest() {
		assertEquals(null, this.funcionarioService.atualizaFuncionario("400111", funcionario));
	}
	
	@Test
	void deletaFuncionarioTest() {
		Funcionario funcionarioDeletado = this.funcionarioService.deletaFuncionario("200111");
		assertEquals("Luis Paulo Fernandes", funcionarioDeletado.getNome());
		assertEquals("luispaulo.fernandes@email.com", funcionarioDeletado.getEmail());
		assertEquals(Funcao.REPARADOR, funcionarioDeletado.getFuncao());
		assertEquals("92369077093", funcionarioDeletado.getCpf());
		assertEquals("456789", funcionarioDeletado.getSenha());
	}
	
	@Test
	void deletaFuncionarioNullTest() {
		assertEquals(null, this.funcionarioService.deletaFuncionario("400111"));
	}
}
