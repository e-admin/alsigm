package solicitudes.prestamos.forms;

import solicitudes.prestamos.PrestamosConstants;
import solicitudes.prestamos.vos.MotivoPrestamoVO;
import es.archigest.framework.web.action.ArchigestActionForm;

/**
 * Formulario para la recogida de datos en la creación y edición de motivos de
 * prestamos
 */
public class MotivoPrestamoForm extends ArchigestActionForm {

	private static final long serialVersionUID = 1L;

	String id;
	String motivo;
	int tipoUsuario;
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

	public int getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(int tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public int getVisibilidad() {
		return visibilidad;
	}

	public void setVisibilidad(int visibilidad) {
		this.visibilidad = visibilidad;
	}

	public void set(MotivoPrestamoVO motivoVO) {
		reset();
		if (motivoVO != null) {
			this.id = motivoVO.getId();
			this.motivo = motivoVO.getMotivo();
			if (motivoVO.getTipoUsuario() != null)
				this.tipoUsuario = motivoVO.getTipoUsuario().intValue();
			if (motivoVO.getVisibilidad() != null)
				this.visibilidad = motivoVO.getVisibilidad().intValue();
		}
	}

	public void populate(MotivoPrestamoVO motivoVO) {
		motivoVO.setId(this.id);
		motivoVO.setMotivo(this.motivo);
		if (this.tipoUsuario > 0)
			motivoVO.setTipoUsuario(new Integer(this.tipoUsuario));
		if (this.tipoUsuario == PrestamosConstants.TIPO_INTERNO_INT
				&& this.visibilidad > 0)
			motivoVO.setVisibilidad(new Integer(this.visibilidad));
	}

	public void reset() {
		this.id = null;
		this.motivo = null;
		this.tipoUsuario = 0;
		this.visibilidad = 0;
	}
}