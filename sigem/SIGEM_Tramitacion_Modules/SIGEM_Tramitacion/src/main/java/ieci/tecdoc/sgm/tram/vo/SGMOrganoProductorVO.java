package ieci.tecdoc.sgm.tram.vo;

import ieci.tecdoc.sgm.core.services.tramitacion.dto.OrganoProductor;



/**
 * Información de un órgano productor.
 */
public class SGMOrganoProductorVO extends OrganoProductor {

	/**
	 * Constructor.
	 */
	public SGMOrganoProductorVO() {
		super();
	}

	/**
	 * Convierte un objeto
	 * @param obj Información del objeto.
	 * @return Objeto.
	 */
	public static SGMOrganoProductorVO getInstance(ieci.tdw.ispac.services.dto.OrganoProductor obj) {
		SGMOrganoProductorVO res = null;
		
		if (obj != null) {
			res = new SGMOrganoProductorVO();
			res.setId(obj.getId());
			res.setInicioProduccion(obj.getInicioProduccion());
		}
		
		return res;
	}

	/**
	 * Convierte un array de objetos
	 * @param objs Información del los objetos.
	 * @return Array de objetos.
	 */
	public static SGMOrganoProductorVO[] getInstances(ieci.tdw.ispac.services.dto.OrganoProductor[] objs) {
		SGMOrganoProductorVO[] res = null;
		
		if (objs != null) {
			res = new SGMOrganoProductorVO[objs.length];
			for (int i = 0; i < objs.length; i++) {
				res[i] = getInstance(objs[i]);
			}
		}
		
		return res;
	}
}
