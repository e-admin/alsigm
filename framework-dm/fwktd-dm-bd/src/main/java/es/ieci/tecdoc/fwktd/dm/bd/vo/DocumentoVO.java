package es.ieci.tecdoc.fwktd.dm.bd.vo;

import java.util.Date;

import es.ieci.tecdoc.fwktd.dm.business.vo.AbstractServiceVO;

public class DocumentoVO extends AbstractServiceVO {

	/**
	 * UID de versión.
	 */
	private static final long serialVersionUID = 4492882709500693697L;

	private String id = null;
	private String nombre = null;
	private String tipoMime = null;
	private long tamano = 0;
	private Date fechaCreacion = null;
	private Date fechaModificacion = null;
	private String metadatos = null;
	private byte[] contenido = null;


	/**
	 * Constructor.
	 */
	public DocumentoVO() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipoMime() {
		return tipoMime;
	}

	public void setTipoMime(String tipoMime) {
		this.tipoMime = tipoMime;
	}

	public long getTamano() {
		return tamano;
	}

	public void setTamano(long tamano) {
		this.tamano = tamano;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public String getMetadatos() {
		return metadatos;
	}

	public void setMetadatos(String metadatos) {
		this.metadatos = metadatos;
	}

	public byte[] getContenido() {
		return contenido;
	}

	public void setContenido(byte[] contenido) {
		this.contenido = contenido;
	}
}
