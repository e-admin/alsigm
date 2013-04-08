package descripcion.vos;

import java.util.Date;

import se.usuarios.ServiceClient;

import common.pagination.PageInfo;
import common.vos.BaseVO;

/**
 * VO para el almacenamiento de información del formulario de búsqueda de
 * descripciones de fondos.
 */
public class BusquedaGeneralVO extends BaseVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String codigoReferencia = null;
	private String titulo = null;
	private String texto = null;
	private String numeroComp = null;
	private String numero = null;
	private String tipoMedida = null;
	private String unidadMedida = null;
	private Date fechaIni = null;
	private Date fechaFin = null;
	private String calificador = null;
	private String fondo = null;
	private String[] niveles = null;
	private String[] descriptores = null;
	private PageInfo pageInfo = null;
	private ServiceClient serviceClient = null;
	private String orderColumnName = null;
	private String tipoOrdenacion = null;
	private int orderColumn = 1;

	/**
	 * Constructor.
	 */
	public BusquedaGeneralVO() {
		super();
	}

	/**
	 * @return Returns the codigoReferencia.
	 */
	public String getCodigoReferencia() {
		return codigoReferencia;
	}

	/**
	 * @param codigoReferencia
	 *            The codigoReferencia to set.
	 */
	public void setCodigoReferencia(String codigoReferencia) {
		this.codigoReferencia = codigoReferencia;
	}

	/**
	 * @return Returns the descriptores.
	 */
	public String[] getDescriptores() {
		return descriptores;
	}

	/**
	 * @param descriptores
	 *            The descriptores to set.
	 */
	public void setDescriptores(String[] descriptores) {
		this.descriptores = descriptores;
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
	 * @return Returns the niveles.
	 */
	public String[] getNiveles() {
		return niveles;
	}

	/**
	 * @param niveles
	 *            The niveles to set.
	 */
	public void setNiveles(String[] niveles) {
		this.niveles = niveles;
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
	 * @return Returns the titulo.
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * @param titulo
	 *            The titulo to set.
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	/**
	 * @return Returns the fondo.
	 */
	public String getFondo() {
		return fondo;
	}

	/**
	 * @param fondo
	 *            The fondo to set.
	 */
	public void setFondo(String fondo) {
		this.fondo = fondo;
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

	public int getOrderColumn() {
		return orderColumn;
	}

	public void setOrderColumn(int orderColumn) {
		this.orderColumn = orderColumn;
	}

	public String getTipoOrdenacion() {
		return tipoOrdenacion;
	}

	public void setTipoOrdenacion(String tipoOrdenacion) {
		this.tipoOrdenacion = tipoOrdenacion;
	}

	public String getOrderColumnName() {
		return orderColumnName;
	}

	public void setOrderColumnName(String orderColumnName) {
		this.orderColumnName = orderColumnName;
	}

	public ServiceClient getServiceClient() {
		return serviceClient;
	}

	public void setServiceClient(ServiceClient serviceClient) {
		this.serviceClient = serviceClient;
	}
}
