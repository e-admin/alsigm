package solicitudes.forms;

import java.lang.reflect.InvocationTargetException;

import solicitudes.consultas.ConsultasConstants;
import solicitudes.prestamos.PrestamosConstants;

import common.util.FormUtils;

public class ConsultaUnidadesDocumentalesForm extends SolicitudesBaseForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/* Fecha Ini */
	private String fechaCompIni = null;
	private String fechaFormatoIni = null;
	private String fechaDIni = null;
	private String fechaMIni = null;
	private String fechaAIni = null;
	private String fechaSIni = null;
	private String fechaIniFormatoIni = null;
	private String fechaIniDIni = null;
	private String fechaIniMIni = null;
	private String fechaIniAIni = null;
	private String fechaIniSIni = null;
	private String fechaFinFormatoIni = null;
	private String fechaFinDIni = null;
	private String fechaFinMIni = null;
	private String fechaFinAIni = null;
	private String fechaFinSIni = null;

	/* Fecha final */
	private String fechaCompFin = null;
	private String fechaFormatoFin = null;
	private String fechaAFin = null;
	private String fechaMFin = null;
	private String fechaDFin = null;
	private String fechaSFin = null;
	private String fechaIniFormatoFin = null;
	private String fechaIniAFin = null;
	private String fechaIniMFin = null;
	private String fechaIniDFin = null;
	private String fechaIniSFin = null;
	private String fechaFinFormatoFin = null;
	private String fechaFinAFin = null;
	private String fechaFinMFin = null;
	private String fechaFinDFin = null;
	private String fechaFinSFin = null;

	private String tipoUsuarioPrestamos = null;
	private String tipoUsuarioConsultas = null;
	private String[] tiposServicio = new String[] {
			String.valueOf(PrestamosConstants.TIPO_SOLICITUD_PRESTAMO),
			String.valueOf(ConsultasConstants.TIPO_SOLICITUD_CONSULTA) };

	private String[] idObjetoAmbito = new String[0];
	private String solicitante = null;
	private String numexp = null;
	private String expediente_like = null;

	private String signatura = null;
	private String signatura_like = null;

	public void reset() throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		FormUtils.resetDeclaredFields(this);
		tiposServicio = new String[] {
				String.valueOf(PrestamosConstants.TIPO_SOLICITUD_PRESTAMO),
				String.valueOf(ConsultasConstants.TIPO_SOLICITUD_CONSULTA) };

	}

	public String getFechaAIni() {
		return fechaAIni;
	}

	public void setFechaAIni(String fechaAIni) {
		this.fechaAIni = fechaAIni;
	}

	public String getFechaCompIni() {
		return fechaCompIni;
	}

	public void setFechaCompIni(String fechaCompIni) {
		this.fechaCompIni = fechaCompIni;
	}

	public String getFechaDIni() {
		return fechaDIni;
	}

	public void setFechaDIni(String fechaDIni) {
		this.fechaDIni = fechaDIni;
	}

	public String getFechaFinAIni() {
		return fechaFinAIni;
	}

	public void setFechaFinAIni(String fechaFinAIni) {
		this.fechaFinAIni = fechaFinAIni;
	}

	public String getFechaFinDIni() {
		return fechaFinDIni;
	}

	public void setFechaFinDIni(String fechaFinDIni) {
		this.fechaFinDIni = fechaFinDIni;
	}

	public String getFechaFinFormatoIni() {
		return fechaFinFormatoIni;
	}

	public void setFechaFinFormatoIni(String fechaFinFormatoIni) {
		this.fechaFinFormatoIni = fechaFinFormatoIni;
	}

	public String getFechaFinMIni() {
		return fechaFinMIni;
	}

	public void setFechaFinMIni(String fechaFinMIni) {
		this.fechaFinMIni = fechaFinMIni;
	}

	public String getFechaFinSIni() {
		return fechaFinSIni;
	}

	public void setFechaFinSIni(String fechaFinSIni) {
		this.fechaFinSIni = fechaFinSIni;
	}

	public String getFechaFormatoIni() {
		return fechaFormatoIni;
	}

	public void setFechaFormatoIni(String fechaFormatoIni) {
		this.fechaFormatoIni = fechaFormatoIni;
	}

	public String getFechaIniAIni() {
		return fechaIniAIni;
	}

	public void setFechaIniAIni(String fechaIniAIni) {
		this.fechaIniAIni = fechaIniAIni;
	}

	public String getFechaIniDIni() {
		return fechaIniDIni;
	}

	public void setFechaIniDIni(String fechaIniDIni) {
		this.fechaIniDIni = fechaIniDIni;
	}

	public String getFechaIniFormatoIni() {
		return fechaIniFormatoIni;
	}

	public void setFechaIniFormatoIni(String fechaIniFormatoIni) {
		this.fechaIniFormatoIni = fechaIniFormatoIni;
	}

	public String getFechaIniMIni() {
		return fechaIniMIni;
	}

	public void setFechaIniMIni(String fechaIniMIni) {
		this.fechaIniMIni = fechaIniMIni;
	}

	public String getFechaIniSIni() {
		return fechaIniSIni;
	}

	public void setFechaIniSIni(String fechaIniSIni) {
		this.fechaIniSIni = fechaIniSIni;
	}

	public String getFechaMIni() {
		return fechaMIni;
	}

	public void setFechaMIni(String fechaMIni) {
		this.fechaMIni = fechaMIni;
	}

	public String getFechaSIni() {
		return fechaSIni;
	}

	public void setFechaSIni(String fechaSIni) {
		this.fechaSIni = fechaSIni;
	}

	public String getTipoUsuarioConsultas() {
		return tipoUsuarioConsultas;
	}

	public void setTipoUsuarioConsultas(String tipoUsuarioConsultas) {
		this.tipoUsuarioConsultas = tipoUsuarioConsultas;
	}

	public String getTipoUsuarioPrestamos() {
		return tipoUsuarioPrestamos;
	}

	public void setTipoUsuarioPrestamos(String tipoUsuarioPrestamos) {
		this.tipoUsuarioPrestamos = tipoUsuarioPrestamos;
	}

	public String[] getTiposServicio() {
		return tiposServicio;
	}

	public void setTiposServicio(String[] tiposServicio) {
		this.tiposServicio = tiposServicio;
	}

	public String getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(String solicitante) {
		this.solicitante = solicitante;
	}

	public String getNumexp() {
		return numexp;
	}

	public void setNumexp(String numexp) {
		this.numexp = numexp;
	}

	public String getExpediente_like() {
		return expediente_like;
	}

	public void setExpediente_like(String expediente_like) {
		this.expediente_like = expediente_like;
	}

	public String getFechaCompFin() {
		return fechaCompFin;
	}

	public void setFechaCompFin(String fechaCompFin) {
		this.fechaCompFin = fechaCompFin;
	}

	public String getFechaFormatoFin() {
		return fechaFormatoFin;
	}

	public void setFechaFormatoFin(String fechaFormatoFin) {
		this.fechaFormatoFin = fechaFormatoFin;
	}

	public String getFechaAFin() {
		return fechaAFin;
	}

	public void setFechaAFin(String fechaAFin) {
		this.fechaAFin = fechaAFin;
	}

	public String getFechaMFin() {
		return fechaMFin;
	}

	public void setFechaMFin(String fechaMFin) {
		this.fechaMFin = fechaMFin;
	}

	public String getFechaDFin() {
		return fechaDFin;
	}

	public void setFechaDFin(String fechaDFin) {
		this.fechaDFin = fechaDFin;
	}

	public String getFechaSFin() {
		return fechaSFin;
	}

	public void setFechaSFin(String fechaSFin) {
		this.fechaSFin = fechaSFin;
	}

	public String getFechaIniFormatoFin() {
		return fechaIniFormatoFin;
	}

	public void setFechaIniFormatoFin(String fechaIniFormatoFin) {
		this.fechaIniFormatoFin = fechaIniFormatoFin;
	}

	public String getFechaIniAFin() {
		return fechaIniAFin;
	}

	public void setFechaIniAFin(String fechaIniAFin) {
		this.fechaIniAFin = fechaIniAFin;
	}

	public String getFechaIniMFin() {
		return fechaIniMFin;
	}

	public void setFechaIniMFin(String fechaIniMFin) {
		this.fechaIniMFin = fechaIniMFin;
	}

	public String getFechaIniDFin() {
		return fechaIniDFin;
	}

	public void setFechaIniDFin(String fechaIniDFin) {
		this.fechaIniDFin = fechaIniDFin;
	}

	public String getFechaIniSFin() {
		return fechaIniSFin;
	}

	public void setFechaIniSFin(String fechaIniSFin) {
		this.fechaIniSFin = fechaIniSFin;
	}

	public String getFechaFinFormatoFin() {
		return fechaFinFormatoFin;
	}

	public void setFechaFinFormatoFin(String fechaFinFormatoFin) {
		this.fechaFinFormatoFin = fechaFinFormatoFin;
	}

	public String getFechaFinAFin() {
		return fechaFinAFin;
	}

	public void setFechaFinAFin(String fechaFinAFin) {
		this.fechaFinAFin = fechaFinAFin;
	}

	public String getFechaFinMFin() {
		return fechaFinMFin;
	}

	public void setFechaFinMFin(String fechaFinMFin) {
		this.fechaFinMFin = fechaFinMFin;
	}

	public String getFechaFinDFin() {
		return fechaFinDFin;
	}

	public void setFechaFinDFin(String fechaFinDFin) {
		this.fechaFinDFin = fechaFinDFin;
	}

	public String getFechaFinSFin() {
		return fechaFinSFin;
	}

	public void setFechaFinSFin(String fechaFinSFin) {
		this.fechaFinSFin = fechaFinSFin;
	}

	public String[] getIdObjetoAmbito() {
		return idObjetoAmbito;
	}

	public void setIdObjetoAmbito(String[] idObjetoAmbito) {
		this.idObjetoAmbito = idObjetoAmbito;
	}

	public String getSignatura() {
		return signatura;
	}

	public void setSignatura(String signatura) {
		this.signatura = signatura;
	}

	public String getSignatura_like() {
		return signatura_like;
	}

	public void setSignatura_like(String signaturaLike) {
		signatura_like = signaturaLike;
	}

}