package compilador;

import compilador.sintatico.AnalisadorSintatico;

public class Compilador {

    public static void main(String[] args) {
        
        try{
            AnalisadorSintatico sintatico = new AnalisadorSintatico();
            sintatico.compilar(args[0]);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

}
