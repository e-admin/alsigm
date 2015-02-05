package deposito.db;

import org.junit.Assert;
import org.junit.Test;

import common.manager.ArchidocDBBaseTest;

import deposito.vos.UDocEnUiDepositoVO;

public class UDocEnUiDepositoDbEntityImplTest extends ArchidocDBBaseTest{

	@Override
	protected IUDocEnUiDepositoDbEntity getDAO() {
		return getManager().getuDocEnUiDepositoDbEntity();
	}

	@Test
	public void getUdocByIdUnidadDoc(){
		UDocEnUiDepositoVO udoc = getDAO().getUdocByIdUnidadDoc(idUnidadDoc);
		Assert.assertNotNull(udoc);
		Assert.assertEquals(idUnidadDoc, udoc.getIdunidaddoc());
	}
}
