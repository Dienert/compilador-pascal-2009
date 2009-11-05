package compilador.lexico;

public class Token {

    private int codigo;
    private String simbolo;

    public Token(int codigo, String simbolo) {
        this.codigo = codigo;
        this.simbolo = simbolo;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }
    
}
