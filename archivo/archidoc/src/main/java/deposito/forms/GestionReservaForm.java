package deposito.forms;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import deposito.actions.ErrorKeys;
import deposito.global.Constants;
import es.archigest.framework.web.action.ArchigestActionForm;

/**
 * Formulario para la recogida de datos en los procesos de reserva y ubicación
 * de relaciones
 */
public class GestionReservaForm extends ArchigestActionForm {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	String idrelacionseleccionada;
	String asignabledestino;
	String idasignabledestino;
	String idtipoasignabledestino;

	public String getIdasignabledestino() {
		return this.idasignabledestino;
	}

	public void setIdasignabledestino(String idasignabledestino) {
		this.idasignabledestino = idasignabledestino;
	}

	public String getIdtipoasignabledestino() {
		return this.idtipoasignabledestino;
	}

	public void setIdtipoasignabledestino(String idtipoasignabledestino) {
		this.idtipoasignabledestino = idtipoasignabledestino;
	}

	public String getAsignabledestino() {
		return this.asignabledestino;
	}

	public String getIdrelacionseleccionada() {
		return this.idrelacionseleccionada;
	}

	public void setIdrelacionseleccionada(String idrelacionseleccionada) {
		this.idrelacionseleccionada = idrelacionseleccionada;
	}

	public void setAsignabledestino(String asignabledestino) {
		this.asignabledestino = asignabledestino;
		if ((asignabledestino != null) && (asignabledestino.length() > 0)) {
			String ids[] = asignabledestino.split(Constants.DELIMITER_IDS);
			if (ids.length == 2) {
				setIdasignabledestino(ids[0].trim());
				setIdtipoasignabledestino(ids[1].trim());
			} else {
				System.err
						.print(this.getClass().getName()
								+ "::AsignableDestino no cumple el formato esperado(idAsignable:idTipo)");
			}
		}
	}

	public ActionErrors validateUbicacion() {
		ActionErrors errors = null;
		if (StringUtils.isBlank(getAsignabledestino())
				&& StringUtils.isBlank(getIdasignabledestino())) {
			errors = new ActionErrors();
			errors.add(
					ErrorKeys.ES_NECESARIO_SELECCIONAR_UNA_UBICACION_PARA_LOS_HUECOS_SIN_RESERVA_MESSAGE_KEY,
					new ActionError(
							ErrorKeys.ES_NECESARIO_SELECCIONAR_UNA_UBICACION_PARA_LOS_HUECOS_SIN_RESERVA_MESSAGE_KEY));
		}
		return errors;
	}

	public void clear() {
		idrelacionseleccionada = null;
		asignabledestino = null;
		idasignabledestino = null;
		idtipoasignabledestino = null;
	}
}