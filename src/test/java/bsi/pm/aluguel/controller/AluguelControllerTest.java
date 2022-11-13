package bsi.pm.aluguel.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import bsi.pm.aluguel.model.Aluguel;
import bsi.pm.aluguel.model.Devolucao;
import bsi.pm.aluguel.service.AluguelService;

@SpringBootTest
@AutoConfigureMockMvc
class AluguelControllerTest {
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Mock
	private AluguelService aluguelServiceMock;

	@Test
	void realizarAluguelTest() throws Exception {
		Aluguel aluguel = new Aluguel("001122", "886622");
	    String aluguelJSON = objectMapper.writeValueAsString(aluguel);
		this.mockMvc.perform(post("/aluguel/").contentType(MediaType.APPLICATION_JSON).content(aluguelJSON)).andExpect(status().is(201))
				.andExpect(content().string(containsString("001122")));
	}
	
	@Test
	void realizarAluguelDadosInvalidosTest() throws Exception {
		Aluguel aluguel = new Aluguel("201122", "886622");
	    String aluguelJSON = objectMapper.writeValueAsString(aluguel);
		this.mockMvc.perform(post("/aluguel/").contentType(MediaType.APPLICATION_JSON).content(aluguelJSON)).andExpect(status().is(422))
				.andExpect(content().string(containsString("Dados invalidos.")));
	}
	
	@Test
	void realizarDevolucaoTest() throws Exception {
		Devolucao devolucao = new Devolucao("001122", "886622");
	    String devolucaoJSON = objectMapper.writeValueAsString(devolucao);
		this.mockMvc.perform(post("/devolucao/").contentType(MediaType.APPLICATION_JSON).content(devolucaoJSON)).andExpect(status().is(200))
				.andExpect(content().string(containsString("001122")));
	}
	
}
