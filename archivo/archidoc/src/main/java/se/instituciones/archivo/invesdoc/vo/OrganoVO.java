package se.instituciones.archivo.invesdoc.vo;

import se.instituciones.InfoOrgano;

import common.Constants;

/**
 * Información de un órgano.
 */
public class OrganoVO implements InfoOrgano
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Identificador del órgano. */
	public String id = null;

	/** Nombre del órgano. */
	public String nombre = null;

	/** Código del órgano. */
	public String codigo = null;

	/** Nivel jerárquico al que pertenece el órgano. */
	public int nivel = 0;

	/** Identificador del órgano padre. */
	public String idPadre = null;

	/** Es institucion */
	private String institucion = null;

	/**
	 * Constructor.
	 */
	public OrganoVO() {
		super();
	}

	/**
	 * Obtiene el identificador del órgano.
	 *
	 * @return Identificador del órgano.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Obtiene el nombre del órgano.
	 *
	 * @return Nombre del órgano.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Obtiene el código del órgano.
	 *
	 * @return Código del órgano.
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * Obtiene el nivel jerárquico al que pertenece el órgano.
	 *
	 * @return Nivel jerárquico.
	 */
	public int getNivel() {
		return nivel;
	}

	/**
	 * Obtiene el identificador del órgano padre al que pertenece el órgano.
	 *
	 * @return Identificador del órgano padre.
	 */
	public String getIdPadre() {
		return idPadre;
	}

	/**
	 * Establece el código del órgano.
	 *
	 * @param codigo
	 *            Código del órgano.
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * Establece el identificador del órgano.
	 *
	 * @param id
	 *            Identificador del órgano.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Establece el identificador del órgano padre.
	 *
	 * @param idPadre
	 *            Identificador del órgano padre.
	 */
	public void setIdPadre(String idPadre) {
		this.idPadre = idPadre;
	}

	/**
	 * Establece el nivel jerárquico al que pertenece el órgano.
	 *
	 * @param nivel
	 *            Nivel jerárquico al que pertenece el órgano.
	 */
	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	public void setNivel(String nivel){
		this.nivel = Integer.parseInt(nivel);
	}


	/**
	 * Establece el nombre del órgano.
	 *
	 * @param nombre
	 *            Nombre del órgano.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Obtiene una representación XML del objeto.
	 *
	 * @return Representación del objeto.
	 */
	public String toXML() {
		final StringBuffer xml = new StringBuffer();

		// Tag de inicio
		xml.append("<OrganoVO>");
		xml.append(Constants.NEWLINE);

		// Id
		xml.append("  <Id>");
		xml.append(id != null ? id : "");
		xml.append("</Id>");
		xml.append(Constants.NEWLINE);

		// Nombre
		xml.append("  <Nombre>");
		xml.append(nombre != null ? nombre : "");
		xml.append("</Nombre>");
		xml.append(Constants.NEWLINE);

		// Codigo
		xml.append("  <Codigo>");
		xml.append(codigo != null ? codigo : "");
		xml.append("</Codigo>");
		xml.append(Constants.NEWLINE);

		// Nivel
		xml.append("  <Nivel>");
		xml.append(nivel);
		xml.append("</Nivel>");
		xml.append(Constants.NEWLINE);

		// IdPadre
		xml.append("  <IdPadre>");
		xml.append(idPadre != null ? idPadre : "");
		xml.append("</IdPadre>");
		xml.append(Constants.NEWLINE);

		// Tag de cierre
		xml.append("</OrganoVO>");
		xml.append(Constants.NEWLINE);

		return xml.toString();
	}

	/**
	 * Obtiene una representación del objeto.
	 *
	 * @return Representación del objeto.
	 */
	public String toString() {
		return toXML();
	}

	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}

	public String getInstitucion() {
		return institucion;
	}

}
