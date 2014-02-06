package ieci.tecdoc.sbo.acs.base;

import ieci.tecdoc.core.collections.IeciTdLongIntegerArrayList;

import java.io.Serializable;
import java.util.ArrayList;


public final class AcsAccessTokenGroups implements Serializable
{

    //~ Instance fields --------------------------------------------------------

    private ArrayList m_al;

    //~ Constructors -----------------------------------------------------------

    public AcsAccessTokenGroups()
    {

        m_al = new ArrayList();

    }

    //~ Methods ----------------------------------------------------------------

    public int count()
    {

        return m_al.size();

    }

    public void add(AcsAccessTokenGroup item)
    {

        m_al.add(item);

    }

    public void add(int id, int genPerms)
    {

        AcsAccessTokenGroup group;
        
        group = new AcsAccessTokenGroup(id, genPerms);
        
        m_al.add(group);

    }

    public AcsAccessTokenGroup get(int index)
    {

        return (AcsAccessTokenGroup)m_al.get(index);

    }

    public IeciTdLongIntegerArrayList getIds()
    {

        IeciTdLongIntegerArrayList ids;
        int                        count;
        int                        i;
        int                        id;
        
        ids   = new IeciTdLongIntegerArrayList();
        count = count();

        for(i = 0; i < count; i++)
        {

            id = get(i).getId();
        
            ids.add(id);

        }

        return ids;

    }

    /**
     * toString methode: creates a String representation of the object
     *
     * @return the String representation
     */
    public String toString()
    {

        StringBuffer buffer = new StringBuffer();
        
        buffer.append("AcsAccessTokenGroups[");

        for(int i = 0; i < m_al.size(); i++)
        {

            buffer.append(" [group").append(i+1);
            buffer.append(" = ").append((m_al.get(i)).toString());
            buffer.append("] ").append(m_al);

        }
        
        buffer.append("]");

        return buffer.toString();

    }

}
 // class
