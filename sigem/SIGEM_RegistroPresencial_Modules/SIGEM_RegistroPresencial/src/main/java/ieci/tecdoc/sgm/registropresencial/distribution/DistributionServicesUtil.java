/**
 * 
 */
package ieci.tecdoc.sgm.registropresencial.distribution;

import com.ieci.tecdoc.common.AuthenticationUser;
import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.TecDocException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.common.keys.HibernateKeys;
import com.ieci.tecdoc.utils.Validator;
import com.ieci.tecdoc.utils.cache.CacheBag;
import com.ieci.tecdoc.utils.cache.CacheFactory;

/**
 * @author 66575267
 * 
 */
public class DistributionServicesUtil {

	public static void validateUserDeptByOficAsoc(String sessionID,
			boolean oficAsoc) throws SessionException, TecDocException {
		if (!oficAsoc) {
			Validator.validate_String_NotNull_LengthMayorZero(sessionID,
					ValidationException.ATTRIBUTE_SESSION);

			// Recuperamos la sesión
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
					sessionID);
			AuthenticationUser user = (AuthenticationUser) cacheBag
					.get(HibernateKeys.HIBERNATE_Iuseruserhdr);

			user.setDeptid(user.getDeptIdOriginal());

			cacheBag.remove(HibernateKeys.HIBERNATE_Iuseruserhdr);
			cacheBag.put(HibernateKeys.HIBERNATE_Iuseruserhdr, user);
		}
	}
}
