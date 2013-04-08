package solicitudes.consultas.forms;

import solicitudes.consultas.ConsultasConstants;
import solicitudes.consultas.vos.MotivoConsultaVO;
import es.archigest.framework.web.action.ArchigestActionForm;

/**
 * Formulario para la recogida de datos en la creación y edición de motivos de
 * consultas
 */
public class MotivoConsultaForm extends ArchigestActionForm {

	private static final long serialVersionUID = 1L;

	String id;
	String motivo;
	int tipoEntidad;
	int tipoConsulta;
	int visibilidad;

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

	public int getTipoEntidad() {
		return tipoEntidad;
	}

	public void setTipoEntidad(int tipoEntidad) {
		this.tipoEntidad = tipoEntidad;
	}

	public int getTipoConsulta() {
		return tipoConsulta;
	}

	public void setTipoConsulta(int tipoConsulta) {
		this.tipoConsulta = tipoConsulta;
	}

	public int getVisibilidad() {
		return visibilidad;
	}

	public void setVisibilidad(int visibilidad) {
		this.visibilidad = visibilidad;
	}

	public void set(MotivoConsultaVO motivoVO) {
		reset();
		if (motivoVO != null) {
			this.id = motivoVO.getId();
			this.motivo = motivoVO.getMotivo();
			if (motivoVO.getTipoEntidad() != null)
				this.tipoEntidad = motivoVO.getTipoEntidad().intValue();
			if (motivoVO.getTipoConsulta() != null)
				this.tipoConsulta = motivoVO.getTipoConsulta().intValue();
			if (motivoVO.getVisibilidad() != null)
				this.visibilidad = motivoVO.getVisibilidad().intValue();
		}
	}

	public void populate(MotivoConsultaVO motivoVO) {
		motivoVO.setId(this.id);
		motivoVO.setMotivo(this.motivo);
		if (this.tipoEntidad > 0)
			motivoVO.setTipoEntidad(new Integer(this.tipoEntidad));
		if (this.tipoConsulta > 0)
			motivoVO.setTipoConsulta(new Integer(this.tipoConsulta));
		if (this.tipoEntidad == ConsultasConstants.TIPO_ENTIDAD_CONSULTORA_INVESTIGADOR_INT
				&& this.visibilidad > 0)
			motivoVO.setVisibilidad(new Integer(this.visibilidad));
	}

	public void reset() {
		this.id = null;
		this.motivo = null;
		this.tipoEntidad = 0;
		this.tipoConsulta = 0;
		this.visibilidad = 0;
	}
}