package ieci.tecdoc.sgm.tram.vo;

import ieci.tecdoc.sgm.core.services.tramitacion.dto.Procedimiento;


/**
 * Información de un procedimiento.
 */
public class SGMProcedimientoVO extends Procedimiento {
	
	/**
	 * Constructor.
	 */
	public SGMProcedimientoVO() {
		super();
	}

	/**
	 * Convierte un objeto
	 * @param obj Información del objeto.
	 * @return Objeto.
	 */
	public static SGMProcedimientoVO getInstance(ieci.tdw.ispac.services.dto.Procedimiento obj) {
		SGMProcedimientoVO res = null;
		
		if (obj != null) {
			res = new SGMProcedimientoVO();
			res.getInformacionBasica().setId(obj.getInformacionBasica().getId());
			res.getInformacionBasica().setCodigo(obj.getInformacionBasica().getCodigo());
			res.getInformacionBasica().setNombre(obj.getInformacionBasica().getNombre());
			res.getInformacionBasica().setCodSistProductor(obj.getInformacionBasica().getCodSistProductor());
			res.getInformacionBasica().setNombreSistProductor(obj.getInformacionBasica().getNombreSistProductor());
			res.setDocumentosBasicos(obj.getDocumentosBasicos());
			res.setNormativa(obj.getNormativa());
			res.setObjeto(obj.getObjeto());
			res.setTramites(obj.getTramites());
			res.setOrganosProductores(SGMOrganoProductorVO.getInstances(obj.getOrganosProductores()));
		}
		
		return res;
	}
}