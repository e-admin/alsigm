/*
 * Created on 13-may-2005
 *
 */
package ieci.tecdoc.mvc.dto.common;

import ieci.tecdoc.idoc.admin.api.user.UserDefs;
import ieci.tecdoc.idoc.admin.api.user.UserProfile;
import ieci.tecdoc.idoc.admin.internal.Defs;
import ieci.tecdoc.mvc.dto.archive.Permission;
import ieci.tecdoc.sbo.acs.base.AcsPermission;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Antonio María
 *
 */
public class UserAccessDTO implements AccessDTO {
    Permission permission; // Permisos sobre invesdoc
    int id;
    int type;
    String name;
    Map profiles;
    boolean isSystemSuperUser;
    
    public UserAccessDTO()
    {
        permission = new Permission();
        profiles = new HashMap();
    }
    
    public boolean getIsSystemSuperUser() {
        return isSystemSuperUser;
    }
    /**
     * @param systemSuperUser The systemSuperUser to set.
     */
    public void setIsSystemSuperUser(boolean systemSuperUser) {
        isSystemSuperUser = systemSuperUser;
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
    public void setProfiles(ieci.tecdoc.idoc.admin.api.user.UserProfiles profiles ) throws Exception
    {
        UserProfile profile = profiles.getProductProfile(UserDefs.PRODUCT_SYSTEM);
        
        if(profile.getProfile()==UserDefs.PROFILE_SUPERUSER ){
            this.profiles.put(String.valueOf(UserDefs.PRODUCT_IDOC), new Integer(UserDefs.PROFILE_SUPERUSER) );
            this.profiles.put(String.valueOf(UserDefs.PRODUCT_VOLUME), new Integer(UserDefs.PROFILE_SUPERUSER) ) ;
            this.profiles.put(String.valueOf(UserDefs.PRODUCT_USER), new Integer(UserDefs.PROFILE_SUPERUSER) );
            this.isSystemSuperUser = true;
            this.permission.setPerm(AcsPermission.ALL);
        }
        else
        {
            profile = profiles.getProductProfile(UserDefs.PRODUCT_USER);
	        this.profiles.put(String.valueOf(UserDefs.PRODUCT_USER), new Integer(profile.getProfile()));
	        
	        profile=profiles.getProductProfile(UserDefs.PRODUCT_IDOC);
	        this.profiles.put(String.valueOf(UserDefs.PRODUCT_IDOC), new Integer(profile.getProfile()));
	        
	        if ( getIdocProfileSuperuser() || getIdocProfileManager() )
	            this.permission.setPerm(AcsPermission.ALL);
	        
	        profile=profiles.getProductProfile(UserDefs.PRODUCT_VOLUME);
	        this.profiles.put(String.valueOf(UserDefs.PRODUCT_VOLUME), new Integer(profile.getProfile()));
	        
        }
    }
    
    
    public Map getProfiles() {
        return profiles;
    }
    
    public Integer getProfile(String idProduct)
    {
        Integer r = (Integer)profiles.get(idProduct);
        return  r;
    }
    public boolean getUserProfileSuperuser()
    {
        boolean r = false;
        Integer profile = (Integer) profiles.get( String.valueOf(UserDefs.PRODUCT_USER));
        if (profile.intValue() == UserDefs.PROFILE_SUPERUSER )
            r = true;
        return r;
    }
    public boolean getIdocProfileSuperuser()
    {
        boolean r = false;
        Integer profile = (Integer) profiles.get( String.valueOf(UserDefs.PRODUCT_IDOC));
        if (profile.intValue() == UserDefs.PROFILE_SUPERUSER ){
            r = true;
        }
        return r;
    }
    public boolean getIdocProfileManager()
    {
        boolean r = false;
        Integer profile = (Integer) profiles.get( String.valueOf(UserDefs.PRODUCT_IDOC));
        if (profile.intValue() == UserDefs.PROFILE_MANAGER ){
            r = true;
        }
        return r;
    }
    
    public Integer getIdocProfile()
    {
        return (Integer) profiles.get( String.valueOf(UserDefs.PRODUCT_IDOC));
    }
    public Integer getUserProfile()
    {
        return (Integer) profiles.get( String.valueOf(UserDefs.PRODUCT_USER));
    }
    public Integer getVolProfile()
    {
        return (Integer) profiles.get( String.valueOf(UserDefs.PRODUCT_VOLUME));
    }
    /**
     * @return Returns the permission.
     */
    public Permission getPermission() {
        return this.permission;
    }
    /**
     * 
     * @param permissions Permisos sobre invesdoc
     */
    public void setPermission(ieci.tecdoc.idoc.admin.api.user.Permission permission )
    {
        int perm = permission.getPermission();
        this.permission.setPerm(perm);
    }
    /**
     * @return Returns the type.
     */
    public int getType() {
        return Defs.DESTINATION_USER;
    }
    
}
