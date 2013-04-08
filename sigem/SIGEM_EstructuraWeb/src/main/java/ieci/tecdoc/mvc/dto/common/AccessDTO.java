/*
 * Created on 13-may-2005
 *
 */
package ieci.tecdoc.mvc.dto.common;

import ieci.tecdoc.mvc.dto.archive.Permission;

/**
 * @author Antonio María
 *
 */
public interface AccessDTO {
    
    public int getId();
    public void setId(int id);
    public String getName();
    public void setName(String name);
    public Permission getPermission();
    public void setPermission(ieci.tecdoc.idoc.admin.api.user.Permission permission);
    public int getType();
}
