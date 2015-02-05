package es.ieci.tecdoc.fwktd.ldap.core.utils;

import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.Filter;

import es.ieci.tecdoc.fwktd.ldap.core.constants.LdapConstants;

/**
 * Clase de utilidad para trabajar con filtros
 * @author Iecisa
 * @version $Revision: 79 $
 *
 */
public final class LdapFilterUtils {

    /**
     * Constructor privado
     */
    private LdapFilterUtils(){

    }

    /**
     * Procesa un string con clases separadas por , para devolver un array
     * @param objectClass string a procesar
     * @return array con las clases
     */
    private static String [] processObjectClasses(final String objectClass){
    	String [] objectClasses = null;
    	if (objectClass!=null){
    		objectClasses = objectClass.split(",");
    	}

    	if (objectClasses!=null){
    		String objClass = null;
    		for (int i=0;i<objectClasses.length;i++){
    			objClass = objectClasses[i];
    			if (objClass!=null){
    				objectClasses[i] = objClass.trim();
    			}
    		}
    	}

    	return objectClasses;
    }

    /**
     * Crea un filtro para un tipo concretos de clase y para un atributo con su valor
     * @param objectClass Clase sobre la que se aplica el filtro, pueden ser varias separadas por ','
     * @return Valor del filtro
     */
    public static Filter createFilterForObjectClass(final String objectClass){
    	String [] objectClasses = processObjectClasses(objectClass);
    	return createFilterForObjectClassAndFilter(objectClasses, null);
    }

    /**
     * Crea un filtro para un tipo concretos de clase y para un atributo con su valor
     * @param objectClass Clase sobre la que se aplica el filtro, pueden ser varias separadas por ','
     * @param attr Atributo sobre el que se aplica el filtro
     * @param value Valor del atributo
     * @return Valor del filtro
     */
    public static Filter createFilterForObjectClassAntAttrValue(final String objectClass, final String attr, final String value){
    	String [] objectClasses = processObjectClasses(objectClass);
    	return createFilterForObjectClassAndFilter(objectClasses, new EqualsFilter(attr, value));
    }

    /**
     * Crea un filtro para un tipo concreto de clase y otro filtro
     * @param objectClass Clase sobre la que se aplica el filtro, pueden ser varias separadas por ','
     * @param filter filtro que se aplica sobre los nodos
     * @return Valor del filtro
     */
    public static Filter createFilterForObjectClassAndFilter(final String objectClass, final Filter filter){
    	String [] objectClasses = processObjectClasses(objectClass);
    	return createFilterForObjectClassAndFilter(objectClasses, filter);
    }

    /**
     * Crea un filtro para unos tipos concretos de clase y otro filtro
     * @param objectClass Clase sobre la que se aplica el filtro
     * @param filter filtro que se aplica sobre los nodos
     * @return Valor del filtro
     */
    private static Filter createFilterForObjectClassAndFilter(final String [] objectClasses, final Filter filter){

    	AndFilter andFilter = new AndFilter();
    	if (objectClasses!=null){
    		for (int i=0;i<objectClasses.length;i++){
    			andFilter.and(new EqualsFilter(LdapConstants.OBJECT_CLASS_ATTRIBUTE, objectClasses[i]));
    		}
    	}

    	if (filter!=null){
    		andFilter.and(filter);
    	}
    	return andFilter;
    }

}
