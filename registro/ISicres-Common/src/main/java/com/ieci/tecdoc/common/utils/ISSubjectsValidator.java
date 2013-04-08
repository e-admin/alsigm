/*
 * Created on 03-ene-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ieci.tecdoc.common.utils;

import java.util.List;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.type.Type;

import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.common.invesicres.ScrCa;
import com.ieci.tecdoc.common.keys.HibernateKeys;

/**
 * @author MANUEL TAVARES
 *
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ISSubjectsValidator implements HibernateKeys {

    public static ScrCa getSubject(Session session, String code, boolean onlyEnabled) throws HibernateException,
            ValidationException {

        StringBuffer query = new StringBuffer();
        Object[] arrObj = new Object[] { code };
        Type[] arrType = new Type[] { Hibernate.STRING };

        query.append("FROM " + HIBERNATE_ScrCa + " scr WHERE scr.code = ?");

        if (onlyEnabled) {
            query.append(" AND scr.enabled = 1");
        }

        return getSubject(session, query.toString(), arrObj, arrType);
    }

    public static ScrCa getSubject(Session session, String code, boolean onlyEnabled, boolean forEreg)
            throws HibernateException, ValidationException {

        StringBuffer query = new StringBuffer();
        Object[] arrObj = new Object[] { code };
        Type[] arrType = new Type[] { Hibernate.STRING };

        query.append("FROM " + HIBERNATE_ScrCa + " scr WHERE scr.code = ?");

        if (onlyEnabled) {
            query.append(" AND scr.enabled = 1");
        }

        if (forEreg) {
            query.append(" AND scr.forEreg = 1");
        } else {
            query.append(" AND scr.forSreg = 1");
        }

        ScrCa ca = getSubject(session, query.toString(), arrObj, arrType);

        return ca;
    }

    /**
     * Obtiene un tipo de asunto para un código de oficina. Permitiendo añadir al filtro, los
     * tipos de asunto habilitados, y especificar si se aplica para los libros de entrada o salida
     * @param session Sesion
     * @param code Cadena que contiene el código de asunto
     * @param onlyEnabled Booleano que indica si sólo se devuelven los habilitados
     * @param forEreg Booleano que indica si el tipo es de entrada.
     * @param ofic Entero que contiene el identificador de la oficina
     * @return {@link ScrCa} Sólo si existe para las condiciones especificadas.
     * @throws HibernateException
     * @throws ValidationException
     */
    public static ScrCa getSubjectForOfic(Session session, String code, boolean onlyEnabled, boolean forEreg,
            Integer ofic) throws HibernateException, ValidationException {

    	return getSubjectForOfic(session, code, onlyEnabled, new Boolean(forEreg), ofic);

    }
    /**
     * Obtiene un tipo de asunto para una oficina determinada
     * @param session Sesion
     * @param code Cadena que contiene el código de asunto
     * @param ofic Entero que contiene el identificador de la oficina
     * @return {@link ScrCa} Sólo si existe para la oficina
     * @throws HibernateException
     * @throws ValidationException
     */
    public static ScrCa getSubjectForOfic(Session session, String code, Integer ofic) throws HibernateException {
    	try {
			return getSubjectForOfic(session, code, true, null, ofic);
		} catch (ValidationException e) {
			//Si no existe el tipo de asunto o no está asociado a una oficina se devuelve nulo.
			return null;
		}
    }

    /**
     * Obtiene un tipo de asunto para un código de oficina. Permitiendo añadir al filtro, los
     * tipos de asunto habilitados, y especificar si se aplica para los libros de entrada o salida
     * @param session Sesion
     * @param code Cadena que contiene el código de asunto
     * @param onlyEnabled Booleano que indica si sólo se devuelven los habilitados
     * @param forEreg Booleano que indica si el tipo es de entrada.
     * @param ofic Entero que contiene el identificador de la oficina
     * @return {@link ScrCa} Sólo si existe para las condiciones especificadas.
     * @throws HibernateException
     * @throws ValidationException
     */
    private static ScrCa getSubjectForOfic(Session session, String code, boolean onlyEnabled, Boolean forEreg,
            Integer ofic) throws HibernateException, ValidationException {

        StringBuffer query = new StringBuffer();
        Object[] arrObj = new Object[] { code };
        Type[] arrType = new Type[] { Hibernate.STRING };

        query.append("FROM " + HIBERNATE_ScrCa + " scr WHERE scr.code = ?");

        if (onlyEnabled) {
            query.append(" AND scr.enabled = 1");
        }

        if(forEreg != null){
	        if (forEreg.booleanValue()) {
	            query.append(" AND scr.forEreg = 1");
	        } else {
	            query.append(" AND scr.forSreg = 1");
	        }
        }

        ScrCa ca = getSubject(session, query.toString(), arrObj, arrType);

        if (ca.getAllOfics().intValue() == 0) {
            List list = session.find("FROM " + HIBERNATE_ScrCaofic + " scr WHERE scr.idMatter=? and scr.scrOfic.id=?",
                    new Integer[] { ca.getId(), ofic }, new Type[] { Hibernate.INTEGER, Hibernate.INTEGER });
            if (list != null && !list.isEmpty()) {
                return ca;
            } else {
                throw new ValidationException(ValidationException.ERROR_ATTRIBUTE_NOT_FOUND);
            }
        } else {
            return ca;
        }
    }

    public static ScrCa getSubjectById(Session session, Integer id) throws HibernateException, ValidationException {

        StringBuffer query = new StringBuffer();
        Object[] arrObj = new Object[] { id };
        Type[] arrType = new Type[] { Hibernate.INTEGER };

        query.append("FROM " + HIBERNATE_ScrCa + " scr WHERE scr.id = ?");

        return getSubject(session, query.toString(), arrObj, arrType);
    }

    private static ScrCa getSubject(Session session, String query, Object[] arrayObj, Type[] arrayType)
            throws HibernateException, ValidationException {

        ScrCa scr = null;
        List list = session.find(query, arrayObj, arrayType);

        if (list != null && !list.isEmpty()) {
            scr = (ScrCa) list.get(0);
        } else {
            throw new ValidationException(ValidationException.ERROR_ATTRIBUTE_NOT_FOUND);
        }

        return scr;
    }
}
