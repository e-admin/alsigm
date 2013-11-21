/**
 *
 */
package es.ieci.tecdoc.isicres.api.business.dao.legacy.impl;

import java.util.List;

import junit.framework.Assert;
import es.ieci.tecdoc.isicres.api.business.dao.test.IsicresBaseDatabaseTestCase;
import es.ieci.tecdoc.isicres.api.business.vo.BaseDepartamentoVO;
import es.ieci.tecdoc.isicres.api.business.vo.DepartamentoUsuarioVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;

/**
 * @author Iecisa
 * @version $Revision$
 *
 */
public class DepartamentoLegacyDAOImplTest extends IsicresBaseDatabaseTestCase {

	protected DepartamentoLegacyDAOImpl departamentoLegacyDAO = new DepartamentoLegacyDAOImpl();

	public void testGetDepartamentos() {

		List<BaseDepartamentoVO> departamentos = departamentoLegacyDAO
				.findDepartamentos();

		Assert.assertEquals(departamentos.size(), 5);
	}

	public void testGetDepartamentoUsuario() {
		UsuarioVO usuarioVO = new UsuarioVO();
		usuarioVO.setId("3");
		DepartamentoUsuarioVO departamento = departamentoLegacyDAO
				.getDepartamentoUsuario(usuarioVO);
		Assert.assertNotNull(departamento);
		Assert.assertEquals(departamento.getId(), "3");

	}

	public void testgetDepartamentoById() {

		BaseDepartamentoVO departamento = departamentoLegacyDAO
				.getDepartamentoById(3);

		Assert.assertNotNull(departamento);
		Assert.assertEquals(departamento.getId(), "3");
	}

}
