/*
 * Created on 01-abr-2005
 *
 */
package ieci.tecdoc.mvc.dto.adminUser.bd;

import java.util.Collection;

/**
 * @author Antonio María
 *
 */
public class GroupListDTO {
    Collection grupos;
    

    /**
     * @return Returns the grupos.
     */
    public Collection getGrupos() {
        return grupos;
    }
    /**
     * @param grupos The grupos to set.
     */
    public void setGrupos(Collection grupos) {
        this.grupos = grupos;
    }
}
