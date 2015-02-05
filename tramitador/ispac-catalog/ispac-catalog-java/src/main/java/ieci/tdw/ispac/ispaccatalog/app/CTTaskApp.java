package ieci.tdw.ispac.ispaccatalog.app;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.app.SimpleEntityApp;
import ieci.tdw.ispac.ispaclib.bean.ValidationError;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.item.CompositeItem;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.DateUtil;

/**
 * EntityApp para la información de un trámite del catálogo de trámites.
 *
 */
public class CTTaskApp extends SimpleEntityApp {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor
	 * @param context Contexto del cliente.
	 */
	public CTTaskApp(ClientContext context) {
		super(context);
	}
	
	/**
	 * Realiza una transformación al item.
	 * @param item Item original.
	 * @return Item transformado.
	 * @throws ISPACException si ocurre algún error.
	 */
	public IItem processItem(IItem item) throws ISPACException {
		
	    CompositeItem composite = new CompositeItem("ID");
		composite.addItem(item, "");

		return composite;
	}

	/**
	 * Inicializa los valores del formulario.
	 * @throws ISPACException si ocurre algún error.
	 */
	public void initiate() throws ISPACException {

		//Obtenermos el procedimiento asociado
		int subPcdId = getItem().getInt("ID_SUBPROCESO");
		if (subPcdId > 0) {
			IItem item = mContext.getAPI().getProcedure(subPcdId);
			((CompositeItem) mitem).addItem(item, "SPAC_P_PROCEDIMIENTOS:");
		}
		
		super.initiate();
		
		if (getString("ID")==null){
			setProperty("AUTOR", mContext.getUser().getName());
			setProperty("FCREACION", DateUtil.getToday());
		}
		if(getItem().get("ID")!=null){
		//Comprobar si tiene tipos de documentos asociados
			
			IInvesflowAPI invesFlowAPI = mContext.getAPI();
			ICatalogAPI catalogAPI=invesFlowAPI.getCatalogAPI();
			if( catalogAPI.countTaskTpDoc(getItem().get("ID").toString())>0)
			{
					setProperty("EXISTE_TIPOS_DOCS_ASOCIADOS", "1");
			}
			else{
				setProperty("EXISTE_TIPOS_DOCS_ASOCIADOS", "");
			}
		}
		else{
			setProperty("EXISTE_TIPOS_DOCS_ASOCIADOS", "");
		}
	
		
	}

	/**
	 * Valida la información del formulario.
	 * @throws ISPACException si ocurre algún error.
	 */
	public boolean validate() throws ISPACException {
		
		boolean ret = super.validate();
		
		if (ret) {
			
			IInvesflowAPI invesFlowAPI = mContext.getAPI();
			ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();
			
			// Bloqueo de la tabla
			catalogAPI.setCTEntitiesForUpdate(ICatalogAPI.ENTITY_CT_TASK, "");
			
			// Nombre único de trámite
			String nombre = getString("NOMBRE");
			IItemCollection itemcol = catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_CT_TASK, " WHERE NOMBRE = '" + DBUtil.replaceQuotes(nombre) + "' AND ID != " + getString("ID"));
			if (itemcol.next()) {
				
				addError(new ValidationError("property(NOMBRE)", "error.task.nameDuplicated", new String[] {nombre}));
				return false;
			}
			
			return true;
		}
		else {
			return false;
		}
	}

}