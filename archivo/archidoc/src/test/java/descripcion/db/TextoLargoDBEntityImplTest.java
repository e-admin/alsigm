package descripcion.db;

import junit.framework.Assert;

import org.junit.Test;

import common.manager.ArchidocDBBaseTest;

public class TextoLargoDBEntityImplTest extends ArchidocDBBaseTest {

	@Override
	protected ITextoDBEntity getDAO() {
		return getManager().getTextoLargoDbEntity();
	}

	@Test
	public void getMaxOrden(){
		int maxOrden = getDAO().getMaxOrden(idElementoCf, idCampo);
		Assert.assertEquals(ultimoOrden, maxOrden);
	}

}
