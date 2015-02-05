/**
 * 
 */
package ieci.tecdoc.sgm.registro.util;

/**
 * @author IECISA
 * 
 *         Interfaz de una asociación entre el código GUID de un documento y el
 *         código CSV
 * 
 * 
 */
public interface RegistroDocumentoCSV {

	public void setGuid(String guid);

	public String getGuid();

	public void setCsv(String csv);

	public String getCsv();

	/**
	 * Recoge los valores de la instancia en una cadena xml
	 * 
	 * @param header
	 *            Si se incluye la cabecera
	 * @return los datos en formato xml
	 */
	public abstract String toXML(boolean header);

	/**
	 * Devuelve los valores de la instancia en una cadena de caracteres.
	 */
	public abstract String toString();

}
