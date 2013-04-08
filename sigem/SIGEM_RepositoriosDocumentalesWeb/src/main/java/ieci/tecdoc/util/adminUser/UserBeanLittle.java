/*
 * Created on 21-mar-2005
 *
 */
package ieci.tecdoc.util.adminUser;

/**
 * @author Antonio María
 *
 */
public class UserBeanLittle {
    int id;
    String nombre;
    boolean bloqueado;
    int deptId;
    String mgrDeptId;
    
    public UserBeanLittle()
    {
        id = -1;
        nombre="";
        bloqueado = false;
        deptId = -1;
        Double d = new Double (4);
        mgrDeptId = null;
       
    }
    
    

    /**
     * @return Returns the mgrDeptId.
     */
    public String getMgrDeptId() {
        return mgrDeptId;
    }
    /**
     * @param mgrDeptId The mgrDeptId to set.
     */
    public void setMgrDeptId(String mgrDeptId) {
        this.mgrDeptId = mgrDeptId;
    }
    /**
     * @return Returns the deptId.
     */
    public int getDeptId() {
        return deptId;
    }
    /**
     * @param deptId The deptId to set.
     */
    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }
    /**
     * @return Returns the bloqueado.
     */
    public boolean isBloqueado() {
        return bloqueado;
    }
    /**
     * @param bloqueado The bloqueado to set.
     */
    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }
    /**
     * @return Returns the id.
     */
    public int getId() {
        return id;
    }
    /**
     * @param id The id to set.
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * @return Returns the nombre.
     */
    public String getNombre() {
        return nombre;
    }
    /**
     * @param nombre The nombre to set.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String toString()
    {
        String s = id + ": "+ nombre + " - Bloqueado: " + bloqueado;
        return s;
    }
}
