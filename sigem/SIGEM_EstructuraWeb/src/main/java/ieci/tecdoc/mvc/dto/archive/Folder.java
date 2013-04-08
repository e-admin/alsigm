/*
 * Created on 10-may-2005
 *
 */
package ieci.tecdoc.mvc.dto.archive;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Antonio María
 *
 */
public class Folder {
    String fdrName; // Anexo: @FLD(-2)
    List fldsIdChoices;
    
    public Folder()
    {
        super();
        fldsIdChoices = new ArrayList();
    }
    /**
     * @return Returns the fldsIdChoices.
     */
    public List getFldsIdChoices() {
        return fldsIdChoices;
    }
    /**
     * @param fldsIdChoices The fldsIdChoices to set.
     */
    public void setFldsIdChoices(List fldsIdChoices) {
        this.fldsIdChoices = fldsIdChoices;
    }
    /**
     * @return Returns the fdrName.
     */
    public String getFdrName() {
        return fdrName;
    }
    /**
     * @param fdrName The fdrName to set.
     */
    public void setFdrName(String fdrName) {
        this.fdrName = fdrName;
    }
}
