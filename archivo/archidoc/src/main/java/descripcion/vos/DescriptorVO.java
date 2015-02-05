package descripcion.vos;

import gcontrol.model.NivelAcceso;

import java.util.Date;

import common.vos.BaseVO;
import common.vos.Describible;
import common.vos.Restringible;

import descripcion.model.EstadoDescriptor;
import descripcion.model.TipoDescriptor;

/**
 * Información de un descriptor.
 */
public class DescriptorVO extends BaseVO implements Describible, Restringible {

	private static final long serialVersionUID = -201057399172865344L;
	private String id = null;
	private String nombre = null;
	private String idLista = null;
	private String nombreLista = null;
	private int tipo = TipoDescriptor.SIN_TIPO;
	private int estado = EstadoDescriptor.VALIDADO;
	private String idSistExt = null;
	private String idDescrSistExt = null;
	private Date timestamp = null;
	private String idFichaDescr = null;
	private String nombreFichaDescr = null;
	private String tieneDescr = null;
	private String editClfDocs = "N";
	private String idRepEcm = null;
	private String nombreRepEcm = null;
	private int nivelAcceso = NivelAcceso.PUBLICO;
	private String idLCA = null;
	private String nombreLCA = null;

	/**
	 * Constructor.
	 */
	public DescriptorVO() {
	}

	/**
	 * Constructor.
	 *
	 * @param idLista
	 *            Identificador de la lista descriptora.
	 * @param nombre
	 *            Nombre del descriptor.
	 */
	public DescriptorVO(String idLista, String nombre) {
		setNombre(nombre);
		setIdLista(idLista);
	}

	/**
	 * @return Returns the estado.
	 */
	public int getEstado() {
		return estado;
	}

	/**
	 * @param estado
	 *            The estado to set.
	 */
	public void setEstado(int estado) {
		this.estado = estado;
	}

	/**
	 * @return Returns the id.
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            The id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return Returns the idDescrSistExt.
	 */
	public String getIdDescrSistExt() {
		return idDescrSistExt;
	}

	/**
	 * @param idDescrSistExt
	 *            The idDescrSistExt to set.
	 */
	public void setIdDescrSistExt(String idDescrSistExt) {
		this.idDescrSistExt = idDescrSistExt;
	}

	/**
	 * @return Returns the idFichaDescr.
	 */
	public String getIdFichaDescr() {
		return idFichaDescr;
	}

	/**
	 * @param idFichaDescr
	 *            The idFichaDescr to set.
	 */
	public void setIdFichaDescr(String idFichaDescr) {
		this.idFichaDescr = idFichaDescr;
	}

	/**
	 * @return Returns the idLCA.
	 */
	public String getIdLCA() {
		return idLCA;
	}

	/**
	 * @param idLCA
	 *            The idLCA to set.
	 */
	public void setIdLCA(String idLCA) {
		this.idLCA = idLCA;
	}

	/**
	 * @return Returns the idLista.
	 */
	public String getIdLista() {
		return idLista;
	}

	/**
	 * @param idLista
	 *            The idLista to set.
	 */
	public void setIdLista(String idLista) {
		this.idLista = idLista;
	}

	/**
	 * @return Returns the idRepEcm.
	 */
	public String getIdRepEcm() {
		return idRepEcm;
	}

	/**
	 * @param idRepEcm
	 *            The idRepEcm to set.
	 */
	public void setIdRepEcm(String idRepEcm) {
		this.idRepEcm = idRepEcm;
	}

	/**
	 * @return Returns the idSistExt.
	 */
	public String getIdSistExt() {
		return idSistExt;
	}

	/**
	 * @param idSistExt
	 *            The idSistExt to set.
	 */
	public void setIdSistExt(String idSistExt) {
		this.idSistExt = idSistExt;
	}

	/**
	 * @return Returns the nivelAcceso.
	 */
	public int getNivelAcceso() {
		return nivelAcceso;
	}

	/**
	 * @param nivelAcceso
	 *            The nivelAcceso to set.
	 */
	public void setNivelAcceso(int nivelAcceso) {
		this.nivelAcceso = nivelAcceso;
	}

	/**
	 * @return Returns the nombre.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            The nombre to set.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return Returns the tieneDescr.
	 */
	public String getTieneDescr() {
		return tieneDescr;
	}

	/**
	 * @param tieneDescr
	 *            The tieneDescr to set.
	 */
	public void setTieneDescr(String tieneDescr) {
		this.tieneDescr = tieneDescr;
	}

	/**
	 * @return Returns the timestamp.
	 */
	public Date getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp
	 *            The timestamp to set.
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return Returns the tipo.
	 */
	public int getTipo() {
		return tipo;
	}

	/**
	 * @param tipo
	 *            The tipo to set.
	 */
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return Returns the nombreFichaDescr.
	 */
	public String getNombreFichaDescr() {
		return nombreFichaDescr;
	}

	/**
	 * @param nombreFichaDescr
	 *            The nombreFichaDescr to set.
	 */
	public void setNombreFichaDescr(String nombreFichaDescr) {
		this.nombreFichaDescr = nombreFichaDescr;
	}

	/**
	 * @return Returns the nombreRepEcm.
	 */
	public String getNombreRepEcm() {
		return nombreRepEcm;
	}

	/**
	 * @param nombreRepEcm
	 *            The nombreRepEcm to set.
	 */
	public void setNombreRepEcm(String nombreRepEcm) {
		this.nombreRepEcm = nombreRepEcm;
	}

	/**
	 * @return Returns the nombreLista.
	 */
	public String getNombreLista() {
		return nombreLista;
	}

	/**
	 * @param nombreLista
	 *            The nombreLista to set.
	 */
	public void setNombreLista(String nombreLista) {
		this.nombreLista = nombreLista;
	}

	/**
	 * @return Returns the nombreLCA.
	 */
	public String getNombreLCA() {
		return nombreLCA;
	}

	/**
	 * @param nombreLCA
	 *            The nombreLCA to set.
	 */
	public void setNombreLCA(String nombreLCA) {
		this.nombreLCA = nombreLCA;
	}

	/**
	 * @return Returns the editClfDocs.
	 */
	public String getEditClfDocs() {
		return editClfDocs;
	}

	/**
	 * @param editClfDocs
	 *            The editClfDocs to set.
	 */
	public void setEditClfDocs(String editClfDocs) {
		this.editClfDocs = editClfDocs;
	}

	/**
	 * Obtiene el identificador del archivo receptor.
	 */
	public String getIdArchivo() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (obj instanceof DescriptorVO) {
			DescriptorVO descriptorVO = (DescriptorVO) obj;

			if (descriptorVO.getId().equals(this.id))
				return true;
		}

		return false;
	}
}
