package es.ieci.tecdoc.fwktd.dir3.test;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import es.ieci.tecdoc.fwktd.dir3.services.ServicioObtenerActualizacionesDCO;

@ContextConfiguration(locations = { "/beans/fwktd-dir3-dco-wsclient/cxf.xml",
		"/beans/fwktd-dir3-dco-wsclient-applicationContext.xml" })
public class ServicioActualizacionDCOTest extends
		AbstractJUnit4SpringContextTests {

	@Autowired
	public ServicioObtenerActualizacionesDCO servicioActualizacionDCO;

	public ServicioObtenerActualizacionesDCO getServicioActualizacionDCO() {
		return servicioActualizacionDCO;
	}

	public void setServicioActualizacionDCO(
			ServicioObtenerActualizacionesDCO servicioActualizacionDCO) {
		this.servicioActualizacionDCO = servicioActualizacionDCO;
	}

	@Test
	public void actualizacionOficinasTest(){
		Date lastDateUpdate = Calendar.getInstance().getTime();

		String file = servicioActualizacionDCO.getFicheroActualizarOficinasDCO(lastDateUpdate);
		file.toString();
	}

	@Test
	public void actualizacionUnidadesTest(){
		Date lastDateUpdate = Calendar.getInstance().getTime();

		String file = servicioActualizacionDCO.getFicheroActualizarUnidadesDCO(lastDateUpdate);
		file.toString();
	}
}
