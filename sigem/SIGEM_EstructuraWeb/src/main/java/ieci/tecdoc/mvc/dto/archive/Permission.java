/*
 * Created on 05-may-2005
 *
 */
package ieci.tecdoc.mvc.dto.archive;

import ieci.tecdoc.mvc.service.adminUser.ServiceCommon;
import ieci.tecdoc.sbo.acs.base.AcsPermission;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Antonio María
 *
 */
public class Permission {
    
    private int permission[];
    private List permissionChoices;
    int perm;
    
    public Permission(){
        super();
        permissionChoices = new ArrayList();
    }

    /**
     * @return Returns the perm.
     */
    public int getPerm() {
        return perm;
    }
    /**
     * @param perm The perm to set.
     */
    public void setPerm(int perm) {
        this.permission = ServiceCommon.getPermmissionArray(perm);
        this.perm = perm;
    }
    /**
     * @return Returns the permission.
     */
    public int[] getPermissionArray() {
        return permission;
    }
    /**
     * @param permission The permission to set.
     */
    public void setPermissionArray(int[] permission) {
        this.permission = permission;
    }
    
    /**
     * @return Returns the permissionChoices.
     */
    public List getPermissionChoices() {
        return permissionChoices;
    }
    /**
     * @param permissionChoices The permissionChoices to set.
     */
    public void setPermissionChoices(List permissionChoices) {
        this.permissionChoices = permissionChoices;
    }
    
    public boolean getCanQuery(){
        return find (AcsPermission.QUERY);
    }
    public boolean getCanUpdate(){
        return find (AcsPermission.UPDATE);
    }
    public boolean getCanCreation(){
        return find (AcsPermission.CREATION);
    }
    public boolean getCanDeletion(){
        return find (AcsPermission.DELETION);
    }
    public boolean getCanPrinting(){
        return find (AcsPermission.PRINTING);
    }    
    	
    boolean find(int perm)
    {
        boolean success = false;
        int n = permission.length;
        int i = 0;
        while (!success && i < n)
        {
            if (permission[i] == perm )
                success = true;
            i++;
        }
        return success;
        
    }
}
