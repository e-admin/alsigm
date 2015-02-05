package deposito.forms;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import common.ConfigConstants;
import common.Constants;
import common.forms.CustomForm;
import common.util.TypeConverter;

import deposito.DepositoConstants;

/**
 * Formulario para la recogida de datos en la gestión de los elementos
 * asignables que integran el depósito físico gestionado por el sistema
 */
public class ElementoAsignableForm extends CustomForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public String getTipoCambioNumeracion() {
		return tipoCambioNumeracion;
	}

	public void setTipoCambioNumeracion(String tipoCambioNumeracion) {
		this.tipoCambioNumeracion = tipoCambioNumeracion;
	}

	String id = null;
	String idPadre = null;
	String tipoElemento = null;
	String nombre = null;
	String pathName = null;
	String nombreTipoElemento = null;
	String longitud = null;
	boolean formatoRegular = false;
	String idFormato = null;
	String numeroHuecos = null;
	String numACrear = null;
	String selHuecos = null;

	String selectedHueco = null;
	String tipoCambioNumeracion = null;
	String numeracionAnteriorAEdionRegular = null;

	public String getLongitud() {
		return longitud;
	}

	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}

	public String getNumeroHuecos() {
		return numeroHuecos;
	}

	public void setNumeroHuecos(String numeroHuecos) {
		this.numeroHuecos = numeroHuecos;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdPadre() {
		return idPadre;
	}

	public void setIdPadre(String idPadre) {
		this.idPadre = idPadre;
	}

	public String getTipoElemento() {
		return tipoElemento;
	}

	public void setTipoElemento(String idTipoElemento) {
		this.tipoElemento = idTipoElemento;
	}

	public String getNumACrear() {
		return numACrear;
	}

	public void setNumACrear(String numACrear) {
		this.numACrear = numACrear;
	}

	public boolean isFormatoRegular() {
		return formatoRegular;
	}

	public void setFormatoRegular(boolean formatoRegular) {
		this.formatoRegular = formatoRegular;
	}

	public String getIdFormato() {
		return idFormato;
	}

	public void setIdFormato(String idFormato) {
		this.idFormato = idFormato;
	}

	public String getNombreTipoElemento() {
		return nombreTipoElemento;
	}

	public void setNombreTipoElemento(String nombreTipoElemento) {
		this.nombreTipoElemento = nombreTipoElemento;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPathName() {
		return pathName;
	}

	public void setPathName(String pathName) {
		this.pathName = pathName;
	}

	public String getSelHuecos() {
		return selHuecos;
	}

	public void setSelHuecos(String selHuecos) {
		this.selHuecos = selHuecos;
	}

	public String getSelectedHueco() {
		return selectedHueco;
	}

	public void setSelectedHueco(String selectedHueco) {
		this.selectedHueco = selectedHueco;
	}

	public int getFilaSelectedHueco() {
		return TypeConverter.toInt(selectedHueco);
	}

	public ActionErrors checkNumeracionRegular() {
		ActionErrors errors = new ActionErrors();
		int filaSeleccionada = getFilaSelectedHueco();
		checkNumeracionEnFila(filaSeleccionada, errors, null, true);
		return errors.size() > 0 ? errors : null;
	}

	public ActionErrors checkNumeracionIrregulares() {
		return checkNumeraciones();
	}

	public ActionErrors checkNumeraciones() {
		ActionErrors errors = new ActionErrors();
		HashSet sinRepetir = new HashSet();
		for (int fila = 1; fila <= getValues().size(); fila++) {
			checkNumeracionEnFila(fila, errors, sinRepetir, false);
		}
		return errors.size() > 0 ? errors : null;
	}

	// este metodo se llama tras tener recalculada la nueva numeracion y
	// seleccionar el tipo de cambio de numeracion
	public ActionErrors checkNumeracionRegularConTipoCambio() {
		ActionErrors errors = new ActionErrors();
		HashSet numeracionesSinRepetir = new HashSet();
		int numHuecosTotal = (int) (getValues().size() / 2);
		int indiceFinal = numHuecosTotal; // por estar generadas ambas
											// numeraciones
		if (tipoCambioNumeracion != null
				&& tipoCambioNumeracion.equals(Constants.PREFIX_R))
			indiceFinal = getFilaSelectedHueco() - 1; // solo tener en cuenta
														// las numeraciones
														// anteriores

		for (int i = 1; i <= indiceFinal; i++) {
			String numeracion = getMapFormValues("" + i);
			if (i != getFilaSelectedHueco())
				numeracionesSinRepetir.add(numeracion);
		}

		int fila = getFilaSelectedHueco();
		String value = (String) getMapFormValues("" + fila);
		if (tipoCambioNumeracion != null
				&& tipoCambioNumeracion.equals(Constants.PREFIX_R)) {
			long numeracion = Long.parseLong(value);
			for (long i = 0; i <= (numHuecosTotal - indiceFinal - 1); i++) {
				if (numeracionesSinRepetir.contains("" + (numeracion + i))) {
					// nunca deberia entrar por aqui
					errors.add(
							ActionErrors.GLOBAL_MESSAGE,
							new ActionError(
									DepositoConstants.ERROR_DEPOSITO_NUMERACION_REGULAR_FILA_REPETIDA,
									"" + (fila + i)));
				}

				if (("" + (numeracion + i)).length() > 16) {
					errors.add(
							ActionErrors.GLOBAL_MESSAGE,
							new ActionError(
									DepositoConstants.ERROR_NUMERACION_RECALCULO_EXCEDE_MAXIMO));
					break;
				}
			}
		} else {
			if (numeracionesSinRepetir.contains(value)) {
				errors.add(
						ActionErrors.GLOBAL_MESSAGE,
						new ActionError(
								DepositoConstants.ERROR_DEPOSITO_NUMERACION_REGULAR_FILA_REPETIDA,
								"" + fila));
			}
		}

		return errors.size() > 0 ? errors : null;
	}

	public final int NUM_CARS_NUMERACION_HUECOS_SIGNATURACION_ASOCIADA = 16;

	public void checkNumeracionEnFila(int fila, ActionErrors errors,
			Set numeracionesSinRepetir, boolean isRegular) {
		String value = (String) getMapFormValues("" + fila);
		if (isRegular) {
			numeracionesSinRepetir = new HashSet();
			for (int i = 1; i <= getFilaSelectedHueco() - 1; i++) {
				String numeracion = getMapFormValues("" + i);
				numeracionesSinRepetir.add(numeracion);
			}
		}

		if (StringUtils.isEmpty(value)) {
			if (isRegular)
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError(
						DepositoConstants.ERROR_EDITAR_NUMERACION_VALOR_VACIO));
			else
				errors.add(
						ActionErrors.GLOBAL_MESSAGE,
						new ActionError(
								DepositoConstants.ERROR_DEPOSITO_NUMERACION_IRREGULAR_FILA_VACIA,
								"" + fila));
			return;
		}

		if (!ConfigConstants.getInstance().getPermitirSignaturaAlfanumerica()) {
			if (!StringUtils.isNumeric(value)) {
				if (isRegular)
					errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError(
							Constants.ERROR_NUMERACION_ALFANUMERICA));
				else
					errors.add(
							ActionErrors.GLOBAL_MESSAGE,
							new ActionError(
									DepositoConstants.ERROR_NUMERACION_IRREGULAR_FILA_ALFANUMERICA,
									"" + fila));
				return;
			}
		}

		if (StringUtils.isNumeric(value)) {
			long numeracion = Long.parseLong(value);
			if (value.trim().length() > NUM_CARS_NUMERACION_HUECOS_SIGNATURACION_ASOCIADA) {
				errors.add(
						ActionErrors.GLOBAL_MESSAGE,
						new ActionError(
								DepositoConstants.ERROR_NUMERACION_HUECOS_DEMASIADO_ALTO,
								"" + fila,
								""
										+ NUM_CARS_NUMERACION_HUECOS_SIGNATURACION_ASOCIADA));
			} else if (numeracion == 0) {
				if (isRegular)
					errors.add(
							ActionErrors.GLOBAL_MESSAGE,
							new ActionError(
									DepositoConstants.ERROR_NUMERACION_REGULAR_NO_MAYOR_CERO));
				else
					errors.add(
							ActionErrors.GLOBAL_MESSAGE,
							new ActionError(
									DepositoConstants.ERROR_NUMERACION_IRREGULAR_FILA_NO_MAYOR_CERO,
									"" + fila));
				return;
			}
		}

		if (!StringUtils.isNumeric(value.trim())
				&& !StringUtils.isAlphanumeric(value.trim())) {
			if (isRegular)
				errors.add(
						ActionErrors.GLOBAL_MESSAGE,
						new ActionError(
								DepositoConstants.ERROR_NUMERACION_REGULAR_NO_MAYOR_CERO));
			else
				errors.add(
						ActionErrors.GLOBAL_MESSAGE,
						new ActionError(
								DepositoConstants.ERROR_NUMERACION_IRREGULAR_FILA_NO_MAYOR_CERO,
								"" + fila));
			return;
		}

		if (numeracionesSinRepetir.contains(value)) {
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							DepositoConstants.ERROR_DEPOSITO_NUMERACION_IRREGULAR_FILA_REPETIDA,
							"" + fila));
		}

		if (!isRegular)
			numeracionesSinRepetir.add(value);
	}

	public String getNumeracionAnteriorAEdionRegular() {
		return numeracionAnteriorAEdionRegular;
	}

	public void setNumeracionAnteriorAEdionRegular(
			String numeracionAnteriorAEdionRegular) {
		this.numeracionAnteriorAEdionRegular = numeracionAnteriorAEdionRegular;
	}

	// public getNumeracionHuecos(String key){
	// return
	// }
}
