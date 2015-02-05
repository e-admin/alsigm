/*
 * Created on 11-abr-2005
 *
 */
package ieci.tecdoc.mvc.dto.volume;

/**
 * @author Antonio María
 *
 */
public class VolumeDTO {
    int id;
    String name;
    String maxSize;
    String actSize;
    int numFiles;
    String states;
    
    /**
     * @param id2
     * @param actSize2
     * @param maxSize2
     * @param name2
     * @param numFiles2
     * @param state2
     */
    public VolumeDTO(int id, String actSize, String maxSize, String name, int numFiles, String states) {
        this.id = id;
        this.actSize = actSize;
        this.maxSize = maxSize;
        this.name = name;
        this.numFiles = numFiles;
        this.states = states;
        
        // TODO Auto-generated constructor stub
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
     * @return Returns the actSize.
     */
    public String getActSize() {
        return actSize;
    }
    /**
     * @param actSize The actSize to set.
     */
    public void setActSize(String actSize) {
        this.actSize = actSize;
    }
    /**
     * @return Returns the maxSize.
     */
    public String getMaxSize() {
        return maxSize;
    }
    /**
     * @param maxSize The maxSize to set.
     */
    public void setMaxSize(String maxSize) {
        this.maxSize = maxSize;
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
     * @return Returns the numFiles.
     */
    public int getNumFiles() {
        return numFiles;
    }
    /**
     * @param numFiles The numFiles to set.
     */
    public void setNumFiles(int numFiles) {
        this.numFiles = numFiles;
    }

    /**
     * @return Returns the states.
     */
    public String getStates() {
        return states;
    }
    /**
     * @param states The states to set.
     */
    public void setStates(String states) {
        this.states = states;
    }
    public String toString()
    {
        return String.valueOf(id);
    }
}
