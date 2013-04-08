/*
 * Created on 04-abr-2005
 *
 */
package ieci.tecdoc.mvc.util.ldap;

import ieci.tecdoc.mvc.util.Constantes;

import javax.naming.directory.Attribute;

/**
 * @author Antonio María
 *
 */
public class Info
{
    private String valor;
    private byte objectClass;
    
    /**
     * @return Returns the dn.
     */
    public Info(String valor, Attribute objectClass)
    {
    //Prevenir objetos a null
    	if (objectClass != null){
    		if ( objectClass.contains("person") )
    			this.objectClass = Constantes.PERSON;
    		else if ( objectClass.contains("group") || objectClass.contains("groupOfUniqueNames"))
    			this.objectClass = Constantes.GROUP;
    		else if ( objectClass.contains("organizationalUnit"))
    			this.objectClass = Constantes.ORGANIZATIONAL_UNIT;
    		else
    			this.objectClass = Constantes.OTHER;
    	}
    	else
    		this.objectClass = Constantes.OTHER;
    	this.valor = valor;
    }
    
    public String getValor() {
        return valor;
    }
    /**
     * @param dn The dn to set.
     */
    public void setValor(String valor) {
        this.valor = valor;
    }
    /**
     * @return Returns the objectClass.
     */
    public byte getObjectClass() {
        return objectClass;
    }
    /**
     * @param objectClass The objectClass to set.
     */
    public void setObjectClass(byte objectClass) {
        this.objectClass = objectClass;
    }
    
}
