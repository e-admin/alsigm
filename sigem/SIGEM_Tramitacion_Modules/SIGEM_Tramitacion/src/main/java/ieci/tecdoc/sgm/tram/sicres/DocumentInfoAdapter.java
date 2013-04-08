package ieci.tecdoc.sgm.tram.sicres;

import org.apache.commons.lang.StringUtils;

import ieci.tecdoc.sgm.core.services.registro.DocumentInfo;;

public class DocumentInfoAdapter extends DocumentInfo {

	public DocumentInfoAdapter(ieci.tdw.ispac.ispaclib.sicres.vo.DocumentInfo docInfo) {
		setDocumentContent(docInfo.getData());
		setFileName(docInfo.getName());
		setPageName(docInfo.getName());
		setExtension(StringUtils.substring(docInfo.getName(), StringUtils.lastIndexOf(docInfo.getName(), '.')+1));
		setDocumentName(docInfo.getId());		
	}
	
}
