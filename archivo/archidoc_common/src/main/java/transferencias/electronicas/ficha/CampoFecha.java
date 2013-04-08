package transferencias.electronicas.ficha;

import java.text.ParseException;
import java.util.Date;

import org.apache.log4j.Logger;

import transferencias.TransferenciasElectronicasConstants;
import transferencias.electronicas.common.CampoFichaBase;

import common.util.CustomDateFormat;
import common.util.DateUtils;

public class CampoFecha extends CampoFichaBase{
	private String formato;
	private String separador;
	private String calificador;

	/** Logger de la clase */
	static Logger logger = Logger
			.getLogger(CampoFecha.class);

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
	/**
	 * @return el calificador
	 */
	public String getCalificador() {
		return calificador;
	}
	/**
	 * @param calificador el calificador a fijar
	 */
	public void setCalificador(String calificador) {
		this.calificador = calificador;
	}

	public Date getDate() throws ParseException{
		//Si no es formato siglo
		if(!CustomDateFormat.DATE_FORMAT_S.equals(formato)){
			return DateUtils.getDate(getValor(), getFormato(), getSeparador());
		}
		return null;
	}
	public String getAnio() {
		try {
			return DateUtils.getAnio(getDate());
		} catch (ParseException e) {
			logger.error(e);
		}
		return null;
	}
	public String getMes() {
		try {
			return DateUtils.getMes(getDate());
		} catch (ParseException e) {
			logger.error(e);
		}
		return null;
	}
	public String getDia() {
		try {
			return DateUtils.getDia(getDate());
		} catch (ParseException e) {
			logger.error(e);
		}
		return null;
	}
	public String getSiglo() {
		if(CustomDateFormat.DATE_FORMAT_S.equals(formato)){
			return getValor();
		}
		return null;
	}

	public String getTipoElemento() {
		return TransferenciasElectronicasConstants.CAMPO_FECHA;
	}
}
