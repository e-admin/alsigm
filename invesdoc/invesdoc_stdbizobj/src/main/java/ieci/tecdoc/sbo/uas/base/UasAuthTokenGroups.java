package ieci.tecdoc.sbo.uas.base;

import java.util.ArrayList;


public final class UasAuthTokenGroups
{

	//~ Instance fields --------------------------------------------------------

	private ArrayList m_al;

	//~ Constructors -----------------------------------------------------------

	public UasAuthTokenGroups()
	{

		m_al = new ArrayList();

	}

	//~ Methods ----------------------------------------------------------------

	public int count()
	{

		return m_al.size();

	}

	public void add(int id)
	{

		UasAuthTokenGroup group = new UasAuthTokenGroup(id);

		m_al.add(group);

	}

	public UasAuthTokenGroup get(int index)
	{

		return (UasAuthTokenGroup)m_al.get(index);

	}

   /**
    * toString methode: creates a String representation of the object
    * @return the String representation
    * @author info.vancauwenberge.tostring plugin

    */
   public String toString() {
      StringBuffer buffer = new StringBuffer();
      
      buffer.append("UasAuthTokenGroups[");
      buffer.append("m_al = ").append(m_al);
      buffer.append("]");
      
      return buffer.toString();
   }
}


// class
