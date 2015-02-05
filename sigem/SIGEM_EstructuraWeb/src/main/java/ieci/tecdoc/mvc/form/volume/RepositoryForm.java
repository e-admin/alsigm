/*
 * Created on 13-abr-2005
 *
 */
package ieci.tecdoc.mvc.form.volume;

import ieci.tecdoc.idoc.admin.api.volume.VolumeDefs;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * @author Antonio María
 *
 */
public class RepositoryForm extends ActionForm{
    int id;
    String name;
    String type;
    String description;
    
    String creationDate;
    String updateDate;
    String updaterName;
    String managerName;
    String creatorName;
    
    String path;
    String states[];
    
    String os;
    String server;
    int typeServer;
    int port;
    String user;
    String pwd;
    
    String states_[];
    
    
    public String[] getStates_() {
        return states_;
    }
    public void setStates_(String[] states_) {
        this.states_ = states_;
    }
    public void reset(ActionMapping mapping, HttpServletRequest request){
        
        id = -1;
        name = 
        type =
        description = 
        
        creationDate = 
        updateDate=
        updaterName =
        managerName=
        creatorName =
        path = "";
        states = new String[2];
        states[0] = String.valueOf(VolumeDefs.REP_STAT_OFFLINE); // Disponible por defecto
        
        port = 21;
        
        typeServer = -1;
        os =
        server = 
        user = 
        pwd = "";
    }

    /**
     * @return Returns the typeServer.
     */
    public int getTypeServer() {
        return typeServer;
    }
    /**
     * @param typeServer The typeServer to set.
     */
    public void setTypeServer(int typeServer) {
        this.typeServer = typeServer;
    }
    /**
     * @return Returns the server.
     */
    public String getServer() {
        return server;
    }
    /**
     * @param server The server to set.
     */
    public void setServer(String server) {
        this.server = server;
    }
    /**
     * @return Returns the os.
     */
    public String getOs() {
        return os;
    }
    /**
     * @param os The os to set.
     */
    public void setOs(String os) {
        this.os = os;
    }
    /**
     * @return Returns the port.
     */
    public int getPort() {
        return port;
    }
    /**
     * @param port The port to set.
     */
    public void setPort(int port) {
        this.port = port;
    }
    /**
     * @return Returns the pwd.
     */
    public String getPwd() {
        return pwd;
    }
    /**
     * @param pwd The pwd to set.
     */
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    /**
     * @return Returns the user.
     */
    public String getUser() {
        return user;
    }
    /**
     * @param user The user to set.
     */
    public void setUser(String user) {
        this.user = user;
    }
    
    /**
     * @return Returns the creationDate.
     */
    public String getCreationDate() {
        return creationDate;
    }
    /**
     * @param creationDate The creationDate to set.
     */
    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
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
     * @return Returns the managerName.
     */
    public String getManagerName() {
        return managerName;
    }
    /**
     * @param managerName The managerName to set.
     */
    public void setManagerName(String managerName) {
        this.managerName = managerName;
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
     * @return Returns the path.
     */
    public String getPath() {
        return path;
    }
    /**
     * @param path The path to set.
     */
    public void setPath(String path) {
        this.path = path;
    }
    /**
     * @return Returns the states.
     */
    public String[] getStates() {
        return states;
    }
    /**
     * @param states The states to set.
     */
    public void setStates(String[] states) {
        this.states = states;
    }
    /**
     * @return Returns the type.
     */
    public String getType() {
        return type;
    }
    /**
     * @param type The type to set.
     */
    public void setType(String type) {
        this.type = type;
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
    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
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
}
