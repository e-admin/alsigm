package descripcion.db;


import org.junit.Test;

import junit.framework.Assert;
import common.manager.ArchidocDBBaseTest;

public class NumeroUDocREDBEntityImplTest extends ArchidocDBBaseTest {

	@Override
	protected INumeroDBEntity getDAO() {
		return getManager().getNumeroUdocReDBEntity();
	}

	@Test
	public void getMaxOrden(){
		int maxOrden = getDAO().getMaxOrden(idUdocRe, idCampo);
		Assert.assertEquals(ultimoOrden, maxOrden);
	}
}
