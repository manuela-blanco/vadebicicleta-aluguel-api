package bsi.pm.aluguel.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import bsi.pm.aluguel.model.Funcionario;

@SpringBootTest
@AutoConfigureMockMvc
class FuncionarioControllerTest {

	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private MockMvc mockMvc;

	@Test
	void getFuncionarioTest() throws Exception {
		this.mockMvc.perform(get("/funcionario/20011")).andExpect(status().isOk())
				.andExpect(content().string(containsString("luispaulo.fernandes@email.com")));
	}
	
	@Test
	void getFuncionarioBlankTest() throws Exception {
		this.mockMvc.perform(get("/funcionario/ ")).andExpect(status().is(422))
				.andExpect(content().string(containsString("Dados invalidos.")));
	}
	
	@Test
	void deletaFuncionarioTest() throws Exception {
		this.mockMvc.perform(delete("/funcionario/20011")).andExpect(status().isOk())
				.andExpect(content().string(containsString("Dados removidos.")));
	}
	
	@Test
	void deletaFuncionarioBlankTest() throws Exception {
		this.mockMvc.perform(delete("/funcionario/ ")).andExpect(status().is(422))
				.andExpect(content().string(containsString("Dados invalidos.")));
	}
	
	@Test
	void deletaFuncionarioNaoEncontradoTest() throws Exception {
		this.mockMvc.perform(delete("/funcionario/10011")).andExpect(status().is(404))
				.andExpect(content().string(containsString("Nao encontrado.")));
	}
	
	@Test
	void getFuncionarioNaoEncontradoTest() throws Exception {
		this.mockMvc.perform(get("/funcionario/10011")).andExpect(status().is(404))
				.andExpect(content().string(containsString("Nao encontrado.")));
	}
	
	@Test
	void getFuncionariosTest() throws Exception {
		this.mockMvc.perform(get("/funcionario/")).andExpect(status().isOk())
				.andExpect(content().string(containsString("luispaulo.fernandes@email.com")))
				.andExpect(content().string(containsString("maria.domingues@email.com")))
				.andExpect(content().string(containsString("julia.figueiros@email.com")));
	}
	
	@Test
	void cadastrarFuncionarioTest() throws Exception {
	    String funcionarioJSON = objectMapper.writeValueAsString(new Funcionario());
		this.mockMvc.perform(post("/funcionario/").contentType(MediaType.APPLICATION_JSON).content(funcionarioJSON)).andExpect(status().is(201))
				.andExpect(content().string(containsString("matricula")));
	}
	
	@Test
	void atualizarFuncionarioTest() throws Exception {
	    String funcionarioJSON = objectMapper.writeValueAsString(new Funcionario());
		this.mockMvc.perform(put("/funcionario/221122").contentType(MediaType.APPLICATION_JSON).content(funcionarioJSON)).andExpect(status().is(200))
				.andExpect(content().string(containsString("{}")));
	}
	
	@Test
	void atualizarFuncionarioBlankTest() throws Exception {
	    String funcionarioJSON = objectMapper.writeValueAsString(new Funcionario());
		this.mockMvc.perform(put("/funcionario/ ").contentType(MediaType.APPLICATION_JSON).content(funcionarioJSON)).andExpect(status().is(422))
				.andExpect(content().string(containsString("Dados invalidos.")));
	}

}
