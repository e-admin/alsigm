/*
 * Created on 05-may-2005
 *
 */
package ieci.tecdoc.mvc.form.archive;


import ieci.tecdoc.mvc.dto.archive.Advanced;
import ieci.tecdoc.mvc.dto.archive.Fields;
import ieci.tecdoc.mvc.dto.archive.Folder;
import ieci.tecdoc.mvc.dto.archive.General;
import ieci.tecdoc.mvc.dto.archive.Indexs;
import ieci.tecdoc.mvc.dto.archive.Permission;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;

/**
 * @author Antonio María
 *
 */
public class ArchiveForm extends ActionForm{
    General general;
    Fields fields;
    Permission permission;
    Advanced advanced;
    Indexs indexs;
    Folder folder;
    boolean hasFtsConfig;
    
    private static Logger logger = Logger.getLogger(ArchiveForm.class);

    /*
    public ArchiveForm(){
        if (logger.isDebugEnabled())
            logger.debug("Constructor de ArchiveForm");
        general = new General();
        fields = new Fields();
        permission = new Permission();
        advanced = new Advanced ();
        indexs = new Indexs();
        folder = new Folder();
    }
    */
    public void executeReset()
    {
        if (logger.isDebugEnabled())
            logger.debug("ExecuteReset en ArchiveForm");
        
        general = new General();
        fields = new Fields();
        permission = new Permission();
        advanced = new Advanced ();
        indexs = new Indexs();
        folder = new Folder();
        hasFtsConfig = false;
    }
    
    public boolean getHasFtsConfig()
    {
        return hasFtsConfig;
    }
    public void setHasFtsConfig(boolean hasFtsConfig)
    {
        this.hasFtsConfig = hasFtsConfig;
    }
    

    /**
     * @return Returns the folder.
     */
    public Folder getFolder() {
        return folder;
    }
    /**
     * @param folder The folder to set.
     */
    public void setFolder(Folder folder) {
        this.folder = folder;
    }
    /**
     * @return Returns the indexs.
     */
    public Indexs getIndexs() {
        return indexs;
    }
    /**
     * @param indexs The indexs to set.
     */
    public void setIndexs(Indexs indexs) {
        this.indexs = indexs;
    }
    /**
     * @return Returns the advanced.
     */
    public Advanced getAdvanced() {
        return advanced;
    }
    /**
     * @param advanced The advanced to set.
     */
    public void setAdvanced(Advanced advanced) {
        this.advanced = advanced;
    }
    /**
     * @return Returns the folders.
     */
    public Fields getFields() {
        return fields ;
    }
    /**
     * @param folders The folders to set.
     */
    public void setFields(Fields  fields ) {
        this.fields  = fields ;
    }
    /**
     * @return Returns the general.
     */
    public General getGeneral() {
        return general;
    }
    /**
     * @param general The general to set.
     */
    public void setGeneral(General general) {
        this.general = general;
    }
    /**
     * @return Returns the permission.
     */
    public Permission getPermission() {
        return permission;
    }
    /**
     * @param permission The permission to set.
     */
    public void setPermission(Permission permission) {
        this.permission = permission;
    }
}
