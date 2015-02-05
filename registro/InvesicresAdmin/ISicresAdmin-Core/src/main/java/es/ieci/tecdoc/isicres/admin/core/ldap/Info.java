/*
 * Created on 04-abr-2005
 *
 */
package es.ieci.tecdoc.isicres.admin.core.ldap;


import javax.naming.directory.Attribute;

/**
 * @author Antonio María
 *
 */
public class Info
{
    private String valor;
    private byte objectClass;
    private Attribute objectClassObj;
    
    /**
     * @return Returns the dn.
     */
    public Info(String valor, Attribute objectClass)
    {
    //Prevenir objetos a null
    	objectClassObj = objectClass;
    	if (objectClass != null){
    		if ( objectClass.contains("person") )
    			this.objectClass = LdapConstants.PERSON;
    		else if ( objectClass.contains("group"))
    			this.objectClass = LdapConstants.DEPARTMENT;
    		else if ( objectClass.contains("groupOfUniqueNames"))
    			this.objectClass = LdapConstants.GROUP;
    		else if ( objectClass.contains("organizationalUnit"))
    			this.objectClass = LdapConstants.ORGANIZATIONAL_UNIT;
    		else
    			this.objectClass = LdapConstants.OTHER;
    	}
    	else
    		this.objectClass = LdapConstants.OTHER;
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

	public Attribute getObjectClassObj() {
		return objectClassObj;
	}

	public void setObjectClassObj(Attribute objectClassObj) {
		this.objectClassObj = objectClassObj;
	}

}
