package transferencias.electronicas.ficha;

import junit.framework.Assert;

import org.junit.Test;

import common.util.CustomDateFormat;

import test.ArchidocCommonBaseTest;

public class CampoFechaTest extends ArchidocCommonBaseTest{

	@Test
	public void testFormatoDDMMAAAAHHMMSS(){
		CampoFecha campoFecha = new CampoFecha();
		campoFecha.setFormato(CustomDateFormat.DATE_FORMAT_DDMMAAAA_HHMMSS);
		campoFecha.setSeparador("/");
		campoFecha.setValor("10/03/2013 18:53:03");

		Assert.assertEquals("10", campoFecha.getDia());
		Assert.assertEquals("3", campoFecha.getMes());
		Assert.assertEquals("2013", campoFecha.getAnio());


		Assert.assertEquals("18", campoFecha.getHoras());
		Assert.assertEquals("53", campoFecha.getMinutos());
		Assert.assertEquals("3", campoFecha.getSegundos());
	}

	@Test
	public void testFormatoAAAAMMDDHHMMSS(){
		CampoFecha campoFecha = new CampoFecha();
		campoFecha.setFormato(CustomDateFormat.DATE_FORMAT_AAAAMMDD_HHMMSS);
		campoFecha.setSeparador("/");
		campoFecha.setValor("2013/03/10 18:53:03");

		Assert.assertEquals("10", campoFecha.getDia());
		Assert.assertEquals("3", campoFecha.getMes());
		Assert.assertEquals("2013", campoFecha.getAnio());


		Assert.assertEquals("18", campoFecha.getHoras());
		Assert.assertEquals("53", campoFecha.getMinutos());
		Assert.assertEquals("3", campoFecha.getSegundos());
	}



}
