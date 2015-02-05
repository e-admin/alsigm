package descripcion.model.xml.definition;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import common.Constants;

import descripcion.model.xml.XmlElement;

/**
 * Clase que almacena la información de un área de la definición de la ficha
 * ISAD(G).
 */
public class DefArea extends XmlElement {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Identificador del área asociada. */
	private String idAreaAsociada = null;

	/** Lista de campos del área. */
	private List campos = new LinkedList();

	/**
	 * Constructor.
	 */
	public DefArea() {
		super();
	}

	/**
	 * Obtiene el identificador del área asociada.
	 * 
	 * @return Identificador del área asociada.
	 */
	public String getIdAreaAsociada() {
		return idAreaAsociada;
	}

	/**
	 * Establece el identificador del área asociada.
	 * 
	 * @param idAreaAsociada
	 *            Identificador del área asociada.
	 */
	public void setIdAreaAsociada(String idAreaAsociada) {
		this.idAreaAsociada = idAreaAsociada;
	}

	/**
	 * Añade un campo a la lista.
	 * 
	 * @param campo
	 *            Campo.
	 */
	public void addCampo(DefCampo campo) {
		campos.add(campo);
	}

	/**
	 * Obtiene un campo de la lista en función de su posición.
	 * 
	 * @param index
	 *            Posición del campo dentro de la lista.
	 * @return DefCampo.
	 */
	public DefCampo getCampo(int index) {
		return (DefCampo) campos.get(index);
	}

	/**
	 * Obtiene la lista de campos.
	 * 
	 * @return Lista de campos.
	 */
	public DefCampo[] getCampos() {
		return (DefCampo[]) campos.toArray(new DefCampo[campos.size()]);
	}

	/**
	 * Obtiene el número de campos de la lista.
	 * 
	 * @return Número de campos.
	 */
	public int getTotalCampos() {
		return campos.size();
	}

	/**
	 * Elimina el campo de la posición determinada.
	 * 
	 * @param index
	 *            Posición del campo a eliminar.
	 */
	public void removeCampo(int index) {
		campos.remove(index);
	}

	/**
	 * Elimina el campo de la lista.
	 * 
	 * @param campo
	 *            Campo a eliminar.
	 */
	public void removeCampo(DefCampo campo) {
		campos.remove(campo);
	}

	/**
	 * Elimina todos las campos de la lista.
	 */
	public void clearCampos() {
		campos.clear();
	}

	/**
	 * Obtiene una representación XML del objeto.
	 * 
	 * @param indent
	 *            Número de espacios de tabulación.
	 * @return Representación del objeto.
	 */
	public String toXML(int indent) {
		final StringBuffer xml = new StringBuffer();
		final String tabs = StringUtils.repeat(" ", indent);

		// Área
		xml.append(tabs + "<" + DefTags.TAG_AREA + ">");
		xml.append(Constants.NEWLINE);

		// Id_Area_Asociada
		xml.append(tabs + "  <" + DefTags.TAG_ID_AREA_ASOCIADA + ">");
		xml.append(idAreaAsociada != null ? idAreaAsociada : "");
		xml.append("</" + DefTags.TAG_ID_AREA_ASOCIADA + ">");
		xml.append(Constants.NEWLINE);

		// Campos
		if (campos.size() > 0) {
			xml.append(tabs + "  <" + DefTags.TAG_CAMPOS + ">");
			xml.append(Constants.NEWLINE);

			for (int i = 0; i < campos.size(); i++)
				xml.append(getCampo(i).toXML(indent + 4));

			xml.append(tabs + "  </" + DefTags.TAG_CAMPOS + ">");
			xml.append(Constants.NEWLINE);
		}

		xml.append(tabs + "</" + DefTags.TAG_AREA + ">");
		xml.append(Constants.NEWLINE);

		return xml.toString();
	}
}
