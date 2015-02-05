package gcontrol.db;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import common.manager.ArchidocDBBaseTest;

public class PermisoRolDBEntityImplTest extends ArchidocDBBaseTest {

	@Override
	protected IPermisoRolDBEntity getDAO() {
		return getManager().getPermisoRolDBEntity();
	}

	@Test
	public void getPermisosUsuario(){
		List listaPermisos = getDAO().getPermisosUsuario(idUsuario);
		Assert.assertNotNull(listaPermisos);
		Assert.assertEquals(1, listaPermisos.size());
	}

}
