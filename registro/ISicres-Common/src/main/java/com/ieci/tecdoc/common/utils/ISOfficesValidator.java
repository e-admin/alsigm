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
import com.ieci.tecdoc.common.invesicres.ScrOfic;
import com.ieci.tecdoc.common.keys.HibernateKeys;

/**
 * @author MANUEL TAVARES
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ISOfficesValidator implements HibernateKeys {

	public static ScrOfic getOffice(Session session, String code) 
		throws HibernateException, ValidationException {
        
		StringBuffer query = new StringBuffer();
		Object[] arrObj = new Object[]{code};
		Type[] arrType = new Type[]{Hibernate.STRING};
        
        query.append("FROM " + HIBERNATE_ScrOfic + " scr WHERE scr.code = ?");
        
        return getOffice(session, query.toString(), arrObj, arrType);
    }
	
	
	public static ScrOfic getOfficeById(Session session, Integer id) 
		throws HibernateException, ValidationException {
        
		StringBuffer query = new StringBuffer();
		Object[] arrObj = new Object[]{id};
		Type[] arrType = new Type[]{Hibernate.INTEGER};
        
		query.append("FROM " + HIBERNATE_ScrOfic + " scr WHERE scr.id = ?");
		
		return getOffice(session, query.toString(), arrObj, arrType);
    }

	
	private static ScrOfic getOffice (Session session, String query, Object[] arrayObj, Type[] arrayType) 
		throws HibernateException, ValidationException {
    
		ScrOfic scr = null;
		List list = session.find(query, arrayObj, arrayType);

		if (list != null && !list.isEmpty()) {
			scr = (ScrOfic) list.get(0);
		} else {
			throw new ValidationException(ValidationException.ERROR_ATTRIBUTE_NOT_FOUND);
		}

		return scr;
	}
}
