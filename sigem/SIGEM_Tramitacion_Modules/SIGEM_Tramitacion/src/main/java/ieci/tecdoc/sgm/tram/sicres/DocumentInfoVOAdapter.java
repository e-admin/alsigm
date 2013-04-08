package ieci.tecdoc.sgm.tram.sicres;

import ieci.tdw.ispac.ispaclib.sicres.vo.DocumentInfo;
import ieci.tecdoc.sgm.core.services.registro.Document;
import ieci.tecdoc.sgm.core.services.registro.Page;

public class DocumentInfoVOAdapter extends DocumentInfo {

	public DocumentInfoVOAdapter(Page page) {
		setId(page.getPageID());
		setName(page.getPageName());
	}
}
