/*
 * Created on 05-may-2005
 *
 */
package ieci.tecdoc.mvc.dto.archive;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Antonio María
 *
 */
public class General {
    int id;
    String name ;
    String description;
    int managerId;
    
    
    String creationDate;
    String creatorName;
    
    String updateDate;
    String updaterName;
    
    List adminUsers;
    List volumeListCollection;
    
    boolean ftsInContents;
    
    // Miscelanea
    String volListType;
    List volListTypeChoices;
    int volListId;
    boolean existsFdrsInArch;
    
    public General()
    {
        super();
        volListTypeChoices = new ArrayList();
        adminUsers = new ArrayList();
        volumeListCollection = new ArrayList();
    }
    
    public boolean getExistsFdrsInArch()
    {
        return existsFdrsInArch;
    }
    public void setExistsFdrsInArch(boolean existsFdrsInArch)
    {
        this.existsFdrsInArch = existsFdrsInArch;
    }
    
    public boolean getFtsInContents()
    {
        return ftsInContents;
    }
    public void setFtsInContents(boolean ftsInContents)
    {
        this.ftsInContents = ftsInContents;
    }
    /**
     * @return Returns the volumeListCollection.
     */
    public List getVolumeListCollection() {
        return volumeListCollection;
    }
    /**
     * @param volumeListCollection The volumeListCollection to set.
     */
    public void setVolumeListCollection(List volumeListCollection) {
        this.volumeListCollection = volumeListCollection;
    }
    /**
     * @return Returns the volListId.
     */
    public int getVolListId() {
        return volListId;
    }
    /**
     * @param volListId The volListId to set.
     */
    public void setVolListId(int volListId) {
        this.volListId = volListId;
    }
    /**
     * @return Returns the volListType.
     */
    public String getVolListType() {
        return volListType;
    }
    /**
     * @param volListType The volListType to set.
     */
    public void setVolListType(String volListType) {
        this.volListType = volListType;
    }
    /**
     * @return Returns the volListTypeChoices.
     */
    public List getVolListTypeChoices() {
        return volListTypeChoices;
    }
    /**
     * @param volListTypeChoices The volListTypeChoices to set.
     */
    public void setVolListTypeChoices(List volListTypeChoices) {
        this.volListTypeChoices = volListTypeChoices;
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
    public String getCreationDate() {
        return creationDate;
    }
    
    public void setCreationDate(Date creationDate, SimpleDateFormat formatter) {
        if (creationDate != null)
            this.creationDate = formatter.format(creationDate );
       else
           this.creationDate = new String();
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
     * @return Returns the updateDate.
     */
    public String getUpdateDate() {
        return updateDate;
    }
    /**
     * @param updateDate The updateDate to set.
     */
    public void setUpdateDate(Date updateDate, SimpleDateFormat formatter) {
        if (updateDate != null )
            this.updateDate = formatter.format(updateDate );
        else
            this.updateDate = new String();
        
        
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
     * @return Returns the managerId.
     */
    public int getManagerId() {
        return managerId;
    }
    /**
     * @param managerId The managerId to set.
     */
    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }
    public String toString()
    {
        int id;
        String name ;
        String description ;
        
        String creationDate;
        String creatorName;
        
        String updateDate;
        String updaterName;
        String s = new String();
        s = 
        "<GENERAL>"+ '\n' +
        	"<ID>"+ getId() + "</ID>"+ '\n'+
        	"<NAME>" + getName() +"</NAME>" + '\n'+
        	"<DESCRIPTION>" + getDescription() + "<DESCRIPTION>" + '\n'+
        	"<CREATION_DATE>" + getCreationDate()+ "<CREATION_DATE>"+'\n' +
        	"<CREATOR_NAME>"+ getCreatorName()+ "<CREATOR_NAME>" + '\n'+
        	"<UPDATE_DATE>" + getUpdateDate() +"</UPDATE_DATE>" + '\n'+
        	"<UPDATER_NAME>"+ getUpdaterName()+ "</UPDATER_NAME>" + '\n' +
        "</GENERAL>" + '\n';
        return s;
    }
}
