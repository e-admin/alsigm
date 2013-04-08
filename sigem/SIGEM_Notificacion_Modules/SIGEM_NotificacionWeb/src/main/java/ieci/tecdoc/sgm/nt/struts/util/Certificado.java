/*
 * Certificado.java
 *
 * Created on 13 de junio de 2007, 15:26
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ieci.tecdoc.sgm.nt.struts.util;

import java.security.cert.X509Certificate;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author X73994NA
 */
public class Certificado {
    
    /** Creates a new instance of Certificado */
    public Certificado() {
    }
    
    public static X509Certificate getClientCert(HttpServletRequest request){
        
        X509Certificate cert = null;

        Object obj = request.getAttribute("javax.servlet.request.X509Certificate");

        if (obj instanceof X509Certificate[]){
            X509Certificate[] certArr = (X509Certificate[])obj;
            cert = certArr[0];
        }else{
            if(obj instanceof X509Certificate)
            cert = (X509Certificate)obj;
        }
        
        return cert;

    }

    
}
