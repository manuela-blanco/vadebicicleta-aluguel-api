package bsi.pm.aluguel.utils;

public enum MensagemErro {

	NOT_FOUND("Nao encontrado."), DADOS_INVALIDOS("Dados invalidos.");
	
    private final String mensagem;

    private MensagemErro(String s) {
        mensagem = s;
    }
    
    public String getMensagem() {
    	return this.mensagem;
    }
    
}
