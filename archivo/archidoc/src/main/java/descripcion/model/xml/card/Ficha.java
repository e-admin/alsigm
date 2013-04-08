package descripcion.model.xml.card;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;

import common.Constants;
import common.db.DBUtils;

import descripcion.exceptions.FichaException;
import descripcion.model.eventos.EventoFichaException;
import descripcion.model.xml.XmlElement;
import descripcion.model.xml.definition.DefFicha;

/**
 * Clase que modeliza la ficha ISAD(G).
 */
public class Ficha extends XmlElement {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Logger de la clase. */
	private static final Logger logger = Logger.getLogger(Ficha.class);

	/** Versión de la ficha por defecto. */
	public static final String DEFAULT_VERSION = "01.00";

	/** Versión de la ficha. */
	private String version = DEFAULT_VERSION;

	/** Indica si la ficha es editable. */
	private boolean editable = false;

	/** Indica si se debe mostrar el botón de automáticos. */
	private boolean automaticos = false;

	/** Identificador del objeto descrito. */
	private String id = null;

	/** Identificador de la ficha. */
	private String idFicha = null;

	/** Tipo de ficha. */
	private int tipo;

	/** Áreas de la ficha. */
	private List areas = new LinkedList();

	/** Definición de la ficha. */
	private DefFicha defFicha = null;

	/** Posibles formatos a aplicar a la ficha */
	private List ltFormatos = null;

	/** Identificador del formato aplicado sobre la ficha */
	private String idFormato = null;

	/** Tipo de acceso */
	private int tipoAcceso = 0;

	/**
	 * Constructor.
	 */
	public Ficha(int tipo) {
		setTipo(tipo);
	}

	/**
	 * Obtiene la versión de la ficha.
	 * 
	 * @return Versión de la ficha.
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Establece la versión de la ficha.
	 * 
	 * @param version
	 *            Versión de la ficha.
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * Indica si la ficha es editable.
	 * 
	 * @return Si la ficha es editable.
	 */
	public boolean isEditable() {
		return editable;
	}

	/**
	 * Establece si la ficha es editable.
	 * 
	 * @param editable
	 *            Si la ficha es editable.
	 */
	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	/**
	 * Si hay que mostrar botón de generación de automáticos.
	 * 
	 * @return true si hay que mostrar botón de generación de automáticos.
	 */
	public boolean isAutomaticos() {
		return automaticos;
	}

	/**
	 * Establece si hay que mostrar botón de generación de automáticos.
	 * 
	 * @param automaticos
	 *            Si hay que mostrar botón de generación de automáticos.
	 */
	public void setAutomaticos(boolean automaticos) {
		this.automaticos = automaticos;
	}

	/**
	 * Obtener el indentificador del objeto descrito.
	 * 
	 * @return Indentificador del objeto descrito.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Establecer el indentificador del objeto descrito.
	 * 
	 * @param id
	 *            Indentificador del objeto descrito.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Devuelve el identificador de la ficha
	 * 
	 * @return identificador de la ficha
	 */
	public String getIdFicha() {
		return idFicha;
	}

	/**
	 * Establece el identificador de la ficha
	 * 
	 * @param idFicha
	 *            identificador de la ficha
	 */
	public void setIdFicha(String idFicha) {
		this.idFicha = idFicha;
	}

	/**
	 * Obtiene el tipo de ficha.
	 * 
	 * @return Tipo de ficha ({@link descripcion.model.xml.card.TipoFicha}).
	 */
	public int getTipo() {
		return tipo;
	}

	/**
	 * Establece el tipo de ficha
	 * 
	 * @param tipo
	 *            Tipo de ficha ({@link descripcion.model.xml.card.TipoFicha}).
	 */
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	/**
	 * Añade un área a la lista.
	 * 
	 * @param area
	 *            Área.
	 */
	public void addArea(Area area) {
		areas.add(area);
	}

	/**
	 * Obtiene un área de la lista en función de su posición.
	 * 
	 * @param index
	 *            Posición del área dentro de la lista.
	 * @return Área.
	 */
	public Area getArea(int index) {
		return (Area) areas.get(index);
	}

	/**
	 * Obtiene la lista de areas.
	 * 
	 * @return Lista de areas.
	 */
	public Area[] getAreas() {
		return (Area[]) areas.toArray(new Area[areas.size()]);
	}

	/**
	 * Obtiene el número de áreas de la lista.
	 * 
	 * @return Número de areas.
	 */
	public int getTotalAreas() {
		return areas.size();
	}

	/**
	 * Elimina el área de la posición determinada.
	 * 
	 * @param index
	 *            Posición del área a eliminar.
	 */
	public void removeArea(int index) {
		areas.remove(index);
	}

	/**
	 * Elimina el área de la lista.
	 * 
	 * @param area
	 *            Área a eliminar.
	 */
	public void removeArea(Area area) {
		areas.remove(area);
	}

	/**
	 * Elimina todas las áreas de la lista.
	 */
	public void clearAreas() {
		areas.clear();
	}

	/**
	 * Obtiene la definición de la ficha.
	 * 
	 * @return Definición de la ficha.
	 */
	public DefFicha getDefFicha() {
		return defFicha;
	}

	/**
	 * Establece la definición de la ficha.
	 * 
	 * @param defFicha
	 *            Definición de la ficha.
	 */
	public void setDefFicha(DefFicha defFicha) {
		this.defFicha = defFicha;
	}

	/**
	 * Ejecuta el evento seleccionado de la ficha.
	 * 
	 * @param tipoEvento
	 *            Tipo de evento lanzado.
	 * @param ficha
	 *            Ficha de descripción.
	 */
	public ActionErrors ejecutarEvento(int tipoEvento, Ficha ficha,
			Locale locale) {
		try {
			return defFicha.ejecutarEvento(tipoEvento, ficha, locale);
		} catch (EventoFichaException e) {
			logger.error("Error al ejecutar el evento " + tipoEvento, e);
			throw new FichaException(e, this.getClass().getName(),
					"Error al ejecutar el evento " + tipoEvento);
		}
	}

	/**
	 * Añade un valor a un elemento de tipo etiqueta-dato.
	 * 
	 * @param idElemento
	 *            Identificador del elemento.
	 * @param valor
	 *            Valor a añadir.
	 * @return Elemento modificado.
	 */
	public ElementoEtiquetaDato addValor(String idElemento, Valor valor) {
		ElementoEtiquetaDato elemento = null;

		if (valor != null) {
			elemento = findElementoEtiquetaDatoById(idElemento);

			if (elemento != null) {
				valor.setAccion(TipoAccion.CREAR);
				elemento.addValor(valor);
			}
		}

		return elemento;
	}

	/**
	 * Devuelve la lista de formatos posibles a aplicar sobre la ficha
	 * 
	 * @return lista de formatos posibles a aplicar sobre la ficha
	 */
	public List getLtFormatos() {
		return ltFormatos;
	}

	/**
	 * Establece la lista de formatos posibles a aplicar sobre la ficha
	 * 
	 * @param ltFormatos
	 *            lista de formatos posibles a aplicar sobre la ficha
	 */
	public void setLtFormatos(List ltFormatos) {
		this.ltFormatos = ltFormatos;
	}

	/**
	 * Devuelve el identificador del formato aplicado sobre la ficha
	 * 
	 * @return identificador del formato aplicado sobre la ficha
	 */
	public String getIdFormato() {
		return idFormato;
	}

	/**
	 * Establece el identificador del formato aplicado sobre la ficha
	 * 
	 * @param idFormato
	 *            identificador del formato aplicado sobre la ficha
	 */
	public void setIdFormato(String idFormato) {
		this.idFormato = idFormato;
	}

	/**
	 * Devuelve el tipo de acceso a la ficha
	 * 
	 * @return tipo de acceso a la ficha ({@link gcontrol.model.TipoAcceso}).
	 */
	public int getTipoAcceso() {
		return tipoAcceso;
	}

	/**
	 * Establece el tipo de acceso
	 * 
	 * @param tipoAcceso
	 *            Tipo de acceso a establecer
	 */
	public void setTipoAcceso(int tipoAcceso) {
		this.tipoAcceso = tipoAcceso;
	}

	/**
	 * Busca un elemento de tipo etiqueta-dato.
	 * 
	 * @param idElemento
	 *            Identificador del elemento.
	 * @return Elemento.
	 */
	public ElementoEtiquetaDato findElementoEtiquetaDatoById(String idElemento) {
		ElementoEtiquetaDato dato = null;

		if (StringUtils.isNotBlank(idElemento)) {
			Area area;
			for (int i = 0; (dato == null) && (i < areas.size()); i++) {
				area = (Area) areas.get(i);
				for (int j = 0; (dato == null)
						&& (j < area.getTotalElementos()); j++) {
					Elemento elemento = (Elemento) area.getElemento(j);

					switch (elemento.getTipo()) {
					case TiposElemento.TIPO_ELEMENTO_AREA:
					case TiposElemento.TIPO_ELEMENTO_CABECERA:
					case TiposElemento.TIPO_ELEMENTO_TABLA:
					case TiposElemento.TIPO_ELEMENTO_TABLA_TEXTUAL:
						dato = findElementoEtiquetaDatoById(idElemento,
								(ContenedorElementos) elemento);
						break;

					case TiposElemento.TIPO_ELEMENTO_ETIQUETA_DATO:
						if ((((ElementoEtiquetaDato) elemento).getEdicion() != null)
								&& idElemento
										.equals(((ElementoEtiquetaDato) elemento)
												.getEdicion().getId()))
							dato = (ElementoEtiquetaDato) elemento;
						break;
					}
				}
			}
		}

		return dato;
	}

	/**
	 * Busca un elemento de tipo etiqueta-dato.
	 * 
	 * @param idElemento
	 *            Identificador del elemento.
	 * @param contenedor
	 *            Contenedor de elementos.
	 * @return Elemento.
	 */
	protected ElementoEtiquetaDato findElementoEtiquetaDatoById(
			String idElemento, ContenedorElementos contenedor) {
		ElementoEtiquetaDato dato = null;

		for (int i = 0; (dato == null) && (i < contenedor.getTotalElementos()); i++) {
			Elemento elemento = (Elemento) contenedor.getElemento(i);
			switch (elemento.getTipo()) {
			case TiposElemento.TIPO_ELEMENTO_AREA:
			case TiposElemento.TIPO_ELEMENTO_CABECERA:
			case TiposElemento.TIPO_ELEMENTO_TABLA:
			case TiposElemento.TIPO_ELEMENTO_TABLA_TEXTUAL:
				dato = findElementoEtiquetaDatoById(idElemento,
						(ContenedorElementos) elemento);
				break;

			case TiposElemento.TIPO_ELEMENTO_ETIQUETA_DATO:
				if ((((ElementoEtiquetaDato) elemento).getEdicion() != null)
						&& idElemento.equals(((ElementoEtiquetaDato) elemento)
								.getEdicion().getId()))
					dato = (ElementoEtiquetaDato) elemento;
				break;
			}
		}

		return dato;
	}

	/**
	 * Busca un elemento de tipo tabla.
	 * 
	 * @param idTabla
	 *            Identificador de la tabla.
	 * @return Tabla.
	 */
	public ElementoTabla findElementoTablaById(String idTabla) {
		ElementoTabla tabla = null;

		if (StringUtils.isNotBlank(idTabla)) {
			Area area;
			for (int i = 0; (tabla == null) && (i < areas.size()); i++) {
				area = (Area) areas.get(i);
				for (int j = 0; (tabla == null)
						&& (j < area.getTotalElementos()); j++) {
					Elemento elemento = (Elemento) area.getElemento(j);

					switch (elemento.getTipo()) {
					case TiposElemento.TIPO_ELEMENTO_AREA:
					case TiposElemento.TIPO_ELEMENTO_CABECERA:
					case TiposElemento.TIPO_ELEMENTO_TABLA_TEXTUAL:
						tabla = findElementoTablaById(idTabla,
								(ContenedorElementos) elemento);
						break;
					case TiposElemento.TIPO_ELEMENTO_TABLA:
						if ((((ElementoTabla) elemento).getEdicion() != null)
								&& idTabla.equals(((ElementoTabla) elemento)
										.getEdicion().getId()))
							tabla = (ElementoTabla) elemento;
						break;
					}
				}
			}
		}

		return tabla;
	}

	/**
	 * Busca un elemento de tipo tabla.
	 * 
	 * @param idTabla
	 *            Identificador del elemento.
	 * @param contenedor
	 *            Contenedor de elementos.
	 * @return Elemento.
	 */
	protected ElementoTabla findElementoTablaById(String idTabla,
			ContenedorElementos contenedor) {
		ElementoTabla tabla = null;

		for (int i = 0; (tabla == null) && (i < contenedor.getTotalElementos()); i++) {
			Elemento elemento = (Elemento) contenedor.getElemento(i);
			switch (elemento.getTipo()) {
			case TiposElemento.TIPO_ELEMENTO_AREA:
			case TiposElemento.TIPO_ELEMENTO_CABECERA:
			case TiposElemento.TIPO_ELEMENTO_TABLA_TEXTUAL:
				tabla = findElementoTablaById(idTabla,
						(ContenedorElementos) elemento);
				break;
			case TiposElemento.TIPO_ELEMENTO_TABLA:
				if ((((ElementoTabla) elemento).getEdicion() != null)
						&& idTabla.equals(((ElementoTabla) elemento)
								.getEdicion().getId()))
					tabla = (ElementoTabla) elemento;
				break;
			}
		}

		return tabla;
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

		// XML
		xml.append(tabs + "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>");
		xml.append(Constants.NEWLINE);

		// Tag de inicio
		xml.append(tabs + "<" + TagsFicha.TAG_FICHA + " "
				+ TagsFicha.ATTR_VERSION + "=\"");
		xml.append(version != null ? version : DEFAULT_VERSION);
		xml.append("\" " + TagsFicha.ATTR_EDITABLE + "=\"");
		xml.append(DBUtils.getStringValue(editable));
		xml.append("\"");

		// Identificador del objeto descrito
		if (StringUtils.isNotBlank(id)) {
			xml.append(" " + TagsFicha.ATTR_ID + "=\"");
			xml.append(id);
			xml.append("\"");
		}

		// Automáticos
		if (automaticos) {
			xml.append(" " + TagsFicha.ATTR_AUTOMATICOS + "=\"");
			xml.append(DBUtils.getStringValue(automaticos));
			xml.append("\"");
		}

		// Tipo de ficha
		xml.append(" " + TagsFicha.ATTR_TIPO + "=\"");
		xml.append(tipo);
		xml.append("\">");

		xml.append(Constants.NEWLINE);

		// Áreas
		if (areas.size() > 0) {
			xml.append(tabs + "  <" + TagsFicha.TAG_AREAS + ">");
			xml.append(Constants.NEWLINE);

			for (int i = 0; i < areas.size(); i++)
				xml.append(getArea(i).toXML(indent + 4));

			xml.append(tabs + "  </" + TagsFicha.TAG_AREAS + ">");
			xml.append(Constants.NEWLINE);
		}

		// Tag de cierre
		xml.append(tabs + "</" + TagsFicha.TAG_FICHA + ">");
		xml.append(Constants.NEWLINE);

		return xml.toString();
	}

}
