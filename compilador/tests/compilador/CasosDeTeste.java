package compilador;

import org.junit.Ignore;
import org.junit.Test;

public class CasosDeTeste {

	/**
	 * Testa arquivos de Ruan
	 */
	@Test
//	@Ignore
	public void testPrsint() {
		for (int i=2; i<=40; i++){
			String [] argumentos =  
				new String[] {"testes/ER-SEM"+String.format("%02d", i)+".PAS"};
			Compilador.main(argumentos);
		}
	}
	
	/**
	 * Meus testes
	 */
	@Test
	@Ignore
	public void testArray(){
		String [] argumentos =  new String[] {"dinho/array.pas"};
		Compilador.main(argumentos);
	}

	/**
	 * Testes dos arquivos da primeira versão
	 */
	@Test
	@Ignore
	public void testPrimeiraVersao(){
		for (int i=1; i<=5; i++){
			String [] argumentos =  new String[] {"Test"+i+".pas"};
			Compilador.main(argumentos);
		}
	}
	
}
