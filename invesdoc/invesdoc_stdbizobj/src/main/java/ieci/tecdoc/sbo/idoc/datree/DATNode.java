package ieci.tecdoc.sbo.idoc.datree;

import ieci.tecdoc.sbo.util.types.SboType;


public final class DATNode
{

    //~ Instance fields --------------------------------------------------------

    private int    m_id;
    private String m_name;
    private int    m_type;

    //~ Constructors -----------------------------------------------------------

    public DATNode()
    {

        m_id   = SboType.NULL_ID;
        m_name = null;
        m_type = SboType.NULL_ID;

    }

    public DATNode(int id, String name, int type)
    {

        m_id   = id;
        m_name = name;
        m_type = type;

    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return Returns the id.
     */
    public int getId()
    {

        return m_id;

    }

    /**
     * DOCUMENT ME!
     *
     * @param id The id to set.
     */
    public void setId(int id)
    {

        m_id = id;

    }

    /**
     * DOCUMENT ME!
     *
     * @return Returns the name.
     */
    public String getName()
    {

        return m_name;

    }

    /**
     * DOCUMENT ME!
     *
     * @param name The name to set.
     */
    public void setName(String name)
    {

        m_name = name;

    }

    /**
     * DOCUMENT ME!
     *
     * @return Returns the type.
     */
    public int getType()
    {

        return m_type;

    }

    /**
     * DOCUMENT ME!
     *
     * @param type The type to set.
     */
    public void setType(int type)
    {

        m_type = type;

    }

    
   /**
    * toString methode: creates a String representation of the object
    * @return the String representation
    */
   public String toString() {
      
      StringBuffer buffer = new StringBuffer();
      buffer.append("DATNode[");
      buffer.append("m_id = ").append(m_id);
      buffer.append(", m_name = ").append(m_name);
      buffer.append(", m_type = ").append(m_type);
      buffer.append("]");
      
      return buffer.toString();
   }
}
 // class
