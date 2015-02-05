package ieci.tdw.ispac.ispaclib.app;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.context.ClientContext;

import java.util.ArrayList;
import java.util.List;

public abstract class MasterDetailEntityApp extends GenericSecondaryEntityApp {
	
	// Lista de ItemBean asociados
	protected List mItemBeanList = null;
	
	// Formateador de la lista
	protected BeanFormatter beanFormatter = null;
	
	public MasterDetailEntityApp(ClientContext context)
	{
		super(context);
		mItemBeanList = new ArrayList();
	}

	public List getItemBeanList() throws ISPACException	{
		return mItemBeanList;
	}
	
	public BeanFormatter getBeanFormatter() throws ISPACException	{
		return beanFormatter;
	}
	
	/**
	 * Obtiene los registros de la entidad detalle
	 * como una lista de ItemBean.
	 * 
	 * @param entity Definición de parámetro de entidad de detalle.
	 * @return Lista con los registros de la entidad detalle.
	 * @throws ISPACException
	 */
	protected abstract List getItemBeanList(EntityParameterDef entity) throws ISPACException;
	
}