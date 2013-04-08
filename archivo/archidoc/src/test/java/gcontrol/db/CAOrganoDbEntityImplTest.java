package gcontrol.db;

import org.junit.Test;

import junit.framework.Assert;
import gcontrol.vos.CAOrganoVO;
import common.manager.ArchidocDBBaseTest;

public class CAOrganoDbEntityImplTest extends ArchidocDBBaseTest {

	@Override
	protected ICAOrganoDbEntity getDAO() {
		return getManager().getOrganoDbEntity();
	}

	@Test
	public void getCAOrgProductorVOByNombreLargo(){

		CAOrganoVO organoVO = getDAO().getCAOrgProductorVOByNombreLargo(nombreLargoOrgano);

		Assert.assertNotNull(organoVO);
		Assert.assertEquals(nombreLargoOrgano, organoVO.getNombreLargo());
	}
}
