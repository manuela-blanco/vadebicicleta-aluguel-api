package bsi.pm.aluguel.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

import bsi.pm.aluguel.model.CartaoDeCredito;
import bsi.pm.aluguel.model.Funcionario;

@SpringBootTest
@AutoConfigureMockMvc
class CartaoDeCreditoControllerTest {
	
	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MockMvc mockMvc;

	@Test
	void getCartaoCiclistaTest() throws Exception {
		this.mockMvc.perform(get("/cartaoDeCredito/00011")).andExpect(status().isOk())
				.andExpect(content().string(containsString("4024007153763191")))
				.andExpect(content().string(containsString("Jose Silva")));
	}
	
	@Test
	void getCartaoCiclistaNaoEncontradoTest() throws Exception {
		this.mockMvc.perform(get("/cartaoDeCredito/20011")).andExpect(status().is(404))
				.andExpect(content().string(containsString("Nao encontrado.")));
	}
	
	@Test
	void getCartaoCiclistaDadoInvalidoTest() throws Exception {
		this.mockMvc.perform(get("/cartaoDeCredito/ ")).andExpect(status().is(422))
				.andExpect(content().string(containsString("Dados invalidos.")));
	}
	
	@Test
	void atualizarCartaoCiclistaDadoInvalidoTest() throws Exception {
	    String cartaoJSON = objectMapper.writeValueAsString(new Funcionario());
		this.mockMvc.perform(put("/cartaoDeCredito/ ").contentType(MediaType.APPLICATION_JSON).content(cartaoJSON)).andExpect(status().is(422))
				.andExpect(content().string(containsString("Dados invalidos.")));
	}
	
	@Test
	void atualizarCartaoCiclistaTest() throws Exception {
	    String cartaoJSON = objectMapper.writeValueAsString(new CartaoDeCredito());
		this.mockMvc.perform(put("/cartaoDeCredito/001122").contentType(MediaType.APPLICATION_JSON).content(cartaoJSON)).andExpect(status().is(200))
				.andExpect(content().string(containsString("Dados atualizados.")));
	}
	
	@Test
	void atualizarCartaoCiclistaNaoEncontradoTest() throws Exception {
	    String cartaoJSON = objectMapper.writeValueAsString(new CartaoDeCredito());
		this.mockMvc.perform(put("/cartaoDeCredito/221122").contentType(MediaType.APPLICATION_JSON).content(cartaoJSON)).andExpect(status().is(404))
				.andExpect(content().string(containsString("Nao encontrado.")));
	}
}
