package ieci.tecdoc.sgm.catalogo.ws.server;
/*
 * $Id: TiposConectores.java,v 1.1.2.1 2008/01/25 12:25:06 jconca Exp $
 */
import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

/**
 * Contenedor de tipos de conectores
 *
 */
public class TiposConectores extends RetornoServicio
{

	private TipoConector[] tiposConectores;
	
	public TipoConector[] getTiposConectores(){
		return tiposConectores;
	}
	
	public  void setTiposConectores(TipoConector[] tiposConectores){
		this.tiposConectores = tiposConectores;
	}

}

