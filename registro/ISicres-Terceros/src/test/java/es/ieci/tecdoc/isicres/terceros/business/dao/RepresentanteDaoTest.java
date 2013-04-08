package es.ieci.tecdoc.isicres.terceros.business.dao;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.test.annotation.ExpectedException;
import org.springframework.test.context.ContextConfiguration;

import es.ieci.tecdoc.fwktd.test.db.AbstractDbUnitTransactionalJUnit4SpringContextTests;
import es.ieci.tecdoc.fwktd.test.db.annotation.DatasetLocation;
import es.ieci.tecdoc.isicres.terceros.business.vo.BaseDireccionVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.BaseTerceroVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.InteresadoVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.RepresentanteInteresadoVO;

/**
 *
 * @author IECISA
 *
 */
@DatasetLocation("data/dataset.xml")
@ContextConfiguration({ "/beans/ISicres-Terceros/datasource-beans.xml",
		"/beans/ISicres-Terceros/dao-beans.xml",
		"/beans/ISicres-Terceros/transaction-beans.xml" })
public class RepresentanteDaoTest extends
		AbstractDbUnitTransactionalJUnit4SpringContextTests {

	@Test
	public void getRepresentantes() {
		List<RepresentanteInteresadoVO> representantes = getRepresentanteDao()
				.getAll();

		Assert.assertNotNull(representantes);
		Assert.assertEquals(2, representantes.size());
	}

	@Test
	public void getRepresentanteValidado() {
		RepresentanteInteresadoVO representante = getRepresentanteDao()
				.get("1");

		Assert.assertNotNull(representante);
		Assert.assertEquals("1", representante.getId());
		Assert.assertEquals("REPRESENTANTE VALIDADO", representante.getNombre());
		Assert.assertNotNull(representante.getDireccionNotificacion());
		Assert.assertEquals("2", representante.getDireccionNotificacion()
				.getId());
		Assert.assertNotNull(representante.getRepresentante());
		Assert.assertEquals("1", representante.getRepresentante().getId());

		Assert.assertNotNull(representante.getInteresado());
		Assert.assertEquals("1", representante.getInteresado().getId());
		Assert.assertEquals("PEREZ PEREZ, PABLO", representante.getInteresado()
				.getNombre());
	}

	@Test
	public void getRepresentanteNoValidado() {
		RepresentanteInteresadoVO representante = getRepresentanteDao()
				.get("2");

		Assert.assertNotNull(representante);
		Assert.assertEquals("2", representante.getId());
		Assert.assertEquals("REPRESENTANTE NO VALIDADO",
				representante.getNombre());
		Assert.assertNotNull(representante.getDireccionNotificacion());
		Assert.assertEquals("2", representante.getDireccionNotificacion()
				.getId());
		Assert.assertEquals("RUE 13 DEL PERCEBE", representante
				.getDireccionNotificacion().getDireccion());
		Assert.assertNotNull(representante.getRepresentante());
		Assert.assertEquals("0", representante.getRepresentante().getId());

		Assert.assertNotNull(representante.getInteresado());
		Assert.assertEquals("1", representante.getInteresado().getId());
		Assert.assertEquals("PEREZ PEREZ, PABLO", representante.getInteresado()
				.getNombre());
	}

	@Test
	public void addRepresentante() {

		RepresentanteInteresadoVO representanteInteresado = new RepresentanteInteresadoVO();
		representanteInteresado.setId("1000");
		representanteInteresado.setNombre("NUEVO REPRESENTANTE");
		InteresadoVO interesado = new InteresadoVO();
		interesado.setId("1");
		representanteInteresado.setInteresado(interesado);
		BaseTerceroVO representante = new BaseTerceroVO();
		representante.setId("1");
		representanteInteresado.setRepresentante(representante);
		BaseDireccionVO direccionNotificacion = new BaseDireccionVO();
		direccionNotificacion.setId("2");
		direccionNotificacion.setDireccion("Direccion");
		representanteInteresado.setDireccionNotificacion(direccionNotificacion);

		getRepresentanteDao().save(representanteInteresado);

		Assert.assertEquals(3, getRepresentanteDao().count());

	}

	@Test
	public void updateRepresentante() {
		RepresentanteInteresadoVO representanteInteresado = getRepresentanteDao()
				.get("1");

		representanteInteresado
				.setNombre("Nombre modificado del representante");
		BaseDireccionVO direccionNotificacion = new BaseDireccionVO();
		direccionNotificacion.setId("1");
		representanteInteresado.setDireccionNotificacion(direccionNotificacion);
		BaseTerceroVO representante = new BaseTerceroVO();
		representante.setId("0");
		representanteInteresado.setRepresentante(representante);
		InteresadoVO interesado = new InteresadoVO();
		interesado.setId("11");
		representanteInteresado.setInteresado(interesado);

		getRepresentanteDao().update(representanteInteresado);

		RepresentanteInteresadoVO representanteInteresadoUpdated = getRepresentanteDao()
				.get("1");

		Assert.assertNotNull(representanteInteresadoUpdated);
		Assert.assertEquals("Nombre modificado del representante",
				representanteInteresadoUpdated.getNombre());
		Assert.assertEquals("1", representanteInteresadoUpdated.getId());
		Assert.assertEquals("1", representanteInteresadoUpdated
				.getDireccionNotificacion().getId());
		Assert.assertEquals("0", representanteInteresadoUpdated
				.getRepresentante().getId());
		Assert.assertEquals("11", representanteInteresadoUpdated
				.getInteresado().getId());
	}

	@Test
	public void deleteRepresentante() {
		getRepresentanteDao().delete("1");

		Assert.assertEquals(1, getRepresentanteDao().count());
	}

	@Test
	public void purgeRepresentantes() {
		getRepresentanteDao().deleteAll();

		Assert.assertEquals(0, getRepresentanteDao().count());
	}

	public RepresentanteInteresadoDao getRepresentanteDao() {
		return representanteDao;
	}

	public void setRepresentanteDao(RepresentanteInteresadoDao representanteDao) {
		this.representanteDao = representanteDao;
	}

	@Autowired
	protected RepresentanteInteresadoDao representanteDao;
}
