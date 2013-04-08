package ieci.tdw.ispac.ispaccatalog.action.form;

import org.apache.struts.upload.FormFile;

/**
 * UploadForm para 2 ficheros.
 *
 */
public class Upload2Form extends UploadForm
{
    FormFile uploadFile2;

    /**
     * @return Devuelve el valor de la propiedad uploadFile2.
     */
    public FormFile getUploadFile2()
    {
        return uploadFile2;
    }
    /**
     * @param uploadFile Cambia el valor de la propiedad uploadFile2.
     */
    public void setUploadFile2(FormFile uploadFile2)
    {
        this.uploadFile2 = uploadFile2;
    }
    
}