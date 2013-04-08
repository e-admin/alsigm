package deposito.db.oracle8i;

import org.junit.Test;

import common.manager.ArchidocBaseOracleTest;
import common.util.DBFactoryConstants;

import deposito.db.IElementoNoAsignableDBEntity;

public class NoAsignableDBEntityTest extends ArchidocBaseOracleTest {

	@Override
	protected IElementoNoAsignableDBEntity getDAO() {
		return getManager().getElementoNoAsignableDBEntity();
	}

	@Override
	protected String getDbFactoryClass() {
		return DBFactoryConstants.ORACLE8_FACTORY;
	}

	@Test
	public void getIdsDescendientes() {
		getDAO().getIdsDescendientes(idElemento);
	}



}
