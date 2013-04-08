package ieci.tecdoc.sgm.tram.vo;

import ieci.tecdoc.sgm.core.services.tramitacion.dto.DocElectronico;

/**
 * Información de un documento electrónico.
 */
public class SGMDocElectronicoVO extends DocElectronico {

	/**
	 * Constructor.
	 */
	public SGMDocElectronicoVO() {
		super();
	}

	/**
	 * Convierte un objeto
	 * @param obj Información del objeto.
	 * @return Objeto.
	 */
	public static SGMDocElectronicoVO getInstance(ieci.tdw.ispac.services.dto.DocElectronico obj) {
		SGMDocElectronicoVO res = null;
		
		if (obj != null) {
			res = new SGMDocElectronicoVO();
			res.setAsunto(obj.getAsunto());
			res.setTipoDocumento(obj.getTipoDocumento());
			res.setExtension(obj.getExtension());
			res.setLocalizador(obj.getLocalizador());
			res.setRepositorio(obj.getRepositorio());
		}
		
		return res;
	}

	/**
	 * Convierte un array de objetos
	 * @param objs Información del los objetos.
	 * @return Array de objetos.
	 */
	public static SGMDocElectronicoVO[] getInstances(ieci.tdw.ispac.services.dto.DocElectronico[] objs) {
		SGMDocElectronicoVO[] res = null;
		
		if (objs != null) {
			res = new SGMDocElectronicoVO[objs.length];
			for (int i = 0; i < objs.length; i++) {
				res[i] = getInstance(objs[i]);
			}
		}
		
		return res;
	}
}
