/*
 * Created on 16-nov-2005
 *
 */
package ieci.tdw.ispac.ispacpublicador.business.milestone;

/**
 * Clase que almacena el resultado de una operación de
 * borrado en bloque de hitos activos.
 * @author Ildefonso Noreña
 *
 */
public class ResultDelete {

    /**
     * Número de Hitos Activos eliminados.
     */
    private int deletedActiveMilestone;
    /**
     * Número de registro eliminados. Puede ser distinto al número
     * de Hitos Activos ya que puede haber varios registros para 
     * el mismo identificador de Hito, al insertarse un registro
     * de Hito Activo por aplicación activa en el sistema.
     */
    private int deletedRegister;
    
    /**
     * Identificador del último Hito borrado. 
     */
    private int lastDeletedMilestone; 
    
    /**
     * Identificador del Sistem Externo. La operación de borrado se
     * realiza por bloques según el sistema externo, este valor lo
     * identifica.
     */
    private String externalSystem;
    /**
     * @return Returns the deletedActiveMilestone.
     */
    
//==================================================================
//  CONSTRUCTORES
//==================================================================    
    
    
    /**
     * Construye un nuevo ResultDelete inicializando los atributos a valores neutros. 
     */
    public ResultDelete(){
        setDeletedActiveMilestone(0);
        setDeletedRegister(0);
        setLastDeletedMilestone(-1);
        setExternalSystem(null);        
    }
    /**
     * Construye un nuevo ResultDelete inicializando los atributos con los parámetros pasados.
     * @param deletedActiveMilestone
     * @param deletedRegister
     * @param lastDeletedMilestone
     * @param externalSystem
     */
    public ResultDelete(int deletedActiveMilestone, int deletedRegister, int lastDeletedMilestone,  String externalSystem){
        setDeletedActiveMilestone(deletedActiveMilestone);
        setDeletedRegister(deletedRegister);
        setLastDeletedMilestone(lastDeletedMilestone);
        setExternalSystem(externalSystem);
    }
    
    

//==================================================================
// GETTERS   y   SETTERS
//==================================================================
    
    public int getDeletedActiveMilestone() {
        return deletedActiveMilestone;
    }
    /**
     * @param deletedActiveMilestone The deletedActiveMilestone to set.
     */
    public void setDeletedActiveMilestone(int deletedActiveMilestone) {
        this.deletedActiveMilestone = deletedActiveMilestone;
    }
    /**
     * @return Returns the deletedRegister.
     */
    public int getDeletedRegister() {
        return deletedRegister;
    }
    /**
     * @param deletedRegister The deletedRegister to set.
     */
    public void setDeletedRegister(int deletedRegister) {
        this.deletedRegister = deletedRegister;
    }
    /**
     * @return Returns the externalSystem.
     */
    public String getExternalSystem() {
        return externalSystem;
    }
    /**
     * @param externalSystem The externalSystem to set.
     */
    public void setExternalSystem(String externalSystem) {
        this.externalSystem = externalSystem;
    }
    /**
     * @return Returns the lastDeletedMilestone.
     */
    public int getLastDeletedMilestone() {
        return lastDeletedMilestone;
    }
    /**
     * @param lastDeletedMilestone The lastDeletedMilestone to set.
     */
    public void setLastDeletedMilestone(int lastDeletedMilestone) {
        this.lastDeletedMilestone = lastDeletedMilestone;
    }
}
