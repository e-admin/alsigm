package deposito.db.ibmdb2;

import org.junit.Test;

import common.manager.ArchidocBaseDB2Test;

import deposito.db.IElementoNoAsignableDBEntity;

public class NoAsignableDBEntityTest extends ArchidocBaseDB2Test {

	@Override
	protected IElementoNoAsignableDBEntity getDAO() {
		return getManager().getElementoNoAsignableDBEntity();
	}


	@Test
	public void getIdsDescendientes() {
		getDAO().getIdsDescendientes(idElemento);
	}
}
