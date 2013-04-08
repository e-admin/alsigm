package ieci.tecdoc.sgm.catalogo.ws.server;
/*
 * $Id: Conectores.java,v 1.1.2.1 2008/01/25 12:25:07 jconca Exp $
 */
import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

/**
 * Contenedor de conectores
 *
 */
public class Conectores extends RetornoServicio
{

	private Conector[] conectores;
	
	public void setConectores(Conector[] conectores){
		this.conectores = conectores;
	}
	
	public Conector[] getConectores(){
		return conectores;
	}
	
}

