/*
 * Created on 24-nov-2004
 *
 */
package ieci.tdw.ispac.ispaclib.item;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.util.ListCollection;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.util.PrefixBuilder;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author JUANIV
 *
 * Crea IItems compuestos con las entidades del expediente especificadas.
 *
 */
public class CompositeItemBuilder
{
    private final IClientContext mctx;
    private ArrayList mentitylist;

    public CompositeItemBuilder(IClientContext ctx)
    {
        mctx=ctx;
        mentitylist=new ArrayList();
    }

    public void addEntity(int nIdEntity)
    throws ISPACException
    {
        IEntitiesAPI entapi=mctx.getAPI().getEntitiesAPI();
        IItem catentity=entapi.getCatalogEntity(nIdEntity);
        mentitylist.add(catentity);
    }

    public IItem compose(IItem item,String itemname,String keyname,String numexpprop)
    throws ISPACException
    {
        String numexp=item.getString(numexpprop);

        //Se crea el item compuesto a partir del item original cone
        CompositeItem compitem=new CompositeItem(PrefixBuilder.buildName(itemname,keyname));
        compitem.addItem(item,PrefixBuilder.buildPrefix(itemname));

        IEntitiesAPI entapi=mctx.getAPI().getEntitiesAPI();
        Iterator it=mentitylist.iterator();
        while (it.hasNext())
        {
            IItem catentity = (IItem)it.next();
            IItemCollection itc = entapi.getEntities(catentity.getKeyInt(), numexp, null);
            if (itc.next())
            {
                compitem.addItem(itc.value(),PrefixBuilder.buildPrefix(catentity.getString("NOMBRE")));
            }
        }

        return compitem;
    }

    public IItemCollection compose(IItemCollection colitem,String itemname,String keyname,String numexpprop)
    throws ISPACException
    {
        ArrayList list=new ArrayList();
        while (colitem.next())
        {
            IItem item = colitem.value();
            list.add(compose(item,itemname,keyname,numexpprop));
        }
        return new ListCollection(list);
    }
}
