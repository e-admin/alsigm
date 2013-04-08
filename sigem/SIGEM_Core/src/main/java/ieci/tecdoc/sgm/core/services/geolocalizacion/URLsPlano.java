/**
 * URLsPlano.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.core.services.geolocalizacion;

public class URLsPlano {
    
	private String urlGuiaUrbana;
    private String urlMapServer;

    public URLsPlano() {
    }

    public URLsPlano(
           String urlGuiaUrbana,
           String urlMapServer) {
           this.urlGuiaUrbana = urlGuiaUrbana;
           this.urlMapServer = urlMapServer;
    }


    /**
     * Gets the urlGuiaUrbana value for this URLsPlano.
     * 
     * @return urlGuiaUrbana
     */
    public String getUrlGuiaUrbana() {
        return urlGuiaUrbana;
    }


    /**
     * Sets the urlGuiaUrbana value for this URLsPlano.
     * 
     * @param urlGuiaUrbana
     */
    public void setUrlGuiaUrbana(String urlGuiaUrbana) {
        this.urlGuiaUrbana = urlGuiaUrbana;
    }


    /**
     * Gets the urlMapServer value for this URLsPlano.
     * 
     * @return urlMapServer
     */
    public String getUrlMapServer() {
        return urlMapServer;
    }


    /**
     * Sets the urlMapServer value for this URLsPlano.
     * 
     * @param urlMapServer
     */
    public void setUrlMapServer(String urlMapServer) {
        this.urlMapServer = urlMapServer;
    }
}
