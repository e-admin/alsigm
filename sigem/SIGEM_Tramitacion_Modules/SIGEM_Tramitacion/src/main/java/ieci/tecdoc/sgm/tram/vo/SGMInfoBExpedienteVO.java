package ieci.tecdoc.sgm.tram.vo;

import ieci.tecdoc.sgm.core.services.tramitacion.dto.InfoBExpediente;

/**
 * Información básica de un expediente.
 */
public class SGMInfoBExpedienteVO extends InfoBExpediente {

	/**
	 * Constructor.
	 */
	public SGMInfoBExpedienteVO() {
		super();
	}

	/**
	 * Convierte un objeto
	 * @param obj Información del objeto.
	 * @return Objeto.
	 */
	public static SGMInfoBExpedienteVO getInstance(ieci.tdw.ispac.services.dto.InfoBExpediente obj) {
		SGMInfoBExpedienteVO res = null;
		
		if (obj != null) {
			res = new SGMInfoBExpedienteVO();
			res.setId(obj.getId());
			res.setNumExp(obj.getNumExp());
			res.setDatosIdentificativos(obj.getDatosIdentificativos());
		}
		
		return res;
	}

	/**
	 * Convierte un array de objetos
	 * @param objs Información del los objetos.
	 * @return Array de objetos.
	 */
	public static SGMInfoBExpedienteVO[] getInstances(ieci.tdw.ispac.services.dto.InfoBExpediente[] objs) {
		SGMInfoBExpedienteVO[] res = null;
		
		if (objs != null) {
			res = new SGMInfoBExpedienteVO[objs.length];
			for (int i = 0; i < objs.length; i++) {
				res[i] = getInstance(objs[i]);
			}
		}
		
		return res;
	}
}
