/**
 *
 */
package com.ieci.tecdoc.common.utils;

import java.util.Date;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;

import com.ieci.tecdoc.common.AuthenticationUser;
import com.ieci.tecdoc.common.invesdoc.Idocfdrstat;
import com.ieci.tecdoc.common.invesicres.ScrAddress;
import com.ieci.tecdoc.common.invesicres.ScrDistribucionActual;
import com.ieci.tecdoc.common.invesicres.ScrDistreg;
import com.ieci.tecdoc.common.invesicres.ScrModifreg;
import com.ieci.tecdoc.common.invesicres.ScrPageInfo;
import com.ieci.tecdoc.common.invesicres.ScrRegasoc;
import com.ieci.tecdoc.common.invesicres.ScrRegint;
import com.ieci.tecdoc.common.invesicres.ScrUserconfig;

/**
 * @author 66575267
 *
 */
public class ISicresSaveQueries {

	public static void saveIDocFdrStat(Session session, Integer bookID,
			int fdrid, AuthenticationUser user, Date currentDate)
			throws HibernateException, Exception {

		Idocfdrstat idoc = new Idocfdrstat();
		idoc.setFdrid(fdrid);
		idoc.setArchid(bookID.intValue());
		idoc.setStat(1);
		idoc.setUserid(user.getId().intValue());
		idoc.setFlags(1);
		idoc.setTimestamp(currentDate);
		session.save(idoc);
	}

	public static void saveScrRegAsoc(Session session, int assocId,
			int idArchPrim, int idArchSec, int idFdrPrim, int idFdrSec)
			throws HibernateException {
		ScrRegasoc scrRegAsoc = new ScrRegasoc();
		scrRegAsoc.setId(new Integer(assocId));
		scrRegAsoc.setIdArchprim(idArchPrim);
		scrRegAsoc.setIdFdrprim(idFdrPrim);
		scrRegAsoc.setIdArchsec(idArchSec);
		scrRegAsoc.setIdFdrsec(idFdrSec);
		session.save(scrRegAsoc);
	}

	public static void saveScrUserconfig(Session session, Integer userid,
			String data) throws HibernateException {
		ScrUserconfig userConfig = new ScrUserconfig();
		userConfig.setUserid(userid);
		userConfig.setData(data);
		session.save(userConfig);
	}

	public static void saveScrDistreg(Session session, int distributionID,
			Integer bookID, int fdrid, Date distDate, int typeOrig, int idOrig,
			int typeDest, int idDest, int state, Date stateDate, String message, int idDistFather)
			throws HibernateException {
		ScrDistreg scrDistReg = new ScrDistreg();

		scrDistReg.setId(new Integer(distributionID));
		scrDistReg.setIdArch(bookID.intValue());
		scrDistReg.setIdFdr(fdrid);
		scrDistReg.setDistDate(distDate);
		scrDistReg.setTypeOrig(typeOrig);
		scrDistReg.setIdOrig(idOrig);
		scrDistReg.setTypeDest(typeDest);
		scrDistReg.setIdDest(idDest);
		scrDistReg.setState(state);
		scrDistReg.setStateDate(stateDate);
		scrDistReg.setMessage(message);
		//Si esta distribución esta asociada a otra
		if(idDistFather != 0){
			scrDistReg.setIddistfather(idDistFather);
		}

		session.save(scrDistReg);
	}

	/**
	 * Método que almacena en BBDD la distribución actual
	 *
	 * @param session
	 *            - Sesión de hibernate
	 * @param iddist
	 *            - ID de la distribución
	 * @param dist_actual
	 *            - Literal de los nuevos destinos de la distribución
	 * @throws HibernateException
	 */
	public static void saveScrDistribucionActual(Session session, Integer iddist,
			String dist_actual) throws HibernateException {

		ScrDistribucionActual scrDistribucionActual = new ScrDistribucionActual();
		scrDistribucionActual.setIddist(iddist);
		scrDistribucionActual.setDist_actual(dist_actual);

		session.save(scrDistribucionActual);
	}

	public static void saveScrPageInfo(Session session, Integer bookID,
			int regid, int pageid, int hashVersion, String hash)
			throws HibernateException {
		ScrPageInfo pageInfo = new ScrPageInfo();
		pageInfo.setBookid(bookID.intValue());
		pageInfo.setRegid(regid);
		pageInfo.setPageid(pageid);
		pageInfo.setHashVersion(hashVersion);
		pageInfo.setHash(hash);
		session.save(pageInfo);
	}

	public static void saveScrModifreg(Session session, Integer id,
			String userName, Date modifDate, String numReg, int idFld,
			int bookId, int oficId, int modifType) throws HibernateException {
		ScrModifreg scrModifreg = new ScrModifreg();
		scrModifreg.setId(id);
		scrModifreg.setUsr(userName);
		scrModifreg.setModifDate(modifDate);
		scrModifreg.setNumReg(numReg);
		scrModifreg.setIdFld(idFld);
		scrModifreg.setIdArch(bookId);
		scrModifreg.setIdOfic(oficId);
		scrModifreg.setModifType(modifType);
		session.save(scrModifreg);
	}

	public static void saveScrRegint(Session session, Integer id, int bookID,
			int registerId, String name, int idPerson, ScrAddress scrAddress,
			int order) throws HibernateException {
		ScrRegint scr = new ScrRegint();
		scr.setId(id);
		scr.setIdArch(bookID);
		scr.setIdFdr(registerId);
		scr.setName(name);
		scr.setIdPerson(idPerson);
		if (scrAddress != null) {
			scr.setScrAddress(scrAddress);
		}
		scr.setOrd(order);
		session.save(scr);
	}
}
