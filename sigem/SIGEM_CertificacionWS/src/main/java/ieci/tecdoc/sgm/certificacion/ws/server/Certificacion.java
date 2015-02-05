package ieci.tecdoc.sgm.certificacion.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

/**
 * Bean con las propiedades de un elemento mime.
 * 
 * @author IECISA
 *
 */
public class Certificacion extends RetornoServicio
{

	/**
	 * Devuelve el identifcador del documento.
	 * @return String Identificador del documento.
	 */
   public String getIdFichero(){
	   return idFichero;
   }
  
  /**
   * Devuelve el identificador de usuario.
   * @return String Identificador de usuario.
   */   
   public String getIdUsuario(){
	   return idUsuario;
   }
  
  /**
   * Establece el identificador del documento
   * @param idFichero Identificador del documento.
   */	
   public void setIdFichero(String idFichero){
	   this.idFichero = idFichero;
   }
  
  /**
   * Establece el identificador del usuario.
   * @param idUsuario Identificador del usuario.
   */   
   public void setIdUsuario(String idUsuario){
	   this.idUsuario = idUsuario;
   }

   private String idUsuario;
   private String idFichero;
   
}