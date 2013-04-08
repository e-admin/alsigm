package ieci.tecdoc.sgm.catalogo.ws.server;
/*
 * $Id: OrganosDestinatarios.java,v 1.1.2.1 2008/01/25 12:25:07 jconca Exp $
 */
import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

/**
 * Conetendor de órganos destinatarios
 *
 */
public class OrganosDestinatarios extends RetornoServicio
{
 
	private OrganoDestinatario[] organosDestinatarios;
	
	public OrganoDestinatario[] getOrganosDestinatarios(){
		return organosDestinatarios;
	}
	
	public void setOrganosDestinatarios(OrganoDestinatario[] organoDestinatarios){
		this.organosDestinatarios = organoDestinatarios;
	}
	
}
