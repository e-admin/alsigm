/*
 * Created on 30-dic-2004
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
import com.ieci.tecdoc.common.invesicres.ScrOrg;
import com.ieci.tecdoc.common.keys.HibernateKeys;

/**
 * @author MANUEL TAVARES
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ISUnitsValidator implements HibernateKeys {

	public static ScrOrg getEntReg(Session session, String code, boolean onlyEnabled) 
		throws HibernateException, ValidationException {
        
		StringBuffer query = new StringBuffer();
		Object[] arrObj = new Object[]{code};
		Type[] arrType = new Type[]{Hibernate.STRING};
		ScrOrg result = null;
        
		query.append("FROM " + HIBERNATE_ScrOrg + " scr WHERE scr.code = ? ");
        
        if (onlyEnabled){
        	query.append(" AND scr.enabled = 1");
        }
        try{
        	result = getOrg(session, query.toString(), arrObj, arrType);
        } catch(ValidationException e){}
        
		if (result == null){
			query = new StringBuffer(); 
			query.append("FROM " + HIBERNATE_ScrOrg + " scr WHERE scr.acron = ?");
	        
	        if (onlyEnabled){
	        	query.append(" AND scr.enabled = 1");
	        }
	        result = getOrg(session, query.toString(), arrObj, arrType);
		}
        
        return result;
    }
		
	
	public static ScrOrg getUnit(Session session, String code, boolean onlyEnabled, List privOrgs) 
		throws HibernateException, ValidationException {
    
		StringBuffer query = new StringBuffer();
		Object[] arrObj = new Object[]{code};
		Type[] arrType = new Type[]{Hibernate.STRING};
		ScrOrg result = null;
		
		query.append("FROM " + HIBERNATE_ScrOrg + " scr WHERE scr.code = ? AND scr.scrTypeadm.id != 0");
		
		if (onlyEnabled){
			query.append(" AND scr.enabled = 1");
		}
        try{
        	result = getOrg(session, query.toString(), arrObj, arrType);
        } catch(ValidationException e){}
		
		if (result == null){
			query = new StringBuffer();
			query.append("FROM " + HIBERNATE_ScrOrg + " scr WHERE scr.acron = ? AND scr.scrTypeadm.id != 0");
			if (onlyEnabled){
				query.append(" AND scr.enabled = 1");
			}
	        result = getOrg(session, query.toString(), arrObj, arrType);
		}
		if (result != null){
			if (privOrgs != null && !privOrgs.isEmpty()){
				if (privOrgs.contains(result.getId())){
					throw new ValidationException(ValidationException.ERROR_ATTRIBUTE_NOT_FOUND);
				} else {
					Integer father = result.getIdFather();
					while (father != null){
						if (privOrgs.contains(father)){
							throw new ValidationException(ValidationException.ERROR_ATTRIBUTE_NOT_FOUND);
						} else {
							try{
								father = ((ScrOrg) session.load(ScrOrg.class, new Integer(father.intValue()))).getIdFather();
							} catch (HibernateException e){
								father = null;
							}
						}
					}
				}
	        }
		}
		return result;
	}
	
	
	public static ScrOrg getEntRegById(Session session, Integer id) 
		throws HibernateException, ValidationException {
        
		StringBuffer query = new StringBuffer();
		Object[] arrObj = new Object[]{id};
		Type[] arrType = new Type[]{Hibernate.INTEGER};

		query.append("FROM " + HIBERNATE_ScrOrg + " scr WHERE scr.id=?");
		
		return getOrg(session, query.toString(), arrObj, arrType);
    }
	
	
	public static ScrOrg getUnitById(Session session, Integer id) 
		throws HibernateException, ValidationException {
        
		StringBuffer query = new StringBuffer();
		Object[] arrObj = new Object[]{id};
		Type[] arrType = new Type[]{Hibernate.INTEGER};
        
		query.append("FROM " + HIBERNATE_ScrOrg + " scr WHERE scr.id=? AND scr.scrTypeadm.id != 0");
		
		return getOrg(session, query.toString(), arrObj, arrType);
    }
	
	
	private static ScrOrg getOrg(Session session, String query, Object[] arrayObj, Type[] arrayType) 
		throws HibernateException, ValidationException {
        
		ScrOrg scr = null;
        List list = session.find(query, arrayObj, arrayType);

        if (list != null && !list.isEmpty()) {
            scr = (ScrOrg) list.get(0);
        } else {
            throw new ValidationException(ValidationException.ERROR_ATTRIBUTE_NOT_FOUND);
        }

        return scr;
    }
}
