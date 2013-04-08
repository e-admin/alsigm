package es.ieci.tecdoc.fwktd.sir.ws.utils;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import es.ieci.tecdoc.fwktd.sir.api.types.EstadoTrazabilidadEnum;
import es.ieci.tecdoc.fwktd.sir.ws.service.TrazabilidadDTO;

public class TrazasUtils {
	public static boolean validarTraza(TrazabilidadDTO trazabilidadAComprobar,
			String codigoEntidadRegistralOrigen,String descripcionEntidadRegistralOrigen,
			String unidadTramitacionOrigen,String descripcionUnidadTramitacionOrigen,
			String codigoEntidadRegistralDestino,String descripcionEntidadRegistralDestino,
			String unidadTramitacionDestino,String descripcionUnidadTramitacionDestino){
		if(trazabilidadAComprobar==null){
			return false;
		}
		return compareStrings(trazabilidadAComprobar.getCodigoEntidadRegistralOrigen(),codigoEntidadRegistralOrigen) &&
			   //compareStrings(trazabilidadAComprobar.getDescripcionEntidadRegistralOrigen(),descripcionEntidadRegistralOrigen) &&
			   //compareStrings(trazabilidadAComprobar.getCodigoUnidadTramitacionOrigen(),unidadTramitacionOrigen) &&
			   //compareStrings(trazabilidadAComprobar.getDescripcionUnidadTramitacionOrigen(),descripcionUnidadTramitacionOrigen) &&
			   compareStrings(trazabilidadAComprobar.getCodigoEntidadRegistralDestino(),codigoEntidadRegistralDestino) 
			   //&& compareStrings(trazabilidadAComprobar.getDescripcionEntidadRegistralDestino(),descripcionEntidadRegistralDestino) &&
			   //compareStrings(trazabilidadAComprobar.getCodigoUnidadTramitacionDestino(),unidadTramitacionDestino) 
			   //&&compareStrings(trazabilidadAComprobar.getDescripcionUnidadTramitacionDestino(),descripcionUnidadTramitacionDestino)
			   ;
	}
	
	private static boolean compareStrings(String a,String b){
		return StringUtils.equalsIgnoreCase(a,b);
	}
	
	public static TrazabilidadDTO buscarTrazaAnterior(List<TrazabilidadDTO> trazas,EstadoTrazabilidadEnum estado){
		return buscarTrazaAnterior(trazas,estado);
	}
	
	public static TrazabilidadDTO buscarTrazaAnterior(List<TrazabilidadDTO> trazas,EstadoTrazabilidadEnum estado,Date fechaHasta,String codigoEntidadRegistralOrigen,String codigoEntidadRegistralDestino){
		for(int i=0;i<trazas.size();i++){
			TrazabilidadDTO traza=trazas.get(i);
			Date fechaTraza=traza.getFechaModificacion().toGregorianCalendar().getTime();
			
			if(fechaHasta!=null && fechaTraza.after(fechaHasta)){ 
				continue; 
			}
			if(traza.getCodigoEstado().equals(estado.getValue())&&traza.getCodigoEntidadRegistralOrigen().equalsIgnoreCase(codigoEntidadRegistralOrigen) && traza.getCodigoEntidadRegistralDestino().equalsIgnoreCase(codigoEntidadRegistralDestino)){	
				return traza;
			}
		}
		return null;
	}
	
	public static boolean trazaAnterior(TrazabilidadDTO traza,Date fechaHasta){
		Date fechaTraza=traza.getFechaModificacion().toGregorianCalendar().getTime();
		return fechaHasta!=null && fechaTraza.before(fechaHasta);
	}
}
