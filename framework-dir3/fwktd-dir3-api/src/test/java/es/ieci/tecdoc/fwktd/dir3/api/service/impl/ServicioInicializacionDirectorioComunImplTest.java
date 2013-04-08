package es.ieci.tecdoc.fwktd.dir3.api.service.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import es.ieci.tecdoc.fwktd.dir3.core.service.ServicioInicializacionDirectorioComun;

/**
 * Clase de Test del servicio de inicializacion del Directorio Común.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
@ContextConfiguration({"/beans/cxf.xml", "/beans/fwktd-dir3-test-beans.xml",
		"/beans/fwktd-dir3-api-applicationContext.xml" })
public class ServicioInicializacionDirectorioComunImplTest extends
		AbstractJUnit4SpringContextTests {

	@Autowired
	private ServicioInicializacionDirectorioComunImpl fwktd_dir3_api_servicioInicializacionDirectorioComunImpl;

	public ServicioInicializacionDirectorioComun getFwktd_dir3_api_servicioInicializacionDirectorioComunImpl() {
		return fwktd_dir3_api_servicioInicializacionDirectorioComunImpl;
	}

	@Test
	public void testInicializacionDCO(){
		fwktd_dir3_api_servicioInicializacionDirectorioComunImpl.inicializarDirectorioComun();
	}

	@Test
	public void testGenerateScriptDCO(){
		fwktd_dir3_api_servicioInicializacionDirectorioComunImpl.generateScriptsInicializacionDirectorioComun();
	}

}
