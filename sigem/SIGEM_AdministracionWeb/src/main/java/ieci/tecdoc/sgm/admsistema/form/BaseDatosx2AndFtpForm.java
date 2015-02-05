package ieci.tecdoc.sgm.admsistema.form;

import org.apache.struts.action.ActionForm;

public class BaseDatosx2AndFtpForm extends ActionForm {

	private String direccionBaseDatosExp;
	private String puertoBaseDatosExp;
	private String usuarioBaseDatosExp;
	private String passwordBaseDatosExp;
	private String passwordRepetidoBaseDatosExp;
	private String direccionBaseDatosImp;
	private String puertoBaseDatosImp;
	private String usuarioBaseDatosImp;
	private String passwordBaseDatosImp;
	private String passwordRepetidoBaseDatosImp;
	private String direccionFtp;
	private String puertoFtp;
	private String rutaFtp;
	private String usuarioFtp;
	private String passwordFtp;
	private String passwordRepetidoFtp;
	private String idEntidadExp;
	private String idEntidadImp;
	private boolean limpiar;

	private String tipoBaseExp;
	private String usuarioBaseDatosTEExp;
	private String passwordBaseDatosTEExp;
	private String usuarioBaseDatosRPExp;
	private String passwordBaseDatosRPExp;
	private String usuarioBaseDatosADExp;
	private String passwordBaseDatosADExp;
	private String usuarioBaseDatosGEExp;
	private String passwordBaseDatosGEExp;
	private String usuarioBaseDatosAuditExp;
	private String passwordBaseDatosAuditExp;
	private String usuarioBaseDatosSIRExp;
	private String passwordBaseDatosSIRExp;
	private String instanciaExp;

	private String tipoBaseImp;
	private String usuarioBaseDatosTEImp;
	private String passwordBaseDatosTEImp;
	private String usuarioBaseDatosRPImp;
	private String passwordBaseDatosRPImp;
	private String usuarioBaseDatosADImp;
	private String passwordBaseDatosADImp;
	private String usuarioBaseDatosGEImp;
	private String passwordBaseDatosGEImp;
	private String usuarioBaseDatosAuditImp;
	private String passwordBaseDatosAuditImp;
	private String usuarioBaseDatosSIRImp;
	private String passwordBaseDatosSIRImp;
	private String instanciaImp;

	public String getDireccionBaseDatosExp() {
		return direccionBaseDatosExp;
	}

	public void setDireccionBaseDatosExp(String direccionBaseDatosExp) {
		this.direccionBaseDatosExp = direccionBaseDatosExp;
	}

	public String getDireccionBaseDatosImp() {
		return direccionBaseDatosImp;
	}

	public void setDireccionBaseDatosImp(String direccionBaseDatosImp) {
		this.direccionBaseDatosImp = direccionBaseDatosImp;
	}

	public String getPasswordBaseDatosExp() {
		return passwordBaseDatosExp;
	}

	public void setPasswordBaseDatosExp(String passwordBaseDatosExp) {
		this.passwordBaseDatosExp = passwordBaseDatosExp;
	}

	public String getPasswordBaseDatosImp() {
		return passwordBaseDatosImp;
	}

	public void setPasswordBaseDatosImp(String passwordBaseDatosImp) {
		this.passwordBaseDatosImp = passwordBaseDatosImp;
	}

	public String getPasswordRepetidoBaseDatosExp() {
		return passwordRepetidoBaseDatosExp;
	}

	public void setPasswordRepetidoBaseDatosExp(String passwordRepetidoBaseDatosExp) {
		this.passwordRepetidoBaseDatosExp = passwordRepetidoBaseDatosExp;
	}

	public String getPasswordRepetidoBaseDatosImp() {
		return passwordRepetidoBaseDatosImp;
	}

	public void setPasswordRepetidoBaseDatosImp(String passwordRepetidoBaseDatosImp) {
		this.passwordRepetidoBaseDatosImp = passwordRepetidoBaseDatosImp;
	}

	public String getPuertoBaseDatosExp() {
		return puertoBaseDatosExp;
	}

	public void setPuertoBaseDatosExp(String puertoBaseDatosExp) {
		this.puertoBaseDatosExp = puertoBaseDatosExp;
	}

	public String getPuertoBaseDatosImp() {
		return puertoBaseDatosImp;
	}

	public void setPuertoBaseDatosImp(String puertoBaseDatosImp) {
		this.puertoBaseDatosImp = puertoBaseDatosImp;
	}

	public String getUsuarioBaseDatosExp() {
		return usuarioBaseDatosExp;
	}

	public void setUsuarioBaseDatosExp(String usuarioBaseDatosExp) {
		this.usuarioBaseDatosExp = usuarioBaseDatosExp;
	}

	public String getUsuarioBaseDatosImp() {
		return usuarioBaseDatosImp;
	}

	public void setUsuarioBaseDatosImp(String usuarioBaseDatosImp) {
		this.usuarioBaseDatosImp = usuarioBaseDatosImp;
	}

	public String getIdEntidadExp() {
		return idEntidadExp;
	}

	public void setIdEntidadExp(String idEntidadExp) {
		this.idEntidadExp = idEntidadExp;
	}

	public String getIdEntidadImp() {
		return idEntidadImp;
	}

	public void setIdEntidadImp(String idEntidadImp) {
		this.idEntidadImp = idEntidadImp;
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

	public boolean isLimpiar() {
		return limpiar;
	}

	public void setLimpiar(boolean limpiar) {
		this.limpiar = limpiar;
	}

	public String getInstanciaExp() {
		return instanciaExp;
	}

	public void setInstanciaExp(String instanciaExp) {
		this.instanciaExp = instanciaExp;
	}

	public String getInstanciaImp() {
		return instanciaImp;
	}

	public void setInstanciaImp(String instanciaImp) {
		this.instanciaImp = instanciaImp;
	}

	public String getPasswordBaseDatosADExp() {
		return passwordBaseDatosADExp;
	}

	public void setPasswordBaseDatosADExp(String passwordBaseDatosADExp) {
		this.passwordBaseDatosADExp = passwordBaseDatosADExp;
	}

	public String getPasswordBaseDatosADImp() {
		return passwordBaseDatosADImp;
	}

	public void setPasswordBaseDatosADImp(String passwordBaseDatosADImp) {
		this.passwordBaseDatosADImp = passwordBaseDatosADImp;
	}

	public String getPasswordBaseDatosGEExp() {
		return passwordBaseDatosGEExp;
	}

	public void setPasswordBaseDatosGEExp(String passwordBaseDatosGEExp) {
		this.passwordBaseDatosGEExp = passwordBaseDatosGEExp;
	}

	public String getPasswordBaseDatosGEImp() {
		return passwordBaseDatosGEImp;
	}

	public void setPasswordBaseDatosGEImp(String passwordBaseDatosGEImp) {
		this.passwordBaseDatosGEImp = passwordBaseDatosGEImp;
	}

	public String getPasswordBaseDatosRPExp() {
		return passwordBaseDatosRPExp;
	}

	public void setPasswordBaseDatosRPExp(String passwordBaseDatosRPExp) {
		this.passwordBaseDatosRPExp = passwordBaseDatosRPExp;
	}

	public String getPasswordBaseDatosRPImp() {
		return passwordBaseDatosRPImp;
	}

	public void setPasswordBaseDatosRPImp(String passwordBaseDatosRPImp) {
		this.passwordBaseDatosRPImp = passwordBaseDatosRPImp;
	}

	public String getPasswordBaseDatosTEExp() {
		return passwordBaseDatosTEExp;
	}

	public void setPasswordBaseDatosTEExp(String passwordBaseDatosTEExp) {
		this.passwordBaseDatosTEExp = passwordBaseDatosTEExp;
	}

	public String getPasswordBaseDatosTEImp() {
		return passwordBaseDatosTEImp;
	}

	public void setPasswordBaseDatosTEImp(String passwordBaseDatosTEImp) {
		this.passwordBaseDatosTEImp = passwordBaseDatosTEImp;
	}

	public String getTipoBaseExp() {
		return tipoBaseExp;
	}

	public void setTipoBaseExp(String tipoBaseExp) {
		this.tipoBaseExp = tipoBaseExp;
	}

	public String getTipoBaseImp() {
		return tipoBaseImp;
	}

	public void setTipoBaseImp(String tipoBaseImp) {
		this.tipoBaseImp = tipoBaseImp;
	}

	public String getUsuarioBaseDatosADExp() {
		return usuarioBaseDatosADExp;
	}

	public void setUsuarioBaseDatosADExp(String usuarioBaseDatosADExp) {
		this.usuarioBaseDatosADExp = usuarioBaseDatosADExp;
	}

	public String getUsuarioBaseDatosADImp() {
		return usuarioBaseDatosADImp;
	}

	public void setUsuarioBaseDatosADImp(String usuarioBaseDatosADImp) {
		this.usuarioBaseDatosADImp = usuarioBaseDatosADImp;
	}

	public String getUsuarioBaseDatosGEExp() {
		return usuarioBaseDatosGEExp;
	}

	public void setUsuarioBaseDatosGEExp(String usuarioBaseDatosGEExp) {
		this.usuarioBaseDatosGEExp = usuarioBaseDatosGEExp;
	}

	public String getUsuarioBaseDatosGEImp() {
		return usuarioBaseDatosGEImp;
	}

	public void setUsuarioBaseDatosGEImp(String usuarioBaseDatosGEImp) {
		this.usuarioBaseDatosGEImp = usuarioBaseDatosGEImp;
	}

	public String getUsuarioBaseDatosRPExp() {
		return usuarioBaseDatosRPExp;
	}

	public void setUsuarioBaseDatosRPExp(String usuarioBaseDatosRPExp) {
		this.usuarioBaseDatosRPExp = usuarioBaseDatosRPExp;
	}

	public String getUsuarioBaseDatosRPImp() {
		return usuarioBaseDatosRPImp;
	}

	public void setUsuarioBaseDatosRPImp(String usuarioBaseDatosRPImp) {
		this.usuarioBaseDatosRPImp = usuarioBaseDatosRPImp;
	}

	public String getUsuarioBaseDatosTEExp() {
		return usuarioBaseDatosTEExp;
	}

	public void setUsuarioBaseDatosTEExp(String usuarioBaseDatosTEExp) {
		this.usuarioBaseDatosTEExp = usuarioBaseDatosTEExp;
	}

	public String getUsuarioBaseDatosTEImp() {
		return usuarioBaseDatosTEImp;
	}

	public void setUsuarioBaseDatosTEImp(String usuarioBaseDatosTEImp) {
		this.usuarioBaseDatosTEImp = usuarioBaseDatosTEImp;
	}

	public String getUsuarioBaseDatosAuditExp() {
		return usuarioBaseDatosAuditExp;
	}

	public void setUsuarioBaseDatosAuditExp(String usuarioBaseDatosAuditExp) {
		this.usuarioBaseDatosAuditExp = usuarioBaseDatosAuditExp;
	}

	public String getPasswordBaseDatosAuditExp() {
		return passwordBaseDatosAuditExp;
	}

	public void setPasswordBaseDatosAuditExp(String passwordBaseDatosAuditExp) {
		this.passwordBaseDatosAuditExp = passwordBaseDatosAuditExp;
	}

	public String getUsuarioBaseDatosSIRExp() {
		return usuarioBaseDatosSIRExp;
	}

	public void setUsuarioBaseDatosSIRExp(String usuarioBaseDatosSIRExp) {
		this.usuarioBaseDatosSIRExp = usuarioBaseDatosSIRExp;
	}

	public String getPasswordBaseDatosSIRExp() {
		return passwordBaseDatosSIRExp;
	}

	public void setPasswordBaseDatosSIRExp(String passwordBaseDatosSIRExp) {
		this.passwordBaseDatosSIRExp = passwordBaseDatosSIRExp;
	}

	public String getUsuarioBaseDatosAuditImp() {
		return usuarioBaseDatosAuditImp;
	}

	public void setUsuarioBaseDatosAuditImp(String usuarioBaseDatosAuditImp) {
		this.usuarioBaseDatosAuditImp = usuarioBaseDatosAuditImp;
	}

	public String getPasswordBaseDatosAuditImp() {
		return passwordBaseDatosAuditImp;
	}

	public void setPasswordBaseDatosAuditImp(String passwordBaseDatosAuditImp) {
		this.passwordBaseDatosAuditImp = passwordBaseDatosAuditImp;
	}

	public String getUsuarioBaseDatosSIRImp() {
		return usuarioBaseDatosSIRImp;
	}

	public void setUsuarioBaseDatosSIRImp(String usuarioBaseDatosSIRImp) {
		this.usuarioBaseDatosSIRImp = usuarioBaseDatosSIRImp;
	}

	public String getPasswordBaseDatosSIRImp() {
		return passwordBaseDatosSIRImp;
	}

	public void setPasswordBaseDatosSIRImp(String passwordBaseDatosSIRImp) {
		this.passwordBaseDatosSIRImp = passwordBaseDatosSIRImp;
	}

	private final static long serialVersionUID = 0;
}