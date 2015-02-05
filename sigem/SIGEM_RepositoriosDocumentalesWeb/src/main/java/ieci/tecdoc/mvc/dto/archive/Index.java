/*
 * Created on 06-may-2005
 *
 */
package ieci.tecdoc.mvc.dto.archive;

/**
 * @author Antonio María
 *
 */
public class Index {
    private int id;
    private String name;
    private boolean isUnique;
    private String [] fldsId;
    
    private boolean isNew;
    private boolean isUpdate;
    public Index(){
        fldsId = new String[0];
    }
    
    public boolean getIsUpdate()
    {
        return isUpdate;
    }
    public void setIsUpdate (boolean isUpdate)
    {
        this.isUpdate = isUpdate;
    }
    
    public boolean getIsNew()
    {
        return isNew;
    }
    public void setIsNew(boolean isNew)
    {
        this.isNew = isNew;
    }
    public void setIsUnique(boolean isUnique)
    {
        this.isUnique = isUnique;
    }
    public boolean getIsUnique()
    {
        return isUnique;
    }
    
    /**
     * @return Returns the fldsId.
     */
    public String[] getFldsId() {
        return fldsId;
    }
    /**
     * @param fldsId The fldsId to set.
     */
    public void setFldsId(String[] fldsId) {
        this.fldsId = fldsId;
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
