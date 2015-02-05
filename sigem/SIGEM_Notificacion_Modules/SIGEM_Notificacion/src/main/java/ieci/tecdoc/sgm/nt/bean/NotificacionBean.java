/*
 * NotificacionBean.java
 *
 * Created on 21 de mayo de 2007, 17:59
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ieci.tecdoc.sgm.nt.bean;

import ieci.tecdoc.sgm.base.xml.core.XmlDocument;
import ieci.tecdoc.sgm.nt.exception.DatosIncorrectosErrorCodigos;
import ieci.tecdoc.sgm.nt.exception.DatosIncorrectosExcepcion;

import org.w3c.dom.NodeList;
/**
 *
 * @author Usuario
 */
public final class NotificacionBean  extends GenericoBean{
    
    // variables con los datos que pueden ser necesarias para dar de alta una notificacion
    
    private Integer estado;
    private String codigoNoti;
    private java.util.Date fechaActualiEstado;
    private String descripcionEstado;
    
    private String usuario;
    private String tipoCorrespondencia;
    private String numeroRegistro;
    private java.util.Date fechaRegistro;
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
    
    private java.util.ArrayList documentos;
    private java.util.ArrayList nombreDocumentos;
    private java.util.ArrayList extension;
    private java.util.ArrayList guid;
    
    private java.util.Date fechaEntrega;
    
    // nombre de las variables anteriores que pueden venir en el xml
    // del constructor que acepta como parametro una cadena que contiene
    // un xml
    private final String NODE_ORGANISMO = "Organismo";
    private final String NODE_ASUNTO = "Asunto";
    private final String NODE_TIPO = "Tipo_Notificación";
    private final String NODE_TEXTO = "Texto_Notificación";	
    private final String NODE_NIF = "NIF";
    private final String NODE_NOMBRE = "Nombre";
    private final String NODE_APELLIDOS = "Apellidos";
    private final String NODE_CORREO = "Correo";
    private final String NODE_EXPEDIENTE = "Número_Expediente";
    private final String NODE_IDIOMA = "Idioma";
    private final String NODE_TIPO_VIA = "Tipo_Via_Pública";
    private final String NODE_NOMBRE_VIA = "Nombre_Via_Pública";
    private final String NODE_NUMERO = "Número";
    private final String NODE_ESCALERA = "Escalera";
    private final String NODE_PISO = "Piso";
    private final String NODE_PUERTA = "Puerta";
    private final String NODE_TELEFONO = "Teléfono";
    private final String NODE_MUNICIPIO = "Municipio";
    private final String NODE_PROVINCIA = "Provincia";
    private final String NODE_CP = "Código_Postal";
    private final String NODE_DOCUMENTO = "Documento";	
    private final String NODE_CODIGO_DOCUMENTO = "Código";
    private final String NODE_EXTENSION_DOCUMENTO = "Extension";
    private final String NODE_VALOR_DOCUMENTO = "Fichero";
    private final String NODE_SISTEMA_NOTIFICACION = "Sistema_Notificacion";

   
    /** Creates a new instance of NotificacionBean */
    public NotificacionBean() {
        documentos = new java.util.ArrayList();
        nombreDocumentos = new java.util.ArrayList();
        extension = new java.util.ArrayList();
        guid = new java.util.ArrayList();
    }
    /*
     * Constructor que permite inicializar todos los campos del bean a partir de un
     * xml que cumple el siguiente formato:
     *
     *<?xml version="1.0" encoding="iso-8859-1"?>
        <AltaNotificacion Tipo=”Entrada” Version="01.00">
            <Datos_Genéricos>
                <Organismo/>
                <Asunto/>
                <Tipo_Notificación/>
                <Texto_Notificación/>
                <Destinatario>
                    <NIF/>
                    <Nombre/>
                    <Apellidos/>
                    <Correo/>
                </Destinatario>
                <Número_Expediente/>
                <Idioma/>
            </Datos_Genéricos>
            <Datos_Específicos>
                <Tipo_Via_Pública/>
                <Nombre_Via_Pública/>
                <Número/>
                <Escalera/>
                <Piso/>
                <Puerta/>
                <Teléfono/>
                <Municipio/>
                <Provincia/>
                <Código_Postal/>
            </Datos_Específicos>
            <Documentos>
                <Documento>
                    <Código/>
                    <Fichero/>
                    <Extension/>
                </Documento>
            </Documentos>
        </AltaNotificacion >

     * @param String cadena que contiene el xml
     *
     */
    public NotificacionBean (String xml_) throws DatosIncorrectosExcepcion{
        XmlDocument doc = new XmlDocument();
        
        documentos = new java.util.ArrayList();
        nombreDocumentos = new java.util.ArrayList();
        extension = new java.util.ArrayList();
        
        try{
            doc.createFromUtf8Text(xml_.getBytes());
        
            // cogemos el organismo
            NodeList salida = doc.getDomDocument().getElementsByTagName(NODE_ORGANISMO);        
            setOrganismo(salida.item(0).getFirstChild().getNodeValue());
            
            // cogemos el asunto
            salida = doc.getDomDocument().getElementsByTagName(NODE_ASUNTO);        
            this.setAsunto(salida.item(0).getFirstChild().getNodeValue());
            
            // cogemos el tipo de notitificacion
            salida = doc.getDomDocument().getElementsByTagName(NODE_TIPO);        
            this.setTipo(salida.item(0).getFirstChild().getNodeValue());
            
            // cogemos el texto de la notitificacion
            salida = doc.getDomDocument().getElementsByTagName(NODE_TEXTO);        
            this.setTexto(salida.item(0).getFirstChild().getNodeValue());
   
            // cogemos el nif del destinatario
            salida = doc.getDomDocument().getElementsByTagName(NODE_NIF);        
            this.setNifDest(salida.item(0).getFirstChild().getNodeValue());
            
            // cogemos el nombre del destinatario
            salida = doc.getDomDocument().getElementsByTagName(NODE_NOMBRE);              
            if (salida.getLength() > 0 && salida.item(0).getFirstChild()!= null)
                this.setNombreDest(salida.item(0).getFirstChild().getNodeValue());
         
            // cogemos los apellidos del destinatario
            salida = doc.getDomDocument().getElementsByTagName(NODE_APELLIDOS); 
            if (salida.getLength() > 0 && salida.item(0).getFirstChild()!= null)
                this.setApellidosDest(salida.item(0).getFirstChild().getNodeValue());
            
            // cogemos el correo del destinatario
            salida = doc.getDomDocument().getElementsByTagName(NODE_CORREO);    
            if (salida.getLength() > 0 && salida.item(0).getFirstChild()!= null && salida.item(0).getFirstChild()!= null)
                this.setCorreoDest(salida.item(0).getFirstChild().getNodeValue());
    
            // cogemos el correo del destinatario
            salida = doc.getDomDocument().getElementsByTagName(NODE_EXPEDIENTE );        
            this.setNumeroExpediente(salida.item(0).getFirstChild().getNodeValue());
            
            // cogemos el idioma
            salida = doc.getDomDocument().getElementsByTagName(NODE_IDIOMA);  
            if (salida.getLength() > 0 && salida.item(0).getFirstChild()!= null)
                this.setIdioma(salida.item(0).getFirstChild().getNodeValue());
            
            // cogemos el tipo de via
            salida = doc.getDomDocument().getElementsByTagName(NODE_TIPO_VIA); 
            if (salida.getLength() > 0 && salida.item(0).getFirstChild()!= null)
                this.setTipoViaDireccion(salida.item(0).getFirstChild().getNodeValue());            

            // cogemos nombre de la via
            salida = doc.getDomDocument().getElementsByTagName(NODE_NOMBRE_VIA); 
            if (salida.getLength() > 0 && salida.item(0).getFirstChild()!= null)
                this.setViaDireccion(salida.item(0).getFirstChild().getNodeValue());        
            
            // cogemos el numero de la direccion
            salida = doc.getDomDocument().getElementsByTagName(NODE_NUMERO);
            if (salida.getLength() > 0 && salida.item(0).getFirstChild()!= null)
                this.setNumeroDireccion(salida.item(0).getFirstChild().getNodeValue());   
            
            // cogemos la escalera de la direccion
            salida = doc.getDomDocument().getElementsByTagName(NODE_ESCALERA); 
            if (salida.getLength() > 0 && salida.item(0).getFirstChild()!= null)
                this.setEscaleraDireccion(salida.item(0).getFirstChild().getNodeValue());   
 
            // cogemos el piso de la direccion
            salida = doc.getDomDocument().getElementsByTagName(NODE_PISO);  
            if (salida.getLength() > 0 && salida.item(0).getFirstChild()!= null)
                this.setPisoDireccion(salida.item(0).getFirstChild().getNodeValue());   
 
            // cogemos la puestra de la direccion
            salida = doc.getDomDocument().getElementsByTagName(NODE_PUERTA);   
            if (salida.getLength() > 0 && salida.item(0).getFirstChild()!= null)
                this.setPuertaDireccion(salida.item(0).getFirstChild().getNodeValue());   
  
            // cogemos el telefono
            salida = doc.getDomDocument().getElementsByTagName(NODE_TELEFONO);   
            if (salida.getLength() > 0 && salida.item(0).getFirstChild()!= null)
                this.setTelefono(salida.item(0).getFirstChild().getNodeValue());
            
            // cogemos el telefono
            salida = doc.getDomDocument().getElementsByTagName(NODE_MUNICIPIO);  
            if (salida.getLength() > 0 && salida.item(0).getFirstChild()!= null)
                this.setMunicipio(salida.item(0).getFirstChild().getNodeValue());

            // cogemos el telefono
            salida = doc.getDomDocument().getElementsByTagName(NODE_PROVINCIA);        
            if (salida.getLength() > 0 && salida.item(0).getFirstChild()!= null)
                this.setProvincia(salida.item(0).getFirstChild().getNodeValue());
            
            // cogemos el coidog postal
            salida = doc.getDomDocument().getElementsByTagName(NODE_CP); 
            if (salida.getLength() > 0 && salida.item(0).getFirstChild()!= null)
                this.setCodigoPostal(salida.item(0).getFirstChild().getNodeValue());            

           // cogemos los codigos de los documentos
            salida = doc.getDomDocument().getElementsByTagName(NODE_DOCUMENTO);        
            String name;
            String value;
            String extension;
            for (int i = 0 ; i < salida.getLength() ; i++){  
                
                name = null;
                value = null;
                extension = null;
                
                name = salida.item(i).getChildNodes().item(0).getFirstChild().getNodeValue();
                value = salida.item(i).getChildNodes().item(1).getFirstChild().getNodeValue();
                extension= salida.item(i).getChildNodes().item(2).getFirstChild().getNodeValue();
                
                if (name != null && value != null && extension != null)    
                    addDocumento(name,value.getBytes(),extension,null); 
            }
        }catch (Exception e){
            throw new DatosIncorrectosExcepcion(DatosIncorrectosErrorCodigos.EC_WRONG_FORMAT);
        }
        
    }
    
    /**************************************************************************************
     ****                                   GETTERS Y SETTERS               ***************
     **************************************************************************************/
    
    /*
     * Devuelve el numero de documentos asociados a la notificacion
     *
     * @return int numero de documentos
     */
    public int documentoCount(){
        return documentos.size();
    }
    
    /**
     * Añade un documento a la notificacion
     *
     * @param nombre_ String codigo del documento
     * @param nuevo_ byte [] contenido binario del fichero
     * @param extension_ String extension del fichero
     */
    public void addDocumento (String nombre_, byte [] nuevo_, String extension_,String guid_){
        documentos.add(nuevo_);
        nombreDocumentos.add(nombre_);
        extension.add(extension_);
        guid.add(guid_);
    }
    
    /**
     * Devuelve el contenido binario de un fichero asociado a la documentación que 
     * ocupa un indice concreto dentro del conjunto de documentos asociados a la 
     * notificacion
     *
     * @param indice_ int indice del documento dentro de todos los documentos asociados a la notificacion
     * @return byte [] contenido binario del fichero
     */
    public byte [] getDataDocumento (int indice_){
        return (byte [])documentos.get(indice_);
    }
    
    /**
     * Devuelve el nombre de un fichero asociado a la documentación que 
     * ocupa un indice concreto dentro del conjunto de documentos asociados a la 
     * notificacion
     * 
     * @param indice_ int indice del documento dentro de todos los documentos asociados a la notificacion
     * @return String nombre del fichero
     */
     public String getNameDocumento (int indice_){
        return (String)nombreDocumentos.get(indice_);
    }
     
    /**
     * Devuelve la extension de un fichero asociado a la documentación que 
     * ocupa un indice concreto dentro del conjunto de documentos asociados a la 
     * notificacion
     *
     * @param indice_ int indice del documento dentro de todos los documentos asociados a la notificacion
     * @return String extension
     */
      public String getExtDocumento (int indice_){
        return (String)extension.get(indice_);
    }
      
       /**
     * Devuelve el identificador gudi de un fichero asociado a la documentación que 
     * ocupa un indice concreto dentro del conjunto de documentos asociados a la 
     * notificacion
     *
     * @param indice_ int indice del documento dentro de todos los documentos asociados a la notificacion
     * @return String extension
     */
      public String getUIDDocumento (int indice_){
        return (String)guid.get(indice_);
    }

    /**
     * Devuelve el nombre del organismo / entidad emisora
     * @return String organismo / entidad emisora
     */
    public String getOrganismo() {
        return organismo;
    }

    /**
     * Establece el organismo
     * @param organismo String nuevo valor del orgnismo
     */
    public void setOrganismo(String organismo) {
        this.organismo = organismo;
    }

    /**
     * devuelve el valor del asunto
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
     * Establece el nuevo valor del tipo
     * @param tipo String nuevo valor del tipo
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Devuelve el texto
     * @return String texto
     */
    public String getTexto() {
        return texto;
    }

    /**
     * Establece el texto
     * @param texto String teto
     */
    public void setTexto(String texto) {
        this.texto = texto;
    }

    /**
     * Devuelve el valor del nif destinatario
     * @return String nif destinatario
     */
    public String getNifDest() {
        return nifDest;
    }

    /**
     * Establece el valor del nif destinatario
     * @param nifDest String nuevo valor del nif destinatario
     */
    public void setNifDest(String nifDest) {
        this.nifDest = nifDest;
    }

    /**
     * Devuelve el valor del nombre del destinatario
     * @return String nombre del destinatario
     */
    public String getNombreDest() {
        return nombreDest;
    }

    /**
     * Establece el valor del nombre del destinatario
     * @param nombreDest String nuevo valor del nombre del destinatario
     */
    public void setNombreDest(String nombreDest) {
        this.nombreDest = nombreDest;
    }

    /**
     * Devuelve los apellidos del destinatario
     * @return String apellidos del destinatario
     */
    public String getApellidosDest() {
        return apellidosDest;
    }

    /**
     * Establece los apellidos del destinatario
     * @param apellidosDest String nuevo valor de los apellidos del destinatario
     */
    public void setApellidosDest(String apellidosDest) {
        this.apellidosDest = apellidosDest;
    }

    /**
     * Devuelve el correo del destinatario
     * @return String correo del destinatario
     */
    public String getCorreoDest() {
        return correoDest;
    }

    /**
     * Establece el valor del correo del destinatario
     * @param correoDest String nuevo valor del correo del destinatario
     */
    public void setCorreoDest(String correoDest) {
        this.correoDest = correoDest;
    }

    /**
     * Devuelve el numero del expediente
     * @return String numero de expediente
     */
    public String getNumeroExpediente() {
        return numeroExpediente;
    }

    /**
     * Establece el valor del numero del expediente
     * @param numeroExpediente @String numero del expediente
     */
    public void setNumeroExpediente(String numeroExpediente) {
        this.numeroExpediente = numeroExpediente;
    }

    /**
     * Devuelve el valor del idioma
     * @return String Idioma
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
    public String getTipoViaDireccion() {
        return tipoViaDireccion;
    }

    /**
     * Establece el valor del tipo de via
     * @param tipoViaDireccion String nuevo valor del tipo de via
     */
    public void setTipoViaDireccion(String tipoViaDireccion) {
        this.tipoViaDireccion = tipoViaDireccion;
    }

    /**
     * Devueleve el nombre de la via
     *
     * @return String nombre de la via
     */
    public String getViaDireccion() {
        return viaDireccion;
    }

    /**
     * Establece el valor del nombre de la via
     * @param viaDireccion String nuevo valor del nombre de la via
     */
    public void setViaDireccion(String viaDireccion) {
        this.viaDireccion = viaDireccion;
    }

    /**
     * Devuelve el valor del numero de la direccion
     * @return String numero de la direccion
     */
    public String getNumeroDireccion() {
        return numeroDireccion;
    }

    /**
     * Establece el valr del numero de la direccion
     * @param numeroDireccion String nuevo valor del numero de la direccion
     */
    public void setNumeroDireccion(String numeroDireccion) {
        this.numeroDireccion = numeroDireccion;
    }

    /**
     * Devuelve el valor de la escalera de la direccion
     * @return String escalera de la direccion
     */
    public String getEscaleraDireccion() {
        return escaleraDireccion;
    }

    /**
     * Establece el valor de la escalera de la direccion
     * @param escaleraDireccion String nuevo valor de la escalera de la direccion
     */
    public void setEscaleraDireccion(String escaleraDireccion) {
        this.escaleraDireccion = escaleraDireccion;
    }

    /**
     * Devuelve el valor del piso de la direccion
     * @return Sring piso
     */
    public String getPisoDireccion() {
        return pisoDireccion;
    }

    /**
     * Establece el valor del piso de la direccion
     * @param pisoDireccion String nuevo valor del piso
     */
    public void setPisoDireccion(String pisoDireccion) {
        this.pisoDireccion = pisoDireccion;
    }

    /**
     * Devuelve el valor de la puerta de la direccion
     * @return String valor de la puerta de la direccion
     */
    public String getPuertaDireccion() {
        return puertaDireccion;
    }

    /**
     * Establece el valor de la puerta de la direccion
     * @param puertaDireccion String nuevo valor de la puerta de la direccion
     */
    public void setPuertaDireccion(String puertaDireccion) {
        this.puertaDireccion = puertaDireccion;
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
     * DEvuelve el valor de la provincia
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
     * Devuelve el valor del numero de registro
     * @return String numero de registro
     */
    public String getNumeroRegistro() {
        return numeroRegistro;
    }

    /**
     * Establece el numero de registro
     * @param numeroRegistro String nuevo valor del numero de registro
     */
    public void setNumeroRegistro(String numeroRegistro) {
        this.numeroRegistro = numeroRegistro;
    }

    /**
     * Devuelve la fecha de registro
     * @return Date fecha de registro
     */
    public java.util.Date getFechaRegistro() {
        return fechaRegistro;
    }

    /**
     * Establece el nuevo valor de la fecha de registro
     * @param fechaRegistro Date nuevo valor de la fecha de registro
     */
    public void setFechaRegistro(java.util.Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    /**
     * devuelve el valor del procedimiento
     * @return String procedimientp
     */
    public String getProcedimiento() {
        return procedimiento;
    }

    /**
     * Establece el valor del procedimientp
     * @param procedimiento String nuevo valor del procedimiento
     */
    public void setProcedimiento(String procedimiento) {
        this.procedimiento = procedimiento;
    }

    /**
     * Devuelve el valor del usuario
     * @return String usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * Establece el valor del usuario
     * @param usuario String nuevo valor del usuario
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * Devuelve el valor del tipo de correspondencia
     * @return String tipo de correspondencia
     */
    public String getTipoCorrespondencia() {
        return tipoCorrespondencia;
    }

    /**
     * Establece el valor del tipo de correspondencia
     * @param tipoCorrespondencia String nuevo valor del tipo de correspondencia
     */
    public void setTipoCorrespondencia(String tipoCorrespondencia) {
        this.tipoCorrespondencia = tipoCorrespondencia;
    }

    /**
     * Devuelve el valor de la fecha de entrega
     * @return Date feha de entrega
     */
    public java.util.Date getFechaEntrega() {
        return fechaEntrega;
    }
    
     /**
     * Devuelve el valor de la fecha de entrega como cadena
     * @return Date feha de entrega
     */
    public String getFechaEntregaCadena() {
        return toString(fechaEntrega);
    }
    
    /**
     * Establece el valor de la fecha de entrega
     * @param fechaEntrega Date nuevo valor de la fecha de entrega
     */
    public void setFechaEntrega(java.util.Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    /**
     * Devuelve el valor del estado
     * @return Integer estado
     */
    public Integer getEstado() {
        return estado;
    }

    /**
     * Establece el valor del estado
     * @param estado Integer nuevo valor del estado
     */
    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public String getDescripcionEstado() {
        return descripcionEstado;
    }

    public void setDescripcionEstado(String descripcionEstado) {
        this.descripcionEstado = descripcionEstado;
    }
    
    /**
     * Devuelve el valor del codigo de notificacion
     * @return String codigo de notificacion
     */
    public String getCodigoNoti() {
        return codigoNoti;
    }

    /**
     * Establece el valor del codigo de notificacion
     * @param codigoNoti String nuevo valor del codigo de notificacion
     */
    public void setCodigoNoti(String codigoNoti) {
        this.codigoNoti = codigoNoti;
    }

    /**
     * Devuelve el valor de la fecha de actulizacion del estado
     * @return Date fecha de actualizacion del estado
     */
    public java.util.Date getFechaActualiEstado() {
        return fechaActualiEstado;
    }

    /**
     * Establece el valor de la fecha de actualizacion del estado 
     * @param fechaActualiEstado Date nuevo valor de la fecha de actualzacion del estado
     */
    public void setFechaActualiEstado(java.util.Date fechaActualiEstado) {
        this.fechaActualiEstado = fechaActualiEstado;
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
