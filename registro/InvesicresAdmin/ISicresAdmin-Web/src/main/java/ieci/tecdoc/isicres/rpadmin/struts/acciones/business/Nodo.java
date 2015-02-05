package ieci.tecdoc.isicres.rpadmin.struts.acciones.business;

import es.ieci.tecdoc.isicres.admin.core.ldap.LdapConstants;

public class Nodo {
	private String valor;
    private String hashCode;
    private boolean hijosMostrados;
    private byte tipo;
    private String icon;
    private int numHijos;
    private boolean tieneHijos;
    
    
    /**
     * @return Returns the tieneHijos.
     */
    public boolean isTieneHijos() {
        return tieneHijos;
    }
    /**
     * @param tieneHijos The tieneHijos to set.
     */
    public void setTieneHijos(boolean tieneHijos) {
        this.tieneHijos = tieneHijos;
    }
    public Nodo(String valor)
    {
        this.valor = valor;
        this.hashCode = String.valueOf(Math.abs(valor.hashCode()));
        hijosMostrados = false;
        tipo = LdapConstants.OTHER; 
    }

    public String getHashCode() {
        return hashCode;
    }

    public String getValor() {
        return valor;
    }
    public void setIcon(String icon)
    {
        this.icon = icon;
    }
    public String getIcon()
    {
        if (tipo == LdapConstants.PERSON)
           icon = Constantes.TOKEN_ICON_PERSON_LDAP;
        else if (tipo == LdapConstants.GROUP)
           icon = Constantes.TOKEN_ICON_GROUP_LDAP;
        else if (tipo == LdapConstants.ORGANIZATIONAL_UNIT)
           icon = Constantes.TOKEN_ICON_ORGANIZATIONAL_UNIT;
        else
            icon = Constantes.TOKEN_ICON_OTHER;
        
        return icon;
    }
    public void setValor(String valor) {
        this.valor = valor;
    }
    public String toString()
    {
        String s = "Valor: " + valor + " Id: " + hashCode;
        return s;
    }
    public String getTitle()
    {
        int i = 0;
        
        i = valor.indexOf(",");
        String r = valor;
        if (i >= 0)
            r =valor.substring(0, i); 
        return r;
    }
    
    /**
     * @return Returns the hijosMostrados.
     */
    public boolean getHijosMostrados() {
        return hijosMostrados;
    }
    /**
     * @param hijosMostrados The hijosMostrados to set.
     */
    public void setHijosMostrados(boolean hijosMostrados) {
        this.hijosMostrados = hijosMostrados;
    }
    /**
     * @return Returns the tipo.
     */
    public byte getTipo() {
        return tipo;
    }
    /**
     * @param tipo The tipo to set.
     */
    public void setTipo(byte tipo) {
        this.tipo = tipo;
    }
    /**
     * @return Returns the numHijos.
     */
    public int getNumHijos() {
        return numHijos;
    }
    /**
     * @param numHijos The numHijos to set.
     */
    public void setNumHijos(int numHijos) {
        this.numHijos = numHijos;
    }
}
