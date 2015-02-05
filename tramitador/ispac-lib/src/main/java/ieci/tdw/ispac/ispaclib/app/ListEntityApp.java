package ieci.tdw.ispac.ispaclib.app;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.entity.EntityDAO;
import ieci.tdw.ispac.ispaclib.dao.join.TableJoinDAO;
import ieci.tdw.ispac.ispaclib.item.CompositeItem;

import java.util.ArrayList;
import java.util.List;

public abstract class ListEntityApp extends GenericSecondaryEntityApp {
	
	// Lista de ItemBean asociados
	protected List mItemBeanList = null;
	
	// Formateador de la lista
	protected BeanFormatter beanFormatter = null;
	
	public ListEntityApp(ClientContext context)	{
		super(context);
		mItemBeanList = new ArrayList();
	}

	public List getItemBeanList() throws ISPACException	{
		return mItemBeanList;
	}
	
	public BeanFormatter getBeanFormatter() throws ISPACException	{
		return beanFormatter;
	}
	
	public void initiate() throws ISPACException {
		
		// Obtener la entidades secundarias
		super.initiate();

		/*
		if (mitem.getKeyInt() == Integer.MIN_VALUE)
			mitem.setKey(ISPACEntities.ENTITY_NULLREGKEYID);
		*/
		
		mItemBeanList = getItemBeanList(getMainEntity());
		
		// Comprobar si hay que cargar la primera entidad de la lista
		if ((mItemBeanList != null) &&
			(!mItemBeanList.isEmpty()) &&
			(getUrlKey() == 0)) {
			
			// el entityapp se encuentra en session y mantiene el item seleccionado.
			// se vuelve al tab de documentos debemos seleccionar el primer elemento de la lista
			// para que el documento q se muestra sea el mismo q aparece seleccionado en el display tag
			// Primera entidades
			ItemBean itemBean = (ItemBean) mItemBeanList.iterator().next();
			IItem firstItem = itemBean.getItem();
			
			// Comprobar si el item cargado no es la primera entidad
			if (mitem.getKeyInt() != firstItem.getKeyInt()) {
				if (firstItem instanceof TableJoinDAO) {
					
					// Obtener el item de la entidad 
					IEntitiesAPI entitiesAPI = mContext.getAPI().getEntitiesAPI();
					IItem item = entitiesAPI.getEntity(getMainEntity(), firstItem.getKeyInt());
					replaceItem(item);
					
				} else {
					replaceItem(firstItem);
				}
			}
		}
	}	
	
	protected abstract List getItemBeanList(int entityId) throws ISPACException;
	
	public void replaceItem(IItem item)	throws ISPACException {

	    if (!(item instanceof EntityDAO)) {
	    	throw new ISPACException("El objeto item no es una instancia de EntityDAO.");
	    }
		
		// Limpiar el IItem Composite
		CompositeItem composite = (CompositeItem) getItem();
		composite.clear();
		
		// Recargar el item para que se ejecuten los eventos
		item.load(mContext);

		// Establecer la nueva entidad
		composite.addItem(item, mPrefixMainEntity + ":");
		
		// Recargar las entidades secundarias asociadas a la entidad principal
		getSecondariesEntities();
		
		// Recargar los documentos asociados al registro de la entidad
		reloadEntityDocumentsItemBeanList();
	}
	
	public String getListOrder() throws ISPACException {
		
		if (getParameters() != null) {
			
			return getParameters().getListOrder();
		}
		
		return null;
	}
	
}