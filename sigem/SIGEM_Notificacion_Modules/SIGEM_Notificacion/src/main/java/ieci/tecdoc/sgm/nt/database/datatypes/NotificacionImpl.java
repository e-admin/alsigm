/*
 * NotificacionImpl.java
 *
 * Created on 18 de mayo de 2007, 12:14
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ieci.tecdoc.sgm.nt.database.datatypes;

/**
 *
 * @author Usuario
 */
public class NotificacionImpl implements Notificacion {
	
    private String id;
    
    private String error;
    
    private String nifDestinatario;

    private String registroSalida;
    
    private java.util.Date fechaRegistroAnterior;
    
    private String numeroExpediente;           
                 
    private String procedimientoExpedienteAnterior;

    private Integer estado;
    private String descripcionEstado;
    
    private java.util.Date fechaActualizacionEstado;
 
    private java.util.Date fechaEfectuadaEntrega;
    
    private String usuario;
    
    private String tipoCorrespondencia; 
    
    private String organismo; 
    
    private String asunto; 
    
    private String tipo ;
    
    private String texto ;
    
    private String nombreDestinatario ;
    
    private String apellidosDestinatario ;
    
    private String correoDestinatario ;
    
    private String idioma ;
    
    private String tipoVia ;
    
    private String nombreVia;
    
    private String numeroVia ;
    
    private String escaleraVia ;
    
    private String pisoVia ;
    
    private String puertaVia;
    
    private String telefono ;
    
    private String municipio ;
    
    private String provincia ;
    
    private String codigoPostal;    
   
    private String notiid;
    
    private String sistemaId;
    
    private String movil;
    
    private String deu;
    
    /** Creates a new instance of NotificacionImpl */
    public NotificacionImpl() {      
    }
    
        /**
     * Recoge los valores de la instancia en una cadena xml
     * @param header Si se incluye la cabecera
     * @return los datos en formato xml
     */		
    public String toXML(boolean header){
        return null;
    }

    /**
     * Devuelve los valores de la instancia en una cadena de caracteres.
     */	
    public String toString(){
        return toXML (false);
    }

    /**
     * Devuelve el codigo de notificacion 
     * @return String codigo de notificacion
     */
    public String getId() {
        return id;
    }

    /**
     * Establece el codigo de notificacion 
     * @param id String codigo de notificaicon
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Devuelve el nif del destinatario
     * @return String nif del destinatario
     */
    public String getNifDestinatario() {
        return nifDestinatario;
    }

    /**
     * Establece el nif del destinatario
     * @param nifDestinatario String nif destinatario
     */
    public void setNifDestinatario(String nifDestinatario) {
        this.nifDestinatario = nifDestinatario;
    }

    /**
     * Devuelve el registro de salida
     * @return String registro de salida
     */
    public String getRegistroSalida() {
        return registroSalida;
    }

    /**
     * Establece el registro de salida
     * @param registroSalida String registro de salida
     */
    public void setRegistroSalida(String registroSalida) {
        this.registroSalida = registroSalida;
    }

    /**
     * Devuelve la fecha de registro 
     * @return Date fecha de registro
     */
    public java.util.Date getFechaRegistroAnterior() {
        return fechaRegistroAnterior;
    }

    /**
     * Establece la fecha de registro
     * @param fechaRegistroAnterior Date fecha de registro
     */
    public void setFechaRegistroAnterior(java.util.Date fechaRegistroAnterior) {
        this.fechaRegistroAnterior = fechaRegistroAnterior;
    }

    /**
     * DEvuelve el numero de expediente
     * @return String numero de expediente
     */
    public String getNumeroExpediente() {
        return numeroExpediente;
    }

    /**
     * Establece el numero de expediente
     * @param numeroExpediente String numero de expediente
     */
    public void setNumeroExpediente(String numeroExpediente) {
        this.numeroExpediente = numeroExpediente;
    }

    /**
     * Devuelve el valor del procedimiento
     * @return String procedimiento
     */
    public String getProcedimientoExpedienteAnterior() {
        return procedimientoExpedienteAnterior;
    }

    /**
     * Establece el valor del procedimiento
     * @param procedimientoExpedienteAnterior String procedimiento
     */
    public void setProcedimientoExpedienteAnterior(String procedimientoExpedienteAnterior) {
        this.procedimientoExpedienteAnterior = procedimientoExpedienteAnterior;
    }

    public String getDescripcionEstado() {
        return descripcionEstado;
    }

    /**
     * Establece el valor del procedimiento
     * @param procedimientoExpedienteAnterior String procedimiento
     */
    public void setDescripcionEstado(String descripcionEstado) {
        this.descripcionEstado = descripcionEstado;
    }
    
    /**
     * Devuelve el estado
     * @return Integer estado
     */
    public Integer getEstado() {
        return estado;
    }

    /**
     * Establece el valor del estado
     * @param estado Integer estado
     */
    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    /**
     * Devuelve el valor de la ultima fecha en la que actualizo el estado
     * @return Date fecha de actualizacion
     */
    public java.util.Date getFechaActualizacionEstado() {
        return fechaActualizacionEstado;
    }

    /**
     * Establece la fecha de la ultima actualizacion del estado
     * @param fechaActualizacionEstado Date de actualizacion
     */
    public void setFechaActualizacionEstado(java.util.Date fechaActualizacionEstado) {
        this.fechaActualizacionEstado = fechaActualizacionEstado;
    }

    /**
     * Devuelve la fecha de entrega de la notificacion. La fecha de alta en el sistema
     * de la notificacion 
     * @return Date fecha de entrega
     */
    public java.util.Date getFechaEfectuadaEntrega() {
        return fechaEfectuadaEntrega;
    }

    /**
     * Establece la fecha de entrega de la notificacion. La fecha de alta en el sistema
     * de la notificacion  
     * @param fechaEfectuadaEntrega Date fecha de entrega
     */
    public void setFechaEfectuadaEntrega(java.util.Date fechaEfectuadaEntrega) {
        this.fechaEfectuadaEntrega = fechaEfectuadaEntrega;
    }

    /**
     * Devuelve el usuario
     * @return String usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * Establece el usuario
     * @param usuario String usuario
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * Devuelve el tipo de correspondencia
     * @return String Tipo de correspondencia
     */
    public String getTipoCorrespondencia() {
        return tipoCorrespondencia;
    }

    /**
     * Establece el tipo de correspondencia
     * @param tipoCorrespondencia String tipo de correspondencia
     */
    public void setTipoCorrespondencia(String tipoCorrespondencia) {
        this.tipoCorrespondencia = tipoCorrespondencia;
    }

    /**
     * Devuelve el organismo / Entidad emisora
     * @return String organismo
     */
    public String getOrganismo() {
        return organismo;
    }

    /**
     * Establece el valor del organismo / entidad emisora
     * @param organismo String nuevo valor del organismo
     */
    public void setOrganismo(String organismo) {
        this.organismo = organismo;
    }

    /**
     * Devuelve el asunto
     * @return String asunto
     */
    public String getAsunto() {
        return asunto;
    }

    /**
     * Establece el valor del asunto
     * @param asunto String nuevo valor del asunto
     */
    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    /**
     * Devuelve el tipo
     * @return String tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Establece el valor del tipo
     * @param tipo String nuevo valor del tipo
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Devuelve el valor del texto
     * @return String valor del texto
     */
    public String getTexto() {
        return texto;
    }

    /**
     * Establece el valor del texto
     * @param texto String nuevo valor del texto
     */
    public void setTexto(String texto) {
        this.texto = texto;
    }

    /**
     * Devuelve el valor del nombre del destinatario
     * @return String valor del nombre del destinatario
     */
    public String getNombreDestinatario() {
        return nombreDestinatario;
    }

    /**
     * Establece el valor del destinatario
     * @param nombreDestinatario String nuevo valor del destinatario
     */
    public void setNombreDestinatario(String nombreDestinatario) {
        this.nombreDestinatario = nombreDestinatario;
    }

    /**
     * Devuelve los apellidos del detinatario
     * @return String valor de los apellidos del destinatario
     */
    public String getApellidosDestinatario() {
        return apellidosDestinatario;
    }

    /**
     * Establece el valor de los apellidos del destinatario
     * @param apellidosDestinatario String nuevo valor de los apellidos del destinatario
     */
    public void setApellidosDestinatario(String apellidosDestinatario) {
        this.apellidosDestinatario = apellidosDestinatario;
    }

    /**
     * Devuelve el valor del correo del destinatario
     * @return String correo del destinatario
     */
    public String getCorreoDestinatario() {
        return correoDestinatario;
    }

    /**
     * Establece el valor del correo del destinatario
     * @param correoDestinatario String nuevo valor del correo del destinatario
     */
    public void setCorreoDestinatario(String correoDestinatario) {
        this.correoDestinatario = correoDestinatario;
    }

    /**
     * Devuelve el valor del idioma 
     *
     * @return String idioma
     */
    public String getIdioma() {
        return idioma;
    }

    /**
     * Establece el valor del idioma
     * @param idioma String nuevo valor del idioma
     */
    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    /**
     * Devuelve el valor del tipo de via
     * @return String tipo de via
     */
    public String getTipoVia() {
        return tipoVia;
    }

    /**
     * Establece el valor del tipo de via
     * @param tipoVia String nuevo valor del tipo de via
     */
    public void setTipoVia(String tipoVia) {
        this.tipoVia = tipoVia;
    }

    /**Devuelve el nombre de la via
     * 
     * @return String nombre de la via
     */
    public String getNombreVia() {
        return nombreVia;
    }

    /**
     * Establece el nombre de la via
     * @param nombreVia String nuevo valor del tipo de via
     */
    public void setNombreVia(String nombreVia) {
        this.nombreVia = nombreVia;
    }

    /**
     * Devuelve el numero de via 
     * @return String numero de via
     */
    public String getNumeroVia() {
        return numeroVia;
    }

    /**
     * Establece el numero de la direccion
     * @param numeroVia String nuevo valor del numero de la direccion
     */
    public void setNumeroVia(String numeroVia) {
        this.numeroVia = numeroVia;
    }

    /**
     * Devuelve el valor de la escalera de la direccion 
     * @return String escalera de la direccion
     */
    public String getEscaleraVia() {
        return escaleraVia;
    }

    /**
     * Establece el valor de la escalera de la direccion
     * @param escaleraVia String nuevo valor de la escalera de la direccion
     */
    public void setEscaleraVia(String escaleraVia) {
        this.escaleraVia = escaleraVia;
    }

    /**
     * Devuelve el valor del piso de la direccionm
     * @return String piso de la direccion
     */
    public String getPisoVia() {
        return pisoVia;
    }

    /**
     * Establece el valor del piso de la direccion
     * @param pisoVia String nuevo valor del piso de la direccion
     */
    public void setPisoVia(String pisoVia) {
        this.pisoVia = pisoVia;
    }

    /**
     * Devuelve el valor de la puerta de la direccion
     * @return String puerta de la direccion
     */
    public String getPuertaVia() {
        return puertaVia;
    }

    /**
     * Establece el valor de la puerta de la via
     * @param puertaVia nuevo valor de la puerta de la via
     */
    public void setPuertaVia(String puertaVia) {
        this.puertaVia = puertaVia;
    }

    /**
     * Devuelve el valor del telefono
     * @return String telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el valor del telefono
     * @param telefono String nuevo valor del telefono
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Devuelve el valor del municipio
     * @return String municipio
     */
    public String getMunicipio() {
        return municipio;
    }

    /**
     * Establece el valor del municipio
     * @param municipio String nuevo valor del municipio
     */
    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    /**
     * Devuelve el valor de la provincia
     * @return String provincia
     */
    public String getProvincia() {
        return provincia;
    }

    /**
     * Establece el valor de la provincia
     * @param provincia String nuevo valor de la provincia
     */
    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    /**
     * Devuelve el valor del codigo postal
     * @return String codigo postal
     */
    public String getCodigoPostal() {
        return codigoPostal;
    }

    /**
     * Establece el valor del codigo postal
     * @param codigoPostal String nuevo valor del codigo postal
     */
    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    /**
     * Devuelve el valor del error que se produjo en la creacion de la notificacion
     * @return String error
     */
    public String getError() {
        return error;
    }

    /**
     * Establece el error que se produjo en la creacion de la notificacion
     * @param error String error
     */
    public void setError(String error) {
        this.error = error;
    }
    
    /**
     * 
     * @return String
     */
    public String getNotiId() {
        return notiid;
    }

    /**
     * 
     * @param  String 
     */
    public void setNotiId(String notiid) {
        this.notiid = notiid;
    }
    
    /**
     * 
     * @return String 
     */
    public String getSistemaId() {
        return sistemaId;
    }

    /**
     * 
     * @param String 
     */
    public void setSistemaId(String sistemaId) {
        this.sistemaId = sistemaId;
    }

	public String getMovil() {
		return movil;
	}

	public void setMovil(String movil) {
		this.movil = movil;
	}

	public String getDEU() {
		return deu;
	}

	public void setDEU(String deu) {
		this.deu = deu;
	}
    
    
}
