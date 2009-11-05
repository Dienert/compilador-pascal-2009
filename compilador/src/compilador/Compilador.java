/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package compilador;

import compilador.sintatico.AnalisadorSintatico;

public class Compilador {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try{
            AnalisadorSintatico sintatico = new AnalisadorSintatico();
            sintatico.compilar(args[0]);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

}
