package gcontrol.db;

import java.util.ArrayList;

import org.junit.Test;

import com.lowagie.text.List;

import common.db.IDBEntity;
import common.manager.ArchidocDBBaseTest;

public class OrganoUsuarioDBEntityImplTest extends ArchidocDBBaseTest{

	@Override
	protected IOrganoUsuarioDBEntity getDAO() {
		return getManager().getOrganoUsuarioDBEntity();
	}

	@Test
	public void getUsuariosValidosEnOrganos(){
		ArrayList list = new ArrayList();
		list.add("prueba");
		getDAO().getUsuariosValidosEnOrganos(list);
	}
}