/**
 *
 */
package es.ieci.tecdoc.isicres.api.business.dao.legacy.impl;

import java.util.List;

import junit.framework.Assert;
import es.ieci.tecdoc.isicres.api.business.dao.test.IsicresBaseDatabaseTestCase;
import es.ieci.tecdoc.isicres.api.business.vo.BaseUsuarioVO;

/**
 * @author Iecisa
 * @version $Revision$
 *
 */
public class UsuarioLegacyDAOImplTest  extends IsicresBaseDatabaseTestCase {

	UsuarioLegacyDAOImpl usuarioLegacyDAOImpl = new UsuarioLegacyDAOImpl();

	public void testGetUsuarios(){
		List<BaseUsuarioVO> usuarios = usuarioLegacyDAOImpl.getUsuarios();
		Assert.assertTrue(usuarios.size()==4);
	}

	public void testGetUsuariosById(){
		BaseUsuarioVO usuario = usuarioLegacyDAOImpl.getUsuarioById(3);
		Assert.assertEquals(usuario.getId(), "3");
	}

	public void testGetUsuariosByIdNotFound(){
		BaseUsuarioVO usuario = usuarioLegacyDAOImpl.getUsuarioById(45);
		Assert.assertNull(usuario);
	}

	public void testGetUsuarioByLogin(){
		String login = "registro";
		BaseUsuarioVO usuario = usuarioLegacyDAOImpl.getUsuarioByLogin(login );
		Assert.assertEquals(usuario.getLoginName(), login);
	}

	public void testGetUsuarioByLoginNotFound(){
		String login = "XYZ";
		BaseUsuarioVO usuario = usuarioLegacyDAOImpl.getUsuarioByLogin(login);
		Assert.assertNull(usuario);
	}

}
