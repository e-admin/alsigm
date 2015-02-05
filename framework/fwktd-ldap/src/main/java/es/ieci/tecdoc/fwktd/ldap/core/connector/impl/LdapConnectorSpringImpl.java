package es.ieci.tecdoc.fwktd.ldap.core.connector.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ldap.AuthenticationException;
import org.springframework.ldap.CommunicationException;
import org.springframework.ldap.NamingException;
import org.springframework.ldap.SizeLimitExceededException;
import org.springframework.ldap.control.PagedResultsCookie;
import org.springframework.ldap.control.PagedResultsDirContextProcessor;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

import es.ieci.tecdoc.fwktd.ldap.core.config.LdapContextConfigLoader;
import es.ieci.tecdoc.fwktd.ldap.core.constants.LdapConstants;
import es.ieci.tecdoc.fwktd.ldap.core.constants.LdapEngineConstants;
import es.ieci.tecdoc.fwktd.ldap.core.messages.LdapErrorCodes;
import es.ieci.tecdoc.fwktd.ldap.core.utils.LdapExceptionUtils;
import es.ieci.tecdoc.fwktd.ldap.core.utils.LdapFormatUtils;
import es.ieci.tecdoc.fwktd.ldap.core.utils.LdapLogDebugUtils;
import es.ieci.tecdoc.fwktd.ldap.core.vo.LdapAuthenticationUserDnVO;
import es.ieci.tecdoc.fwktd.ldap.core.vo.LdapContextConfig;
import es.ieci.tecdoc.fwktd.ldap.core.vo.LdapSearchVO;


/**
 * Implementacion Spring del conector LDAP
 * @author Iecisa
 * @version $Revision: 115 $
 *
 */
public class LdapConnectorSpringImpl extends LdapConnectorAbstractImpl {



	/**
	 * Logger para la clase
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(LdapConnectorSpringImpl.class);

	/**
	 * Comprueba si un control es soportado por el servidor LDAP
	 * @param control Identificador del control
	 * @param ldapContextConfig Configuracion de contexto
	 * @return Si el control es soportado
	 */
	private boolean checkSupportedControl(final String control, final LdapContextConfig ldapContextConfig){
		LdapContextSource ctxSource = new LdapContextSource();
		ctxSource.setUrl(ldapContextConfig.getLdapUrl());
		ctxSource.setUserDn(ldapContextConfig.getLdapUser());
		ctxSource.setPassword(ldapContextConfig.getLdapPassword());

		DirContext context = null;
		boolean ret = false;
		try {
			ctxSource.afterPropertiesSet();
			context = ctxSource.getReadWriteContext();
			Attributes attrs = context.getAttributes("", new String[] { LdapConstants.SUPPORTED_CONTROL_ATTRIBUTE });

			Attribute attr = attrs.get(LdapConstants.SUPPORTED_CONTROL_ATTRIBUTE);
			for (int i = 0; i < attr.size(); ++i) {
				if (attr.get(i).equals(control)) {
					ret = true;
					break;
				}
			}
		} catch (Exception e){
			LdapLogDebugUtils.generateDebug(LdapErrorCodes.ERR_10010_CONTROL_NOT_SUPPORTED_ERROR, LOGGER, e);
		} finally {
			if (context!=null) {
				try {
					context.close();
				} catch (javax.naming.NamingException e) {
					LdapLogDebugUtils.generateDebug(LdapErrorCodes.ERR_10010_CONTROL_NOT_SUPPORTED_ERROR, LOGGER, e);
				}
			}
		}

		return ret;
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.ldap.core.connector.LdapConnector#authenticate(es.ieci.tecdoc.fwktd.ldap.core.vo.LdapAuthenticationUserDnVO, es.ieci.tecdoc.fwktd.ldap.core.config.LdapContextConfigLoader)
	 */
	public String authenticate(final LdapAuthenticationUserDnVO authInfo,
			final LdapContextConfigLoader ldapCtxCfgLoader) throws Exception {

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("authenticate(LdapAuthenticationInfoVO="
					+ authInfo + ",LdapContextConfigLoader="
					+ ldapCtxCfgLoader + ") - <-- Entrada");
		}

		String ret = null;
		if ((authInfo == null)
				|| (authInfo.getUserCredentials() == null)
				|| ("".equals(authInfo.getUserCredentials()))) {
			LdapExceptionUtils.generateErrorException(LdapErrorCodes.ERR_10000_AUTHENTICACION_CREDENTIALS_EMPTY, LOGGER);
		} else {
			LdapContextConfig ldapContextConfig = ldapCtxCfgLoader.loadLdapContextConfig();

			LdapContextSource contextSource = new LdapContextSource();
			contextSource.setUrl(ldapContextConfig.getLdapUrl());
			contextSource.setBase(ldapContextConfig.getLdapBase());
			contextSource.setUserDn(authInfo.getUserDn());
			contextSource.setPassword(authInfo.getUserCredentials());

			Map baseEnvProps = new HashMap();
			baseEnvProps.put(LdapConstants.CONTEXT_ATTRIBUTES_BINARY, ldapContextConfig.getLdapGuidAttribute());

			if (ldapContextConfig.isLdapPool()){
				contextSource.setPooled(ldapContextConfig.isLdapPool());
				baseEnvProps.put(LdapConstants.POOL_TIMEOUT_ENV_PARAMETER, ldapContextConfig.getLdapPoolTimeout());
			}

			contextSource.setBaseEnvironmentProperties(baseEnvProps);

			try {
				contextSource.afterPropertiesSet();
				DirContext ctx = contextSource.getReadWriteContext();
				Attributes attrs = ctx.getAttributes("");
				attrs = ctx.getAttributes(authInfo.getUserDn(),new String [] {ldapContextConfig.getLdapGuidAttribute()});
				Attribute attr = attrs.get(ldapContextConfig.getLdapGuidAttribute());
				if (LdapEngineConstants.ENGINE_ACTIVE_DIRECTORY.equals(ldapContextConfig.getLdapEngine())){
                    ret = LdapFormatUtils.formatGuid(ldapContextConfig.getLdapEngine(), attr.get());
                } else {
                    ret = (String) attr.get();
                }
			}
			catch (AuthenticationException e){
				LdapExceptionUtils.generateErrorException(LdapErrorCodes.ERR_10002_AUTHENTICATION_PROBLEM, LOGGER, e);
			}
			catch(CommunicationException e) {
				LdapExceptionUtils.generateErrorException(LdapErrorCodes.ERR_10002_AUTHENTICATION_PROBLEM, LOGGER, e);
			}
			catch(NamingException e) {
				LdapExceptionUtils.generateErrorException(LdapErrorCodes.ERR_10002_AUTHENTICATION_PROBLEM, LOGGER, e);
			}
			catch (Exception e){
				LdapExceptionUtils.generateErrorException(LdapErrorCodes.ERR_10002_AUTHENTICATION_PROBLEM, LOGGER, e);
			}
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("authenticate(LdapAuthenticationInfoVO=" + authInfo
					+ ",LdapContextConfigLoader=" + ldapCtxCfgLoader
					+ ") - --> Salida  - valor de retorno=" + ret);
		}

		return ret;
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.ldap.core.connector.LdapConnector#find(es.ieci.tecdoc.fwktd.ldap.core.vo.LdapSearchVO, es.ieci.tecdoc.fwktd.ldap.core.config.LdapContextConfigLoader)
	 */
	public List find(final LdapSearchVO ldapSearch,
			final LdapContextConfigLoader ldapCtxCfgLoader) throws Exception {

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("find(LdapSearchVO="
					+ ldapSearch + ",LdapContextConfigLoader="
					+ ldapCtxCfgLoader
					+ ") - <-- Entrada");
		}

		LdapContextConfig ldapContextConfig = ldapCtxCfgLoader.loadLdapContextConfig();
		List result = new ArrayList();

		if ((ldapContextConfig == null)
				|| (ldapContextConfig.getLdapPassword() == null)
				|| ("".equals(ldapContextConfig.getLdapPassword()))) {
			LdapExceptionUtils.generateErrorException(LdapErrorCodes.ERR_10000_AUTHENTICACION_CREDENTIALS_EMPTY, LOGGER);
		} else {

			boolean pagedResults = checkSupportedControl(LdapConstants.SIMPLE_PAGED_RESULTS_CONTROL, ldapContextConfig);

			Map baseEnvProps = new HashMap();
			baseEnvProps.put(LdapConstants.CONTEXT_ATTRIBUTES_BINARY, ldapContextConfig.getLdapGuidAttribute());

			LdapContextSource contextSource = new LdapContextSource();
			contextSource.setUrl(ldapContextConfig.getLdapUrl());
			contextSource.setBase(ldapContextConfig.getLdapBase());
			contextSource.setUserDn(ldapContextConfig.getLdapUser());
			contextSource.setPassword(ldapContextConfig.getLdapPassword());

			if (ldapContextConfig.isLdapPool()){
				contextSource.setPooled(ldapContextConfig.isLdapPool());
				baseEnvProps.put(LdapConstants.POOL_TIMEOUT_ENV_PARAMETER, ldapContextConfig.getLdapPoolTimeout());
			}

			contextSource.setBaseEnvironmentProperties(baseEnvProps);

			try {
				contextSource.afterPropertiesSet();
				contextSource.getReadWriteContext();
			}
			catch (AuthenticationException e){
				LdapExceptionUtils.generateErrorException(LdapErrorCodes.ERR_10003_GENERIC_PROBLEM, LOGGER, e);
			}
			catch(CommunicationException e) {
				LdapExceptionUtils.generateErrorException(LdapErrorCodes.ERR_10003_GENERIC_PROBLEM, LOGGER, e);
			}
			catch(NamingException e) {
				LdapExceptionUtils.generateErrorException(LdapErrorCodes.ERR_10003_GENERIC_PROBLEM, LOGGER, e);
			}
			catch (Exception e){
				LdapExceptionUtils.generateErrorException(LdapErrorCodes.ERR_10003_GENERIC_PROBLEM, LOGGER, e);
			}

			LdapTemplate ldapFindTemplate = new LdapTemplate();
			ldapFindTemplate.setContextSource(contextSource);
			ldapFindTemplate.setIgnorePartialResultException(true);

			SearchControls ctls = new SearchControls();

            int scope = SearchControls.OBJECT_SCOPE;
            if (StringUtils.isNotEmpty(ldapSearch.getScope())){
                try{
                	scope = Integer.valueOf(ldapSearch.getScope()).intValue();
                } catch (Exception e) {
                	LdapLogDebugUtils.generateDebug(LdapErrorCodes.ERR_10003_GENERIC_PROBLEM, LOGGER, e);
                }
            }

			ctls.setSearchScope(scope);
			String [] retAttrs = ldapSearch.getRetAttrs();
			if ((retAttrs!=null)&&(retAttrs.length>0)) {
				ctls.setReturningAttributes(retAttrs);
			}
			if (ldapSearch.getExpected()>LdapConstants.NO_SEARCH_LIMIT) {
				ctls.setCountLimit(ldapSearch.getExpected());
			}

			PagedResultsCookie cookie = new PagedResultsCookie(new byte[0]);
			PagedResultsDirContextProcessor processor = new PagedResultsDirContextProcessor(LdapConstants.PAGE_SIZE, cookie);
			processor.setReplaceSameControlEnabled(false);

			try {
				AttributesMapper mapper = (AttributesMapper)getLdapMapper();
				if (pagedResults){
					List pagedResult = (List) ldapFindTemplate.search(ldapSearch.getBase(), ldapSearch.getFilter().toString(), ctls, mapper, processor);
					result.addAll(pagedResult);

					cookie = processor.getCookie();
					while ((cookie!=null)&&(cookie.getCookie()!=null)&&(cookie.getCookie().length!=0)){
						//processor = new PagedResultsDirContextProcessor(LdapConstants.PAGE_SIZE,cookie);
						pagedResult =(List) ldapFindTemplate.search(ldapSearch.getBase(), ldapSearch.getFilter().toString(), ctls, mapper, processor);
						result.addAll(pagedResult);
						cookie = processor.getCookie();
					}
				} else {
					result = ldapFindTemplate.search(ldapSearch.getBase(), ldapSearch.getFilter().toString(),ctls, mapper);
				}
			} catch (SizeLimitExceededException e) {
				LdapExceptionUtils.generateErrorException(LdapErrorCodes.ERR_10004_RESULT_SIZE_NOT_MATCH_EXPECTED, LOGGER, e);
			} catch (Exception e){
				LdapExceptionUtils.generateErrorException(LdapErrorCodes.ERR_10003_GENERIC_PROBLEM, LOGGER, e);
			}
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("find(LdapSearchVO="
					+ ldapSearch + ",LdapContextConfigLoader="
					+ ldapCtxCfgLoader
					+ ") - --> Salida  - valor de retorno=" + result);
		}

		return result;
	}

}
