package es.ieci.tecdoc.isicres.api.intercambioregistral.proxy.business.vo;

import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

public class RegistroIntercambioRegistralVO {
	 protected String codigoAsunto;
	    protected String codigoEntidadRegistralDestino;
	    protected String codigoEntidadRegistralInicio;
	    protected String codigoEntidadRegistralOrigen;
	    protected String codigoUnidadTramitacionDestino;
	    protected String codigoUnidadTramitacionOrigen;
	    protected String contactoUsuario;
	    protected String descripcionEntidadRegistralDestino;
	    protected String descripcionEntidadRegistralInicio;
	    protected String descripcionEntidadRegistralOrigen;
	    protected String descripcionUnidadTramitacionDestino;
	    protected String descripcionUnidadTramitacionOrigen;
	    protected String documentacionFisica;
	    protected String expone;
	    protected XMLGregorianCalendar fechaRegistro;
	    protected XMLGregorianCalendar fechaRegistroInicial;
	    protected String indicadorPrueba;
	    protected String nombreUsuario;
	    protected String numeroExpediente;
	    protected String numeroRegistro;
	    protected String numeroRegistroInicial;
	    protected String numeroTransporte;
	    protected String observacionesApunte;
	    protected String referenciaExterna;
	    protected String resumen;
	    protected String solicita;
	    protected byte[] timestampRegistro;
	    protected byte[] timestampRegistroInicial;
	    protected String tipoRegistro;
	    protected String tipoTransporte;
	    
	    protected List<AnexoIntercambioRegistralVO> anexos;
	    
	    protected List<InteresadoIntercambioRegistralVO> interesados;

		public String getCodigoAsunto() {
			return codigoAsunto;
		}

		public void setCodigoAsunto(String codigoAsunto) {
			this.codigoAsunto = codigoAsunto;
		}

		public String getCodigoEntidadRegistralDestino() {
			return codigoEntidadRegistralDestino;
		}

		public void setCodigoEntidadRegistralDestino(
				String codigoEntidadRegistralDestino) {
			this.codigoEntidadRegistralDestino = codigoEntidadRegistralDestino;
		}

		public String getCodigoEntidadRegistralInicio() {
			return codigoEntidadRegistralInicio;
		}

		public void setCodigoEntidadRegistralInicio(String codigoEntidadRegistralInicio) {
			this.codigoEntidadRegistralInicio = codigoEntidadRegistralInicio;
		}

		public String getCodigoEntidadRegistralOrigen() {
			return codigoEntidadRegistralOrigen;
		}

		public void setCodigoEntidadRegistralOrigen(String codigoEntidadRegistralOrigen) {
			this.codigoEntidadRegistralOrigen = codigoEntidadRegistralOrigen;
		}

		public String getCodigoUnidadTramitacionDestino() {
			return codigoUnidadTramitacionDestino;
		}

		public void setCodigoUnidadTramitacionDestino(
				String codigoUnidadTramitacionDestino) {
			this.codigoUnidadTramitacionDestino = codigoUnidadTramitacionDestino;
		}

		public String getCodigoUnidadTramitacionOrigen() {
			return codigoUnidadTramitacionOrigen;
		}

		public void setCodigoUnidadTramitacionOrigen(
				String codigoUnidadTramitacionOrigen) {
			this.codigoUnidadTramitacionOrigen = codigoUnidadTramitacionOrigen;
		}

		public String getContactoUsuario() {
			return contactoUsuario;
		}

		public void setContactoUsuario(String contactoUsuario) {
			this.contactoUsuario = contactoUsuario;
		}

		public String getDescripcionEntidadRegistralDestino() {
			return descripcionEntidadRegistralDestino;
		}

		public void setDescripcionEntidadRegistralDestino(
				String descripcionEntidadRegistralDestino) {
			this.descripcionEntidadRegistralDestino = descripcionEntidadRegistralDestino;
		}

		public String getDescripcionEntidadRegistralInicio() {
			return descripcionEntidadRegistralInicio;
		}

		public void setDescripcionEntidadRegistralInicio(
				String descripcionEntidadRegistralInicio) {
			this.descripcionEntidadRegistralInicio = descripcionEntidadRegistralInicio;
		}

		public String getDescripcionEntidadRegistralOrigen() {
			return descripcionEntidadRegistralOrigen;
		}

		public void setDescripcionEntidadRegistralOrigen(
				String descripcionEntidadRegistralOrigen) {
			this.descripcionEntidadRegistralOrigen = descripcionEntidadRegistralOrigen;
		}

		public String getDescripcionUnidadTramitacionDestino() {
			return descripcionUnidadTramitacionDestino;
		}

		public void setDescripcionUnidadTramitacionDestino(
				String descripcionUnidadTramitacionDestino) {
			this.descripcionUnidadTramitacionDestino = descripcionUnidadTramitacionDestino;
		}

		public String getDescripcionUnidadTramitacionOrigen() {
			return descripcionUnidadTramitacionOrigen;
		}

		public void setDescripcionUnidadTramitacionOrigen(
				String descripcionUnidadTramitacionOrigen) {
			this.descripcionUnidadTramitacionOrigen = descripcionUnidadTramitacionOrigen;
		}

		public String getDocumentacionFisica() {
			return documentacionFisica;
		}

		public void setDocumentacionFisica(String documentacionFisica) {
			this.documentacionFisica = documentacionFisica;
		}

		public String getExpone() {
			return expone;
		}

		public void setExpone(String expone) {
			this.expone = expone;
		}

		public XMLGregorianCalendar getFechaRegistro() {
			return fechaRegistro;
		}

		public void setFechaRegistro(XMLGregorianCalendar fechaRegistro) {
			this.fechaRegistro = fechaRegistro;
		}

		public XMLGregorianCalendar getFechaRegistroInicial() {
			return fechaRegistroInicial;
		}

		public void setFechaRegistroInicial(XMLGregorianCalendar fechaRegistroInicial) {
			this.fechaRegistroInicial = fechaRegistroInicial;
		}

		public String getIndicadorPrueba() {
			return indicadorPrueba;
		}

		public void setIndicadorPrueba(String indicadorPrueba) {
			this.indicadorPrueba = indicadorPrueba;
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

		public String getReferenciaExterna() {
			return referenciaExterna;
		}

		public void setReferenciaExterna(String referenciaExterna) {
			this.referenciaExterna = referenciaExterna;
		}

		public String getResumen() {
			return resumen;
		}

		public void setResumen(String resumen) {
			this.resumen = resumen;
		}

		public String getSolicita() {
			return solicita;
		}

		public void setSolicita(String solicita) {
			this.solicita = solicita;
		}

		public byte[] getTimestampRegistro() {
			return timestampRegistro;
		}

		public void setTimestampRegistro(byte[] timestampRegistro) {
			this.timestampRegistro = timestampRegistro;
		}

		public byte[] getTimestampRegistroInicial() {
			return timestampRegistroInicial;
		}

		public void setTimestampRegistroInicial(byte[] timestampRegistroInicial) {
			this.timestampRegistroInicial = timestampRegistroInicial;
		}

		public String getTipoRegistro() {
			return tipoRegistro;
		}

		public void setTipoRegistro(String tipoRegistro) {
			this.tipoRegistro = tipoRegistro;
		}

		public String getTipoTransporte() {
			return tipoTransporte;
		}

		public void setTipoTransporte(String tipoTransporte) {
			this.tipoTransporte = tipoTransporte;
		}

		public List<AnexoIntercambioRegistralVO> getAnexos() {
			return anexos;
		}

		public void setAnexos(List<AnexoIntercambioRegistralVO> anexos) {
			this.anexos = anexos;
		}

		public List<InteresadoIntercambioRegistralVO> getInteresados() {
			return interesados;
		}

		public void setInteresados(List<InteresadoIntercambioRegistralVO> interesados) {
			this.interesados = interesados;
		}
	    

}
