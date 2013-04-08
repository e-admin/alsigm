package ieci.tdw.ispac.ispaclib.ldap.directory;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.directory.IDirectoryEntry;
import ieci.tdw.ispac.ispaclib.ldap.config.LdapCfg;
import ieci.tdw.ispac.ispaclib.ldap.utils.LdapUtils;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.apache.log4j.Logger;

/**
 * Clase que representa un entry del directorio
 */
public class LdapEntry implements IDirectoryEntry {

	private static final long serialVersionUID = -4763571942948196054L;

	private static final Logger logger = Logger.getLogger(LdapEntry.class);
	
	/**
	 * Tipo de entrada
	 */ 
	private int m_entryType;
	
	/**
	 * Nombre usuario
	 */	
	private String m_commonName;
	
	/**
	 * DN relativo al contexto-root
	 */
	private String m_relativeDN;
	
	/**
	 * UID de la entrada
	 */
	private String m_UID;
	
	/**
	 * Array de miembros: solo para grupos
	 */
	private String[] m_members;

	/**
	 * Nombre largo de usuario
	 */
	private String msLongName;
	
	//private LdapEntry m_deptEntry;
	private LdapEntry m_parentEntry;
	private LdapEntry[] m_grpEntries;

	
	// Nombre de atributos
	private final String OBJECTCLASS_ATTNAME = "objectclass";
	//private final String MEMBEROF_ATTNAME = "memberof";

	/**
	 * Inicializa el objeto a a partir de SearchResult .
	 * T&iacute;picamente se llama a este constructor cuando el parametro entry
	 * se ha obtenido en una b&uacute;squeda donde su ra&iacute;z es
	 * el <code>context root</code>.
	 *
	 * @param entry la entrada ldap
	 * @throws ISPACException si se produce algún error
	 */
	private LdapEntry(SearchResult entry) throws ISPACException
	{
		m_entryType = ET_UNDEFINED;

		LdapCfg cfg = LdapCfg.getInstance();
		m_relativeDN = entry.getName();
		Attributes attrs = entry.getAttributes();
		NamingEnumeration neAttrs = attrs.getAll();
		
		if (neAttrs != null) {
			try {
				
				String personLabel = null;
				String groupLabel = null;
				String unitLabel = null;
				String organizationLabel = null;
				String domainLabel = null;
				
				while (neAttrs.hasMoreElements()) {
					
					Attribute attr = (Attribute) neAttrs.next();

					if (attr.getID().equalsIgnoreCase(cfg.getGUIDAttName())) {
						m_UID = getUIDValue(attr);
					} else if (attr.getID().equalsIgnoreCase(cfg.getMemberAttName())) {
						m_members = getMembers (attr);
					} else if (attr.getID().equalsIgnoreCase(OBJECTCLASS_ATTNAME)) {
						m_entryType = getTypeValue(attr);
					} else if (attr.getID().equalsIgnoreCase(cfg.getCNAttName())) {
						m_commonName = getCommonName(attr);
					}

					if (attr.getID().equalsIgnoreCase(cfg.getPersonLabelAttName())) {
						personLabel = (String) attr.get();
					} 
					if (attr.getID().equalsIgnoreCase(cfg.getGroupLabelAttName())) {
						groupLabel = (String) attr.get();
					} 
					if (attr.getID().equalsIgnoreCase(cfg.getUnitLabelAttName())) {
						unitLabel = (String) attr.get();
					} 
					if (attr.getID().equalsIgnoreCase(cfg.getOrganizationLabelAttName())) {
						organizationLabel = (String) attr.get();
					} 
					if (attr.getID().equalsIgnoreCase(cfg.getDomainLabelAttName())) {
						domainLabel = (String) attr.get();
					}
				}
				
				if (isPerson()) {
					msLongName = personLabel;
				} else if (isGroup()) {
					msLongName = groupLabel;
				} else if (isUnit()) {
					msLongName = unitLabel;
				} else if (isOrganization()) {
					msLongName = organizationLabel;
				} else if (isDomain()) {
					msLongName = domainLabel;
				}
				
			} catch (NamingException e) {
				logger.error("Error al construir el entry", e);
				throw new ISPACException("Error construyendo entry: "
						+ m_relativeDN, e);
			}
		}
	}

	/**
	 * Constructor que a partir de SearchResult inicializa el objeto. Necesita
	 * dos de los par&aacute;metros con los que se realiz&oacute; la b&uacute;squeda por la que se
	 * obtuvo el resultado (searchresult). Estos par&aacute;metros los necesita para
	 * poder inicializar correctamente el atributo <code>m_relativeDN</code>.
	 * Este constructor se utiliza cuando la b&uacute;squeda se haya
	 * hecho partiendo de una entrada que no sea el <code>context root</code>, ya que entonces
	 * el <code>DN</code> que se obtiene del parametro <code>entry</code> es relativo a la ra&iacute;z de la b&uacute;squeda
	 * y no al <code>context root</code>.
	 *
	 * @param entry cotiene una entrada ldap
	 * @param raiz de la búsqueda
	 * @param searchScope ambito de la búqueda
	 * @throws ISPACException si se produce error
	 */
	private LdapEntry(SearchResult entry, String searchRoot, int searchScope)
			throws ISPACException
	{
		this(entry);
		if (searchScope == SearchControls.OBJECT_SCOPE)
			m_relativeDN = searchRoot;
		else
		{
			if (!searchRoot.equals(""))
				m_relativeDN = entry.getName() + "," + searchRoot;
		}
	}

	/**
	 * Devuelve el <code>DN</code> relativo a la entrada LDAP de configuración
	 *
	 * @return <code>DN</code> de la entrada
	 */
	public String getRelativeDN()
	{
		return m_relativeDN;
	}

	/**
	 * Establece el <code>DN</code> relativo a partir del <code>DN</code> absoluto
	 *
	 */
	public void setRelativeDN(String dn) throws ISPACException
	{
		//LdapCfg cfg = LdapCfg.getInstance();
		//m_relativeDN = cfg.toRelativeDN (dn);
		m_relativeDN = dn;
	}

	/**
	 * Devuelve el DN completo de la entrada LDAP
	 *
	 * @return DN de la entrada
	 */
	public String getDN()
	throws ISPACException
	{
		return LdapCfg.getInstance().toAbsoluteDN(m_relativeDN);
	}

//	/**
//	 * Devuelve el nombre de usuario de la entrada LDAP
//	 *
//	 * @return <code>DN</code> de la entrada
//	 */
//	public String getName()
//	throws ISPACException
//	{
//		if (m_relativeDN.length() == 0)
//			return LdapCfg.getInstance().getRootDN();
//
//		return m_relativeDN;
//	}
//
//    /**
//     * Devuelve el nombre de usuario de la entrada LDAP determinado por CN_ATTNAME
//     *
//     * @return DN de la entrada
//     */
//    public String getCommonName()
//    throws ISPACException
//    {
//        return m_commonName;
//    }

	
	public String getDistinguishedName()
	throws ISPACException
	{
		if (m_relativeDN.length() == 0)
			return LdapCfg.getInstance().getRootDN();

		return m_relativeDN;
	}

    public String getName()
    throws ISPACException
    {
    	String name = "";
    	
        if (m_commonName != null) {
        	name = m_commonName;
        }
        else {
        	name = getDistinguishedName();
        }
        
        return StringUtils.replace(name, "\\,", ",");
        
    	/*
    	if (m_commonName != null)
        	return m_commonName;
        return getDistinguishedName();
        */
    }

	public String getLongName() throws ISPACException {
		
		if (StringUtils.isBlank(msLongName)) {
			return getName();
		}
		
		return StringUtils.replace(msLongName, "\\,", ",");
		
		/*
		if (StringUtils.isBlank(msLongName)) {
			return getName();
		}
		return msLongName;
		*/
	}

	/**
	 * Devuelve el UID
	 *
	 * @return UID de la entrada
	 */
	public String getUID()
	throws ISPACException
	{
		return m_UID;
	}

	/**
	 * Devuelve el tipo de entrada que representa
	 *
	 * @return tipo de entrada
	 */
	public int getEntryType()
	throws ISPACException
	{
		return m_entryType;
	}

	public String[] getMembers ()
	{
		return m_members;
	}

	public boolean isPerson()
	{
		return (m_entryType == ET_PERSON);
	}

	public boolean isGroup()
	{
		return (m_entryType == ET_GROUP);
	}

	public boolean isUnit()
	{
		return (m_entryType == ET_UNIT);
	}

	public boolean isOrganization()
	{
		return (m_entryType == ET_ORGANIZATION);
	}

	public boolean isDomain()
	{
		return (m_entryType == ET_DOMAIN);
	}

	/**
	 * Devuelve el <code>DN</code> de la entrada padre asociado a esta entrada
	 *
	 * @return dn
	 */
	public String getParentRelativeDN() throws ISPACException
	{
		if (m_parentEntry == null)
			getParentEntry(getRelativeDN());
		return m_parentEntry.getRelativeDN();
	}

	/**
	 * Devuelve el uid de la entrada padre asociado a esta entrada
	 *
	 * @return dn
	 */
	public String getParentUID() throws ISPACException
	{
		if (m_parentEntry == null)
			getParentEntry(getRelativeDN());
		return m_parentEntry.getUID();
	}

	/**
	 * Devuelve los <code>DN</code> de los grupos del usuario
	 *
	 * @return enumeracion con los <code>DN</code>
	 * @throws ISPACException si se intenta obtener <code>DN</code> de una entrada que no
	 *             sea un usuario
	 */
	public Enumeration getGroupDNs() throws ISPACException
	{
		Vector vDNs = new Vector();
		if (m_entryType == ET_PERSON)
		{
			if (m_grpEntries == null)
				getGroupEntries(getRelativeDN());

			if (m_grpEntries != null)
				for (int i = 0; i < m_grpEntries.length; i++)
					vDNs.add(m_grpEntries[i].getRelativeDN());
		}
		else
			throw new ISPACException(
					"LdapEntry::getGroupDNs (), la entrada no es una persona y por tanto no tiene grupos");

		return vDNs.elements();
	}

	/**
	 * Devuelve los uids de los grupos del usuario
	 *
	 * @return enumeracion con los dn
	 * @throws ISPACException si se intenta obtener uids de una entrada que no
	 *             sea un usuario
	 */
	public Enumeration getGroupUIDs() throws ISPACException
	{
		Vector vUIDs = new Vector();
		if (m_entryType == ET_PERSON)
		{
			if (m_grpEntries == null)
				getGroupEntries(getRelativeDN());

			if (m_grpEntries != null)
				for (int i = 0; i < m_grpEntries.length; i++)
					vUIDs.add(m_grpEntries[i].getUID());
		}
		else
			throw new ISPACException(
					"LdapEntry::getGroupUIDs (), la entrada no es una persona y por tanto no tiene grupos");

		return vUIDs.elements();
	}

	/**
	 * Devuelve un objeto LdapEntry con la información asociada de la entrada
	 * padre
	 *
	 * @throws ISPACException si se produce algun error
	 */
	//public LdapEntry getDeptEntry() throws ISPACException
	public IDirectoryEntry getParentEntry()
	throws ISPACException
	{
		if (m_parentEntry == null)
			getParentEntry(getRelativeDN());
		return m_parentEntry;
	}

    /**
     * Devuelve un objeto LdapEntry con la información asociada de la entrada
     * LDAP que es padre de la entrada de <code>DN</code> pasado como parametro
     *
     * @param <code>DN</code> de la entrada LDAP hija
     * @throws ISPACException si se produce algun error
     */
	//private void getDeptEntry(String dn) throws ISPACException
	private void getParentEntry(String dn) throws ISPACException
	{
		LdapCfg cfg = LdapCfg.getInstance();
		LdapEntry entry = null;

		String rootSearch = cfg.toAbsoluteDN(dn);

		if (!rootSearch.equals(cfg.getRootDN()))
		{
			String deptDN = LdapUtils.getParentDN(rootSearch);
			String searchString = "(objectclass=*)";

			NamingEnumeration ne = LdapDirectoryConnectorHelper.getInstance()
					.search(deptDN, searchString, SearchControls.OBJECT_SCOPE);
			if (ne != null)
				entry = createLdapEntry(ne, cfg.toRelativeDN(deptDN), SearchControls.OBJECT_SCOPE);

			m_parentEntry = entry;
		}
	}

	/**
	 * Devuelve coleccion de grupos del usuario
	 *
	 * @throws ISPACException si se produce error
	 */
	public Set getGroupEntries() throws ISPACException
	{
		HashSet entries = new HashSet();
		getGroupEntries(getRelativeDN());

		if (this.m_grpEntries != null)
		{
			for (int i=0; i<this.m_grpEntries.length; i++)
				entries.add(m_grpEntries[i]);
		}

		return entries;
	}

	/**
	 * Devuelve la colecci&oacute;n de grupos del usuario
	 *
	 * @throws ISPACException si se produce error
	 */
	public Set getGroupEntriesEx() throws ISPACException
	{
		HashSet entries = new HashSet();
		getGroupEntries(getRelativeDN());

		if (this.m_grpEntries != null)
		{
			for (int i=0; i<this.m_grpEntries.length; i++)
			{
				entries.add(m_grpEntries[i]);
				//Modificado para hacer hereditario la herencia entre grupos.
				if (m_grpEntries[i].isGroup())
				{
				    Set groupentries=m_grpEntries[i].getGroupEntriesEx();
				    entries.addAll(groupentries);
				}
			}
		}

		return entries;
	}

	/**
	 * Devuelve array de objetos LdapEntry con la información asociada a las
	 * entradas ldap de los grupos del usuario de <code>DN</code> pasado como parametro
	 *
	 * @param <code>DN</code> del usuario
	 * @throws ISPACException si se produce error
	 */
	private void getGroupEntries(String dn) throws ISPACException
	{
		if (m_grpEntries == null)
		{
			LdapCfg cfg = LdapCfg.getInstance();
			Vector vEntries = new Vector();
			String absDN = cfg.toAbsoluteDN(dn);
			String searchExpr = "(" + cfg.getMemberAttName() + "=" + absDN + ")";

			NamingEnumeration ne = LdapDirectoryConnectorHelper.getInstance()
					.search(cfg.getRootDN(), searchExpr,
							SearchControls.SUBTREE_SCOPE);
			if (ne != null) {
				LdapEntry entry = null;
				do
				{
					entry = createLdapEntry(ne);
					if (entry != null)
						vEntries.add(entry);
				}
				while (entry != null);
			}

			m_grpEntries = toLdapEntriesArray(vEntries);
		}
	}

	/**
	 * Crea una entrada LdapEntry a partir del siguiente elemento de la
	 * enumeraci&oacute;n. Debe ser llamado cuando el par&aacute;metro ha sido obtenido
	 * en una b&uacute;squeda cuya raiz de b&uacute;squeda sea el context root
	 *
	 * @param ne enumeracion
	 * @return LdapEntry
	 * @throws ISPACException
	 */
	protected static LdapEntry createLdapEntry(NamingEnumeration ne)
			throws ISPACException
	{
		LdapEntry entry = null;

		try
		{
			if ((ne != null) && ne.hasMoreElements())
			{
				SearchResult result = (SearchResult) ne.next();
				entry = new LdapEntry(result);
			}
			else
				entry = null;
		}
		catch (NamingException e)
		{
			logger.error("Error al crear el objeto LdapEntry", e);
			String errMsg = "LdapEntry:createLdapEntry(NamingEnumeration ne), error creando objeto LdapEntry";
			throw new ISPACException(errMsg, e);
		}
		return entry;
	}


	/**
	 * Crea una entrada LdapEntry a partir del siguiente elemento de la
	 * enumeracion. Es usado cuando se tiene que calcular el <code>DN</code> relativo
	 * de la entrada. Esto ocurre cuando el NamingEnumeration que se le pasa
	 * como parametro es el resultado de una busqueda con una raiz de búsqueda
	 * distinta al context root
	 * @param ne enumeracion
	 * @param dn a partir del cual se hizo la busqueda que obtuvo el
	 * NamingEnumeration
	 * @param scope de la busqueda que obtuvo el NamingEnumeration
	 * @return LdapEntry
	 * @throws ISPACException
	 */
	protected static LdapEntry createLdapEntry(NamingEnumeration ne,
			String searchRoot, int searchScope) throws ISPACException
	{
		LdapEntry entry = null;
		try
		{
			if ((ne != null) && ne.hasMoreElements())
			{
				SearchResult result = (SearchResult) ne.next();
				entry = new LdapEntry(result, searchRoot, searchScope);
			}
			else
				entry = null;
		}
		catch (NamingException e)
		{
			logger.error("Error al crear el objeto LdapEntry", e);
			String errMsg = "LdapEntry:createLdapEntry(NamingEnumeration ne, "
					+ searchRoot + "), error creando objeto LdapEntry";
			throw new ISPACException(errMsg, e);
		}
		return entry;
	}

	/**
	 * Convierte un vector de entrada ldap a un array de entradas ldap
	 *
	 * @param vEntries vector de entradas ldap
     * @result array de LdapEntry
	 */
	private LdapEntry[] toLdapEntriesArray(Vector vEntries)
	{
		vEntries.trimToSize();
		LdapEntry[] entries = new LdapEntry[vEntries.size()];
		if (vEntries.size() > 0)
			entries = (LdapEntry[]) vEntries.toArray(entries);
		else
			entries = null;
		return entries;
	}

	/**
	 * Devuelve el valor asociado al atributo q representa el tipo de entrada
	 *
	 * @param attr atributo
	 * @return tipo
	 */
	private int getTypeValue(Attribute attr)
	throws NamingException, ISPACException
	{
		LdapCfg cfg = LdapCfg.getInstance();

		int type = ET_UNDEFINED;

		NamingEnumeration list = attr.getAll();

		if (list != null) {
			
			String oc;
			for (int i = 0; list.hasMoreElements(); i++)
			{
				oc = (String) list.next();
				if (oc.equalsIgnoreCase(cfg.getPersonObjectClass()))
				{
					type = ET_PERSON;
					break;
				}
				else if (oc.equalsIgnoreCase(cfg.getGroupObjectClass()))
				{
					type = ET_GROUP;
				}
				else if (oc.equalsIgnoreCase(cfg.getUnitObjectClass()))
				{
					type = ET_UNIT;
				}
				else if (oc.equalsIgnoreCase(cfg.getOrganizationObjectClass()))
				{
					type = ET_ORGANIZATION;
				}
				else if (oc.equalsIgnoreCase(cfg.getDomainObjectClass()))
				{
					type = ET_DOMAIN;
				}
			}
		}
		
		return type;
	}

	private String[] getMembers(Attribute attr) throws NamingException
	{
		NamingEnumeration list = attr.getAll();
		String[] members = new String[attr.size()];
		for  (int i=0; list.hasMoreElements(); i++)
			members[i] = (String) list.next();
		return members;
	}


	/**
	 * Devuelve el valor asociado al atributo que representa el uid. Si es AD
	 * hay que formatearlo.
	 *
	 * @param attr atributo que representa el uid
	 * @return uid
	 */
	private String getUIDValue(Attribute attr) throws ISPACException
	{
		try
		{
			LdapCfg cfg = LdapCfg.getInstance();
			String uid;
			Object attrValue = attr.get();
			if (cfg.isBinaryUUID())
			{
				byte[] asBytes = (byte[]) attrValue;
				uid = formatBinaryGuid(asBytes);
			}
			else
			{
				uid = attrValue.toString();
			}
			return uid;
		}
		catch(NamingException e)
		{
			logger.error("Error al obtener el valor del UID", e);
			throw new ISPACException(e);
		}
	}

	/**
	 * Devuelve el valor asociado al atributo que representa el nombre de
	 * usuario.
	 *
	 * @param attr atributo que representa el nombre de usuario
	 * @return nombre usuario
	 */
	private String getCommonName(Attribute attr) throws ISPACException
	{
		try
		{
			String name = (String) attr.get();
			name = name.substring(name.indexOf('=') + 1, name.length());
			return name;
		}
		catch(NamingException e)
		{
			logger.error("Error al obtener el valor del Common Name", e);
			throw new ISPACException(e);
		}
	}

	/**
	 * Transforma el UID de AD de un formato en byte[] a String
	 *
	 * @param guidObj UID en formato byte[]
     * @result UID en formato string
	 */
	private String formatBinaryGuid(byte[] guidObj)
	{
        if (guidObj==null)
            return null;

		String guidStr = "";
		int len, i;
		String byteStr;

		len = guidObj.length;

		for (i = 0; i < len; i++)
		{
			byteStr = Integer.toHexString(guidObj[i] & 0xff);
			if (byteStr.length() == 1)
				byteStr = "0" + byteStr;

			guidStr = guidStr + byteStr;
		}
		return guidStr;
	}

	public static byte[] GuidToByteArray(String sGUID)
	{
        if (sGUID==null)
            return null;

        try {
			int guidLen = sGUID.length()/2;
	
			byte[] bGUID = new byte [guidLen];
	
			String sByte = null;
			for (int i = 0; i < guidLen; i++)
			{
				sByte = sGUID.substring(i*2, i*2+2);
				bGUID[i] = Integer.decode("0x"+sByte).byteValue();
			}
	
			return bGUID;
        } catch (Exception e) {
        	logger.warn("Error al extraer los bytes del GUID", e);
        	return null;
        }
	}
	
	/*private static void  escapeSearchResultNameCharacters(SearchResult result){
	

		if(result!=null && StringUtils.contains(result.getName(), "\"")){
			String aux=result.getName().substring(1,result.getName().length()-1);
		   result.setName(aux);
		}
		
	}*/
}
