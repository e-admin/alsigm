package ieci.tecdoc.sbo.acs.std;

import ieci.tecdoc.core.db.DbOutputRecord;
import ieci.tecdoc.core.db.DbOutputStatement;


public final class AcsDaoObjHdrRecOwner implements DbOutputRecord
{

	//~ Instance fields --------------------------------------------------------

	private int    m_ownerType;	
	private int    m_ownerId;
	
	//~ Constructors -----------------------------------------------------------

	public AcsDaoObjHdrRecOwner()
	{
	}
	
	public int getOwnerType()
	{

		return m_ownerType;

	}	
	
	public int getOwnerId()
	{

		return m_ownerId;

	}

	public void getStatementValues(DbOutputStatement stmt)
				   throws Exception
	{

		int i = 1;
		
		m_ownerType = stmt.getLongInteger(i++);		
		m_ownerId   = stmt.getLongInteger(i++);

	}

	/**
	 * toString methode: creates a String representation of the object
	 *
	 * @return the String representation
	 */
	public String toString()
	{

		StringBuffer buffer = new StringBuffer();
		
		buffer.append("AcsDaoObjPermRecOwner[");
		buffer.append("m_ownerType = ").append(m_ownerType);
		buffer.append(", m_ownerId = ").append(m_ownerId);		
		buffer.append("]");

		return buffer.toString();

	}

}


// class
