package ieci.tecdoc.sgm.catalogo.ws.server;
/*
 * $Id: ConectoresAutenticacion.java,v 1.1.2.1 2008/01/25 12:25:07 jconca Exp $
 */
import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

/**
 * Contenedor de conectores de autenticación.
 * 
 * @author IECISA
 *
 */
public class ConectoresAutenticacion extends RetornoServicio
{

	private ConectorAutenticacion[] conectoresAutenticacion;
	
	public ConectorAutenticacion[] getConectoresAutenticacion(){
		return conectoresAutenticacion;
	}
	
	public void setConectoresAutenticacion (ConectorAutenticacion[] conectoresAutenticacion){
		this.conectoresAutenticacion = conectoresAutenticacion;
	}
	
}