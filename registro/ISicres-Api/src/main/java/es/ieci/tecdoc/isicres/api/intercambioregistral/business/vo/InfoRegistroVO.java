package es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo;

import java.util.Date;
import java.util.List;

import es.ieci.tecdoc.fwktd.sir.core.types.TipoRegistroEnum;

public class InfoRegistroVO {

	 protected Long idLibro;
	 protected TipoRegistroEnum tipoRegistro;

	 protected Long idRegistro;
	 protected String codigoAsunto;
	 protected String descripcionAsunto;
	 protected String contactoUsuario;
	 protected Date fechaRegistro;
	 protected Date fechaRegistroInicial;
	 protected String unidadDestino;
	 protected String unidadOrigen;
	 protected String nombreUsuario;
	 protected String numeroExpediente;
	 protected String numeroRegistro;
	 protected String numeroRegistroInicial;
	 protected String numeroTransporte;
	 protected String observacionesApunte;
	 protected String resumen;
	 protected String tipoTransporte;
	 protected String codigoTransporteSIR;
	 protected Integer docFisicaRequerida;
	 protected Integer docFisicaComplementaria;
	 protected Integer sinDocFisica;

	 protected EntidadRegistralVO entidadRegistralOrigen;
	 protected UnidadTramitacionIntercambioRegistralVO unidadTramitacionDestino;
	 protected UnidadTramitacionIntercambioRegistralVO unidadTramitacionOrigen;

	 /**
	  * Comentario, expone, solicita
	  */
	 protected List<InfoRegistroCamposExtendidosVO> camposExtendidos;

	 protected List<InfoRegistroInteresadoVO> interesados;

	 protected List<InfoRegistroPageRepositoryVO> listadoDocumentos;

	public String getTipoTransporte() {
		return tipoTransporte;
	}
	public void setTipoTransporte(String tipoTransporte) {
		this.tipoTransporte = tipoTransporte;
	}
	public String getCodigoAsunto() {
		return codigoAsunto;
	}
	public void setCodigoAsunto(String codigoAsunto) {
		this.codigoAsunto = codigoAsunto;
	}
	public String getContactoUsuario() {
		return contactoUsuario;
	}
	public void setContactoUsuario(String contactoUsuario) {
		this.contactoUsuario = contactoUsuario;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public Date getFechaRegistroInicial() {
		return fechaRegistroInicial;
	}
	public void setFechaRegistroInicial(Date fechaRegistroInicial) {
		this.fechaRegistroInicial = fechaRegistroInicial;
	}
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	public String getNumeroExpediente() {
		return numeroExpediente;
	}
	public void setNumeroExpediente(String numeroExpediente) {
		this.numeroExpediente = numeroExpediente;
	}
	public String getNumeroRegistro() {
		return numeroRegistro;
	}
	public void setNumeroRegistro(String numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}
	public String getNumeroRegistroInicial() {
		return numeroRegistroInicial;
	}
	public void setNumeroRegistroInicial(String numeroRegistroInicial) {
		this.numeroRegistroInicial = numeroRegistroInicial;
	}
	public String getNumeroTransporte() {
		return numeroTransporte;
	}
	public void setNumeroTransporte(String numeroTransporte) {
		this.numeroTransporte = numeroTransporte;
	}
	public String getObservacionesApunte() {
		return observacionesApunte;
	}
	public void setObservacionesApunte(String observacionesApunte) {
		this.observacionesApunte = observacionesApunte;
	}
	public String getResumen() {
		return resumen;
	}
	public void setResumen(String resumen) {
		this.resumen = resumen;
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
	public List<InfoRegistroInteresadoVO> getInteresados() {
		return interesados;
	}
	public void setInteresados(List<InfoRegistroInteresadoVO> interesados) {
		this.interesados = interesados;
	}

	public String getUnidadDestino() {
		return unidadDestino;
	}
	public void setUnidadDestino(String unidadDestino) {
		this.unidadDestino = unidadDestino;
	}

	public String getUnidadOrigen() {
		return unidadOrigen;
	}
	public void setUnidadOrigen(String unidadOrigen) {
		this.unidadOrigen = unidadOrigen;
	}
	public List<InfoRegistroPageRepositoryVO> getListadoDocumentos() {
		return listadoDocumentos;
	}
	public void setListadoDocumentos(
			List<InfoRegistroPageRepositoryVO> listadoDocumentos) {
		this.listadoDocumentos = listadoDocumentos;
	}
	public EntidadRegistralVO getEntidadRegistralOrigen() {
		return entidadRegistralOrigen;
	}
	public void setEntidadRegistralOrigen(EntidadRegistralVO entidadRegistralOrigen) {
		this.entidadRegistralOrigen = entidadRegistralOrigen;
	}
	public UnidadTramitacionIntercambioRegistralVO getUnidadTramitacionDestino() {
		return unidadTramitacionDestino;
	}
	public void setUnidadTramitacionDestino(
			UnidadTramitacionIntercambioRegistralVO unidadTramitacionDestino) {
		this.unidadTramitacionDestino = unidadTramitacionDestino;
	}
	public Integer getDocFisicaRequerida() {
		return docFisicaRequerida;
	}
	public void setDocFisicaRequerida(Integer docFisicaRequerida) {
		this.docFisicaRequerida = docFisicaRequerida;
	}
	public Integer getDocFisicaComplementaria() {
		return docFisicaComplementaria;
	}
	public void setDocFisicaComplementaria(Integer docFisicaComplementaria) {
		this.docFisicaComplementaria = docFisicaComplementaria;
	}
	public Integer getSinDocFisica() {
		return sinDocFisica;
	}
	public void setSinDocFisica(Integer sinDocFisica) {
		this.sinDocFisica = sinDocFisica;
	}
	public List<InfoRegistroCamposExtendidosVO> getCamposExtendidos() {
		return camposExtendidos;
	}
	public void setCamposExtendidos(
			List<InfoRegistroCamposExtendidosVO> camposExtendidos) {
		this.camposExtendidos = camposExtendidos;
	}
	public String getDescripcionAsunto() {
		return descripcionAsunto;
	}
	public void setDescripcionAsunto(String descripcionAsunto) {
		this.descripcionAsunto = descripcionAsunto;
	}
	public UnidadTramitacionIntercambioRegistralVO getUnidadTramitacionOrigen() {
		return unidadTramitacionOrigen;
	}
	public void setUnidadTramitacionOrigen(
			UnidadTramitacionIntercambioRegistralVO unidadTramitacionOrigen) {
		this.unidadTramitacionOrigen = unidadTramitacionOrigen;
	}
	public String getCodigoTransporteSIR() {
		return codigoTransporteSIR;
	}
	public void setCodigoTransporteSIR(String codigoTransporteSIR) {
		this.codigoTransporteSIR = codigoTransporteSIR;
	}
	public TipoRegistroEnum getTipoRegistro() {
		return tipoRegistro;
	}
	public void setTipoRegistro(TipoRegistroEnum tipoRegistro) {
		this.tipoRegistro = tipoRegistro;
	}





}
