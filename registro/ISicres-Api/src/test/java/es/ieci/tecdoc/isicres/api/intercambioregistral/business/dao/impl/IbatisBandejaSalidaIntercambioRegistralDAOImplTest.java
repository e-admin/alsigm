package es.ieci.tecdoc.isicres.api.intercambioregistral.business.dao.impl;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import es.ieci.tecdoc.fwktd.server.pagination.PageInfo;
import es.ieci.tecdoc.fwktd.server.pagination.PaginatedArrayList;
import es.ieci.tecdoc.fwktd.test.db.AbstractDbUnitTransactionalJUnit4SpringContextTests;
import es.ieci.tecdoc.fwktd.test.db.annotation.DatasetLocation;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.dao.BandejaSalidaIntercambioRegistralDAO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.types.CriterioBusquedaIRSalidaEnum;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.types.OperadorCriterioBusquedaIREnum;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.BandejaSalidaItemVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.CriterioBusquedaIRSalidaVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.CriteriosBusquedaIRSalidaVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.EstadoIntercambioRegistralSalidaEnumVO;

@DatasetLocation("data/ISicres-Api/intercambioRegistral/dataset.xml")
@ContextConfiguration({ "/beans/transactionTest-beans.xml",
		"/beans/ISicres-Api/intercambioRegistral/dao-intercambio-beans.xml",
		"/beans/ISicres-Api/intercambioRegistral/datasource-intercambio-beans.xml",
		"/beans/datasourceTest-beans.xml"})
public class IbatisBandejaSalidaIntercambioRegistralDAOImplTest extends
		AbstractDbUnitTransactionalJUnit4SpringContextTests {

	@Autowired
	protected BandejaSalidaIntercambioRegistralDAO bandejaSalidaIntercambioRegistralDAO;

	@Test
	public void testFindAsientosRegistralesEnviados(){

		EstadoIntercambioRegistralSalidaEnumVO estado = EstadoIntercambioRegistralSalidaEnumVO.ENVIADO;

		CriteriosBusquedaIRSalidaVO criterios = new CriteriosBusquedaIRSalidaVO();

		List<BandejaSalidaItemVO> listaIntercambios = bandejaSalidaIntercambioRegistralDAO.findByCriterios(estado , criterios);

		Assert.assertNotNull(listaIntercambios);
		Assert.assertEquals(listaIntercambios.size(), 3);

	}

	@Test
	public void testFindAsientosRegistralesDevueltos(){

		EstadoIntercambioRegistralSalidaEnumVO estado = EstadoIntercambioRegistralSalidaEnumVO.DEVUELTO;

		CriteriosBusquedaIRSalidaVO criterios = new CriteriosBusquedaIRSalidaVO();

		List<BandejaSalidaItemVO> listaIntercambios = bandejaSalidaIntercambioRegistralDAO.findByCriterios(estado , criterios);

		Assert.assertNotNull(listaIntercambios);
		Assert.assertEquals(listaIntercambios.size(), 1);
	}


	@Test
	public void testFindAsientosRegistralesCriterioIdIntercambio(){

		EstadoIntercambioRegistralSalidaEnumVO estado = EstadoIntercambioRegistralSalidaEnumVO.ACEPTADO;

		CriteriosBusquedaIRSalidaVO criterios = new CriteriosBusquedaIRSalidaVO();
		CriterioBusquedaIRSalidaVO criterio = new CriterioBusquedaIRSalidaVO();
		criterio.setNombre(CriterioBusquedaIRSalidaEnum.ID_EXCHANGE_SIR);
		criterio.setOperador(OperadorCriterioBusquedaIREnum.EQUAL);
		criterio.setValor("O00001013_13_00000004");

		criterios.addCriterioVO(criterio);

		List<BandejaSalidaItemVO> listaIntercambios = bandejaSalidaIntercambioRegistralDAO.findByCriterios(estado , criterios);

		Assert.assertNotNull(listaIntercambios);
		Assert.assertEquals(listaIntercambios.size(), 1);
	}

	@Test
	public void testFindAsientosRegistralesCriterioIdRegistro(){

		EstadoIntercambioRegistralSalidaEnumVO estado = EstadoIntercambioRegistralSalidaEnumVO.ENVIADO;

		CriteriosBusquedaIRSalidaVO criterios = new CriteriosBusquedaIRSalidaVO();
		CriterioBusquedaIRSalidaVO criterio = new CriterioBusquedaIRSalidaVO();
		criterio.setNombre(CriterioBusquedaIRSalidaEnum.ID_FOLDER);
		criterio.setOperador(OperadorCriterioBusquedaIREnum.EQUAL);
		criterio.setValor(104);

		criterios.addCriterioVO(criterio);

		List<BandejaSalidaItemVO> listaIntercambios = bandejaSalidaIntercambioRegistralDAO.findByCriterios(estado , criterios);

		Assert.assertNotNull(listaIntercambios);
		Assert.assertEquals(listaIntercambios.size(), 1);
	}

	@Test
	public void testFindAsientosRegistralesCriterioUsername(){

		EstadoIntercambioRegistralSalidaEnumVO estado = EstadoIntercambioRegistralSalidaEnumVO.ENVIADO;

		CriteriosBusquedaIRSalidaVO criterios = new CriteriosBusquedaIRSalidaVO();
		CriterioBusquedaIRSalidaVO criterio = new CriterioBusquedaIRSalidaVO();
		criterio.setNombre(CriterioBusquedaIRSalidaEnum.USERNAME);
		criterio.setOperador(OperadorCriterioBusquedaIREnum.EQUAL);
		criterio.setValor("REGISTRO");

		criterios.addCriterioVO(criterio);

		List<BandejaSalidaItemVO> listaIntercambios = bandejaSalidaIntercambioRegistralDAO.findByCriterios(estado , criterios);

		Assert.assertNotNull(listaIntercambios);
		Assert.assertEquals(listaIntercambios.size(), 2);
	}

	@Test
	public void testFindAsientosRegistralesCriterios(){

		EstadoIntercambioRegistralSalidaEnumVO estado = EstadoIntercambioRegistralSalidaEnumVO.ENVIADO;

		CriteriosBusquedaIRSalidaVO criterios = new CriteriosBusquedaIRSalidaVO();
		CriterioBusquedaIRSalidaVO criterio = new CriterioBusquedaIRSalidaVO();
		criterio.setNombre(CriterioBusquedaIRSalidaEnum.USERNAME);
		criterio.setOperador(OperadorCriterioBusquedaIREnum.EQUAL);
		criterio.setValor("REGISTRO");

		CriterioBusquedaIRSalidaVO criterio2 = new CriterioBusquedaIRSalidaVO();
		criterio2.setNombre(CriterioBusquedaIRSalidaEnum.ID_ARCHIVADOR);
		criterio2.setOperador(OperadorCriterioBusquedaIREnum.IN);
		Integer[] archivadores = {1,2};
		criterio2.setValor(archivadores);

		criterios.addCriterioVO(criterio);
		criterios.addCriterioVO(criterio2);

		List<BandejaSalidaItemVO> listaIntercambios = bandejaSalidaIntercambioRegistralDAO.findByCriterios(estado , criterios);

		Assert.assertNotNull(listaIntercambios);
		Assert.assertEquals(listaIntercambios.size(), 2);
	}

	@Test
	public void testFindAsientosRegistralesPaginacion(){

		EstadoIntercambioRegistralSalidaEnumVO estado = EstadoIntercambioRegistralSalidaEnumVO.ENVIADO;

		CriteriosBusquedaIRSalidaVO criterios = new CriteriosBusquedaIRSalidaVO();
		CriterioBusquedaIRSalidaVO criterio = new CriterioBusquedaIRSalidaVO();
		criterio.setNombre(CriterioBusquedaIRSalidaEnum.USERNAME);
		criterio.setOperador(OperadorCriterioBusquedaIREnum.EQUAL);
		criterio.setValor("REGISTRO");

		PageInfo pageInfo = new PageInfo();

		pageInfo.setMaxNumItems(2);
		pageInfo.setObjectsPerPage(1);
		pageInfo.setPageNumber(1);

		criterios.addCriterioVO(criterio);

		PaginatedArrayList<BandejaSalidaItemVO> listaIntercambios = bandejaSalidaIntercambioRegistralDAO.findByCriterios(estado , criterios, pageInfo);

		Assert.assertEquals(listaIntercambios.getFullListSize(),2);
		Assert.assertNotNull(listaIntercambios);
		Assert.assertEquals(listaIntercambios.getList().size(), 1);
	}

	@Test
	public void testFindAsientosRegistralesOperadorNotEqual(){

		EstadoIntercambioRegistralSalidaEnumVO estado = EstadoIntercambioRegistralSalidaEnumVO.ENVIADO;

		CriteriosBusquedaIRSalidaVO criterios = new CriteriosBusquedaIRSalidaVO();

		CriterioBusquedaIRSalidaVO criterio = new CriterioBusquedaIRSalidaVO();
		criterio.setNombre(CriterioBusquedaIRSalidaEnum.USERNAME);
		criterio.setOperador(OperadorCriterioBusquedaIREnum.NOT_EQUAL);
		criterio.setValor("REGISTRO");
		criterios.addCriterioVO(criterio);

		List<BandejaSalidaItemVO> listaIntercambios = bandejaSalidaIntercambioRegistralDAO.findByCriterios(estado , criterios);

		Assert.assertEquals(listaIntercambios.size(), 1);
	}

	@Test
	public void testFindAsientosRegistralesOperadorGreater(){

		EstadoIntercambioRegistralSalidaEnumVO estado = EstadoIntercambioRegistralSalidaEnumVO.ENVIADO;

		CriteriosBusquedaIRSalidaVO criterios = new CriteriosBusquedaIRSalidaVO();

		CriterioBusquedaIRSalidaVO criterio = new CriterioBusquedaIRSalidaVO();
		criterio.setNombre(CriterioBusquedaIRSalidaEnum.STATE_DATE);
		criterio.setOperador(OperadorCriterioBusquedaIREnum.EQUAL_OR_GREATER_THAN);
		criterio.setValor(new Date());
		criterios.addCriterioVO(criterio);

		List<BandejaSalidaItemVO> listaIntercambios = bandejaSalidaIntercambioRegistralDAO.findByCriterios(estado , criterios);

		Assert.assertEquals(listaIntercambios.size(), 0);
	}

	@Test
	public void testFindAsientosRegistralesOperadorLess(){

		EstadoIntercambioRegistralSalidaEnumVO estado = EstadoIntercambioRegistralSalidaEnumVO.ENVIADO;

		CriteriosBusquedaIRSalidaVO criterios = new CriteriosBusquedaIRSalidaVO();

		CriterioBusquedaIRSalidaVO criterio = new CriterioBusquedaIRSalidaVO();
		criterio.setNombre(CriterioBusquedaIRSalidaEnum.STATE_DATE);
		criterio.setOperador(OperadorCriterioBusquedaIREnum.EQUAL_OR_LESS_THAN);
		criterio.setValor(new Date());
		criterios.addCriterioVO(criterio);

		List<BandejaSalidaItemVO> listaIntercambios = bandejaSalidaIntercambioRegistralDAO.findByCriterios(estado , criterios);

		Assert.assertEquals(listaIntercambios.size(), 3);
	}

	@Test
	public void testFindAsientosRegistralesOperadorLike(){

		EstadoIntercambioRegistralSalidaEnumVO estado = EstadoIntercambioRegistralSalidaEnumVO.ENVIADO;

		CriteriosBusquedaIRSalidaVO criterios = new CriteriosBusquedaIRSalidaVO();

		CriterioBusquedaIRSalidaVO criterio = new CriterioBusquedaIRSalidaVO();
		criterio.setNombre(CriterioBusquedaIRSalidaEnum.USERNAME);
		criterio.setOperador(OperadorCriterioBusquedaIREnum.LIKE);
		criterio.setValor("R");
		criterios.addCriterioVO(criterio);

		List<BandejaSalidaItemVO> listaIntercambios = bandejaSalidaIntercambioRegistralDAO.findByCriterios(estado , criterios);

		Assert.assertEquals(listaIntercambios.size(), 2);
	}

	@Test
	public void testFindAsientosRegistralesOperadorIN(){

		EstadoIntercambioRegistralSalidaEnumVO estado = EstadoIntercambioRegistralSalidaEnumVO.ENVIADO;

		CriteriosBusquedaIRSalidaVO criterios = new CriteriosBusquedaIRSalidaVO();

		CriterioBusquedaIRSalidaVO criterio2 = new CriterioBusquedaIRSalidaVO();
		criterio2.setNombre(CriterioBusquedaIRSalidaEnum.ID_ARCHIVADOR);
		criterio2.setOperador(OperadorCriterioBusquedaIREnum.IN);
		Integer[] archivadores = {1,2};
		criterio2.setValor(archivadores);

		criterios.addCriterioVO(criterio2);

		List<BandejaSalidaItemVO> listaIntercambios = bandejaSalidaIntercambioRegistralDAO.findByCriterios(estado , criterios);

		Assert.assertEquals(listaIntercambios.size(), 2);
	}

	@Test
	public void testFindAsientosRegistralesOrderBy(){

		EstadoIntercambioRegistralSalidaEnumVO estado = EstadoIntercambioRegistralSalidaEnumVO.ENVIADO;

		CriteriosBusquedaIRSalidaVO criterios = new CriteriosBusquedaIRSalidaVO();

		CriterioBusquedaIRSalidaVO criterio2 = new CriterioBusquedaIRSalidaVO();
		criterio2.setNombre(CriterioBusquedaIRSalidaEnum.ID_ARCHIVADOR);
		criterio2.setOperador(OperadorCriterioBusquedaIREnum.IN);
		Integer[] archivadores = {1,2,3};
		criterio2.setValor(archivadores);

		criterios.addCriterioVO(criterio2);

		criterios.addOrderBy(CriterioBusquedaIRSalidaEnum.ID_FOLDER);

		List<BandejaSalidaItemVO> listaIntercambios = bandejaSalidaIntercambioRegistralDAO.findByCriterios(estado , criterios);

		Assert.assertEquals(listaIntercambios.size(), 3);

		Assert.assertEquals(listaIntercambios.get(0).getId(), new Long(1000));
	}




}
