/*
 * invesflow Java - ISPAC
 * $Source: /TEST/SIGEM/ispacweb/src/ieci/tdw/ispac/ispacweb/menu/BeanResourceFilter.java,v $
 * $Revision: 1.3 $
 * $Date: 2008/09/29 08:23:09 $
 * $Author: luismimg $
 *
 */
package ieci.tdw.ispac.ispacweb.menu;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.bean.ActionBean;
import ieci.tdw.ispac.ispaclib.bean.ObjectBean;

import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;

import org.apache.struts.util.MessageResources;

public class BeanResourceFilter
{

    public static void translateActionKeys(Collection actionbeans, Locale locale, 
    		MessageResources resources) throws ISPACException {
        BeanResourceFilter.translateKeys(actionbeans, locale, resources,
        		ActionBean.TITLE);
    }

    public static void translateKeys(Collection beans, Locale locale,
    		MessageResources resources, String property) throws ISPACException {
        Iterator it = beans.iterator();
        ObjectBean bean;
        while (it.hasNext()) {
            bean = (ObjectBean) it.next();
            bean.setProperty(property,
            		resources.getMessage(locale, (String)bean.getProperty(property)));
        }
    }
}
