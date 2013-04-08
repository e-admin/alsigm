package ieci.tecdoc.sgm.tram.vo;

import ieci.tecdoc.sgm.core.services.tramitacion.dto.Emplazamiento;

/**
 * Información de un emplazamiento.
 */
public class SGMEmplazamientoVO extends Emplazamiento {

	/**
	 * Constructor.
	 */
	public SGMEmplazamientoVO() {
		super();
	}

	/**
	 * Convierte un objeto
	 * @param obj Información del objeto.
	 * @return Objeto.
	 */
	public static SGMEmplazamientoVO getInstance(ieci.tdw.ispac.services.dto.Emplazamiento obj) {
		SGMEmplazamientoVO res = null;
		
		if (obj != null) {
			res = new SGMEmplazamientoVO();
			res.setLocalizacion(obj.getLocalizacion());
			res.setPoblacion(obj.getPoblacion());
			res.setConcejo(obj.getConcejo());
			res.setComunidad(obj.getComunidad());
			res.setPais(obj.getPais());
		}
		
		return res;
	}

	/**
	 * Convierte un array de objetos
	 * @param objs Información del los objetos.
	 * @return Array de objetos.
	 */
	public static SGMEmplazamientoVO[] getInstances(ieci.tdw.ispac.services.dto.Emplazamiento[] objs) {
		SGMEmplazamientoVO[] res = null;
		
		if (objs != null) {
			res = new SGMEmplazamientoVO[objs.length];
			for (int i = 0; i < objs.length; i++) {
				res[i] = getInstance(objs[i]);
			}
		}
		
		return res;
	}
}
