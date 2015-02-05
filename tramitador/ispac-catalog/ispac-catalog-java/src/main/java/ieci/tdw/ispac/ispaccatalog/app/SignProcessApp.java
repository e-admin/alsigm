package ieci.tdw.ispac.ispaccatalog.app;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IProcedureAPI;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaccatalog.messages.Messages;
import ieci.tdw.ispac.ispaclib.app.SimpleEntityApp;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.bean.ValidationError;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.item.CompositeItem;
import ieci.tdw.ispac.ispaclib.sign.portafirmas.ProcessSignConnector;
import ieci.tdw.ispac.ispaclib.sign.portafirmas.ProcessSignConnectorFactory;
import ieci.tdw.ispac.ispaclib.sign.portafirmas.ProcessSignConnectorImpl;
import ieci.tdw.ispac.ispaclib.util.ISPACConfiguration;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

public class SignProcessApp extends SimpleEntityApp {

	private static final Logger logger = Logger.getLogger(SignProcessApp.class);

	// Lista de firmantes
	protected List mItemBeanList = null;

	//Lista de valores de la tbl de validación tipos notificacion

	protected List tiposNotificacion=null;

	public static int SIGNPROCESS_SPECIFIC_GENERIC = 1;
	public static int SIGNPROCESS_SPECIFIC_TYPE = 2;



	public List getItemBeanList() throws ISPACException	{
		return mItemBeanList;
	}

    public SignProcessApp(ClientContext context)
    {
        super(context);
        mItemBeanList = new ArrayList();
        tiposNotificacion=new ArrayList();
    }

    public List getTiposNotificacion() {
		return tiposNotificacion;
	}

	public void setTiposNotificacion(List tiposNotificacion) {
		this.tiposNotificacion = tiposNotificacion;
	}

	public IItem processItem(IItem item)
	throws ISPACException
	{
	    CompositeItem composite = new CompositeItem("SPAC_CTOS_FIRMA_CABECERA:ID_CIRCUITO");
		composite.addItem(item, "SPAC_CTOS_FIRMA_CABECERA:");
		return composite;
	}
	public void initiate() throws ISPACException
    {
		super.initiate();

		IEntitiesAPI entitiesAPI= mContext.getAPI().getEntitiesAPI();
		ICatalogAPI catalogAPI = mContext.getAPI().getCatalogAPI();
		IItemCollection details = catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_SIGNPROCESS_DETAIL, "WHERE ID_CIRCUITO = " + getItem().getKeyInt() + " ORDER BY ID_PASO");

		Iterator itr= details.iterator();
		int cont=1;
		while(itr.hasNext()){

			((CompositeItem)getItem()).addItem(((IItem)itr.next()), cont+":");
			cont++;

		}
		details.reset();
		mItemBeanList = CollectionBean.getBeanList(details);

		// Obtener las relaciones entre expedientes
		IItemCollection itemCol = entitiesAPI.queryEntities(SpacEntities.SPAC_VLDTBL_TIPO_NOTIF, " WHERE VIGENTE = 1 ORDER BY VALOR");

		tiposNotificacion=CollectionBean.getBeanList(itemCol);

		IItem item = getItem();

		if (item.getString("SPAC_CTOS_FIRMA_CABECERA:ID_CIRCUITO")==null){
			setProperty("SPAC_CTOS_FIRMA_CABECERA:SISTEMA",  ProcessSignConnectorFactory.getInstance().getProcessSignConnector().getIdSystem());
			setProperty("SPAC_CTOS_FIRMA_CABECERA:APLICACION", ISPACConfiguration.getInstance().get(ISPACConfiguration.PROCESS_SIGN_CONNECTOR_APPLICATION));

		}
    }

	public boolean validate() throws ISPACException
	{
		boolean ret = super.validate();

		if (ret) {

			IInvesflowAPI invesFlowAPI = mContext.getAPI();
			ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();
			IProcedureAPI procedureAPI = invesFlowAPI.getProcedureAPI();

			// Bloqueo de la tabla
			catalogAPI.setCTEntitiesForUpdate(ICatalogAPI.ENTITY_SIGNPROCESS_HEADER, "");

			// No se permite cambiar un proceso de firma de específico a genérico cuando ya está en uso
			if (getItem().getInt("SPAC_CTOS_FIRMA_CABECERA:TIPO") == SIGNPROCESS_SPECIFIC_GENERIC) {

				if (procedureAPI.isInUse(ICatalogAPI.ENTITY_SIGNPROCESS_HEADER, getItem().getKeyInt())) {

					addError(new ValidationError("property(TIPO)", "error.signprocess.change.SpecificToGeneric"));
				}
			}

			// Nombre único del proceso de firma
			String descripcion = getString("SPAC_CTOS_FIRMA_CABECERA:DESCRIPCION");
			IItemCollection itemcol = catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_SIGNPROCESS_HEADER, " WHERE DESCRIPCION = '" + DBUtil.replaceQuotes(descripcion) + "' AND ID_CIRCUITO != " + getString("SPAC_CTOS_FIRMA_CABECERA:ID_CIRCUITO"));
			if (itemcol.next()) {

				addError(new ValidationError("property(DESCRIPCION)", "error.signprocess.nameDuplicated", new String[] {descripcion}));
			}

			if (getErrors().isEmpty()) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}

	public void store() throws ISPACException {

		IItem item = getItem();

		if (StringUtils.isEmpty(item.getString("SPAC_CTOS_FIRMA_CABECERA:DESCRIPCION"))) {
			item.set("SPAC_CTOS_FIRMA_CABECERA:DESCRIPCION", 0);
		}

		super.store();
	}

}