package ieci.tecdoc.sgm.admin.beans;

import ieci.tecdoc.sgm.admin.interfaces.AccionMultientidad;
import ieci.tecdoc.sgm.base.xml.lite.XmlTextBuilder;

import java.io.Serializable;
import java.util.ArrayList;

public class AccionesMultientidad implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList acciones;

	public AccionesMultientidad() {
		acciones = new ArrayList();
	}

	/**
	 * Devuelve el número de objetos acción de la colección.
	 * 
	 * @return int Número de objetos acción de la colección.
	 */
	public int count() {
		return acciones.size();
	}

	/**
	 * Devuelve el objeto acción de la posición indicada dentro de la colección
	 * 
	 * @param index
	 *            Posición del objeto acción a recuperar.
	 * @return AccionMultientidad Accion de multientidad asociada al registro.
	 */
	public AccionMultientidad get(int index) {
		return (AccionMultientidad) acciones.get(index);
	}

	/**
	 * Añade una nueva acción a la colección.
	 * 
	 * @param accionMultientidad
	 *            Acción multientidad a añadir.
	 */
	public void add(AccionMultientidad accionMultientidad) {
		acciones.add(accionMultientidad);
	}

	/**
	 * Devuelve una cadena XML con los datos de las acciones de multientidad de
	 * la colección.
	 * 
	 * @param headline
	 *            Indica si el XML debe llevar cabecera.
	 * @return String Cadena XML con los datos de los mime types.
	 */
	public String toXML(boolean headline) {
		XmlTextBuilder bdr;
		String tagName = "Acciones";
		AccionMultientidad accionMultientidad;

		bdr = new XmlTextBuilder();
		if (headline)
			bdr.setStandardHeader();

		bdr.addOpeningTag(tagName);

		for (int i = 0; i < count(); i++) {
			accionMultientidad = get(i);

			bdr.addFragment(accionMultientidad.toXML(false));
		}

		bdr.addClosingTag(tagName);

		return bdr.getText();
	}

	/**
	 * Devuelve una cadena con los datos de las acciones de multientidad.
	 * 
	 * @return String Cadena tipo XML con los datos de las acciones de
	 *         multientidad.
	 */
	public String toString() {
		return toXML(false);
	}

}
