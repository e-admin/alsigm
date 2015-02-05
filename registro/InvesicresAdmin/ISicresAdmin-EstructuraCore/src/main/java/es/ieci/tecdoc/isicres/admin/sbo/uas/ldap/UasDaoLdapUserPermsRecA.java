package es.ieci.tecdoc.isicres.admin.sbo.uas.ldap;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbInputRecord;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbInputStatement;
import es.ieci.tecdoc.isicres.admin.core.db.DbOutputRecord;
import es.ieci.tecdoc.isicres.admin.core.db.DbOutputStatement;


public final class UasDaoLdapUserPermsRecA implements DbInputRecord, DbOutputRecord
{

	//~ Instance fields --------------------------------------------------------

	public int	m_userId;
	public int 	m_prodId;
	public int 	m_type;

	//~ Constructors -----------------------------------------------------------

	public UasDaoLdapUserPermsRecA() {}

	//~ Methods ----------------------------------------------------------------

	public void getStatementValues(DbOutputStatement stmt)
							throws Exception
	{

		int i = 1;

		m_userId	= stmt.getLongInteger(i++);
		m_prodId	= stmt.getLongInteger(i++);
		m_type		= stmt.getLongInteger(i++);

	}

	public void setStatementValues(DbInputStatement stmt)
	throws Exception
	{
	
		int i = 1;
		
		stmt.setLongInteger(i++, m_userId);
		stmt.setLongInteger(i++, m_prodId);
		stmt.setLongInteger(i++, m_type);
	
	}
	
	/**
	 * toString methode: creates a String representation of the object
	 *
	 * @return the String representation
	 */
	public String toString()
	{

		StringBuffer buffer = new StringBuffer();
		buffer.append("UasDaoLdapUserPermsRecA[");
		buffer.append("m_userId = ").append(m_userId);
		buffer.append(", m_prodId = ").append(m_prodId);
		buffer.append(", m_type = ").append(m_type);
		buffer.append("]");
		return buffer.toString();

	}

}
 // class
