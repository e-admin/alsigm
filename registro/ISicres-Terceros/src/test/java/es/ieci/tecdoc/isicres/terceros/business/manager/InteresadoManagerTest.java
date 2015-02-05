package es.ieci.tecdoc.isicres.terceros.business.manager;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import es.ieci.tecdoc.fwktd.test.db.AbstractDbUnitTransactionalJUnit4SpringContextTests;
import es.ieci.tecdoc.fwktd.test.db.annotation.DatasetLocation;
import es.ieci.tecdoc.isicres.terceros.business.vo.BaseDireccionVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.BaseTerceroVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.InteresadoVO;

/**
 *
 * @author IECISA
 *
 */
@DatasetLocation("data/dataset.xml")
@ContextConfiguration({ "/beans/ISicres-Terceros/incrementer-beans.xml",
		"/beans/ISicres-Terceros/manager-beans.xml",
		"/beans/ISicres-Terceros/transaction-beans.xml",
		"/beans/ISicres-Terceros/dao-beans.xml",
		"/beans/ISicres-Terceros/datasource-beans.xml" })
public class InteresadoManagerTest extends
		AbstractDbUnitTransactionalJUnit4SpringContextTests {

	@Test
	public void getInteresados() {
		List<InteresadoVO> interesados = getInteresadoManager().getAll();

		Assert.assertNotNull(interesados);
		Assert.assertEquals(2, interesados.size());
	}

	@Test
	public void getInteresado() {
		InteresadoVO interesado = getInteresadoManager().get("1");

		Assert.assertNotNull(interesado);
		Assert.assertEquals("1", interesado.getId());
		Assert.assertEquals("PEREZ PEREZ, PABLO", interesado.getNombre());
		Assert.assertEquals(1, interesado.getOrden());
		Assert.assertEquals("1", interesado.getIdLibro());
		Assert.assertEquals("1", interesado.getIdRegistro());
		Assert.assertNotNull(interesado.getDireccionNotificacion());
		Assert.assertEquals("2", interesado.getDireccionNotificacion().getId());
		Assert.assertNotNull(interesado.getTercero());
		Assert.assertEquals("1", interesado.getTercero().getId());
	}

	@Test
	public void addInteresadoValidado() {
		InteresadoVO interesado = new InteresadoVO();
		interesado.setNombre("NUEVO INTERESADO");
		interesado.setOrden(2);
		interesado.setPrincipal(true);
		interesado.setIdLibro("1");
		interesado.setIdRegistro("1");
		BaseTerceroVO tercero = new BaseTerceroVO();
		tercero.setId("1");
		interesado.setTercero(tercero);
		BaseDireccionVO direccionNotificacion = new BaseDireccionVO();
		direccionNotificacion.setId("3");
		interesado.setDireccionNotificacion(direccionNotificacion);

		getInteresadoManager().save(interesado);

		Assert.assertEquals(3, getInteresadoManager().count());
	}

	public InteresadoManager getInteresadoManager() {
		return interesadoManager;
	}

	public void setInteresadoManager(InteresadoManager interesadoManager) {
		this.interesadoManager = interesadoManager;
	}

	@Autowired
	protected InteresadoManager interesadoManager;
}
