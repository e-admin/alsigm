package ieci.tecdoc.sgm.pe.ws.server;
/*
 * $Id: ListaLiquidaciones.java,v 1.1.4.1 2008/01/25 12:30:46 jconca Exp $
 */
import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class ListaLiquidaciones extends RetornoServicio {

	Liquidacion[] liquidaciones;

	public Liquidacion[] getLiquidaciones() {
		return liquidaciones;
	}

	public void setLiquidaciones(Liquidacion[] liquidaciones) {
		this.liquidaciones = liquidaciones;
	}
	
}
