package es.ieci.tecdoc.isicres.api.web.util;

import gnu.trove.THashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.ieci.tecdoc.common.AuthenticationUser;
import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.TecDocException;
import com.ieci.tecdoc.common.invesdoc.Idocarchdet;
import com.ieci.tecdoc.common.invesdoc.Iuserusertype;
import com.ieci.tecdoc.common.invesicres.ScrOfic;
import com.ieci.tecdoc.common.invesicres.ScrRegstate;
import com.ieci.tecdoc.common.isicres.AxSf;
import com.ieci.tecdoc.common.keys.HibernateKeys;
import com.ieci.tecdoc.common.keys.IDocKeys;
import com.ieci.tecdoc.common.keys.ServerKeys;
import com.ieci.tecdoc.common.utils.ISicresAPerms;
import com.ieci.tecdoc.common.utils.ISicresGenPerms;
import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.usecase.UseCaseConf;
import com.ieci.tecdoc.utils.cache.CacheBag;
import com.ieci.tecdoc.utils.cache.CacheFactory;

/**
 * Clase helper de utilidad para obtener datos de la session actual del modelo de objetos antiguos
 * es usada desde {@link CurrentUserSessionContextUtil} 
 * @author IECISA
 *
 */
public class CurrentUserSessionContextUtilHelper {
	
	protected CacheBag getCacheBag(String sessionID) throws SessionException, TecDocException{
		return CacheFactory.getCacheInterface().getCacheEntry(sessionID);
	}
	
	protected UseCaseConf getUseCaseConf(HttpServletRequest request){
		HttpSession session = request.getSession(true);
		return (UseCaseConf) session.getAttribute(Keys.J_USECASECONF);
	}
	
	protected String getSessionId(HttpServletRequest request){
		return getUseCaseConf(request).getSessionID();
	}
	
	protected AuthenticationUser getAuthenticationUser(String sessionID) throws SessionException, TecDocException{
		return (AuthenticationUser)getCacheBag(sessionID).get(HibernateKeys.HIBERNATE_Iuseruserhdr);
	}
	
	protected ScrOfic getScrOfic(String sessionID) throws SessionException, TecDocException{
		return (ScrOfic)getCacheBag(sessionID).get(HibernateKeys.HIBERNATE_ScrOfic);
	}
	
	protected Integer getIdLibro(HttpServletRequest request){
		HttpSession session = request.getSession(true);
		return (Integer) session.getAttribute(Keys.J_BOOK);
	}
	protected Integer getIdRegistro(HttpServletRequest request){
		HttpSession session = request.getSession(true);
		return (Integer) session.getAttribute(Keys.J_REGISTER);
	}
	
	protected THashMap getBookInformation(String sessionID,Integer idLibro) throws SessionException, TecDocException{
		 return (THashMap) getCacheBag(sessionID).get(idLibro);
	}
	
	protected AxSf getAxsf(String sessionID,Integer idLibro) throws SessionException, TecDocException{
		return (AxSf) getBookInformation(sessionID, idLibro).get(ServerKeys.AXSF);
	}
	
	protected ISicresAPerms getISicresAPerms(String sessionID,Integer idLibro) throws SessionException, TecDocException{
		ISicresAPerms result = null ; 
		THashMap bookInfo = getBookInformation(sessionID, idLibro);
		if (bookInfo!=null){
			result =(ISicresAPerms) bookInfo.get(ServerKeys.APERMS_USER);
		}
		return result;
		
	}
	
	protected ISicresGenPerms getISicresGenPerms(String sessionID) throws SessionException, TecDocException{
		return (ISicresGenPerms) getCacheBag(sessionID).get(ServerKeys.GENPERMS_USER);
	}
	
	protected Idocarchdet getIdocarchdetFldDef(String sessionID,Integer idLibro) throws SessionException, TecDocException{
		return (Idocarchdet) getBookInformation(sessionID, idLibro).get(IDocKeys.IDOCARCHDET_FLD_DEF_ASOBJECT);
	}
	protected Idocarchdet getIdocarchdetValidDef(String sessionID,Integer idLibro) throws SessionException, TecDocException{
		return (Idocarchdet) getBookInformation(sessionID, idLibro).get(IDocKeys.IDOCARCHDET_VLD_DEF_ASOBJECT);
	}
	
	protected ScrRegstate getScrRegstate(String sessionID,Integer idLibro) throws SessionException, TecDocException{
		return (ScrRegstate) getBookInformation(sessionID, idLibro).get(HibernateKeys.HIBERNATE_ScrRegstate);
	}
	
	protected String getProfile(String sessionID) throws SessionException, TecDocException{
		return String.valueOf(((Iuserusertype) getCacheBag(sessionID).get(HibernateKeys.HIBERNATE_Iuserusertype)).getType());
	}
	
	protected Iuserusertype getIuserusertype(String sessionID) throws SessionException, TecDocException{
		return (Iuserusertype) getCacheBag(sessionID).get(HibernateKeys.HIBERNATE_Iuserusertype);
	}
	
	protected String getIdEntidad(HttpServletRequest request){
		return getUseCaseConf(request).getEntidadId();
	}
}
