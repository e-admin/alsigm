/**
 * Notificacion.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.nt.ws.client;

public class Notificacion  extends ieci.tecdoc.sgm.nt.ws.client.RetornoServicio  implements java.io.Serializable {
    private java.lang.String apellidosDest;

    private java.lang.String asunto;

    private java.lang.String codigoNoti;

    private java.lang.String codigoPostal;

    private java.lang.String correoDest;

    private java.lang.String descripcionEstado;

    private java.lang.String[] documentos;

    private java.lang.String escaleraDireccion;

    private java.lang.String estado;

    private java.lang.String[] extension;

    private java.lang.String fechaActualiEstado;

    private java.lang.String fechaEntrega;

    private java.lang.String fechaRegistro;

    private java.lang.String[] guid;

    private java.lang.String idioma;

    private java.lang.String municipio;

    private java.lang.String nifDest;

    private java.lang.String nombreDest;

    private java.lang.String[] nombreDocumentos;

    private java.lang.String numeroDireccion;

    private java.lang.String numeroExpediente;

    private java.lang.String numeroRegistro;

    private java.lang.String organismo;

    private java.lang.String pisoDireccion;

    private java.lang.String procedimiento;

    private java.lang.String provincia;

    private java.lang.String puertaDireccion;

    private java.lang.String telefono;

    private java.lang.String texto;

    private java.lang.String tipo;

    private java.lang.String tipoCorrespondencia;

    private java.lang.String tipoViaDireccion;

    private java.lang.String usuario;

    private java.lang.String viaDireccion;
    
    private java.lang.String notiId;
    
    private java.lang.String deu;
    
    private java.lang.String movil;
    
    private java.lang.String sistemaId;

    public Notificacion() {
    }

    public Notificacion(
           java.lang.String errorCode,
           java.lang.String returnCode,
           java.lang.String apellidosDest,
           java.lang.String asunto,
           java.lang.String codigoNoti,
           java.lang.String codigoPostal,
           java.lang.String correoDest,
           java.lang.String descripcionEstado,
           java.lang.String[] documentos,
           java.lang.String escaleraDireccion,
           java.lang.String estado,
           java.lang.String[] extension,
           java.lang.String fechaActualiEstado,
           java.lang.String fechaEntrega,
           java.lang.String fechaRegistro,
           java.lang.String[] guid,
           java.lang.String idioma,
           java.lang.String municipio,
           java.lang.String nifDest,
           java.lang.String nombreDest,
           java.lang.String[] nombreDocumentos,
           java.lang.String numeroDireccion,
           java.lang.String numeroExpediente,
           java.lang.String numeroRegistro,
           java.lang.String organismo,
           java.lang.String pisoDireccion,
           java.lang.String procedimiento,
           java.lang.String provincia,
           java.lang.String puertaDireccion,
           java.lang.String telefono,
           java.lang.String texto,
           java.lang.String tipo,
           java.lang.String tipoCorrespondencia,
           java.lang.String tipoViaDireccion,
           java.lang.String usuario,
           java.lang.String viaDireccion,
           java.lang.String notiId,
           java.lang.String deu,
           java.lang.String movil,
           java.lang.String sistemaId) {
        super(
            errorCode,
            returnCode);
        this.apellidosDest = apellidosDest;
        this.asunto = asunto;
        this.codigoNoti = codigoNoti;
        this.codigoPostal = codigoPostal;
        this.correoDest = correoDest;
        this.descripcionEstado = descripcionEstado;
        this.documentos = documentos;
        this.escaleraDireccion = escaleraDireccion;
        this.estado = estado;
        this.extension = extension;
        this.fechaActualiEstado = fechaActualiEstado;
        this.fechaEntrega = fechaEntrega;
        this.fechaRegistro = fechaRegistro;
        this.guid = guid;
        this.idioma = idioma;
        this.municipio = municipio;
        this.nifDest = nifDest;
        this.nombreDest = nombreDest;
        this.nombreDocumentos = nombreDocumentos;
        this.numeroDireccion = numeroDireccion;
        this.numeroExpediente = numeroExpediente;
        this.numeroRegistro = numeroRegistro;
        this.organismo = organismo;
        this.pisoDireccion = pisoDireccion;
        this.procedimiento = procedimiento;
        this.provincia = provincia;
        this.puertaDireccion = puertaDireccion;
        this.telefono = telefono;
        this.texto = texto;
        this.tipo = tipo;
        this.tipoCorrespondencia = tipoCorrespondencia;
        this.tipoViaDireccion = tipoViaDireccion;
        this.usuario = usuario;
        this.viaDireccion = viaDireccion;
        this.notiId = notiId;
        this.deu=deu;
        this.movil=movil;
        this.sistemaId=sistemaId;
    }


    /**
     * Gets the apellidosDest value for this Notificacion.
     * 
     * @return apellidosDest
     */
    public java.lang.String getApellidosDest() {
        return apellidosDest;
    }


    /**
     * Sets the apellidosDest value for this Notificacion.
     * 
     * @param apellidosDest
     */
    public void setApellidosDest(java.lang.String apellidosDest) {
        this.apellidosDest = apellidosDest;
    }


    /**
     * Gets the asunto value for this Notificacion.
     * 
     * @return asunto
     */
    public java.lang.String getAsunto() {
        return asunto;
    }


    /**
     * Sets the asunto value for this Notificacion.
     * 
     * @param asunto
     */
    public void setAsunto(java.lang.String asunto) {
        this.asunto = asunto;
    }


    /**
     * Gets the codigoNoti value for this Notificacion.
     * 
     * @return codigoNoti
     */
    public java.lang.String getCodigoNoti() {
        return codigoNoti;
    }


    /**
     * Sets the codigoNoti value for this Notificacion.
     * 
     * @param codigoNoti
     */
    public void setCodigoNoti(java.lang.String codigoNoti) {
        this.codigoNoti = codigoNoti;
    }


    /**
     * Gets the codigoPostal value for this Notificacion.
     * 
     * @return codigoPostal
     */
    public java.lang.String getCodigoPostal() {
        return codigoPostal;
    }


    /**
     * Sets the codigoPostal value for this Notificacion.
     * 
     * @param codigoPostal
     */
    public void setCodigoPostal(java.lang.String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }


    /**
     * Gets the correoDest value for this Notificacion.
     * 
     * @return correoDest
     */
    public java.lang.String getCorreoDest() {
        return correoDest;
    }


    /**
     * Sets the correoDest value for this Notificacion.
     * 
     * @param correoDest
     */
    public void setCorreoDest(java.lang.String correoDest) {
        this.correoDest = correoDest;
    }


    /**
     * Gets the descripcionEstado value for this Notificacion.
     * 
     * @return descripcionEstado
     */
    public java.lang.String getDescripcionEstado() {
        return descripcionEstado;
    }


    /**
     * Sets the descripcionEstado value for this Notificacion.
     * 
     * @param descripcionEstado
     */
    public void setDescripcionEstado(java.lang.String descripcionEstado) {
        this.descripcionEstado = descripcionEstado;
    }


    /**
     * Gets the documentos value for this Notificacion.
     * 
     * @return documentos
     */
    public java.lang.String[] getDocumentos() {
        return documentos;
    }


    /**
     * Sets the documentos value for this Notificacion.
     * 
     * @param documentos
     */
    public void setDocumentos(java.lang.String[] documentos) {
        this.documentos = documentos;
    }


    /**
     * Gets the escaleraDireccion value for this Notificacion.
     * 
     * @return escaleraDireccion
     */
    public java.lang.String getEscaleraDireccion() {
        return escaleraDireccion;
    }


    /**
     * Sets the escaleraDireccion value for this Notificacion.
     * 
     * @param escaleraDireccion
     */
    public void setEscaleraDireccion(java.lang.String escaleraDireccion) {
        this.escaleraDireccion = escaleraDireccion;
    }


    /**
     * Gets the estado value for this Notificacion.
     * 
     * @return estado
     */
    public java.lang.String getEstado() {
        return estado;
    }


    /**
     * Sets the estado value for this Notificacion.
     * 
     * @param estado
     */
    public void setEstado(java.lang.String estado) {
        this.estado = estado;
    }


    /**
     * Gets the extension value for this Notificacion.
     * 
     * @return extension
     */
    public java.lang.String[] getExtension() {
        return extension;
    }


    /**
     * Sets the extension value for this Notificacion.
     * 
     * @param extension
     */
    public void setExtension(java.lang.String[] extension) {
        this.extension = extension;
    }


    /**
     * Gets the fechaActualiEstado value for this Notificacion.
     * 
     * @return fechaActualiEstado
     */
    public java.lang.String getFechaActualiEstado() {
        return fechaActualiEstado;
    }


    /**
     * Sets the fechaActualiEstado value for this Notificacion.
     * 
     * @param fechaActualiEstado
     */
    public void setFechaActualiEstado(java.lang.String fechaActualiEstado) {
        this.fechaActualiEstado = fechaActualiEstado;
    }


    /**
     * Gets the fechaEntrega value for this Notificacion.
     * 
     * @return fechaEntrega
     */
    public java.lang.String getFechaEntrega() {
        return fechaEntrega;
    }


    /**
     * Sets the fechaEntrega value for this Notificacion.
     * 
     * @param fechaEntrega
     */
    public void setFechaEntrega(java.lang.String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }


    /**
     * Gets the fechaRegistro value for this Notificacion.
     * 
     * @return fechaRegistro
     */
    public java.lang.String getFechaRegistro() {
        return fechaRegistro;
    }


    /**
     * Sets the fechaRegistro value for this Notificacion.
     * 
     * @param fechaRegistro
     */
    public void setFechaRegistro(java.lang.String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }


    /**
     * Gets the guid value for this Notificacion.
     * 
     * @return guid
     */
    public java.lang.String[] getGuid() {
        return guid;
    }


    /**
     * Sets the guid value for this Notificacion.
     * 
     * @param guid
     */
    public void setGuid(java.lang.String[] guid) {
        this.guid = guid;
    }


    /**
     * Gets the idioma value for this Notificacion.
     * 
     * @return idioma
     */
    public java.lang.String getIdioma() {
        return idioma;
    }


    /**
     * Sets the idioma value for this Notificacion.
     * 
     * @param idioma
     */
    public void setIdioma(java.lang.String idioma) {
        this.idioma = idioma;
    }


    /**
     * Gets the municipio value for this Notificacion.
     * 
     * @return municipio
     */
    public java.lang.String getMunicipio() {
        return municipio;
    }


    /**
     * Sets the municipio value for this Notificacion.
     * 
     * @param municipio
     */
    public void setMunicipio(java.lang.String municipio) {
        this.municipio = municipio;
    }


    /**
     * Gets the nifDest value for this Notificacion.
     * 
     * @return nifDest
     */
    public java.lang.String getNifDest() {
        return nifDest;
    }


    /**
     * Sets the nifDest value for this Notificacion.
     * 
     * @param nifDest
     */
    public void setNifDest(java.lang.String nifDest) {
        this.nifDest = nifDest;
    }


    /**
     * Gets the nombreDest value for this Notificacion.
     * 
     * @return nombreDest
     */
    public java.lang.String getNombreDest() {
        return nombreDest;
    }


    /**
     * Sets the nombreDest value for this Notificacion.
     * 
     * @param nombreDest
     */
    public void setNombreDest(java.lang.String nombreDest) {
        this.nombreDest = nombreDest;
    }


    /**
     * Gets the nombreDocumentos value for this Notificacion.
     * 
     * @return nombreDocumentos
     */
    public java.lang.String[] getNombreDocumentos() {
        return nombreDocumentos;
    }


    /**
     * Sets the nombreDocumentos value for this Notificacion.
     * 
     * @param nombreDocumentos
     */
    public void setNombreDocumentos(java.lang.String[] nombreDocumentos) {
        this.nombreDocumentos = nombreDocumentos;
    }


    /**
     * Gets the numeroDireccion value for this Notificacion.
     * 
     * @return numeroDireccion
     */
    public java.lang.String getNumeroDireccion() {
        return numeroDireccion;
    }


    /**
     * Sets the numeroDireccion value for this Notificacion.
     * 
     * @param numeroDireccion
     */
    public void setNumeroDireccion(java.lang.String numeroDireccion) {
        this.numeroDireccion = numeroDireccion;
    }


    /**
     * Gets the numeroExpediente value for this Notificacion.
     * 
     * @return numeroExpediente
     */
    public java.lang.String getNumeroExpediente() {
        return numeroExpediente;
    }


    /**
     * Sets the numeroExpediente value for this Notificacion.
     * 
     * @param numeroExpediente
     */
    public void setNumeroExpediente(java.lang.String numeroExpediente) {
        this.numeroExpediente = numeroExpediente;
    }


    /**
     * Gets the numeroRegistro value for this Notificacion.
     * 
     * @return numeroRegistro
     */
    public java.lang.String getNumeroRegistro() {
        return numeroRegistro;
    }


    /**
     * Sets the numeroRegistro value for this Notificacion.
     * 
     * @param numeroRegistro
     */
    public void setNumeroRegistro(java.lang.String numeroRegistro) {
        this.numeroRegistro = numeroRegistro;
    }


    /**
     * Gets the organismo value for this Notificacion.
     * 
     * @return organismo
     */
    public java.lang.String getOrganismo() {
        return organismo;
    }


    /**
     * Sets the organismo value for this Notificacion.
     * 
     * @param organismo
     */
    public void setOrganismo(java.lang.String organismo) {
        this.organismo = organismo;
    }


    /**
     * Gets the pisoDireccion value for this Notificacion.
     * 
     * @return pisoDireccion
     */
    public java.lang.String getPisoDireccion() {
        return pisoDireccion;
    }


    /**
     * Sets the pisoDireccion value for this Notificacion.
     * 
     * @param pisoDireccion
     */
    public void setPisoDireccion(java.lang.String pisoDireccion) {
        this.pisoDireccion = pisoDireccion;
    }


    /**
     * Gets the procedimiento value for this Notificacion.
     * 
     * @return procedimiento
     */
    public java.lang.String getProcedimiento() {
        return procedimiento;
    }


    /**
     * Sets the procedimiento value for this Notificacion.
     * 
     * @param procedimiento
     */
    public void setProcedimiento(java.lang.String procedimiento) {
        this.procedimiento = procedimiento;
    }


    /**
     * Gets the provincia value for this Notificacion.
     * 
     * @return provincia
     */
    public java.lang.String getProvincia() {
        return provincia;
    }


    /**
     * Sets the provincia value for this Notificacion.
     * 
     * @param provincia
     */
    public void setProvincia(java.lang.String provincia) {
        this.provincia = provincia;
    }


    /**
     * Gets the puertaDireccion value for this Notificacion.
     * 
     * @return puertaDireccion
     */
    public java.lang.String getPuertaDireccion() {
        return puertaDireccion;
    }


    /**
     * Sets the puertaDireccion value for this Notificacion.
     * 
     * @param puertaDireccion
     */
    public void setPuertaDireccion(java.lang.String puertaDireccion) {
        this.puertaDireccion = puertaDireccion;
    }


    /**
     * Gets the telefono value for this Notificacion.
     * 
     * @return telefono
     */
    public java.lang.String getTelefono() {
        return telefono;
    }


    /**
     * Sets the telefono value for this Notificacion.
     * 
     * @param telefono
     */
    public void setTelefono(java.lang.String telefono) {
        this.telefono = telefono;
    }


    /**
     * Gets the texto value for this Notificacion.
     * 
     * @return texto
     */
    public java.lang.String getTexto() {
        return texto;
    }


    /**
     * Sets the texto value for this Notificacion.
     * 
     * @param texto
     */
    public void setTexto(java.lang.String texto) {
        this.texto = texto;
    }


    /**
     * Gets the tipo value for this Notificacion.
     * 
     * @return tipo
     */
    public java.lang.String getTipo() {
        return tipo;
    }


    /**
     * Sets the tipo value for this Notificacion.
     * 
     * @param tipo
     */
    public void setTipo(java.lang.String tipo) {
        this.tipo = tipo;
    }


    /**
     * Gets the tipoCorrespondencia value for this Notificacion.
     * 
     * @return tipoCorrespondencia
     */
    public java.lang.String getTipoCorrespondencia() {
        return tipoCorrespondencia;
    }


    /**
     * Sets the tipoCorrespondencia value for this Notificacion.
     * 
     * @param tipoCorrespondencia
     */
    public void setTipoCorrespondencia(java.lang.String tipoCorrespondencia) {
        this.tipoCorrespondencia = tipoCorrespondencia;
    }


    /**
     * Gets the tipoViaDireccion value for this Notificacion.
     * 
     * @return tipoViaDireccion
     */
    public java.lang.String getTipoViaDireccion() {
        return tipoViaDireccion;
    }


    /**
     * Sets the tipoViaDireccion value for this Notificacion.
     * 
     * @param tipoViaDireccion
     */
    public void setTipoViaDireccion(java.lang.String tipoViaDireccion) {
        this.tipoViaDireccion = tipoViaDireccion;
    }


    /**
     * Gets the usuario value for this Notificacion.
     * 
     * @return usuario
     */
    public java.lang.String getUsuario() {
        return usuario;
    }


    /**
     * Sets the usuario value for this Notificacion.
     * 
     * @param usuario
     */
    public void setUsuario(java.lang.String usuario) {
        this.usuario = usuario;
    }


    /**
     * Gets the viaDireccion value for this Notificacion.
     * 
     * @return viaDireccion
     */
    public java.lang.String getViaDireccion() {
        return viaDireccion;
    }


    /**
     * Sets the viaDireccion value for this Notificacion.
     * 
     * @param viaDireccion
     */
    public void setViaDireccion(java.lang.String viaDireccion) {
        this.viaDireccion = viaDireccion;
    }
    
    public java.lang.String getNotiId() {
        return notiId;
    }
    
    public void setNotiId(java.lang.String notiId) {
        this.notiId = notiId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Notificacion)) return false;
        Notificacion other = (Notificacion) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.apellidosDest==null && other.getApellidosDest()==null) || 
             (this.apellidosDest!=null &&
              this.apellidosDest.equals(other.getApellidosDest()))) &&
            ((this.asunto==null && other.getAsunto()==null) || 
             (this.asunto!=null &&
              this.asunto.equals(other.getAsunto()))) &&
            ((this.codigoNoti==null && other.getCodigoNoti()==null) || 
             (this.codigoNoti!=null &&
              this.codigoNoti.equals(other.getCodigoNoti()))) &&
            ((this.codigoPostal==null && other.getCodigoPostal()==null) || 
             (this.codigoPostal!=null &&
              this.codigoPostal.equals(other.getCodigoPostal()))) &&
            ((this.correoDest==null && other.getCorreoDest()==null) || 
             (this.correoDest!=null &&
              this.correoDest.equals(other.getCorreoDest()))) &&
            ((this.descripcionEstado==null && other.getDescripcionEstado()==null) || 
             (this.descripcionEstado!=null &&
              this.descripcionEstado.equals(other.getDescripcionEstado()))) &&
            ((this.documentos==null && other.getDocumentos()==null) || 
             (this.documentos!=null &&
              java.util.Arrays.equals(this.documentos, other.getDocumentos()))) &&
            ((this.escaleraDireccion==null && other.getEscaleraDireccion()==null) || 
             (this.escaleraDireccion!=null &&
              this.escaleraDireccion.equals(other.getEscaleraDireccion()))) &&
            ((this.estado==null && other.getEstado()==null) || 
             (this.estado!=null &&
              this.estado.equals(other.getEstado()))) &&
            ((this.extension==null && other.getExtension()==null) || 
             (this.extension!=null &&
              java.util.Arrays.equals(this.extension, other.getExtension()))) &&
            ((this.fechaActualiEstado==null && other.getFechaActualiEstado()==null) || 
             (this.fechaActualiEstado!=null &&
              this.fechaActualiEstado.equals(other.getFechaActualiEstado()))) &&
            ((this.fechaEntrega==null && other.getFechaEntrega()==null) || 
             (this.fechaEntrega!=null &&
              this.fechaEntrega.equals(other.getFechaEntrega()))) &&
            ((this.fechaRegistro==null && other.getFechaRegistro()==null) || 
             (this.fechaRegistro!=null &&
              this.fechaRegistro.equals(other.getFechaRegistro()))) &&
            ((this.guid==null && other.getGuid()==null) || 
             (this.guid!=null &&
              java.util.Arrays.equals(this.guid, other.getGuid()))) &&
            ((this.idioma==null && other.getIdioma()==null) || 
             (this.idioma!=null &&
              this.idioma.equals(other.getIdioma()))) &&
            ((this.municipio==null && other.getMunicipio()==null) || 
             (this.municipio!=null &&
              this.municipio.equals(other.getMunicipio()))) &&
            ((this.nifDest==null && other.getNifDest()==null) || 
             (this.nifDest!=null &&
              this.nifDest.equals(other.getNifDest()))) &&
            ((this.nombreDest==null && other.getNombreDest()==null) || 
             (this.nombreDest!=null &&
              this.nombreDest.equals(other.getNombreDest()))) &&
            ((this.nombreDocumentos==null && other.getNombreDocumentos()==null) || 
             (this.nombreDocumentos!=null &&
              java.util.Arrays.equals(this.nombreDocumentos, other.getNombreDocumentos()))) &&
            ((this.numeroDireccion==null && other.getNumeroDireccion()==null) || 
             (this.numeroDireccion!=null &&
              this.numeroDireccion.equals(other.getNumeroDireccion()))) &&
            ((this.numeroExpediente==null && other.getNumeroExpediente()==null) || 
             (this.numeroExpediente!=null &&
              this.numeroExpediente.equals(other.getNumeroExpediente()))) &&
            ((this.numeroRegistro==null && other.getNumeroRegistro()==null) || 
             (this.numeroRegistro!=null &&
              this.numeroRegistro.equals(other.getNumeroRegistro()))) &&
            ((this.organismo==null && other.getOrganismo()==null) || 
             (this.organismo!=null &&
              this.organismo.equals(other.getOrganismo()))) &&
            ((this.pisoDireccion==null && other.getPisoDireccion()==null) || 
             (this.pisoDireccion!=null &&
              this.pisoDireccion.equals(other.getPisoDireccion()))) &&
            ((this.procedimiento==null && other.getProcedimiento()==null) || 
             (this.procedimiento!=null &&
              this.procedimiento.equals(other.getProcedimiento()))) &&
            ((this.provincia==null && other.getProvincia()==null) || 
             (this.provincia!=null &&
              this.provincia.equals(other.getProvincia()))) &&
            ((this.puertaDireccion==null && other.getPuertaDireccion()==null) || 
             (this.puertaDireccion!=null &&
              this.puertaDireccion.equals(other.getPuertaDireccion()))) &&
            ((this.telefono==null && other.getTelefono()==null) || 
             (this.telefono!=null &&
              this.telefono.equals(other.getTelefono()))) &&
            ((this.texto==null && other.getTexto()==null) || 
             (this.texto!=null &&
              this.texto.equals(other.getTexto()))) &&
            ((this.tipo==null && other.getTipo()==null) || 
             (this.tipo!=null &&
              this.tipo.equals(other.getTipo()))) &&
            ((this.tipoCorrespondencia==null && other.getTipoCorrespondencia()==null) || 
             (this.tipoCorrespondencia!=null &&
              this.tipoCorrespondencia.equals(other.getTipoCorrespondencia()))) &&
            ((this.tipoViaDireccion==null && other.getTipoViaDireccion()==null) || 
             (this.tipoViaDireccion!=null &&
              this.tipoViaDireccion.equals(other.getTipoViaDireccion()))) &&
            ((this.usuario==null && other.getUsuario()==null) || 
             (this.usuario!=null &&
              this.usuario.equals(other.getUsuario()))) &&
            ((this.viaDireccion==null && other.getViaDireccion()==null) || 
             (this.viaDireccion!=null &&
              this.viaDireccion.equals(other.getViaDireccion()))) &&
            ((this.notiId==null && other.getNotiId()==null) || 
             (this.notiId!=null &&
              this.notiId.equals(other.getNotiId()))) &&
            ((this.deu==null && other.getDeu()==null) || 
             (this.deu!=null &&
              this.deu.equals(other.getDeu()))) &&
            ((this.movil==null && other.getMovil()==null) || 
             (this.movil!=null &&
              this.movil.equals(other.getMovil()))) &&
            ((this.sistemaId==null && other.getSistemaId()==null) || 
             (this.sistemaId!=null &&
              this.sistemaId.equals(other.getSistemaId())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getApellidosDest() != null) {
            _hashCode += getApellidosDest().hashCode();
        }
        if (getAsunto() != null) {
            _hashCode += getAsunto().hashCode();
        }
        if (getCodigoNoti() != null) {
            _hashCode += getCodigoNoti().hashCode();
        }
        if (getCodigoPostal() != null) {
            _hashCode += getCodigoPostal().hashCode();
        }
        if (getCorreoDest() != null) {
            _hashCode += getCorreoDest().hashCode();
        }
        if (getDescripcionEstado() != null) {
            _hashCode += getDescripcionEstado().hashCode();
        }
        if (getDocumentos() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDocumentos());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDocumentos(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getEscaleraDireccion() != null) {
            _hashCode += getEscaleraDireccion().hashCode();
        }
        if (getEstado() != null) {
            _hashCode += getEstado().hashCode();
        }
        if (getExtension() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getExtension());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getExtension(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getFechaActualiEstado() != null) {
            _hashCode += getFechaActualiEstado().hashCode();
        }
        if (getFechaEntrega() != null) {
            _hashCode += getFechaEntrega().hashCode();
        }
        if (getFechaRegistro() != null) {
            _hashCode += getFechaRegistro().hashCode();
        }
        if (getGuid() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getGuid());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getGuid(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getIdioma() != null) {
            _hashCode += getIdioma().hashCode();
        }
        if (getMunicipio() != null) {
            _hashCode += getMunicipio().hashCode();
        }
        if (getNifDest() != null) {
            _hashCode += getNifDest().hashCode();
        }
        if (getNombreDest() != null) {
            _hashCode += getNombreDest().hashCode();
        }
        if (getNombreDocumentos() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getNombreDocumentos());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getNombreDocumentos(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getNumeroDireccion() != null) {
            _hashCode += getNumeroDireccion().hashCode();
        }
        if (getNumeroExpediente() != null) {
            _hashCode += getNumeroExpediente().hashCode();
        }
        if (getNumeroRegistro() != null) {
            _hashCode += getNumeroRegistro().hashCode();
        }
        if (getOrganismo() != null) {
            _hashCode += getOrganismo().hashCode();
        }
        if (getPisoDireccion() != null) {
            _hashCode += getPisoDireccion().hashCode();
        }
        if (getProcedimiento() != null) {
            _hashCode += getProcedimiento().hashCode();
        }
        if (getProvincia() != null) {
            _hashCode += getProvincia().hashCode();
        }
        if (getPuertaDireccion() != null) {
            _hashCode += getPuertaDireccion().hashCode();
        }
        if (getTelefono() != null) {
            _hashCode += getTelefono().hashCode();
        }
        if (getTexto() != null) {
            _hashCode += getTexto().hashCode();
        }
        if (getTipo() != null) {
            _hashCode += getTipo().hashCode();
        }
        if (getTipoCorrespondencia() != null) {
            _hashCode += getTipoCorrespondencia().hashCode();
        }
        if (getTipoViaDireccion() != null) {
            _hashCode += getTipoViaDireccion().hashCode();
        }
        if (getUsuario() != null) {
            _hashCode += getUsuario().hashCode();
        }
        if (getViaDireccion() != null) {
            _hashCode += getViaDireccion().hashCode();
        }
        if (getNotiId() != null) {        	
            _hashCode += getNotiId().hashCode();
        }
        if (getDeu() != null) {
            _hashCode += getDeu().hashCode();
        }
        if (getMovil() != null) {
            _hashCode += getMovil().hashCode();
        }
        if (getSistemaId() != null) {
            _hashCode += getSistemaId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Notificacion.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://server.ws.nt.sgm.tecdoc.ieci", "Notificacion"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("apellidosDest");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.nt.sgm.tecdoc.ieci", "apellidosDest"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("asunto");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.nt.sgm.tecdoc.ieci", "asunto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoNoti");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.nt.sgm.tecdoc.ieci", "codigoNoti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoPostal");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.nt.sgm.tecdoc.ieci", "codigoPostal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("correoDest");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.nt.sgm.tecdoc.ieci", "correoDest"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descripcionEstado");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.nt.sgm.tecdoc.ieci", "descripcionEstado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("documentos");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.nt.sgm.tecdoc.ieci", "documentos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://server.ws.nt.sgm.tecdoc.ieci", "item"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("escaleraDireccion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.nt.sgm.tecdoc.ieci", "escaleraDireccion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("estado");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.nt.sgm.tecdoc.ieci", "estado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("extension");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.nt.sgm.tecdoc.ieci", "extension"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://server.ws.nt.sgm.tecdoc.ieci", "item"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaActualiEstado");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.nt.sgm.tecdoc.ieci", "fechaActualiEstado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaEntrega");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.nt.sgm.tecdoc.ieci", "fechaEntrega"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaRegistro");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.nt.sgm.tecdoc.ieci", "fechaRegistro"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("guid");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.nt.sgm.tecdoc.ieci", "guid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://server.ws.nt.sgm.tecdoc.ieci", "item"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idioma");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.nt.sgm.tecdoc.ieci", "idioma"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("municipio");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.nt.sgm.tecdoc.ieci", "municipio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nifDest");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.nt.sgm.tecdoc.ieci", "nifDest"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nombreDest");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.nt.sgm.tecdoc.ieci", "nombreDest"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nombreDocumentos");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.nt.sgm.tecdoc.ieci", "nombreDocumentos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://server.ws.nt.sgm.tecdoc.ieci", "item"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroDireccion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.nt.sgm.tecdoc.ieci", "numeroDireccion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroExpediente");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.nt.sgm.tecdoc.ieci", "numeroExpediente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroRegistro");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.nt.sgm.tecdoc.ieci", "numeroRegistro"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("organismo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.nt.sgm.tecdoc.ieci", "organismo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pisoDireccion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.nt.sgm.tecdoc.ieci", "pisoDireccion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("procedimiento");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.nt.sgm.tecdoc.ieci", "procedimiento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("provincia");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.nt.sgm.tecdoc.ieci", "provincia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("puertaDireccion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.nt.sgm.tecdoc.ieci", "puertaDireccion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("telefono");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.nt.sgm.tecdoc.ieci", "telefono"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("texto");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.nt.sgm.tecdoc.ieci", "texto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.nt.sgm.tecdoc.ieci", "tipo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoCorrespondencia");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.nt.sgm.tecdoc.ieci", "tipoCorrespondencia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoViaDireccion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.nt.sgm.tecdoc.ieci", "tipoViaDireccion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("usuario");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.nt.sgm.tecdoc.ieci", "usuario"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("viaDireccion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.nt.sgm.tecdoc.ieci", "viaDireccion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("notiId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.nt.sgm.tecdoc.ieci", "notiId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deu");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.nt.sgm.tecdoc.ieci", "deu"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("movil");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.nt.sgm.tecdoc.ieci", "movil"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sistemaId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://server.ws.nt.sgm.tecdoc.ieci", "sistemaId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

	public java.lang.String getDeu() {
		return deu;
	}

	public void setDeu(java.lang.String deu) {
		this.deu = deu;
	}

	public java.lang.String getMovil() {
		return movil;
	}

	public void setMovil(java.lang.String movil) {
		this.movil = movil;
	}

	public java.lang.String getSistemaId() {
		return sistemaId;
	}

	public void setSistemaId(java.lang.String sistemaId) {
		this.sistemaId = sistemaId;
	}

}
