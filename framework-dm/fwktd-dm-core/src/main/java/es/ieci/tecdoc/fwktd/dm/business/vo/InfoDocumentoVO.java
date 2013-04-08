package es.ieci.tecdoc.fwktd.dm.business.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Información de un documento en el gestor documental.
 * @author Iecisa
 * @version $Revision$
 *
 */
public class InfoDocumentoVO extends AbstractServiceVO {

	/**
	 * UID de versión.
	 */
	private static final long serialVersionUID = 2916669722959600316L;

	private String id = null;
	private String nombre = null;
	private String tipoMime = null;
	//private String tipoDocumental = null;
	private long tamano = 0;
	private Date fechaCreacion = null;
	private Date fechaModificacion = null;
	private List<MetadatoVO> metadatos = new ArrayList<MetadatoVO>();


	/**
	 * Constructor.
	 */
	public InfoDocumentoVO() {
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

//	public String getTipoDocumental() {
//		return tipoDocumental;
//	}
//
//	public void setTipoDocumental(String tipoDocumental) {
//		this.tipoDocumental = tipoDocumental;
//	}

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

	public List<MetadatoVO> getMetadatos() {
		return metadatos;
	}

	public void setMetadatos(List<MetadatoVO> metadatos) {
		this.metadatos.clear();
		if (metadatos != null) {
			this.metadatos.addAll(metadatos);
		}
	}
}
