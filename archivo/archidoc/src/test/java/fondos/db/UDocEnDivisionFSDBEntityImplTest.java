package fondos.db;

import org.junit.Assert;
import org.junit.Test;

import common.manager.ArchidocDBBaseTest;

public class UDocEnDivisionFSDBEntityImplTest extends ArchidocDBBaseTest {

	@Override
	protected IUDocEnDivisionFSDbEntity getDAO() {
		return getManager().getuDocEnDivisionFSDbEntity();
	}

	@Test
	public void getCountUDocsEnDivisionFSVO(){
		int numUdocs = getDAO().getCountUDocsEnDivisionFSVO(idFS);
		Assert.assertEquals(2, numUdocs);
	}

}
