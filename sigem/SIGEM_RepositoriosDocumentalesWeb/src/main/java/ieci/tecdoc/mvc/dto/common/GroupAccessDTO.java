/*
 * Created on 13-may-2005
 *
 */
package ieci.tecdoc.mvc.dto.common;

import ieci.tecdoc.idoc.admin.internal.Defs;
import ieci.tecdoc.mvc.dto.archive.Permission;

/**
 * @author Antonio María
 *
 */
public class GroupAccessDTO implements AccessDTO {

    /* (non-Javadoc)
     * @see ieci.tecdoc.mvc.dto.common.AccessDTO#getId()
     */
    int id;
    String name;
    Permission permission; // Permisos sobre invesdoc
    
    public GroupAccessDTO()
    {
        permission = new Permission();
    }
    public int getId() {
        // TODO Auto-generated method stub
        return id;
    }

    /* (non-Javadoc)
     * @see ieci.tecdoc.mvc.dto.common.AccessDTO#setId(int)
     */
    public void setId(int id) {
        this.id = id;
        
    }

    /* (non-Javadoc)
     * @see ieci.tecdoc.mvc.dto.common.AccessDTO#getName()
     */
    public String getName() {
        // TODO Auto-generated method stub
        return name;
    }

    /* (non-Javadoc)
     * @see ieci.tecdoc.mvc.dto.common.AccessDTO#setName(java.lang.String)
     */
    public void setName(String name) {
        this.name = name;
        
    }

    /* (non-Javadoc)
     * @see ieci.tecdoc.mvc.dto.common.AccessDTO#getPermission()
     */
    public Permission getPermission() {
        return this.permission;
    }

    /* (non-Javadoc)
     * @see ieci.tecdoc.mvc.dto.common.AccessDTO#setPermission(ieci.tecdoc.idoc.admin.api.user.Permission)
     */
    public void setPermission(ieci.tecdoc.idoc.admin.api.user.Permission permission) {
        int perm = permission.getPermission();
        this.permission.setPerm(perm);
        
    }

    public int getType() {
        return Defs.DESTINATION_GROUP;
    }
    public boolean getIsSystemSuperUser() {
        return false;
    }

}
