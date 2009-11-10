
package compilador.semantico;

import compilador.ErroCompilacao;

public class AnalisadorSemantico {

    private int linha, nivel, cont;

    public AnalisadorSemantico(){
    }
    
    public void execute(int linha, int acao) throws ErroCompilacao {
        
        this.linha = linha;
        switch(acao) {
            case -1:
//                addIdType();
                break;
            case -2:
//                checkOperandos();
                break;
            case -3:
//                addDecList();
                break;
            case -4:
//                declare();
                break;
            case -5:
//                inNivel();
                break;
            case -6:
//                outNivel();
                break;
            case -7:
//                declareFunction();
                break;
            case -8:
//                declareProcedure();
                break;
            case -9:
//                testIdComando();
                break;
            case -10:
//                testArgs();
                break;
            case -11:
//                expBoolean();
                break;
            case -12:
//                expInteger();
                break;
            case -13:
//                decFor();
                break;

        }
        
    }
    
    
}
