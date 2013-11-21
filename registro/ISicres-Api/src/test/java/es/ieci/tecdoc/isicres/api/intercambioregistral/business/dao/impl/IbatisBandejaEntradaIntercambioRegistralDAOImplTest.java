package es.ieci.tecdoc.isicres.api.intercambioregistral.business.dao.impl;

import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import es.ieci.tecdoc.fwktd.server.pagination.PageInfo;
import es.ieci.tecdoc.fwktd.server.pagination.PaginatedArrayList;
import es.ieci.tecdoc.fwktd.test.db.AbstractDbUnitTransactionalJUnit4SpringContextTests;
import es.ieci.tecdoc.fwktd.test.db.annotation.DatasetLocation;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.dao.BandejaEntradaIntercambioRegistralDAO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.types.CriterioBusquedaIREntradaEnum;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.types.OperadorCriterioBusquedaIREnum;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.BandejaEntradaItemVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.CriterioBusquedaIREntradaVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.CriteriosBusquedaIREntradaVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.EstadoIntercambioRegistralEntradaEnumVO;

@DatasetLocation("data/ISicres-Api/intercambioRegistral/dataset.xml")
@ContextConfiguration({ "/beans/transactionTest-beans.xml",
		"/beans/ISicres-Api/intercambioRegistral/dao-intercambio-beans.xml",
		"/beans/ISicres-Api/intercambioRegistral/datasource-intercambio-beans.xml",
		"/beans/datasourceTest-beans.xml"})
public class IbatisBandejaEntradaIntercambioRegistralDAOImplTest extends
		AbstractDbUnitTransactionalJUnit4SpringContextTests {

	@Autowired
	protected BandejaEntradaIntercambioRegistralDAO bandejaEntradaIntercambioRegistralDAO;

	@Test
	public void testFindAsientosRegistralesAceptados(){

		EstadoIntercambioRegistralEntradaEnumVO estado = EstadoIntercambioRegistralEntradaEnumVO.ACEPTADO;

		CriteriosBusquedaIREntradaVO criterios = new CriteriosBusquedaIREntradaVO();

		List<BandejaEntradaItemVO> listaIntercambios = bandejaEntradaIntercambioRegistralDAO.findByCriterios(estado , criterios);

		Assert.assertEquals(listaIntercambios.size(), 3);

	}

	@Test
	public void testFindAsientosRegistralesRechazados(){

		EstadoIntercambioRegistralEntradaEnumVO estado = EstadoIntercambioRegistralEntradaEnumVO.RECHAZADO;

		CriteriosBusquedaIREntradaVO criterios = new CriteriosBusquedaIREntradaVO();

		List<BandejaEntradaItemVO> listaIntercambios = bandejaEntradaIntercambioRegistralDAO.findByCriterios(estado , criterios);

		Assert.assertEquals(listaIntercambios.size(), 1);
	}

	@Test
	public void testFindAsientosRegistralesReenviados(){

		EstadoIntercambioRegistralEntradaEnumVO estado = EstadoIntercambioRegistralEntradaEnumVO.REENVIADO;

		CriteriosBusquedaIREntradaVO criterios = new CriteriosBusquedaIREntradaVO();

		List<BandejaEntradaItemVO> listaIntercambios = bandejaEntradaIntercambioRegistralDAO.findByCriterios(estado , criterios);

		Assert.assertEquals(listaIntercambios.size(), 1);
	}

	@Test
	public void testFindAsientosRegistralesCriterioIdIntercambio(){

		EstadoIntercambioRegistralEntradaEnumVO estado = EstadoIntercambioRegistralEntradaEnumVO.ACEPTADO;

		CriteriosBusquedaIREntradaVO criterios = new CriteriosBusquedaIREntradaVO();
		CriterioBusquedaIREntradaVO criterio = new CriterioBusquedaIREntradaVO();
		criterio.setNombre(CriterioBusquedaIREntradaEnum.ID_EXCHANGE_SIR);
		criterio.setOperador(OperadorCriterioBusquedaIREnum.EQUAL);
		criterio.setValor("O00001013_13_00000004");

		criterios.addCriterioVO(criterio);

		List<BandejaEntradaItemVO> listaIntercambios = bandejaEntradaIntercambioRegistralDAO.findByCriterios(estado , criterios);

		Assert.assertEquals(listaIntercambios.size(), 1);
	}

	@Test
	public void testFindAsientosRegistralesCriterioIdRegistro(){

		EstadoIntercambioRegistralEntradaEnumVO estado = EstadoIntercambioRegistralEntradaEnumVO.ACEPTADO;

		CriteriosBusquedaIREntradaVO criterios = new CriteriosBusquedaIREntradaVO();
		CriterioBusquedaIREntradaVO criterio = new CriterioBusquedaIREntradaVO();
		criterio.setNombre(CriterioBusquedaIREntradaEnum.ID_FOLDER);
		criterio.setOperador(OperadorCriterioBusquedaIREnum.EQUAL);
		criterio.setValor(104);

		criterios.addCriterioVO(criterio);

		List<BandejaEntradaItemVO> listaIntercambios = bandejaEntradaIntercambioRegistralDAO.findByCriterios(estado , criterios);

		Assert.assertEquals(listaIntercambios.size(), 1);
	}

	@Test
	public void testFindAsientosRegistralesCriterioUsername(){

		EstadoIntercambioRegistralEntradaEnumVO estado = EstadoIntercambioRegistralEntradaEnumVO.ACEPTADO;

		CriteriosBusquedaIREntradaVO criterios = new CriteriosBusquedaIREntradaVO();
		CriterioBusquedaIREntradaVO criterio = new CriterioBusquedaIREntradaVO();
		criterio.setNombre(CriterioBusquedaIREntradaEnum.USERNAME);
		criterio.setOperador(OperadorCriterioBusquedaIREnum.EQUAL);
		criterio.setValor("REGISTRO");

		criterios.addCriterioVO(criterio);

		List<BandejaEntradaItemVO> listaIntercambios = bandejaEntradaIntercambioRegistralDAO.findByCriterios(estado , criterios);

		Assert.assertEquals(listaIntercambios.size(), 2);
	}

	@Test
	public void testFindAsientosRegistralesCriterios(){

		EstadoIntercambioRegistralEntradaEnumVO estado = EstadoIntercambioRegistralEntradaEnumVO.ACEPTADO;

		CriteriosBusquedaIREntradaVO criterios = new CriteriosBusquedaIREntradaVO();
		CriterioBusquedaIREntradaVO criterio = new CriterioBusquedaIREntradaVO();
		criterio.setNombre(CriterioBusquedaIREntradaEnum.USERNAME);
		criterio.setOperador(OperadorCriterioBusquedaIREnum.EQUAL);
		criterio.setValor("REGISTRO");

		CriterioBusquedaIREntradaVO criterio2 = new CriterioBusquedaIREntradaVO();
		criterio2.setNombre(CriterioBusquedaIREntradaEnum.ID_ARCHIVADOR);
		criterio2.setOperador(OperadorCriterioBusquedaIREnum.IN);
		Integer[] archivadores = {1,2};
		criterio2.setValor(archivadores);

		criterios.addCriterioVO(criterio);
		criterios.addCriterioVO(criterio2);

		List<BandejaEntradaItemVO> listaIntercambios = bandejaEntradaIntercambioRegistralDAO.findByCriterios(estado , criterios);

		Assert.assertEquals(listaIntercambios.size(), 2);
	}

	@Test
	public void testFindAsientosRegistralesPaginacion(){

		EstadoIntercambioRegistralEntradaEnumVO estado = EstadoIntercambioRegistralEntradaEnumVO.ACEPTADO;

		CriteriosBusquedaIREntradaVO criterios = new CriteriosBusquedaIREntradaVO();
		CriterioBusquedaIREntradaVO criterio = new CriterioBusquedaIREntradaVO();
		criterio.setNombre(CriterioBusquedaIREntradaEnum.USERNAME);
		criterio.setOperador(OperadorCriterioBusquedaIREnum.EQUAL);
		criterio.setValor("REGISTRO");

		PageInfo pageInfo = new PageInfo();

		pageInfo.setMaxNumItems(5);
		pageInfo.setObjectsPerPage(1);
		pageInfo.setPageNumber(1);

		criterios.addCriterioVO(criterio);

		PaginatedArrayList<BandejaEntradaItemVO> listaIntercambios = bandejaEntradaIntercambioRegistralDAO.findByCriterios(estado , criterios, pageInfo);

		Assert.assertEquals(listaIntercambios.getFullListSize(),2);
		Assert.assertEquals(listaIntercambios.getList().size(), 1);
	}

	@Test
	public void testFindAsientosRegistralesPendientes(){

		EstadoIntercambioRegistralEntradaEnumVO estado = EstadoIntercambioRegistralEntradaEnumVO.PENDIENTE;

		CriteriosBusquedaIREntradaVO criterios = new CriteriosBusquedaIREntradaVO();

		List<BandejaEntradaItemVO> listaIntercambios = bandejaEntradaIntercambioRegistralDAO.findByCriterios(estado , criterios);

		Assert.assertEquals(listaIntercambios.size(), 0);

	}

	@Test
	public void testFindAsientosRegistralesOperadorNotEqual(){

		EstadoIntercambioRegistralEntradaEnumVO estado = EstadoIntercambioRegistralEntradaEnumVO.ACEPTADO;

		CriteriosBusquedaIREntradaVO criterios = new CriteriosBusquedaIREntradaVO();

		CriterioBusquedaIREntradaVO criterio = new CriterioBusquedaIREntradaVO();
		criterio.setNombre(CriterioBusquedaIREntradaEnum.USERNAME);
		criterio.setOperador(OperadorCriterioBusquedaIREnum.NOT_EQUAL);
		criterio.setValor("REGISTRO");
		criterios.addCriterioVO(criterio);

		List<BandejaEntradaItemVO> listaIntercambios = bandejaEntradaIntercambioRegistralDAO.findByCriterios(estado , criterios);

		Assert.assertEquals(listaIntercambios.size(), 1);
	}

	@Test
	public void testFindAsientosRegistralesOperadorGreater(){

		EstadoIntercambioRegistralEntradaEnumVO estado = EstadoIntercambioRegistralEntradaEnumVO.ACEPTADO;

		CriteriosBusquedaIREntradaVO criterios = new CriteriosBusquedaIREntradaVO();

		CriterioBusquedaIREntradaVO criterio = new CriterioBusquedaIREntradaVO();
		criterio.setNombre(CriterioBusquedaIREntradaEnum.STATE_DATE);
		criterio.setOperador(OperadorCriterioBusquedaIREnum.EQUAL_OR_GREATER_THAN);
		criterio.setValor(new Date());
		criterios.addCriterioVO(criterio);

		List<BandejaEntradaItemVO> listaIntercambios = bandejaEntradaIntercambioRegistralDAO.findByCriterios(estado , criterios);

		Assert.assertEquals(listaIntercambios.size(), 0);
	}

	@Test
	public void testFindAsientosRegistralesOperadorLess(){

		EstadoIntercambioRegistralEntradaEnumVO estado = EstadoIntercambioRegistralEntradaEnumVO.ACEPTADO;

		CriteriosBusquedaIREntradaVO criterios = new CriteriosBusquedaIREntradaVO();

		CriterioBusquedaIREntradaVO criterio = new CriterioBusquedaIREntradaVO();
		criterio.setNombre(CriterioBusquedaIREntradaEnum.STATE_DATE);
		criterio.setOperador(OperadorCriterioBusquedaIREnum.EQUAL_OR_LESS_THAN);
		criterio.setValor(new Date());
		criterios.addCriterioVO(criterio);

		List<BandejaEntradaItemVO> listaIntercambios = bandejaEntradaIntercambioRegistralDAO.findByCriterios(estado , criterios);

		Assert.assertEquals(listaIntercambios.size(), 3);
	}

	@Test
	public void testFindAsientosRegistralesOperadorLike(){

		EstadoIntercambioRegistralEntradaEnumVO estado = EstadoIntercambioRegistralEntradaEnumVO.ACEPTADO;

		CriteriosBusquedaIREntradaVO criterios = new CriteriosBusquedaIREntradaVO();

		CriterioBusquedaIREntradaVO criterio = new CriterioBusquedaIREntradaVO();
		criterio.setNombre(CriterioBusquedaIREntradaEnum.USERNAME);
		criterio.setOperador(OperadorCriterioBusquedaIREnum.LIKE);
		criterio.setValor("R");
		criterios.addCriterioVO(criterio);

		List<BandejaEntradaItemVO> listaIntercambios = bandejaEntradaIntercambioRegistralDAO.findByCriterios(estado , criterios);

		Assert.assertEquals(listaIntercambios.size(), 2);
	}

	@Test
	public void testFindAsientosRegistralesOperadorIN(){

		EstadoIntercambioRegistralEntradaEnumVO estado = EstadoIntercambioRegistralEntradaEnumVO.ACEPTADO;

		CriteriosBusquedaIREntradaVO criterios = new CriteriosBusquedaIREntradaVO();

		CriterioBusquedaIREntradaVO criterio2 = new CriterioBusquedaIREntradaVO();
		criterio2.setNombre(CriterioBusquedaIREntradaEnum.ID_ARCHIVADOR);
		criterio2.setOperador(OperadorCriterioBusquedaIREnum.IN);
		Integer[] archivadores = {1,2};
		criterio2.setValor(archivadores);

		criterios.addCriterioVO(criterio2);

		List<BandejaEntradaItemVO> listaIntercambios = bandejaEntradaIntercambioRegistralDAO.findByCriterios(estado , criterios);

		Assert.assertEquals(listaIntercambios.size(), 2);
	}

	@Test
	public void testFindAsientosRegistralesOrderBy(){

		EstadoIntercambioRegistralEntradaEnumVO estado = EstadoIntercambioRegistralEntradaEnumVO.ACEPTADO;

		CriteriosBusquedaIREntradaVO criterios = new CriteriosBusquedaIREntradaVO();

		CriterioBusquedaIREntradaVO criterio2 = new CriterioBusquedaIREntradaVO();
		criterio2.setNombre(CriterioBusquedaIREntradaEnum.ID_ARCHIVADOR);
		criterio2.setOperador(OperadorCriterioBusquedaIREnum.IN);
		Integer[] archivadores = {1,2,3};
		criterio2.setValor(archivadores);

		criterios.addCriterioVO(criterio2);

		criterios.addOrderBy(CriterioBusquedaIREntradaEnum.ID_FOLDER);

		List<BandejaEntradaItemVO> listaIntercambios = bandejaEntradaIntercambioRegistralDAO.findByCriterios(estado , criterios);

		Assert.assertEquals(listaIntercambios.size(), 3);

		Assert.assertEquals(listaIntercambios.get(0).getId(), new Long(1000));
	}
}
