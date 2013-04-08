/*
 * Created on 21-abr-2005
 *
 */
package ieci.tecdoc.mvc.dto.access;

/**
 * @author Antonio María
 *
 */
public class Product {
    int id;
    int profile;
    
    public Product (int id, int profile)
    {
        this.id = id;
        this.profile = profile;
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
     * @return Returns the profile.
     */
    public int getProfile() {
        return profile;
    }
    /**
     * @param profile The profile to set.
     */
    public void setProfile(int profile) {
        this.profile = profile;
    }
}
