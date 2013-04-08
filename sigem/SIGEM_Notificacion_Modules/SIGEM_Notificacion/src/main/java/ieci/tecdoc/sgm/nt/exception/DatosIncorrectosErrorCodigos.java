/*
 * DatosIncorrectosErrorCodigos.java
 *
 * Created on 22 de mayo de 2007, 10:31
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ieci.tecdoc.sgm.nt.exception;

/**
 *
 * @author Usuario
 */
public final class DatosIncorrectosErrorCodigos {
    
    /**
     * Creates a new instance of DatosIncorrectosErrorCodigos
     */
    public DatosIncorrectosErrorCodigos() {
    }
    
    public static final long EC_PREFIX = 10004000;

    public static final long EC_WRONG_FORMAT = EC_PREFIX + 1;
    public static final long EC_UNKNOW_ERROR = EC_PREFIX + 2;
    public static final long EC_UNKNOW_DATA = EC_PREFIX + 3;
    public static final long EC_ERROR_ESTADO_SEARCH = EC_PREFIX + 4;
    public static final long EC_UPDATE_ERROR = EC_PREFIX + 5;
    
}
