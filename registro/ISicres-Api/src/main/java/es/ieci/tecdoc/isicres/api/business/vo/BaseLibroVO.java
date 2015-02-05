package es.ieci.tecdoc.isicres.api.business.vo;

import java.util.Date;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */

public class BaseLibroVO extends BaseIsicresApiVO {

	private static final long serialVersionUID = -992151281032399852L;

	protected String id;

	protected EstadoLibroVO estadoLibro;

	/**
	 * nombre del libro
	 */
	protected String name;

	/**
	 * identificador del archivador del libro
	 */
	protected String idArchivador;

	/**
	 * comentario o información adicional del libro
	 */
	protected String comentario;

	/**
	 * fecha de cierre del libro
	 */
	protected Date fechaCierre;

	/**
	 * literal del usuario que cerró el libro
	 */
	protected String usuarioCierre;

	/**
	 * esquema del libro
	 */
	protected EsquemaLibroVO esquemaLibro;

	protected ConfiguracionLibroVO configuracion;

	public BaseLibroVO() {
		super();
	}

	public BaseLibroVO(String anId) {
		this.id = anId;
	}

	public EsquemaLibroVO getEsquemaLibro() {
		if (null == esquemaLibro) {
			esquemaLibro = new EsquemaLibroVO();
		}
		return esquemaLibro;
	}

	public void setEsquemaLibro(EsquemaLibroVO esquemaLibro) {
		this.esquemaLibro = esquemaLibro;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public EstadoLibroVO getEstadoLibro() {
		if (null == estadoLibro) {
			estadoLibro = new EstadoLibroVO();
		}
		return estadoLibro;
	}

	public void setEstadoLibro(EstadoLibroVO estadoLibro) {
		this.estadoLibro = estadoLibro;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdArchivador() {
		return idArchivador;
	}

	public void setIdArchivador(String idArchivador) {
		this.idArchivador = idArchivador;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public ConfiguracionLibroVO getConfiguracion() {
		if (null == configuracion) {
			configuracion = new ConfiguracionLibroVO();
		}
		return configuracion;
	}

	public void setConfiguracion(ConfiguracionLibroVO configuracion) {
		this.configuracion = configuracion;
	}

	public Date getFechaCierre() {
		return fechaCierre;
	}

	public void setFechaCierre(Date fechaCierre) {
		this.fechaCierre = fechaCierre;
	}

	public String getUsuarioCierre() {
		return usuarioCierre;
	}

	public void setUsuarioCierre(String usuarioCierre) {
		this.usuarioCierre = usuarioCierre;
	}

}
