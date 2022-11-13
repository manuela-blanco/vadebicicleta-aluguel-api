package bsi.pm.aluguel;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootApplication
public class AluguelApplication {

	public static void main(String[] args) {
		SpringApplication.run(AluguelApplication.class, args); 
	}

    @Bean
    public ObjectMapper buildObjectMapper() {
        return new ObjectMapper();
    }
    
    @Bean
    public SecureRandom buildRandom() {
        return new SecureRandom();
    }
    
    @Bean
    public SimpleDateFormat buildDateFormat() {
    	return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Bean
    public RestTemplate buildRestTemplate() {
        return new RestTemplate();
    }
}
