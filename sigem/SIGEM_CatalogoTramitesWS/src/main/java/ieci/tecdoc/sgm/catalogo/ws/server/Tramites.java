package ieci.tecdoc.sgm.catalogo.ws.server;
/*
 * $Id: Tramites.java,v 1.1.2.1 2008/01/25 12:25:07 jconca Exp $
 */
import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class Tramites extends RetornoServicio{
 
	private Tramite[] tramites;
	
	public void setTramites(Tramite[] tramites){
		this.tramites = tramites;
	}
	
	public Tramite[] getTramites(){
		return tramites;
	}
	
}