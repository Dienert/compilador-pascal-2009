package compilador.lexico;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class AnalisadorLexico {

    private boolean ungeted, doubleUngeted;
    private int next, line;
    private FileInputStream in;
    
    public AnalisadorLexico(String fileName) throws FileNotFoundException {        
            in = new FileInputStream(fileName);     
            line = 1;
    }
    
    @SuppressWarnings("serial")
	private static class EOF extends IOException{};
    
    /**
     * Verifica se e' uma palavra reservada ou apenas um identificador
     * @param simbol
     * @return Codigo da palavra reservada ou de um identificador 
     */
    private static int codeOf(StringBuilder simbol) {

        String token = simbol.toString();
        switch (token.charAt(0)) {
            case 'a':
                if (token.equals("and")) return Code.OP_AND;
                return Code.ID;                
            case 'b':
                if (token.equals("begin")) return Code.PR_BEGIN;
                if (token.equals("boolean")) return Code.PR_BOOLEAN;
                return Code.ID;
            case 'c':
                if (token.equals("case")) return Code.PR_CASE;
                return Code.ID;                
            case 'd':
                if (token.equals("do")) return Code.PR_DO;
                if (token.equals("downto")) return Code.PR_DOWNTO;
                return Code.ID;                
            case 'e':
                if (token.equals("end")) return Code.PR_END;
                if (token.equals("else")) return Code.PR_ELSE;
                return Code.ID;
            case 'f':
                if (token.equals("false")) return Code.PR_FALSE;
                if (token.equals("for")) return Code.PR_FOR;
                if (token.equals("function")) return Code.PR_FUNCTION;
                return Code.ID;
            case 'i':
                if (token.equals("if")) return Code.PR_IF;
                if (token.equals("integer")) return Code.PR_INTEIRO;
                return Code.ID;
            case 'm':
                if (token.equals("mod")) return Code.OP_MOD;
                return Code.ID;
            case 'n':
                if (token.equals("not")) return Code.OP_NOT;
                return Code.ID;
            case 'o':
                if (token.equals("or")) return Code.OP_OR;
                if (token.equals("of")) return Code.PR_OF;
                return Code.ID;
            case 'p':
                if (token.equals("procedure")) return Code.PR_PROCEDURE;
                if (token.equals("program")) return Code.PR_PROGRAM;
                return Code.ID;
            case 'r':
                if (token.equals("real")) return Code.PR_REAL;
                return Code.ID;
            case 's':
                if (token.equals("step")) return Code.PR_STEP;
                if (token.equals("string")) return Code.PR_STRING;
                return Code.ID;
            case 't':
                if (token.equals("then")) return Code.PR_THEN;
                if (token.equals("true")) return Code.PR_TRUE;
                if (token.equals("to")) return Code.PR_TO;
                return Code.ID;
            case 'v':
                if (token.equals("var")) return Code.PR_VAR;
                return Code.ID;
            case 'w':                
                if (token.equals("while")) return Code.PR_WHILE;                    
                return Code.ID;
            default:
                return Code.ID;
        }
    }
    
    /**
     * 
     * @return Próximo caracter do arquivo
     * @throws java.io.IOException Ao atingir o End Of File
     */
    private char nextChar() throws IOException {
        
        // Se houver dois caracteres devolvidos, retorna o mais recente
        if(doubleUngeted)
            doubleUngeted = false;            
        // Se houver um caracter devolvido, o retorna
        else if (ungeted)
            ungeted = doubleUngeted;
        // Senao pega outro caracter do arquivo
        else
            next = in.read();        
        
        // Testa se chegou ao fim do arquivo
        if (next == -1)
            throw new EOF();
        
        return (char) next;
    }
    
    /**
     * 
     * @return Próximo caracter do arquivo, exceto espacos, identacoes e novas linhas
     * @throws java.io.IOException Ao atingir o End Of File
     */
    private char nextNonBlank() throws IOException {
        do {
            next = nextChar();
            if(next == '\n')
                line++;
        } while (next == ' ' || next == '\t' || next == '\n' || next == '\r');
        
        return (char)next;
    }

    /**
     * 
     * @param c caracter
     * @return true se c for uma letra sem acento
     */
    private static boolean isLetter(char c){
        return ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'));
    }
    
    /**
     * 
     * @param c caracter
     * @return true se c for um digito
     */
    private static boolean isDigit(char c){
        return (c >= '0' && c <= '9');
    }

    public int getLine() {
        return line;
    }   
    
    
    /**
     * 
     * @return Proximo token
     * @throws java.io.IOException Ao atingir o End Of File
     */
    public Token nextToken() {
        char ch;
        StringBuilder simbol = new StringBuilder();
        
        try{
            ch = nextNonBlank();

            switch (ch) {
                case '(': 
                    return new Token(Code.ABRE_PARENTESES, "(");
                case ')': 
                    return new Token(Code.FECHA_PARENTESES, ")");
                case '+': 
                    return new Token(Code.OP_ADICAO, "+");
                case '-': 
                    return new Token(Code.OP_SUBTRACAO, "-");
                case '*': 
                    return new Token(Code.OP_MULTIPLICACAO, "*");
                case ',': 
                    return new Token(Code.VIRGULA, ",");
                case '/': 
                    if(nextChar() != '/') {
                        unget();
                        return new Token(Code.OP_DIVISAO, "/");
                    }
                    while(nextChar() != '\n'){}
                    line++;
                    return nextToken();
                case '=': 
                    return new Token(Code.OP_IGUAL, "=");
                case ';': 
                    return new Token(Code.PONTO_E_VIRGULA, ";");            
                case '<': 
                    ch = nextChar();
                    if(ch == '=')          
                        return new Token(Code.OP_MENOR_IGUAL, "<=");
                    else if(ch == '>')     
                        return new Token(Code.OP_DIFERENTE, "<>");
                    unget();               
                    return new Token(Code.OP_MENOR, "<");
                case '>': 
                    if(nextChar() == '=')  
                        return new Token(Code.OP_MAIOR_IGUAL, ">=");
                    unget();               
                    return new Token(Code.OP_MAIOR, ">");
                case ':':  
                    if(nextChar() == '=') 
                        return new Token(Code.OP_ATRIBUICAO, ":=");
                    unget();               
                    return new Token(Code.DOIS_PONTOS, ":");
                case '.': 
                	try {
	                    ch = nextNonBlank();
                	} catch (IOException e) {
                		return new Token(Code.PONTO, ".");
                	}
                    if(ch == '.'){
                        return new Token(Code.PONTO_PONTO, "..");
                    }    
                    if (ch == '{') {
                    	try {
	                    	while (nextNonBlank() != '}');
                    		return new Token(Code.PONTO, ".");
                    	} catch (IOException e) {
                    		return new Token(Code.ERRO, ch+"");
                    	}
                    }
                    simbol.append('.');
                default:  
                    break;
            }
        }catch (IOException e){
            return new Token(Code.DOLLAR, "$");
        }
        // Testa se eh numero
        if (isDigit(ch))
            return numberToken(simbol, ch);        
        
        // Testa se comecou com '.' e o caracter seguinte nao eh digito
        if (simbol.toString().equals(".")) {
            unget();
            return new Token(Code.ERRO, simbol.toString());            
        }
        
        // Testa se eh um identificador        
        if (isLetter(ch))
            return identifierToken(simbol, ch);
        
        if(ch == '\'')
            return stringToken(simbol, ch);
        
        // Se chegar aqui, significa que o caracter nao eh valido
        if (ch == '{') {
        	try {
				while (nextNonBlank() != '}');
			} catch (IOException e) {
				return new Token(Code.ERRO, ch+"" );
			}
        	return nextToken();
        }
        	
        return new Token(Code.ERRO, ch+"" );
        
    }

    // Devolve o ultimo caracter lido, ou seja, o nextChar() lerá novamente o caracter
    private void unget() {
        ungeted = true;                   
    }
    
    // Devolve duas vezes o ultimo caracter lido
    private void doubleUnget() {
        doubleUngeted = true;           
    }

    private Token identifierToken(StringBuilder simbol, char ch){        
        // Adiciona todas as letras sequenciais a simbol
        try{
            do{
                simbol.append(ch);
                ch = nextChar();                    
            }while (isLetter(ch) || isDigit(ch) || ch == '_');
        }finally{
            unget();
            if(simbol.toString().length() < 64)
                return new Token(codeOf(simbol), simbol.toString());
            else
                return new Token(codeOf(simbol), simbol.toString().substring(0, 64));
        }
            
    }
    
    private Token stringToken(StringBuilder simbol, char ch){
        
        boolean barra = false;  // Indica se o caracter anterior foi contrabarra
                
        try{
            ch = nextChar();
            while( barra || ch != '\'' ){
                simbol.append(ch);                
                if(ch != '\\'){
                    barra = false;                       
                }                
                else{
                    barra = true;
                }
                ch = nextChar();
            }
        }catch (EOF e){
            unget();
            return new Token(Code.ERRO, simbol.toString());
        }finally{
            return new Token(Code.LIT_STRING, simbol.toString());            
        }            
    }

    private Token numberToken(StringBuilder simbol, char ch){        
        int type = (simbol.length() == 0)?Code.LIT_INTEIRO:Code.LIT_REAL;
        boolean isNumber = true;
        
        try{
            // Adiciona todos os digitos sequenciais a simbol
            while (isDigit(ch)) {
                simbol.append(ch);                
                ch = nextChar();
            }

            // Testa se encontrou um '.' e simbol ainda nao possui nenhum '.'
            if (ch == '.' && simbol.charAt(0) != '.') {
                ch = nextChar();
                // Caso seja '..' devolve os pontos e retorna o token anterior
                if(ch == '.'){
                    doubleUnget();
                    return new Token(Code.LIT_INTEIRO, simbol.toString());
                }
                // Senao adiciona o '.' e continua a formar do token
                else{
                    simbol.append('.');                    
                    type = Code.LIT_REAL;
                }
            }            
            
            // Adiciona todos os digitos sequenciais depois do '.' a simbol
            while (isDigit(ch)) {
                simbol.append(ch);
                ch = nextChar();
            }

            // Caso encontre um 'e', vericica se o numero esta completo
            if (ch == 'e') {                
                type = Code.LIT_REAL;
                isNumber = false;
                simbol.append('e');
                ch = nextChar();

                // Pega um possivel sinal apos o 'e'
                if (ch == '+' || ch == '-') {
                    simbol.append(ch);
                    ch = nextChar();
                }

                // Se encontrar um numero, adiciona ele a simbol
                if (isDigit(ch)) {
                    isNumber = true;
                    while (isDigit(ch)) {
                        simbol.append(ch);
                        ch = nextChar();
                    }                    
                }
                
                // Senao pega toda a palavra e transmite como erro
                else{
                    isNumber = false;
                    while(next != ' ' && next != '\t' && next != '\n' && next != '\r'){
                        simbol.append(ch);
                        ch = nextChar();
                    }
                }
            }
            
            if(isLetter(ch) || ch == '.'){
                isNumber = false;
                // Pega toda a palavra e transmite como erro
                while(next != ' ' && next != '\t' && next != '\n' && next != '\r'){
                    simbol.append(ch);
                    ch = nextChar();
                }                                
            }
        }finally{
            unget();
            if(isNumber){
                return new Token(type, simbol.toString());                
            }
            else{
                return new Token(Code.ERRO, simbol.toString());                                
            }                
        }
    }
}

