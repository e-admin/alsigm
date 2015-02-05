package ieci.tecdoc.sgm.catalogo.ws.server;
/*
 * $Id: ConectorAutenticacion.java,v 1.1.2.1 2008/01/25 12:25:07 jconca Exp $
 */
import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;


// import org.apache.log4j.Logger;
/**
 * Clase que almacena información que relacionada un trámite con un conector.
 */
public class ConectorAutenticacion extends RetornoServicio {

   protected String tramiteId;
   protected String conectorId;

   /*
    * Constructor de la clase.
    */
   public ConectorAutenticacion() {

   }

   /**
    * Obtienen el identificador de trámite
    * @return String Identificador de trámite.
    */
   public String getTramiteId() {
      return this.tramiteId;
   }

   /**
    * Obtienen el identificador del conector
    * @return String Identificador de conector.
    */
   public String getConectorId() {
      return this.conectorId;
   }

   /**
    * Establece el identificador del trámite
    * @param sessionId Identificador de trámite.
    */
   public void setTramiteId(String tramiteId) {
      this.tramiteId = tramiteId;
   }

   /**
    * Establece el identificador del conector
    * @param hookId Identificador de conector.
    */
   public void setConectorId(String conectorId) {
      this.conectorId = conectorId;
   }

}