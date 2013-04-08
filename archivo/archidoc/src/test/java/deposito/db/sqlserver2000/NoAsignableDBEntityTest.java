package deposito.db.sqlserver2000;

import org.junit.Test;

import common.manager.ArchidocBaseSQLServerTest;

import deposito.db.IElementoNoAsignableDBEntity;

public class NoAsignableDBEntityTest extends ArchidocBaseSQLServerTest {

	@Override
	protected IElementoNoAsignableDBEntity getDAO() {
		return getManager().getElementoNoAsignableDBEntity();
	}

	@Test
	public void getIdsDescendientes(){
		getDAO().getIdsDescendientes(idElemento);
	}
}
