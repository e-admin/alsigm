package ieci.tecdoc.sgm.tram.vo;

import ieci.tecdoc.sgm.core.services.tramitacion.dto.Expediente;

/**
 * Información de un expediente.
 */
public class SGMExpedienteVO extends Expediente {

	/**
	 * Constructor.
	 */
	public SGMExpedienteVO() {
		super();
	}

	/**
	 * Convierte un objeto
	 * @param obj Información del objeto.
	 * @return Objeto.
	 */
	public static SGMExpedienteVO getInstance(ieci.tdw.ispac.services.dto.Expediente obj) {
		SGMExpedienteVO res = null;
		
		if (obj != null) {
			res = new SGMExpedienteVO();
			res.getInformacionBasica().setId(obj.getInformacionBasica().getId());
			res.getInformacionBasica().setNumExp(obj.getInformacionBasica().getNumExp());
			res.getInformacionBasica().setDatosIdentificativos(obj.getInformacionBasica().getDatosIdentificativos());
			res.setFechaInicio(obj.getFechaInicio());
			res.setFechaFinalizacion(obj.getFechaFinalizacion());
			res.setIdOrgProductor(obj.getIdOrgProductor());
			res.setNombreOrgProductor(obj.getNombreOrgProductor());
			res.setAsunto(obj.getAsunto());
			res.setInteresados(SGMInteresadoVO.getInstances(obj.getInteresados()));
			res.setEmplazamientos(SGMEmplazamientoVO.getInstances(obj.getEmplazamientos()));
			res.setDocumentosFisicos(SGMDocFisicoVO.getInstances(obj.getDocumentosFisicos()));
			res.setDocumentosElectronicos(SGMDocElectronicoVO.getInstances(obj.getDocumentosElectronicos()));
		}
		
		return res;
	}

}
