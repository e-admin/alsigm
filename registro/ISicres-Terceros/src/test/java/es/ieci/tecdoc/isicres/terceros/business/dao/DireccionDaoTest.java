package es.ieci.tecdoc.isicres.terceros.business.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.test.annotation.ExpectedException;
import org.springframework.test.context.ContextConfiguration;

import es.ieci.tecdoc.fwktd.test.db.AbstractDbUnitTransactionalJUnit4SpringContextTests;
import es.ieci.tecdoc.fwktd.test.db.annotation.DatasetLocation;
import es.ieci.tecdoc.isicres.terceros.business.vo.BaseDireccionVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.CiudadVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.DireccionFisicaVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.DireccionTelematicaVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.ProvinciaVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.TerceroValidadoFisicoVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.TerceroValidadoVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.TipoDireccionTelematicaVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.enums.DireccionType;

/**
 *
 * @author IECISA
 *
 */
@DatasetLocation("data/dataset.xml")
@ContextConfiguration({ "/beans/ISicres-Terceros/transaction-beans.xml",
		"/beans/ISicres-Terceros/datasource-beans.xml",
		"/beans/ISicres-Terceros/dao-beans.xml" })
public class DireccionDaoTest extends
		AbstractDbUnitTransactionalJUnit4SpringContextTests {

	@Test
	public void getAllDirecciones() {
		List<BaseDireccionVO> all = getDireccionDao().getAll();

		Assert.assertNotNull(all);
		Assert.assertEquals(3, all.size());
	}

	@Test
	public void getDireccionFisica() {
		BaseDireccionVO direccion = getDireccionDao().get("2");

		Assert.assertNotNull(direccion);
		Assert.assertTrue(direccion instanceof DireccionFisicaVO);
	}

	@Test
	public void getDireccionTelematica() {
		BaseDireccionVO direccion = getDireccionDao().get("1");

		Assert.assertNotNull(direccion);
		Assert.assertTrue(direccion instanceof DireccionTelematicaVO);
	}

	@Test
	public void addDireccionFisica() {
		DireccionFisicaVO direccion = new DireccionFisicaVO();
		direccion.setId("1000");
		CiudadVO ciudad = new CiudadVO();
		ciudad.setNombre("Oviedo");
		direccion.setCiudad(ciudad);
		direccion.setCodigoPostal("33001");
		direccion.setDireccion("Calle ... ");
		direccion.setPrincipal(true);
		ProvinciaVO provincia = new ProvinciaVO();
		provincia.setNombre("Asturias");
		direccion.setProvincia(provincia);
		TerceroValidadoVO tercero = new TerceroValidadoVO();
		tercero.setId("1");
		direccion.setTercero(tercero);

		BaseDireccionVO save = getDireccionDao().save(direccion);

		Assert.assertNotNull(save);

		Assert.assertEquals(4, getDireccionDao().getAll().size());
		DireccionFisicaVO direccionSaved = (DireccionFisicaVO) getDireccionDao()
				.get("1000");

		Assert.assertNotNull(direccionSaved);
		Assert.assertEquals("1000", direccionSaved.getId());
		Assert.assertEquals(DireccionType.FISICA, direccionSaved.getTipo());
		Assert.assertEquals("Oviedo", direccion.getCiudad().getNombre());
		Assert.assertEquals("33001", direccion.getCodigoPostal());
		Assert.assertEquals("Calle ... ", direccion.getDireccion());
		Assert.assertTrue(direccion.isPrincipal());
		Assert.assertEquals("Asturias", direccion.getProvincia().getNombre());
	}

	@Test
	public void addDireccionTelematica() {
		DireccionTelematicaVO direccion = new DireccionTelematicaVO();
		direccion.setId("1000");
		direccion.setDireccion("985123456");
		direccion.setPrincipal(false);
		TerceroValidadoVO tercero = new TerceroValidadoVO();
		tercero.setId("2");
		direccion.setTercero(tercero);
		TipoDireccionTelematicaVO tipoDireccionTelematica = new TipoDireccionTelematicaVO();
		tipoDireccionTelematica.setId("1");
		direccion.setTipoDireccionTelematica(tipoDireccionTelematica);

		getDireccionDao().save(direccion);

		Assert.assertEquals(4, getDireccionDao().getAll().size());
		DireccionTelematicaVO direccionSaved = (DireccionTelematicaVO) getDireccionDao()
				.get("1000");

		Assert.assertNotNull(direccionSaved);
		Assert.assertEquals("1000", direccion.getId());
		Assert.assertEquals("985123456", direccion.getDireccion());
		Assert.assertFalse(direccion.isPrincipal());
		Assert.assertEquals("2", tercero.getId());
		Assert.assertEquals("1", direccion.getTipoDireccionTelematica().getId());
	}

	@Test
	public void updateDireccionFisica() {
		DireccionTelematicaVO direccion = (DireccionTelematicaVO) getDireccionDao()
				.get("1");

		Assert.assertNotNull(direccion);
		direccion.setDireccion("admin@localhost.org");
		TipoDireccionTelematicaVO tipoDireccionTelematica = new TipoDireccionTelematicaVO();
		tipoDireccionTelematica.setId("2");
		direccion.setTipoDireccionTelematica(tipoDireccionTelematica);

		getDireccionDao().update(direccion);

		DireccionTelematicaVO direccionUpdated = (DireccionTelematicaVO) getDireccionDao()
				.get("1");
		Assert.assertNotNull(direccionUpdated);
		Assert.assertEquals("admin@localhost.org",
				direccionUpdated.getDireccion());
		Assert.assertEquals("2", direccionUpdated.getTipoDireccionTelematica()
				.getId());
	}

	@Test
	public void deleteDireccionFisica() {
		getDireccionDao().delete("2");

		Assert.assertEquals(2, getDireccionDao().count());
	}

	@Test
	@ExpectedException(ObjectRetrievalFailureException.class)
	public void deleteUnknownDireccionFisica() {
		getDireccionDao().delete("10");
	}

	@Test
	public void deleteDireccionTelematica() {
		getDireccionDao().delete("1");

		Assert.assertEquals(2, getDireccionDao().count());
	}

	@Test
	public void deleteDireccionesTercero() {
		TerceroValidadoFisicoVO tercero = new TerceroValidadoFisicoVO();
		tercero.setId("1");

		getDireccionDao().deleteAll(tercero);
	}

	@Test
	@ExpectedException(DataIntegrityViolationException.class)
	public void addDuplicatedDireccionTelematica() {
		DireccionTelematicaVO direccion = new DireccionTelematicaVO();
		direccion.setId("1");

		getDireccionDao().save(direccion);
	}

	@Test
	@ExpectedException(DataIntegrityViolationException.class)
	public void addDireccionTelematicaWithoutId() {
		DireccionTelematicaVO direccion = new DireccionTelematicaVO();

		getDireccionDao().save(direccion);
	}

	@Test
	public void getAllDireccionesTelematicas() {
		List<DireccionTelematicaVO> all = getDireccionDao()
				.getAllDireccionesTelematicas();

		Assert.assertNotNull(all);
		Assert.assertEquals(1, all.size());
	}

	@Test
	public void getAllDireccionesFisicas() {
		List<DireccionFisicaVO> all = getDireccionDao()
				.getAllDireccionesFisicas();

		Assert.assertNotNull(all);
		Assert.assertEquals(2, all.size());
	}

	@Test
	@ExpectedException(ObjectRetrievalFailureException.class)
	public void getUnknownDireccionTelematica() {
		getDireccionDao().get("5000");
	}

	public DireccionDao getDireccionDao() {
		return direccionDao;
	}

	public void setDireccionDao(DireccionDao direccionDao) {
		this.direccionDao = direccionDao;
	}

	// Members
	@Autowired
	protected DireccionDao direccionDao;
}
