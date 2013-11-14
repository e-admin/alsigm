/**
 *
 */
package es.ieci.tecdoc.isicres.api.business.dao.legacy.impl;

import java.util.List;

import junit.framework.Assert;
import es.ieci.tecdoc.isicres.api.business.dao.test.IsicresBaseDatabaseTestCase;
import es.ieci.tecdoc.isicres.api.business.vo.BaseUsuarioVO;
import es.ieci.tecdoc.isicres.api.business.vo.GrupoUsuarioVO;

/**
 * @author Iecisa
 * @version $Revision$
 *
 */
public class GrupoUsuarioLegacyDAOImplTest extends IsicresBaseDatabaseTestCase {

	GrupoUsuarioLegacyDAOImpl grupoUsuarioLegacyDAOImpl = new GrupoUsuarioLegacyDAOImpl();


	public void testGetGrupoById(){
		GrupoUsuarioVO grupo = grupoUsuarioLegacyDAOImpl.getGrupoById(1);
		Assert.assertEquals(grupo.getId(), "1");
	}

	public void testGetGrupoByIdNotFound(){
		GrupoUsuarioVO grupo = grupoUsuarioLegacyDAOImpl.getGrupoById(1540);
		Assert.assertNull(grupo);
	}

	public void testFindGrupos(){
		List<GrupoUsuarioVO> grupos = grupoUsuarioLegacyDAOImpl.getGrupos();
		Assert.assertTrue(grupos.size() == 3);
	}

	public void testFindGruposLdap(){
		List<GrupoUsuarioVO> grupos = grupoUsuarioLegacyDAOImpl.getGruposLdap();
		Assert.assertTrue(grupos.size() == 0);
	}

	public void testFindGruposPertenecientesUsuario() {
		List<GrupoUsuarioVO> grupos = grupoUsuarioLegacyDAOImpl
				.getGruposPertenecientesUsuario(4);
		Assert.assertTrue(grupos.size() == 3);
	}

	public void testFindGruposPertenecientesUsuarioNotFound() {
		List<GrupoUsuarioVO> grupos = grupoUsuarioLegacyDAOImpl
				.getGruposPertenecientesUsuario(4540);
		Assert.assertTrue(grupos.size() == 0);
	}


	public void testFindGruposNoPertenecientesUsuario() {
		List<GrupoUsuarioVO> grupos = grupoUsuarioLegacyDAOImpl
				.getGruposNoPertenecientesUsuario(3);
		Assert.assertTrue(grupos.size() == 3);
	}

	public void testGetUsuariosDelGrupo(){
		List<BaseUsuarioVO> usuarios = grupoUsuarioLegacyDAOImpl.getUsuariosDelGrupo(3);
		Assert.assertEquals(usuarios.size(), 1);
	}

	public void testGetUsuariosDelGrupoNotFound(){
		List<BaseUsuarioVO> usuarios = grupoUsuarioLegacyDAOImpl.getUsuariosDelGrupo(509);
		Assert.assertEquals(usuarios.size(), 0);
	}


}
