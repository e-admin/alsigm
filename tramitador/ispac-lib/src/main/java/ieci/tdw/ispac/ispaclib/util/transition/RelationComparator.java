/*
 * invesflow Java - ISPAC
 * $Source: /TEST/SIGEM/ispaclib/src/ieci/tdw/ispac/ispaclib/util/transition/RelationComparator.java,v $
 * $Revision: 1.1 $
 * $Date: 2007/04/24 11:17:02 $
 * $Author: luismimg $
 *
 */
package ieci.tdw.ispac.ispaclib.util.transition;


import java.util.Comparator;

/**
 * RelationComparator
 *
 * @version $Revision: 1.1 $ $Date: 2007/04/24 11:17:02 $
 */
public class RelationComparator implements Comparator
{
    TransitionTable table;

    public RelationComparator(TransitionTable closuretable)
    {
        this.table=closuretable;
    }

    /* (non-Javadoc)
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    public int compare(Object o1, Object o2)
    {
        if (table.transition(o1).contains(o2))
            return 1;

        if (table.transition(o2).contains(o1))
            return -1;

        if (o1==o2)
            return 0;

        //No hay relación definida, se crea una arbitraria.
        table.addTransition(o1,o2);
        return 1;
    }

}
