package ieci.tecdoc.sgm.autenticacion.util.cryptography.catcert.soap;


/*
 * ISoap.java
 *
 * Created on 20 de junio de 2001, 16:45
 */

/** Interface de los métodos que deben realizarse para generar un servicio Soap
 *
 * @author luismiguel
 * @version
 */
public interface ISoap {
     // Servidor del Servlet Router
   /** Devuelve el Host donde esta instalado el router Soap
    */   
      public String getSoapHost();
      // Puerto del Servlet Router
      /** Devuelve el Puerto donde esta instalado el router Soap
       */      
      public int getSoapPort(); 
      // Nombre del Servlet Router
      /** Devuelve el servlet donde esta instalado el router Soap
       */      
      public String getSoapEndPoint(); 
      // Nombre del servicio
      /** Devuelve Urn del servicio que se quiere llamar
       */      
          
     
}

