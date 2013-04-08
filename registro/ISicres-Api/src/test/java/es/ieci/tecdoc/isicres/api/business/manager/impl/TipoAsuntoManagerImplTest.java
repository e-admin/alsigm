package es.ieci.tecdoc.isicres.api.business.manager.impl;

import java.util.List;

import junit.framework.TestCase;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ieci.tecdoc.common.keys.ISicresKeys;

import es.ieci.tecdoc.isicres.api.business.dao.test.IsicresBaseDatabaseTestCase;
import es.ieci.tecdoc.isicres.api.business.manager.LoginManager;
import es.ieci.tecdoc.isicres.api.business.manager.TipoAsuntoManager;
import es.ieci.tecdoc.isicres.api.business.vo.BaseCriterioBusquedaVO;
import es.ieci.tecdoc.isicres.api.business.vo.CriterioBusquedaTipoAsuntoSqlVO;
import es.ieci.tecdoc.isicres.api.business.vo.TipoAsuntoVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;

public class TipoAsuntoManagerImplTest extends IsicresBaseDatabaseTestCase {

	protected TipoAsuntoManager tipoAsuntoManager;
	protected LoginManager loginManager;

	protected void setUp() throws Exception {
		super.setUp();
		// setRegistroManager(new RegistroManagerImpl());
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				getConfigLocations());

		setTipoAsuntoManager((TipoAsuntoManager) applicationContext
				.getBean("tipoAsuntoManager"));
		setLoginManager(new LoginLegacyManagerImpl());
	}

	public void testFindAll() {
		UsuarioVO usuario = buildSigemUser();

		usuario = getLoginManager().login(usuario);

		List result = tipoAsuntoManager.findAll(usuario, null);
		int count = tipoAsuntoManager.findAllCount(usuario);

		assertNotNull(result);
		assertTrue(result.size() > 0);
		assertTrue(result.size() == count);
	}

	public void testFindAll2() {
		UsuarioVO usuario = buildSigemUser();

		usuario = getLoginManager().login(usuario);

		BaseCriterioBusquedaVO criterio = new BaseCriterioBusquedaVO();
		criterio.setLimit(new Long(15));
		criterio.setOffset(new Long(0));

		List result = tipoAsuntoManager.findAll(usuario, criterio);
		int count = tipoAsuntoManager.findAllCount(usuario);

		assertNotNull(result);
		assertTrue(result.size() > 0);
		assertTrue(result.size() < count);
	}

	public void testFindAll3() {
		UsuarioVO usuario = buildSigemUserNotExist();

		usuario = getLoginManager().login(usuario);

		BaseCriterioBusquedaVO criterio = new BaseCriterioBusquedaVO();
		criterio.setLimit(new Long(15));
		criterio.setOffset(new Long(0));

		List result = tipoAsuntoManager.findAll(usuario, criterio);

		assertNotNull(result);
		assertTrue(result.size() == 0);
	}

	public void testGetTipoAsuntoById() {

		UsuarioVO usuario = buildSigemUser();

		usuario = getLoginManager().login(usuario);

		TipoAsuntoVO result = tipoAsuntoManager.getTipoAsuntoById(usuario,
				"001");

		assertNotNull(result);
	}

	public void testGetTipoAsuntoById2() {

		UsuarioVO usuario = buildSigemUser();

		usuario = getLoginManager().login(usuario);

		TipoAsuntoVO result = tipoAsuntoManager.getTipoAsuntoById(usuario,
				"453");

		assertNull(result);
	}

	public void testGetTipoAsuntoByCodigo() {

		UsuarioVO usuario = buildSigemUser();

		usuario = getLoginManager().login(usuario);

		TipoAsuntoVO result = tipoAsuntoManager.getTipoAsuntoByCodigo(usuario,
				"TLIC");

		assertNotNull(result);
	}

	public void testGetTipoAsuntoByCodigo2() {

		UsuarioVO usuario = buildSigemUser();

		usuario = getLoginManager().login(usuario);

		TipoAsuntoVO result = tipoAsuntoManager.getTipoAsuntoByCodigo(usuario,
				"XXXX");

		assertNull(result);
	}

	public void testFindTipoAsuntoByCriterioWhereSql() {
		UsuarioVO usuario = buildSigemUser();

		usuario = getLoginManager().login(usuario);

		CriterioBusquedaTipoAsuntoSqlVO criterioBusqueda = new CriterioBusquedaTipoAsuntoSqlVO();
		criterioBusqueda.setSql("for_ereg = 1");

		List result = tipoAsuntoManager.findTipoAsuntoByCriterioWhereSql(
				usuario, criterioBusqueda);

		int count = tipoAsuntoManager.findTipoAsuntoByCriterioWhereSqlCount(
				usuario, criterioBusqueda);

		assertNotNull(result);
		assertTrue(result.size() == count);
		assertTrue(result.size() > 0);
	}

	public void testFindTipoAsuntoByCriterioWhereSql2() {
		UsuarioVO usuario = buildSigemUser();

		usuario = getLoginManager().login(usuario);

		CriterioBusquedaTipoAsuntoSqlVO criterioBusqueda = new CriterioBusquedaTipoAsuntoSqlVO();
		criterioBusqueda.setSql("for_ereg = 5");

		List result = tipoAsuntoManager.findTipoAsuntoByCriterioWhereSql(
				usuario, criterioBusqueda);

		int count = tipoAsuntoManager.findTipoAsuntoByCriterioWhereSqlCount(
				usuario, criterioBusqueda);

		assertNotNull(result);
		assertTrue(result.size() == 0);
	}

	private UsuarioVO buildSigemUser() {
		UsuarioVO usuario = new UsuarioVO();
		usuario.setLoginName("sigem");
		usuario.setPassword("sigem");
		usuario.getConfiguracionUsuario().setIdEntidad(
				ISicresKeys.IS_INVESICRES);
		return usuario;
	}

	private UsuarioVO buildSigemUserNotExist() {
		UsuarioVO usuario = new UsuarioVO();
		usuario.setLoginName("sigemX");
		usuario.setPassword("sigemX");
		usuario.getConfiguracionUsuario().setIdEntidad(
				ISicresKeys.IS_INVESICRES);
		return usuario;
	}

	public TipoAsuntoManager getTipoAsuntoManager() {
		return tipoAsuntoManager;
	}

	public void setTipoAsuntoManager(TipoAsuntoManager tipoAsuntoManager) {
		this.tipoAsuntoManager = tipoAsuntoManager;
	}

	public LoginManager getLoginManager() {
		return loginManager;
	}

	public void setLoginManager(LoginManager loginManager) {
		this.loginManager = loginManager;
	}

	protected String[] getConfigLocations() {
		return new String[] { "/beans/datasourceTest-beans.xml",
				"/beans/ISicres-Api/applicationContext.xml",
				"/beans/ISicres-Api/dao-beans.xml",
				"/beans/ISicres-Api/manager-beans.xml",
				"/beans/ISicres-Api/transaction-beans.xml" };
	}
}
