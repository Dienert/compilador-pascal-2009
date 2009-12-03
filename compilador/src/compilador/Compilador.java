package compilador;

import compilador.sintatico.AnalisadorSintatico;

public class Compilador {

    public static void main(String[] args) {
        
        try{
            AnalisadorSintatico sintatico = new AnalisadorSintatico();
            sintatico.compilar(args[0]);
            System.out.println("Arquivo: "+args[0]+": ok");
        } catch(Exception e){
        	System.out.println("Arquivo: "+args[0]+": problema");
            e.printStackTrace();
        }
    }

}
