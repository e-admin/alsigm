package es.ieci.tecdoc.isicres.admin.sbo.uas.ldap;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbInputRecord;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbInputStatement;
import es.ieci.tecdoc.isicres.admin.core.db.DbOutputRecord;
import es.ieci.tecdoc.isicres.admin.core.db.DbOutputStatement;


public final class UasDaoLdapUserRecA implements DbInputRecord, DbOutputRecord
{

	//~ Instance fields --------------------------------------------------------

	public int    m_id;
	public String m_ldapGuid;
	public String m_ldapFullName;

	//~ Constructors -----------------------------------------------------------

	public UasDaoLdapUserRecA() {}

	//~ Methods ----------------------------------------------------------------

	public void getStatementValues(DbOutputStatement stmt)
							throws Exception
	{

		int i = 1;

		m_id	  = stmt.getLongInteger(i++);
		m_ldapGuid   = stmt.getLongText(i++);
		m_ldapFullName    = stmt.getLongText(i++);

	}

	public void setStatementValues(DbInputStatement stmt)
	throws Exception
	{
	
		int i = 1;
		
		stmt.setLongInteger(i++, m_id);
		stmt.setLongText(i++, m_ldapGuid);
		stmt.setLongText(i++, m_ldapFullName);
	
	}
	
	/**
	 * toString methode: creates a String representation of the object
	 *
	 * @return the String representation
	 */
	public String toString()
	{

		StringBuffer buffer = new StringBuffer();
		buffer.append("UasDaoLdapUserRecA[");
		buffer.append("m_id = ").append(m_id);
		buffer.append(", m_ldapGuid = ").append(m_ldapGuid);
		buffer.append(", m_ldapFullName = ").append(m_ldapFullName);
		buffer.append("]");
		return buffer.toString();

	}

}
 // class
