/*
 * ConfSoap.java
 *
 * Created on 27 de abril de 2004, 10:43
 */

package ieci.tecdoc.sgm.autenticacion.util.cryptography.catcert.soap.conf;

/**
 *
 * @author  luismiguel
 */
public class SoapEnvInfo {
    
    /**
     * Holds value of property host.
     */
    private String host;
    
    /**
     * Holds value of property port.
     */
    private int port;
    
   
    /**
     * Holds value of property service.
     */
    private String service;
    
    /**
     * Holds value of property context.
     */
    private String context;
    
    /**
     * Holds value of property http.
     */
    private String http;
    
    /** Creates a new instance of ConfSoap */
    public SoapEnvInfo(String service, String http, String host, int port, String context) {
        
       this.service = service;  
       this.http = http;
       this.host = host;
       this.port = port; 
       this.context = context;
    }
    
    /**
     * Getter for property host.
     * @return Value of property host.
     */
    public String getHost() {
        return this.host;
    }
    
    /**
     * Setter for property host.
     * @param host New value of property host.
     */
    public void setHost(String host) {
        this.host = host;
    }
    
    /**
     * Getter for property port.
     * @return Value of property port.
     */
    public int getPort() {
        return this.port;
    }
    
    /**
     * Setter for property port.
     * @param port New value of property port.
     */
    public void setPort(int port) {
        this.port = port;
    }
    
      
    /**
     * Getter for property service.
     * @return Value of property service.
     */
    public String getService() {
        return this.service;
    }
    
    /**
     * Setter for property service.
     * @param service New value of property service.
     */
    public void setService(String service) {
        this.service = service;
    }
    
    /**
     * Getter for property context.
     * @return Value of property context.
     */
    public String getContext() {
        return this.context;
    }
    
    /**
     * Setter for property context.
     * @param context New value of property context.
     */
    public void setContext(String context) {
        this.context = context;
    }
    
    /**
     * Getter for property http.
     * @return Value of property http.
     */
    public String getHttp() {
        return this.http;
    }
    
    /**
     * Setter for property http.
     * @param http New value of property http.
     */
    public void setHttp(String http) {
        this.http = http;
    }
    
}
