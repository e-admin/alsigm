/*
 * invesflow Java - ISPAC
 * $Source: /TEST/SIGEM/ispaccatalog/src/ieci/tdw/ispac/ispaccatalog/action/form/UploadForm.java,v $
 * $Revision: 1.1 $
 * $Date: 2007/04/24 11:12:48 $
 * $Author: luismimg $
 *
 */
package ieci.tdw.ispac.ispaccatalog.action.form;

import org.apache.struts.upload.FormFile;

/**
 * UploadForm
 *
 *
 * @version $Revision: 1.1 $ $Date: 2007/04/24 11:12:48 $
 */
public class UploadForm extends EntityForm
{
    FormFile uploadFile;

    /**
     * @return Devuelve el valor de la propiedad uploadFile.
     */
    public FormFile getUploadFile()
    {
        return uploadFile;
    }
    /**
     * @param uploadFile Cambia el valor de la propiedad uploadFile.
     */
    public void setUploadFile(FormFile uploadFile)
    {
        this.uploadFile = uploadFile;
    }
}
