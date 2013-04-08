
package es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo;

import java.util.Date;

/**
 * Clase que contiene el estado del intercambio registral de Salida
 *
 */
public class EstadoIntercambioRegistralSalidaVO {

	/**
	 * Identificador del Estado del Intercambio Registral
	 */
	private Long id;

	/**
	 * Identificador del Estado principal del Intercambio Registral
	 */
	private Long idExReg;

	/**
	 * Estado del Intercambio Registral
	 */
	private EstadoIntercambioRegistralSalidaEnumVO estado;

	/**
	 * Fecha del Estado para el Intercambio Registral
	 */
	private Date fechaEstado;

	/**
	 * Nombre de usuario involucrado en el estado del Intercambio Registral
	 */
	private String userName;
	
	/**
	 * comentario acerca del estado, por ejemplo porque se rechazo
	 */
	private String comentarios;




	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the idExReg
	 */
	public Long getIdExReg() {
		return idExReg;
	}

	/**
	 * @param idExReg the idExReg to set
	 */
	public void setIdExReg(Long idExReg) {
		this.idExReg = idExReg;
	}

	/**
	 * @return the estado
	 */
	public EstadoIntercambioRegistralSalidaEnumVO getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(EstadoIntercambioRegistralSalidaEnumVO estado) {
		this.estado = estado;
	}

	/**
	 * @return the fechaEstado
	 */
	public Date getFechaEstado() {
		return fechaEstado;
	}

	/**
	 * @param fechaEstado the fechaEstado to set
	 */
	public void setFechaEstado(Date fechaEstado) {
		this.fechaEstado = fechaEstado;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getIdEstado()
	{
		int id = EstadoIntercambioRegistralSalidaEnumVO.PENDIENTE.getValue();
		if(estado!=null)
		{
			id=estado.getValue();
		}
		return id;
	}

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}


}
