package bsi.pm.aluguel.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import bsi.pm.aluguel.model.CartaoDeCredito;
import bsi.pm.aluguel.model.Ciclista;

@SpringBootTest
@AutoConfigureMockMvc
class CiclistaControllerTest {

	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Mock
	private Ciclista ciclista;
	
	@Mock
	private CartaoDeCredito cartao;

	@Test
	void cadastrarCiciclistaTest() throws Exception {
		Map<String, Object> cadastro = new HashMap<>();
		Ciclista ciclista = new Ciclista();
		ciclista.setEmail("mfblancorodriguez@gmail.com");
		cadastro.put("ciclista", ciclista);
		cadastro.put("meioDePagamento", new CartaoDeCredito(ciclista.getNome(),
                "4024007153763191", "02/2024", "646"));
	    String cadastroJSON = objectMapper.writeValueAsString(cadastro);
		this.mockMvc.perform(post("/ciclista/").contentType(MediaType.APPLICATION_JSON).content(cadastroJSON)).andExpect(status().is(201))
				.andExpect(content().string(containsString("AGUARDANDO_CONFIRMACAO")));
	}
	
	@Test
	void atualizarCiciclistaTest() throws Exception {
	    String ciclistaJSON = objectMapper.writeValueAsString(new Ciclista());
		this.mockMvc.perform(put("/ciclista/001122").contentType(MediaType.APPLICATION_JSON).content(ciclistaJSON)).andExpect(status().is(200))
				.andExpect(content().string(containsString("{}")));
	}
	
	@Test
	void atualizarCiciclistaBlankTest() throws Exception {
	    String ciclistaJSON = objectMapper.writeValueAsString(new Ciclista());
		this.mockMvc.perform(put("/ciclista/ ").contentType(MediaType.APPLICATION_JSON).content(ciclistaJSON)).andExpect(status().is(405))
				.andExpect(content().string(containsString("Dados invalidos.")));
	}
	
	@Test
	void getCiclistaTest() throws Exception {
		this.mockMvc.perform(get("/ciclista/00011")).andExpect(status().isOk())
				.andExpect(content().string(containsString("jose.silva@email.com")));
	}
	
	@Test
	void getCiclistaDadosInvalidosTest() throws Exception {
		this.mockMvc.perform(get("/ciclista/ ")).andExpect(status().is(422))
				.andExpect(content().string(containsString("Dados invalidos.")));
	}
	
	@Test
	void getCiclistaNaoEncontradoTest() throws Exception {
		this.mockMvc.perform(get("/ciclista/20011")).andExpect(status().is(404))
				.andExpect(content().string(containsString("Nao encontrado.")));
	}
	
	@Test
	void verificaEmailTest() throws Exception {
		this.mockMvc.perform(get("/ciclista/existeEmail/jose.silva@email.com")).andExpect(status().isOk())
				.andExpect(content().string(containsString("true")));
	}
	
	@Test
	void verificaEmailDadosInvalidosTest() throws Exception {
		this.mockMvc.perform(get("/ciclista/existeEmail/ ")).andExpect(status().is(400))
				.andExpect(content().string(containsString("Dados invalidos.")));
	}
	
	@Test
	void verificaEmailInvalidoTest() throws Exception {
		this.mockMvc.perform(get("/ciclista/existeEmail/@email.com")).andExpect(status().is(422))
				.andExpect(content().string(containsString("Dados invalidos.")));
	}
	
	@Test
	void ativarCiclistaTest() throws Exception {
		this.mockMvc.perform(post("/ciclista/001122/ativar").header("x-id-requisicao" , "00112233")).andExpect(status().is(201))
				.andExpect(content().string(containsString("jose.silva@email.com")));
	}
	
	@Test
	void ativarCiclistaDadosInvalidosTest() throws Exception {
		this.mockMvc.perform(post("/ciclista/ /ativar").header("x-id-requisicao" , "00112233")).andExpect(status().is(405))
				.andExpect(content().string(containsString("Dados invalidos.")));
	}
	
	@Test
	void ativarCiclistaNaoEncontradoTest() throws Exception {
		this.mockMvc.perform(post("/ciclista/221100/ativar").header("x-id-requisicao" , "00112233")).andExpect(status().is(404))
				.andExpect(content().string(containsString("Nao encontrado.")));
	}
}
