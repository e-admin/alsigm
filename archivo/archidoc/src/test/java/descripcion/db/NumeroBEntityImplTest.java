package descripcion.db;


import org.junit.Test;

import junit.framework.Assert;
import common.manager.ArchidocDBBaseTest;

public class NumeroBEntityImplTest extends ArchidocDBBaseTest {

	@Override
	protected INumeroDBEntity getDAO() {
		return getManager().getNumeroDBEntity();
	}

	@Test
	public void getMaxOrden(){
		int maxOrden = getDAO().getMaxOrden(idElementoCf, idCampo);
		Assert.assertEquals(ultimoOrden, maxOrden);
	}
}
