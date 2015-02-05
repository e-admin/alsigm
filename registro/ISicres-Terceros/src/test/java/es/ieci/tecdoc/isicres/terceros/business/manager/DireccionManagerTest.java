package es.ieci.tecdoc.isicres.terceros.business.manager;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.ExpectedException;
import org.springframework.test.context.ContextConfiguration;

import es.ieci.tecdoc.fwktd.test.db.annotation.DatasetLocation;
import es.ieci.tecdoc.isicres.terceros.business.vo.BaseDireccionVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.DireccionFisicaVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.TerceroValidadoFisicoVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.TerceroValidadoVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.enums.DireccionType;

@DatasetLocation("data/dataset.xml")
@ContextConfiguration({ "/beans/ISicres-Terceros/incrementer-beans.xml",
		"/beans/ISicres-Terceros/manager-beans.xml",
		"/beans/ISicres-Terceros/transaction-beans.xml",
		"/beans/ISicres-Terceros/dao-beans.xml",
		"/beans/ISicres-Terceros/datasource-beans.xml" })
public class DireccionManagerTest {
	@Test
	public void deleteDireccionesTercero() {
		TerceroValidadoVO tercero = new TerceroValidadoFisicoVO();
		tercero.setId("1");
		getDireccionManager().deleteDirecciones(tercero);

		TerceroValidadoVO terceroValidadoVO = getTerceroManager().get("1");
		Assert.assertNotNull(terceroValidadoVO);
		Assert.assertEquals(0, terceroValidadoVO.getDirecciones().size());
	}

	@Test
	public void deleteDireccionTercero() {
		TerceroValidadoVO tercero = new TerceroValidadoFisicoVO();
		tercero.setId("1");
		DireccionFisicaVO direccion = new DireccionFisicaVO();
		direccion.setId("1");

		getDireccionManager().deleteDireccion(direccion, tercero);

		tercero = getTerceroManager().get("1");

		Assert.assertNotNull(tercero);
		Assert.assertEquals(2, tercero.getDirecciones().size());
	}

	@Test
	@ExpectedException(IllegalArgumentException.class)
	public void deleteNullDireccionTercero() {
		TerceroValidadoVO tercero = new TerceroValidadoFisicoVO();
		tercero.setId("1");
		DireccionFisicaVO direccion = new DireccionFisicaVO();

		getDireccionManager().deleteDireccion(direccion, tercero);
	}

	@Test
	public void getDireccionesTercero() {
		TerceroValidadoVO tercero = new TerceroValidadoFisicoVO();
		tercero.setId("1");

		List<? extends BaseDireccionVO> direccionesFisicas = getDireccionManager()
				.getDirecciones(tercero, DireccionType.FISICA);
		Assert.assertNotNull(direccionesFisicas);
		Assert.assertEquals(2, direccionesFisicas.size());

		List<? extends BaseDireccionVO> direccionesTelematicas = getDireccionManager()
				.getDirecciones(tercero, DireccionType.TELEMATICA);
		Assert.assertNotNull(direccionesTelematicas);
		Assert.assertEquals(1, direccionesTelematicas.size());
	}

	public DireccionManager getDireccionManager() {
		return direccionManager;
	}

	public void setDireccionManager(DireccionManager direccionManager) {
		this.direccionManager = direccionManager;
	}

	public TerceroManager getTerceroManager() {
		return terceroManager;
	}

	public void setTerceroManager(TerceroManager terceroManager) {
		this.terceroManager = terceroManager;
	}

	@Autowired
	protected DireccionManager direccionManager;

	@Autowired
	protected TerceroManager terceroManager;
}
