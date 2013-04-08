package es.ieci.tecdoc.fwktd.ldap.core.vo;

import org.springframework.ldap.filter.Filter;

/**
 * Objeto con informacion sobre la busqueda para el manager
 * @author Iecisa
 * @version $Revision: 79 $
 *
 */
public class LdapSearchManagerVO {

	/**
	 * Nodo base para el inicio de la busqueda
	 */
	private String base;

	/**
	 * Ambito de la busqueda
	 */
	private String scope;

	/**
	 * Filtro de la busqueda
	 */
	private Filter filter;

	/**
	 * Indica si se aniade al filtro las clases del usuario
	 * Por defecto: true
	 */
	private boolean addUserClasses = true;

	/**
	 * Indica si se aniade al filtro las clases de grupos
	 * Por defecto: true
	 */
	private boolean addGroupClasses = true;

	/**
	 * Permite obtener el nodo base para el inicio de la busqueda
	 * @return Nodo base para el inicio de la busqueda
	 */
	public String getBase() {
		return base;
	}

	/**
	 * Permite establecer el nodo base para el inicio de la busqueda
	 * @param base Nodo base para el inicio de la busqueda
	 */
	public void setBase(final String base) {
		this.base = base;
	}

	/**
	 * Permite obtener el ambito de la busqueda
	 * @return Ambito de la busqueda
	 */
	public String getScope() {
		return scope;
	}

	/**
	 * Permite establecer el ambito de la busqueda
	 * @param scope Ambito de la busqueda
	 */
	public void setScope(final String scope) {
		this.scope = scope;
	}

	/**
	 * Permite obtener el filtro de la busqueda
	 * @return Filtro de la busqueda
	 */
	public Filter getFilter() {
		return filter;
	}

	/**
	 * Permite establecer el filtro de la busqueda
	 * @param filter Filtro de la busqueda
	 */
	public void setFilter(final Filter filter) {
		this.filter = filter;
	}

	/**
	 * Indica si se aniaden las clases de usuario al filtro
	 * @return si se aniaden las clases de usuario al filtro
	 */
	public boolean isAddUserClasses() {
		return addUserClasses;
	}

	/**
	 * Establece si se aniaden las clases de usuario al filtro
	 * @param addUserClasses si se aniaden las clases de usuario al filtro
	 */
	public void setAddUserClasses(final boolean addUserClasses) {
		this.addUserClasses = addUserClasses;
	}

	/**
	 * Indica si se aniaden las clases de grupo al filtro
	 * @return si se aniaden las clases de grupo al filtro
	 */
	public boolean isAddGroupClasses() {
		return addGroupClasses;
	}

	/**
	 * Establece si se aniaden las clases de grupo al filtro
	 * @param addGroupClasses si se aniaden las clases de grupo al filtro
	 */
	public void setAddGroupClasses(final boolean addGroupClasses) {
		this.addGroupClasses = addGroupClasses;
	}

	/**
	 * {@inheritDoc}
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new StringBuffer()
		.append("(base:")
		.append(base)
		.append(",scope:")
		.append(scope)
		.append(",filter:")
		.append(filter.encode())
		.append(",addFilterUserClasses:")
		.append(addUserClasses)
		.append(",addFilterGroupClasses:")
		.append(addGroupClasses)
		.append(")").toString();
	}

}
