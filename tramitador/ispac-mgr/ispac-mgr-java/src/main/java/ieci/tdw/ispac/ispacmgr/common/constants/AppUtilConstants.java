package ieci.tdw.ispac.ispacmgr.common.constants;



import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class AppUtilConstants {

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