package bsi.pm.aluguel.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import bsi.pm.aluguel.model.CartaoDeCredito;
import bsi.pm.aluguel.model.Ciclista;
import bsi.pm.aluguel.model.Cobranca;
import bsi.pm.aluguel.model.NovoEmail;
import bsi.pm.aluguel.utils.Nacionalidade;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class CiclistaServiceTest {

	@InjectMocks
	private CiclistaService ciclistaService;
	
	@Spy
	private ObjectMapper objectMapper;
	
	@Mock
	private RestTemplate restTemplate;
	
	@Mock
	private CartaoDeCredito cartaoDeCredito;
	
	@Mock
	private Cobranca cobranca;
	
	@Mock
	private NovoEmail novoEmail;
	
	@Mock
	private Ciclista ciclista;
	
	@Mock
	private CartaoDeCreditoService cartaoDeCreditoService;

	@Test
	void cadastrarCiclistaNullTest() {
		assertEquals(null, this.ciclistaService.cadastrarCiclista(null));
	}
	
	@Test
	void cadastrarCiclistaTest() {
		Ciclista ciclista = new Ciclista();
		ciclista.setEmail("jose.silva@email.com");
		CartaoDeCredito cartao = new CartaoDeCredito();
		
		Map<String, Object> cadastro = new HashMap<>();
		cadastro.put("ciclista", ciclista);
		cadastro.put("meioDePagamento", cartao);
		
		Mockito.when(this.cartaoDeCreditoService.validaCartao(ArgumentMatchers.any(CartaoDeCredito.class))).thenReturn(true);
		
		Mockito.when(restTemplate.postForEntity(
		          Mockito.eq("https://externos.herokuapp.com/enviarEmail/"), ArgumentMatchers.any(HttpEntity.class), Mockito.eq(NovoEmail.class)))
		        .thenReturn(new ResponseEntity<NovoEmail>(novoEmail, HttpStatus.OK));
				
		assertEquals(ciclista.getEmail(), this.ciclistaService.cadastrarCiclista(cadastro).getEmail());
	}
	
	@Test
	void cadastrarCiclistaCartaoInvalidoTest() {
		Ciclista ciclista = new Ciclista();
		ciclista.setEmail("jose.silva@email.com");
		CartaoDeCredito cartao = new CartaoDeCredito();
		
		Map<String, Object> cadastro = new HashMap<>();
		cadastro.put("ciclista", ciclista);
		cadastro.put("meioDePagamento", cartao);
		
		Mockito.when(this.cartaoDeCreditoService.validaCartao(ArgumentMatchers.any(CartaoDeCredito.class))).thenReturn(false);
		
		assertEquals(null, this.ciclistaService.cadastrarCiclista(cadastro));
	}
	
	@Test
	void persistenciaDadosBDTrueTest() {
		assertEquals(true, this.ciclistaService.persistenciaDadosBD(ciclista));
	}
	
	@Test
	void persistenciaDadosBDFalseTest() {
		assertEquals(false, this.ciclistaService.persistenciaDadosBD(null));
	}
	
	@Test
	void retornaCiclistaTest() {
		Ciclista ciclistaRetornado = this.ciclistaService.retornaCiclista("000111");
		assertEquals("Jose Silva", ciclistaRetornado.getNome());
	}
	
	@Test
	void retornaCiclistaNullTest() {
		assertEquals(null, this.ciclistaService.retornaCiclista("200111"));
	}
	
	@Test
	void retornaCiclistaEmailTest() {
		Ciclista ciclistaRetornado = this.ciclistaService.retornaCiclistaEmail("jose.silva@email.com");
		assertEquals("Jose Silva", ciclistaRetornado.getNome());
		assertEquals("jose.silva@email.com", ciclistaRetornado.getEmail());
		assertEquals("123456", ciclistaRetornado.getSenha());
		assertEquals(Nacionalidade.BRASILEIRO, ciclistaRetornado.getNacionalidade());
		assertEquals("1990-07-10", ciclistaRetornado.getNascimento());
		
	}
	
	@Test
	void retornaCiclistaEmailNullTest() {
		assertEquals(null, this.ciclistaService.retornaCiclistaEmail(null));
	}
	
	@Test
	void atualizaCiclistaTest() {
		assertEquals(ciclista, this.ciclistaService.atualizaCiclista("001122", ciclista));
	}
	
	@Test
	void atualizaCiclistaNullTest() {
		assertEquals(null, this.ciclistaService.atualizaCiclista("201122", ciclista));
	}
	
	@Test
	void ativarCiclistaTest() {
		Ciclista ciclistaAtivado = this.ciclistaService.ativarCiclista("001122");
		assertEquals("Jose Silva", ciclistaAtivado.getNome());
	}
	
	@Test
	void ativarCiclistaNullTest() {
		assertEquals(null, this.ciclistaService.ativarCiclista("201122"));
	}
	
	@Test
	void validaFormatoEmailFalseTest() {
		assertEquals(false, this.ciclistaService.validaFormatoEmail("@email.com"));
	}
	
	@Test
	void validaFormatoEmailTest() {
		assertEquals(true, this.ciclistaService.validaFormatoEmail("jose@email.com"));
	}
	
	@Test
	void verificaEmailNullTest() {
		assertEquals(null, this.ciclistaService.verificaEmail(null));
	}
	
	@Test
	void verificaEmailCiclistaNullTest() {
		assertEquals(null, this.ciclistaService.verificaEmail(" "));
	}
	
	@Test
	void verificaEmailTest() {
		Ciclista ciclista = this.ciclistaService.verificaEmail("jose.silva@email.com");
		assertEquals("Jose Silva", ciclista.getNome());
	}
}
