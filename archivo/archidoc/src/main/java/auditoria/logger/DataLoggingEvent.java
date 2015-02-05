package auditoria.logger;

import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import common.Messages;
import common.vos.BaseVO;

/**
 * Clase que encapsula todos los datos complementario a un evento.
 */
public class DataLoggingEvent extends BaseVO {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Detalles asociados al evento. <detalle> <tipo_dato>codigo
	 * prevision</tipo_dato> <valor>2005/102</valor> </detalle>
	 */
	private Map detalles = new LinkedHashMap();
	/** Identificador del tipo de objeto afectado */
	private int object = 0;
	/** Identificador en la base de datos del objeto afectado */
	private String idObject = null;

	/**
	 * Constructor por defecto de los datos asociados al evento
	 */
	public DataLoggingEvent() {
	}

	/**
	 * Constructor por defecto de los datos asociados al evento
	 * 
	 * @param object
	 *            Tipo del objeto afectado
	 * @param idObject
	 *            Identifador en base de datos del objeto afectado
	 */
	public DataLoggingEvent(int object, String idObject) {
		this.object = object;
		this.idObject = idObject;
	}

	/**
	 * Añade un detalle al conjunto de detalles
	 * 
	 * @param tipo
	 *            Tipo de dato(Se utiliza como clave de indexacion). Tipos se
	 *            encuentran en @link auditoria.ArchivoDetails
	 * @param valor
	 *            Valor del tipo
	 */
	public DataLoggingEvent addDetalleNoVacio(Locale locale, String tipo,
			String valor, String addToTitle) {
		if (!StringUtils.isBlank(valor))
			return addDetalle(locale, tipo, valor, addToTitle);
		return this;
	}

	/**
	 * Añade un detalle al conjunto de detalles
	 * 
	 * @param tipo
	 *            Tipo de dato(Se utiliza como clave de indexacion). Tipos se
	 *            encuentran en @link auditoria.ArchivoDetails
	 * @param valor
	 *            Valor del tipo
	 */
	public DataLoggingEvent addDetalle(Locale locale, String tipo,
			String valor, String addToTitle) {
		if (StringUtils.isEmpty(addToTitle))
			addToTitle = "";
		if (valor == null)
			valor = "";
		detalles.put(Messages.getString(tipo, locale) + addToTitle, valor);
		return this;
	}

	/**
	 * Añade un detalle al conjunto de detalles
	 * 
	 * @param tipo
	 *            Tipo de dato(Se utiliza como clave de indexacion). Tipos se
	 *            encuentran en @link auditoria.ArchivoDetails
	 * @param valor
	 *            Valor del tipo
	 */
	public DataLoggingEvent addDetalle(Locale locale, String tipo, String valor) {
		return addDetalle(locale, tipo, valor, null);
	}

	/**
	 * Añade un detalle al conjunto de detalles
	 * 
	 * @param tipo
	 *            Tipo de dato(Se utiliza como clave de indexacion). Tipos se
	 *            encuentran en @link auditoria.ArchivoDetails
	 * @param valor
	 *            Valor del tipo
	 */
	public DataLoggingEvent addDetalleNoVacio(Locale locale, String tipo,
			String valor) {
		return addDetalleNoVacio(locale, tipo, valor, null);
	}

	/**
	 * Añade un detalle al conjunto de detalles
	 * 
	 * @param tipo
	 *            Tipo de dato(Se utiliza como clave de indexacion). Tipos se
	 *            encuentran en @link auditoria.ArchivoDetails
	 * @param valor
	 *            Valor del tipo
	 */
	public DataLoggingEvent addDetalleSinProperty(String tipo, String valor) {
		if (valor == null)
			valor = "";
		// quitar los dos puntos del final de la cadena de titulo de campo
		if (StringUtils.isNotEmpty(tipo)) {
			if (tipo.trim().substring(tipo.length() - 1).equals(":")) {
				tipo = tipo.trim().substring(0, tipo.length() - 1);
			}
		}
		detalles.put(tipo, valor);
		return this;
	}

	/**
	 * Añade un detalle al conjunto de detalles
	 * 
	 * @param tipo
	 *            Tipo de dato(Se utiliza como clave de indexacion). Tipos se
	 *            encuentran en @link auditoria.ArchivoDetails
	 * @param valor
	 *            Valor del tipo
	 */
	public DataLoggingEvent addDetalleNoVacioSinProperty(String tipo,
			String valor) {
		if (!StringUtils.isBlank(valor))
			return addDetalleSinProperty(tipo, valor);
		return this;
	}

	/**
	 * Añade un detalle numerado al conjunto de detalles
	 * 
	 * @param tipo
	 *            Tipo de dato(Se utiliza como clave de indexacion). Tipos se
	 *            encuentran en @link auditoria.ArchivoDetails
	 * @param num
	 *            Numero del detalle
	 * @param valor
	 *            Valor del tipo
	 */
	public DataLoggingEvent addDetalleNum(Locale locale, String tipo,
			String num, String valor) {
		if (valor == null)
			valor = "";
		detalles.put(Messages.getString(tipo, locale) + " " + num, valor);
		return this;
	}

	/**
	 * Elimina el valor del tipo indicado si está presente entre los detalles
	 * 
	 * @param tipo
	 *            tipo de dato que se desea obtener de los detalles
	 * @return Valor del tipo de dato
	 */
	public String removeDetalle(String tipo) {
		return (String) detalles.remove(tipo);
	}

	/**
	 * Devuelve el valor del tipo indicado o null si no está presente entre los
	 * detalles
	 * 
	 * @param tipo
	 *            tipo de dato que se desea obtener de los detalles
	 * @return Valor del tipo de dato
	 */
	public String getDetalle(String tipo) {
		return (String) detalles.get(tipo);
	}

	public String getIdObject() {
		return idObject;
	}

	public void setIdObject(String idObject) {
		this.idObject = idObject;
	}

	public int getObject() {
		return object;
	}

	public void setObject(int object) {
		this.object = object;
	}

	public Map getDetalles() {
		return detalles;
	}

	public void setDetalles(Hashtable detalles) {
		this.detalles = detalles;
	}
}
