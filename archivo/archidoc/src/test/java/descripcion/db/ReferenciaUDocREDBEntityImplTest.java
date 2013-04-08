package descripcion.db;

import junit.framework.Assert;

import org.junit.Test;

import common.manager.ArchidocDBBaseTest;

public class ReferenciaUDocREDBEntityImplTest extends ArchidocDBBaseTest {

	@Override
	protected IReferenciaDBEntity getDAO() {
		return getManager().getReferenciaUdocReDBEntity();
	}

	@Test
	public void getMaxOrden(){
		int maxOrden = getDAO().getMaxOrden(idUdocRe, idCampo);
		Assert.assertEquals(ultimoOrden, maxOrden);
	}
}
