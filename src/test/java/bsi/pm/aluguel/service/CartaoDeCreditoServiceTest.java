package bsi.pm.aluguel.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import bsi.pm.aluguel.model.CartaoDeCredito;
import bsi.pm.aluguel.model.Ciclista;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class CartaoDeCreditoServiceTest {

	@InjectMocks
	private CartaoDeCreditoService cartaoService;
	
	@Mock
	private CartaoDeCredito cartaoMock;
	
	@Mock
	private Ciclista ciclistaMock;
	
	@Mock
	private RestTemplate restTemplate;
		
	@Test
	void validaCartaoTest() {
		Mockito.when(restTemplate.postForEntity(
				Mockito.eq("https://externos.herokuapp.com/validaCartaoDeCredito/"), ArgumentMatchers.any(HttpEntity.class), Mockito.eq(String.class)))
		.thenReturn(new ResponseEntity<String>("Dados válidos.", HttpStatus.OK));
		
		assertEquals(true, this.cartaoService.validaCartao(cartaoMock));
	}
	
	@Test
	void validaCartaoFalseTest() {
		Mockito.when(restTemplate.postForEntity(
				Mockito.eq("https://externos.herokuapp.com/validaCartaoDeCredito/"), ArgumentMatchers.any(HttpEntity.class), Mockito.eq(String.class)))
		.thenReturn(new ResponseEntity<String>("Dados inválidos.", HttpStatus.UNPROCESSABLE_ENTITY));

		assertEquals(false, this.cartaoService.validaCartao(cartaoMock));
	}
    
	@Test
	void persistenciaDadosBDTrueTest() {
		assertEquals(true, this.cartaoService.persistenciaDadosBD(cartaoMock));
	}
	
	@Test
	void persistenciaDadosBDFalseTest() {
		assertEquals(false, this.cartaoService.persistenciaDadosBD(null));
	}
	
	@Test
	void retornaCartaoNullTest() {
		assertEquals(null, this.cartaoService.retornaCartao(null));
	}
	
	@Test
	void retornaCartaoTest() {
		CartaoDeCredito cartaoRetornado = this.cartaoService.retornaCartao(ciclistaMock);
		assertEquals("4024007153763191", cartaoRetornado.getNumero());
	}
	
	@Test
	void atualizaCartaoFalseTest() {
		assertEquals(false, this.cartaoService.atualizaCartao(null, null));
	}
	
	@Test
	void atualizaCartaoCiclistaNullFalseTest() {
		assertEquals(false, this.cartaoService.atualizaCartao(null, cartaoMock));
	}
	
	@Test
	void atualizaCartaoTest() {
		assertEquals(true, this.cartaoService.atualizaCartao(ciclistaMock, cartaoMock));
	}
}
