package es.ieci.tecdoc.fwktd.audit.api.business.manager;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;

import es.ieci.tecdoc.fwktd.audit.api.constants.CamposTrazaAuditoriaConstants;
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
@ContextConfiguration({
		"/beans/fwktd-audit-api/fwktd-audit-api-incrementer-beans.xml",
		"/beans/fwktd-audit-api/fwktd-audit-api-manager-beans.xml",
		"/beans/fwktd-audit-api/fwktd-audit-api-dao-beans.xml",
		"/beans/fwktd-audit-api/fwktd-audit-api-transaction-beans.xml",

		"/beans/fwktd-audit-api-applicationContext.xml",
		"/beans/fwktd-audit-api/transaction-beans.xml",
		"/beans/fwktd-audit-api/datasource-beans.xml" })
public class AuditoriaManagerImplTest extends
		AbstractDbUnitTransactionalJUnit4SpringContextTests {

	@Autowired
	@Qualifier("main")
	protected AuditoriaManager auditoriaManager;

	public AuditoriaManager getAuditoriaManager() {
		return auditoriaManager;
	}

	public void setAuditoriaManager(AuditoriaManager auditoriaManager) {
		this.auditoriaManager = auditoriaManager;
	}

	@Test
	public void testCount() {
		int count = auditoriaManager.count();
		Assert.assertEquals(count, 4);
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

		auditoriaManager.save(traza1);
		int count = auditoriaManager.count();

		Assert.assertEquals(count, 5);
	}

	@Test(expected = org.springframework.dao.DataAccessException.class)
	public void testSaveKO() {

		TrazaAuditoriaVO traza1 = new TrazaAuditoriaVO();
		traza1.setAppId(new Long(1));
		traza1.setEventType(new Long(1));
		traza1.setEventDate(new Date());

		// userId = null para forzar el fallo
		traza1.setUserId(null);

		traza1.setUserName("user name 1");
		traza1.setUserHostName("user host name");

		auditoriaManager.save(traza1);

		int count = auditoriaManager.count();
		assert (count == 2);
		Assert.assertEquals(count, 2);
	}

	@Test
	public void testGetOK() {
		TrazaAuditoriaVO trazaAuditoriaVO = auditoriaManager.get("1000");
		assert (trazaAuditoriaVO.getId().equalsIgnoreCase("1000"));

		List<TrazaAuditoriaVO> lista = auditoriaManager.getAll();
		Assert.assertEquals(lista.size(), 4);
	}

	@Test(expected = org.springframework.orm.ObjectRetrievalFailureException.class)
	public void testGetKO() {
		TrazaAuditoriaVO trazaAuditoriaVO = auditoriaManager.get("10000");

		Assert.assertEquals(trazaAuditoriaVO, null);
	}

	@Test
	public void testUpdate() {
		TrazaAuditoriaVO traza = auditoriaManager.get("1000");
		
		Calendar now = new GregorianCalendar();

		//DateTime de SQLServer no almacena milisegundos
		now.set(Calendar.MILLISECOND, 0);
		traza.setEventDate(now.getTime());
		traza.setAppId(new Long(1000));
		traza.setEventDescription("Nueva");
		traza.setAppDescription("Nueva descripción");
		traza.setEventType(new Long(100));
		traza.setNewValue("Nuevo valor");
		traza.setObjectField("Nuevo object field");
		traza.setObjectId("Nuevo object id");
		traza.setObjectType("Object type");
		traza.setObjectTypeDescription("Nuevo object type description");
		traza.setOldValue("Antiguo valor");
		traza.setUserHostName("Nuevo user host name");
		traza.setUserId("500");
		traza.setUserIp("nueva ip");
		traza.setUserName("nuevo user name");

		auditoriaManager.update(traza);
		TrazaAuditoriaVO trazaUpdated = auditoriaManager.get("1000");
		Assert.assertEquals(traza.getAppId(), trazaUpdated.getAppId());
		Assert.assertEquals(traza.getEventDescription(),
				trazaUpdated.getEventDescription());
		Assert.assertEquals(traza.getEventDate().getTime(), trazaUpdated.getEventDate().getTime());
		Assert.assertEquals(traza.getAppDescription(),
				trazaUpdated.getAppDescription());
		Assert.assertEquals(traza.getEventType(), trazaUpdated.getEventType());
		Assert.assertEquals(traza.getUserIp(), trazaUpdated.getUserIp());
		Assert.assertEquals(traza, trazaUpdated);
	}
	
	
	@Test(expected = org.springframework.dao.DataAccessException.class)
	public void testUpdateKO() {
		TrazaAuditoriaVO traza = auditoriaManager.get("1000");
		
		Calendar now = new GregorianCalendar();

		//DateTime de SQLServer no almacena milisegundos
		now.set(Calendar.MILLISECOND, 0);
		
		traza.setAppId(null);
		traza.setEventDescription("Nueva");
		traza.setAppDescription("Nueva descripción");
		traza.setEventType(null);
		traza.setEventDate(null);
		traza.setNewValue("Nuevo valor");
		traza.setObjectField("Nuevo object field");
		traza.setObjectId("Nuevo object id");
		traza.setObjectType("Object type");
		traza.setObjectTypeDescription("Nuevo object type description");
		traza.setOldValue("Antiguo valor");
		traza.setUserHostName("Nuevo user host name");
		traza.setUserId(null);
		traza.setUserIp("nueva ip");
		traza.setUserName("nuevo user name");

		auditoriaManager.update(traza);
		TrazaAuditoriaVO trazaUpdated = auditoriaManager.get("1000");
		Assert.assertEquals(traza.getAppId(), trazaUpdated.getAppId());
		Assert.assertEquals(traza.getEventDescription(),
				trazaUpdated.getEventDescription());
		Assert.assertEquals(traza.getEventDate().getTime(), trazaUpdated.getEventDate().getTime());
		Assert.assertEquals(traza.getAppDescription(),
				trazaUpdated.getAppDescription());
		Assert.assertEquals(traza.getEventType(), trazaUpdated.getEventType());
		Assert.assertEquals(traza.getUserIp(), trazaUpdated.getUserIp());
		Assert.assertEquals(traza, trazaUpdated);
	}

	@Test
	public void testDeleteOk() {

		auditoriaManager.delete("1000");
		auditoriaManager.getAll();
		int count = auditoriaManager.count();
		Assert.assertEquals(count, 3);
	}

	@Test
	public void testFilterVarChar() {
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
		filter3.setField(CamposTrazaAuditoriaConstants.EVENT_TYPE_FIELD);
		filter3.setOperator(OperatorEnum.ES_IGUAL);
		filter3.setJdbcType(JdbcTypesEnum.BIG_INT);
		filter3.setValue("1");

		List<FilterVO> filters = new LinkedList<FilterVO>();
		filters.add(filter);
		filters.add(filter2);
		filters.add(filter3);
		criteria.setFilters(filters);
		
		int count = auditoriaManager.countByCriteria(criteria);
		Assert.assertEquals(count, 1);

		List<TrazaAuditoriaVO> list = auditoriaManager.findByCriteria(criteria);
		Assert.assertEquals(list.size(), 1);

	}
	
	@Test
	public void testFilterOrder() {
		CriteriaVO criteria = new CriteriaVO();
		FilterVO filter = new FilterVO();
		filter.setField(CamposTrazaAuditoriaConstants.EVENT_DESCRIPTION_FIELD);
		filter.setOperator(OperatorEnum.COMIENZA_POR);
		filter.setValue("D");

		
		List<FilterVO> filters = new LinkedList<FilterVO>();
		filters.add(filter);
		
		criteria.setFilters(filters);
		criteria.setOrderBy("ID DESC");

		List<TrazaAuditoriaVO> list = auditoriaManager.findByCriteria(criteria);
		TrazaAuditoriaVO traza = list.get(0);
		Assert.assertEquals(traza.getId(), "1002");

	}

	@Test
	public void testFilterBigInt() {
		CriteriaVO criteria = new CriteriaVO();
		FilterVO filter1 = new FilterVO();
		filter1.setField("id");
		filter1.setOperator(OperatorEnum.ES_IGUAL);
		filter1.setValue("1000");
		filter1.setJdbcType(JdbcTypesEnum.BIG_INT);

		FilterVO filter2 = new FilterVO();
		filter2.setField("event_description");
		filter2.setOperator(OperatorEnum.CONTIENE);
		filter2.setValue("Desc");

		List<FilterVO> filters1 = new LinkedList<FilterVO>();
		filters1.add(filter1);
		filters1.add(filter2);

		criteria.setFilters(filters1);
		// criteria.setWhere("TRUE");

		int count = auditoriaManager.countByCriteria(criteria);
		// Assert.assertEquals(count, 1);

		List<TrazaAuditoriaVO> list = auditoriaManager.findByCriteria(criteria);
		Assert.assertEquals(list.size(), 1);

	}

	@Test
	public void testFilterTimestamp() {
		CriteriaVO criteria = new CriteriaVO();

		List<FilterVO> filters = new LinkedList<FilterVO>();

		FilterVO filter = new FilterVO();

		filter.setOperator(OperatorEnum.ES_IGUAL);
		filter.setField(CamposTrazaAuditoriaConstants.EVENT_DATE_FIELD);
		filter.setValue("28/06/2012 00:00:00");
		filter.setJdbcType(JdbcTypesEnum.TIMESTAMP);

		FilterVO filter2 = new FilterVO();
		filter2.setField("object_type_description");
		filter2.setOperator(OperatorEnum.COMIENZA_POR);
		filter2.setValue("d");
		
		filters.add(filter);
		filters.add(filter2);
		
		criteria.setFilters(filters);

		List<TrazaAuditoriaVO> list = auditoriaManager.findByCriteria(criteria);
		Assert.assertEquals(list.size(), 1);
	}

	@Test
	public void testPagination() {
		CriteriaVO criteria = new CriteriaVO();
		FilterVO filter = new FilterVO();
		filter.setField(CamposTrazaAuditoriaConstants.EVENT_DESCRIPTION_FIELD);
		filter.setOperator(OperatorEnum.COMIENZA_POR);
		filter.setValue("D");

		List<FilterVO> filters = new LinkedList<FilterVO>();
		filters.add(filter);

		criteria.setFilters(filters);

		int count = auditoriaManager.countByCriteria(criteria);
		Assert.assertEquals(count, 3);

		PaginationContext paginationContext = new PaginationContext(2);
		PaginatedList<TrazaAuditoriaVO> list = auditoriaManager.findByCriteria(criteria, paginationContext);
		

		Assert.assertEquals(list.getFullListSize(), 3);
		Assert.assertEquals(list.size(), 2);
		Assert.assertEquals(paginationContext.getPageNumber(), 0);
		paginationContext.nextPage();
		
		list = auditoriaManager.findByCriteria(criteria, paginationContext);
		Assert.assertEquals(paginationContext.hasMorePages(), false);
		
		
		Assert.assertEquals(paginationContext.getPageElementsCount(), 1);
		Assert.assertEquals(paginationContext.getPageNumber(), 1);

	}

}
