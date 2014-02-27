package ieci.tecdoc.sgm.migration.test;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import junit.framework.Assert;
import junit.framework.TestCase;
import ieci.tecdoc.sgm.core.exception.SigemException;
import ieci.tecdoc.sgm.migration.SigemMigrationServiceAdapter;
import ieci.tecdoc.sgm.migration.config.Config;
import ieci.tecdoc.sgm.migration.exception.MigrationException;
import ieci.tecdoc.sgm.migration.mgr.dto.ResultadoMigracionDto;
import ieci.tecdoc.sgm.migration.mgr.impl.MigrationManagerImpl;

public class MigrationRegistersTest extends TestCase {

	private static boolean ENTORNO_LOCAL = false;
	
	/**
	 * Constructor que establece la variable ENTORNO_LOCAL para ejecutar los test
	 * en función del fichero de configuración. Esto tiene sentido al ejecutar mvn
	 * puesto que para generar el war ejecuta estos TestCase
	 */
	public MigrationRegistersTest() {
		try {
			ENTORNO_LOCAL = Boolean.getBoolean(Config.getInstance().getEntornoLocal());
		} catch (MigrationException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Test que ejecuta la migración de registros de entrada
	 */
	public void testMigrationRegistersEntrada() {
		
		try {
			MigrationManagerImpl manager = new MigrationManagerImpl(
					Config.getInstance().getOrigenEntity(), 
					Config.getInstance().getDestinoEntity());
			if(ENTORNO_LOCAL) {
				ResultadoMigracionDto resultado = new ResultadoMigracionDto();
				try {
					manager.migrarRegistrosLibroEntrada(resultado);
					Assert.assertTrue(true);
				} catch (RemoteException e) {
					e.printStackTrace();
					Assert.assertTrue(false);
				}
			}
		} catch (MigrationException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		} catch (SigemException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		} catch (ServiceException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}	
	}
	
	/**
	 * Test que ejecuta la migración de registros de salida
	 */
	
	public void testMigrationRegistersSalida() {
		
		try {
			MigrationManagerImpl manager = new MigrationManagerImpl(
					Config.getInstance().getOrigenEntity(), 
					Config.getInstance().getDestinoEntity());
			if(ENTORNO_LOCAL) {
				ResultadoMigracionDto resultado = new ResultadoMigracionDto();
				try {
					manager.migrarRegistrosLibroSalida(resultado);
					Assert.assertTrue(true);
				} catch (RemoteException e) {
					e.printStackTrace();
					Assert.assertTrue(false);
				}
			}
		} catch (MigrationException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		} catch (SigemException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		} catch (ServiceException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}	
	}
	
	/**
	 * Test que ejecuta el jobs de migración de registros
	 */
	public void testMigrationRegistersJob() {
		SigemMigrationServiceAdapter job = new SigemMigrationServiceAdapter();
		try {
			if(ENTORNO_LOCAL) {
				job.migracion();
				Assert.assertTrue(true);
			}
		} catch (MigrationException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}
}
