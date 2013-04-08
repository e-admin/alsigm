package es.ieci.tecdoc.isicres.api.business.manager.impl;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ieci.tecdoc.common.keys.ISicresKeys;

import es.ieci.tecdoc.isicres.api.business.dao.test.IsicresBaseDatabaseTestCase;
import es.ieci.tecdoc.isicres.api.business.manager.LoginManager;
import es.ieci.tecdoc.isicres.api.business.manager.UnidadAdministrativaManager;
import es.ieci.tecdoc.isicres.api.business.vo.CriterioBusquedaUnidadAdministrativaByTipoVO;
import es.ieci.tecdoc.isicres.api.business.vo.CriterioBusquedaUnidadAdministrativaByUnidadVO;
import es.ieci.tecdoc.isicres.api.business.vo.CriterioBusquedaWhereSqlVO;
import es.ieci.tecdoc.isicres.api.business.vo.UnidadAdministrativaVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;

public class UnidadAdministrativaManagerImplTest extends IsicresBaseDatabaseTestCase{
	
	protected UnidadAdministrativaManager unidadAdministrativaManager;
	protected LoginManager loginManager;
	
	protected void setUp() throws Exception {
		super.setUp();
 
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				getConfigLocations());

		setUnidadAdministrativaManager((UnidadAdministrativaManager) applicationContext
				.getBean("unidadAdministrativaManager"));
		setLoginManager(new LoginLegacyManagerImpl());
	}

	public void testFindAllTipoUnidadAdministrativa(){
		UsuarioVO usuario = buildSigemUser();
		
		usuario = getLoginManager().login(usuario);
		
		List result = unidadAdministrativaManager.findAllTipoUnidadAdministrativa(usuario, null);
		int count = unidadAdministrativaManager.findCountAllTipoUnidadAdministrativa(usuario);
		
		assertNotNull(result);
		assertTrue(result.size() == count);
		assertTrue(result.size()>0);
	}
	
	public void testFindAllTipoUnidadAdministrativa2(){
		UsuarioVO usuario = buildSigemUserNotExist();
		
		usuario = getLoginManager().login(usuario);
		
		List result = unidadAdministrativaManager.findAllTipoUnidadAdministrativa(usuario, null);
		int count = unidadAdministrativaManager.findCountAllTipoUnidadAdministrativa(usuario);
		
		assertNotNull(result);
		assertTrue(result.size() == count);
		assertTrue(result.size()==0);
	}
	
	public void testFindUnidadAdministrativaByTipo(){
		UsuarioVO usuario = buildSigemUser();
		
		usuario = getLoginManager().login(usuario);
		
		CriterioBusquedaUnidadAdministrativaByTipoVO criterio = new CriterioBusquedaUnidadAdministrativaByTipoVO();
		criterio.setIdTipoUnidadAdministrativa("11");
		
		List result = unidadAdministrativaManager.findUnidadAdministrativaByTipo(usuario, criterio);
		int count = unidadAdministrativaManager.findCountUnidadAdministrativaByTipo(usuario, criterio);
		
		assertNotNull(result);
		assertTrue(result.size() == count);
		assertTrue(result.size()>0);
		
	}
	
	public void testFindUnidadAdministrativaByTipo2(){
		UsuarioVO usuario = buildSigemUser();
		
		usuario = getLoginManager().login(usuario);
		
		CriterioBusquedaUnidadAdministrativaByTipoVO criterio = new CriterioBusquedaUnidadAdministrativaByTipoVO();
		criterio.setIdTipoUnidadAdministrativa("766");
		
		List result = unidadAdministrativaManager.findUnidadAdministrativaByTipo(usuario, criterio);
		int count = unidadAdministrativaManager.findCountUnidadAdministrativaByTipo(usuario, criterio);
		
		assertNotNull(result);
		assertTrue(result.size() == count);
		assertTrue(result.size()==0);
		
	}
	
	public void testFindUnidadAdministrativaByTipo3(){
		UsuarioVO usuario = buildSigemUserNotExist();
		
		usuario = getLoginManager().login(usuario);
		
		CriterioBusquedaUnidadAdministrativaByTipoVO criterio = new CriterioBusquedaUnidadAdministrativaByTipoVO();
		criterio.setIdTipoUnidadAdministrativa("11");
		
		List result = unidadAdministrativaManager.findUnidadAdministrativaByTipo(usuario, criterio);
		int count = unidadAdministrativaManager.findCountUnidadAdministrativaByTipo(usuario, criterio);
		
		assertNotNull(result);
		assertTrue(result.size() == count);
		assertTrue(result.size()==0);
	}
	
	public void testFindUnidadAdministrativaByUnidad(){
		UsuarioVO usuario = buildSigemUser();
		
		usuario = getLoginManager().login(usuario);
		
		CriterioBusquedaUnidadAdministrativaByUnidadVO criterio = new CriterioBusquedaUnidadAdministrativaByUnidadVO();
		criterio.setIdUnidadAdministrativaPadre("4805");
		
		List result = unidadAdministrativaManager.findUnidadAdministrativaByUnidad(usuario, criterio);
		int count = unidadAdministrativaManager.findCountUnidadAdministrativaByUnidad(usuario, criterio);
		
		assertNotNull(result);
		assertTrue(result.size() == count);
		assertTrue(result.size()>0);
	}
	
	public void testFindUnidadAdministrativaByUnidad2(){
		UsuarioVO usuario = buildSigemUser();
		
		usuario = getLoginManager().login(usuario);
		
		CriterioBusquedaUnidadAdministrativaByUnidadVO criterio = new CriterioBusquedaUnidadAdministrativaByUnidadVO();
		criterio.setIdUnidadAdministrativaPadre("9999");
		
		List result = unidadAdministrativaManager.findUnidadAdministrativaByUnidad(usuario, criterio);
		int count = unidadAdministrativaManager.findCountUnidadAdministrativaByUnidad(usuario, criterio);
		
		assertNotNull(result);
		assertTrue(result.size() == count);
		assertTrue(result.size()==0);
	}
	
	public void testFindUnidadAdministrativaByUnidad3(){
		UsuarioVO usuario = buildSigemUserNotExist();
		
		usuario = getLoginManager().login(usuario);
		
		CriterioBusquedaUnidadAdministrativaByUnidadVO criterio = new CriterioBusquedaUnidadAdministrativaByUnidadVO();
		criterio.setIdUnidadAdministrativaPadre("4805");
		
		List result = unidadAdministrativaManager.findUnidadAdministrativaByUnidad(usuario, criterio);
		int count = unidadAdministrativaManager.findCountUnidadAdministrativaByUnidad(usuario, criterio);
		
		assertNotNull(result);
		assertTrue(result.size() == count);
		assertTrue(result.size()== 0);
	}
	
	public void testFindUnidadAdministrativaByCondition(){
		UsuarioVO usuario = buildSigemUser();
		
		usuario = getLoginManager().login(usuario);
		
		CriterioBusquedaWhereSqlVO criterio = new CriterioBusquedaWhereSqlVO();
		criterio.setSql("TYPE = 11");
		
		List result = unidadAdministrativaManager.findUnidadAdministrativaByCondition(usuario, criterio);
		int count = unidadAdministrativaManager.findCountUnidadAdministrativaByCondition(usuario, criterio);
		
		assertNotNull(result);
		assertTrue(result.size() == count);
		assertTrue(result.size()> 0);
	}
	
	public void testFindUnidadAdministrativaByCondition2(){
		UsuarioVO usuario = buildSigemUser();
		
		usuario = getLoginManager().login(usuario);
		
		CriterioBusquedaWhereSqlVO criterio = new CriterioBusquedaWhereSqlVO();
		criterio.setSql("TYPE = 86754");
		
		List result = unidadAdministrativaManager.findUnidadAdministrativaByCondition(usuario, criterio);
		int count = unidadAdministrativaManager.findCountUnidadAdministrativaByCondition(usuario, criterio);
		
		assertNotNull(result);
		assertTrue(result.size() == count);
		assertTrue(result.size()== 0);
	}
	
	public void testFindUnidadAdministrativaByCondition3(){
		UsuarioVO usuario = buildSigemUserNotExist();
		
		usuario = getLoginManager().login(usuario);
		
		CriterioBusquedaWhereSqlVO criterio = new CriterioBusquedaWhereSqlVO();
		criterio.setSql("TYPE = 11");
		
		List result = unidadAdministrativaManager.findUnidadAdministrativaByCondition(usuario, criterio);
		int count = unidadAdministrativaManager.findCountUnidadAdministrativaByCondition(usuario, criterio);
		
		assertNotNull(result);
		assertTrue(result.size() == count);
		assertTrue(result.size()== 0);
	}
	
	public void testGetUnidadAdministrativaByCodigo(){
		UsuarioVO usuario = buildSigemUser();
		
		usuario = getLoginManager().login(usuario);
		
		UnidadAdministrativaVO result = unidadAdministrativaManager
				.getUnidadAdministrativaByCodigo(usuario, "001");
		
		assertNotNull(result);
	}
	
	public void testGetUnidadAdministrativaByCodigo2(){
		UsuarioVO usuario = buildSigemUser();
		
		usuario = getLoginManager().login(usuario);
		
		UnidadAdministrativaVO result = unidadAdministrativaManager
				.getUnidadAdministrativaByCodigo(usuario, "xxx");
		
		assertNull(result);
	}
	
	public void testGetUnidadAdministrativaByCodigo3(){
		UsuarioVO usuario = buildSigemUserNotExist();
		
		usuario = getLoginManager().login(usuario);
		
		UnidadAdministrativaVO result = unidadAdministrativaManager
				.getUnidadAdministrativaByCodigo(usuario, "001");
		
		assertNull(result);
	}
	
	public UnidadAdministrativaManager getUnidadAdministrativaManager() {
		return unidadAdministrativaManager;
	}

	public void setUnidadAdministrativaManager(
			UnidadAdministrativaManager unidadAdministrativaManager) {
		this.unidadAdministrativaManager = unidadAdministrativaManager;
	}

	public LoginManager getLoginManager() {
		return loginManager;
	}

	public void setLoginManager(LoginManager loginManager) {
		this.loginManager = loginManager;
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
	
	protected String[] getConfigLocations() {
		return new String[] { "/beans/datasourceTest-beans.xml",
				"/beans/ISicres-Api/applicationContext.xml",
				"/beans/ISicres-Api/dao-beans.xml",
				"/beans/ISicres-Api/manager-beans.xml",
				"/beans/ISicres-Api/transaction-beans.xml" };
	}
}
