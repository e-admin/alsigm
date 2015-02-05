package es.ieci.tecdoc.fwktd.audit.api.service.impl;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;

import es.ieci.tecdoc.fwktd.audit.api.constants.CamposTrazaAuditoriaConstants;
import es.ieci.tecdoc.fwktd.audit.core.exception.AuditoriaException;
import es.ieci.tecdoc.fwktd.audit.core.service.AuditoriaService;
import es.ieci.tecdoc.fwktd.audit.core.vo.AppAuditoriaVO;
import es.ieci.tecdoc.fwktd.audit.core.vo.TrazaAuditoriaVO;
import es.ieci.tecdoc.fwktd.audit.core.vo.seach.CriteriaVO;
import es.ieci.tecdoc.fwktd.audit.core.vo.seach.FilterVO;
import es.ieci.tecdoc.fwktd.audit.core.vo.seach.JdbcTypesEnum;
import es.ieci.tecdoc.fwktd.audit.core.vo.seach.OperatorEnum;
import es.ieci.tecdoc.fwktd.audit.core.vo.seach.PaginatedList;
import es.ieci.tecdoc.fwktd.audit.core.vo.seach.PaginationContext;
import es.ieci.tecdoc.fwktd.test.db.AbstractDbUnitTransactionalJUnit4SpringContextTests;
import es.ieci.tecdoc.fwktd.test.db.annotation.DatasetLocation;

/**
 * 
 * @author IECISA
 * 
 */
@DatasetLocation("data/dataset.xml")
@ContextConfiguration({ "/beans/fwktd-audit-api/fwktd-audit-api-beans.xml",
		"/beans/fwktd-audit-api/fwktd-audit-api-incrementer-beans.xml",
		"/beans/fwktd-audit-api/fwktd-audit-api-manager-beans.xml",
		"/beans/fwktd-audit-api/fwktd-audit-api-dao-beans.xml",
		"/beans/fwktd-audit-api/fwktd-audit-api-transaction-beans.xml",

		"/beans/fwktd-audit-api-applicationContext.xml",
		"/beans/fwktd-audit-api/transaction-beans.xml",
		"/beans/fwktd-audit-api/datasource-beans.xml" })
public class ServicioAuditoriaImplTest extends
		AbstractDbUnitTransactionalJUnit4SpringContextTests {

	@Autowired
	@Qualifier("main")
	protected AuditoriaService auditoriaService;

	public AuditoriaService getAuditoriaService() {
		return auditoriaService;
	}

	public void setAuditoriaService(AuditoriaService auditoriaService) {
		this.auditoriaService = auditoriaService;
	}

	@Test
	public void testSaveOk() {

		TrazaAuditoriaVO traza1 = new TrazaAuditoriaVO();
		traza1.setAppId(new Long(1));
		traza1.setEventType(new Long(1));
		traza1.setEventDate(new Date());
		traza1.setUserId("1");
		traza1.setUserName("user name 1");
		traza1.setUserHostName("user host name");
		traza1.setUserIp("10.228.20.1");
		traza1.setObjectType("2");

		auditoriaService.audit(traza1);
		TrazaAuditoriaVO trazaSaved = auditoriaService.getTraza("1000");

		Assert.assertEquals(trazaSaved.getId(), "1000");

	}

	@Test(expected = es.ieci.tecdoc.fwktd.audit.core.exception.AuditoriaException.class)
	public void testSaveKO() {

		TrazaAuditoriaVO traza1 = new TrazaAuditoriaVO();
		traza1.setAppId(new Long(1));
		traza1.setEventType(new Long(1));
		traza1.setEventDate(new Date());

		// userId = null para forzar el fallo
		traza1.setUserId(null);

		traza1.setUserName("user name 1");
		traza1.setUserHostName("user host name");

		auditoriaService.audit(traza1);

	}

	@Test(expected = es.ieci.tecdoc.fwktd.audit.core.exception.AuditoriaException.class)
	public void testGetKO() {

		auditoriaService.getTraza("100008");

	}

	@Test(expected = es.ieci.tecdoc.fwktd.audit.core.exception.AuditoriaException.class)
	public void testGetKO2() {

		auditoriaService.getTraza(null);

	}

	@Test
	public void testFindByCriteria() {
		CriteriaVO criteria = new CriteriaVO();
		FilterVO filter = new FilterVO();
		filter.setField(CamposTrazaAuditoriaConstants.APP_ID_FIELD);
		filter.setOperator(OperatorEnum.ES_IGUAL);
		filter.setValue("1");
		filter.setJdbcType(JdbcTypesEnum.BIG_INT);

		FilterVO filter2 = new FilterVO();
		filter2.setField(CamposTrazaAuditoriaConstants.USER_NAME_FIELD);
		filter2.setOperator(OperatorEnum.NO_ES_IGUAL);
		filter2.setValue("1");
		
		FilterVO filter3 = new FilterVO();
		filter2.setField(CamposTrazaAuditoriaConstants.USER_ID_FIELD);
		filter2.setOperator(OperatorEnum.NO_ES_IGUAL);
		filter2.setValue("3");

		List<FilterVO> filters = new LinkedList<FilterVO>();
		filters.add(filter);
		filters.add(filter2);
		criteria.setFilters(filters);
		List<TrazaAuditoriaVO> list = auditoriaService.findByCriteria(criteria);
		Assert.assertEquals(list.size(), 1);
	}

	@Test
	public void testFindByCriteriaPaginated() {
		CriteriaVO criteria = new CriteriaVO();
		FilterVO filter = new FilterVO();
		filter.setField(CamposTrazaAuditoriaConstants.EVENT_DESCRIPTION_FIELD);
		filter.setOperator(OperatorEnum.COMIENZA_POR);
		filter.setValue("D");

		List<FilterVO> filters = new LinkedList<FilterVO>();
		filters.add(filter);

		criteria.setFilters(filters);
		// criteria.setWhere("TRUE");

		int count = auditoriaService.countByCriteria(criteria);
		Assert.assertEquals(count, 3);

		PaginationContext paginationContext = new PaginationContext(2);
		PaginatedList<TrazaAuditoriaVO> list = auditoriaService.findByCriteria(
				criteria, paginationContext);

		Assert.assertEquals(list.getFullListSize(), 3);
		Assert.assertEquals(list.size(), 2);
		Assert.assertEquals(paginationContext.getPageNumber(), 0);
		paginationContext.nextPage();

		list = auditoriaService.findByCriteria(criteria, paginationContext);
		Assert.assertEquals(paginationContext.hasMorePages(), false);

		Assert.assertEquals(paginationContext.getPageElementsCount(), 1);
		Assert.assertEquals(paginationContext.getPageNumber(), 1);
	}

	@Test
	public void testFindByAppIdEventType() {
		Long appId = new Long(1);
		Long eventType = new Long(1);
		Date startDate = new Date(0);
		Date endDate = new Date();
		List<TrazaAuditoriaVO> results = null;
		results = auditoriaService.findByAppIdEventType(appId, eventType,
				startDate, endDate);
		Assert.assertEquals(results.size(), 1);
	}

	@Test
	public void testFindByAppIdEventTypeP() {
		Long appId = new Long(1);
		Long eventType = new Long(1);
		Date startDate = new Date(0);
		Date endDate = new Date();

		PaginationContext paginationContext = new PaginationContext(10);
		PaginatedList<TrazaAuditoriaVO> results = null;
		results = auditoriaService.findByAppIdEventType(appId, eventType,
				startDate, endDate, paginationContext);
		Assert.assertEquals(results.size(), 1);
	}

	@Test
	public void testFindByUserObjectId() {

		List<TrazaAuditoriaVO> results = null;

		results = auditoriaService.findByUserObjectId("1", new Long(1), new Long(1), "10",
				new Date(0), new Date());

		Assert.assertEquals(results.size(), 0);
		
		PaginatedList<TrazaAuditoriaVO> resultsP = null;
		PaginationContext paginationContext = new PaginationContext(10);
		resultsP = auditoriaService.findByUserObjectId("1",new Long (1), new Long(1), "1",
				new Date(0), new Date(),paginationContext);

		Assert.assertEquals(resultsP.size(), 1);
		
	}

	@Test
	public void testFindByUserObjectField() {

		List<TrazaAuditoriaVO> results = null;

		results = auditoriaService.findByUserObjectField("1", new Long(1), new Long(1),
				"1", "field", new Date(0), new Date());

		Assert.assertEquals(results.size(), 1);
		
		PaginatedList<TrazaAuditoriaVO> resultsP = null;
		PaginationContext paginationContext = new PaginationContext(10);
		resultsP = auditoriaService.findByUserObjectField("1", new Long(1), new Long(1),
				"1", "field", new Date(0), new Date(),paginationContext);
		Assert.assertEquals(resultsP.size(), 1);
	}

	@Test
	public void testFindByUserIdAppId() {
		List<TrazaAuditoriaVO> results = null;

		results = auditoriaService.findByUserIdAppId("1", new Long("1"),
				new Date(0), new Date());
		Assert.assertEquals(results.size(), 1);

		PaginatedList<TrazaAuditoriaVO> resultsPaginated = null;
		PaginationContext paginationContext = new PaginationContext(10);
		resultsPaginated = auditoriaService.findByUserIdAppId("1",
				new Long("1"), new Date(0), new Date(), paginationContext);
		Assert.assertEquals(resultsPaginated.size(), 1);

	}
	
	@Test
	public void testFindByUserId(){
		
		List<TrazaAuditoriaVO> results = null;
		results = auditoriaService.findByUserId("2", new Date(0), new Date());
		Assert.assertEquals(results.size(), 1);
		PaginatedList<TrazaAuditoriaVO> resultsPaginated = null;
		PaginationContext paginationContext = new PaginationContext(10);
		resultsPaginated = auditoriaService.findByUserId("2", new Date(0), new Date(),paginationContext);
		Assert.assertEquals(resultsPaginated.size(), 1);
	}
	
	@Test
	public void testFindByObjectId() {

		List<TrazaAuditoriaVO> results = null;
		results = auditoriaService.findByObjectId(new Long(2), "2", new Date(0), new Date());
		Assert.assertEquals(results.size(), 1);
		PaginatedList<TrazaAuditoriaVO> resultsPaginated = null;
		PaginationContext paginationContext = new PaginationContext(10);
		resultsPaginated = auditoriaService.findByObjectId(new Long(2), "2", new Date(0), new Date(),
				paginationContext);
		Assert.assertEquals(resultsPaginated.size(), 1);
	}
	
	@Test
	public void testFindLikeObjectId() {

		List<TrazaAuditoriaVO> results = null;
		results = auditoriaService.findLikeObjectId(new Long(1), "%_4_%", new Date(0), new Date());
		Assert.assertEquals(results.size(), 1);
		PaginatedList<TrazaAuditoriaVO> resultsPaginated = null;
		PaginationContext paginationContext = new PaginationContext(10);
		resultsPaginated = auditoriaService.findLikeObjectId(new Long(1), "%_4_%", new Date(0), new Date(),
				paginationContext);
		Assert.assertEquals(resultsPaginated.size(), 1);
	}
	
	@Test
	public void testFindLikeUserObjectId() {

		List<TrazaAuditoriaVO> results = null;
		results = auditoriaService.findLikeUserObjectId("3",new Long(1), "%_4_%", new Date(0), new Date());
		Assert.assertEquals(results.size(), 1);
		PaginatedList<TrazaAuditoriaVO> resultsPaginated = null;
		PaginationContext paginationContext = new PaginationContext(10);
		resultsPaginated = auditoriaService.findLikeUserObjectId("3",new Long(1), "%_4_%", new Date(0), new Date(),
				paginationContext);
		Assert.assertEquals(resultsPaginated.size(), 1);
	}
	
	@Test
	public void testGetIdAplicacion(){
		try{
		AppAuditoriaVO app = auditoriaService.getAppAuditoria("e");
		Assert.fail();
		}catch(AuditoriaException e){
		
		}
		AppAuditoriaVO app = auditoriaService.getAppAuditoria("app1");
		Assert.assertEquals(app.getAppId(), new Long(1));
	}

}
