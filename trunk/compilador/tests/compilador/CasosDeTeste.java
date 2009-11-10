package compilador;

import static org.junit.Assert.*;

import org.junit.Test;

public class CasosDeTeste {

	@Test
	public void testPrsint() {
		String [] argumentos = new String[] {"Pascal/prsint5.pas"};
		Compilador.main(argumentos);
	}

}
