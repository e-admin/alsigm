/*
 * Notificacion.java
 *
 * Created on 18 de mayo de 2007, 12:13
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ieci.tecdoc.sgm.nt.database.datatypes;

/**
 *
 * @author Usuario
 */
public interface Notificacion {
    
    /**
     * Devuelve el codigo de error que se produjo en la notidicacion
     * @return String error
     */       
    public abstract String getError ();
    
    /**
     * Establece el codigo de error que se produjo en la notidicacion
     * @return String codigo de error.
     */       
    public abstract void setError (String nuevoValor_);

    /**
     * Devuelve el Guid de la Notificación.
     * @return String id.
     */       
    public abstract String getId ();
    
    /**
     * Devuelve el NIF del Destinatario de la Notificación.
     * @return String Nif.
     */     
    public abstract String getNifDestinatario ();

    /**
     * Devuelve el Registro de Salida de la Notificación.
     * @return String Registro de salida.
     */    
    public abstract String getRegistroSalida();
    
    /**
     * Devuelve la Fecha y hora del Registro anterior.
     * @return Date Fecha y hora.
     */   
    public abstract java.util.Date getFechaRegistroAnterior ();
    
    /**
     * Devuelve el Número del Expediente de la Notificación.
     * @return String Numero de expediete.
     */       
    public abstract String getNumeroExpediente ();           
                 
    /**
     * Devuelve el Procedimiento del Expediente anterior.
     * @return String procedimiento.
     */       	
    public abstract String getProcedimientoExpedienteAnterior ();

    public abstract String getDescripcionEstado ();
    
    /**
     * Devuelve el estado de la Notificación.
     * @return Integer  Estado.
     */   
    public abstract Integer getEstado ();

    /**
     * Devuelve Fecha y hora de la ultima actualizacion del estado de la Notificación.
     * @return Date fecha y hora.
     */   
    public abstract java.util.Date getFechaActualizacionEstado ();
    
    /**
     * Devuelve Fecha y hora de la Entrega de la Notificación.
     * @return Date fecha y hora.
     */   
    public abstract java.util.Date getFechaEfectuadaEntrega ();
  
     /**
     * Devuelve el usuario que realiza la  Notificación.
     * @return String identificador de usuario.
     */   
    public abstract String getUsuario ();
    
     /**
     * Devuelve el tipo de correspondecia de  la  Notificación.
     * @return String tipo de correspondecia
     */   
    public abstract String getTipoCorrespondencia ();
            
     
     /**
     * Devuelve el organismo/entidad emisora  la  Notificación.
     * @return String organismo
     */   
    public abstract String getOrganismo ();
    
     /**
     * Devuelve el asunto de  la  Notificación.
     * @return String asunto
     */   
    public abstract String getAsunto ();
    
     /**
     * Devuelve el tipo de  la  Notificación.
     * @return String tipo
     */   
    public abstract String getTipo ();        
     
     /**
     * Devuelve el texto asociado a  la  Notificación.
     * @return String texto asociado
     */   
    public abstract String getTexto ();        
            
     /**
     * Devuelve el nombre del destinatario de la  Notificación.
     * @return String nombre del destinatario
     */   
    public abstract String getNombreDestinatario ();       
     
     /**
     * Devuelve los apellidos del destinatario de la  Notificación.
     * @return String apellidos del destinatario
     */   
    public abstract String getApellidosDestinatario ();    
    
     /**
     * Devuelve el correo del destinatario de la  Notificación.
     * @return String correo del destinatario
     */   
    public abstract String getCorreoDestinatario ();    
         
     /**
     * Devuelve el idioma de la  Notificación.
     * @return String idioma
     */   
    public abstract String getIdioma();    
     
     /**
     * Devuelve el tipo de via de la  Notificación.
     * @return String tipo de via
     */   
    public abstract String getTipoVia(); 
    
     /**
     * Devuelve el nombre de la via de la  Notificación.
     * @return String nombre de la via
     */   
    public abstract String getNombreVia(); 
    
     /**
     * Devuelve el numero de la direccion de la via de la  Notificación.
     * @return String numero de la direccion
     */   
    public abstract String getNumeroVia(); 
            
     /**
     * Devuelve la escalera de la direccion de la via de la  Notificación.
     * @return String escalera de la direccion
     */   
    public abstract String getEscaleraVia();         
            
     /**
     * Devuelve el piso de la direccion de la via de la  Notificación.
     * @return String piso de la direccion
     */   
    public abstract String getPisoVia();        
    
     /**
     * Devuelve la puerta de la direccion de la via de la  Notificación.
     * @return String puerta de la direccion
     */   
    public abstract String getPuertaVia();           
    
     /**
     * Devuelve el telefono de referencia de la  Notificación.
     * @return String telefono
     */   
    public abstract String getTelefono();    
    
     /**
     * Devuelve el municipio de la  Notificación.
     * @return String municipio
     */   
    public abstract String getMunicipio();    
    
     /**
     * Devuelve la provincia de la  Notificación.
     * @return String provincia
     */   
    public abstract String getProvincia();    
    
     /**
     * Devuelve el codigo postal de la  Notificación.
     * @return String codigo postal
     */   
    public abstract String getCodigoPostal();  
    
    /**
     * Establece el Guid de la Notificación.
     * @param String id.
     */       
    public abstract void setId (String nuevoValor_);
    
    /**
     * Establece el NIF del Destinatario de la Notificación.
     * @param String Nif.
     */     
    public abstract void setNifDestinatario (String nuevoValor_);

    /**
     * Establece el Registro de Salida de la Notificación.
     * @param String Registro de salida.
     */    
    public abstract void setRegistroSalida(String nuevoValor_);
    
    /**
     * Establece la Fecha y hora del Registro anterior.
     * @param Date Fecha y hora.
     */   
    public abstract void setFechaRegistroAnterior (java.util.Date nuevoValor_);
    
    /**
     * Establece el Número del Expediente de la Notificación.
     * @param String Numero de expediete.
     */       
    public abstract void setNumeroExpediente (String nuevoValor_);   
                 
    /**
     * Establece el Procedimiento del Expediente anterior.
     * @param String procedimiento.
     */       	
    public abstract void setProcedimientoExpedienteAnterior (String nuevoValor_);

    public abstract void setDescripcionEstado (String nuevoValor_);
    
    /**
     * Establece el estado de la Notificación. 
     * @param String Nuevo estado
     */          
    public abstract void setEstado (Integer nuevoValor_);
  
    /**
     * Establece la Fecha y hora de la ultima actualizacion del estado de la Notificación.
     * @param Date fecha y  de la actualizacion del estado
     */   
    public abstract void setFechaActualizacionEstado (java.util.Date nuevoValor_);
 
     /**
     * Establece Fecha y hora de la Entrega de la Notificación.
     * @param Date fecha y hora.
     */   
    public abstract void setFechaEfectuadaEntrega (java.util.Date nuevoValor_);
    
    
    /**
     * Establece el usuario que realiza la  Notificación.
     * @param String identificador de usuario.
     */   
    public abstract void setUsuario (String nuevoValor_);
    
     /**
     * Establece el tipo de correspondecia de  la  Notificación.
     * @param String tipo de correspondecia
     */   
    public abstract void setTipoCorrespondencia (String nuevoValor_);
            
     
     /**
     * Establece el organismo/entidad emisora  la  Notificación.
     * @param String organismo
     */   
    public abstract void setOrganismo (String nuevoValor_);
    
     /**
     * Establece el asunto de  la  Notificación.
     * @param String asunto
     */   
    public abstract void setAsunto (String nuevoValor_);
    
     /**
     * Establece el tipo de  la  Notificación.
     * @param String tipo
     */   
    public abstract void setTipo (String nuevoValor_);
     
     /**
     * Establece el texto asociado a  la  Notificación.
     * @param String texto asociado
     */   
    public abstract void setTexto (String nuevoValor_);
            
     /**
     * Establece el nombre del destinatario de la  Notificación.
     * @param String nombre del destinatario
     */   
    public abstract void setNombreDestinatario (String nuevoValor_);
     
     /**
     * Establece los apellidos del destinatario de la  Notificación.
     * @param String apellidos del destinatario
     */   
    public abstract void setApellidosDestinatario (String nuevoValor_);
    
     /**
     * Establece el correo del destinatario de la  Notificación.
     * @param String correo del destinatario
     */   
    public abstract void setCorreoDestinatario (String nuevoValor_);
         
     /**
     * Establece el idioma de la  Notificación.
     * @param String idioma
     */   
    public abstract void setIdioma(String nuevoValor_);
     
     /**
     * Establece el tipo de via de la  Notificación.
     * @param String tipo de via
     */   
    public abstract void setTipoVia(String nuevoValor_);
    
     /**
     * Establece el nombre de la via de la  Notificación.
     * @param String nombre de la via
     */   
    public abstract void setNombreVia(String nuevoValor_);
    
     /**
     * Establece el numero de la direccion de la via de la  Notificación.
     * @param String numero de la direccion
     */   
    public abstract void setNumeroVia(String nuevoValor_);
            
     /**
     * Establece la escalera de la direccion de la via de la  Notificación.
     * @param String escalera de la direccion
     */   
    public abstract void setEscaleraVia(String nuevoValor_);
            
     /**
     * Establece el piso de la direccion de la via de la  Notificación.
     * @param String piso de la direccion
     */   
    public abstract void setPisoVia(String nuevoValor_);
    
     /**
     * Establece la puerta de la direccion de la via de la  Notificación.
     * @param String puerta de la direccion
     */   
    public abstract void setPuertaVia(String nuevoValor_);
    
     /**
     * Establece el telefono de referencia de la  Notificación.
     * @param String telefono
     */   
    public abstract void setTelefono(String nuevoValor_);
    
     /**
     * Establece el municipio de la  Notificación.
     * @param String municipio
     */   
    public abstract void setMunicipio(String nuevoValor_);
    
     /**
     * Establece la provincia de la  Notificación.
     * @param String provincia
     */   
    public abstract void setProvincia(String nuevoValor_);
    
     /**
     * Establece el codigo postal de la  Notificación.
     * @param String codigo postal
     */   
    public abstract void setCodigoPostal(String nuevoValor_);
  
     /**
     * Recoge los valores de la instancia en una cadena xml
     * @param header Si se incluye la cabecera
     * @return los datos en formato xml
     */	
    
    public abstract String getNotiId();
    public abstract void setNotiId(String notiid);
    
    public abstract String getSistemaId();
    public abstract void setSistemaId(String sistemaId);
    
    public abstract String getDEU();
    public abstract void setDEU(String deu);
    
    public abstract String getMovil();
    public abstract void setMovil(String movil);
	
    public abstract String toXML(boolean header);

    /**
     * Devuelve los valores de la instancia en una cadena de caracteres.
     */
	
    public abstract String toString();
}
