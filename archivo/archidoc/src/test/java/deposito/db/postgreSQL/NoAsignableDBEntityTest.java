package deposito.db.postgreSQL;

import org.junit.Test;

import common.manager.ArchidocBasePostgreSQLTest;

import deposito.db.IElementoNoAsignableDBEntity;

public class NoAsignableDBEntityTest extends ArchidocBasePostgreSQLTest {

	@Override
	protected IElementoNoAsignableDBEntity getDAO() {
		return getManager().getElementoNoAsignableDBEntity();
	}

	@Test
	public void getIdsDescendientes(){
		getDAO().getIdsDescendientes(idElemento);
	}
}
