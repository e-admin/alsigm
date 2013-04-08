package es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo;

import java.util.Date;
import java.util.List;

public class IntercambioRegistralSalidaVO extends BaseIntercambioRegistralVO {

	public static final int TIPO_ENTRADA=1;
	public static final int TIPO_SALIDA=2;

	protected Long id;

	protected Long idLibro;

	protected Long idRegistro;

	protected Date fechaEstado;

	protected EstadoIntercambioRegistralSalidaEnumVO estado;

	protected String idIntercambioRegistral;

	protected Long idIntercambioInterno;

	protected Date fechaIntercambio;

	/**
	 * tipo del libro del que se estar creando el intercambio. en este vo los valores seran obtenidos de los enumerados de <code>TipoLibroEnum</code> 
	 */
	protected Integer tipoOrigen;

	protected Integer idOfic;

	protected String nombreOfic;

	protected DetalleIntercambioRegistralSalidaVO detalleIntercambioRegistralSalida;

	protected List<EstadoIntercambioRegistralSalidaVO> estadosIntercambioRegistralSalida;

	protected String username;

	/**
	 * Codigo de entidad al que ha sido enviado el intercambio
	 */
	protected String codeEntity;
	protected String nameEntity;

	/**
	 * Codigo de unidad de tramitación al que ha sido enviado el intercambio (si procede)
	 */
	protected String codeTramunit;
	protected String nameTramunit;
	
	/**
	 * comentarios acerca del intercabio de salida, motivo de rechazo, etc ..
	 */
	protected String comentarios;

	/**
	 * @return the estadosIntercambioRegistralSalida
	 */
	public List<EstadoIntercambioRegistralSalidaVO> getEstadosIntercambioRegistralSalida() {
		return estadosIntercambioRegistralSalida;
	}

	/**
	 * @param estadosIntercambioRegistralSalida the estadosIntercambioRegistralSalida to set
	 */
	public void setEstadosIntercambioRegistralSalida(
			List<EstadoIntercambioRegistralSalidaVO> estadosIntercambioRegistralSalida) {
		this.estadosIntercambioRegistralSalida = estadosIntercambioRegistralSalida;
	}

	/**
	 * @return the nombreOfic
	 */
	public String getNombreOfic() {
		return nombreOfic;
	}

	/**
	 * @param nombreOfic the nombreOfic to set
	 */
	public void setNombreOfic(String nombreOfic) {
		this.nombreOfic = nombreOfic;
	}

	public Long getId() {
		return id;
	}


	/**
	 * @return the fechaIntercambio
	 */
	public Date getFechaIntercambio() {
		return fechaIntercambio;
	}

	/**
	 * @param fechaIntercambio the fechaIntercambio to set
	 */
	public void setFechaIntercambio(Date fechaIntercambio) {
		this.fechaIntercambio = fechaIntercambio;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public Long getIdLibro() {
		return idLibro;
	}


	public void setIdLibro(Long idLibro) {
		this.idLibro = idLibro;
	}


	public Long getIdRegistro() {
		return idRegistro;
	}


	public void setIdRegistro(Long idRegistro) {
		this.idRegistro = idRegistro;
	}


	public Date getFechaEstado() {
		return fechaEstado;
	}


	public void setFechaEstado(Date fechaEstado) {
		this.fechaEstado = fechaEstado;
	}


	public EstadoIntercambioRegistralSalidaEnumVO getEstado() {
		return estado;
	}


	public void setEstado(EstadoIntercambioRegistralSalidaEnumVO estado) {
		this.estado = estado;
	}


	public Integer getTipoOrigen() {
		return tipoOrigen;
	}


	public void setTipoOrigen(Integer tipoOrigen) {
		this.tipoOrigen = tipoOrigen;
	}



	public Integer getIdOfic() {
		return idOfic;
	}


	public void setIdOfic(Integer idOfic) {
		this.idOfic = idOfic;
	}


	public DetalleIntercambioRegistralSalidaVO getDetalleIntercambioRegistralSalida() {
		return detalleIntercambioRegistralSalida;
	}


	public void setDetalleIntercambioRegistralSalida(
			DetalleIntercambioRegistralSalidaVO detalleIntercambioRegistralSalida) {
		this.detalleIntercambioRegistralSalida = detalleIntercambioRegistralSalida;
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

	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}

	public String getIdIntercambioRegistral() {
		return idIntercambioRegistral;
	}

	public void setIdIntercambioRegistral(String idIntercambioRegistral) {
		this.idIntercambioRegistral = idIntercambioRegistral;
	}

	public Long getIdIntercambioInterno() {
		return idIntercambioInterno;
	}

	public void setIdIntercambioInterno(String idIntercambioInterno) {
		this.idIntercambioInterno = Long.parseLong(idIntercambioInterno);
	}
	
	public void setIdIntercambioInterno(Long idIntercambioInterno) {
		this.idIntercambioInterno = idIntercambioInterno;
	}

	public String getCodeEntity() {
		return codeEntity;
	}

	public void setCodeEntity(String codeEntity) {
		this.codeEntity = codeEntity;
	}

	public String getNameEntity() {
		return nameEntity;
	}

	public void setNameEntity(String nameEntity) {
		this.nameEntity = nameEntity;
	}

	public String getCodeTramunit() {
		return codeTramunit;
	}

	public void setCodeTramunit(String codeTramunit) {
		this.codeTramunit = codeTramunit;
	}

	public String getNameTramunit() {
		return nameTramunit;
	}

	public void setNameTramunit(String nameTramunit) {
		this.nameTramunit = nameTramunit;
	}

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

}
