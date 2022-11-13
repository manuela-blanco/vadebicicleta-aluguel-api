package bsi.pm.aluguel.service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bsi.pm.aluguel.model.Funcionario;
import bsi.pm.aluguel.utils.Funcao;

@Service
public class FuncionarioService {

	@Autowired
	private SecureRandom random;
	
	public Funcionario cadastrarFuncionario(Funcionario novoFuncionario) {
		if(Objects.nonNull(novoFuncionario)) {			
			novoFuncionario.setMatricula(Integer.toString(random.nextInt(Integer.MAX_VALUE - 1) + 1));
			novoFuncionario.setId(UUID.randomUUID().toString());
			this.persistenciaDadosBD(novoFuncionario);
			
			return novoFuncionario;
		}
		return null;
	}
	
	public boolean persistenciaDadosBD(Funcionario funcionario) {
		return Objects.nonNull(funcionario);
	}
	
	public boolean deletaDadosBD(Funcionario funcionario) {
		return Objects.nonNull(funcionario);
	}

	public List<Funcionario> retornaFuncionarios() {
		List<Funcionario> funcionarios = new ArrayList<>();
		funcionarios.add(new Funcionario(UUID.randomUUID().toString(), "123456", "julia.figueiros@email.com", "74271082058",
										 Funcao.ADMINISTRATIVO, "Julia Figueiros", Integer.toString(random.nextInt(Integer.MAX_VALUE - 1) + 1)));
		funcionarios.add(new Funcionario(UUID.randomUUID().toString(), "789123", "maria.domingues@email.com", "68662938043",
				 Funcao.ADMINISTRATIVO, "Maria Domingues", Integer.toString(random.nextInt(Integer.MAX_VALUE - 1) + 1)));
		funcionarios.add(new Funcionario(UUID.randomUUID().toString(), "456789", "luispaulo.fernandes@email.com", "92369077093",
				 Funcao.REPARADOR, "Luis Paulo Fernandes", Integer.toString(random.nextInt(Integer.MAX_VALUE - 1) + 1)));
		return funcionarios;
	}

	public Funcionario retornaFuncionario(String idFuncionario) {
		if(idFuncionario.startsWith("2")) {
			return new Funcionario(UUID.randomUUID().toString(), "456789", "luispaulo.fernandes@email.com", "92369077093",
					 Funcao.REPARADOR, "Luis Paulo Fernandes", Integer.toString(random.nextInt(Integer.MAX_VALUE - 1) + 1));
		}
		return null;
	}

	public Funcionario atualizaFuncionario(String idFuncionario, Funcionario funcionario) {
		Funcionario funcionarioEncontrado = this.retornaFuncionario(idFuncionario);
		if(Objects.nonNull(funcionarioEncontrado)) {
			this.persistenciaDadosBD(funcionario);
			return funcionario;
		}
		return null;
	}

	public Funcionario deletaFuncionario(String idFuncionario) {
		Funcionario funcionarioEncontrado = this.retornaFuncionario(idFuncionario);
		if(Objects.nonNull(funcionarioEncontrado)) {
			this.deletaDadosBD(funcionarioEncontrado);
			return funcionarioEncontrado;
		}
		return null;
	}

}
