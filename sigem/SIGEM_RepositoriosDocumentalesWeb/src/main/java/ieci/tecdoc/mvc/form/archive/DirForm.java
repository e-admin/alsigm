/*
 * Created on 17-may-2005
 *
 */
package ieci.tecdoc.mvc.form.archive;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * @author Antonio María
 *
 */
public class DirForm extends ActionForm{
    String name;
    int id;
    String description;
    
    List adminUsers;
    int adminUserId;
    
    String creationDate;
    String updateDate;
    String updaterName;
    String managerName;
    String creatorName;
    
	/* (non-Javadoc)
	 * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
    public void executeReset(ActionMapping arg0, HttpServletRequest arg1) {
    // TODO Auto-generated method stub
        name = 
        description = 
        creationDate = 
        updateDate = 
        updaterName = 
        managerName = 
        creatorName  =  new String();            
    }
    
    public void setCreationDate(Date creationDate, SimpleDateFormat formatter) {
        this.creationDate = formatter.format(creationDate );
    }
    public void setUpdateDate(Date updateDate, SimpleDateFormat formatter) {
        if (updateDate != null)
            this.updateDate = formatter.format(updateDate );
        else
            this.updateDate = new String();
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
     * @return Returns the adminUserId.
     */
    public int getAdminUserId() {
        return adminUserId;
    }
    /**
     * @param adminUserId The adminUserId to set.
     */
    public void setAdminUserId(int adminUserId) {
        this.adminUserId = adminUserId;
    }
    /**
     * @return Returns the adminUsers.
     */
    public List getAdminUsers() {
        return adminUsers;
    }
    /**
     * @param adminUsers The adminUsers to set.
     */
    public void setAdminUsers(List adminUsers) {
        this.adminUsers = adminUsers;
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
}
