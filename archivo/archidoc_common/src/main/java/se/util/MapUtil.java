/*
 * Created on 19-abr-2005
 *
 */
package se.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author ABELRL
 *
 */
public class MapUtil {
    public static List toList(Map map){
        ArrayList ret = new ArrayList();
        Collection allInstituciones = map.values();
        for (Iterator iter = allInstituciones.iterator(); iter.hasNext();) {
            ret.add(iter.next());
        }
        return ret!=null?ret:null;
        
    }

}
