/*
 * invesflow Java - ISPAC
 * $Source: /TEST/SIGEM/ispaclib/src/ieci/tdw/ispac/ispaclib/util/transition/TransitionTable.java,v $
 * $Revision: 1.1 $
 * $Date: 2007/04/24 11:17:02 $
 * $Author: luismimg $
 *
 */
package ieci.tdw.ispac.ispaclib.util.transition;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * TransitionTable
 *
 *
 * @version $Revision: 1.1 $ $Date: 2007/04/24 11:17:02 $
 */
public class TransitionTable
{
    private final Map transitionmap;

    public TransitionTable(Collection transitions)
    {
        transitionmap=new HashMap();
        build(transitions);
    }

    private TransitionTable(Map transitionmap)
    {
        this.transitionmap=transitionmap;
    }

    private void build(Collection transitions)
    {
        Iterator it=transitions.iterator();
        while(it.hasNext())
        {
            ITransition trn=(ITransition)it.next();
            put(transitionmap,trn.destination(),trn.origin());
        }
    }

    public Set transition(Object obj)
    {
        Set retset=(Set)transitionmap.get(obj);
        if (retset==null)
            return new HashSet();

        return retset;
    }

    public void addTransition(ITransition trn)
    {
        put(transitionmap,trn.destination(),trn.origin());
    }

    public void addTransition(Object orig,Object dest)
    {
        put(transitionmap,orig,dest);
    }

    public TransitionTable closure()
    {
        HashMap tranclosuremap=new HashMap(transitionmap);
        closuremap(tranclosuremap);
        return new TransitionTable(tranclosuremap);
    }



    private void put(Map map,Object key,Object value)
    {
        Set set=(Set)map.get(key);
        if (set==null)
        {
            set=new HashSet();
            map.put(key,set);
        }

        set.add(value);
    }

    private void closuremap(Map map)
    {
        for (int n=0;n<map.size();n++)
        {
	        Iterator it=map.values().iterator();
	        while (it.hasNext())
	        {
	            Set tempset=new HashSet();
	            Set set = (Set) it.next();
	            Iterator setit=set.iterator();
	            while(setit.hasNext())
	            {
	                Set refset=(Set)map.get(setit.next());
	                if (refset!=null)
	                    tempset.addAll(refset);
	            }
	            set.addAll(tempset);
	        }
        }
    }
}
