/*
 * Created on 22-mar-2005
 *
 */
package ieci.tecdoc.mvc.form.adminUser.bd;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * @author Antonio María
 *
 */
public class BasicForm extends ActionForm{
    
    private static Logger logger = Logger.getLogger(BasicForm.class);

    String guid;
    int id;
    String nombre;
    String descripcion;
    /*
    Date creationDate;
    Date updateDate;
    int updaterId;
    int managerId;
    int creatorId;
    */
    String creationDate;
    String updateDate;
    String updaterName;
    String managerName;
    String creatorName;

    String managerId;
    String belongToDept; // Nombre del depto al que pertenece en caso de que sea usuario
    boolean bloqueado;
    
    
    // Permisos Genéricos
    boolean idocConsulta;
    boolean idocModificacion;
    boolean idocCreacion;
    boolean idocBorrado; 
    boolean idocImpresion;
    

    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        if (logger.isDebugEnabled())
            logger.debug("# BasicForm Reset!");
        
        id = -1; 
        guid =
        nombre = 
        descripcion =
        belongToDept =
        creationDate = 
        updateDate = 
        updaterName = 
        managerName = 
        creatorName =
        managerId = "";
        
        idocConsulta= 
        idocModificacion= 
        idocCreacion =
        idocBorrado= 
        idocImpresion =
        
        bloqueado = false;
        
    }

    /**
     * @return Returns the managerId.
     */
    public String getManagerId() {
        return managerId;
    }
    /**
     * @param managerId The managerId to set.
     */
    public void setManagerId(String managerId) {
        this.managerId = managerId;
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
     * @return Returns the belongToDept.
     */
    public String getBelongToDept() {
        return belongToDept;
    }
    /**
     * @param belongToDept The belongToDept to set.
     */
    public void setBelongToDept(String belongToDept) {
        this.belongToDept = belongToDept;
    }
    /**
     * @return Returns the creationDate.
     */
    public String getCreationDate() {
        return creationDate;
    }
    /**
     * @param creationDate The creationDate to set.
     */
    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
    /**
     * @return Returns the creatorName.
     */
    public String getCreatorName() {
        return creatorName;
    }
    /**
     * @param creatorName The creatorName to set.
     */
    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }
    /**
     * @return Returns the descripcion.
     */
    public String getDescripcion() {
        return descripcion;
    }
    /**
     * @param descripcion The descripcion to set.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
     * @return Returns the idocBorrado.
     */
    public boolean isIdocBorrado() {
        return idocBorrado;
    }
    /**
     * @param idocBorrado The idocBorrado to set.
     */
    public void setIdocBorrado(boolean idocBorrado) {
        this.idocBorrado = idocBorrado;
    }
    /**
     * @return Returns the idocConsulta.
     */
    public boolean isIdocConsulta() {
        return idocConsulta;
    }
    /**
     * @param idocConsulta The idocConsulta to set.
     */
    public void setIdocConsulta(boolean idocConsulta) {
        this.idocConsulta = idocConsulta;
    }
    /**
     * @return Returns the idocCreacion.
     */
    public boolean isIdocCreacion() {
        return idocCreacion;
    }
    /**
     * @param idocCreacion The idocCreacion to set.
     */
    public void setIdocCreacion(boolean idocCreacion) {
        this.idocCreacion = idocCreacion;
    }
    /**
     * @return Returns the idocImpresion.
     */
    public boolean isIdocImpresion() {
        return idocImpresion;
    }
    /**
     * @param idocImpresion The idocImpresion to set.
     */
    public void setIdocImpresion(boolean idocImpresion) {
        this.idocImpresion = idocImpresion;
    }
    /**
     * @return Returns the idocModificacion.
     */
    public boolean isIdocModificacion() {
        return idocModificacion;
    }
    /**
     * @param idocModificacion The idocModificacion to set.
     */
    public void setIdocModificacion(boolean idocModificacion) {
        this.idocModificacion = idocModificacion;
    }
    /**
     * @return Returns the managerName.
     */
    public String getManagerName() {
        return managerName;
    }
    /**
     * @param managerName The managerName to set.
     */
    public void setManagerName(String managerName) {
        this.managerName = managerName;
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
    /**
     * @return Returns the updateDate.
     */
    public String getUpdateDate() {
        return updateDate;
    }
    /**
     * @param updateDate The updateDate to set.
     */
    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }
    /**
     * @return Returns the updaterName.
     */
    public String getUpdaterName() {
        return updaterName;
    }
    /**
     * @param updaterName The updaterName to set.
     */
    public void setUpdaterName(String updaterName) {
        this.updaterName = updaterName;
    }
    /**
     * @return Returns the logger.
     */
    public static Logger getLogger() {
        return logger;
    }
    /**
     * @param logger The logger to set.
     */
    public static void setLogger(Logger logger) {
        BasicForm.logger = logger;
    }
    /**
     * @return Returns the guid.
     */
    public String getGuid() {
        return guid;
    }
    /**
     * @param guid The guid to set.
     */
    public void setGuid(String guid) {
        this.guid = guid;
    }
}
