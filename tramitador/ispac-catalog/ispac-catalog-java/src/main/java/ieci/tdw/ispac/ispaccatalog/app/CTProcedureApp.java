package ieci.tdw.ispac.ispaccatalog.app;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IRespManagerAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.IProcedure;
import ieci.tdw.ispac.api.item.IResponsible;
import ieci.tdw.ispac.ispaclib.app.SimpleEntityApp;
import ieci.tdw.ispac.ispaclib.bean.ValidationError;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.item.CompositeItem;
import ieci.tdw.ispac.ispaclib.utils.CollectionUtils;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.DateUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.util.Date;

public class CTProcedureApp extends SimpleEntityApp {

	/**
	 * Constructor
	 * @param context Contexto de cliente.
	 */
    public CTProcedureApp(ClientContext context) {
        super(context);
    }
    /**
     * Sobrescribe el metodo processItem del @ItemBean para preprocesar el item 
     * que se establece en el entityApp y que se convierta en un objeto composite
     * lo que ofrece la posibilidad de añadir otros objetos
     * 
     */
	public IItem processItem(IItem item) throws ISPACException
	{
	    CompositeItem composite = new CompositeItem("ID");
		composite.addItem(item, "");
		
		return composite;
	}

	/**
	 * Inicializa los valores del EntityApp. 
	 * Añade los objetos que contienen el valor de los campos que 
	 * son validados: Sistema Productor, Actividad/Función, etc
	 * @throws ISPACException si ocurre algún error. 
	 */
    public void initiate() throws ISPACException {
    	
    	// APIs
    	IInvesflowAPI invesflowAPI = mContext.getAPI();
    	ICatalogAPI catalogAPI = invesflowAPI.getCatalogAPI();
    	IRespManagerAPI respAPI = invesflowAPI.getRespManagerAPI();
    	
    	//AÑADE LOS SUSTITUTOS DE LOS CAMPOS VALIDADOS.
    	// El valor sustituto será accesible en la JSP 
    	// con el nombre de la entidad y la propiedad SUSTITUTO.
    	// EJ: SPAC_VLDTBL_SIST_PRODUCTORES:SUSTITUTO

    	// Añade un objeto composite con la información del sistema productor.
    	addSustituteItem(catalogAPI, 
    			ICatalogAPI.ENTITY_SPAC_VLDTBL_SIST_PRODUCTORES,
    			"SPAC_VLDTBL_SIST_PRODUCTORES", "COD_SISTEMA_PRODUCTOR");
    	
    	// Añade un objeto composite con la información de actividad/función 
    	addSustituteItem(catalogAPI, 
    			ICatalogAPI.ENTITY_SPAC_VLDTBL_ACTS_FUNCS,
    			"SPAC_VLDTBL_ACTS_FUNCS", "ACT_FUNC");

    	
    	// Añade un objeto composite con la información de materias/competencia 
    	addSustituteItem(catalogAPI, 
    			ICatalogAPI.ENTITY_SPAC_VLDTBL_MATS_COMP,
    			"SPAC_VLDTBL_MATS_COMP", "MTRS_COMP");
    	
    	//Añade un objeto composite con la información de situación telemática
    	addSustituteItem(catalogAPI, 
    			ICatalogAPI.ENTITY_SPAC_VLDTBL_SIT_TLM,
    			"SPAC_VLDTBL_SIT_TLM", "SIT_TLM");
    	
//    	 Añade un objeto composite con la información de tipo de relación
    	addSustituteItem(catalogAPI, 
    			ICatalogAPI.ENTITY_SPAC_TBL_002,
    			"SPAC_TBL_002", "TP_REL");

    	// Añade un objeto composite con la información del órgano resolutor
		String orgUnitId = getItem().getString("ORG_RSLTR");
		if (StringUtils.isNotBlank(orgUnitId)) {
			IResponsible responsible = respAPI.getResp(orgUnitId);
			if (responsible != null) {
		    	((CompositeItem) mitem).addItem(responsible, 
		    			"ORG_RSLTR:", false);
			}
		}

    	// Añade un objeto composite con la información de forma de iniciación
    	addSustituteItem(catalogAPI, 
    			ICatalogAPI.ENTITY_SPAC_VLDTBL_FORMA_INIC,
    			"SPAC_VLDTBL_FORMA_INIC", "FORMA_INIC");
    	
    	// Añade un objeto composite con  la información de unidades de plazo
    	addSustituteItem(catalogAPI, 
    			ICatalogAPI.ENTITY_SPAC_TBL_001,
    			"SPAC_TBL_001", "UNID_PLZ");
    	
    	// Añade un objeto composite con  la información de efectos del silencio
    	addSustituteItem(catalogAPI, 
    			ICatalogAPI.ENTITY_SPAC_VLDTBL_EFEC_SLNCIO,
    			"SPAC_VLDTBL_EFEC_SLNCIO", "EFEC_SILEN");
    	
    	// Añade un objeto composite con  la información de recursos
    	/*
    	addSustituteItem(catalogAPI, 
    			ICatalogAPI.ENTITY_SPAC_VLDTBL_RECURSOS,
    			"SPAC_VLDTBL_RECURSOS", "RECURSOS");
    	*/
    }

    protected void addSustituteItem(ICatalogAPI catalogAPI, int ctEntityId,
    		String ctEntityName, String codeColName) throws ISPACException {

		String code = getItem().getString(codeColName);
		if (StringUtils.isNotBlank(code)) {
	    	IItemCollection collection = catalogAPI.queryCTEntities(ctEntityId,
	        		new StringBuffer("WHERE VALOR = '").append(DBUtil.replaceQuotes(code)).append("'")
	        			.toString());
			if (collection.next()) {
		    	((CompositeItem) mitem).addItem(collection.value(), 
		    			ctEntityName + ":", false);
			}
		}
	}

	public boolean validate() throws ISPACException { 

		if (super.validate()) {

			// Identificación -> Código de procedimiento
			if (StringUtils.isBlank(getString("COD_PCD"))) {
				addError(new ValidationError("property(COD_PCD)", "error.pcd.code.required"));
			}

			// Tramitación -> Fechas de inicio y fin
			try {
				if (StringUtils.isNotBlank(getString("FINICIO")) 
						&& StringUtils.isNotBlank(getString("FFIN"))
						&& (DateUtil.compare(
								DateUtil.getDate(getString("FINICIO")),
								DateUtil.getDate(getString("FFIN"))) > 0)) {
					addError(new ValidationError("property(FFIN)", "error.pcd.enddate.invalid"));
				}
			} catch (Exception e) {}
				

			return CollectionUtils.isEmpty(getErrors());
			
		} else {
			return false;
		}
	}

	public void store() throws ISPACException {
		
		//Actualizamos el usuario y fecha de la ultima modificacion del procedimiento
		IProcedure procedure = mContext.getAPI().getProcedure(mitem.getKeyInt());
		procedure.set("ID_UPD", mContext.getUser().getUID());
		procedure.set("NOMBRE_UPD", mContext.getUser().getName());
		procedure.set("TS_UPD", new Date());
		procedure.store(mContext);
		
		setProperty("COD_PCD", getString("COD_PCD").trim());
		
		super.store();
	}
	
}