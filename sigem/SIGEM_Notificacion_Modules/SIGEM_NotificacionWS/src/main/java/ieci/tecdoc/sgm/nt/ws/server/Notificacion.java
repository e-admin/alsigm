package ieci.tecdoc.sgm.nt.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class Notificacion extends RetornoServicio {

    private String estado;
    private String descripcionEstado;
    private String codigoNoti;
    private String fechaActualiEstado;
    
    private String usuario;
    private String tipoCorrespondencia;
    private String numeroRegistro;
    private String fechaRegistro;
    private String procedimiento;
    private String organismo;
    private String asunto;
    private String tipo;
    private String texto;
    private String nifDest;
    private String nombreDest;
    private String apellidosDest;
    private String correoDest;
    private String numeroExpediente;
    private String idioma;
    private String tipoViaDireccion;
    private String viaDireccion;
    private String numeroDireccion;
    private String escaleraDireccion;
    private String pisoDireccion;
    private String puertaDireccion;
    private String telefono;
    private String municipio;
    private String provincia;
    private String codigoPostal;
    private String notiId;
    private String movil;
    private String deu;
    private String sistemaId;
    
    
    private String[] documentos;
    private String[] nombreDocumentos;
    private String[] extension;
    private String[] guid;
    
    private String fechaEntrega;
    
	public String getFechaEntrega() {
		return fechaEntrega;
	}
	public void setFechaEntrega(String fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}
	public String getApellidosDest() {
		return apellidosDest;
	}
	public void setApellidosDest(String apellidosDest) {
		this.apellidosDest = apellidosDest;
	}
	public String getAsunto() {
		return asunto;
	}
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	public String getCodigoNoti() {
		return codigoNoti;
	}
	public void setCodigoNoti(String codigoNoti) {
		this.codigoNoti = codigoNoti;
	}
	public String getCodigoPostal() {
		return codigoPostal;
	}
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	public String getCorreoDest() {
		return correoDest;
	}
	public void setCorreoDest(String correoDest) {
		this.correoDest = correoDest;
	}
	public String[] getDocumentos() {
		return documentos;
	}
	public void setDocumentos(String[] documentos) {
		this.documentos = documentos;
	}
	public String getEscaleraDireccion() {
		return escaleraDireccion;
	}
	public void setEscaleraDireccion(String escaleraDireccion) {
		this.escaleraDireccion = escaleraDireccion;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getDescripcionEstado() {
		return descripcionEstado;
	}
	public void setDescripcionEstado(String descripcionEstado) {
		this.descripcionEstado = descripcionEstado;
	}
	public String[] getExtension() {
		return extension;
	}
	public void setExtension(String[] extension) {
		this.extension = extension;
	}
	public String getFechaActualiEstado() {
		return fechaActualiEstado;
	}
	public void setFechaActualiEstado(String fechaActualiEstado) {
		this.fechaActualiEstado = fechaActualiEstado;
	}
	public String getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(String fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public String[] getGuid() {
		return guid;
	}
	public void setGuid(String[] guid) {
		this.guid = guid;
	}
	public String getIdioma() {
		return idioma;
	}
	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}
	public String getMunicipio() {
		return municipio;
	}
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	public String getNifDest() {
		return nifDest;
	}
	public void setNifDest(String nifDest) {
		this.nifDest = nifDest;
	}
	public String getNombreDest() {
		return nombreDest;
	}
	public void setNombreDest(String nombreDest) {
		this.nombreDest = nombreDest;
	}
	public String[] getNombreDocumentos() {
		return nombreDocumentos;
	}
	public void setNombreDocumentos(String[] nombreDocumentos) {
		this.nombreDocumentos = nombreDocumentos;
	}
	public String getNumeroDireccion() {
		return numeroDireccion;
	}
	public void setNumeroDireccion(String numeroDireccion) {
		this.numeroDireccion = numeroDireccion;
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
	public String getOrganismo() {
		return organismo;
	}
	public void setOrganismo(String organismo) {
		this.organismo = organismo;
	}
	public String getPisoDireccion() {
		return pisoDireccion;
	}
	public void setPisoDireccion(String pisoDireccion) {
		this.pisoDireccion = pisoDireccion;
	}
	public String getProcedimiento() {
		return procedimiento;
	}
	public void setProcedimiento(String procedimiento) {
		this.procedimiento = procedimiento;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getPuertaDireccion() {
		return puertaDireccion;
	}
	public void setPuertaDireccion(String puertaDireccion) {
		this.puertaDireccion = puertaDireccion;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getTipoCorrespondencia() {
		return tipoCorrespondencia;
	}
	public void setTipoCorrespondencia(String tipoCorrespondencia) {
		this.tipoCorrespondencia = tipoCorrespondencia;
	}
	public String getTipoViaDireccion() {
		return tipoViaDireccion;
	}
	public void setTipoViaDireccion(String tipoViaDireccion) {
		this.tipoViaDireccion = tipoViaDireccion;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getViaDireccion() {
		return viaDireccion;
	}
	public void setViaDireccion(String viaDireccion) {
		this.viaDireccion = viaDireccion;
	}
	public String getNotiId() {
		return notiId;
	}
	public void setNotiId(String notiId) {
		this.notiId = notiId;
	}
	public String getMovil() {
		return movil;
	}
	public void setMovil(String movil) {
		this.movil = movil;
	}
	public String getDeu() {
		return deu;
	}
	public void setDeu(String deu) {
		this.deu = deu;
	}
	public String getSistemaId() {
		return sistemaId;
	}
	public void setSistemaId(String sistemaId) {
		this.sistemaId = sistemaId;
	}
    
}
