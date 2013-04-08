/*
 * Created on 05-may-2005
 *
 */
package ieci.tecdoc.mvc.dto.archive;


/**
 * @author Antonio María
 *
 */
public class Advanced {
    private String adminUserName;
    private String volListName;
    private String volListType;
    
    /**
     * @return Returns the adminUserName.
     */
    public Advanced()
    {
        super();
    }
    public String getAdminUserName() {
        return adminUserName;
    }
    /**
     * @param adminUserName The adminUserName to set.
     */
    public void setAdminUserName(String adminUserName) {
        this.adminUserName = adminUserName;
    }
    
    /**
     * @return Returns the volListName.
     */
    public String getVolListName() {
        return volListName;
    }
    /**
     * @param volListName The volListName to set.
     */
    public void setVolListName(String volListName) {
        this.volListName = volListName;
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
}
