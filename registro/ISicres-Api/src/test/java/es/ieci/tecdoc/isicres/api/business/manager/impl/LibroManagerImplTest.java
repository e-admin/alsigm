package es.ieci.tecdoc.isicres.api.business.manager.impl;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ieci.tecdoc.common.keys.ISicresKeys;

import es.ieci.tecdoc.isicres.api.business.dao.test.IsicresBaseDatabaseTestCase;
import es.ieci.tecdoc.isicres.api.business.manager.LibroManager;
import es.ieci.tecdoc.isicres.api.business.manager.LoginManager;
import es.ieci.tecdoc.isicres.api.business.vo.BaseLibroVO;
import es.ieci.tecdoc.isicres.api.business.vo.EsquemaLibroVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;

public class LibroManagerImplTest extends IsicresBaseDatabaseTestCase {

	protected LibroManager libroManager;
	protected LoginManager loginManager;

	protected void setUp() throws Exception {
		super.setUp();
		// setRegistroManager(new RegistroManagerImpl());
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				getConfigLocations());

		setLibroManager((LibroManager) applicationContext
				.getBean("libroManager"));
		setLoginManager(new LoginLegacyManagerImpl());
	}

	public void testfindLibrosEntradaByUser() {

		UsuarioVO usuario = buildSigemUser();

		usuario = getLoginManager().login(usuario);

		List result = libroManager.findLibrosEntradaByUser(usuario);

		assertNotNull(result);
		assertTrue(result.size() > 0);
	}

	public void testfindLibrosEntradaByUser2() {

		UsuarioVO usuario = buildSigemUserNotExist();

		usuario = getLoginManager().login(usuario);

		List result = libroManager.findLibrosEntradaByUser(usuario);

		assertNotNull(result);
		assertTrue(result.size() == 0);
	}

	public void testfindLibrosSalidaByUser() {
		UsuarioVO usuario = buildSigemUser();

		usuario = getLoginManager().login(usuario);

		List result = libroManager.findLibrosSalidaByUser(usuario);

		assertNotNull(result);
		assertTrue(result.size() > 0);
	}

	public void testfindLibrosSalidaByUser2() {
		UsuarioVO usuario = buildSigemUserNotExist();

		usuario = getLoginManager().login(usuario);

		List result = libroManager.findLibrosSalidaByUser(usuario);

		assertNotNull(result);
		assertTrue(result.size() == 0);
	}

	public void testgetEsquemaLibro() {
		UsuarioVO usuario = buildSigemUser();

		usuario = getLoginManager().login(usuario);

		BaseLibroVO libro = new BaseLibroVO();
		libro.setId("1");

		EsquemaLibroVO result = libroManager.getEsquemaLibro(usuario, libro);

		assertNotNull(result);
	}

	public void testgetEsquemaLibro2() {
		UsuarioVO usuario = buildSigemUser();

		usuario = getLoginManager().login(usuario);

		BaseLibroVO libro = new BaseLibroVO();
		libro.setId("43435");

		EsquemaLibroVO result = libroManager.getEsquemaLibro(usuario, libro);

		assertNull(result);
	}

	public void testgetEsquemaLibro3() {
		UsuarioVO usuario = buildSigemUserNotExist();

		usuario = getLoginManager().login(usuario);

		BaseLibroVO libro = new BaseLibroVO();
		libro.setId("43435");

		EsquemaLibroVO result = libroManager.getEsquemaLibro(usuario, libro);

		assertNull(result);
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
		usuario.setLoginName("sigem2");
		usuario.setPassword("sigem2");
		usuario.getConfiguracionUsuario().setIdEntidad(
				ISicresKeys.IS_INVESICRES);
		return usuario;
	}

	public LibroManager getLibroManager() {
		return libroManager;
	}

	public void setLibroManager(LibroManager libroManager) {
		this.libroManager = libroManager;
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
