package descripcion.db;

import junit.framework.Assert;

import org.junit.Test;

import common.manager.ArchidocDBBaseTest;

public class ReferenciaDBEntityImplTest extends ArchidocDBBaseTest {

	@Override
	protected IReferenciaDBEntity getDAO() {
		return getManager().getReferenciaDBEntity();
	}

	@Test
	public void getMaxOrden(){
		int maxOrden = getDAO().getMaxOrden(idElementoCf, idCampo);
		Assert.assertEquals(ultimoOrden, maxOrden);
	}
}
