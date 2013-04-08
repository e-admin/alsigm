package ieci.tecdoc.sgm.tram.vo;

import ieci.tecdoc.sgm.core.services.tramitacion.dto.DocFisico;

/**
 * Información de un documento físico.
 */
public class SGMDocFisicoVO extends DocFisico {

	/**
	 * Constructor.
	 */
	public SGMDocFisicoVO() {
		super();
	}

	/**
	 * Convierte un objeto
	 * @param obj Información del objeto.
	 * @return Objeto.
	 */
	public static SGMDocFisicoVO getInstance(ieci.tdw.ispac.services.dto.DocFisico obj) {
		SGMDocFisicoVO res = null;
		
		if (obj != null) {
			res = new SGMDocFisicoVO();
			res.setAsunto(obj.getAsunto());
			res.setTipoDocumento(obj.getTipoDocumento());
		}
		
		return res;
	}

	/**
	 * Convierte un array de objetos
	 * @param objs Información del los objetos.
	 * @return Array de objetos.
	 */
	public static SGMDocFisicoVO[] getInstances(ieci.tdw.ispac.services.dto.DocFisico[] objs) {
		SGMDocFisicoVO[] res = null;
		
		if (objs != null) {
			res = new SGMDocFisicoVO[objs.length];
			for (int i = 0; i < objs.length; i++) {
				res[i] = getInstance(objs[i]);
			}
		}
		
		return res;
	}
}
