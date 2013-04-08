package descripcion.model.xml.card;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import common.Constants;
import common.db.DBUtils;

import descripcion.model.xml.definition.DefTipos;

/**
 * Clase que almacena la información de edición de un dato de tipo referencia.
 */
public class EdicionDatoReferencia extends EdicionDato {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Tipo de referencia. */
	private short tipoReferencia = DefTipos.TIPO_REFERENCIA_DESCONOCIDO;

	/** Lista de listas descriptoras del campo. */
	private List identificadores = new LinkedList();

	/** indica si el descriptor tiene ficha asociada */
	// private boolean descrFichaAsociada=false;

	/** indica si el dato al que se referencia tiene ficha asociada */
	private boolean tieneFichaAsociada = false;

	/**
	 * Constructor.
	 */
	public EdicionDatoReferencia() {
		super(DefTipos.TIPO_DATO_REFERENCIA);
	}

	/**
	 * Obtiene el tipo de referencia.
	 * 
	 * @return Tipo de referencia.
	 */
	public short getTipoReferencia() {
		return tipoReferencia;
	}

	/**
	 * Establece el tipo de referencia.
	 * 
	 * @param tipoReferencia
	 *            Tipo de referencia.
	 */
	public void setTipoReferencia(short tipoReferencia) {
		this.tipoReferencia = tipoReferencia;
	}

	/**
	 * Añade el identificador de una lista de descriptores.
	 * 
	 * @param id
	 *            Identificador de una lista de descriptores.
	 */
	public void addIdentificador(String id) {
		identificadores.add(id);
	}

	/**
	 * Obtiene el identificador de una lista de descriptores en función de su
	 * posición.
	 * 
	 * @param index
	 *            Posición del identificador dentro de la lista.
	 * @return Identificador.
	 */
	public String getIdentificador(int index) {
		return (String) identificadores.get(index);
	}

	/**
	 * Obtiene la lista de identificadores de listas de descriptores.
	 * 
	 * @return Lista de identificadores.
	 */
	public String[] getIdentificadores() {
		return (String[]) identificadores.toArray(new String[identificadores
				.size()]);
	}

	/**
	 * Obtiene el número de identificadores de listas de descriptores.
	 * 
	 * @return Número de identificadores.
	 */
	public int getTotalIdentificadores() {
		return identificadores.size();
	}

	/**
	 * Elimina el identificador de la lista de descriptores de la posición
	 * determinada.
	 * 
	 * @param index
	 *            Posición del identificador a eliminar.
	 */
	public void removeIdentificador(int index) {
		identificadores.remove(index);
	}

	/**
	 * Elimina el identificador de la lista de descriptores de la lista.
	 * 
	 * @param id
	 *            Identificador a eliminar.
	 */
	public void removeIdentificador(String id) {
		identificadores.remove(id);
	}

	/**
	 * Elimina todos los identificadores de la lista.
	 */
	public void clearIdentificadores() {
		identificadores.clear();
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

		// Referencia con ficha asociada
		xml.append(tabs + "  <" + TagsFicha.TAG_TIENE_FICHA_ASOCIADA + ">");
		xml.append(isTieneFichaAsociada());
		xml.append("</" + TagsFicha.TAG_TIENE_FICHA_ASOCIADA + ">");
		xml.append(Constants.NEWLINE);

		/*
		 * xml.append(tabs + "  <" + TagsFicha.TAG_DESCR_FICHA_ASOCIADA + ">");
		 * xml.append(isDescrFichaAsociada()); xml.append("</" +
		 * TagsFicha.TAG_DESCR_FICHA_ASOCIADA + ">");
		 * xml.append(Constants.NEWLINE);
		 */

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

		// Obligatorio
		xml.append(tabs + "  <" + TagsFicha.TAG_EDICION_OBLIGATORIO + ">");
		xml.append(DBUtils.getStringValue(isObligatorio()));
		xml.append("</" + TagsFicha.TAG_EDICION_OBLIGATORIO + ">");
		xml.append(Constants.NEWLINE);

		// Valor inicial
		if (getValorInicial() != null)
			xml.append(getValorInicial().toXML(indent + 2));

		// Tipo de referencia
		xml.append(tabs + "  <" + TagsFicha.TAG_EDICION_TIPO_REFERENCIA + ">");
		if (tipoReferencia != DefTipos.TIPO_REFERENCIA_DESCONOCIDO)
			xml.append(tipoReferencia);
		xml.append("</" + TagsFicha.TAG_EDICION_TIPO_REFERENCIA + ">");
		xml.append(Constants.NEWLINE);

		// Listas de descriptores
		if (identificadores.size() > 0) {
			// Inicio del tag Validación
			xml.append(tabs + "  <" + TagsFicha.TAG_VALIDACION_EDICION + ">");
			xml.append(Constants.NEWLINE);

			// Identificadores
			for (int i = 0; i < getTotalIdentificadores(); i++) {
				xml.append(tabs + "    <"
						+ TagsFicha.TAG_LISTA_DESCRIPTORA_EDICION + " id=\"");
				xml.append(getIdentificador(i));
				xml.append("\"/>");
				xml.append(Constants.NEWLINE);
			}

			// Cierre del tag Validación
			xml.append(tabs + "  </" + TagsFicha.TAG_VALIDACION_EDICION + ">");
			xml.append(Constants.NEWLINE);
		}

		// Tag de cierre
		xml.append(tabs + "</" + TagsFicha.TAG_EDICION + ">");
		xml.append(Constants.NEWLINE);

		return xml.toString();
	}

	public boolean isTieneFichaAsociada() {
		return tieneFichaAsociada;
	}

	public void setTieneFichaAsociada(boolean tieneFichaAsociada) {
		this.tieneFichaAsociada = tieneFichaAsociada;
	}
}
