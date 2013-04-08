package es.ieci.tecdoc.fwktd.ldap.core.vo;

/**
 * Clase con la configuracion base para el manager LDAP
 * @author Iecisa
 * @version $Revision: 115 $
 *
 */
public class LdapManagerConfigVO implements LdapManagerConfig {


	/**
	 * Atributo de busqueda de usuarios
	 */
	private String ldapUsersSearchAttribute;

	/**
	 * Clases de busqueda de usuarios
	 */
	private String ldapUsersSearchObjectClasses;

	/**
	 * Ambito de busqueda de usuarios
	 */
	private String ldapUsersSearchScope;

	/**
	 * Inicio de busqueda de usuarios
	 */
	private String ldapUsersSearchStart;

	/**
	 * Atributo de busqueda de grupos
	 */
	private String ldapGroupsSearchAttribute;

	/**
	 * Clases de busqueda de grupos
	 */
	private String ldapGroupsSearchObjectClasses;

	/**
	 * Ambito de busqueda de grupos
	 */
	private String ldapGroupsSearchScope;

	/**
	 * Inicio de busqueda de grupos
	 */
	private String ldapGroupsSearchStart;

	/**
	 * Contenedor de atributos a retornar del usuario
	 */
	private LdapAttributeContainer ldapUsersAttributes;

	/**
	 * Contenedor de atributos a retornar del grupo
	 */
	private LdapAttributeContainer ldapGroupsAttributes;

	/**
	 * Constructor
	 */
	public LdapManagerConfigVO (){
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.ldap.core.vo.LdapManagerConfig#getLdapUsersSearchAttribute()
	 */
	public String getLdapUsersSearchAttribute(){
		return ldapUsersSearchAttribute;
	}

	/**
	 * Permite establecer el valor de la propiedad de configuracion users attribute
	 * @param usersAttr valor de la propiedad de configuracion users attribute
	 */
	public void setLdapUsersSearchAttribute(final String usersAttr){
		this.ldapUsersSearchAttribute = usersAttr;
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.ldap.core.vo.LdapManagerConfig#getLdapUsersSearchObjectClasses()
	 */
	public String getLdapUsersSearchObjectClasses(){
		return ldapUsersSearchObjectClasses;
	}

	/**
	 * Permite establecer el valor de la propiedad de configuracion users object classes
	 * @param usersObjClasses valor de la propiedad de configuracion users object classes
	 */
	public void setLdapUsersSearchObjectClasses(final String usersObjClasses){
		this.ldapUsersSearchObjectClasses = usersObjClasses;
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.ldap.core.vo.LdapManagerConfig#getLdapUsersSearchScope()
	 */
	public String getLdapUsersSearchScope(){
		return ldapUsersSearchScope;
	}

	/**
	 * Permite establecer el valor de la propiedad de configuracion users scope
	 * @param usersScope valor de la propiedad de configuracion users scope
	 */
	public void setLdapUsersSearchScope(final String usersScope){
		this.ldapUsersSearchScope = usersScope;
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.ldap.core.vo.LdapManagerConfig#getLdapUsersSearchStart()
	 */
	public String getLdapUsersSearchStart(){
		return ldapUsersSearchStart;
	}

	/**
	 * Permite establecer el valor de la propiedad de configuracion users start
	 * @param usersStart valor de la propiedad de configuracion users start
	 */
	public void setLdapUsersSearchStart(final String usersStart){
		this.ldapUsersSearchStart = usersStart;
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.ldap.core.vo.LdapManagerConfig#getLdapGroupsSearchAttribute()
	 */
	public String getLdapGroupsSearchAttribute(){
		return ldapGroupsSearchAttribute;
	}

	/**
	 * Permite establecer el valor de la propiedad de configuracion groups attribute
	 * @param groupsAttr valor de la propiedad de configuracion groups attribute
	 */
	public void setLdapGroupsSearchAttribute(final String groupsAttr){
		this.ldapGroupsSearchAttribute = groupsAttr;
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.ldap.core.vo.LdapManagerConfig#getLdapGroupsSearchObjectClasses()
	 */
	public String getLdapGroupsSearchObjectClasses(){
		return ldapGroupsSearchObjectClasses;
	}

	/**
	 * Permite establecer el valor de la propiedad de configuracion groups object classes
	 * @param groupsObjClasses valor de la propiedad de configuracion groups object classes
	 */
	public void setLdapGroupsSearchObjectClasses(final String groupsObjClasses){
		this.ldapGroupsSearchObjectClasses = groupsObjClasses;
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.ldap.core.vo.LdapManagerConfig#getLdapGroupsSearchScope()
	 */
	public String getLdapGroupsSearchScope(){
		return ldapGroupsSearchScope;
	}

	/**
	 * Permite establecer el valor de la propiedad de configuracion groups scope
	 * @param groupsScope valor de la propiedad de configuracion groups scope
	 */
	public void setLdapGroupsSearchScope(final String groupsScope){
		this.ldapGroupsSearchScope = groupsScope;
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.ldap.core.vo.LdapManagerConfig#getLdapGroupsSearchStart()
	 */
	public String getLdapGroupsSearchStart(){
		return ldapGroupsSearchStart;
	}

	/**
	 * Permite establecer el valor de la propiedad de configuracion groups start
	 * @param groupsStart valor de la propiedad de configuracion groups start
	 */
	public void setLdapGroupsSearchStart(final String groupsStart){
		this.ldapGroupsSearchStart = groupsStart;
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.ldap.core.vo.LdapManagerConfig#getLdapUsersAttributes()
	 */
	public LdapAttributeContainer getLdapUsersAttributes(){
		return ldapUsersAttributes;
	}

	/**
	 * Permite establecer los atributos que se retornan para la busqueda de usuarios
	 * @param usersAttributes atributos que se retornan para la busqueda de usuarios
	 */
	public void setLdapUsersAttributes(final LdapAttributeContainer usersAttributes){
		this.ldapUsersAttributes = usersAttributes;
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.ldap.core.vo.LdapManagerConfig#getLdapGroupsAttributes()
	 */
	public LdapAttributeContainer getLdapGroupsAttributes(){
		return ldapGroupsAttributes;
	}

	/**
	 * Permite establecer los atributos que se retornan para la busqueda de grupos
	 * @param groupsAttributes atributos que se retornan para la busqueda de grupos
	 */
	public void setLdapGroupsAttributes(final LdapAttributeContainer groupsAttributes){
		this.ldapGroupsAttributes = groupsAttributes;
	}

	/**
	 * {@inheritDoc}
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new StringBuffer()
		.append("(ldapUsersSearchStart:")
		.append(getLdapUsersSearchStart())
		.append(",ldapUsersSearchScope:")
		.append(getLdapUsersSearchScope())
		.append(",ldapUsersSearchAttribute:")
		.append(getLdapUsersSearchAttribute())
		.append(",ldapUsersSearchObjectClasses:")
		.append(getLdapUsersSearchObjectClasses())
		.append("(ldapGroupsSearchStart:")
		.append(getLdapGroupsSearchStart())
		.append(",ldapGroupsSearchScope:")
		.append(getLdapGroupsSearchScope())
		.append(",ldapGroupsSearchAttribute:")
		.append(getLdapGroupsSearchAttribute())
		.append(",ldapGroupsSearchObjectClasses:")
		.append(getLdapGroupsSearchObjectClasses())
		.append(")").toString();
	}
}
