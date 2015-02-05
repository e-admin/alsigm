package es.ieci.tecdoc.fwktd.dir3.test;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import es.ieci.tecdoc.fwktd.dir3.services.ServicioObtenerInicializacionDCO;

@ContextConfiguration(locations = { "/beans/fwktd-dir3-dco-wsclient/cxf.xml",
		"/beans/fwktd-dir3-dco-wsclient-applicationContext.xml" })
public class ServicioInicializacionDCOTest extends AbstractJUnit4SpringContextTests{


	@Autowired
	public ServicioObtenerInicializacionDCO servicioInicializacionDCO;

	@Test
	public void inicializarUnidadesTest()
	{
		String file = getServicioInicializacionDCO().getFicheroInicializarUnidadesDCO();
		Assert.assertNotNull(file.toString());
	}

	@Test
	public void inicializarOficinasTest()
	{
		String file = getServicioInicializacionDCO().getFicheroInicializarOficinasDCO();
		Assert.assertNotNull(file.toString());
	}

	@Test
	public void inicializarTest()
	{
		//UNIDADES DCO
		String fileUnidadesDCO = getServicioInicializacionDCO().getFicheroInicializarUnidadesDCO();
		//OFICINAS
		String fileOficinas = getServicioInicializacionDCO().getFicheroInicializarOficinasDCO();

		Assert.assertNotNull(fileUnidadesDCO.toString());
		Assert.assertNotNull(fileOficinas.toString());
	}

	public ServicioObtenerInicializacionDCO getServicioInicializacionDCO() {
		return servicioInicializacionDCO;
	}

	public void setServicioInicializacionDCO(
			ServicioObtenerInicializacionDCO servicioInicializacionDCO) {
		this.servicioInicializacionDCO = servicioInicializacionDCO;
	}


}
