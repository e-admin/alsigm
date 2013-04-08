package descripcion.model.xml.definition;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import common.Constants;
import common.util.ArrayUtils;

import descripcion.model.xml.XmlElement;

/**
 * Clase que almacena la información específica de un campo de tipo dato en
 * función de su tipo de dato.
 */
public class DefInformacionEspecifica extends XmlElement {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Identificador de la tabla de validación. */
	private String idTblVld = null;

	/** Lista de formatos de fechas. */
	private List formatos = new LinkedList();

	/** Tipo de referencia. */
	private short tipoReferencia = DefTipos.TIPO_REFERENCIA_DESCONOCIDO;

	/** Identificadores de las listas descriptoras. */
	private String idsListasDescriptoras = null;

	/** Tipo numérico. */
	private short tipoNumerico = DefTipos.TIPO_NUMERICO_DESCONOCIDO;

	/** Tipo de la medida. */
	private short tipoMedida = DefTipos.TIPO_MEDIDA_DESCONOCIDO;

	/** Unidad del tipo de medida. */
	private String unidadMedida = null;

	/** Rango mínimo. */
	private Double rangoMinimo = null;

	/** Rango máximo. */
	private Double rangoMaximo = null;

	/**
	 * Constructor.
	 */
	public DefInformacionEspecifica() {
		super();
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
	 * Añade un formato a la lista.
	 * 
	 * @param formato
	 *            Formato de fecha.
	 */
	public void addFormato(DefFormatoFecha formato) {
		formatos.add(formato);
	}

	/**
	 * Obtiene un formato de la lista en función de su posición.
	 * 
	 * @param index
	 *            Posición del formato dentro de la lista.
	 * @return Formato de fecha.
	 */
	public DefFormatoFecha getFormato(int index) {
		return (DefFormatoFecha) formatos.get(index);
	}

	/**
	 * Obtiene el separador del tipo especificado
	 * 
	 * @param tipo
	 *            Cadena que define el tipo de formato
	 * @return Cadena que define el caracter separador
	 */
	public String getSeparadorTipo(String tipo) {
		String separador = null;

		DefFormatoFecha[] formatosFecha = getFormatos();

		if (ArrayUtils.isNotEmpty(formatosFecha) && tipo != null) {
			for (int i = 0; i < formatosFecha.length; i++) {
				DefFormatoFecha formatoFecha = formatosFecha[i];

				if (formatoFecha != null && tipo.equals(formatoFecha.getTipo())) {
					return formatoFecha.getSeparador();
				}
			}
		}
		return separador;
	}

	/**
	 * Obtiene la lista de formatos de fechas.
	 * 
	 * @return Lista de formatos de fechas.
	 */
	public DefFormatoFecha[] getFormatos() {
		return (DefFormatoFecha[]) formatos
				.toArray(new DefFormatoFecha[formatos.size()]);
	}

	/**
	 * Obtiene el número de formatos de fechas de la lista.
	 * 
	 * @return Número de formatos de fechas.
	 */
	public int getTotalFormatos() {
		return formatos.size();
	}

	/**
	 * Elimina el formato de fecha de la posición determinada.
	 * 
	 * @param index
	 *            Posición del formato de fecha a eliminar.
	 */
	public void removeFormato(int index) {
		formatos.remove(index);
	}

	/**
	 * Elimina el formato de la lista.
	 * 
	 * @param formato
	 *            Formato a eliminar.
	 */
	public void removeFormato(DefFormatoFecha formato) {
		formatos.remove(formato);
	}

	/**
	 * Elimina todas las formatos de la lista.
	 */
	public void clearFormatos() {
		formatos.clear();
	}

	/**
	 * Obtiene los identificadores de las listas descriptoras.
	 * 
	 * @return Identificadores de las listas descriptoras.
	 */
	public String getIdsListasDescriptoras() {
		return idsListasDescriptoras;
	}

	/**
	 * Establece los identificadores de las listas descriptoras.
	 * 
	 * @param idsListasDescriptoras
	 *            Identificadores de las listas descriptoras.
	 */
	public void setIdsListasDescriptoras(String idsListasDescriptoras) {
		this.idsListasDescriptoras = idsListasDescriptoras;
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
	 * @return Returns the rangoMaximo.
	 */
	public Double getRangoMaximo() {
		return rangoMaximo;
	}

	/**
	 * @param rangoMaximo
	 *            The rangoMaximo to set.
	 */
	public void setRangoMaximo(Double rangoMaximo) {
		this.rangoMaximo = rangoMaximo;
	}

	/**
	 * @return Returns the rangoMinimo.
	 */
	public Double getRangoMinimo() {
		return rangoMinimo;
	}

	/**
	 * @param rangoMinimo
	 *            The rangoMinimo to set.
	 */
	public void setRangoMinimo(Double rangoMinimo) {
		this.rangoMinimo = rangoMinimo;
	}

	/**
	 * @return Returns the tipoMedida.
	 */
	public short getTipoMedida() {
		return tipoMedida;
	}

	/**
	 * @param tipoMedida
	 *            The tipoMedida to set.
	 */
	public void setTipoMedida(short tipoMedida) {
		this.tipoMedida = tipoMedida;
	}

	/**
	 * @return Returns the tipoNumerico.
	 */
	public short getTipoNumerico() {
		return tipoNumerico;
	}

	/**
	 * @param tipoNumerico
	 *            The tipoNumerico to set.
	 */
	public void setTipoNumerico(short tipoNumerico) {
		this.tipoNumerico = tipoNumerico;
	}

	/**
	 * @return Returns the unidadMedida.
	 */
	public String getUnidadMedida() {
		return unidadMedida;
	}

	/**
	 * @param unidadMedida
	 *            The unidadMedida to set.
	 */
	public void setUnidadMedida(String unidadMedida) {
		this.unidadMedida = unidadMedida;
	}

	private void validateRangoToXml(StringBuffer xml, String tabs) {
		// Validacion/Rango
		if ((rangoMinimo != null) || (rangoMaximo != null)) {
			xml.append(tabs + "    <" + DefTags.TAG_RANGO_INFO_ESPEC);
			if (rangoMinimo != null) {
				xml.append(" " + DefTags.ATTR_RANGO_MINIMO_INFO_ESPEC + "=\"");
				xml.append(rangoMinimo);
				xml.append("\"");
			}
			if (rangoMaximo != null) {
				xml.append(" " + DefTags.ATTR_RANGO_MAXIMO_INFO_ESPEC + "=\"");
				xml.append(rangoMaximo);
				xml.append("\"");
			}
			xml.append("/>");
			xml.append(Constants.NEWLINE);
		}
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

		xml.append(tabs + "<" + DefTags.TAG_INFORMACION_ESPECIFICA_CAMPO_DATO
				+ ">");
		xml.append(Constants.NEWLINE);

		// Tipo Numérico
		if (tipoNumerico > DefTipos.TIPO_NUMERICO_DESCONOCIDO) {
			xml.append(tabs + "  <" + DefTags.TAG_TIPO_NUMERICO_INFO_ESPEC
					+ ">");
			xml.append(tipoNumerico);
			xml.append("</" + DefTags.TAG_TIPO_NUMERICO_INFO_ESPEC + ">");
			xml.append(Constants.NEWLINE);
		}

		// Validacion/Id_TblVld
		if ((idTblVld != null) && (idTblVld.length() > 0)) {
			xml.append(tabs + "  <" + DefTags.TAG_VALIDACION_INFO_ESPEC + ">");
			xml.append(Constants.NEWLINE);

			validateRangoToXml(xml, tabs);

			if (tipoMedida > DefTipos.TIPO_MEDIDA_DESCONOCIDO) {
				xml.append(tabs + "    <" + DefTags.TAG_MEDIDA_INFO_ESPEC + ">");
				xml.append(Constants.NEWLINE);

				xml.append(tabs + "      <"
						+ DefTags.TAG_TIPO_MEDIDA_INFO_ESPEC + ">");
				xml.append(tipoMedida);
				xml.append("</" + DefTags.TAG_TIPO_MEDIDA_INFO_ESPEC + ">");
				xml.append(Constants.NEWLINE);

				xml.append(tabs + "      <"
						+ DefTags.TAG_UNIDAD_MEDIDA_INFO_ESPEC + ">");
				xml.append(Constants.NEWLINE);
				xml.append(tabs + "        <" + DefTags.TAG_IDTBLVLD_INFO_ESPEC
						+ ">");
				xml.append(idTblVld);
				xml.append("</" + DefTags.TAG_IDTBLVLD_INFO_ESPEC + ">");
				xml.append(Constants.NEWLINE);
				xml.append(tabs + "      </"
						+ DefTags.TAG_UNIDAD_MEDIDA_INFO_ESPEC + ">");
				xml.append(Constants.NEWLINE);
				xml.append(tabs + "    </" + DefTags.TAG_MEDIDA_INFO_ESPEC
						+ ">");
				xml.append(Constants.NEWLINE);
			}

			else {
				xml.append(tabs + "        <" + DefTags.TAG_IDTBLVLD_INFO_ESPEC
						+ ">");
				xml.append(idTblVld);
				xml.append("</" + DefTags.TAG_IDTBLVLD_INFO_ESPEC + ">");
				xml.append(Constants.NEWLINE);
			}
			xml.append(tabs + "  </" + DefTags.TAG_VALIDACION_INFO_ESPEC + ">");
			xml.append(Constants.NEWLINE);
		}

		else {
			validateRangoToXml(xml, tabs);
		}

		// Validacion/Formatos
		if (formatos.size() > 0) {
			xml.append(tabs + "  <" + DefTags.TAG_VALIDACION_INFO_ESPEC + ">");
			xml.append(Constants.NEWLINE);

			xml.append(tabs + "    <" + DefTags.TAG_FORMATOS_INFO_ESPEC + ">");
			xml.append(Constants.NEWLINE);

			for (int i = 0; i < formatos.size(); i++)
				xml.append(getFormato(i).toXML(indent + 6));

			xml.append(tabs + "    </" + DefTags.TAG_FORMATOS_INFO_ESPEC + ">");
			xml.append(Constants.NEWLINE);

			xml.append(tabs + "  </" + DefTags.TAG_VALIDACION_INFO_ESPEC + ">");
			xml.append(Constants.NEWLINE);
		}

		// Tipo_Referencia
		if (tipoReferencia > DefTipos.TIPO_REFERENCIA_DESCONOCIDO) {
			xml.append(tabs + "  <" + DefTags.TAG_TIPO_REFERENCIA_INFO_ESPEC
					+ ">");
			xml.append(tipoReferencia);
			xml.append("</" + DefTags.TAG_TIPO_REFERENCIA_INFO_ESPEC + ">");
			xml.append(Constants.NEWLINE);
		}

		// Ids_Listas_Descriptoras
		if ((idsListasDescriptoras != null)
				&& (idsListasDescriptoras.length() > 0)) {
			xml.append(tabs + "  <" + DefTags.TAG_IDS_LISTAS_DESC_INFO_ESPEC
					+ ">");
			xml.append(idsListasDescriptoras);
			xml.append("</" + DefTags.TAG_IDS_LISTAS_DESC_INFO_ESPEC + ">");
			xml.append(Constants.NEWLINE);
		}

		xml.append(tabs + "</" + DefTags.TAG_INFORMACION_ESPECIFICA_CAMPO_DATO
				+ ">");
		xml.append(Constants.NEWLINE);

		return xml.toString();
	}
}
