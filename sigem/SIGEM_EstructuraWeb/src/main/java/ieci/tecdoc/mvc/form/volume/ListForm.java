/*
 * Created on 11-abr-2005
 *
 */
package ieci.tecdoc.mvc.form.volume;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * @author Antonio María
 *
 */
public class ListForm extends ActionForm{
    int id;
    String name;
    String type;
    String description;
    
    String creationDate;
    String updateDate;
    String updaterName;
    String managerName;
    String creatorName;
    
    public void reset(ActionMapping mapping, HttpServletRequest request){
        
        id = -1;
        name = 
        type =
        description = 
        
        creationDate = 
        updateDate=
        updaterName =
        managerName=
        creatorName =
        "";
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
     * @return Returns the type.
     */
    public String getType() {
        return type;
    }
    /**
     * @param type The type to set.
     */
    public void setType(String type) {
        this.type = type;
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
