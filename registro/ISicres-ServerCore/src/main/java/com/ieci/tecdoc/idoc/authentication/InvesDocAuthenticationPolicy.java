//
// FileName: InvesDocAuthenticationPolicy.java
//
package com.ieci.tecdoc.idoc.authentication;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.hibernate.Criteria;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import net.sf.hibernate.expression.Expression;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.AuthenticationPolicy;
import com.ieci.tecdoc.common.AuthenticationUser;
import com.ieci.tecdoc.common.exception.SecurityException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.common.invesdoc.Iuseruserhdr;
import com.ieci.tecdoc.common.invesdoc.Iuserusersys;
import com.ieci.tecdoc.common.invesicres.ScrOfic;
import com.ieci.tecdoc.common.keys.ConfigurationKeys;
import com.ieci.tecdoc.common.keys.HibernateKeys;
import com.ieci.tecdoc.common.keys.IDocKeys;
import com.ieci.tecdoc.common.keys.ISicresKeys;
import com.ieci.tecdoc.common.utils.Configurator;
import com.ieci.tecdoc.common.utils.ISicresQueries;
import com.ieci.tecdoc.idoc.utils.CryptoUtils;
import com.ieci.tecdoc.utils.HibernateUtil;
import com.ieci.tecdoc.utils.Validator;

/**
 * @author lmvicente
 * @version
 * @since
 * @creationDate 30-mar-2004
 */

public class InvesDocAuthenticationPolicy implements AuthenticationPolicy, IDocKeys, ISicresKeys, HibernateKeys {

    /***************************************************************************
     * Attributes
     **************************************************************************/

    private static final Logger log = Logger.getLogger(InvesDocAuthenticationPolicy.class);

    /***************************************************************************
     * Constructors
     **************************************************************************/

    /***************************************************************************
     * Public methods
     **************************************************************************/

    public AuthenticationUser validate(String login, String password, String entidad) throws SecurityException, ValidationException {
        validateParameters(login, password);

        Transaction tran = null;
        try {
            Session session = HibernateUtil.currentSession(entidad);
            tran = session.beginTransaction();

            // Recuperamos el usuario
            List list = ISicresQueries.getUserUserHdrByName(session, login);

            // Comprobamos que existe el usuario si en la consulta nos devuelve
            // algun objeto
            if (list.size() == 0) {
                throw new SecurityException(SecurityException.ERROR_USER_NOTFOUND);
            }

            // Cargamos el usuario para su modificacion, si no lo cargamos
            // con load no lo podemos modificar y guardar posteriormente
            Iuseruserhdr user = (Iuseruserhdr) session.load(Iuseruserhdr.class, ((Iuseruserhdr) list.get(0)).getId());

            // Comprobamos que no esta bloqueado
            if (user.getStat() == IUSERUSERHDR_LOCKED_STAT) {
                throw new SecurityException(SecurityException.ERROR_USER_ISLOCKED);
            }

            // Comprobamos password
            if (entidad.equals(IS_INVESICRES)){
	            if (!validatePassword(user.getPassword(), password, user.getId())) {
	                // Incrementar el numero de reintentos
	                user.setNumbadcnts(user.getNumbadcnts() + 1);

	                // Si llegamos al numero maximo de reintentos lo bloqueamos y
	                // reseteamos
	                boolean wasLocked = false;

	                if (user.getNumbadcnts() >= getMaxNumBadCnts(session)) {
	                    wasLocked = true;
	                    user.setNumbadcnts(IUSERUSERHDR_INITIAL_NUMBADCNTS);
	                    user.setStat(IUSERUSERHDR_LOCKED_STAT);
	                }
	                session.flush();

	                if (wasLocked) {
	                    throw new SecurityException(SecurityException.ERROR_USER_WASLOCKED);
	                } else {
	                    throw new SecurityException(SecurityException.ERROR_PASSWORD_INCORRECT);
	                }
	            } else {
	                if (user.getNumbadcnts() != IUSERUSERHDR_INITIAL_NUMBADCNTS) {
	                    // Todo es correcto, reseteamos el usuario
	                    user.setNumbadcnts(IUSERUSERHDR_INITIAL_NUMBADCNTS);
	                    session.flush();
	                }
	            }
            } else {
                if (user.getNumbadcnts() != IUSERUSERHDR_INITIAL_NUMBADCNTS) {
                    // Todo es correcto, reseteamos el usuario
                    user.setNumbadcnts(IUSERUSERHDR_INITIAL_NUMBADCNTS);
                    session.flush();
                }
            }

            List deptList = getUserDeptList(user.getId(), entidad);

            AuthenticationUser authenticationUser = new AuthenticationUser();

            if (deptList != null && deptList.size() > 0) {
            	authenticationUser.setDeptList(deptList);
			}

            authenticationUser.setId(user.getId());
            authenticationUser.setName(login);
            authenticationUser.setDeptid(new Integer(user.getDeptid()));
            authenticationUser.setDeptIdOriginal(new Integer(user.getDeptid()));

            //Commit de la transacción
            HibernateUtil.commitTransaction(tran);

            return authenticationUser;
        } catch (SecurityException sE){
			try {
				//Commit de la transacción
				HibernateUtil.commitTransaction(tran);
			} catch (HibernateException cE) {
				log.warn("Error al realizar el commit de la transacción", cE);
			}
        	throw sE;
        } catch (HibernateException hE) {
			try {
				//Commit de la transacción
				HibernateUtil.commitTransaction(tran);
			} catch (HibernateException cE) {
				log.warn("Error al realizar el commit de la transacción", cE);
			}
            log.warn("No se puede validar al usuario [" + login + "] password [" + password + "]");
            throw new SecurityException(SecurityException.ERROR_SQL);
        } finally {
			HibernateUtil.closeSession(entidad);
        }
    }

    public AuthenticationUser validate(String userDn, String entidad) throws SecurityException{
    	throw new SecurityException(SecurityException.ERROR_AUTHENTICATION_POLICY_NOTFOUND);
    }
    public void changePassword(String login, String newPassword, String oldPassword, String entidad) throws SecurityException,
            ValidationException {
        if (Configurator.getInstance().getPropertyAsBoolean(ConfigurationKeys.KEY_SERVER_CHECK_PASSWORD).booleanValue()) {
            validateParameters(login, newPassword, oldPassword);
            try {
                Session session = HibernateUtil.currentSession(entidad);

                // Recuperamos el usuario
                List list = ISicresQueries.getUserUserHdrByName(session, login);

                // Comprobamos que existe el usuario si en la consulta nos
                // devuelve algun objeto
                if (list.size() == 0) {
                    throw new SecurityException(SecurityException.ERROR_USER_NOTFOUND);
                }

                // Cargamos el usuario para su modificacion, sino lo cargamos
                // con load no lo podemos modificar y guardar posteriormente
                Iuseruserhdr user = (Iuseruserhdr) session.load(Iuseruserhdr.class, ((Iuseruserhdr) list.get(0))
                        .getId());

                // Comprobamos que no esta bloqueado
                if (user.getStat() == IUSERUSERHDR_LOCKED_STAT) {
                    throw new SecurityException(SecurityException.ERROR_USER_ISLOCKED);
                }

                String encOldPassword = CryptoUtils.encryptPassword(oldPassword, user.getId());

                // Comprobamos password
                if (!user.getPassword().equals(encOldPassword)) {
                    // Incrementar el numero de reintentos
                    user.setNumbadcnts(user.getNumbadcnts() + 1);

                    // Si llegamos al numero maximo de reintentos lo bloqueamos
                    // y reseteamos
                    boolean wasLocked = false;

                    if (user.getNumbadcnts() >= getMaxNumBadCnts(session)) {
                        wasLocked = true;
                        user.setNumbadcnts(IUSERUSERHDR_INITIAL_NUMBADCNTS);
                        user.setStat(IUSERUSERHDR_LOCKED_STAT);
                    }
                    session.flush();

                    if (wasLocked) {
                        throw new SecurityException(SecurityException.ERROR_USER_WASLOCKED);
                    } else {
                        throw new SecurityException(SecurityException.ERROR_PASSWORD_INCORRECT);
                    }
                } else {
                    if (user.getNumbadcnts() != IUSERUSERHDR_INITIAL_NUMBADCNTS) {
                        // Todo es correcto, reseteamos el usuario
                        user.setNumbadcnts(IUSERUSERHDR_INITIAL_NUMBADCNTS);
                        session.flush();
                    }
                }

                // String webPassword = CryptoUtils.decodeToString(newPassword);
                // String encodedPassword =
                // CryptoUtils.encodeString(StringUtils.reverse(webPassword +
                // user.getId()));
                user.setPassword(CryptoUtils.encryptPassword(newPassword, user.getId()));
                session.update(user);
                session.flush();
            } catch (SecurityException sE) {
                throw sE;
            } catch (Exception hE) {
                log.error(
                        "No se puede cambiar la contraseña al usuario [" + login + "] password [" + oldPassword + "]",
                        hE);
                throw new SecurityException(SecurityException.ERROR_USER_NOTFOUND);
            }
        }
    }

    public boolean validatePassword(String userPassword, String passwordToValidate, Integer id)
            throws SecurityException {

        try {

            String encPwd1 = CryptoUtils.encryptPassword(passwordToValidate, id);

            return (encPwd1.equals(userPassword));
        } catch (Exception e) {
            log.error("No se puede validar la contraseña [" + userPassword + "]", e);
            throw new SecurityException(SecurityException.ERROR_PASSWORD_INCORRECT);
        }
    }

    /***************************************************************************
     * Protected methods
     **************************************************************************/

    /***************************************************************************
     * Private methods
     **************************************************************************/

    /**
     * Metodo que obtiene el numero maximo de intentos de acceso a la aplicación
     *
     * @param session - Sesion de hibernate
     * @return int - Numero de intentos posibles erroneos para el acceso a la aplicación
     *
     * @throws HibernateException
     */
	private int getMaxNumBadCnts(Session session) throws HibernateException {
		//inicializamos la variable
		int result = 0;

		//realizamos la consulta para obtener la información de BBDD
		Iuserusersys iuserusersys = ISicresQueries.getIuserusersys(session);

		//si el objeto iuserusersyses es distinto de null, seteamos el valor de BBDD
		if(iuserusersys != null){
			result =  iuserusersys.getMaxbaccnt();
		}else{
			log.warn("No se ha obtenido información de la tabla IUSERUSERSYS");
		}

		return result;
	}

    private void validateParameters(String login, String password) throws ValidationException {
        try {
            Validator.validate_String_NotNull_WithLength(login, "", IUSERUSERHDR_MAX_LOGIN_LENGTH);
        } catch (ValidationException vE) {
            throw new ValidationException(ValidationException.ERROR_LOGIN_LENGTH);
        }
        try {
            Validator.validate_String_NotNull_WithLength(password, "", IUSERUSERHDR_MAX_PASSWORD_LENGTH);
        } catch (ValidationException vE) {
            throw new ValidationException(ValidationException.ERROR_PASSWORD_LENGTH);
        }
    }

    private void validateParameters(String login, String password, String password2) throws ValidationException {
        try {
            Validator.validate_String_NotNull_WithLength(login, "", IUSERUSERHDR_MAX_LOGIN_LENGTH);
        } catch (ValidationException vE) {
            throw new ValidationException(ValidationException.ERROR_LOGIN_LENGTH);
        }
        try {
            Validator.validate_String_NotNull_WithLength(password, "", IUSERUSERHDR_MAX_PASSWORD_LENGTH);
        } catch (ValidationException vE) {
            throw new ValidationException(ValidationException.ERROR_PASSWORD_LENGTH);
        }
        try {
            Validator.validate_String_NotNull_WithLength(password2, "", IUSERUSERHDR_MAX_PASSWORD_LENGTH);
        } catch (ValidationException vE) {
            throw new ValidationException(ValidationException.ERROR_PASSWORD_LENGTH);
        }
    }


	public List getUserDeptList (Integer idUser, String entidad)
		throws SecurityException {

		String sqlUsrOfic = "id in (select idofic from scr_usrofic where iduser = ";
		String sqlIuseruserhdr = "deptid in (select deptid from iuseruserhdr where id = ";
		StringBuffer queryUsrOfic = new StringBuffer();
		StringBuffer queryIuseruserhdr = new StringBuffer();
		List deptList = new ArrayList();

		try {
			Session session = HibernateUtil.currentSession(entidad);

			queryUsrOfic.append(sqlUsrOfic);
			queryUsrOfic.append(idUser);
			queryUsrOfic.append(")");

			queryIuseruserhdr.append(sqlIuseruserhdr);
			queryIuseruserhdr.append(idUser);
			queryIuseruserhdr.append(")");

			Criteria criteriaResults = session.createCriteria(ScrOfic.class);
			criteriaResults.add(Expression.sql(queryUsrOfic.toString()));
			List list = criteriaResults.list();

			Criteria criteriaResults1 = session.createCriteria(ScrOfic.class);
			criteriaResults1.add(Expression.sql(queryIuseruserhdr.toString()));
			List list1 = criteriaResults1.list();

			if (list != null && list.size() > 0) {
				Iterator it = list.iterator();
				while (it.hasNext()) {
					ScrOfic ofic = (ScrOfic) it.next();
					deptList.add(new Integer(ofic.getDeptid()));
				}
			}

			if (list1 != null && list1.size() > 0) {
				Iterator it = list1.iterator();
				while (it.hasNext()) {
					ScrOfic ofic = (ScrOfic) it.next();
					Integer deptId = new Integer(ofic.getDeptid());
					if (!deptList.contains(deptId)) {
						deptList.add(deptId);
					}
				}
			}

			return deptList;

		} catch (Exception e) {
			log.error("Impossible to load other offices for user ["
					+ idUser + "]", e);

			throw new SecurityException(SecurityException.ERROR_USER_NOTFOUND);
		}

	}


    /***************************************************************************
     * Inner classes
     **************************************************************************/

    /***************************************************************************
     * Test brench
     **************************************************************************/

}