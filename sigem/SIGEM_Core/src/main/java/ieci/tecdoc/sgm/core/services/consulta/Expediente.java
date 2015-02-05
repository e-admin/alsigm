package ieci.tecdoc.sgm.core.services.consulta;

import java.util.Date;

public class Expediente {

	public static final String COD_ESTADO_EXPEDIENTE_INICIADO = "0";
	public static final String COD_ESTADO_EXPEDIENTE_FINALIZADO = "1";

	private String numero;

	private String procedimiento;

	private Date fechaInicio;

	private String numeroRegistro;

	private Date fechaRegistro;

	private String informacionAuxiliar;

	private String aportacion;

	private String codigoPresentacion;
	
	private String notificacion;
	
	private String pagos;
	
	private String estado;

	public String getAportacion() {
		return aportacion;
	}

	public void setAportacion(String aportacion) {
		this.aportacion = aportacion;
	}

	public String getCodigoPresentacion() {
		return codigoPresentacion;
	}

	public void setCodigoPresentacion(String codigoPresentacion) {
		this.codigoPresentacion = codigoPresentacion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getInformacionAuxiliar() {
		return informacionAuxiliar;
	}

	public void setInformacionAuxiliar(String informacionAuxiliar) {
		this.informacionAuxiliar = informacionAuxiliar;
	}

	public String getNotificacion() {
		return notificacion;
	}

	public void setNotificacion(String notificacion) {
		this.notificacion = notificacion;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getNumeroRegistro() {
		return numeroRegistro;
	}

	public void setNumeroRegistro(String numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}

	public String getProcedimiento() {
		return procedimiento;
	}

	public void setProcedimiento(String procedimiento) {
		this.procedimiento = procedimiento;
	}

	public String getPagos() {
		return pagos;
	}

	public void setPagos(String pagos) {
		this.pagos = pagos;
	}
	
	

}
