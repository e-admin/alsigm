package ieci.tecdoc.sgm.tram.vo;

import ieci.tecdoc.sgm.core.services.tramitacion.dto.InfoFichero;

public class SGMInfoFicheroVO extends InfoFichero {

	/**
	 * Constructor.
	 */
	public SGMInfoFicheroVO() {
		super();
	}
	
	/**
	 * Convierte un objeto
	 * @param obj Información del objeto.
	 * @return Objeto.
	 */
	public static SGMInfoFicheroVO getInstance(ieci.tdw.ispac.services.dto.InfoFichero obj) {
		SGMInfoFicheroVO res = null;
		
		if (obj != null) {
			res = new SGMInfoFicheroVO();
			res.setNombre(obj.getNombre());
			res.setFechaAlta(obj.getFechaAlta());
			res.setFirmas(SGMFirmaVO.getInstances(obj.getFirmas()));
		}
		
		return res;
	}

}
