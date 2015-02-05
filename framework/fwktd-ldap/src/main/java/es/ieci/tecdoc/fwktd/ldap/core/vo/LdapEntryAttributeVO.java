package es.ieci.tecdoc.fwktd.ldap.core.vo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Atributo de una entrada LDAP
 * @author Iecisa
 * @version $Revision: 75 $
 *
 */
public class LdapEntryAttributeVO {

	/**
	 * Identificador del atributo
	 */
	private String idAttr;

	/**
	 * Lista de valores del atributo
	 */
	private List values = new ArrayList();

	/**
	 * Contructor
	 * @param idAttr Identificador del atributo
	 * @param values Valores del atributo
	 */
	public LdapEntryAttributeVO(final String idAttr, final List values) {
		super();
		this.idAttr = idAttr;
		this.values.addAll(values);
	}

	/**
	 * Contructor
	 * @param idAttr Identificador del atributo
	 * @param value Valor del atributo
	 */
	public LdapEntryAttributeVO(final String idAttr, final Object value) {
		super();
		this.idAttr = idAttr;
		this.values.clear();
		this.values.add(value);
	}

	/**
	 * Obtiene el identificador del atributo
	 * @return identificador del atributo
	 */
	public String getIdAttr() {
		return idAttr;
	}

	/**
	 * Permite establecer el identificador del atributo
	 * @param idAttr identificador del atributo
	 */
	public void setIdAttr(final String idAttr) {
		this.idAttr = idAttr;
	}

	/**
	 * Obtiene la lista de valores del atributo
	 * @return Lista de valores del atributo
	 */
	public List getValues() {
		return values;
	}

	/**
	 * Obtiene un valor del atributo
	 * @return Valor del atributo
	 */
	public Object getValue() {
		Object value = null;
		if (!isEmpty()){
			value = values.get(0);
		}
		return value;
	}

	/**
	 * Permite establecer la lista de valores del atributo
	 * @param values lista de valores del atributo
	 */
	public void setValues(final List values) {
		this.values = values;
	}

	/**
	 * Permite establecer el valor del atributo
	 * @param value Valor del atributo
	 */
	public void setValue(final Object value) {
		this.values.clear();
		this.values.add(value);
	}

	/**
	 * Obtiene si la lista de valores esta vacia
	 * @return si la lista de valores esta vacia
	 */
	public boolean isEmpty(){
		return ((values==null)||(values.isEmpty()));
	}

	/**
	 * Obtiene si la lista de valores es simple (un valor)
	 * @return si la lista de valores es simple (un valor)
	 */
	public boolean isSingleValue(){
		return (values.size()==1);
	}

	/**
	 * Obtiene si la lista de valores es multiple (mas de un valor)
	 * @return si la lista de valores es multiple (mas de un valor)
	 */
	public boolean isMultiValue(){
		return (values.size()>1);
	}

	/**
	 * {@inheritDoc}
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer("(").append(idAttr).append("=");
		if (!isEmpty()){
			Iterator itAttr = values.listIterator();
			while (itAttr.hasNext()){
				buffer.append(itAttr.next());
				if (itAttr.hasNext()) {
					buffer.append(",");
				}
			}
		}
		buffer.append(")");
		return buffer.toString();
	}


}
