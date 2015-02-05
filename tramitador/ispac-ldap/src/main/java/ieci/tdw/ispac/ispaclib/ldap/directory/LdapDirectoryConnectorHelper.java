package ieci.tdw.ispac.ispaclib.ldap.directory;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACNullObject;
import ieci.tdw.ispac.api.item.IResponsible;
import ieci.tdw.ispac.ispaclib.directory.IDirectoryEntry;
import ieci.tdw.ispac.ispaclib.ldap.common.HumanReadableAttr;
import ieci.tdw.ispac.ispaclib.ldap.config.LdapCfg;
import ieci.tdw.ispac.ispaclib.ldap.utils.LdapUtils;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.naming.CommunicationException;
import javax.naming.Context;
import javax.naming.NameNotFoundException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.apache.log4j.Logger;

public class LdapDirectoryConnectorHelper {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(LdapDirectoryConnectorHelper.class);

	private static LdapDirectoryConnectorHelper mInstance = null;

	private DirContext m_dirctx = null;
	private Date lastConnection = null;

	/**
	 * Constructor.
	 * @throws ISPACException si ocurre algún error.
	 */
	private LdapDirectoryConnectorHelper() throws ISPACException{
		initDirContext();
	}

	/**
	 * Obtiene una instancia de la clase.
	 * @return Instancia de la clase.
	 * @throws ISPACException si ocurre algún error.
	 */
	public static synchronized LdapDirectoryConnectorHelper getInstance() throws ISPACException {
		
		if (mInstance == null) {
			mInstance = new LdapDirectoryConnectorHelper();
		}
		
		return mInstance;
	}

	private void initDirContext() throws ISPACException {
		LdapCfg cfg = LdapCfg.getInstance();
		m_dirctx = connect(cfg.getAdminUser(), cfg.getAdminPwd());
	}


	/**
	 * Establece conexion con el directorio con el dn y password pasados como
	 * parametros
	 *
	 * @param sDN dn del usuario
	 * @param sPwd passwd
	 * @throws ISPACException si se produce error
	 */
	protected DirContext connect(String sDN, String sPwd) throws ISPACException {

		LdapCfg cfg = LdapCfg.getInstance();
		DirContext ctx = null;
		if (sDN != null) {

			Hashtable env = cfg.getCnxEnv();
			env.put(Context.SECURITY_PRINCIPAL, escapeRootCharacters(sDN));
			env.put(Context.SECURITY_CREDENTIALS, sPwd);

			try {
				ctx = new InitialDirContext(env);
			} catch (NamingException e) {
				logger.error("Error al conectar", e);

				String sError = "LdapDirectoryConnectorHelper:Connect(" + sDN + "," + sPwd + "): " + e.toString();
				throw new ISPACException(sError, e);
			}
		}
		return ctx;
	}

	/**
	 * Devuelve el contexto root
	 *
	 * @return context-root
	 */
	public String getContextRoot() throws ISPACException {
		return LdapCfg.getInstance().getOrgRootDN();
	}

	/**
	 * Autenticación con el directorio
	 *
	 * @param sUser nombre usuario
	 * @param sPwd password usuario
	 * @throws ISPACException si se produce error
	 */
	public IDirectoryEntry login(String sUser, String sPwd)
			throws ISPACException {

		if (StringUtils.isBlank(sUser)) {
			throw new ISPACException("LdapDirectoryConnectorHelper:login(): El login del usuario es obligatorio");
		}

		if (StringUtils.isBlank(sPwd)) {
			throw new ISPACException("LdapDirectoryConnectorHelper:login(): La contraseña del usuario es obligatoria");
		}

		String sDN = null;
		LdapCfg cfg = LdapCfg.getInstance();

		LdapEntry entry = (LdapEntry) getEntryFromUserName(sUser);
		if (entry != null) {
			sDN = entry.getRelativeDN() + "," + cfg.getRootDN();
		}

		connect(sDN, sPwd);

		return entry;
	}

	/**
	 * Devuelve un objeto LdapEntry con la información asociada a la entrada
	 * ldap con dn igual a respDN. Este DN debe ser absoluto
	 *
	 * @param respDN dn absoluto
	 * @param ISPACException si se produce error
	 * @return objeto LdapEntry
	 * @throws ISPACExpcetion si se produce algun error
	 */
	public IDirectoryEntry getEntryFromDN(String respDN) throws ISPACException {

		LdapCfg cfg = LdapCfg.getInstance();
		LdapEntry entry = null;
		String searchString = "(objectclass=*)";

		NamingEnumeration ne = search(respDN, searchString, SearchControls.OBJECT_SCOPE);

		if (ne != null) {
			entry = LdapEntry.createLdapEntry(ne, cfg.toRelativeDN(respDN),
					SearchControls.OBJECT_SCOPE);
		}

		if (entry == null) {
			throw new ISPACNullObject("LdapDirectoryConnectorHelper:getRespFromDN( " + respDN
					+ "): responsable no encontrado");
		}

		return entry;
	}

	/**
	 * Devuelve un objeto LdapEntry con el uid
	 *
	 * @param respUID
	 * @return objeto LdapEntry
	 * @throws ISPACExpcetion si se produce algun error
	 */
	public IDirectoryEntry getEntryFromUID(String respUID) throws ISPACException {

		LdapCfg cfg = LdapCfg.getInstance();
		Object[] args = new Object[1];
		LdapEntry entry = null;

		if (cfg.isBinaryUUID()) {
			args[0] = LdapEntry.GuidToByteArray(respUID);
		} else {
			args[0] = respUID;
		}

		if (args[0] != null) {

			String searchString = "(" + cfg.getGUIDAttName() + "={0})";

			NamingEnumeration ne = search(cfg.getRootDN(), searchString, args,
					SearchControls.SUBTREE_SCOPE);
			if (ne != null) {
				entry = LdapEntry.createLdapEntry(ne);
			}
		}

		if (entry == null) {
			throw new ISPACNullObject("LdapDirectoryConnectorHelper:getRespFromUID( " + respUID
					+ "): responsable no encontrado");
		}

		return entry;
	}

	/**
	 * Devuelve un objeto LdapEntry con la información asociada a la entrada
	 * ldap del usuario pasado como parametro
	 *
	 * @param userName nombre del usuario
	 * @param ISPACException si se produce error
	 * @return objeto LdapEntry
	 * @throws ISPACExpcetion si se produce algun error
	 */
	public IDirectoryEntry getEntryFromUserName(String userName)
			throws ISPACException {


		LdapCfg cfg = LdapCfg.getInstance();
		LdapEntry entry = null;
		String searchString = "(&(objectclass=" + cfg.getPersonObjectClass()
			+ ")(" + cfg.getPersonLoginAttName() + "=" + userName + "))";

		NamingEnumeration ne = search(cfg.getRootDN(), searchString,
				SearchControls.SUBTREE_SCOPE);
		if (ne != null) {
			entry = LdapEntry.createLdapEntry(ne);
		}
		
		if (entry == null) {
			throw new ISPACNullObject("LdapDirectoryConnectorHelper:getEntryFromUserName( " + userName
					+ "): usuario no encontrado");
		}

		return entry;
	}

	public Set getEntryFromName(String name, String []elementos) throws ISPACException {
		
		LdapCfg cfg = LdapCfg.getInstance();
		String searchString = "(|" +
		  					  "(&(objectclass=" + cfg.getDomainObjectClass() + ")(" + cfg.getDomainLabelAttName() + "=*" + name + "*)) " +
		  					  "(&(objectclass=" + cfg.getUnitObjectClass()+ ")(" + cfg.getUnitLabelAttName() + "=*" + name + "*))" ;
		if(elementos==null){
			searchString +="(&(objectclass=" + cfg.getPersonObjectClass() + ")(" + cfg.getPersonLabelAttName() + "=*" + name + "*)) " +
							"(&(objectclass=" + cfg.getGroupObjectClass() + ")(" + cfg.getGroupLabelAttName() + "=*" + name + "*)) " +
							"(&(objectclass=" + cfg.getOrganizationObjectClass()+ ")(" + cfg.getOrganizationLabelAttName() + "=*" + name + "*))" ;
								
		} else {
			for(int i=0; i<elementos.length; i++){
			
				if(StringUtils.equalsIgnoreCase(elementos[i], IResponsible.PROPERTY_GROUP)){
					searchString+=	"(&(objectclass=" + cfg.getGroupObjectClass() + ")(" + cfg.getGroupLabelAttName() + "=*" + name + "*)) " ;
				} else if(StringUtils.equalsIgnoreCase(elementos[i], IResponsible.PROPERTY_USER)){
					searchString +="(&(objectclass=" + cfg.getPersonObjectClass() + ")(" + cfg.getPersonLabelAttName() + "=*" + name + "*)) " ;
				} else if(StringUtils.equalsIgnoreCase(elementos[i], IResponsible.PROPERTY_ORGUNIT)){
					searchString+=	"(&(objectclass=" + cfg.getOrganizationObjectClass()+ ")(" + cfg.getOrganizationLabelAttName() + "=*" + name + "*))" ;
				}
			}
		}
		
		searchString+=")";
		LinkedHashSet entries = new LinkedHashSet();
		NamingEnumeration ne = search(cfg.getRootDN(), searchString, SearchControls.SUBTREE_SCOPE );
		addEntries(ne, entries);
		
		return entries;
	
	}

	private void addEntries(NamingEnumeration ne, Set entries) throws ISPACException{
		LdapEntry entry = null;
		do
		{
			try {
				entry = LdapEntry.createLdapEntry(ne);
			} catch (ISPACException e) {
				logger.error("LdapDirectoryConnector::addEntries", e);
				throw new ISPACException("Error en LdapDirectoryConnectorHelper::addEntries: " + e);
			}
			if (entry != null)
				entries.add(entry);
		} while (entry != null);

	}
	
	/**
	 * Realiza busquedas en el directorio
	 *
	 * @param root entrada ldap que es raiz de la búsqueda
	 * @param searchString filtro de búsqueda
	 * @param scope ambito de la busqueda
	 * @return enumeracion con los resultados de la busqueda
	 * @throws ISPACException si se produce algun error
	 */
	protected NamingEnumeration search(String root, String searchString,
			int scope) throws ISPACException {

		NamingEnumeration ne = null;
		
		SearchControls sc = new SearchControls();
		sc.setSearchScope(scope);
		sc.setReturningAttributes(getReturningAttributes());

		try {
			ne = search(root, searchString, null, sc);
		} catch (NameNotFoundException e) {
			logger.warn("No se han encontrado resultados: root=[" + root
					+ "], searchString=[" + searchString + "], scope=[" + scope
					+ "]", e);
		} catch (NamingException e) {
			logger.error("Error en la búsqueda LDAP: root=[" + root
					+ "], searchString=[" + searchString + "], scope=[" + scope
					+ "]", e);
			throw new ISPACException("Error en la búsqueda LDAP", e);
		}
		return ne;
	}

	protected NamingEnumeration search(String root, String searchString,
			Object[] objects, int scope) throws ISPACException {

		NamingEnumeration ne = null;
		
		SearchControls sc = new SearchControls();
		sc.setSearchScope(scope);
		sc.setReturningAttributes(getReturningAttributes());

		try{
			ne = search(root, searchString, objects, sc);
		} catch (NameNotFoundException e) {
			logger.warn("No se han encontrado resultados: root=[" + root
					+ "], searchString=[" + searchString + "], objects=["
					+ objects + "], scope=[" + scope + "]", e);
		} catch (NamingException e) {
			logger.error("Error en la búsqueda LDAP: root=[" + root
					+ "], searchString=[" + searchString + "], objects=["
					+ objects + "], scope=[" + scope + "]", e);
			throw new ISPACException("Error en la búsqueda LDAP", e);
		}

		return ne;
	}

	private NamingEnumeration searchNoAttrsFilter(String root, String searchString,
			Object[] objects, int scope) throws ISPACException {

		NamingEnumeration ne = null;
		
		SearchControls sc = new SearchControls();
		sc.setSearchScope(scope);

		try{
			ne = search(root, searchString, objects, sc);
		} catch (NameNotFoundException e) {
			logger.warn("No se han encontrado resultados: root=[" + root
					+ "], searchString=[" + searchString + "], objects=["
					+ objects + "], scope=[" + scope + "]", e);
		} catch (NamingException e) {
			logger.error("Error en la búsqueda LDAP: root=[" + root
					+ "], searchString=[" + searchString + "], objects=["
					+ objects + "], scope=[" + scope + "]", e);
			throw new ISPACException("Error en la búsqueda LDAP", e);
		}

		return ne;
	}

	private NamingEnumeration search(String root, String searchString,
			Object[] objects, SearchControls searchControls) throws ISPACException, NamingException {

		String escapedSearchString = escapeFilterCharacters(searchString);
		String espacedRoot= escapeRootCharacters(root);

		NamingEnumeration ne = null;

		try {
			ne = m_dirctx.search(espacedRoot, escapedSearchString, objects, searchControls);
			lastConnection = new Date();
		} catch(CommunicationException e) {
			if ((e.getCause() != null) && (e.getCause()  instanceof IOException)) {
				logger.warn("Error en la conexión. Última conexión válida: "+lastConnection+". Intentando reconexión...", e);
				initDirContext();
				ne = m_dirctx.search(espacedRoot, escapedSearchString, objects, searchControls);
				lastConnection = new Date();
			} else {
				logger.error("Error en la búsqueda LDAP: root=[" + espacedRoot
						+ "], searchString=[" + escapedSearchString + "], objects=["
						+ objects + "], searchControls=[" + searchControls + "]", e);
				throw e;
			}
		}
		
		return ne;
	}

	private Attributes getAttrsFromUID(String respUID) throws ISPACException {

		SearchResult result = null;
		Object[] args = new Object[1];
		LdapCfg cfg = LdapCfg.getInstance();
		try {
			if (cfg.isBinaryUUID())
				args[0] = LdapEntry.GuidToByteArray(respUID);
			else
				args[0] = respUID;

			if (args[0] != null) {

				String searchString = "(" + cfg.getGUIDAttName() + "={0})";
				NamingEnumeration ne = searchNoAttrsFilter(cfg.getRootDN(), searchString, args,
						SearchControls.SUBTREE_SCOPE);
				if (ne != null && ne.hasMore())
					result = (SearchResult) ne.next();
				else if (ne == null)
					throw new ISPACNullObject("LdapDirectoryConnectorHelper:getEntryFromUID( "
							+ respUID + "): entrada no encontrada");
			}
		} catch (NamingException e) {
			logger.error("Error al obtener los atributos", e);
			throw new ISPACException("LdapDirectoryConnectorHelper:getEntryFromUID( " + respUID
					+ "): " + e);
		}
		if (result != null)
			return result.getAttributes();

		return null;
	}

//	/**
//	 * Obtiene un DN a partir del nombre de usuario
//	 *
//	 * @param userName nombre del usuario
//	 * @return DN del usuario
//	 * @throws ISPACException si se produce error
//	 */
//	protected String DNFromUserName(String userName) throws ISPACException {
//		LdapCfg cfg = LdapCfg.getInstance();
//		LdapEntry entry = (LdapEntry) getEntryFromUserName(userName);
//		if (entry != null)
//			return entry.getRelativeDN() + "," + cfg.getRootDN();
//
//		return null;
//	}
//
//	/**
//	 * Obtiene el UID de un usuario
//	 *
//	 * @param userName nombre de usuario
//	 * @return UID del usuario
//	 * @throws ISPACException si se produce error
//	 */
//	public String UIDFromUserName(String userName) throws ISPACException {
//		LdapEntry entry = (LdapEntry) getEntryFromUserName(userName);
//		if (entry != null) {
//			return entry.getUID();
//		}
//		return null;
//	}
//
//	/**
//	 * Cambia el password de un usuario
//	 *
//	 * @param sUser usuario
//	 * @param sOldPwd password antiguo
//	 * @param sNewPwd nuevo password
//	 * @throws ISPACException si se produce algun error
//	 */
//	public void changePassword(String sUser, String sOldPwd, String sNewPwd)
//			throws ISPACException {
//		login(sUser, sOldPwd);
//		String name = DNFromUserName(sUser);
//		String attrName = "userpassword";
//		String attrValue = sNewPwd;
//		setAttribute(name, attrName, attrValue);
//	}
//
//	/**
//	 * Cambia el valor asociado a un atributos de una entrada
//	 *
//	 * @param name nombre de la entrada
//	 * @param attrName nombre del atributo
//	 * @param valor del atributo
//	 * @throws ISPACException si se produce error
//	 */
//	protected void setAttribute(String name, String attrName, String attrValue)
//			throws ISPACException {
//
//		LdapCfg cfg = LdapCfg.getInstance();
//		DirContext ctx = connect(cfg.getAdminUser(), cfg.getAdminPwd());
//		ModificationItem[] mi = new ModificationItem[1];
//		mi[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute(attrName,
//				attrValue));
//		try {
//			ctx.modifyAttributes(name, mi);
//			ctx.close();
//		} catch (NamingException e) {
//			logger.error("Error al establecer el atributo: "
//					+ name + "," + attrName + "," + attrValue, e);
//			String errMsg = "LdapDirectoryConnectorHelper:setAttribute( " + name + "," + attrName
//				+ "," + attrValue + ")";
//			throw new ISPACException(errMsg, e);
//		}
//	}
//
//	/**
//	 * Función que devuelve las entradas ldap desde un determinado DN, aplicando
//	 * un filtro y con un cierto ambito
//	 *
//	 * @param dnRelative DN relativo del cual obtener los DN 'hijos'
//	 * @param filter filtro que se aplica en la busqueda
//	 * @param scope ambito de la busqueda
//	 * @return Vector de entradas
//	 * @throws ISPACException si se produce error
//	 */
//	public Vector getChildEntriesFromDN(String dnRelative, String filter, int scope)
//			throws ISPACException
//	{
//		LdapCfg cfg = LdapCfg.getInstance();
//		LdapEntry entry = null;
//		Vector entries = new Vector();
//		String dn = cfg.toAbsoluteDN(dnRelative);
//		NamingEnumeration ne = search(dn, filter, scope);
//		do
//		{
//			entry = LdapEntry.createLdapEntry(ne, dnRelative, scope);
//			if (entry != null)
//				entries.add(entry);
//		} while (entry != null);
//
//		return entries;
//	}

	/**
	 * Función que devuelve las entradas ldap desde un determinado DN, aplicando
	 * un filtro y con un cierto ambito
	 *
	 * @param dnRelative DN relativo del cual obtener los DN 'hijos'
	 * @param filter filtro que se aplica en la busqueda
	 * @param scope ambito de la busqueda
	 * @return Vector de entradas
	 * @throws ISPACException si se produce error
	 */
	public Set getChildEntries(String dnRelative, String filter, int scope) throws ISPACException
	{
		LdapCfg cfg = LdapCfg.getInstance();
		LdapEntry entry = null;
		LinkedHashSet entries = new LinkedHashSet();
		String dn = cfg.toAbsoluteDN(dnRelative);
		NamingEnumeration ne = search(dn, filter, scope);
		do
		{
			entry = LdapEntry.createLdapEntry(ne, dnRelative, scope);
			if (entry != null)
				entries.add(entry);
		} while (entry != null);


		return entries;
	}

	/**
	 * Calcula los ancestros de un determinado UID
	 *
	 * @param dn dn abssoluto del nodo sobre el cual calcular los ancestros
	 * @return los ancestros
	 * @throws ISPACException
	 */
	public Set getAncestorsFromUID(String respUID) throws ISPACException {
		LdapEntry entry = (LdapEntry) getEntryFromUID(respUID);
		LinkedHashSet ancestors = new LinkedHashSet();
		return getAncestors(entry, ancestors);
	}

	private Set getAncestors(LdapEntry entry, LinkedHashSet ancestors) throws ISPACException {

		LdapCfg cfg = LdapCfg.getInstance();
		String dn = entry.getDN();

		if (cfg.belongToRootDN(dn)) {
			ancestors.add(entry);
			if (!dn.equalsIgnoreCase(cfg.getOrgRootDN())
					&& !dn.equalsIgnoreCase(cfg.getGroupsRootDN())) {

				String parentDN = LdapUtils.getParentDN(dn);
				LdapEntry parent = (LdapEntry) getEntryFromDN(parentDN);
				getAncestors(parent, ancestors);
			}
		} else {
			throw new ISPACException(
					"Error en LdapDirectoryConnectorHelper:getAncestors(): el dn pasado como parametro está fuera de contexto");
		}
		return ancestors;
	}


	public Set getAllGroups() throws ISPACException
	{

		LdapCfg cfg = LdapCfg.getInstance();
		LdapEntry entry = null;
		LinkedHashSet entries = new LinkedHashSet();
		NamingEnumeration ne = search(cfg.getGroupsRootDN(), "(objectclass=" + cfg.getGroupObjectClass() + ")",
				SearchControls.SUBTREE_SCOPE);
		do
		{
			entry = LdapEntry.createLdapEntry(ne);
			if (entry != null)
				entries.add(entry);
		} while (entry != null);


		return entries;
	}

	public Set getMembersFromUID( String respUID)
	throws ISPACException
	{

		HashSet set = new HashSet();
		LdapEntry entry = (LdapEntry) getEntryFromUID(respUID);
		String[] members = entry.getMembers();
		if (members != null) {
			for (int i = 0; i< members.length; i++)
			{
				entry = (LdapEntry) getEntryFromDN(members[i]);
				if ((entry != null) && entry.isPerson()) {
					set.add(entry);
				}
			}
		}

		return set;

	}

	public IDirectoryEntry getEntryFromRoot()
	throws ISPACException
	{
		LdapCfg cfg = LdapCfg.getInstance();
		return getEntryFromDN(cfg.getOrgRootDN());

	}

	public Set getOrgUnitsFromUID( String respUID)
	throws ISPACException
	{

		LdapCfg cfg = LdapCfg.getInstance();
		LdapEntry entry = (LdapEntry) getEntryFromUID(respUID);
		Set orgUnits = new LinkedHashSet();

		if (StringUtils.isNotBlank(cfg.getOrganizationObjectClass())) {
			orgUnits.addAll(getChildEntries(entry.getRelativeDN(),
					"(objectclass=" + cfg.getOrganizationObjectClass() + ")",
					SearchControls.ONELEVEL_SCOPE));
		}

		if (StringUtils.isNotBlank(cfg.getUnitObjectClass())
				&& !StringUtils.equals(cfg.getUnitObjectClass(), cfg.getOrganizationObjectClass())) {
			orgUnits.addAll(getChildEntries(entry.getRelativeDN(),
					"(objectclass=" + cfg.getUnitObjectClass() + ")",
					SearchControls.ONELEVEL_SCOPE));
		}
		return orgUnits;
	}

	public Set getUsersFromUID( String respUID)
	throws ISPACException
	{
		LdapCfg cfg = LdapCfg.getInstance();
		LdapEntry entry = (LdapEntry) getEntryFromUID(respUID);
		String searchExpr = "(objectclass=" + cfg.getPersonObjectClass() + ")";
		return getChildEntries(entry.getRelativeDN(),
			searchExpr,
			SearchControls.ONELEVEL_SCOPE);
	}

	public LinkedHashMap getPropertiesFromUID(String respUID)
	throws ISPACException
	{
		LinkedHashMap attributes = new LinkedHashMap();
		try {
			Attributes attrs = getAttrsFromUID(respUID);
			if (attrs != null) {
				NamingEnumeration neAttrs = attrs.getAll();
				while (neAttrs.hasMore()) {
					Attribute attr = (Attribute) neAttrs.next();
					String name = attr.getID();
					if (HumanReadableAttr.isReadable(attr))
					{
						NamingEnumeration list = attr.getAll();
						List values = new ArrayList();
						boolean addAttribute = true;
						while (list.hasMore()) {
							Object value = list.next();
							if (value instanceof String)
								values.add(value);
							else {
								addAttribute = false;
								break;
							}
						}
						if (addAttribute && !values.isEmpty())
							attributes.put(name, values);
					}
				}
			}
		} catch (NamingException e) {
			logger.error("Error al obtener las propiedades", e);
			throw new ISPACException("Error en LdapDirectoryConnectorHelper::getAttributes: " + e);
		}
		return attributes;
	}

	protected String[] getReturningAttributes() throws ISPACException {

		LdapCfg cfg = LdapCfg.getInstance();

		List attrList = new ArrayList();
		attrList.add("objectclass");

		if (StringUtils.isNotBlank(cfg.getGUIDAttName())
				&& !attrList.contains(cfg.getGUIDAttName())) {
			attrList.add(cfg.getGUIDAttName());
		}

		if (StringUtils.isNotBlank(cfg.getMemberAttName())
				&& !attrList.contains(cfg.getMemberAttName())) {
			attrList.add(cfg.getMemberAttName());
		}

		if (StringUtils.isNotBlank(cfg.getCNAttName())
				&& !attrList.contains(cfg.getCNAttName())) {
			attrList.add(cfg.getCNAttName());
		}

		if (StringUtils.isNotBlank(cfg.getPersonLabelAttName())
				&& !attrList.contains(cfg.getPersonLabelAttName())) {
			attrList.add(cfg.getPersonLabelAttName());
		}
		if (StringUtils.isNotBlank(cfg.getPersonLoginAttName())
				&& !attrList.contains(cfg.getPersonLoginAttName())) {
			attrList.add(cfg.getPersonLoginAttName());
		}
		if (StringUtils.isNotBlank(cfg.getGroupLabelAttName())
				&& !attrList.contains(cfg.getGroupLabelAttName())) {
			attrList.add(cfg.getGroupLabelAttName());
		}
		if (StringUtils.isNotBlank(cfg.getUnitLabelAttName())
				&& !attrList.contains(cfg.getUnitLabelAttName())) {
			attrList.add(cfg.getUnitLabelAttName());
		}
		if (StringUtils.isNotBlank(cfg.getOrganizationLabelAttName())
				&& !attrList.contains(cfg.getOrganizationLabelAttName())) {
			attrList.add(cfg.getOrganizationLabelAttName());
		}
		if (StringUtils.isNotBlank(cfg.getDomainLabelAttName())
				&& !attrList.contains(cfg.getDomainLabelAttName())) {
			attrList.add(cfg.getDomainLabelAttName());
		}



/*
		String[] attrIDs = new String[] {
			cfg.getGUIDAttName(),
			"objectclass",
			cfg.getMemberAttName(),
			cfg.getCNAttName(),
			cfg.getPersonLabelAttName(),
			cfg.getPersonLoginAttName(),
			cfg.getGroupLabelAttName(),
			cfg.getUnitLabelAttName(),
			cfg.getOrganizationLabelAttName(),
			cfg.getDomainLabelAttName()
		};
*/

		return (String[]) attrList.toArray(new String[attrList.size()]);
	}


	private String escapeFilterCharacters(String filter) {

		filter = StringUtils.replace(filter, "\\", "\\5c");

		return filter;
	}

	private String escapeRootCharacters(String root){
		root=StringUtils.replace(root,"\\\\\\", "\\5c");
		if(StringUtils.contains(root, "\"") && !StringUtils.contains(root, "\\\\\"")){
			root=StringUtils.replace(root, "\"", "");
		}

		if(StringUtils.containsIgnoreCase(root, "CN")){
			root=StringUtils.replace(root,"\\\\\"" ,"\\\"");
		}
		else{
			root=StringUtils.replace(root, "/", "\\/");
			root=StringUtils.replace(root,"\\\\\"" ,"\\\\\\\"");
		}


		return root;
	}

}
