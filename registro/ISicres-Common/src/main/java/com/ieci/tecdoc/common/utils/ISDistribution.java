/*
 * Created on 01-feb-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ieci.tecdoc.common.utils;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import net.sf.hibernate.Criteria;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.expression.Expression;

import com.ieci.tecdoc.common.entity.dao.DBEntityDAOFactory;
import com.ieci.tecdoc.common.exception.BookException;
import com.ieci.tecdoc.common.invesdoc.Iusergroupuser;
import com.ieci.tecdoc.common.invesicres.ScrDistreg;
import com.ieci.tecdoc.common.invesicres.ScrDistregstate;

/**
 * @author MANUEL TAVARES
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ISDistribution {

    public static final int STATE_PENDIENTE = 1;
    public static final int STATE_ACEPTADO = 2;
    public static final int STATE_ARCHIVADO = 3;
    public static final int STATE_RECHAZADO = 4;
    public static final int STATE_REDISTRIBUIDO = 5;
    public static final int STATE_PENDIENTEDIST = 6;
    public static final int DISTRIBUCION_IN_DIST = 1;
    public static final int DISTRIBUCION_OUT_DIST = 2;

	public ISDistribution(){

	}

	public void setDistState(Session session, int distId, int State,
			Date currentDate, String userName, Integer userId, String entidad, String remarks,
			boolean caseSensitive) throws HibernateException, SQLException,
			Exception {

		int distStateId = DBEntityDAOFactory.getCurrentDBEntityDAO().getNextIdForScrDistRegState(userId, entidad);

//		String tableName = "SCR_DISTREGSTATE";
//		Integer size = BBDDUtils.getTableColumnSize(tableName, "USERNAME", entidad);

		Integer size = new Integer(BBDDUtils.SCR_DISTREGSTATE_USERNAME_FIELD_LENGTH);

		String aux = userName;
		if (aux.length() > size.intValue()){
			aux = aux.substring(0, size.intValue());
			userName = aux;
		}

		if (caseSensitive){
			userName = userName.toUpperCase();
		}

		ScrDistregstate scrDistRegState = new ScrDistregstate();

    	scrDistRegState.setId(new Integer(distStateId));
    	scrDistRegState.setIdDist(distId);
    	scrDistRegState.setState(State);
    	scrDistRegState.setStateDate(currentDate);
    	scrDistRegState.setUsername(userName);
	if (remarks != null) {
			if (caseSensitive) {
				scrDistRegState.setMessage(remarks.toUpperCase());
			} else {
				scrDistRegState.setMessage(remarks);
			}
		}
    	session.save(scrDistRegState);
	}

	public void changeStateAcceptRedis(Session session, Integer bookID,
			int fdrid, int oficID, String userName, Integer userId,
			int distributionType, Timestamp currentDate, String entidad,
			boolean caseSensitive) throws HibernateException, SQLException,
			BookException, Exception {
		StringBuffer query = new StringBuffer();
		ISDistribution ISDist = new ISDistribution();

		List iUserGroupUser = ISicresQueries.getIUserGroupUser(session, userId);

		switch (distributionType) {
		case DISTRIBUCION_IN_DIST: {
			query.append(" (   ( type_Dest=1 and id_Dest= ");
			query.append(userId.intValue());
			query.append(") or ( type_Dest=2 and id_Dest=");
			query.append(oficID);
			if (iUserGroupUser.size() > 0) {
				for (int i = 0; i < iUserGroupUser.size(); i++) {
					Integer idGroup = new Integer(
							((Iusergroupuser) iUserGroupUser.get(i))
									.getGroupid());
					query.append(") or ( type_Dest=3 and id_Dest=");
					query.append(idGroup);

				}
			}

			query.append(") ) ");
			break;
		}
		case DISTRIBUCION_OUT_DIST: {
			query.append(" (   ( type_Orig=1 and id_Orig= ");
			query.append(userId.intValue());
			query.append(") or ( type_Orig=2 and id_Orig=");
			query.append(oficID);
			if (iUserGroupUser.size() > 0) {
				for (int i = 0; i < iUserGroupUser.size(); i++) {
					Integer idGroup = new Integer(
							((Iusergroupuser) iUserGroupUser.get(i))
									.getGroupid());
					query.append(") or ( type_Orig=3 and id_Orig=");
					query.append(idGroup);

				}
			}
			query.append(") ) ");
			break;
		}
		default:
			throw new BookException(
					BookException.ERROR_CANNOT_UPDATE_DISTRIBUTION);
		}

		String where = getWhereStateAcceptRedis(bookID, fdrid)
				+ query.toString();
		Criteria criteriaResults = session.createCriteria(ScrDistreg.class);
		criteriaResults.add(Expression.sql(where));
		List list = criteriaResults.list();

		if (list != null && !list.isEmpty()) {

			ScrDistreg distReg = null;
			for (Iterator it = list.iterator(); it.hasNext();) {
				distReg = (ScrDistreg) it.next();
				distReg.setState(ISDistribution.STATE_REDISTRIBUIDO);
				distReg.setStateDate(currentDate);
				session.update(distReg);

				//TODO: ¿Mensaje al distribuir?
				ISDist.setDistState(session, distReg.getId().intValue(),
						ISDistribution.STATE_REDISTRIBUIDO, currentDate,
						userName, userId, entidad, null, caseSensitive);
			}

		}

	}

	public String getWhereStateAcceptRedis(Integer bookID, int fdrid) {
		StringBuffer result = new StringBuffer();

		result.append(" id_arch=");
		result.append(bookID.intValue());
		result.append(" and id_fdr=");
		result.append(fdrid);
		result.append(" and state=2 and ");

		return result.toString();
	}

}
