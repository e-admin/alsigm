/*
 * Created on 05-may-2005
 *
 */
package ieci.tecdoc.mvc.dto.archive;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Antonio María
 *
 */
public class Fields {
    private List FldsList;
    private List ieciTdTypeChoices;
    
    public Fields()
    {
        super();
        FldsList = new ArrayList();
        ieciTdTypeChoices = new ArrayList();
    }
    
    /**
     * @return Returns the fldsList.
     */
    public List getFldsList() {
        return FldsList;
    }
    /**
     * @param fldsList The fldsList to set.
     */
    public void setFldsList(List fldsList) {
        FldsList = fldsList;
    }
    /**
     * @return Returns the ieciTdTypeChoices.
     */
    public List getIeciTdTypeChoices() {
        return ieciTdTypeChoices;
    }
    /**
     * @param ieciTdTypeChoices The ieciTdTypeChoices to set.
     */
    public void setIeciTdTypeChoices(List ieciTdTypeChoices) {
        this.ieciTdTypeChoices = ieciTdTypeChoices;
    }
}
