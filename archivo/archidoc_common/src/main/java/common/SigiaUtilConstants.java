/*
 * Created on 04-may-2005
 *
 */
package common;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ABELRL
 *
 */
public class SigiaUtilConstants {

    public final static Map getConstantsMap(Class source) {
        Map contantsMap = new HashMap();
        Field[] fields = source.getDeclaredFields();
        int nFields = fields.length;
        for (int i = 0; i < nFields; i++) {
            if (Modifier.isStatic(fields[i].getModifiers()))
                try {
                    contantsMap.put(fields[i].getName(), fields[i].get(null));

                } catch (IllegalAccessException iae) {
                }
        }
        return contantsMap;
    }

}