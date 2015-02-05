package ieci.tdw.ispac.ispaclib.events;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.bean.ObjectBean;

/**
 * DescEventsBean
 *
 */
public class DescEventsBean extends ObjectBean
{

    private static final String ATTR_ID = "ID";
    private static final String ATTR_DESCRIPTION = "DESCRIPCION";

    private Integer mid;

    public DescEventsBean(Integer id)
    {
        mid=id;
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.bean.ObjectBean#getProperty(java.lang.String)
     */
    public Object getProperty(String keyname) throws ISPACException
    {
        if (keyname.equalsIgnoreCase(DescEventsBean.ATTR_ID))
            return mid;
        else if (keyname.equalsIgnoreCase(DescEventsBean.ATTR_DESCRIPTION))
            return DescriptionsPEvents.getDescEventsMap().get(mid);
        return null;
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.bean.ObjectBean#setProperty(java.lang.String, java.lang.Object)
     */
    public void setProperty(String key, Object value) throws ISPACException
    {
    }
}
