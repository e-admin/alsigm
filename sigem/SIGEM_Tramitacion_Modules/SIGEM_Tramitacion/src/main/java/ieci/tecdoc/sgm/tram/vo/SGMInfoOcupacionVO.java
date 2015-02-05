package ieci.tecdoc.sgm.tram.vo;

import ieci.tecdoc.sgm.core.services.tramitacion.dto.InfoOcupacion;

public class SGMInfoOcupacionVO extends InfoOcupacion {

	/**
	 * Constructor.
	 */
	public SGMInfoOcupacionVO() {
		super();
	}

	/**
	 * Convierte un objeto
	 * @param obj Información del objeto.
	 * @return Objeto.
	 */
	public static SGMInfoOcupacionVO getInstance(ieci.tdw.ispac.services.dto.InfoOcupacion obj) {
		SGMInfoOcupacionVO res = null;
		
		if (obj != null) {
			res = new SGMInfoOcupacionVO();
			res.setEspacioOcupado(obj.getEspacioOcupado());
			res.setEspacioTotal(obj.getEspacioTotal());
			res.setNumeroFicheros(obj.getNumeroFicheros());
		}
		
		return res;
	}

}
