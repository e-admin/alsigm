package transferencias.electronicas.common;

import java.util.Date;

import transferencias.TransferenciasElectronicasConstants;

import common.Constants;
import common.CustomDateConstants;
import common.util.DateUtils;

public class Fecha extends CampoFichaBase {
	private String formato = CustomDateConstants.DATE_FORMAT_DDMMAAAA;
	private String separador = Constants.SLASH;

	/**
	 * @return el formato
	 */
	public String getFormato() {
		return formato;
	}
	/**
	 * @param formato el formato a fijar
	 */
	public void setFormato(String formato) {
		this.formato = formato;
	}
	/**
	 * @return el separador
	 */
	public String getSeparador() {
		return separador;
	}
	/**
	 * @param separador el separador a fijar
	 */
	public void setSeparador(String separador) {
		this.separador = separador;
	}

	public Date asDate(){
		return DateUtils.getDate(getValor());
	}
	/**
	 * {@inheritDoc}
	 * @see transferencias.electronicas.ficha.ICampoFicha#getTipoElemento()
	 */
	public String getTipoElemento() {
		return TransferenciasElectronicasConstants.FECHA;
	}


}
