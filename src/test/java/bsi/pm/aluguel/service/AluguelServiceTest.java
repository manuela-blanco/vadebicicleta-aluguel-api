package bsi.pm.aluguel.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
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

import bsi.pm.aluguel.model.Aluguel;
import bsi.pm.aluguel.model.Cobranca;
import bsi.pm.aluguel.model.Devolucao;
import bsi.pm.aluguel.model.NovoEmail;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class AluguelServiceTest {

	@Mock
	private RestTemplate restTemplate;
	
	@Mock
	private static Cobranca cobranca;
	
	@Mock
	private static NovoEmail novoEmail;
	
	@Spy
	private Aluguel aluguel;
	
	@Spy
	private Devolucao devolucao;
	
	@Spy
	private CiclistaService ciclistaService;
	
    @InjectMocks
    private AluguelService aluguelService;
	
	@Spy
	private SimpleDateFormat formatter;
	
    @BeforeEach
    public void setup() {
    	
		Mockito.when(restTemplate.postForEntity(
				Mockito.eq("https://externos.herokuapp.com/cobranca/"), ArgumentMatchers.any(HttpEntity.class), Mockito.eq(Cobranca.class)))
		.thenReturn(new ResponseEntity<Cobranca>(cobranca, HttpStatus.OK));
		
		Mockito.when(restTemplate.postForEntity(
				Mockito.eq("https://externos.herokuapp.com/filaCobranca/"), ArgumentMatchers.any(HttpEntity.class), Mockito.eq(Cobranca.class)))
		.thenReturn(new ResponseEntity<Cobranca>(cobranca, HttpStatus.OK));
		
		Mockito.when(restTemplate.postForEntity(
		          Mockito.eq("https://externos.herokuapp.com/enviarEmail/"), ArgumentMatchers.any(HttpEntity.class), Mockito.eq(NovoEmail.class)))
		        .thenReturn(new ResponseEntity<NovoEmail>(novoEmail, HttpStatus.OK));
		
		Mockito.when(restTemplate.postForEntity(
		          "https://equipamento.herokuapp.com/tranca/883322/status/LIVRE", "", String.class))
		        .thenReturn(new ResponseEntity<String>("{}", HttpStatus.OK));

		Mockito.when(restTemplate.postForEntity(
		          "https://equipamento.herokuapp.com/tranca/883322/status/OCUPADA", "", String.class))
		        .thenReturn(new ResponseEntity<String>("{}", HttpStatus.OK));
		
    }
	
	@Test
	void realizarAluguelNullTest() throws URISyntaxException, IOException {
		aluguel.setCiclista("201122");
		aluguel.setTrancaInicio("882233");
		assertEquals(null, this.aluguelService.realizarAluguel(aluguel));
	}
	
	@Test
	void realizarAluguelTest() throws URISyntaxException, IOException {
		aluguel.setCiclista("001122");
		aluguel.setTrancaInicio("882233");
		novoEmail.setEmail("jose.silva@email.com");
		novoEmail.setMensagem("Aluguel realizado.");
       
		assertEquals(aluguel, this.aluguelService.realizarAluguel(aluguel));
	}
	
	@Test
	void retornaUltimoAluguelCiclistaNullTest() {
		assertEquals(null, this.aluguelService.retornaUltimoAluguelCiclista(null));
	}
	
	@Test
	void retornaUltimoAluguelCiclistaTest() {
		Aluguel retornado = this.aluguelService.retornaUltimoAluguelCiclista("001122");
		assertEquals("001122", retornado.getCiclista());
	}
	
	@Test
	void persistenciaDadosBDTrueTest() {
		assertEquals(true, this.aluguelService.persistenciaDadosBD(aluguel));
	}
	
	@Test
	void persistenciaDadosBDFalseTest() {
		assertEquals(false, this.aluguelService.persistenciaDadosBD(null));
	}
	
	@Test
	void enviaCobrancaTest() {
		assertEquals(cobranca, this.aluguelService.enviaCobranca("0022"));
	}
	
	@Test
	void enviaCobrancaNullTest() {
		assertEquals(null, this.aluguelService.enviaCobranca("220011"));
	}
	
	@Test
	void enviaFilaCobrancaTrueTest() {
		assertEquals(true, this.aluguelService.enviaFilaCobranca(cobranca));
	}
	
	@Test
	void enviaFilaCobrancaFalseTest() {
		Mockito.when(restTemplate.postForEntity(
				Mockito.eq("https://externos.herokuapp.com/filaCobranca/"), ArgumentMatchers.any(HttpEntity.class), Mockito.eq(Cobranca.class)))
		.thenReturn(new ResponseEntity<Cobranca>(cobranca, HttpStatus.BAD_REQUEST));

		assertEquals(false, this.aluguelService.enviaFilaCobranca(null));
	}
	
	@Test
	void enviaEmailTest() {
		assertEquals(novoEmail, this.aluguelService.enviaEmail("jose.silva@email.com", "Aluguel realizado."));
	}
	
	@Test
	void enviaEmailNullTest() {
		assertEquals(null, this.aluguelService.enviaEmail(null, null));
	}
	
	@Test
	void enviaEmailBlankTest() {
		assertEquals(null, this.aluguelService.enviaEmail(" ", "Mensagem teste"));
	}
	
	@Test
	void enviaEmailBlankMensagemTest() {
		assertEquals(null, this.aluguelService.enviaEmail("jose.silva@email.com", " "));
	}
	
	@Test
	void realizarDevolucaoTest() throws ParseException, URISyntaxException, IOException {
		devolucao.setCiclista("001122");
		devolucao.setTrancaFim("883344");
		aluguel.setCiclista("001122");
		aluguel.setTrancaInicio("882233");		
		assertEquals("001122", this.aluguelService.realizarDevolucao(devolucao).getCiclista());
	}
	
	@Test
	void finalizaAluguelTest() throws ParseException {
		devolucao.setCiclista("001122");
		devolucao.setTrancaFim("883344");
		Aluguel aluguelRetornado = this.aluguelService.finalizaAluguel(devolucao);
		assertEquals("883344", aluguelRetornado.getTrancaFim());
	}
	
	@Test
	void calculaCustosAdicionaisTest() throws ParseException {
	    Calendar calendar = Calendar.getInstance();
	    calendar.add(Calendar.HOUR_OF_DAY, -3);
	    Cobranca cobrancaRetornada = this.aluguelService.calculaCustosAdicionais(this.formatter.format(calendar.getTime()), this.formatter.format(new Date()), "001122");
		assertEquals(20, cobrancaRetornada.getValor());
	}
	
	@Test
	void calculaCustosAdicionaisNullTest() throws ParseException {
	    Calendar calendar = Calendar.getInstance();
	    calendar.add(Calendar.HOUR_OF_DAY, -2);
	    Cobranca cobrancaRetornada = this.aluguelService.calculaCustosAdicionais(this.formatter.format(calendar.getTime()), this.formatter.format(new Date()), "001122");
		assertEquals(null, cobrancaRetornada);
	}
	
	@Test
	void calculaCustosAdicionaisTaxaExtraTest() throws ParseException {
	    Calendar calendar = Calendar.getInstance();
	    calendar.add(Calendar.HOUR_OF_DAY, -3);
	    calendar.add(Calendar.MINUTE, -30);
	    Cobranca cobrancaRetornada = this.aluguelService.calculaCustosAdicionais(this.formatter.format(calendar.getTime()), this.formatter.format(new Date()), "001122");
		assertEquals(30, cobrancaRetornada.getValor());
	}
}
