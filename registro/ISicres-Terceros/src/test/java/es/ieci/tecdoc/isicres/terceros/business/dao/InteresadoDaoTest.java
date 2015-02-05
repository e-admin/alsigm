package es.ieci.tecdoc.isicres.terceros.business.dao;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.ExpectedException;
import org.springframework.test.context.ContextConfiguration;

import es.ieci.tecdoc.fwktd.test.db.AbstractDbUnitTransactionalJUnit4SpringContextTests;
import es.ieci.tecdoc.fwktd.test.db.annotation.DatasetLocation;
import es.ieci.tecdoc.isicres.terceros.business.vo.InteresadoVO;

/**
 *
 * @author IECISA
 *
 */
@DatasetLocation("data/dataset.xml")
@ContextConfiguration({ "/beans/ISicres-Terceros/datasource-beans.xml",
		"/beans/ISicres-Terceros/dao-beans.xml",
		"/beans/ISicres-Terceros/transaction-beans.xml" })
public class InteresadoDaoTest extends
		AbstractDbUnitTransactionalJUnit4SpringContextTests {

	@Test
	public void getAllInteresados() {
		List<InteresadoVO> interesados = getInteresadoDao().getAll();

		Assert.assertNotNull(interesados);
		Assert.assertEquals(2, interesados.size());
	}

	@Test
	public void getInteresadoVO() {
		InteresadoVO interesado = getInteresadoDao().get("1");

		Assert.assertNotNull(interesado);
		Assert.assertEquals("1", interesado.getId());
		Assert.assertEquals("1", interesado.getIdLibro());
		Assert.assertEquals("1", interesado.getIdRegistro());
		Assert.assertEquals("PEREZ PEREZ, PABLO", interesado.getNombre());
		Assert.assertEquals(1, interesado.getOrden());
		Assert.assertFalse(interesado.isPrincipal());
		Assert.assertNotNull(interesado.getDireccionNotificacion());
		Assert.assertEquals("2", interesado.getDireccionNotificacion().getId());

		interesado = getInteresadoDao().get("11");

		Assert.assertNotNull(interesado);
		Assert.assertEquals("11", interesado.getId());
		Assert.assertEquals("ANTONIO ALCANTARA", interesado.getNombre());
		Assert.assertEquals(1, interesado.getOrden());
		Assert.assertFalse(interesado.isPrincipal());
		Assert.assertEquals("1", interesado.getIdLibro());
		Assert.assertEquals("4", interesado.getIdRegistro());
		Assert.assertEquals("0", interesado.getTercero().getId());
		Assert.assertNull(interesado.getDireccionNotificacion());
	}

	@Test
	public void countInteresados() {
		Assert.assertEquals(2, getInteresadoDao().count());
	}

	@Test
	public void getInteresadosRegistro() {
		List<InteresadoVO> interesados = getInteresadoDao().getInteresados("1",
				"1");

		Assert.assertNotNull(interesados);
	}

	@Test
	@ExpectedException(IllegalArgumentException.class)
	public void getInteresadosRegistroNullIdRegistro() {
		getInteresadoDao().getInteresados(null, null);
	}

	@Test
	@ExpectedException(IllegalArgumentException.class)
	public void getInteresadosRegistroNullIds() {
		getInteresadoDao().getInteresados("", "");
	}

	public InteresadoDao getInteresadoDao() {
		return interesadoDao;
	}

	public void setInteresadoDao(InteresadoDao interesadoDao) {
		this.interesadoDao = interesadoDao;
	}

	@Autowired
	protected InteresadoDao interesadoDao;
}
