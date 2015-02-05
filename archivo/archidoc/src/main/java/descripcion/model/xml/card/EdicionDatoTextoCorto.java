package descripcion.model.xml.card;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import common.Constants;
import common.db.DBUtils;

import descripcion.model.xml.definition.DefTipos;

/**
 * Clase que almacena la información de edición de un texto corto.
 */
public class EdicionDatoTextoCorto extends EdicionDato {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Identificador de la tabla de validación. */
	private String idTblVld = null;

	/** Lista de valores posibles del campo. */
	private List opciones = new LinkedList();

	/**
	 * Constructor.
	 */
	public EdicionDatoTextoCorto() {
		super(DefTipos.TIPO_DATO_TEXTO_CORTO);
	}

	/**
	 * Obtiene el identificador de la tabla de validación.
	 * 
	 * @return Identificador de la tabla de validación.
	 */
	public String getIdTblVld() {
		return idTblVld;
	}

	/**
	 * Establece el identificador de la tabla de validación.
	 * 
	 * @param idTblVld
	 *            Identificador de la tabla de validación.
	 */
	public void setIdTblVld(String idTblVld) {
		this.idTblVld = idTblVld;
	}

	/**
	 * Añade una opción a la lista.
	 * 
	 * @param opcion
	 *            Opción.
	 */
	public void addOpcion(Opcion opcion) {
		opciones.add(opcion);
	}

	/**
	 * Obtiene una opción de la lista en función de su posición.
	 * 
	 * @param index
	 *            Posición de la opción dentro de la lista.
	 * @return Opción.
	 */
	public Opcion getOpcion(int index) {
		return (Opcion) opciones.get(index);
	}

	/**
	 * Obtiene la lista de opciones.
	 * 
	 * @return Lista de opciones.
	 */
	public Opcion[] getOpciones() {
		return (Opcion[]) opciones.toArray(new Opcion[opciones.size()]);
	}

	/**
	 * Obtiene el número de opciones de la lista.
	 * 
	 * @return Número de opciones.
	 */
	public int getTotalOpciones() {
		return opciones.size();
	}

	/**
	 * Elimina la opción de la posición determinada.
	 * 
	 * @param index
	 *            Posición de la opción a eliminar.
	 */
	public void removeOpcion(int index) {
		opciones.remove(index);
	}

	/**
	 * Elimina la opción de la lista.
	 * 
	 * @param opcion
	 *            Opción a eliminar.
	 */
	public void removeOpcion(Opcion opcion) {
		opciones.remove(opcion);
	}

	/**
	 * Elimina todas las opciones de la lista.
	 */
	public void clearOpciones() {
		opciones.clear();
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

		// Tag de inicio
		xml.append(tabs + "<" + TagsFicha.TAG_EDICION + ">");
		xml.append(Constants.NEWLINE);

		// Id
		xml.append(tabs + "  <" + TagsFicha.TAG_EDICION_ID + ">");
		if (getId() != null)
			xml.append(getId());
		xml.append("</" + TagsFicha.TAG_EDICION_ID + ">");
		xml.append(Constants.NEWLINE);

		// Tipo
		xml.append(tabs + "  <" + TagsFicha.TAG_EDICION_TIPO + ">");
		xml.append(getTipo());
		xml.append("</" + TagsFicha.TAG_EDICION_TIPO + ">");
		xml.append(Constants.NEWLINE);

		// Multivalor
		xml.append(tabs + "  <" + TagsFicha.TAG_EDICION_MULTIVALOR + ">");
		xml.append(DBUtils.getStringValue(isMultivalor()));
		xml.append("</" + TagsFicha.TAG_EDICION_MULTIVALOR + ">");
		xml.append(Constants.NEWLINE);

		// Editable
		xml.append(tabs + "  <" + TagsFicha.TAG_EDICION_EDITABLE + ">");
		xml.append(DBUtils.getStringValue(isEditable()));
		xml.append("</" + TagsFicha.TAG_EDICION_EDITABLE + ">");
		xml.append(Constants.NEWLINE);

		// Padre Editable
		xml.append(tabs + "  <" + TagsFicha.TAG_EDICION_PADRE_EDITABLE + ">");
		xml.append(DBUtils.getStringValue(isPadreEditable()));
		xml.append("</" + TagsFicha.TAG_EDICION_PADRE_EDITABLE + ">");
		xml.append(Constants.NEWLINE);

		// Tipo de Padre
		if (getTipoPadre() > 0) {
			xml.append(tabs + "  <" + TagsFicha.TAG_EDICION_TIPO_PADRE + ">");
			xml.append(getTipoPadre());
			xml.append("</" + TagsFicha.TAG_EDICION_TIPO_PADRE + ">");
			xml.append(Constants.NEWLINE);
		}

		// Multilinea
		xml.append(tabs + "  <" + TagsFicha.TAG_EDICION_MULTILINEA + ">");
		xml.append(DBUtils.getStringValue(isMultilinea()));
		xml.append("</" + TagsFicha.TAG_EDICION_MULTILINEA + ">");
		xml.append(Constants.NEWLINE);

		// Obligatorio
		xml.append(tabs + "  <" + TagsFicha.TAG_EDICION_OBLIGATORIO + ">");
		xml.append(DBUtils.getStringValue(isObligatorio()));
		xml.append("</" + TagsFicha.TAG_EDICION_OBLIGATORIO + ">");
		xml.append(Constants.NEWLINE);

		// Valor inicial
		if (getValorInicial() != null)
			xml.append(getValorInicial().toXML(indent + 2));

		// Id_TblVld
		xml.append(tabs + "  <" + TagsFicha.TAG_EDICION_ID_TBLVLD + ">");
		if (idTblVld != null)
			xml.append(idTblVld);
		xml.append("</" + TagsFicha.TAG_EDICION_ID_TBLVLD + ">");
		xml.append(Constants.NEWLINE);

		// Opciones
		if (opciones.size() > 0) {
			// Inicio del tag Opciones
			xml.append(tabs + "  <" + TagsFicha.TAG_OPCIONES + ">");
			xml.append(Constants.NEWLINE);

			// Opciones
			for (int i = 0; i < getTotalOpciones(); i++)
				xml.append(getOpcion(i).toXML(indent + 4));

			// Cierre del tag Opciones
			xml.append(tabs + "  </" + TagsFicha.TAG_OPCIONES + ">");
			xml.append(Constants.NEWLINE);
		}

		// Tag de cierre
		xml.append(tabs + "</" + TagsFicha.TAG_EDICION + ">");
		xml.append(Constants.NEWLINE);

		return xml.toString();
	}
}
