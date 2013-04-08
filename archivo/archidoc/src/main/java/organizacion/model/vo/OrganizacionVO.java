package organizacion.model.vo;

import java.util.Date;

import util.TreeModelItem;

import common.Constants;
import common.vos.BaseVO;

public class OrganizacionVO extends BaseVO implements TreeModelItem {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/* Identificador del Órgano */
	private String id;
	/* Código del Órgano */
	private String codigo;
	/* Nombre */
	private String nombre;
	/* Tipo de organismo (1.- Institución, 2.- Órgano) */
	private Integer tipo;
	/* Nivel del Órgano en la estructura jerárquica */
	private Integer nivel;
	/* Identificador del Órgano padre */
	private String idOrgPadre;
	/* Estado del Órgano */
	private Integer estado;
	/* Fecha de inicio del período de vigencia */
	private Date finiciovigencia;
	/* Fecha de finalización de vigencia */
	private Date ffinvigencia;
	/* Descripción */
	private String descripcion;

	/* Objeto padre */
	OrganizacionVO parentElement = null;

	public OrganizacionVO() {
	}

	public OrganizacionVO(String id, String codigo, String nombre,
			Integer tipo, Integer nivel, Integer estado, Date finiciovigencia) {
		this(id, codigo, nombre, tipo, nivel, null, estado, finiciovigencia,
				null, null);
	}

	public OrganizacionVO(String id, String codigo, String nombre,
			Integer tipo, Integer nivel, String idOrgPadre, Integer estado,
			Date finiciovigencia, Date ffinvigencia, String descripcion) {
		this.id = id;
		this.codigo = codigo;
		this.nombre = nombre;
		this.tipo = tipo;
		this.nivel = nivel;
		this.idOrgPadre = idOrgPadre;
		this.estado = estado;
		this.finiciovigencia = finiciovigencia;
		this.ffinvigencia = ffinvigencia;
		this.descripcion = descripcion;
	}

	/**
	 * @return el codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo
	 *            el codigo a establecer
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return el descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 *            el descripcion a establecer
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return el estado
	 */
	public Integer getEstado() {
		return estado;
	}

	/**
	 * @param estado
	 *            el estado a establecer
	 */
	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	/**
	 * @return el fFinVigencia
	 */
	public Date getFfinvigencia() {
		return ffinvigencia;
	}

	/**
	 * @param finVigencia
	 *            el fFinVigencia a establecer
	 */
	public void setFfinvigencia(Date finVigencia) {
		ffinvigencia = finVigencia;
	}

	/**
	 * @return el fInicioVigencia
	 */
	public Date getFiniciovigencia() {
		return finiciovigencia;
	}

	/**
	 * @param inicioVigencia
	 *            el fInicioVigencia a establecer
	 */
	public void setFiniciovigencia(Date inicioVigencia) {
		finiciovigencia = inicioVigencia;
	}

	/**
	 * @return el id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            el id a establecer
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return el idOrgPadre
	 */
	public String getIdOrgPadre() {
		return idOrgPadre;
	}

	/**
	 * @param idOrgPadre
	 *            el idOrgPadre a establecer
	 */
	public void setIdOrgPadre(String idOrgPadre) {
		this.idOrgPadre = idOrgPadre;
	}

	/**
	 * @return el nivel
	 */
	public Integer getNivel() {
		return nivel;
	}

	/**
	 * @param nivel
	 *            el nivel a establecer
	 */
	public void setNivel(Integer nivel) {
		this.nivel = nivel;
	}

	/**
	 * @return el nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            el nombre a establecer
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return el tipo
	 */
	public Integer getTipo() {
		return tipo;
	}

	/**
	 * @param tipo
	 *            el tipo a establecer
	 */
	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public boolean isLeaf() {
		return false;
	}

	public Object getItemId() {
		return this.id;
	}

	public String getItemName() {
		return this.nombre;
	}

	public OrganizacionVO getParentElement() {
		return parentElement;
	}

	public void setParentElement(OrganizacionVO parentElement) {
		this.parentElement = parentElement;
	}

	public String getItemPath() {
		StringBuffer itemPath = new StringBuffer();
		if (parentElement != null)
			itemPath.append(((TreeModelItem) parentElement).getItemPath())
					.append("/");
		itemPath.append("item").append(this.getItemId());
		return itemPath.toString();
	}

	public String getTexto() {
		return codigo + Constants.STRING_SPACE + nombre;
	}
}