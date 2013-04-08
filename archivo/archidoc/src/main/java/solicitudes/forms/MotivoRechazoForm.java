package solicitudes.forms;

import solicitudes.vos.MotivoRechazoVO;
import es.archigest.framework.web.action.ArchigestActionForm;

/**
 * Formulario para la recogida de datos en la creación y edición de motivos de
 * rechazo
 */
public class MotivoRechazoForm extends ArchigestActionForm {

	private static final long serialVersionUID = 1L;

	String id;
	String motivo;
	int tipoSolicitud;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public int getTipoSolicitud() {
		return tipoSolicitud;
	}

	public void setTipoSolicitud(int tipoSolicitud) {
		this.tipoSolicitud = tipoSolicitud;
	}

	public void set(MotivoRechazoVO motivoVO) {
		reset();
		if (motivoVO != null) {
			this.id = motivoVO.getId();
			this.motivo = motivoVO.getMotivo();
			if (motivoVO.getTipoSolicitud() != null)
				this.tipoSolicitud = motivoVO.getTipoSolicitud().intValue();
		}
	}

	public void populate(MotivoRechazoVO motivoVO) {
		motivoVO.setId(this.id);
		motivoVO.setMotivo(this.motivo);
		if (this.tipoSolicitud > 0)
			motivoVO.setTipoSolicitud(new Integer(this.tipoSolicitud));
	}

	public void reset() {
		this.id = null;
		this.motivo = null;
		this.tipoSolicitud = 0;
	}
}