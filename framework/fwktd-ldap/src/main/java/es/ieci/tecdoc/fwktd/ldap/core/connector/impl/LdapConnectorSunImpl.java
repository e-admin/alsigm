package es.ieci.tecdoc.fwktd.ldap.core.connector.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ieci.tecdoc.fwktd.ldap.core.config.LdapContextConfigLoader;
import es.ieci.tecdoc.fwktd.ldap.core.constants.LdapConstants;
import es.ieci.tecdoc.fwktd.ldap.core.constants.LdapEngineConstants;
import es.ieci.tecdoc.fwktd.ldap.core.messages.LdapErrorCodes;
import es.ieci.tecdoc.fwktd.ldap.core.utils.LdapExceptionUtils;
import es.ieci.tecdoc.fwktd.ldap.core.utils.LdapFormatUtils;
import es.ieci.tecdoc.fwktd.ldap.core.utils.LdapLogDebugUtils;
import es.ieci.tecdoc.fwktd.ldap.core.utils.LdapReflectionUtils;
import es.ieci.tecdoc.fwktd.ldap.core.vo.LdapAuthenticationUserDnVO;
import es.ieci.tecdoc.fwktd.ldap.core.vo.LdapContextConfig;
import es.ieci.tecdoc.fwktd.ldap.core.vo.LdapEntryAttributeVO;
import es.ieci.tecdoc.fwktd.ldap.core.vo.LdapEntryVO;
import es.ieci.tecdoc.fwktd.ldap.core.vo.LdapSearchVO;


/**
 * Implementacion Sun del conector LDAP
 * @author Iecisa
 * @version $Revision: 115 $
 *
 */
public class LdapConnectorSunImpl extends LdapConnectorAbstractImpl {

    /**
     * Logger para la clase
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(LdapConnectorSunImpl.class);

    /**
     * Clase para resultados paginados en la request
     */
    private static Class requestPagedResultsControlClass;

    /**
     * Clase para resultados paginados en la response
     */
    private static Class responsePagedResultsControlClass;

    /**
     * Metodo para obtener la cookie del control de resultados paginados
     */
    private static final String RESPONSE_PAGED_RESULTS_CONTROL_METHOD = "getCookie";



    /**
     * Carga las clases de resultados paginados
     */
    private void loadPagedResultsControlClasses() {

        if ((responsePagedResultsControlClass==null)&&(requestPagedResultsControlClass==null)){

            try {
                requestPagedResultsControlClass = Class.forName(LdapConstants.DEFAULT_REQUEST_CONTROL);
                responsePagedResultsControlClass = Class.forName(LdapConstants.DEFAULT_RESPONSE_CONTROL);
            }
            catch (ClassNotFoundException e) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Clases por defecto para busqueda paginada no encontradas [" + LdapConstants.DEFAULT_REQUEST_CONTROL + "," + LdapConstants.DEFAULT_RESPONSE_CONTROL + "]", e);
                }

                try {
                    requestPagedResultsControlClass = Class.forName(LdapConstants.FALLBACK_REQUEST_CONTROL);
                    responsePagedResultsControlClass = Class.forName(LdapConstants.FALLBACK_RESPONSE_CONTROL);
                }
                catch (ClassNotFoundException e1) {
                    LdapExceptionUtils.generateErrorException(LdapErrorCodes.ERR_10008_PAGINATED_RESULT_CLASSES_NOT_FOUND, new Object[] {
                            LdapConstants.DEFAULT_REQUEST_CONTROL,
                            LdapConstants.DEFAULT_RESPONSE_CONTROL,
                            LdapConstants.FALLBACK_REQUEST_CONTROL,
                            LdapConstants.FALLBACK_RESPONSE_CONTROL }, LOGGER, e1);
                }
            }
        }
    }

    /**
     * Comprueba si un control es soportado por el servidor LDAP
     * @param control Identificador del control
     * @param ldapContextConfig Configuracion de contexto
     * @return Si el control es soportado
     */
    private boolean checkSupportedControl(final String control, final LdapContext context){

        boolean ret = false;

        try {
            Attributes attrs = context.getAttributes("", new String[] { LdapConstants.SUPPORTED_CONTROL_ATTRIBUTE });

            Attribute attr = attrs.get(LdapConstants.SUPPORTED_CONTROL_ATTRIBUTE);
            for (int i = 0; i < attr.size(); ++i) {
                if (attr.get(i).equals(control)) {
                    ret = true;
                    break;
                }
            }
        } catch (NamingException e) {
            LdapLogDebugUtils.generateDebug(LdapErrorCodes.ERR_10010_CONTROL_NOT_SUPPORTED_ERROR, new String [] {control}, LOGGER, e);
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

            Hashtable env = new Hashtable();
            env.put(Context.PROVIDER_URL, ldapContextConfig.getLdapUrl());
            env.put(Context.INITIAL_CONTEXT_FACTORY, LdapConstants.INITIAL_CONTEXT_FACTORY);
            env.put(Context.SECURITY_AUTHENTICATION, LdapConstants.SECURITY_AUTHENTICATION);
            env.put(Context.SECURITY_PRINCIPAL,authInfo.getUserDn());
            env.put(Context.SECURITY_CREDENTIALS,authInfo.getUserCredentials());
            if (LdapEngineConstants.ENGINE_ACTIVE_DIRECTORY.equals(ldapContextConfig.getLdapEngine())){
                env.put(LdapConstants.CONTEXT_ATTRIBUTES_BINARY, ldapContextConfig.getLdapGuidAttribute());
            }

            LdapContext ctx = null;
            try {
                ctx = new InitialLdapContext(env,null);
                Attributes attrs = ctx.getAttributes(authInfo.getUserDn(),new String [] {ldapContextConfig.getLdapGuidAttribute()});
                Attribute attr = attrs.get(ldapContextConfig.getLdapGuidAttribute());
                if (LdapEngineConstants.ENGINE_ACTIVE_DIRECTORY.equals(ldapContextConfig.getLdapEngine())){
                    ret = LdapFormatUtils.formatGuid(ldapContextConfig.getLdapEngine(), attr.get());
                } else {
                    ret = (String) attr.get();
                }
            } catch (javax.naming.NamingException e) {
                LdapExceptionUtils.generateErrorException(LdapErrorCodes.ERR_10002_AUTHENTICATION_PROBLEM, LOGGER, e);
            } finally {
                if (ctx != null) {
                    try {
                        ctx.close();
                    } catch (NamingException e) {
                        LdapLogDebugUtils.generateDebug(LdapErrorCodes.ERR_10003_GENERIC_PROBLEM, LOGGER, e);
                    }
                }
            }

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("authenticate(LdapAuthenticationInfoVO=" + authInfo
                        + ",LdapContextConfigLoader=" + ldapCtxCfgLoader
                        + ") - --> Salida  - valor de retorno=" + ret);
            }
        }

        return ret;
    }

    /**
     * Parsea los controles para comprobar si hay otra pagina para obtener
     * @param controls Controles de la peticion
     * @return cookie de la siguiente pagina
     * @throws NamingException
     */
    private byte[] parseControls(final Control[] controls) {

        byte[] cookie = null;

        try {
            if (controls != null) {

                Method method = null;
                Class [] paramTypes = new Class[] {};
                for (int i = 0; i < controls.length; i++) {
                    if (controls[i].getClass().equals(responsePagedResultsControlClass)) {
                        method = LdapReflectionUtils.getClassMethod(controls[i].getClass(), RESPONSE_PAGED_RESULTS_CONTROL_METHOD, paramTypes);
                        cookie = (byte [])method.invoke(controls[i], paramTypes);
                    }
                }
            }
        } catch (IllegalArgumentException e) {
            LdapExceptionUtils.generateErrorException(LdapErrorCodes.ERR_10009_PAGINATED_RESULT_COOKIE_PARSE_ERROR, LOGGER, e);
        } catch (IllegalAccessException e) {
            LdapExceptionUtils.generateErrorException(LdapErrorCodes.ERR_10009_PAGINATED_RESULT_COOKIE_PARSE_ERROR, LOGGER, e);
        } catch (InvocationTargetException e) {
            LdapExceptionUtils.generateErrorException(LdapErrorCodes.ERR_10009_PAGINATED_RESULT_COOKIE_PARSE_ERROR, LOGGER, e);
        }

        return (cookie == null) ? new byte[0] : cookie;
    }

    /**
     * Establece el dn y el guid para una entrada del arbol Ldap
     * @param entry Entrada del arbol Ldap
     * @param ldapContextConfig Configuracion del arbol Ldap
     */
    private void processEntryDnGuid(final LdapEntryVO entry, final LdapContextConfig ldapContextConfig){
        LdapEntryAttributeVO ldapEntryAttr = entry.getAttribute(ldapContextConfig.getLdapDnAttribute());
        if (ldapEntryAttr!=null){
            String fullDn = (String)ldapEntryAttr.getValue();
            String baseDn = ldapContextConfig.getLdapBase();
            entry.setFullDn(fullDn);
            entry.setRelativeDn(calculateRelativeDn(fullDn, baseDn));
        }

        ldapEntryAttr = entry.getAttribute(ldapContextConfig.getLdapGuidAttribute());
        if (ldapEntryAttr!=null) {
            if (LdapEngineConstants.ENGINE_ACTIVE_DIRECTORY.equals(ldapContextConfig.getLdapEngine())){
                String ldapguid = LdapFormatUtils.formatGuid(ldapContextConfig.getLdapEngine(), ldapEntryAttr.getValue());
                ldapEntryAttr.setValue(ldapguid);
                entry.setLdapGuid(ldapguid);
            } else {
                entry.setLdapGuid((String)ldapEntryAttr.getValue());
            }
        }
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

        List result = new ArrayList();
        LdapContextConfig ldapContextConfig = ldapCtxCfgLoader.loadLdapContextConfig();

        if ((ldapContextConfig == null)
                || (ldapContextConfig.getLdapPassword() == null)
                || ("".equals(ldapContextConfig.getLdapPassword()))) {
            LdapExceptionUtils.generateErrorException(LdapErrorCodes.ERR_10000_AUTHENTICACION_CREDENTIALS_EMPTY, LOGGER);
        } else {

            Hashtable env = new Hashtable();
            env.put(Context.PROVIDER_URL, ldapContextConfig.getLdapUrl());
            env.put(Context.INITIAL_CONTEXT_FACTORY, LdapConstants.INITIAL_CONTEXT_FACTORY);
            env.put(Context.SECURITY_AUTHENTICATION, LdapConstants.SECURITY_AUTHENTICATION);
            env.put(Context.SECURITY_PRINCIPAL,ldapContextConfig.getLdapUser());
            env.put(Context.SECURITY_CREDENTIALS,ldapContextConfig.getLdapPassword());
            if (LdapEngineConstants.ENGINE_ACTIVE_DIRECTORY.equals(ldapContextConfig.getLdapEngine())){
                env.put(LdapConstants.CONTEXT_ATTRIBUTES_BINARY, ldapContextConfig.getLdapGuidAttribute());
            }

            if (ldapContextConfig.isLdapPool()){
                 env.put(LdapConstants.POOL_ENV_PARAMETER, Boolean.toString(ldapContextConfig.isLdapPool()));
                 env.put(LdapConstants.POOL_TIMEOUT_ENV_PARAMETER, ldapContextConfig.getLdapPoolTimeout());
            }

            LdapContext ctx = null;
            try {
                ctx = new InitialLdapContext(env,null);

                loadPagedResultsControlClasses();

                boolean pagedResult = checkSupportedControl(LdapConstants.SIMPLE_PAGED_RESULTS_CONTROL, ctx);

                SearchControls searchCtls = new SearchControls();

                int scope = SearchControls.OBJECT_SCOPE;
                if (StringUtils.isNotEmpty(ldapSearch.getScope())){
	                try{
	                	scope = Integer.valueOf(ldapSearch.getScope()).intValue();
	                } catch (Exception e) {
	                	LdapLogDebugUtils.generateDebug(LdapErrorCodes.ERR_10003_GENERIC_PROBLEM, LOGGER, e);
	                }
                }

                searchCtls.setSearchScope(scope);
                String [] retAttrs = ldapSearch.getRetAttrs();
                if ((retAttrs!=null)&&(retAttrs.length>0)) {
                    searchCtls.setReturningAttributes(retAttrs);
                }

                if (ldapSearch.getExpected()>LdapConstants.NO_SEARCH_LIMIT) {
                    searchCtls.setCountLimit(ldapSearch.getExpected());
                }

                byte[] cookie = null;
                int pageSize = LdapConstants.PAGE_SIZE;

                if (pagedResult) {
                    Control[] ctls = new Control[]{ LdapReflectionUtils.createPagedResultsRequestControl(requestPagedResultsControlClass, pageSize,Control.CRITICAL)};
                    ctx.setRequestControls(ctls);
                }

                do {
                    NamingEnumeration results = ctx.search(calculateFullDn(ldapSearch.getBase(), ldapContextConfig.getLdapBase()) , ldapSearch.getStrFilter(), searchCtls);
                    while (results != null && results.hasMoreElements()) {
                        SearchResult searchResult = (SearchResult)results.next();
                        Attributes attributes = searchResult.getAttributes();
                        LdapEntryVO entry = getLdapMapper().mapAttributes(attributes);
                        processEntryDnGuid(entry, ldapContextConfig);
                        result.add(entry);
                    }
                    if (pagedResult) {
                        cookie = parseControls(ctx.getResponseControls());
                        ctx.setRequestControls(new Control[]{ LdapReflectionUtils.createPagedResultsRequestControl(requestPagedResultsControlClass, pageSize, cookie, Control.CRITICAL) });
                    }
                } while ((cookie != null) && (cookie.length != 0));
            } catch (NamingException e) {
                LdapExceptionUtils.generateErrorException(LdapErrorCodes.ERR_10003_GENERIC_PROBLEM, LOGGER, e);
            } finally {
                if (ctx != null){
                    try {
                        ctx.close();
                    } catch (NamingException e) {
                        LdapLogDebugUtils.generateDebug(LdapErrorCodes.ERR_10003_GENERIC_PROBLEM, LOGGER, e);
                    }
                }
            }
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("find(LdapSearchVO="
                    + ldapSearch + ",LdapContextConfigLoader="
                    + ldapCtxCfgLoader
                    + ") - --> Salida  - valor de retorno=" + result);
        }

        int expectedResults = ldapSearch.getExpected();
        if ((expectedResults>LdapConstants.NO_SEARCH_LIMIT)&&
                (result!=null)&&(result.size()>expectedResults)){
            LdapExceptionUtils.generateErrorException(LdapErrorCodes.ERR_10004_RESULT_SIZE_NOT_MATCH_EXPECTED, LOGGER);
        }

        return result;
    }

}
