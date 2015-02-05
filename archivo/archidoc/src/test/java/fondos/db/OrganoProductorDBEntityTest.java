package fondos.db;

import org.junit.Assert;
import org.junit.Test;

import common.manager.ArchidocDBBaseTest;
import fondos.vos.OrganoProductorVO;

public class OrganoProductorDBEntityTest extends ArchidocDBBaseTest {

	@Override
	protected IOrganoProductorDbEntity getDAO() {
		return getManager().getOrganoProductorDbEntity();
	}


	@Test
	public void getOrgProductorXIdOrgano(){
		OrganoProductorVO organoProductor = getDAO().getOrgProductorXIdOrgano(idOrgano);
		Assert.assertNotNull(organoProductor);
	}
}
