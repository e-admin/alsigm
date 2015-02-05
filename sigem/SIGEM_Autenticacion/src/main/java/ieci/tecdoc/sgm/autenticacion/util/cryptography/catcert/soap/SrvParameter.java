/*
 * SrvParameter.java
 *
 * Created on 27 de abril de 2004, 11:11
 */

package ieci.tecdoc.sgm.autenticacion.util.cryptography.catcert.soap;

import javax.xml.rpc.ParameterMode;
import javax.xml.namespace.QName;



/**
 *
 * @author  luismiguel
 */
public class SrvParameter {
    
    /**
     * Holds value of property name.
     */
    private String name;
    
    /**
     * Holds value of property qname.
     */
    private QName qname;
    
    /**
     * Holds value of property mode.
     */
    private ParameterMode mode;
    
    /**
     * Holds value of property value.
     */
    private Object value;
    
    /** Creates a new instance of SrvParameter */
    public SrvParameter(  String name, QName qname, ParameterMode mode, Object value) {
       this.name = name;
       this.qname = qname;
       this.mode = mode;
       this.value = value;
    }
    
    /**
     * Getter for property name.
     * @return Value of property name.
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * Setter for property name.
     * @param name New value of property name.
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Getter for property qname.
     * @return Value of property qname.
     */
    public QName getQname() {
        return this.qname;
    }
    
    /**
     * Setter for property qname.
     * @param qname New value of property qname.
     */
    public void setQname(QName qname) {
        this.qname = qname;
    }
    
    /**
     * Getter for property mode.
     * @return Value of property mode.
     */
    public ParameterMode getMode() {
        return this.mode;
    }
    
    /**
     * Setter for property mode.
     * @param mode New value of property mode.
     */
    public void setMode(ParameterMode mode) {
        this.mode = mode;
    }
    
    /**
     * Getter for property value.
     * @return Value of property value.
     */
    public Object getValue() {
        return this.value;
    }
    
    /**
     * Setter for property value.
     * @param value New value of property value.
     */
    public void setValue(Object value) {
        this.value = value;
    }
    
}
