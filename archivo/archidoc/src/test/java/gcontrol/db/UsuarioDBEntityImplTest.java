package gcontrol.db;

import org.junit.Test;

import common.db.IDBEntity;
import common.manager.ArchidocDBBaseTest;

public class UsuarioDBEntityImplTest extends ArchidocDBBaseTest {

	@Override
	protected IUsuarioDBEntity getDAO() {
		return getManager().getUsuarioDBEntity();
	}

	@Test
	public void getUsuariosXIdsEnSistOrg(){
		getDAO().getUsuariosXIdsEnSistOrg(new String[]{"U1"}, new String[]{"100"} );
	}
}
