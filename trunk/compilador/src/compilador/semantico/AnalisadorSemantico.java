
package compilador.semantico;

import compilador.ErroCompilacao;
import compilador.lexico.Code;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class AnalisadorSemantico {

    private LinkedList<Object> typeList;
    private LinkedList<String> idList;
    private LinkedList<String> decList;
    private LinkedList<Integer> opList;
    private HashMap<String, Object> tabelaId;
    private HashMap<String, LinkedList> tabelaArgumentos;
    private int linha, nivel, cont;
    private String testing;



    private class Procedure{}
    
    public AnalisadorSemantico(){
        typeList = new LinkedList<Object>();
        idList = new LinkedList<String>();
        decList = new LinkedList<String>();
        opList = new LinkedList<Integer>();
        tabelaId = new HashMap<String, Object>();
        tabelaArgumentos = new HashMap<String, LinkedList>();
        tabelaId.put("write0", new Procedure());
        tabelaId.put("writeln0", new Procedure());
        tabelaId.put("read0", new Procedure());
        tabelaId.put("readln0", new Procedure());
    }
    
    public void execute(int linha, int acao) throws ErroCompilacao {
        
        this.linha = linha;
        switch(acao) {
            case -1:
                addIdType();
                break;
            case -2:
                checkOperandos();
                break;
            case -3:
                addDecList();
                break;
            case -4:
                declare();
                break;
            case -5:
                inNivel();
                break;
            case -6:
                outNivel();
                break;
            case -7:
                declareFunction();
                break;
            case -8:
                declareProcedure();
                break;
            case -9:
                testIdComando();
                break;
            case -10:
                testArgs();
                break;
            case -11:
                expBoolean();
                break;
            case -12:
                expInteger();
                break;
            case -13:
                decFor();
                break;

        }
        
    }
    
    public void addId(String id){
        idList.push(id);
    }
    
    public void addInteger(){
        typeList.push(new Integer(0));
        cont++;
    }
    
    public void addReal(){
        typeList.push(new Double(0));
        cont++;
    }
    
    public void addString(){
        typeList.push(new String());
        cont++;
    }
    
    public void addBoolean(){
        typeList.push(new Boolean(false));
        cont++;
    }
    
    public void addOperador(int code){
        opList.push(code);
    }
    
    private void addIdType() throws ErroCompilacao {
        String id = idList.pop();
        cont++;
        Object type = null;
        int n = nivel;
        
        do {
            type = tabelaId.get(id + n);
        } while(type == null && --n >= 0);
        
        if(type != null && !Procedure.class.isInstance(type)) {
            typeList.push(type);
        } else if(type == null){            
            throw new ErroCompilacao(linha, id);           
        } else {
            throw new ErroCompilacao("Procedimentos não podem ser usados em expressões", linha);
        }
    }

    private void testIdComando() throws ErroCompilacao {
        testing = idList.pop();
        cont = 0;
        Object type = null;
        int n = nivel;        
        do {
            type = tabelaId.get(testing + n);
        } while(type == null && --n >= 0);
        
        if(type != null && !Procedure.class.isInstance(type)) {
            typeList.push(type);
        } else if(type == null){            
            throw new ErroCompilacao(linha, testing);           
        }
    }
    
    private void declareFunction() throws ErroCompilacao {
        LinkedList args = new LinkedList();
        for(String key : tabelaId.keySet()){
            if(key.endsWith(""+nivel)) {
                args.add(tabelaId.get(key));
            }
        }
        String functionID = decList.pop();
        tabelaArgumentos.put(functionID, args);
        Object type = typeList.pop();
        if(tabelaId.put(functionID+(nivel-1), type) != null)
            throw new ErroCompilacao("Tentando sobrescrever identificador no mesmo nivel", linha);
    }

    private void declareProcedure() throws ErroCompilacao {
        LinkedList args = new LinkedList();
        for(String key : tabelaId.keySet()){
            if(key.endsWith(""+nivel)) {
                args.add(tabelaId.get(key));
            }
        }
        String procedureID = idList.pop();
        tabelaArgumentos.put(procedureID, args);
        if(tabelaId.put(procedureID+0, new Procedure()) != null)
            throw new ErroCompilacao("Tentando sobrescrever identificador no mesmo nivel", linha);
    }
    
    private void inNivel() {
        nivel++;
    }
    
    private boolean isInteger(Object o){
        return Integer.class.isInstance(o);
    }
    
    private boolean isDouble(Object o){
        return Double.class.isInstance(o);
    }
    
    private boolean isBoolean(Object o){
        return Boolean.class.isInstance(o);
    }
    
    private boolean isNumber(Object o){
        return (isInteger(o) || isDouble(o));
    }
    
    private void checkOperandos() throws ErroCompilacao {        
        Object type2 = typeList.pop();
        Object type1 = typeList.pop();
        int op = opList.pop();
        cont --;
        
        switch(op){
            case Code.OP_ADICAO:
            case Code.OP_SUBTRACAO:
            case Code.OP_MULTIPLICACAO:
            case Code.OP_DIVISAO:
                if((isDouble(type1) && isNumber(type2)) || (isNumber(type1) && isDouble(type2))) {
                    typeList.push(new Double(0));
                } else if(isInteger(type1) && isInteger(type2)) {
                    typeList.push(new Integer(0));            
                } else {
                    throw new ErroCompilacao("Operador \""+Code.getDescrition(op)+"\" requer dois números", linha);
                }
                break;
            case Code.OP_MOD:
                if(isInteger(type1) && isInteger(type2)) {
                    typeList.push(new Integer(0));            
                } else {
                    throw new ErroCompilacao("Operador \"mod\" requer dois integer", linha);
                }
                break;
            case Code.OP_AND:
            case Code.OP_OR:
                if(isBoolean(type1) && isBoolean(type2)) {
                    typeList.push(new Boolean(false));            
                } else {
                    throw new ErroCompilacao("Operador \""+Code.getDescrition(op)+"\" requer dois booleanos", linha);
                }
                break;
            case Code.OP_DIFERENTE:
            case Code.OP_IGUAL:
            case Code.OP_MAIOR:
            case Code.OP_MAIOR_IGUAL:
            case Code.OP_MENOR:
            case Code.OP_MENOR_IGUAL:
                if((isNumber(type1) && isNumber(type2))) {
                    typeList.push(new Boolean(false));
                } else {
                    throw new ErroCompilacao("Operador \""+Code.getDescrition(op)+"\" requer dois números", linha);
                }
                break;
            case Code.OP_ATRIBUICAO:                
                if(type1.getClass().isInstance(type2) || (isDouble(type1) && isInteger(type2))) {
                    cont--;
                } else {
                    throw new ErroCompilacao("Operador \":=\" requer requer uma variável ou " +
                            "função antes e uma espressão de mesmo tipo depois", linha);
                }
        }
        
    }
    
    private void addDecList(){
        decList.push(idList.pop());
    }
    
    private void declare() throws ErroCompilacao{
        Object type = typeList.pop();
        while(!decList.isEmpty()){
            if(tabelaArgumentos.get(decList.getFirst())!= null) {
                throw new ErroCompilacao("Tentando sobrescrever um procedimento ou função", linha);                
            }
            if(tabelaId.put(decList.pop()+nivel, type) != null) {
                throw new ErroCompilacao("Tentando sobrescrever identificador no mesmo nivel", linha);
            }
        }
    }

    private void outNivel() {
        LinkedList<String> keysToRemove = new LinkedList<String>();
        for(String key : tabelaId.keySet()) {
            if(key.endsWith(""+nivel)) {
                keysToRemove.add(key);
            }                
        }        
        for(String key : keysToRemove) {
            tabelaId.remove(key);
        }
        nivel--;
    }
    
    private void decFor() {
        tabelaId.put(idList.pop()+(nivel), new Integer(0));
    }

    private void expBoolean() throws ErroCompilacao {
        Object type = typeList.pop();
        if(!Boolean.class.isInstance(type)){
            throw new ErroCompilacao(linha, new Boolean(true), type);                                
        }
    }

    private void expInteger() throws ErroCompilacao {
        Object obj = typeList.pop();
        if(!Integer.class.isInstance(obj)){
            throw new ErroCompilacao(linha, new Integer(0), obj);                                
        }
    }

    private void testArgs() throws ErroCompilacao {
        if(testing.equals("writeln") && typeList.size() == 0){
            return;
        } else if (testing.equals("write") || testing.equals("writeln")){
            if(typeList.size() < 1){
                throw new ErroCompilacao("Numero incorreto de argumentos para "+testing, linha);                    
            } else {
                typeList.clear();                
            }  
            return;
        } else if(testing.equals("read")){
            if(typeList.size() != 1){
                throw new ErroCompilacao("Numero incorreto de argumentos para "+testing, linha);                    
            } else {
                typeList.pop();                
            }
        }
        LinkedList args = tabelaArgumentos.get(testing);
        try {
            if(args != null){
                for(Object obj : args){
                    Object type = typeList.pop();
                    if(!obj.getClass().isInstance(type) && (!isDouble(obj) || !isInteger(type))) {                    
                        throw new ErroCompilacao(linha, obj, type);                    
                    }
                    cont--;
                }
                if(cont != 0){
                    System.out.println(cont);
                    System.out.println(typeList);
                    throw new ErroCompilacao("Muitos argumentos para "+testing,linha);                    
                }                
            }
        }catch (NoSuchElementException e){
            throw new ErroCompilacao("Poucos argumentos para "+testing,linha);                    
        }
    }
}
