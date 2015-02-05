package es.ieci.tecdoc.isicres.terceros.business.manager;

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
import es.ieci.tecdoc.isicres.terceros.business.vo.CiudadVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.DireccionFisicaVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.DireccionTelematicaVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.ProvinciaVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.TerceroValidadoFisicoVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.TerceroValidadoJuridicoVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.TerceroValidadoVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.TipoDireccionTelematicaVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.TipoDocumentoIdentificativoTerceroVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.enums.DireccionType;

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
public class TerceroManagerTest extends
		AbstractDbUnitTransactionalJUnit4SpringContextTests {

	@Test
	public void getAllTerceros() {
		List<TerceroValidadoVO> all = getTerceroManager().getAll();

		Assert.assertNotNull(all);
		Assert.assertEquals(4, all.size());
	}

	@Test
	public void countTerceros() {
		Assert.assertEquals(4, getTerceroManager().count());
	}

	@Test
	public void getTerceroFisico() {
		TerceroValidadoVO terceroValidadoVO = getTerceroManager().get("1");

		Assert.assertNotNull(terceroValidadoVO);
		Assert.assertTrue(terceroValidadoVO instanceof TerceroValidadoFisicoVO);

		TerceroValidadoFisicoVO terceroFisico = (TerceroValidadoFisicoVO) terceroValidadoVO;
		Assert.assertEquals("1", terceroFisico.getId());
		Assert.assertEquals("PABLO", terceroFisico.getNombre());
		Assert.assertEquals("PEREZ", terceroFisico.getApellido1());
		Assert.assertEquals("PEREZ", terceroFisico.getApellido2());
		Assert.assertEquals("12345678Z", terceroFisico.getNumeroDocumento());
		Assert.assertEquals("2", terceroFisico.getTipoDocumento().getId());

		Assert.assertEquals(3, terceroFisico.getDirecciones().size());
	}

	@Test
	public void getTerceroJuridico() {
		TerceroValidadoVO terceroValidadoVO = getTerceroManager().get("2");

		Assert.assertNotNull(terceroValidadoVO);
		Assert.assertTrue(terceroValidadoVO instanceof TerceroValidadoJuridicoVO);

		TerceroValidadoJuridicoVO terceroJuridico = (TerceroValidadoJuridicoVO) terceroValidadoVO;
		Assert.assertEquals("2", terceroJuridico.getId());
		Assert.assertEquals("IECI", terceroJuridico.getNombre());
		Assert.assertEquals("A28855260", terceroJuridico.getNumeroDocumento());
		Assert.assertEquals("1", terceroJuridico.getTipoDocumento().getId());

		Assert.assertEquals(0, terceroJuridico.getDirecciones().size());
	}

	@Test
	public void addTerceroFisico() {
		TerceroValidadoFisicoVO terceroValidado = new TerceroValidadoFisicoVO();
		terceroValidado.setNombre("Nombre");
		terceroValidado.setApellido1("Apellido1");
		terceroValidado.setApellido2("Apellido2");
		terceroValidado.setNumeroDocumento("12345678Z");
		TipoDocumentoIdentificativoTerceroVO tipoDocumento = new TipoDocumentoIdentificativoTerceroVO();
		tipoDocumento.setId("1");
		terceroValidado.setTipoDocumento(tipoDocumento);

		getTerceroManager().save(terceroValidado);
	}

	@Test
	public void addTerceroJuridico() {
		TerceroValidadoJuridicoVO terceroJuridico = new TerceroValidadoJuridicoVO();
		terceroJuridico.setNombre("Nombre");
		terceroJuridico.setNumeroDocumento("Q3310111");
		TipoDocumentoIdentificativoTerceroVO tipoDocumento = new TipoDocumentoIdentificativoTerceroVO();
		tipoDocumento.setId("1");
		terceroJuridico.setTipoDocumento(tipoDocumento);

		getTerceroManager().save(terceroJuridico);

		TerceroValidadoVO terceroSaved = getTerceroManager().get("4");

		Assert.assertTrue(terceroSaved instanceof TerceroValidadoJuridicoVO);

		terceroJuridico = (TerceroValidadoJuridicoVO) terceroSaved;
		Assert.assertEquals("4", terceroJuridico.getId());
		Assert.assertEquals("Nombre", terceroJuridico.getNombre());
		Assert.assertEquals("Q3310111", terceroJuridico.getNumeroDocumento());
		Assert.assertEquals("1", terceroJuridico.getTipoDocumento().getId());

		Assert.assertEquals(3, getTerceroManager().count());
	}

	@Test
	public void updateTerceroFisico() {
		TerceroValidadoVO terceroValidadoVO = getTerceroManager().get("1");

		List<BaseDireccionVO> direcciones = terceroValidadoVO.getDirecciones();
		// Se elimina una direccion
		direcciones.remove(0);
		// Se añade una direccion telematica
		DireccionTelematicaVO direccionTelematica = new DireccionTelematicaVO();
		direccionTelematica.setDireccion("Direccion telematica");
		direccionTelematica.setPrincipal(false);
		TipoDireccionTelematicaVO tipoDireccionTelematica = new TipoDireccionTelematicaVO();
		tipoDireccionTelematica.setId("1");
		direccionTelematica.setTipoDireccionTelematica(tipoDireccionTelematica);
		direcciones.add(direccionTelematica);
		// Se añade una direccion fisica
		DireccionFisicaVO direccionFisica = new DireccionFisicaVO();
		CiudadVO ciudad = new CiudadVO();
		ciudad.setNombre("Ciudad");
		direccionFisica.setCiudad(ciudad);
		direccionFisica.setCodigoPostal("12345");
		direccionFisica.setDireccion("Direccion");
		direccionFisica.setPrincipal(true);
		ProvinciaVO provincia = new ProvinciaVO();
		provincia.setNombre("Provincia");
		direccionFisica.setProvincia(provincia);
		direcciones.add(direccionFisica);

		getTerceroManager().update(terceroValidadoVO);

		TerceroValidadoVO terceroUpdated = getTerceroManager().get("1");
		Assert.assertNotNull(terceroUpdated);
		Assert.assertEquals(4, terceroUpdated.getDirecciones().size());

	}

	@Test
	public void deleteTercero() {
		getTerceroManager().delete("1");

		Assert.assertEquals(3, getTerceroManager().count());
	}

	@Test
	@ExpectedException(ObjectRetrievalFailureException.class)
	public void deleteUnknownTercero() {
		getTerceroManager().delete("1000");
	}

	@Test
	public void purgeTerceros() {
		List<? extends TerceroValidadoVO> terceros = getTerceroManager()
				.getAll();

		Assert.assertEquals(4, getTerceroManager().count());

		getTerceroManager().deleteAll(terceros);

		Assert.assertEquals(0, getTerceroManager().count());
	}

	public TerceroManager getTerceroManager() {
		return terceroManager;
	}

	public void setTerceroManager(TerceroManager terceroManager) {
		this.terceroManager = terceroManager;
	}

	@Autowired
	protected TerceroManager terceroManager;

}
