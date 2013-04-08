package es.ieci.tecdoc.isicres.api.business.dao.legacy.impl;

import java.util.List;
import java.util.Locale;

import es.ieci.tecdoc.isicres.api.business.dao.UnidadAdministrativaDAO;
import es.ieci.tecdoc.isicres.api.business.dao.test.IsicresBaseDatabaseTestCase;
import es.ieci.tecdoc.isicres.api.business.keys.ConstantKeys;
import es.ieci.tecdoc.isicres.api.business.vo.CriterioBusquedaTipoUnidadAdministrativaVO;
import es.ieci.tecdoc.isicres.api.business.vo.CriterioBusquedaUnidadAdministrativaByTipoVO;
import es.ieci.tecdoc.isicres.api.business.vo.CriterioBusquedaUnidadAdministrativaByUnidadVO;
import es.ieci.tecdoc.isicres.api.business.vo.CriterioBusquedaWhereSqlVO;
import es.ieci.tecdoc.isicres.api.business.vo.UnidadAdministrativaVO;

public class UnidadAdministrativaLegacyDAOImplTest extends
		IsicresBaseDatabaseTestCase {

	protected UnidadAdministrativaDAO unidadAdministrativaDAO = new UnidadAdministrativaLegacyDAOImpl();
	protected Locale locale = new Locale(ConstantKeys.LOCALE_LENGUAGE_DEFAULT,
			ConstantKeys.LOCALE_COUNTRY_DEFAULT);

	public void testFindAllTipoUnidadesAdmin() {
		CriterioBusquedaTipoUnidadAdministrativaVO criterio = new CriterioBusquedaTipoUnidadAdministrativaVO();
		criterio.setOffset(Long.valueOf("0"));
		criterio.setLimit(Long.valueOf("10"));
		List result = unidadAdministrativaDAO.findAllTipoUnidadesAdmin(locale,
				criterio);

		assertNotNull(result);
		assertTrue(result.size() > 0);
		assertTrue(result.size() == 4);
	}

	public void testFindUnidadesAdmByUnidad() {
		CriterioBusquedaUnidadAdministrativaByUnidadVO criterio = new CriterioBusquedaUnidadAdministrativaByUnidadVO();
		criterio.setIdUnidadAdministrativaPadre("4487");
		criterio.setLimit(Long.valueOf("10"));
		criterio.setOffset(Long.valueOf("0"));

		List result = unidadAdministrativaDAO.findUnidadesAdmByUnidad(locale,
				criterio);
		assertNotNull(result);
		assertTrue(result.size() > 0);
		assertTrue(result.size() == 9);
	}

	public void testFindUnidadesAdmByUnidad2() {
		CriterioBusquedaUnidadAdministrativaByUnidadVO criterio = new CriterioBusquedaUnidadAdministrativaByUnidadVO();
		criterio.setIdUnidadAdministrativaPadre("9999");
		criterio.setLimit(Long.valueOf("10"));
		criterio.setOffset(Long.valueOf("0"));

		List result = unidadAdministrativaDAO.findUnidadesAdmByUnidad(locale,
				criterio);
		assertNotNull(result);
		assertTrue(result.size() == 0);
	}

	public void testFindUnidadesAdmByTipo() {
		CriterioBusquedaUnidadAdministrativaByTipoVO criterio = new CriterioBusquedaUnidadAdministrativaByTipoVO();
		criterio.setIdTipoUnidadAdministrativa("1");
		criterio.setLimit(Long.valueOf("10"));
		criterio.setOffset(Long.valueOf("0"));

		List result = unidadAdministrativaDAO.findUnidadesAdmByTipo(locale,
				criterio);
		assertNotNull(result);
		assertTrue(result.size() > 0);
		assertTrue(result.size() == 3);
	}

	public void testFindUnidadesAdmByTipo2() {
		CriterioBusquedaUnidadAdministrativaByTipoVO criterio = new CriterioBusquedaUnidadAdministrativaByTipoVO();
		criterio.setIdTipoUnidadAdministrativa("534");
		criterio.setLimit(Long.valueOf("10"));
		criterio.setOffset(Long.valueOf("0"));

		List result = unidadAdministrativaDAO.findUnidadesAdmByTipo(locale,
				criterio);
		assertNotNull(result);
		assertTrue(result.size() == 0);
	}

	public void testFindUnidadByCode() {
		UnidadAdministrativaVO result = unidadAdministrativaDAO
				.findUnidadByCode(locale, "MIR712");
		assertNotNull(result);
	}

	public void testFindUnidadByCode2() {
		UnidadAdministrativaVO result = unidadAdministrativaDAO
				.findUnidadByCode(locale, "XXXXXX");
		assertNull(result);
	}

	public void testFindUnidadesAdmWhereSQL() {
		CriterioBusquedaWhereSqlVO criterio = new CriterioBusquedaWhereSqlVO();
		criterio.setLimit(Long.valueOf("200"));
		criterio.setOffset(Long.valueOf("0"));
		criterio.setSql(" ENABLED = 1");

		List result = unidadAdministrativaDAO.findUnidadesAdmWhereSQL(locale,
				criterio);

		assertNotNull(result);
		assertTrue(result.size() > 0);
	}

	public void testFindUnidadesAdmWhereSQL2() {
		CriterioBusquedaWhereSqlVO criterio = new CriterioBusquedaWhereSqlVO();
		criterio.setLimit(Long.valueOf("10"));
		criterio.setOffset(Long.valueOf("0"));
		criterio.setSql(" ENABLED = 3");

		List result = unidadAdministrativaDAO.findUnidadesAdmWhereSQL(locale,
				criterio);

		assertNotNull(result);
		assertTrue(result.size() == 0);
	}

}
