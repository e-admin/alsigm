package ieci.tecdoc.sgm.core.utils.ldap;

import javax.naming.InvalidNameException;


import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.sun.jndi.ldap.LdapName;

public class LdapUtils {

	private static Logger logger = Logger.getLogger(LdapUtils.class);
	
	
    /**
     * Metodo que obtiene el titulo del nodo pasado como parametro
     * @param nodeDn
     * @return
     */
    public static String getLdapTitleDn(String nodeDn){
    	String result = nodeDn;    	
    	LdapName ldapName = null;

    	try {
    		if(StringUtils.isNotEmpty(nodeDn)){
	    		ldapName = new LdapName(nodeDn);
	    		result = ldapName.get(ldapName.size()-1);
    		}
		} catch (InvalidNameException e) {
    		logger.debug("Error al obtener el title del nodo: "+ nodeDn );
		}
  
		return result;
    }
}
