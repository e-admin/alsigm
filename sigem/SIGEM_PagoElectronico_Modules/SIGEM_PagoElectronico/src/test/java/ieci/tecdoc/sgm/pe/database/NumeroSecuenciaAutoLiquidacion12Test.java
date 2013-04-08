package ieci.tecdoc.sgm.pe.database;

import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.core.db.DataSourceManager;
import ieci.tecdoc.sgm.pe.ConfiguracionComun;
import ieci.tecdoc.sgm.pe.database.exception.DbExcepcion;
import junit.framework.TestCase;

public class NumeroSecuenciaAutoLiquidacion12Test extends TestCase{

	String codEntidad = null;
	
	public void testObtenerIdentificador() {
		long numero = 0;
		try {
			numero = NumeroSecuenciaAutoLiquidacion12.obtenerIdentificador(codEntidad);
		} catch (DbExcepcion e) {
			e.printStackTrace();
			fail();
		}
		System.out.println(numero);
	}

	public void testObtenerIdentificadorDBConNoTrans() {
		DbConnection dbcon = new DbConnection();
		long numero = 0;
		try {
//			dbcon.open(Configuracion.getDatabaseConnection());
			dbcon.open(DataSourceManager.getInstance().getConnection(ConfiguracionComun.getConfiguracion()));			
			numero = NumeroSecuenciaAutoLiquidacion12.obtenerIdentificador(dbcon);			
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(true);
		}finally{
			try {
				dbcon.close();
			} catch (Exception e) {
				e.printStackTrace();
				fail();
			}
		}
		System.out.println(numero);
	}

	public void testObtenerIdentificadorDBCon() {
		DbConnection dbcon = new DbConnection();
		long numero = 0;
		try {
//			dbcon.open(Configuracion.getDatabaseConnection());
			dbcon.open(DataSourceManager.getInstance().getConnection(ConfiguracionComun.getConfiguracion()));			
			dbcon.beginTransaction();
			numero = NumeroSecuenciaAutoLiquidacion12.obtenerIdentificador(dbcon);
			System.out.println(numero);
			dbcon.endTransaction(true);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}finally{
			try {
				dbcon.close();
			} catch (Exception e) {
				e.printStackTrace();
				fail();
			}
		}		
		System.out.println(numero);
		assertTrue(true);
	}

}
