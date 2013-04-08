package ieci.tecdoc.sgm.tram.vo;

import ieci.tecdoc.sgm.core.services.tramitacion.dto.Interesado;

/**
 * Información de un interesado.
 */
public class SGMInteresadoVO extends Interesado {
	
	/**
	 * Constructor.
	 */
	public SGMInteresadoVO() {
		super();
	}

	/**
	 * Convierte un objeto
	 * @param obj Información del objeto.
	 * @return Objeto.
	 */
	public static SGMInteresadoVO getInstance(ieci.tdw.ispac.services.dto.Interesado obj) {
		SGMInteresadoVO res = null;
		
		if (obj != null) {
			res = new SGMInteresadoVO();
			res.setIdEnTerceros(obj.getIdEnTerceros());
			res.setInteresadoPrincipal(obj.isInteresadoPrincipal());
			res.setNumIdentidad(obj.getNumIdentidad());
			res.setNombre(obj.getNombre());
			res.setRol(obj.getRol());
		}
		
		return res;
	}

	/**
	 * Convierte un array de objetos
	 * @param objs Información del los objetos.
	 * @return Array de objetos.
	 */
	public static SGMInteresadoVO[] getInstances(ieci.tdw.ispac.services.dto.Interesado[] objs) {
		SGMInteresadoVO[] res = null;
		
		if (objs != null) {
			res = new SGMInteresadoVO[objs.length];
			for (int i = 0; i < objs.length; i++) {
				res[i] = getInstance(objs[i]);
			}
		}
		
		return res;
	}
}
