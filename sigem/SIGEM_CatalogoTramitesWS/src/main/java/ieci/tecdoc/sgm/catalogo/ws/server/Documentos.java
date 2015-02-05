package ieci.tecdoc.sgm.catalogo.ws.server;
/*
 * $Id: Documentos.java,v 1.1.2.1 2008/01/25 12:25:07 jconca Exp $
 */
import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class Documentos extends RetornoServicio{
	
	private DocumentoExtendido[] documentos;
	
	public void setDocumentos(DocumentoExtendido[] documentos){
		this.documentos = documentos;
	}
	
	public DocumentoExtendido[] getDocumentos(){
		return documentos;
	}
	
}