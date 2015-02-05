package es.ieci.tecdoc.fwktd.ldap.core.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Objeto para almacenar la informacion de una entrada LDAP
 * @author Iecisa
 * @version $Revision: 79 $
 *
 */
public class LdapEntryVO {

	/**
	 * Map con los atributos de la entrada
	 */
	private Map map;

	/**
	 * Dn relativo de la entrada
	 */
	private String relativeDn;

	/**
	 * Dn absoluto de la entrada
	 */
	private String fullDn;

	/**
	 * Guid de la entrada
	 */
	private String ldapGuid;

	/**
	 * Obtiene el map de atributos
	 * @return map de atributos
	 */
	public Map getMap() {
		return map;
	}

	/**
	 * Permite establecer el map de atributos
	 * @param mapAttrs map de atributos
	 */
	public void setMap(final Map mapAttrs) {
		this.map = new HashMap();

		if ((mapAttrs!=null)&&(!mapAttrs.isEmpty())){
			Iterator it = mapAttrs.keySet().iterator();
			while (it.hasNext()){
				Object key = it.next();
				putAttribute((String)key, map.get(key));
			}
		}

	}

	/**
	 * Obtiene el dn relativo de la entrada
	 * @return dn relativo de la entrada
	 */
	public String getRelativeDn() {
		return relativeDn;
	}

	/**
	 * Establece el dn relativo de la entrada
	 * @param relativeDn dn relativo de la entrada
	 */
	public void setRelativeDn(final String relativeDn) {
		this.relativeDn = relativeDn;
	}

	/**
	 * Obtiene el dn completo de la entrada
	 * @return dn completo de la entrada
	 */
	public String getFullDn() {
		return fullDn;
	}

	/**
	 * Establece el dn completo de la entrada
	 * @param fullDn dn completo de la entrada
	 */
	public void setFullDn(final String fullDn) {
		this.fullDn = fullDn;
	}

	/**
	 * Permite obtener el guid de la entrada
	 * @return guid de la entrada
	 */
	public String getLdapGuid() {
		return ldapGuid;
	}

	/**
	 * Permite establecer el guid de la entrada
	 * @param ldapGuid guid de la entrada
	 */
	public void setLdapGuid(final String ldapGuid) {
		this.ldapGuid = ldapGuid;
	}

	/**
	 * Permite aniadir un atributo a la entrada ldap
	 * @param ldapEntryAttr Objeto {@link LdapEntryAttributeVO} con la informacion del atributo
	 */
	public void putAttribute(final LdapEntryAttributeVO ldapEntryAttr){
		if (map == null) {
			map = new HashMap();
		}
		map.put(ldapEntryAttr.getIdAttr().toLowerCase(), ldapEntryAttr);
	}

	/**
	 * Permite aniadir un atributo a la entrada ldap
	 * @param attrId Identificador del atributo
	 * @param ltValues Lista de valores
	 */
	public void putAttribute(final String attrId, final List ltValues){
		if (map == null) {
			map = new HashMap();
		}
		map.put(attrId.toLowerCase(), new LdapEntryAttributeVO(attrId.toLowerCase(), ltValues));
	}

	/**
	 * Permite aniadir un atributo a la entrada ldap
	 * @param attrId Identificador del atributo
	 * @param value Valor a aniadir
	 */
	public void putAttribute(final String attrId, final Object value){
		if (map == null) {
			map = new HashMap();
		}
		map.put(attrId.toLowerCase(), new LdapEntryAttributeVO(attrId.toLowerCase(), value));
	}

	/**
	 * Permite obtener un atributo de la entrada
	 * @param idAttr Identificador del atributo
	 * @return Objeto {@link LdapEntryAttributeVO}
	 */
	public LdapEntryAttributeVO getAttribute(final String idAttr){
		LdapEntryAttributeVO ldapEntryAttr = null;
		if (map!=null) {
			ldapEntryAttr = (LdapEntryAttributeVO) map.get(idAttr.toLowerCase());
		}

		return ldapEntryAttr;
	}

	/**
	 * Permite obtener un valor de un atributo de la entrada
	 * @param idAttr Identificador del atributo
	 * @return Valor del atributo
	 */
	public String getAttributeSingleValue(final String idAttr){
		String retValue = null;
		if (map!=null) {
			LdapEntryAttributeVO ldapEntryAttr = (LdapEntryAttributeVO) map.get(idAttr.toLowerCase());

			if (ldapEntryAttr!=null){
				retValue = (String) ldapEntryAttr.getValue();
			}
		}

		return retValue;
	}

	/**
	 * Permite obtener varios atributos de una entrada
	 * @param ids Identificadores de atributos
	 * @return Lista de objetos {@link LdapEntryAttributeVO}
	 */
	public List getAttributes(final String [] ids){
		List attributes = null;
		if ((ids!=null)&&(ids.length>0)&&(map!=null)){
			attributes = new ArrayList();
			LdapEntryAttributeVO ldapEntryAttr = null;
			for (int i=0; i<ids.length; i++){
				ldapEntryAttr = (LdapEntryAttributeVO) map.get(ids[i].toLowerCase());
				if (ldapEntryAttr!=null) {
					attributes.add(ldapEntryAttr);
				}
			}
		}
		return attributes;
	}

	/**
	 * Permite obtener los atributos
	 * @return Lista de objetos {@link LdapEntryAttributeVO}
	 */
	public List getAttributes(){
		List attributes = null;
		if ((map!=null)&&(!map.isEmpty())){
			attributes = new ArrayList();
			Iterator itAttrs = map.keySet().iterator();
			while (itAttrs.hasNext()){
				Object key = itAttrs.next();
				attributes.add(map.get(key));
			}
		}
		return attributes;
	}

	/**
	 * {@inheritDoc}
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer("ldapGuid:").append(ldapGuid).append(",relativeDn:").append(relativeDn).append(",fullDn:").append(fullDn);
		if ((map!=null)&&(!map.isEmpty())){
			buffer.append("{");
			Iterator itAttributes = map.entrySet().iterator();
			while (itAttributes.hasNext()){
				Entry entry = (Entry) itAttributes.next();
				buffer.append(entry.getValue());
				if (itAttributes.hasNext()) {
					buffer.append(",");
				}
			}
			buffer.append("}");
		}

		return buffer.toString();
	}
}
