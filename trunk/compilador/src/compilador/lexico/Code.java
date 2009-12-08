package compilador.lexico;

public class Code {

    public static final int 
            
            DOLLAR = 0,
            ABRE_PARENTESES = 1,
            FECHA_PARENTESES = 2,            
            OP_MULTIPLICACAO = 3,
            OP_ADICAO = 4,
            VIRGULA = 5,
            OP_SUBTRACAO = 6,
            PONTO_PONTO = 7,
            OP_DIVISAO = 8,
            DOIS_PONTOS = 9,
            OP_ATRIBUICAO = 10,            
            PONTO_E_VIRGULA = 11,            
            OP_MENOR = 12,
            OP_IGUAL = 13,
            OP_MAIOR = 14,
            OP_MENOR_IGUAL = 15,
            OP_MAIOR_IGUAL = 16,
            OP_DIFERENTE = 17,
            OP_AND = 18,
            PR_BEGIN = 19, 
            PR_BOOLEAN = 20,
            PR_CASE = 21,
            PR_DO = 22,
            PR_DOWNTO = 23,
            PR_ELSE = 24,                             
            PR_END = 25, 
            PR_FALSE = 26,
            PR_FOR = 27, 
            PR_FUNCTION = 28,
            ID = 29, 
            PR_IF = 30, 
            PR_INTEIRO = 31,
            LIT_INTEIRO = 32, 
            LIT_REAL = 33,
            LIT_STRING = 34,
            OP_MOD = 35,
            OP_NOT = 36,
            PR_OF = 37,
            OP_OR = 38,
            PR_PROCEDURE = 39,
            PR_PROGRAM = 40,
            PR_REAL = 41,
            PR_STEP = 42,
            PR_STRING = 43,            
            PR_THEN = 44,
            PR_TO = 45,
            PR_TRUE = 46,            
            PR_VAR = 47,
            PR_WHILE = 48, 
            PONTO = 49,
            ABRE_CHAVE = 50,
            FECHA_CHAVE = 51,
            PR_ARRAY = 52,
            ABRE_COLCHETE = 53,
            FECHA_COLCHETE = 54,
            ERRO = 55; 
            
    public static final int
            
            ARGUMENTOS = 100,
            ARGUMENTOS_OPCIONAL = 101,
            ATRIBUICAO_OU_CALL = 102,
            BLOCO = 103,
            BLOCO_CASE = 104,
            BLOCO_CASE_2 = 105,
            BLOCO_ELSE = 106,
            BLOCO_OU_COMANDO = 107,
            COMANDO = 108,
            CONDICIONAL = 109,            
            DECLARACAO_ROTINAS = 110,
            DECLARACAO_VARIAVEIS = 111,
            EXPRESSAO = 112,
            EXPRESSAO_COMPARACAO = 113,
            EXP_SIMPLES = 114,
            EXP_SIMPLES_2 = 115,
            FATOR = 116,
            LACO = 117,
            LISTA_ARGUMENTOS = 118,
            LIST_COMANDOS = 119,
            LISTA_LITERAIS = 120,
            LISTA_LITERAIS_2 = 121,
            LISTA_PARAMETROS = 122,
            LISTA_VARIAVEIS = 123,
            OPERADOR_ADITIVO = 124,
            OPERADOR_MULTIPLICATIVO = 125,
            OPERADOR_RELACIONAL = 126,
            PARAMETROS = 127,
            PASSO = 128,
            PROGRAMA = 129,
            SINAL = 130,
            TERMO = 131,
            TERMO2 = 132,
            TIPO = 133,            
            TO_OU_DOWNTO = 134,
    		COMENTARIO = 135;
    
    public static boolean isTerminal(int code){
        return (code >= 0 && code < 100);
    }
    
    public static boolean isNaoTerminal(int code){
        return (code >= 100 && code < 200);
    }
    
    public static boolean isAcaoSemantica(int code){
        return code < 0;
    }
    
    public static int getRowOf(int naoTerminal){
        return naoTerminal - 100;
    }
    
    public static int AS(int acaoSemantica){
        return -acaoSemantica;
    }
    
    // Decodifica o código do terminal em sua descricao
    public static String getDescrition(int code){
        switch(code){
            case ERRO: return "ERRO";
            case ID: return "IDENTIFICADOR";
            case LIT_INTEIRO: return "LITERAL INTEIRO";
            case LIT_REAL: return "LITERAL REAL";
            case LIT_STRING: return "LITERAL STRING";
            case OP_ADICAO: return "+";
            case OP_SUBTRACAO: return "-";
            case OP_MULTIPLICACAO: return "*";
            case OP_DIVISAO: return "/";
            case OP_MOD: return "mod";
            case OP_ATRIBUICAO: return ":=";
            case OP_AND: return "and";
            case OP_OR: return "or";
            case OP_NOT: return "not";
            case OP_MENOR: return "<";
            case OP_MENOR_IGUAL: return "<=";
            case OP_IGUAL: return "=";
            case OP_DIFERENTE: return "<>";
            case OP_MAIOR: return ">";
            case OP_MAIOR_IGUAL: return ">=";
            case DOIS_PONTOS: return ":";
            case PONTO_PONTO: return "..";
            case VIRGULA: return ",";
            case PONTO_E_VIRGULA: return ";";
            case ABRE_PARENTESES: return "(";
            case FECHA_PARENTESES: return ")";
            case PR_BOOLEAN: return "boolean";
            case PR_INTEIRO: return "integer";
            case PR_REAL: return "real";
            case PR_STRING: return "string";
            case PR_FALSE: return "false";
            case PR_TRUE: return "true";
            case PR_BEGIN: return "begin";
            case PR_END: return "end";
            case PR_IF: return "if";
            case PR_THEN: return "then";
            case PR_ELSE: return "else";
            case PR_FOR: return "for";
            case PR_TO: return "to";
            case PR_DOWNTO: return "downto";
            case PR_STEP: return "step";
            case PR_DO: return "do";
            case PR_WHILE: return "while";
            case PR_CASE: return "case";
            case PR_OF: return "of";
            case PR_FUNCTION: return "function";
            case PR_PROCEDURE: return "procedure";
            case PR_VAR: return "var";
            case PR_PROGRAM: return "program";
            case PR_ARRAY: return "array";
            case PONTO: return ".";
            case ABRE_CHAVE: return "{";
            case FECHA_CHAVE: return "}";
            case ABRE_COLCHETE: return "[";
            case FECHA_COLCHETE: return "]";
            default:return "ERRO";
        }
    }
    
}
