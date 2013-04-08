package es.ieci.tecdoc.fwktd.sir.api.manager.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import es.ieci.tecdoc.fwktd.sir.api.manager.CompresionManager;
import es.ieci.tecdoc.fwktd.sir.api.vo.FicheroVO;

/**
 * @author Iecisa
 * @version $Revision$
 *
 */
@ContextConfiguration({ 
	"/beans/fwktd-sir-test-beans-initial-custom.xml",
	"/beans/fwktd-sir-api-applicationContext.xml",
		"/beans/fwktd-sir-test-beans-custom.xml" })
public class CompresionManagerImplTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private CompresionManager fwktd_sir_compresionManager;

	public CompresionManager getCompresionManager() {
		return fwktd_sir_compresionManager;
	}

	@Test
	public void testManager() {
		Assert.assertNotNull(getCompresionManager());
	}

	@Test
	public void testComprimirFichero() {

		File ficheroComprimido = null;

		try {

			ficheroComprimido = getCompresionManager().comprimirFichero(
					new FicheroVO("fichero1.txt", "Contenido1".getBytes()));

			Assert.assertTrue(ficheroComprimido.isFile());
			Assert.assertTrue(ficheroComprimido.length() > 0);

		} catch (Exception e) {
			logger.error("Error al comprimir el fichero", e);
			Assert.fail(e.getMessage());
		} finally {
			if ((ficheroComprimido != null) && ficheroComprimido.isFile()) {
				ficheroComprimido.delete();
			}
		}
	}

	@Test
	public void testComprimirFicheros() {

		File ficheroComprimido = null;

		try {

			List<FicheroVO> ficheros = new ArrayList<FicheroVO>();
			ficheros.add(new FicheroVO("fichero1.txt", "Contenido1".getBytes()));
			ficheros.add(new FicheroVO("fichero2.xml", "<contenido/>".getBytes()));

			ficheroComprimido = getCompresionManager().comprimirFicheros(ficheros);

			Assert.assertTrue(ficheroComprimido.isFile());
			Assert.assertTrue(ficheroComprimido.length() > 0);

		} catch (Exception e) {
			logger.error("Error al comprimir el fichero", e);
			Assert.fail(e.getMessage());
		} finally {
			if ((ficheroComprimido != null) && ficheroComprimido.isFile()) {
				ficheroComprimido.delete();
			}
		}
	}

	@Test
	public void testDescomprimirFicheros() throws IOException {

		File ficheroComprimido = null;
		File destDir = null;

		try {

			List<FicheroVO> ficheros = new ArrayList<FicheroVO>();
			ficheros.add(new FicheroVO("fichero1.txt", "Contenido1".getBytes()));
			ficheros.add(new FicheroVO("fichero2.xml", "<contenido/>".getBytes()));

			ficheroComprimido = getCompresionManager().comprimirFicheros(ficheros);

			Assert.assertTrue(ficheroComprimido.isFile());
			Assert.assertTrue(ficheroComprimido.length() > 0);

			destDir = new File(System.getProperty("java.io.tmpdir"), "d_" + ficheroComprimido.getName());

			getCompresionManager().descomprimirFichero(ficheroComprimido, destDir);


		} catch (Exception e) {
			logger.error("Error al comprimir el fichero", e);
			Assert.fail(e.getMessage());
		} finally {
			if ((ficheroComprimido != null) && ficheroComprimido.isFile()) {
				ficheroComprimido.delete();
			}
			if ((destDir != null) && destDir.isDirectory()) {
				FileUtils.deleteDirectory(destDir);
			}
		}
	}
}
