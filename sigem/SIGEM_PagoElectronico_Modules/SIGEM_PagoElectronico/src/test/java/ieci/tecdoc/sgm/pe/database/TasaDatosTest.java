package ieci.tecdoc.sgm.pe.database;

import junit.framework.TestCase;
import ieci.tecdoc.sgm.pe.database.exception.DbExcepcion;
import ieci.tecdoc.sgm.pe.exception.GuidIncorrectoExcepcion;


public class TasaDatosTest extends TestCase{

	String codEntidad = null;
	
	public void testLoad() {
		TasaDatos oDoc = new TasaDatos();
		try {
			oDoc.load("100", "000000", codEntidad);
		} catch (GuidIncorrectoExcepcion e) {
			e.printStackTrace();
			fail();
		} catch (DbExcepcion e) {
			e.printStackTrace();
			fail();
		}
		assertTrue(true);				
	}

	public void testAdd() {
		TasaDatos oDoc = new TasaDatos();
		oDoc.setCodigo("110");
		oDoc.setIdEntidadEmisora("000000");
		oDoc.setModelo("MODELO02");
		oDoc.setNombre("Prueba dos");
		oDoc.setTipo("AL1");
		try {
			oDoc.add(codEntidad);
		} catch (DbExcepcion e) {
			e.printStackTrace();
			fail();
		}
		assertTrue(true);
	}

	public void testDelete() {
		TasaDatos oDoc = new TasaDatos();
		oDoc.setCodigo("110");
		oDoc.setIdEntidadEmisora("000000");
		try {
			oDoc.delete(codEntidad);
		} catch (DbExcepcion e) {
			e.printStackTrace();
			fail();
		} catch (GuidIncorrectoExcepcion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
		assertTrue(true);
	}

}
