/*
 * Created on 11-abr-2005
 *
 */
package ieci.tecdoc.mvc.form.volume;

import ieci.tecdoc.idoc.admin.api.volume.VolumeDefs;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * @author Antonio María
 *
 */
public class VolumeForm extends ActionForm {

    int id;
    int idRep;
    String name;
    String maxSize;
    String actSize;
    int numFiles;
    String states[];
    
    String path;
    String volPath;
    String description;
    String repositoryName;
    
    String creationDate;
    String updateDate;
    String updaterName;
    String managerName;
    String creatorName;
    
    String listAsociated[];
    
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        id = 
        numFiles = -1;

        states = new String[3]; // Cuando se crea un volumen por defecto esta disponible
        states[0] = String.valueOf(VolumeDefs.VOL_STAT_NOT_READY);
        name = 
        maxSize =
        actSize =
        path =
        description =
        repositoryName =
        
        creationDate =
        updateDate =
        updaterName =
        managerName =
        creatorName =
        volPath =
            new String();
        
        listAsociated = new String[5];
    }

    
    
    /**
     * @return Returns the volPath.
     */
    public String getVolPath() {
        return volPath;
    }
    /**
     * @param volPath The volPath to set.
     */
    public void setVolPath(String volPath) {
        this.volPath = volPath;
    }
    /**
     * @return Returns the listAsociated.
     */
    public String[] getListAsociated() {
        return listAsociated;
    }
    /**
     * @param listAsociated The listAsociated to set.
     */
    public void setListAsociated(String[] listAsociated) {
        this.listAsociated = listAsociated;
    }
    /**
     * @return Returns the idRep.
     */
    public int getIdRep() {
        return idRep;
    }
    /**
     * @param idRep The idRep to set.
     */
    public void setIdRep(int idRep) {
        this.idRep = idRep;
    }
    /**
     * @return Returns the actSize.
     */
    public String getActSize() {
        return actSize;
    }
    /**
     * @param actSize The actSize to set.
     */
    public void setActSize(String actSize) {
        this.actSize = actSize;
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
     * @return Returns the description.
     */
    public String getDescription() {
        return description;
    }
    /**
     * @param description The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
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
     * @return Returns the maxSize.
     */
    public String getMaxSize() {
        return maxSize;
    }
    /**
     * @param maxSize The maxSize to set.
     */
    public void setMaxSize(String maxSize) {
        this.maxSize = maxSize;
    }
    /**
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }
    /**
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return Returns the numFiles.
     */
    public int getNumFiles() {
        return numFiles;
    }
    /**
     * @param numFiles The numFiles to set.
     */
    public void setNumFiles(int numFiles) {
        this.numFiles = numFiles;
    }
    /**
     * @return Returns the path.
     */
    public String getPath() {
        return path;
    }
    /**
     * @param path The path to set.
     */
    public void setPath(String path) {
        this.path = path;
    }
    /**
     * @return Returns the repositoryName.
     */
    public String getRepositoryName() {
        return repositoryName;
    }
    /**
     * @param repositoryName The repositoryName to set.
     */
    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }
    /**
     * @return Returns the state.
     */
    public String[] getStates() {
        return states;
    }
    /**
     * @param state The state to set.
     */
    public void setStates(String[] states) {
        this.states = states;
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
}
