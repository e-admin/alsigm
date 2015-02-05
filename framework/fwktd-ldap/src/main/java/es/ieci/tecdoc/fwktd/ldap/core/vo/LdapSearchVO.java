package es.ieci.tecdoc.fwktd.ldap.core.vo;

import org.springframework.ldap.filter.Filter;

import es.ieci.tecdoc.fwktd.ldap.core.constants.LdapConstants;

/**
 * Objeto con informacion sobre la busqueda
 * @author Iecisa
 * @version $Revision: 79 $
 *
 */
public class LdapSearchVO {

	/**
	 * Nodo base para el inicio de la busqueda
	 */
	private String base;

	/**
	 * Ambito de la busqueda
	 */
	private String scope;

	/**
	 * Numero de resultados esperados
	 */
	private int expected = LdapConstants.NO_SEARCH_LIMIT;

	/**
	 * Filtro de la busqueda
	 */
	private Filter filter;

	/**
	 * Atributos a devolver
	 */
	private String [] retAttrs;

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
	 * Permite obtener el filtro de la busqueda en representacion string
	 * @return Filtro de la busqueda en representacion string
	 */
	public String getStrFilter() {
		String strFilter = null;
		if (filter!=null){
			strFilter = filter.encode();
		}
		return strFilter;
	}

	/**
	 * Permite establecer el filtro de la busqueda
	 * @param filter Filtro de la busqueda
	 */
	public void setFilter(final Filter filter) {
		this.filter = filter;
	}

	/**
	 * Permite obtener el numero esperado de resultados
	 * @return numero esperado de resultados
	 */
	public int getExpected() {
		return expected;
	}

	/**
	 * Permite establecer el numero esperado de resultados
	 * @param expected limite esperado de resultados
	 */
	public void setExpected(final int expected) {
		this.expected = expected;
	}

	/**
	 * Permite obtener los atributos a obtener
	 * @return Atributos a obtener
	 */
	public String[] getRetAttrs() {
		return retAttrs;
	}

	/**
	 * Permite establecer los atributos a obtener
	 * @param retAttrs Atributos a obtener
	 */
	public void setRetAttrs(final String[] retAttrs) {
		this.retAttrs = retAttrs;
	}

	/**
	 * {@inheritDoc}
	 * @see java.lang.Object#toString()
	 */
	public String toString() {

		StringBuffer buffAttrs = new StringBuffer();
		if ((retAttrs!=null)&&(retAttrs.length>0)){
			buffAttrs.append(",retAttrs:(");
			for (int i=0;i<retAttrs.length;i++){
				if (i>0) {
					buffAttrs.append(",");
				}
				buffAttrs.append(retAttrs[i]);
			}
			buffAttrs.append(")");
		}

		return new StringBuffer()
		.append("(base:")
		.append(base)
		.append(",scope:")
		.append(scope)
		.append(",filter:")
		.append(filter.encode())
		.append(",expected:")
		.append(expected)
		.append(buffAttrs)
		.append(")").toString();
	}

}
