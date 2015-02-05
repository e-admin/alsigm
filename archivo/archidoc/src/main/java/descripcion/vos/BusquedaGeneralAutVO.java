package descripcion.vos;

import java.util.Date;

import common.pagination.PageInfo;
import common.vos.BaseVO;

/**
 * VO para el almacenamiento de información del formulario de búsqueda de
 * descripciones de autoridades.
 */
public class BusquedaGeneralAutVO extends BaseVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String nombre = null;
	private String texto = null;
	private String descripcion = null;
	private String numeroComp = null;
	private String numero = null;
	private String tipoMedida = null;
	private String unidadMedida = null;
	private Date fechaIni = null;
	private Date fechaFin = null;
	private String calificador = null;
	private String[] listasDescriptoras = null;
	private PageInfo pageInfo = null;

	/**
	 * Constructor.
	 */
	public BusquedaGeneralAutVO() {
		super();
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
	 * @return Returns the fechaFin.
	 */
	public Date getFechaFin() {
		return fechaFin;
	}

	/**
	 * @param fechaFin
	 *            The fechaFin to set.
	 */
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	/**
	 * @return Returns the fechaIni.
	 */
	public Date getFechaIni() {
		return fechaIni;
	}

	/**
	 * @param fechaIni
	 *            The fechaIni to set.
	 */
	public void setFechaIni(Date fechaIni) {
		this.fechaIni = fechaIni;
	}

	/**
	 * @return Returns the texto.
	 */
	public String getTexto() {
		return texto;
	}

	/**
	 * @param texto
	 *            The texto to set.
	 */
	public void setTexto(String texto) {
		this.texto = texto;
	}

	/**
	 * @return Returns the calificador.
	 */
	public String getCalificador() {
		return calificador;
	}

	/**
	 * @param calificador
	 *            The calificador to set.
	 */
	public void setCalificador(String calificador) {
		this.calificador = calificador;
	}

	/**
	 * @return Returns the listasDescriptoras.
	 */
	public String[] getListasDescriptoras() {
		return listasDescriptoras;
	}

	/**
	 * @param listasDescriptoras
	 *            The listasDescriptoras to set.
	 */
	public void setListasDescriptoras(String[] listasDescriptoras) {
		this.listasDescriptoras = listasDescriptoras;
	}

	/**
	 * @return Returns the numero.
	 */
	public String getNumero() {
		return numero;
	}

	/**
	 * @param numero
	 *            The numero to set.
	 */
	public void setNumero(String numero) {
		this.numero = numero;
	}

	/**
	 * @return Returns the numeroComp.
	 */
	public String getNumeroComp() {
		return numeroComp;
	}

	/**
	 * @param numeroComp
	 *            The numeroComp to set.
	 */
	public void setNumeroComp(String numeroComp) {
		this.numeroComp = numeroComp;
	}

	/**
	 * @return Returns the tipoMedida.
	 */
	public String getTipoMedida() {
		return tipoMedida;
	}

	/**
	 * @param tipoMedida
	 *            The tipoMedida to set.
	 */
	public void setTipoMedida(String tipoMedida) {
		this.tipoMedida = tipoMedida;
	}

	/**
	 * @return Returns the unidadesMedida.
	 */
	public String getUnidadMedida() {
		return unidadMedida;
	}

	/**
	 * @param unidadesMedida
	 *            The unidadesMedida to set.
	 */
	public void setUnidadMedida(String unidadesMedida) {
		this.unidadMedida = unidadesMedida;
	}

	/**
	 * @return Returns the descripcion.
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 *            The descripcion to set.
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return Returns the pageInfo.
	 */
	public PageInfo getPageInfo() {
		return pageInfo;
	}

	/**
	 * @param pageInfo
	 *            The pageInfo to set.
	 */
	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}
}
