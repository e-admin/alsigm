package ieci.tdw.ispac.ispacpublicador.business.vo;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;

import org.apache.log4j.Logger;

/**
 * Clase base para todos los Value Objects.
 *
 */
public abstract class BaseVO implements Serializable {

	/** Logger de la clase. */
    protected Logger logger;
    
    /** Sufijo de los value objects. */
    public static final String VALUE_OBJECT_SUFFIX = "VO";
    

    /**
     * Constructor.
     *
     */
    public BaseVO() {
        logger = Logger.getLogger(getClass());
    }
    
    private String obtainPropertyName(String getterName) {
        if(getterName.startsWith("get")) {
            return getterName.substring("get".length());
        } else if(getterName.startsWith("is")) {
            return getterName.substring("is".length());
        } else {
            return "";
        }
    }

    private boolean isProperty(Method method) {
        return (method.getName().startsWith("get") 
        			|| method.getName().startsWith("is")) 
        		&& method.getParameterTypes().length == 0 
        		&& method.getDeclaringClass() != (java.lang.Object.class);
    }

    private String processPropertyValue(Object property, String name) {
        StringBuffer res = new StringBuffer();
        if(property.getClass().isArray()) {
            for(int i = 0; i < Array.getLength(property); i++) {
                res.append("<" + name + ">");
                res.append(Array.get(property, i));
                res.append("</" + name + ">");
            }
        } else if(property instanceof Collection) {
            for(Iterator it = ((Collection)property).iterator(); it.hasNext(); 
            		res.append("</" + name + ">")) {
                res.append("<" + name + ">");
                res.append(it.next());
            }
        } else {
            res.append(property);
        }
        return res.toString();
    }

    private boolean isMultiProperty(Object property) {
        return property.getClass().isArray() 
        			|| (property instanceof Collection);
    }

    private String processMethod(Method m) {
        if(isProperty(m)) {
            String name = obtainPropertyName(m.getName());
            Object property = null;
            
            try {
                property = m.invoke(this, new Object[0]);
            } catch(Exception e) {
                logger.warn("Error obteniendo valor de la propiedad " + name, e);
            }
            
            if(property != null) {
                String pName = name;
                if(isMultiProperty(property)) {
                    if(pName.endsWith("s")) {
                        pName = pName + "es";
                    } else {
                        pName = pName + "s";
                    }
                }
                StringBuffer xml = new StringBuffer("<" + pName + ">");
                xml.append(processPropertyValue(property, name));
                xml.append("</" + pName + ">");
                return xml.toString();
            }
        }
        return "";
    }

    private String getBeanName() {
        String className = getClass().getName();
        int pos = className.lastIndexOf('.');
        String beanName = className.substring(pos + 1, className.length());
        if(beanName.endsWith("VO")) {
            beanName = beanName.substring(0, beanName.length() - "VO".length());
        }
        return beanName;
    }

    public String toXML() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<" + getBeanName() + ">");
        Method methods[] = getClass().getMethods();
        for(int i = 0; i < methods.length; i++) {
            buffer.append(processMethod(methods[i]));
        }
        buffer.append("</" + getBeanName() + ">");
        return buffer.toString();
    }

    public String toString() {
        return toXML();
    }
    
}
