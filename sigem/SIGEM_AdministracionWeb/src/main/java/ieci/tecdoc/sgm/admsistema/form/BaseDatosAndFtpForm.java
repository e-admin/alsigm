package ieci.tecdoc.sgm.admsistema.form;

import org.apache.struts.action.ActionForm;

public class BaseDatosAndFtpForm extends ActionForm {

	private String direccionBaseDatos;
	private String puertoBaseDatos;
	private String usuarioBaseDatos;
	private String passwordBaseDatos;
	private String passwordRepetidoBaseDatos;
	private String direccionFtp;
	private String puertoFtp;
	private String rutaFtp;
	private String usuarioFtp;
	private String passwordFtp;
	private String passwordRepetidoFtp;
	private String idEntidad;
	private String idImportacion;

	private String tipoBase;
	private String usuarioBaseDatosTE;
	private String passwordBaseDatosTE;
	private String usuarioBaseDatosRP;
	private String passwordBaseDatosRP;
	private String usuarioBaseDatosAD;
	private String passwordBaseDatosAD;
	private String usuarioBaseDatosGE;
	private String passwordBaseDatosGE;
	private String usuarioBaseDatosAudit;
	private String passwordBaseDatosAudit;
	private String usuarioBaseDatosSIR;
	private String passwordBaseDatosSIR;
	private String instancia;

	public String getDireccionBaseDatos() {
		return direccionBaseDatos;
	}

	public void setDireccionBaseDatos(String direccionBaseDatos) {
		this.direccionBaseDatos = direccionBaseDatos;
	}

	public String getPasswordBaseDatos() {
		return passwordBaseDatos;
	}

	public void setPasswordBaseDatos(String passwordBaseDatos) {
		this.passwordBaseDatos = passwordBaseDatos;
	}

	public String getPuertoBaseDatos() {
		return puertoBaseDatos;
	}

	public void setPuertoBaseDatos(String puertoBaseDatos) {
		this.puertoBaseDatos = puertoBaseDatos;
	}

	public String getUsuarioBaseDatos() {
		return usuarioBaseDatos;
	}

	public void setUsuarioBaseDatos(String usuarioBaseDatos) {
		this.usuarioBaseDatos = usuarioBaseDatos;
	}

	public String getIdEntidad() {
		return idEntidad;
	}

	public void setIdEntidad(String idEntidad) {
		this.idEntidad = idEntidad;
	}

	public String getPasswordRepetidoBaseDatos() {
		return passwordRepetidoBaseDatos;
	}

	public void setPasswordRepetidoBaseDatos(String passwordRepetidoBaseDatos) {
		this.passwordRepetidoBaseDatos = passwordRepetidoBaseDatos;
	}

	public String getDireccionFtp() {
		return direccionFtp;
	}

	public void setDireccionFtp(String direccionFtp) {
		this.direccionFtp = direccionFtp;
	}

	public String getPasswordFtp() {
		return passwordFtp;
	}

	public void setPasswordFtp(String passwordFtp) {
		this.passwordFtp = passwordFtp;
	}

	public String getPasswordRepetidoFtp() {
		return passwordRepetidoFtp;
	}

	public void setPasswordRepetidoFtp(String passwordRepetidoFtp) {
		this.passwordRepetidoFtp = passwordRepetidoFtp;
	}

	public String getPuertoFtp() {
		return puertoFtp;
	}

	public void setPuertoFtp(String puertoFtp) {
		this.puertoFtp = puertoFtp;
	}

	public String getRutaFtp() {
		return rutaFtp;
	}

	public void setRutaFtp(String rutaFtp) {
		this.rutaFtp = rutaFtp;
	}

	public String getUsuarioFtp() {
		return usuarioFtp;
	}

	public void setUsuarioFtp(String usuarioFtp) {
		this.usuarioFtp = usuarioFtp;
	}

	public String getIdImportacion() {
		return idImportacion;
	}

	public void setIdImportacion(String idImportacion) {
		this.idImportacion = idImportacion;
	}

	public String getInstancia() {
		return instancia;
	}

	public void setInstancia(String instancia) {
		this.instancia = instancia;
	}

	public String getPasswordBaseDatosAD() {
		return passwordBaseDatosAD;
	}

	public void setPasswordBaseDatosAD(String passwordBaseDatosAD) {
		this.passwordBaseDatosAD = passwordBaseDatosAD;
	}

	public String getPasswordBaseDatosGE() {
		return passwordBaseDatosGE;
	}

	public void setPasswordBaseDatosGE(String passwordBaseDatosGE) {
		this.passwordBaseDatosGE = passwordBaseDatosGE;
	}

	public String getPasswordBaseDatosRP() {
		return passwordBaseDatosRP;
	}

	public void setPasswordBaseDatosRP(String passwordBaseDatosRP) {
		this.passwordBaseDatosRP = passwordBaseDatosRP;
	}

	public String getPasswordBaseDatosTE() {
		return passwordBaseDatosTE;
	}

	public void setPasswordBaseDatosTE(String passwordBaseDatosTE) {
		this.passwordBaseDatosTE = passwordBaseDatosTE;
	}

	public String getTipoBase() {
		return tipoBase;
	}

	public void setTipoBase(String tipoBase) {
		this.tipoBase = tipoBase;
	}

	public String getUsuarioBaseDatosAD() {
		return usuarioBaseDatosAD;
	}

	public void setUsuarioBaseDatosAD(String usuarioBaseDatosAD) {
		this.usuarioBaseDatosAD = usuarioBaseDatosAD;
	}

	public String getUsuarioBaseDatosGE() {
		return usuarioBaseDatosGE;
	}

	public void setUsuarioBaseDatosGE(String usuarioBaseDatosGE) {
		this.usuarioBaseDatosGE = usuarioBaseDatosGE;
	}

	public String getUsuarioBaseDatosRP() {
		return usuarioBaseDatosRP;
	}

	public void setUsuarioBaseDatosRP(String usuarioBaseDatosRP) {
		this.usuarioBaseDatosRP = usuarioBaseDatosRP;
	}

	public String getUsuarioBaseDatosTE() {
		return usuarioBaseDatosTE;
	}

	public void setUsuarioBaseDatosTE(String usuarioBaseDatosTE) {
		this.usuarioBaseDatosTE = usuarioBaseDatosTE;
	}

	public String getUsuarioBaseDatosAudit() {
		return usuarioBaseDatosAudit;
	}

	public void setUsuarioBaseDatosAudit(String usuarioBaseDatosAudit) {
		this.usuarioBaseDatosAudit = usuarioBaseDatosAudit;
	}

	public String getPasswordBaseDatosAudit() {
		return passwordBaseDatosAudit;
	}

	public void setPasswordBaseDatosAudit(String passwordBaseDatosAudit) {
		this.passwordBaseDatosAudit = passwordBaseDatosAudit;
	}

	public String getUsuarioBaseDatosSIR() {
		return usuarioBaseDatosSIR;
	}

	public void setUsuarioBaseDatosSIR(String usuarioBaseDatosSIR) {
		this.usuarioBaseDatosSIR = usuarioBaseDatosSIR;
	}

	public String getPasswordBaseDatosSIR() {
		return passwordBaseDatosSIR;
	}

	public void setPasswordBaseDatosSIR(String passwordBaseDatosSIR) {
		this.passwordBaseDatosSIR = passwordBaseDatosSIR;
	}

	private final static long serialVersionUID = 0;
}