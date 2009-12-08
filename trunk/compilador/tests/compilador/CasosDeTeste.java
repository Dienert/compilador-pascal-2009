package compilador;

import org.junit.Ignore;
import org.junit.Test;

public class CasosDeTeste {

	@Test
	@Ignore
	public void testPrsint() {
		for (int i=2; i<=40; i++){
			String [] argumentos =  
				new String[] {"testes/ER-SEM"+String.format("%02d", i)+".PAS"};
			Compilador.main(argumentos);
		}
	}
	
	@Test
	public void testArray(){
		String [] argumentos =  new String[] {"dinho/array.pas"};
		Compilador.main(argumentos);
	}

}
