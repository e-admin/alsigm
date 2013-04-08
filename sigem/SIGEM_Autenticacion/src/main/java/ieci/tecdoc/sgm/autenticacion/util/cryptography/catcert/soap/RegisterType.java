/*
 * RegisterType.java
 *
 * Created on 10 de junio de 2004, 17:33
 */

package ieci.tecdoc.sgm.autenticacion.util.cryptography.catcert.soap;

import javax.xml.namespace.QName;


/**
 *
 * @author  LUISMIGUEL
 */
public class RegisterType {
    
    Class   mClase;
    QName   mQName;
    boolean mDh;
    
    /** Creates a new instance of RegisterType */
    public RegisterType( Class clase, QName name, boolean bDh) {
       mClase = clase;
       mQName = name;
       mDh = bDh;
    }
   
     public Class getClassReg() {
         return mClase;
     }
     
   public QName getQName() {
       return mQName;
   }
   
   public boolean getDh() {
       return mDh;  
   }
    
}
