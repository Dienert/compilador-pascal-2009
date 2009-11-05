
package compilador;

import compilador.lexico.Code;
import compilador.lexico.Token;

@SuppressWarnings("serial")
public class ErroCompilacao extends Exception {
    
    int tipo, linha, espected;
    Token antes, atual;
    String message, id;
    
    public static final int REGRA_ZERO = 0, TOKEN_INVALIDO = 1, NAO_ESPERADO = 2,
            NAO_DECLARADO = 3, MENSAGEM = 4;

    public ErroCompilacao(int linha, Token atual) {                
        this.tipo = TOKEN_INVALIDO;
        this.linha = linha;
        this.atual = atual;
    }
    
    public ErroCompilacao(int linha, Token antes, Token atual) {                
        this.tipo = REGRA_ZERO;
        this.linha = linha;
        this.atual = atual;
        this.antes = antes;
    }

    public ErroCompilacao(int linha, Token antes, Token atual, int espected) {
        this.tipo = NAO_ESPERADO;
        this.linha = linha;
        this.antes = antes;
        this.atual = atual;
        this.espected = espected;
    }
    
    public ErroCompilacao(int linha, String id) {
        this.tipo = NAO_DECLARADO;
        this.linha = linha;  
        this.id = id;
    }
    
    public ErroCompilacao(String mensagem, int linha) {
        this.tipo = MENSAGEM;
        this.linha = linha;          
        this.message = mensagem;
    }
    
    public ErroCompilacao(int linha, Object obj, Object type) {
        this.tipo = MENSAGEM;
        this.linha = linha;          
        message = "Esperando expressão do tipo %s, mas encontrado do tipo %s";
        String tipo1, tipo2;
        if(Integer.class.isInstance(obj))
            tipo1 = "integer";
        else if(Double.class.isInstance(obj))
            tipo1 = "real";
        else if(Boolean.class.isInstance(obj))
            tipo1 = "boolean";
        else if(String.class.isInstance(obj))
            tipo1 = "string";
        else
            tipo1 = "procedure";
            
        
        if(Integer.class.isInstance(type))
            tipo2 = "integer";
        else if(Double.class.isInstance(type))
            tipo2 = "real";
        else if(Boolean.class.isInstance(type))
            tipo2 = "boolean";
        else if(String.class.isInstance(obj))
            tipo2 = "string";
        else
            tipo2 = "procedure";
            
        message = String.format(message, tipo1, tipo2);
    }

    
    
    public void printStackTrace() {
        switch(tipo){
            case REGRA_ZERO:
                message = "Erro na linha: %d\n\"%s\" não esperado após \"%s\"";
                message = String.format(message, linha, atual.getSimbolo(), antes.getSimbolo());
                System.err.println(message);            
                break;
            case TOKEN_INVALIDO:
                message = "Erro na linha: %d\n\"%s\" não é valido na gramática";
                message = String.format(message, linha, atual.getSimbolo());
                System.err.println(message);   
                break;
            case NAO_ESPERADO:
                message = "Erro na linha: %d\nEsperando \"%s\" após \"%s\", mas encontrado \"%s\"";
                message = String.format(message, linha, Code.getDescrition(espected), atual.getSimbolo(), antes.getSimbolo());
                System.err.println(message);
                break;
            case NAO_DECLARADO:
                message = "Erro na linha: %d\nIdentificador \"%s\" não declarado";
                message = String.format(message, linha, id);
                System.err.println(message);
                break;
            case MENSAGEM:
                message = "Erro na linha: %d\n"+message;
                message = String.format(message, linha);
                System.err.println(message);
                break;
        }
        
    }
    
    
    
}
