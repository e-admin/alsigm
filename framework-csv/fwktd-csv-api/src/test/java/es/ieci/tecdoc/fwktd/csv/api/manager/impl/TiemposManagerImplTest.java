package es.ieci.tecdoc.fwktd.csv.api.manager.impl;

import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import es.ieci.tecdoc.fwktd.csv.api.manager.TiemposManager;

@ContextConfiguration({
	"/beans/fwktd-csv-api-applicationContext.xml",
	"/beans/fwktd-csv-test-beans.xml" })
public class TiemposManagerImplTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private TiemposManager fwktd_csv_tiemposManagerImpl;

	public TiemposManager getTiemposManager() {
		return fwktd_csv_tiemposManagerImpl;
	}

	@Test
	public void testManager() {
		Assert.assertNotNull("El manager es nulo", getTiemposManager());
	}

	@Test
	public void testGetFechaActual() {
		Date fechaActual = getTiemposManager().getFechaActual();
		Assert.assertNotNull("La fecha es nula", fechaActual);
	}
}
