/*
 * DocumentoInfo.java
 *
 * Created on 12 de julio de 2007, 12:24
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ieci.tecdoc.sgm.nt.bean;

/**
 *
 * @author X73994NA
 */
public final class DocumentoInfo extends GenericoBean{
    
    
    private byte[] content;
    private String  extension;
    private String  guid;
    private String  mimeType;
        
    /** Creates a new instance of DocumentoInfo */
    public DocumentoInfo() {       
    }
    
    public DocumentoInfo(ieci.tecdoc.sgm.rde.database.DocumentoInfo rdeBeanType_) {
        
        if ( rdeBeanType_ != null ){
            setContent(rdeBeanType_.getContent());
            setExtension(rdeBeanType_.getExtension());
            setGuid(rdeBeanType_.getGuid());
            setMimeType(rdeBeanType_.getMimeType());
        }  
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
    
}
