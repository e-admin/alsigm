package salas.form;

import salas.vos.MesaVO;

import common.forms.CustomForm;
import common.util.DateUtils;
import common.util.StringUtils;

/**
 * Formulario para la gestión de Mesas
 * 
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class MesaForm extends CustomForm {

	private static final long serialVersionUID = 1L;

	/**
	 * Identificador de la Mesa
	 */
	private String idMesa = null;

	/**
	 * Codigo de la Mesa
	 */
	private String codigo = null;

	/**
	 * Numero de orden de la mesa en la sala
	 */
	private Integer numOrden = null;

	/**
	 * Identificador de la Sala
	 */
	private String idSala = null;

	/**
	 * Nombre de la Sala
	 */
	private String nombreSala = null;

	/**
	 * Estado en el que se encuentra la Mesa
	 */
	private String estado = null;

	/**
	 * Fecha de estado de la Mesa
	 */
	private String fechaEstado = null;

	/**
	 * Identificador del usuario de consulta
	 */
	private String idUsuarioConsulta = null;

	/**
	 * Nombre del usuario de consulta
	 */
	private String nombreUsuarioConsulta = null;

	/**
	 * Numero de mesas a crear en la sala
	 */
	private String numeroMesas = null;

	/**
	 * Identificadores de las mesas
	 */
	private String[] idsMesa = null;

	public String getIdMesa() {
		return idMesa;
	}

	public void setIdMesa(String idMesa) {
		this.idMesa = idMesa;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Integer getNumOrden() {
		return numOrden;
	}

	public void setNumOrden(Integer numOrden) {
		this.numOrden = numOrden;
	}

	public String getIdSala() {
		return idSala;
	}

	public void setIdSala(String idSala) {
		this.idSala = idSala;
	}

	public String getNombreSala() {
		return nombreSala;
	}

	public void setNombreSala(String nombreSala) {
		this.nombreSala = nombreSala;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getFechaEstado() {
		return fechaEstado;
	}

	public void setFechaEstado(String fechaEstado) {
		this.fechaEstado = fechaEstado;
	}

	public String getIdUsuarioConsulta() {
		return idUsuarioConsulta;
	}

	public void setIdUsuarioConsulta(String idUsuarioConsulta) {
		this.idUsuarioConsulta = idUsuarioConsulta;
	}

	public String getNombreUsuarioConsulta() {
		return nombreUsuarioConsulta;
	}

	public void setNombreUsuarioConsulta(String nombreUsuarioConsulta) {
		this.nombreUsuarioConsulta = nombreUsuarioConsulta;
	}

	public String getNumeroMesas() {
		return numeroMesas;
	}

	public void setNumeroMesas(String numeroMesas) {
		this.numeroMesas = numeroMesas;
	}

	public String[] getIdsMesa() {
		return idsMesa;
	}

	public void setIdsMesa(String[] idsMesa) {
		this.idsMesa = idsMesa;
	}

	public void set(MesaVO mesaVO) {
		reset();
		if (mesaVO != null) {
			this.idMesa = mesaVO.getId();
			this.codigo = mesaVO.getCodigo();
			this.numOrden = mesaVO.getNumOrden();
			this.idSala = mesaVO.getIdSala();
			this.estado = mesaVO.getEstado();
			if (mesaVO.getFechaEstado() != null)
				this.fechaEstado = DateUtils
						.formatDate(mesaVO.getFechaEstado());
			this.idUsuarioConsulta = mesaVO.getIdUsrCSala();
		}
	}

	public void populate(MesaVO mesaVO) {
		mesaVO.setId(this.idSala);
		mesaVO.setCodigo(this.codigo);
		mesaVO.setNumorden(this.numOrden);
		mesaVO.setIdSala(this.idSala);
		mesaVO.setEstado(this.estado);
		if (StringUtils.isNotEmpty(this.fechaEstado))
			mesaVO.setFechaEstado(DateUtils.getDate(this.fechaEstado));
		mesaVO.setIdUsrCSala(this.idUsuarioConsulta);
	}

	public void reset() {
		this.idMesa = null;
		this.codigo = null;
		this.numOrden = null;
		this.estado = null;
		this.fechaEstado = null;
		this.idUsuarioConsulta = null;
	}
}