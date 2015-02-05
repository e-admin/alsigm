/*
 * Created on 06-may-2005
 *
 */
package ieci.tecdoc.mvc.dto.archive;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Antonio María
 *
 */
public class Indexs {
    List indexsList;
    
    List fldsIdChoices;
    
    public Indexs()
    {
        super();
        indexsList = new ArrayList();
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
     * @return Returns the indexsList.
     */
    public List getIndexsList() {
        return indexsList;
    }
    /**
     * @param indexsList The indexsList to set.
     */
    public void setIndexsList(List indexsList) {
        this.indexsList = indexsList;
    }
}
