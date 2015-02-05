/*
 * invesflow Java - ISPAC
 * $Source: /TEST/SIGEM/ispacweb/src/ieci/tdw/ispac/ispacweb/util/URLBuilder.java,v $
 * $Revision: 1.2 $
 * $Date: 2007/06/19 08:16:24 $
 * $Author: abelrl $
 *
 */
package ieci.tdw.ispac.ispacweb.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * URLBuilder
 *
 *
 * @version $Revision: 1.2 $ $Date: 2007/06/19 08:16:24 $
 */
public final class URLBuilder
{

    public static String buildURLParams(Map parameters,Set omitparams)
    {
        StringBuffer url = new StringBuffer();
        String key = null;

        Iterator it = parameters.keySet().iterator();
        while (it.hasNext())
        {
            key = (String) it.next();
            if (!omitparams.contains(key))
            {
                String[] values=(String[])parameters.get(key);
                for(int i=0;i<values.length;i++)
                {
	                if (url.length() != 0)
	                    url.append("&");
	                url.append(key).append("=").append(values[i]);
                }
            }
        }
        return url.toString();
    }
    
}
