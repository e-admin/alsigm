package ieci.tecdoc.sgm.tram.vo;

import ieci.tecdoc.sgm.core.services.tramitacion.dto.Firma;

public class SGMFirmaVO extends Firma {

	/**
	 * Constructor.
	 */
	public SGMFirmaVO() {
		super();
	}

	/**
	 * Convierte un objeto
	 * @param obj Información del objeto.
	 * @return Objeto.
	 */
	public static SGMFirmaVO getInstance(ieci.tdw.ispac.services.dto.Firma obj) {
		SGMFirmaVO res = null;
		
		if (obj != null) {
			res = new SGMFirmaVO();
			res.setAutenticada(obj.isAutenticada());
			res.setAutor(obj.getAutor());
		}
		
		return res;
	}

	/**
	 * Convierte un array de objetos
	 * @param objs Información del los objetos.
	 * @return Array de objetos.
	 */
	public static SGMFirmaVO[] getInstances(ieci.tdw.ispac.services.dto.Firma[] objs) {
		SGMFirmaVO[] res = null;
		
		if (objs != null) {
			res = new SGMFirmaVO[objs.length];
			for (int i = 0; i < objs.length; i++) {
				res[i] = getInstance(objs[i]);
			}
		}
		
		return res;
	}

}
