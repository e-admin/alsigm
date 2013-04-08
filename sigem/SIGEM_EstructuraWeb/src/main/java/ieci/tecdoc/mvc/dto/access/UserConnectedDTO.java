/*
 * Created on 21-abr-2005
 *
 */
package ieci.tecdoc.mvc.dto.access;

import ieci.tecdoc.idoc.admin.api.user.UserDefs;

import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

/**
 * @author Antonio María
 *
 */
public class UserConnectedDTO {
    int id;
    String userName;
    
    boolean hasAccessUser;
    boolean hasAccessVol;
    boolean hasAccessSys;
    boolean isSystemXSuperUser;
    boolean isSystemSuperUser;
    Map profiles;
    //Bag dept;
    
    private static final   Logger logger       = Logger.getLogger(UserConnectedDTO.class);
    
    public UserConnectedDTO() {}
    public UserConnectedDTO(int id, String userName)
    {
        this.id = id;
        this.userName = userName;
        profiles = new TreeMap();
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
     * @return Returns the xSystemSuperUser.
     */
    public boolean getIsSystemXSuperUser() {
        return isSystemXSuperUser;
    }
    /**
     * @param systemSuperUser The xSystemSuperUser to set.
     */
    public void setIsSystemXSuperUser(boolean systemXSuperUser) {
        isSystemXSuperUser = systemXSuperUser;
    }
    public void setProfiles(Map profiles)
    {
        this.profiles = profiles;
    }
    
    /**
     * @return Returns the profiles.
     */
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
     * @return Returns the hasAccessArch.
     */
    public boolean getHasAccessSys() {
        return hasAccessSys;
    }
    /**
     * @param hasAccessArch The hasAccessArch to set.
     */
    public void setHasAccessSys(boolean hasAccessSys) {
        this.hasAccessSys= hasAccessSys;
    }
    /**
     * @return Returns the hasAccessUser.
     */
    public boolean getHasAccessUser() {
        return hasAccessUser;
    }
    /**
     * @param hasAccessUser The hasAccessUser to set.
     */
    public void setHasAccessUser(boolean hasAccessUser) {
        this.hasAccessUser = hasAccessUser;
    }
    /**
     * @return Returns the hasAccessVol.
     */
    public boolean getHasAccessVol() {
        return hasAccessVol;
    }
    /**
     * @param hasAccessVol The hasAccessVol to set.
     */
    public void setHasAccessVol(boolean hasAccessVol) {
        this.hasAccessVol = hasAccessVol;
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
     * @return Returns the userName.
     */
    public String getUserName() {
        return userName;
    }
    /**
     * @param userName The userName to set.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String toString(){
        return id + ": " + userName;
    }
}