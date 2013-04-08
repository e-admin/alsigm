package es.ieci.tecdoc.fwktd.dir3.api.service.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration({"/jndi.xml", "/beans/cxf.xml", "/beans/fwktd-dir3-test-beans.xml",
		"/beans/fwktd-dir3-api-applicationContext.xml" })
public class ServicioActualizacionDirectorioComunImplTest extends
		AbstractJUnit4SpringContextTests {


	@Autowired
	private ServicioActualizacionDirectorioComunImpl fwktd_dir3_api_servicioActualizacionDirectorioComunImpl;

	protected ServicioActualizacionDirectorioComunImpl getServicioConsultaDirectorioComun() {
		return fwktd_dir3_api_servicioActualizacionDirectorioComunImpl;
	}

	@Test
	public void generateScriptSQLtest1(){
		fwktd_dir3_api_servicioActualizacionDirectorioComunImpl.generateScriptsActualizacionDirectorioComun();
	}

	@Test
	public void actualizarDCtest1(){
		fwktd_dir3_api_servicioActualizacionDirectorioComunImpl.actualizarDirectorioComun();
	}

}
