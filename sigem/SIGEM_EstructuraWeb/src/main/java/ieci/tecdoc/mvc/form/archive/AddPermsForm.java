/*
 * Created on 12-may-2005
 *
 */
package ieci.tecdoc.mvc.form.archive;

import ieci.tecdoc.mvc.dto.archive.Permission;
import ieci.tecdoc.mvc.dto.common.AccessDTO;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * @author Antonio María
 *
 */
public class AddPermsForm extends ActionForm{
    private Permission permission;
    private String destName;
    private String archiveName;
    private int archiveId; 
    private int acsId;
    
    private AccessDTO accessDTO;
    
    private boolean showForm;
    
    
    
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        // super.reset(mapping, request);
        permission = new Permission();
        permission.setPermissionArray(new int[4]);
    }
    
    /**
     * @return Returns the accessDTO.
     */
    public AccessDTO getAccessDTO() {
        return accessDTO;
    }
    /**
     * @param accessDTO The accessDTO to set.
     */
    public void setAccessDTO(AccessDTO accessDTO) {
        this.accessDTO = accessDTO;
    }
    /**
     * @return Returns the acsId.
     */
    public int getAcsId() {
        return acsId;
    }
    /**
     * @param acsId The acsId to set.
     */
    public void setAcsId(int acsId) {
        this.acsId = acsId;
    }
    /**
     * @return Returns the archiveId.
     */
    public int getArchiveId() {
        return archiveId;
    }
    /**
     * @param archiveId The archiveId to set.
     */
    public void setArchiveId(int archiveId) {
        this.archiveId = archiveId;
    }
    public boolean getShowForm()
    {
        return showForm;
    }
    public void setShowForm(boolean showForm)
    {
        this.showForm = showForm;
    }

    /**
     * @return Returns the archiveName.
     */
    public String getArchiveName() {
        return archiveName;
    }
    /**
     * @param archiveName The archiveName to set.
     */
    public void setArchiveName(String archiveName) {
        this.archiveName = archiveName;
    }
    /**
     * @return Returns the permission.
     */
    public Permission getPermission() {
        return permission;
    }
    /**
     * @param permission The permission to set.
     */
    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    /**
     * @return Returns the destName.
     */
    public String getDestName() {
        return destName;
    }
    /**
     * @param destName The destName to set.
     */
    public void setDestName(String destName) {
        this.destName = destName;
    }
}
