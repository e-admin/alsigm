/*
 * Created on 30-dic-2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ieci.tecdoc.common.isicres;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.hibernate.Session;

import com.ieci.tecdoc.common.entity.dao.DBEntityDAOFactory;
import com.ieci.tecdoc.common.exception.AttributesException;
import com.ieci.tecdoc.common.exception.BookException;
import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.common.invesdoc.Iuserusertype;
import com.ieci.tecdoc.common.invesicres.ScrOfic;
import com.ieci.tecdoc.common.keys.HibernateKeys;
import com.ieci.tecdoc.common.keys.IDocKeys;
import com.ieci.tecdoc.common.utils.ISOfficesValidator;
import com.ieci.tecdoc.common.utils.ISSubjectsValidator;
import com.ieci.tecdoc.common.utils.ISUnitsValidator;
import com.ieci.tecdoc.common.utils.Repository;
import com.ieci.tecdoc.utils.HibernateUtil;
import com.ieci.tecdoc.utils.Validator;
import com.ieci.tecdoc.utils.cache.CacheBag;
import com.ieci.tecdoc.utils.cache.CacheFactory;

/**
 * @author MANUEL TAVARES
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ISFieldsValidator implements HibernateKeys {

	private static final int NUM_FIELDS = 5;

	private static final int EREG_FLD_OFIC = 5;
	private static final int EREG_FLD_SENDER = 7;
	private static final int EREG_FLD_DEST = 8;
	private static final int EREG_FLD_REGORIG = 13;
	private static final int EREG_FLD_MATTERTYPE = 16;

	private static final int SREG_FLD_OFIC = 5;
	private static final int SREG_FLD_SENDER = 7;
	private static final int SREG_FLD_DEST = 8;
	private static final int SREG_FLD_MATTERTYPE = 12;

	public static List validateValues(String sessionID, Integer bookID, Map values, boolean OnlyEnabled, String entidad)
		throws AttributesException, BookException, SessionException, ValidationException {

		Validator.validate_String_NotNull_LengthMayorZero(sessionID, ValidationException.ATTRIBUTE_SESSION);
		Validator.validate_Integer(bookID, ValidationException.ATTRIBUTE_BOOK);
		Validator.validate_NotNull(values, ValidationException.ATTRIBUTE_VALIDATION_FIELD_VALUE);

    	List privOrgs = null;
		List result = new ArrayList();
		Integer[] arrFields = new Integer[NUM_FIELDS];

		try {
			Session session = HibernateUtil.currentSession(entidad);
			//Recuperamos la sesión
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(sessionID);

			//Es necesario tener el libro abierto para consultar su contenido.
			if (!cacheBag.containsKey(bookID)) {
				throw new BookException(BookException.ERROR_BOOK_NOT_OPEN);
			}

			if (Repository.getInstance(entidad).isInBook(bookID).booleanValue()) {
				arrFields[0] = new Integer(EREG_FLD_OFIC);
				arrFields[1] = new Integer(EREG_FLD_SENDER);
				arrFields[2] = new Integer(EREG_FLD_DEST);
				arrFields[3] = new Integer(EREG_FLD_REGORIG);
				arrFields[4] = new Integer(EREG_FLD_MATTERTYPE);
			}else{
				arrFields[0] = new Integer(SREG_FLD_OFIC);
				arrFields[1] = new Integer(SREG_FLD_SENDER);
				arrFields[2] = new Integer(SREG_FLD_DEST);
				arrFields[3] = new Integer(SREG_FLD_MATTERTYPE);
			}
            Iuserusertype userusertype = (Iuserusertype) cacheBag.get(HIBERNATE_Iuserusertype);
			ScrOfic scrOfic = (ScrOfic) cacheBag.get(HIBERNATE_ScrOfic);

			for (int i = 0; i < arrFields.length; i++){

				if (values.containsKey(arrFields[i]) && (values.get(arrFields[i]) != null)) {

					int FldId = arrFields[i].intValue();

					try{
						switch (FldId){
							case EREG_FLD_OFIC:
								if (values.get(arrFields[i]).getClass().equals(ArrayList.class)){
									for (Iterator it = ((List)values.get(arrFields[i])).iterator(); it.hasNext();){
										String code = (String) it.next();
										ISOfficesValidator.getOffice(session, code);
									}
								} else {
									ISOfficesValidator.getOffice(session, values.get(arrFields[i]).toString());
								}
								break;
							case EREG_FLD_SENDER:
							case EREG_FLD_DEST:
						        if (userusertype.getType() != IDocKeys.IUSERUSERTYPE_USER_TYPE_ADMIN) {
								    privOrgs = DBEntityDAOFactory.getCurrentDBEntityDAO().getPrivOrgs(scrOfic.getId().intValue(), entidad);
						        }
								if (values.get(arrFields[i]).getClass().equals(ArrayList.class)){
									for (Iterator it = ((List)values.get(arrFields[i])).iterator(); it.hasNext();){
										String code = (String) it.next();
		                                ISUnitsValidator.getUnit(session, code, OnlyEnabled, privOrgs);
									}
								} else {
	                                ISUnitsValidator.getUnit(session, values.get(arrFields[i]).toString(), OnlyEnabled, privOrgs);
								}
								break;
							case EREG_FLD_REGORIG:
								if (values.get(arrFields[i]).getClass().equals(ArrayList.class)){
									for (Iterator it = ((List)values.get(arrFields[i])).iterator(); it.hasNext();){
										String code = (String) it.next();
										ISUnitsValidator.getEntReg(session, code, OnlyEnabled);
									}
								} else {
									ISUnitsValidator.getEntReg(session, values.get(arrFields[i]).toString(), OnlyEnabled);
								}
								break;
							case EREG_FLD_MATTERTYPE:
								if (values.get(arrFields[i]).getClass().equals(ArrayList.class)){
									for (Iterator it = ((List)values.get(arrFields[i])).iterator(); it.hasNext();){
										String code = (String) it.next();
										ISSubjectsValidator.getSubjectForOfic(session, code, OnlyEnabled, true, scrOfic.getId());
									}
								} else {
									ISSubjectsValidator.getSubjectForOfic(session, values.get(arrFields[i]).toString(), OnlyEnabled, true, scrOfic.getId());
								}
								break;
							case SREG_FLD_MATTERTYPE:
								if (values.get(arrFields[i]).getClass().equals(ArrayList.class)){
									for (Iterator it = ((List)values.get(arrFields[i])).iterator(); it.hasNext();){
										String code = (String) it.next();
										ISSubjectsValidator.getSubjectForOfic(session, code, OnlyEnabled, false, scrOfic.getId());
									}
								} else {
									ISSubjectsValidator.getSubjectForOfic(session, values.get(arrFields[i]).toString(), OnlyEnabled, false, scrOfic.getId());
								}
								break;
						}
					}
					catch (ValidationException e) {
						result.add(arrFields[i]);
					}
				}
			}

			return result;

		} catch (Exception e) {
			throw new AttributesException(AttributesException.ERROR_CANNOT_FIND_VALIDATIONFIELDS);
		} finally {
			 HibernateUtil.closeSession(entidad);
		}
	}
}
