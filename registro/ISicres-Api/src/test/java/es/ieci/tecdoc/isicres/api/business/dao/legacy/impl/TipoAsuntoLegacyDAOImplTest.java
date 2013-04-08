package es.ieci.tecdoc.isicres.api.business.dao.legacy.impl;

import java.util.List;
import java.util.Locale;

import es.ieci.tecdoc.isicres.api.business.dao.TipoAsuntoDAO;
import es.ieci.tecdoc.isicres.api.business.dao.test.IsicresBaseDatabaseTestCase;
import es.ieci.tecdoc.isicres.api.business.keys.ConstantKeys;
import es.ieci.tecdoc.isicres.api.business.vo.BaseCriterioBusquedaVO;
import es.ieci.tecdoc.isicres.api.business.vo.CriterioBusquedaTipoAsuntoSqlVO;
import es.ieci.tecdoc.isicres.api.business.vo.TipoAsuntoVO;

public class TipoAsuntoLegacyDAOImplTest extends IsicresBaseDatabaseTestCase {

	protected TipoAsuntoDAO tipoAsuntoLegacyDAO = new TipoAsuntoLegacyDAOImpl();
	protected Locale locale = new Locale(ConstantKeys.LOCALE_LENGUAGE_DEFAULT,
			ConstantKeys.LOCALE_COUNTRY_DEFAULT);

	public void testGetTipoAsuntoByCodigo() {
		TipoAsuntoVO result = tipoAsuntoLegacyDAO.getTipoAsuntoByCodigo(locale,
				"2", "TLIC");
		assertNotNull(result);
	}

	public void testGetTipoAsuntoByCodigo2() {
		TipoAsuntoVO result = tipoAsuntoLegacyDAO.getTipoAsuntoByCodigo(locale,
				"563", "TSUB");
		assertNull(result);
	}

	public void testGetTipoAsuntoByCodigo3() {
		TipoAsuntoVO result = tipoAsuntoLegacyDAO.getTipoAsuntoByCodigo(locale,
				"2", "XXX");
		assertNull(result);
	}

	public void testGetTipoAsuntoByCodigo4() {
		TipoAsuntoVO result = tipoAsuntoLegacyDAO.getTipoAsuntoByCodigo(locale,
				"5", "TSUB");
		assertNull(result);
	}

	public void testFindAllAsuntos() {
		BaseCriterioBusquedaVO baseCriterioBusqueda = new BaseCriterioBusquedaVO();
		baseCriterioBusqueda.setLimit(Long.valueOf("10"));
		baseCriterioBusqueda.setOffset(Long.valueOf("0"));

		List result = tipoAsuntoLegacyDAO.findAllAsuntos(locale, "2",
				baseCriterioBusqueda);
		assertNotNull(result);
		assertTrue(result.size() > 0);
		assertTrue(result.size() == 7);
	}

	public void testFindAllAsuntos2() {
		BaseCriterioBusquedaVO baseCriterioBusqueda = new BaseCriterioBusquedaVO();
		baseCriterioBusqueda.setLimit(Long.valueOf("10"));
		baseCriterioBusqueda.setOffset(Long.valueOf("0"));

		List result = tipoAsuntoLegacyDAO.findAllAsuntos(locale, "555",
				baseCriterioBusqueda);
		assertNotNull(result);
		assertTrue(result.size() == 6);
	}

	public void testFindTipoAsuntoByCriterioWhereSql() {
		CriterioBusquedaTipoAsuntoSqlVO criterioBusqueda = new CriterioBusquedaTipoAsuntoSqlVO();
		criterioBusqueda.setSql("FOR_EREG = 1");
		criterioBusqueda.setLimit(Long.valueOf("10"));
		criterioBusqueda.setOffset(Long.valueOf("0"));

		List result = tipoAsuntoLegacyDAO.findTipoAsuntoByCriterioWhereSql(
				locale, "2", criterioBusqueda);

		assertNotNull(result);
		assertTrue(result.size() > 0);
	}

	public void testFindTipoAsuntoByCriterioWhereSql2() {
		CriterioBusquedaTipoAsuntoSqlVO criterioBusqueda = new CriterioBusquedaTipoAsuntoSqlVO();
		criterioBusqueda.setSql("FOR_EREG = 0");
		criterioBusqueda.setLimit(Long.valueOf("10"));
		criterioBusqueda.setOffset(Long.valueOf("0"));

		List result = tipoAsuntoLegacyDAO.findTipoAsuntoByCriterioWhereSql(
				locale, "2", criterioBusqueda);

		assertNotNull(result);
		assertTrue(result.size() == 1);
	}

	public void testFindTipoAsuntoByCriterioWhereSql3() {
		CriterioBusquedaTipoAsuntoSqlVO criterioBusqueda = new CriterioBusquedaTipoAsuntoSqlVO();
		criterioBusqueda.setSql("FOR_EREG = 2");
		criterioBusqueda.setLimit(Long.valueOf("10"));
		criterioBusqueda.setOffset(Long.valueOf("0"));

		List result = tipoAsuntoLegacyDAO.findTipoAsuntoByCriterioWhereSql(
				locale, "2", criterioBusqueda);

		assertTrue(result.size() == 0);
	}

	public void testGetTipoAsuntoById() {
		TipoAsuntoVO result = tipoAsuntoLegacyDAO
				.getTipoAsuntoById(locale, "1");
		assertNotNull(result);
	}

	public void testGetTipoAsuntoById2() {
		TipoAsuntoVO result = tipoAsuntoLegacyDAO.getTipoAsuntoById(locale,
				"8452");
		assertNull(result);
	}

	public void testgetTipoAsuntoAllOficOrIdOfic() {
		TipoAsuntoVO result = tipoAsuntoLegacyDAO.getTipoAsuntoAllOficOrIdOfic(
				locale, "TSUB", "2");
		assertNotNull(result);
	}

	public void testgetTipoAsuntoAllOficOrIdOfic2() {
		TipoAsuntoVO result = tipoAsuntoLegacyDAO.getTipoAsuntoAllOficOrIdOfic(
				locale, "TSUB", "945");
		assertNull(result);
	}

	public void testgetTipoAsuntoAllOficOrIdOfic3() {
		TipoAsuntoVO result = tipoAsuntoLegacyDAO.getTipoAsuntoAllOficOrIdOfic(
				locale, "XXX", "2");
		assertNull(result);
	}

}
