package ieci.tecdoc.sgm.tram.vo;

import ieci.tecdoc.sgm.core.services.tramitacion.dto.InfoBProcedimiento;

/**
 * Información básica de un procedimiento.
 */
public class SGMInfoBProcedimientoVO extends InfoBProcedimiento {

	/**
	 * Constructor.
	 */
	public SGMInfoBProcedimientoVO() {
		super();
	}

	/**
	 * Convierte un objeto
	 * @param obj Información del objeto.
	 * @return Objeto.
	 */
	public static SGMInfoBProcedimientoVO getInstance(ieci.tdw.ispac.services.dto.InfoBProcedimiento obj) {
		SGMInfoBProcedimientoVO res = null;
		
		if (obj != null) {
			res = new SGMInfoBProcedimientoVO();
			res.setId(obj.getId());
			res.setCodigo(obj.getCodigo());
			res.setNombre(obj.getNombre());
			res.setCodSistProductor(obj.getCodSistProductor());
			res.setNombreSistProductor(obj.getNombreSistProductor());
		}
		
		return res;
	}

	/**
	 * Convierte un array de objetos
	 * @param objs Información del los objetos.
	 * @return Array de objetos.
	 */
	public static SGMInfoBProcedimientoVO[] getInstances(ieci.tdw.ispac.services.dto.InfoBProcedimiento[] objs) {
		SGMInfoBProcedimientoVO[] res = null;
		
		if (objs != null) {
			res = new SGMInfoBProcedimientoVO[objs.length];
			for (int i = 0; i < objs.length; i++) {
				res[i] = getInstance(objs[i]);
			}
		}
		
		return res;
	}
}
