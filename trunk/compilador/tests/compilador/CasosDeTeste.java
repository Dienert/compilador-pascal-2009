package compilador;

import org.junit.Test;

public class CasosDeTeste {

	@Test
	public void testPrsint() {
		String [] argumentos = new String[] {"Test1.pas"};
		Compilador.main(argumentos);
	}

}
