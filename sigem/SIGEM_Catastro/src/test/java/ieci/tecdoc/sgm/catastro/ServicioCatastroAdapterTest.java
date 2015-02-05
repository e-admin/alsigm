/**
 * 
 */
package ieci.tecdoc.sgm.catastro;

import ieci.tecdoc.sgm.core.services.catastro.CatastroServicioException;
import ieci.tecdoc.sgm.core.services.catastro.Parcelas;
import ieci.tecdoc.sgm.core.services.catastro.ServicioCatastro;
import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class ServicioCatastroAdapterTest extends TestCase {

	private static final String REFERENCIA_CATASTRAL = "3747819WK7334F";

	private ServicioCatastro servicio = new ServicioCatastroAdapter();

	public void testConsultarCatastro() throws CatastroServicioException {
		Parcelas parcelas = servicio.consultarCatastro(REFERENCIA_CATASTRAL);
		Assert.assertNotNull("No se ha encontrado ninguna parcela", parcelas);
		Assert.assertTrue("No se ha encontrado ninguna parcela",
				parcelas.count() > 0);
	}

	public void testValidarReferenciaCatastral()
			throws CatastroServicioException {
		Assert.assertTrue("La referencia catastral no es válida",
				servicio.validarReferenciaCatastral(REFERENCIA_CATASTRAL));
		Assert.assertFalse(servicio.validarReferenciaCatastral("XXX"));
	}
}
