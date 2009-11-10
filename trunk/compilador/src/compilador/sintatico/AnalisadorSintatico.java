package compilador.sintatico;

import java.io.IOException;
import java.util.LinkedList;

import compilador.ErroCompilacao;
import compilador.lexico.AnalisadorLexico;
import compilador.lexico.Code;
import compilador.lexico.Token;
import compilador.semantico.AnalisadorSemantico;

public class AnalisadorSintatico {

    private int[][] tabelaAnaliseSintatica;
    private int[] regras;
    private int[] posicoes;

    public AnalisadorSintatico() {
        initTas();
        initVectors();
    }

    /**
     * 
     * @param fileName arquivo fonte a ser compilado
     * @throws java.io.IOException caso não consiga abrir o arquivo
     * @throws compilador.ErroCompilacao caso encontre um erro de compilação
     */
    public void compilar(String fileName) throws IOException, ErroCompilacao {

        AnalisadorLexico lexico = new AnalisadorLexico(fileName);
        
        AnalisadorSemantico semantico = new AnalisadorSemantico();
        
        LinkedList<Integer> pilha = new LinkedList<Integer>();
        
        Token token = lexico.nextToken();
        Token anterior = null;        

        pilha.push(Code.DOLLAR);
        pilha.push(Code.PROGRAMA);

        laco:
        while (true) {
            
            int topo = pilha.getFirst();
            
            if (topo == token.getCodigo()) {                
                
                switch(topo){
                    case Code.DOLLAR:
                        break laco;
                    case Code.ID:
//                        semantico.addId(token.getSimbolo());
                        break;
                    case Code.PR_INTEIRO:
                    case Code.LIT_INTEIRO:
//                        semantico.addInteger();
                        break;
                    case Code.PR_REAL:
                    case Code.LIT_REAL:
//                        semantico.addReal();
                        break;
                    case Code.PR_STRING:
                    case Code.LIT_STRING:
//                        semantico.addString();
                        break;
                    case Code.PR_BOOLEAN:
                    case Code.PR_TRUE:
                    case Code.PR_FALSE:
//                        semantico.addBoolean();
                        break;
                    case Code.OP_ADICAO:
                    case Code.OP_AND:
                    case Code.OP_ATRIBUICAO:
                    case Code.OP_DIFERENTE:
                    case Code.OP_DIVISAO:
                    case Code.OP_IGUAL:
                    case Code.OP_MAIOR:
                    case Code.OP_MAIOR_IGUAL:
                    case Code.OP_MENOR:
                    case Code.OP_MENOR_IGUAL:
                    case Code.OP_MOD:
                    case Code.OP_MULTIPLICACAO:
                    case Code.OP_NOT:
                    case Code.OP_OR:
                    case Code.OP_SUBTRACAO:
//                        semantico.addOperador(topo);
                        break;
                }
                pilha.pop();
                anterior = token;
                token = lexico.nextToken();                                                   
                
                
            } else if (Code.isNaoTerminal(topo)) {
                
                topo = Code.getRowOf(pilha.pop());
                
                if(token.getCodigo() == Code.ERRO){
                    throw new ErroCompilacao(lexico.getLine(), token);
                }
                int regra = tabelaAnaliseSintatica[topo][token.getCodigo()];
                
                if (regra == 0) {
                    throw new ErroCompilacao(lexico.getLine(), anterior, token);
                }                
                for (int i = posicoes[regra]; regras[i] != 0; i++) {
                    pilha.push(regras[i]);
                }
                
            } else if (Code.isAcaoSemantica(topo)) {
                semantico.execute(lexico.getLine(), pilha.pop());
            } else { 
                throw new ErroCompilacao(lexico.getLine(),token, anterior, topo);                               
            }
        }
        
        System.out.println("\nPrograma aprovado pelos analisadores léxico e sintático \n");

    }

    /**
     * Método que inicia o vetor de regras
     *
     */
    public void initVectors() {

        regras = new int[]{            
            0,
/*001*/     Code.DECLARACAO_ROTINAS, Code.DECLARACAO_VARIAVEIS, Code.PONTO_E_VIRGULA, Code.ID, Code.PR_PROGRAM, 0,
/*007**/     Code.DECLARACAO_VARIAVEIS, Code.PONTO_E_VIRGULA, Code.LISTA_VARIAVEIS, Code.AS(3), Code.ID, Code.PR_VAR, 0,
/*014*/     0,
/*015**/     Code.LISTA_VARIAVEIS,  Code.AS(3), Code.ID, Code.VIRGULA, 0,
/*020**/     Code.AS(4), Code.TIPO, Code.DOIS_PONTOS, 0,
/*024**/     Code.DECLARACAO_ROTINAS, Code.AS(6), Code.BLOCO, Code.DECLARACAO_VARIAVEIS, Code.PONTO_E_VIRGULA, Code.AS(8), Code.FECHA_PARENTESES, Code.PARAMETROS, Code.AS(5), Code.ABRE_PARENTESES, Code.ID, Code.PR_PROCEDURE, 0,
/*037**/     Code.DECLARACAO_ROTINAS, Code.AS(6), Code.BLOCO, Code.DECLARACAO_VARIAVEIS, Code.PONTO_E_VIRGULA, Code.AS(7), Code.AS(3), Code.TIPO, Code.DOIS_PONTOS, Code.FECHA_PARENTESES, Code.PARAMETROS, Code.AS(5), Code.ABRE_PARENTESES, Code.ID, Code.PR_FUNCTION, 0,
/*053*/     Code.BLOCO, 0,
/*055**/     Code.LISTA_PARAMETROS, Code.LISTA_VARIAVEIS, Code.AS(3), Code.ID, 0,
/*060*/     0,
/*061**/     Code.LISTA_PARAMETROS, Code.LISTA_VARIAVEIS, Code.AS(3), Code.ID, Code.VIRGULA, 0,
/*067*/     0,
/*068*/     Code.PR_END, Code.LIST_COMANDOS, Code.PR_BEGIN, 0,
/*072*/     Code.LIST_COMANDOS, Code.COMANDO, 0,
/*075*/     0,
/*076*/     Code.PONTO_E_VIRGULA, Code.ATRIBUICAO_OU_CALL, Code.ID, 0,
/*080*/     Code.CONDICIONAL, 0,
/*082*/     Code.LACO, 0,
/*084**/     Code.AS(2), Code.EXPRESSAO, Code.OP_ATRIBUICAO, Code.AS(9), 0,
/*089*/     Code.ARGUMENTOS_OPCIONAL, 0,
/*091*/     Code.BLOCO_ELSE, Code.BLOCO_OU_COMANDO, Code.PR_THEN, Code.AS(11), Code.EXPRESSAO, Code.PR_IF, 0,            
/*098*/     Code.PR_END, Code.BLOCO_CASE, Code.PR_OF, Code.AS(12), Code.EXPRESSAO, Code.PR_CASE, 0,
/*105*/     Code.COMANDO, 0,
/*107*/     Code.BLOCO, 0,
/*109*/     Code.BLOCO_OU_COMANDO, Code.PR_ELSE, 0,
/*112*/     0,
/*113*/     Code.BLOCO_CASE_2, Code.LIST_COMANDOS, Code.DOIS_PONTOS, Code.LISTA_LITERAIS, Code.AS(12), Code.LIT_INTEIRO, 0,
/*120*/     Code.BLOCO_CASE_2, Code.LIST_COMANDOS, Code.DOIS_PONTOS, Code.LISTA_LITERAIS, Code.AS(12), Code.LIT_INTEIRO, 0,
/*127*/     Code.LIST_COMANDOS, Code.DOIS_PONTOS, Code.PR_ELSE, 0,
/*131*/     0,
/*132*/     Code.AS(12), Code.LIT_INTEIRO, Code.PONTO_PONTO, 0, 
/*136*/     Code.LISTA_LITERAIS_2, Code.AS(12), Code.LIT_INTEIRO, Code.VIRGULA, 0,
/*141*/     0,
/*142*/     Code.LISTA_LITERAIS_2, Code.AS(12), Code.LIT_INTEIRO, Code.VIRGULA, 0,
/*147*/     0,
/*148*/     Code.BLOCO, Code.PR_DO, Code.AS(11), Code.EXPRESSAO, Code.PR_WHILE, 0,
/*154*/     Code.BLOCO, Code.PR_DO, Code.PASSO, Code.AS(12), Code.EXPRESSAO, Code.TO_OU_DOWNTO, Code.AS(12), Code.EXPRESSAO, Code.OP_ATRIBUICAO, Code.AS(13), Code.ID, Code.PR_FOR, 0,
/*167*/     Code.PR_TO, 0,
/*169*/     Code.PR_DOWNTO, 0,
/*171*/     Code.AS(12), Code.EXPRESSAO, Code.PR_STEP, 0,
/*175*/     0,
/*176*/     Code.LISTA_ARGUMENTOS, Code.EXPRESSAO, 0,
/*179*/     0,
/*180*/     Code.LISTA_ARGUMENTOS, Code.EXPRESSAO, Code.VIRGULA, 0,
/*192*/     0,
/*193*/     Code.EXPRESSAO_COMPARACAO, Code.EXP_SIMPLES, 0,
/*196*/     Code.AS(2), Code.EXP_SIMPLES, Code.OPERADOR_RELACIONAL, 0,
/*200*/     0,
/*201*/     Code.EXP_SIMPLES_2, Code.TERMO, 0,
/*204**/     Code.AS(2), Code.EXP_SIMPLES, Code.OPERADOR_ADITIVO, 0,
/*200*/     0,
/*201*/     Code.TERMO2, Code.FATOR, 0,
/*204*/     Code.TERMO2, Code.FATOR, Code.SINAL, 0,
/*208**/     Code.AS(2), Code.TERMO, Code.OPERADOR_MULTIPLICATIVO, 0,
/*212*/     0,
/*213**/    Code.ARGUMENTOS_OPCIONAL, Code.ID, 0,
/*216*/     Code.LIT_INTEIRO, 0,
/*218*/     Code.LIT_REAL, 0,
/*220*/     Code.LIT_STRING, 0,
/*222*/     Code.PR_TRUE, 0,
/*224*/     Code.PR_FALSE, 0,
/*226*/     Code.FATOR, Code.OP_NOT, 0,
/*229*/     Code.FECHA_PARENTESES, Code.EXPRESSAO, Code.ABRE_PARENTESES, 0,
/*233**/     Code.AS(10), Code.FECHA_PARENTESES, Code.ARGUMENTOS, Code.ABRE_PARENTESES,Code.AS(9), 0,
/*239*/		Code.OP_IGUAL, 0,
/*241*/     Code.OP_MENOR, 0,
/*243*/     Code.OP_MAIOR, 0,
/*245*/     Code.OP_MENOR_IGUAL, 0,
/*247*/     Code.OP_MAIOR_IGUAL, 0,
/*249*/     Code.OP_DIFERENTE, 0,
/*251*/     Code.OP_ADICAO, 0,
/*253*/     Code.OP_SUBTRACAO, 0,
/*255*/     Code.OP_ADICAO, 0,
/*257*/     Code.OP_SUBTRACAO, 0,
/*259*/     Code.OP_OR, 0,
/*261*/     Code.OP_MULTIPLICACAO, 0,
/*263*/     Code.OP_DIVISAO, 0,
/*265*/     Code.OP_MOD, 0,
/*267*/     Code.OP_AND, 0,
/*269*/     Code.PR_INTEIRO, 0,
/*271*/     Code.PR_REAL, 0,
/*273*/     Code.PR_STRING, 0,            
/*275*/     Code.PR_BOOLEAN, 0,
/*277*/     Code.AS(1), 0,
/*279*/
        };
        
        posicoes = new int[100];
        
        posicoes[0] = 0;
        
        for(int i = 1, j = 0; j < regras.length; j++){
            if(regras[j] == 0){
                posicoes[i++] = j+1;
            }
        } 
    }

    /**
     * Tabela de análise sintática (39 não-terminais x 50 terminais)
     * O valor de cada par aponta para um índice do vetor posicoes que contém o
     * início de cada regra no vetor de regras.
     * 
     */
    private void initTas(){   
    	
        tabelaAnaliseSintatica = new int[][] {        
/* 00 */    {0,42,43,0,42,0,42,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,42,0,0,42,0,0,42,42,42,0,42,0,0,0,0,0,0,0,0,0,42,0,0,0},
/* 01 */    {0,64,84,84,84,84,84,0,84,0,0,84,84,84,84,84,84,84,84,0,0,0,84,84,0,0,0,0,0,0,0,0,0,0,0,84,0,84,84,0,0,0,84,0,84,84,0,0,0,0},
/* 02 */    {0,20,0,0,0,0,0,0,0,0,19,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
/* 03 */    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,13,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
/* 04 */    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,27,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
/* 05 */    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,29,30,0,0,0,0,0,0,28,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
/* 06 */    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,26,0,0,25,26,0,26,0,26,26,0,26,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,26,0},
/* 07 */    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,24,0,23,0,0,0,0,0,23,0,23,23,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,23,0},
/* 08 */    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,17,0,0,0,0,0,18,0,16,17,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,18,0},
/* 09 */    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,22,0,0,0,0,0,0,0,0,21,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
/* 10 */    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8,0,0,0,0,0,0,0,0,7,0,0,0,0,0,0,0,0,0,0,6,0,0,0,0,0,0,0,0,0,0},
/* 11 */    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,0,0,0,0,0,0,0,0,3,0,0,0,0,0,0,0,0,0,0,3,0,0,0,0,0,0,0,2,0,0},
/* 12 */    {0,46,0,0,46,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,46,0,0,46,0,0,46,46,46,0,46,0,0,0,0,0,0,0,0,0,46,0,0,0},
/* 13 */    {0,0,48,0,0,48,0,0,0,0,0,48,47,47,47,47,47,47,0,0,0,0,48,48,0,0,0,0,0,0,0,0,0,0,0,0,0,48,0,0,0,0,48,0,48,48,0,0,0,0},
/* 14 */    {0,49,0,0,49,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,49,0,0,49,0,0,49,49,49,0,49,0,0,0,0,0,0,0,0,0,49,0,0,0},
/* 15 */    {0,0,51,0,50,51,50,0,0,0,0,51,51,51,51,51,51,51,0,0,0,0,51,51,0,0,0,0,0,0,0,0,0,0,0,0,0,51,50,0,0,0,51,0,51,51,0,0,0,0},
/* 16 */    {0,63,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,61,0,0,56,0,0,57,58,59,0,62,0,0,0,0,0,0,0,0,0,60,0,0,0},
/* 17 */    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,37,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,36,0},
/* 18 */    {0,0,45,0,0,44,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
/* 19 */    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,14,0,0,15,15,0,14,0,14,14,0,15,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,14,0},
/* 20 */    {0,0,0,0,0,32,0,31,0,33,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
/* 21 */    {0,0,0,0,0,34,0,0,0,35,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
/* 22 */    {0,0,12,0,0,11,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
/* 23 */    {0,0,0,0,0,4,0,0,0,5,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
/* 24 */    {0,0,0,0,73,0,74,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,75,0,0,0,0,0,0,0,0,0,0,0},
/* 25 */    {0,0,0,76,0,0,0,0,77,0,0,0,0,0,0,0,0,0,79,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,78,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
/* 26 */    {0,0,0,0,0,0,0,0,0,0,0,0,66,65,67,68,69,70,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
/* 27 */    {0,0,10,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,9,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
/* 28 */    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,41,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,40,0,0,0,0,0,0,0},
/* 29 */    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0},
/* 30 */    {0,0,0,0,71,0,72,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
/* 31 */    {0,52,0,0,53,0,53,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,52,0,0,52,0,0,52,52,52,0,52,0,0,0,0,0,0,0,0,0,52,0,0,0},
/* 32 */    {0,0,55,54,55,55,55,0,54,0,0,55,55,55,55,55,55,55,54,0,0,0,55,55,0,0,0,0,0,0,0,0,0,0,0,54,0,55,55,0,0,0,55,0,55,55,0,0,0,0},
/* 33 */    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,83,0,0,0,0,0,0,0,0,0,0,80,0,0,0,0,0,0,0,0,0,81,0,82,0,0,0,0,0,0},
/* 34 */    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,39,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,38,0,0,0,0},
        };
    }
    

}
