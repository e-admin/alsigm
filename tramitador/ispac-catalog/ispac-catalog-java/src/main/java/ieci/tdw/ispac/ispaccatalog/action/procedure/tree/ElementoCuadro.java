package ieci.tdw.ispac.ispaccatalog.action.procedure.tree;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaccatalog.helpers.NodeNameHelper;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.bean.TreeModel;
import ieci.tdw.ispac.ispaclib.bean.TreeModelItem;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

public class ElementoCuadro implements TreeModelItem {

	//========================================================================
	// Tipos de elementos del cuadro
	//========================================================================
	public static final int TYPE_PROCEDURE 				= 1;
	public static final int TYPE_STAGE 					= 2;
	public static final int TYPE_STAGE_INPUT_FLOWS		= 3;
	public static final int TYPE_STAGE_INPUT_FLOW		= 4;
	public static final int TYPE_STAGE_OUTPUT_FLOWS		= 5;
	public static final int TYPE_STAGE_OUTPUT_FLOW		= 6;
	public static final int TYPE_TASK					= 7;
	public static final int TYPE_DOCTYPE				= 8;
	public static final int TYPE_TEMPLATE				= 9;
	public static final int TYPE_SINC_NODE				= 10;
	public static final int TYPE_SINC_NODE_INPUT_FLOWS	= 11;
	public static final int TYPE_SINC_NODE_INPUT_FLOW	= 12;
	public static final int TYPE_SINC_NODE_OUTPUT_FLOWS	= 13;
	public static final int TYPE_SINC_NODE_OUTPUT_FLOW	= 14;
	public static final int TYPE_STAGEDOCTYPE			= 15;
	public static final int TYPE_TEMPLATESTAGETYPEDOC	= 16;
	public static final int TYPE_SUBPROCEDURE 			= 17;
	public static final int TYPE_ACTIVITY				= 18;
	public static final int TYPE_TASK_COMPLEX			= 19;
	//========================================================================

	protected static final Logger logger = Logger.getLogger(ElementoCuadro.class);
	
	protected ItemBean item = null;
	protected int type;
	protected String propertyName = null;
	protected String propertyId = null;
	protected ElementoCuadro parentElement = null;
	protected List childs = null;
	protected String language = null;
	
	/**
	 * Constructor.
	 * @param type Tipo de elemento del cuadro.
	 * @param item Información del elemento del cuadro.
	 */
	public ElementoCuadro(int type, ItemBean item, String language) {
		this.item = item;
		this.type = type;
		
		if (isEntityTipoDocTramite()||isEntityTipoDocFase()) {
			this.propertyId = "CT_TPDOC:ID";
			this.propertyName = "CT_TPDOC:NOMBRE";
		} else if (isEntityFlujoEntradaFase() 
				|| isEntityFlujoSalidaFase()) {
			this.propertyId = "FLOW:ID";
			this.propertyName = "STAGE:NOMBRE";
		} else if (isEntityFlujoEntradaNodoSinc() 
				|| isEntityFlujoSalidaNodoSinc()) {
			this.propertyId = "FLOW:ID";
			this.propertyName = null;
		} else {
			this.propertyId = "ID";
			this.propertyName = "NOMBRE";
		}
		
		this.language = language;
	}

	public int getType() {
		return type;
	}
	
	public Object getItemId() {
		return getRegId();
	}
	
	public String getItemName() {
		try {

			if (isEntityNodoSincronizacion()) {
				return NodeNameHelper.getSyncNodeName(language, Integer.parseInt((String)getItemId()));
			} else if (isEntityFlujoEntradaNodoSinc()
    					|| isEntityFlujoSalidaNodoSinc()) {
				return NodeNameHelper.getSyncNodeName(language, Integer.parseInt(getProperty("NODE:ID")));
			} else if (isEntityPlantillaTipoDoc() || isEntityPlantillaStageTipoDoc()) {
				if (item.getProperty("isEspecific").equals(Boolean.FALSE)) 
					item.setProperty("NOMBRE", item.getProperty("NOMBRE")+ "(G)");					
				return item.getString(propertyName);
			}
			else {
				return item.getString(propertyName);
			}
		} catch (ISPACException e) {
			logger.warn("Error al obtener el nombre del elemento: " 
					+ propertyName, e);
		}
		return "NOMBRE_NOTFOUND";
	}

	public String getItemPath() {
		StringBuffer itemPath = new StringBuffer();
		if (parentElement != null)
			itemPath.append(((TreeModelItem) parentElement).getItemPath()).append("/");
		itemPath.append("item").append(getItemId());
		return itemPath.toString();
	}

	public void addChild(ElementoCuadro child) {
		if (this.childs == null)
			this.childs = new ArrayList();
		this.childs.add(child);
		child.setParentElement(this);
	}

	public List getAncestors() {
		List ancestors = null;
		if (parentElement != null) {
			ancestors = new ArrayList();
			ElementoCuadro padre = parentElement;
			do {
				ancestors.add(0, padre);
				padre = padre.getParentElement();
			} while (padre != null);
		}
		return ancestors;
	}

	public TreeModelItem getParent() {
		return (TreeModelItem) this.parentElement;
	}

	public TreeModel getTreeModel() {
		return null;
	}

	public boolean isLeaf() {
		return false;
	}

	public void setChilds(List childs) {
		this.childs = childs != null ? childs : new ArrayList();
		CollectionUtils.forAllDo(this.childs, parentSetter);
	}

	public ElementoCuadro getParentElement() {
		return parentElement;
	}

	public void setParentElement(ElementoCuadro parentElement) {
		this.parentElement = parentElement;
	}

	private final Closure parentSetter = new Closure() {
		public void execute(Object arg0) {
			((ElementoCuadro) arg0).setParentElement(ElementoCuadro.this);
		}
	};
	
	public int getEntityId(){
		
		int entityId = -1;
		
		if (isEntityProcedimiento()) {
			entityId = ICatalogAPI.ENTITY_P_PROCEDURE;
		} else if (isEntitySubProceso()) {
			entityId = ICatalogAPI.ENTITY_P_SUBPROCEDURE;
		} else if (isEntityFaseProcedimiento()) {
			entityId = ICatalogAPI.ENTITY_P_STAGE;
		} else if (isEntityActividad()) {
			entityId = ICatalogAPI.ENTITY_P_ACTIVITIES;
		} else if (isEntityTramiteFase() || isEntityTramiteComplejo()) {
			entityId = ICatalogAPI.ENTITY_P_TASK;
		} else if (isEntityTipoDocFase()){
			entityId = ICatalogAPI.ENTITY_P_FSTD;
		} else if (isEntityTipoDocTramite()) {
			entityId = ICatalogAPI.ENTITY_CT_TYPEDOC;
		} else if (isEntityPlantillaTipoDoc()||isEntityPlantillaStageTipoDoc()) {
			entityId = ICatalogAPI.ENTITY_CT_TEMPLATE;
		} else if (isEntityNodoSincronizacion()) {
			entityId = ICatalogAPI.ENTITY_P_SINCNODE;
		} else if (isEntityFlujoEntradaFase()
				|| isEntityFlujoSalidaFase()
				|| isEntityFlujoEntradaNodoSinc()
				|| isEntityFlujoSalidaNodoSinc()) {
			entityId = ICatalogAPI.ENTITY_P_FLUJOS;
		}
		
		return entityId;
	}
	
	public String getRegId(){
		try {
			return item.getString(propertyId);
		} catch (ISPACException e) {
			logger.warn("Error al obtener el id del elemento: " 
					+ propertyId, e);
		}
		return null;
	}

	public ItemBean getItem(){
		return item;
	}
	
	public boolean isEntityProcedimiento(){
		return type == TYPE_PROCEDURE;
	}

	public boolean isEntitySubProceso(){
		return type == TYPE_SUBPROCEDURE;
	}

	public boolean isEntityFaseProcedimiento(){
		return type == TYPE_STAGE;
	}

	public boolean isEntityActividad(){
		return type == TYPE_ACTIVITY;
	}

	public boolean isEntityFlujosEntradaFase(){
		return type == TYPE_STAGE_INPUT_FLOWS;
	}

	public boolean isEntityFlujoEntradaFase(){
		return type == TYPE_STAGE_INPUT_FLOW;
	}

	public boolean isEntityFlujosSalidaFase(){
		return type == TYPE_STAGE_OUTPUT_FLOWS;
	}

	public boolean isEntityFlujoSalidaFase(){
		return type == TYPE_STAGE_OUTPUT_FLOW;
	}

	public boolean isEntityTramiteFase(){
		return type == TYPE_TASK;
	}
	
	public boolean isEntityTramiteComplejo(){
		return type == TYPE_TASK_COMPLEX;
	}
	
	public boolean isEntityTipoDocTramite(){
		return type == TYPE_DOCTYPE;
	}
	public boolean isEntityTipoDocFase(){
		return type == TYPE_STAGEDOCTYPE;
	}

	public boolean isEntityPlantillaStageTipoDoc(){
		return type == TYPE_TEMPLATESTAGETYPEDOC;
	}
	public boolean isEntityPlantillaTipoDoc(){
		return type == TYPE_TEMPLATE;
	}
	public boolean isEntityNodoSincronizacion() {
		return type == TYPE_SINC_NODE;
	}
	public boolean isEntityFlujosEntradaNodoSinc(){
		return type == TYPE_SINC_NODE_INPUT_FLOWS;
	}

	public boolean isEntityFlujoEntradaNodoSinc(){
		return type == TYPE_SINC_NODE_INPUT_FLOW;
	}

	public boolean isEntityFlujosSalidaNodoSinc(){
		return type == TYPE_SINC_NODE_OUTPUT_FLOWS;
	}

	public boolean isEntityFlujoSalidaNodoSinc(){
		return type == TYPE_SINC_NODE_OUTPUT_FLOW;
	}

	public String getProperty(String prop){
		try {
			return item.getString(prop);
		} catch (ISPACException e) {
			logger.warn("Error al obtener la propiedad del elemento: " 
					+ prop, e);
		}
		return null;
	}

}
