/*
 * EstadoBean.java
 *
 * Created on 23 de mayo de 2007, 17:30
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ieci.tecdoc.sgm.nt.bean;

/**
 *
 * Cotenedor de atributos necesarios para identificar un estado
 */
public final class EstadoBean  extends GenericoBean{
    
    private int estado;
    
    /** Creates a new instance of EstadoBean */
    public EstadoBean() {
    }
    
    /** Creates a new instance of EstadoBean */
    public EstadoBean(int estado_) {
        estado = estado_;
    }

    /** Devuelve el estado
     * 
     * @return int
     */
    public int getEstado() {
        return estado;
    }

    /** Establece el estado
     * 
     * @param estado int
     */
    public void setEstado(int estado) {
        this.estado = estado;
    }
    
}
