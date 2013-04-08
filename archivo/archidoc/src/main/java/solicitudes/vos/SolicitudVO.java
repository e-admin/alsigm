package solicitudes.vos;

import gcontrol.vos.ArchivoVO;
import gcontrol.vos.UsuarioVO;

import java.util.Date;

import common.vos.BaseVO;

/**
 * Información básica de las solicitudes.
 */
public abstract class SolicitudVO extends BaseVO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2323172424361830975L;
	private String id = null;
	private String ano = null;
	private int orden = 0;
	private ArchivoVO archivo = null;
	private int estado = 0;
	private Date festado = null;
	private String idarchivo = null;
	private Date finicialreserva = null;
	private Date fentrega = null;
	private int comparatorType = 0;
	private UsuarioVO usuarioGestor = null;
	private String observaciones = null;
	private String idMotivo = null;

	/**
	 * Constructor.
	 */
	public SolicitudVO() {
		super();
	}

	/**
	 * @return Returns the ano.
	 */
	public String getAno() {
		return ano;
	}

	/**
	 * @param ano
	 *            The ano to set.
	 */
	public void setAno(String ano) {
		this.ano = ano;
	}

	/**
	 * @return Returns the archivo.
	 */
	public ArchivoVO getArchivo() {
		return archivo;
	}

	/**
	 * @param archivo
	 *            The archivo to set.
	 */
	public void setArchivo(ArchivoVO archivo) {
		this.archivo = archivo;
	}

	/**
	 * @return Returns the comparatorType.
	 */
	public int getComparatorType() {
		return comparatorType;
	}

	/**
	 * @param comparatorType
	 *            The comparatorType to set.
	 */
	public void setComparatorType(int comparatorType) {
		this.comparatorType = comparatorType;
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
	 * @return Returns the fentrega.
	 */
	public Date getFentrega() {
		return fentrega;
	}

	/**
	 * @param fentrega
	 *            The fentrega to set.
	 */
	public void setFentrega(Date fentrega) {
		this.fentrega = fentrega;
	}

	/**
	 * @return Returns the festado.
	 */
	public Date getFestado() {
		return festado;
	}

	/**
	 * @param festado
	 *            The festado to set.
	 */
	public void setFestado(Date festado) {
		this.festado = festado;
	}

	/**
	 * @return Returns the finicialreserva.
	 */
	public Date getFinicialreserva() {
		return finicialreserva;
	}

	/**
	 * @param finicialreserva
	 *            The finicialreserva to set.
	 */
	public void setFinicialreserva(Date finicialreserva) {
		this.finicialreserva = finicialreserva;
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
	 * @return Returns the idarchivo.
	 */
	public String getIdarchivo() {
		return idarchivo;
	}

	/**
	 * @param idarchivo
	 *            The idarchivo to set.
	 */
	public void setIdarchivo(String idarchivo) {
		this.idarchivo = idarchivo;
	}

	/**
	 * @return Returns the orden.
	 */
	public int getOrden() {
		return orden;
	}

	/**
	 * @param orden
	 *            The orden to set.
	 */
	public void setOrden(int orden) {
		this.orden = orden;
	}

	/**
	 * @return Returns the usuarioGestor.
	 */
	public UsuarioVO getUsuarioGestor() {
		return usuarioGestor;
	}

	/**
	 * @param usuarioGestor
	 *            The usuarioGestor to set.
	 */
	public void setUsuarioGestor(UsuarioVO usuarioGestor) {
		this.usuarioGestor = usuarioGestor;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public void setIdMotivo(String idMotivo) {
		this.idMotivo = idMotivo;
	}

	public String getIdMotivo() {
		return idMotivo;
	}

}
