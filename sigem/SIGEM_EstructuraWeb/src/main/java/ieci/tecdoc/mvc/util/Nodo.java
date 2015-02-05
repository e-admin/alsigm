package ieci.tecdoc.mvc.util;
/*
 * Created on 24-feb-2005
 *
 */

/**
 * @author Antonio María
 *
 */
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
        tipo = Constantes.OTHER; 
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
        if (tipo == Constantes.PERSON)
           icon = "include/images/person.gif";
        else if (tipo == Constantes.GROUP)
           icon = "include/images/group.gif";
        else if (tipo == Constantes.ORGANIZATIONAL_UNIT)
           icon = "include/images/ou.gif";
        else
            icon = "include/images/other.gif";
        
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
