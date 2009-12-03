package compilador;

import org.junit.Test;

public class CasosDeTeste {

	@Test
	public void testPrsint() {
		for (int i=2; i<=40; i++){
			String [] argumentos =  
				new String[] {"testes/ER-SEM"+String.format("%02d", i)+".PAS"};
			Compilador.main(argumentos);
		}
	}

}
