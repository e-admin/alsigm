package descripcion.db;

import junit.framework.Assert;

import org.junit.Test;

import common.manager.ArchidocDBBaseTest;

public class TextoLargoUDocREDBEntityImplTest extends ArchidocDBBaseTest {

	@Override
	protected ITextoDBEntity getDAO() {
		return getManager().getTextoLargoUdocReDBEntity();
	}

	@Test
	public void getMaxOrden(){
		int maxOrden = getDAO().getMaxOrden(idUdocRe, idCampo);
		Assert.assertEquals(ultimoOrden, maxOrden);
	}
}
