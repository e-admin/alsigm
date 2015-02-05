package ieci.tdw.ispac.ispaccatalog.managers;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IProcedureAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.IProcedure;
import ieci.tdw.ispac.ispaclib.catalog.procedure.IPcdElement;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.TypeConverter;
import ieci.tdw.ispac.ispacweb.wizard.ItemSelectionHelper;

import javax.servlet.http.HttpSession;

/**
 * Utilidad para gestionar la información de un procedimiento nuevo.
 */
public class NewProcedureMgr {
	
	private static final String ISPAC_ATTR_CREATEPCD_ID = "ISPAC_ATTR_CREATEPCD_ID";
	private static final String ISPAC_ATTR_CREATEPCD_NEWNAME = "ISPAC_ATTR_CREATEPCD_NEWNAME";
	private static final String ISPAC_ATTR_CREATEPCD_MODE = "ISPAC_ATTR_CREATEPCD_MODE";
	private static final String ISPAC_ATTR_CREATEPCD_PARENT = "ISPAC_ATTR_CREATEPCD_PARENT";
	private static final String ISPAC_ATTR_CREATEPCD_BLANK = "ISPAC_ATTR_CREATEPCD_BLANK";
	private static final String ISPAC_ATTR_CREATEPCD_TYPE = "ISPAC_ATTR_CREATEPCD_TYPE";

    static final String ACTION_BEGIN = "begin";
    static final String ACTION_MODIFY = "modify";
    static final String ACTION_NEXT = "next";

    public static final String MODE_NEW = "new";
    public static final String MODE_DRAFT = "draft";
    public static final String MODE_CLONE = "clone";
    public static final String MODE_MODIFY = "modify";
    
    private IClientContext ctx = null;
    private HttpSession httpsession = null;

    private boolean beginnewpcd = false;
    private String id = "";
    private String name = "";
    private String mode = "";
    private String action = "";
    private String parent = "";
    private boolean blank = false;
    private int type = IPcdElement.TYPE_PROCEDURE;


    /**
	 * Constructor.
	 * @param ctx Contexto de cliente.
	 * @param session Información de la sesión.
	 */
    public NewProcedureMgr(IClientContext ctx, HttpSession httpsession) {
		this.ctx = ctx;
		this.httpsession = httpsession;
	}

	public void getContext() {
		this.id = (String) httpsession
				.getAttribute(ISPAC_ATTR_CREATEPCD_ID);
		this.mode = (String) httpsession
				.getAttribute(ISPAC_ATTR_CREATEPCD_MODE);
		this.name = (String) httpsession
				.getAttribute(ISPAC_ATTR_CREATEPCD_NEWNAME);
		this.parent = (String) httpsession
				.getAttribute(ISPAC_ATTR_CREATEPCD_PARENT);
		this.type = TypeConverter.toInt(
				(Integer) httpsession.getAttribute(ISPAC_ATTR_CREATEPCD_TYPE), 
				IPcdElement.TYPE_PROCEDURE);

		this.action = "";
		this.beginnewpcd = false;
		this.blank = ((Boolean) httpsession
				.getAttribute(ISPAC_ATTR_CREATEPCD_BLANK)).booleanValue();
	}

	public void setContext() {
		httpsession.setAttribute(ISPAC_ATTR_CREATEPCD_ID, id);
		httpsession.setAttribute(ISPAC_ATTR_CREATEPCD_MODE, mode);
		httpsession.setAttribute(ISPAC_ATTR_CREATEPCD_NEWNAME, name);
		httpsession.setAttribute(ISPAC_ATTR_CREATEPCD_PARENT, parent);
		httpsession.setAttribute(ISPAC_ATTR_CREATEPCD_BLANK, new Boolean(blank));
		httpsession.setAttribute(ISPAC_ATTR_CREATEPCD_TYPE, new Integer(type));
	}

	public void clearContext() {
		httpsession.removeAttribute(ISPAC_ATTR_CREATEPCD_ID);
		httpsession.removeAttribute(ISPAC_ATTR_CREATEPCD_MODE);
		httpsession.removeAttribute(ISPAC_ATTR_CREATEPCD_NEWNAME);
		httpsession.removeAttribute(ISPAC_ATTR_CREATEPCD_PARENT);
		httpsession.removeAttribute(ISPAC_ATTR_CREATEPCD_BLANK);
		httpsession.removeAttribute(ISPAC_ATTR_CREATEPCD_TYPE);
	}

	public void setNewContext(String pcdId, int type, String mode, String name,
			String action, String parent, boolean blank) throws ISPACException {
		
		this.id = pcdId;
		this.type = type;
		this.mode = mode;
		this.name = name;
		this.action = action;
		this.parent = parent;
		this.blank = blank;
		this.beginnewpcd = action.equalsIgnoreCase(NewProcedureMgr.ACTION_BEGIN);

		processAtributeContext();
		processItemSelection();
		createName();
	}

	private void createName() throws ISPACException {
		
		if (StringUtils.isBlank(id) || StringUtils.isNotBlank(name)) {
			return;
		}

		IInvesflowAPI invesFlowAPI = ctx.getAPI();
		IProcedure pcd = invesFlowAPI.getProcedure(getIntPcdId());

		if (checkType(NewProcedureMgr.MODE_NEW))
			name = "";
		else if (checkType(NewProcedureMgr.MODE_CLONE))
			name = "Copia de " + pcd.getString("NOMBRE");
		else
			name = pcd.getString("NOMBRE");
	}

	private void processItemSelection() throws ISPACException {
		
			if (beginnewpcd) {
				ItemSelectionHelper.clear(httpsession);
				action = "";
				return;
			}

			if (StringUtils.isBlank(id)) {
				return;
			}
	
			int pcdId = getIntPcdId();

			IInvesflowAPI invesFlowAPI = ctx.getAPI();
			ItemSelectionHelper helper = new ItemSelectionHelper(httpsession);
			
			if (type == IPcdElement.TYPE_PROCEDURE) {
				
				helper.clearItemIdList(ICatalogAPI.ENTITY_CT_STAGE);
				
				IItemCollection stages = invesFlowAPI.getProcedureAPI()
					.getStages(pcdId);
				while (stages.next()) {
					IItem stage = stages.value();
					String ctStageId = stage.getString("ID_CTFASE"); 
					helper.addItemBean(ICatalogAPI.ENTITY_CT_STAGE,  null,
							stage.getString("NOMBRE"),
							ctStageId);
					
					IItemCollection stageTasks = invesFlowAPI
						.getProcedureAPI().getStageTasks(stage.getKeyInt());
					while (stageTasks.next()) {
						IItem task = stageTasks.value();
						helper.addItemBean(ICatalogAPI.ENTITY_CT_TASK,
								ctStageId,
								task.getString("NOMBRE"),
								task.getString("ID_CTTRAMITE"));
					}
				}
				
			} else { //if (type == IPcdElement.TYPE_SUBPROCEDURE) {

				helper.clearItemIdList(ICatalogAPI.ENTITY_P_ACTIVITIES);
				
				IItemCollection activities = invesFlowAPI.getProcedureAPI()
					.getStages(pcdId);
				while (activities.next()) {
					IItem activity = activities.value();
					helper.addItemBean(ICatalogAPI.ENTITY_P_ACTIVITIES, null,
							activity.getString("NOMBRE"),
							activity.getString("ID_CTFASE"));
				}
			}
//		}
	}

	public void processAtributeContext() {
		if (beginnewpcd) {
			parent = String.valueOf(IProcedureAPI.CLONEPCD_PARENT_CHILDREN);
			setContext();
		} else {
			String newname = (String) httpsession
					.getAttribute(ISPAC_ATTR_CREATEPCD_NEWNAME);
			if (name.equals("") && newname != null) {
				name = newname;
			}
			
			httpsession.setAttribute(ISPAC_ATTR_CREATEPCD_NEWNAME, name);

			String newparent = (String) httpsession
					.getAttribute(ISPAC_ATTR_CREATEPCD_PARENT);
			if (StringUtils.isBlank(parent)) {
				parent = newparent;
			}

			httpsession.setAttribute(ISPAC_ATTR_CREATEPCD_PARENT, parent);
			httpsession.setAttribute(ISPAC_ATTR_CREATEPCD_BLANK,
					new Boolean(blank));

			getContext();
		}
	}

	/**
	 * @return Devuelve el valor de la propiedad action.
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @param action Cambia el valor de la propiedad action.
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * @return Devuelve el valor de la propiedad pcdId.
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param pcdId Cambia el valor de la propiedad pcdId.
	 */
	public void setId(String pcdId) {
		this.id = pcdId;
	}

	/**
	 * @return Devuelve el valor de la propiedad pcdId.
	 */
	public int getIntPcdId() {
		try {
			return Integer.parseInt(id);
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	/**
	 * @param pcdId Cambia el valor de la propiedad pcdId.
	 */
	public void setIntPcdId(int pcdId) {
		this.id = String.valueOf(pcdId);
	}

	/**
	 * @return Devuelve el valor de la propiedad pcdName.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param pcdName Cambia el valor de la propiedad pcdName.
	 */
	public void setName(String pcdName) {
		this.name = pcdName;
	}

	/**
	 * @return Devuelve el valor de la propiedad mode.
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * @param mode Cambia el valor de la propiedad mode.
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	public boolean checkType(String type) {
		return StringUtils.equalsIgnoreCase(mode, type);
	}

	/**
	 * @return
	 */
	public String getParent() {
		return this.parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public int getIntParent() {
		try {
			return Integer.parseInt(parent);
		} catch (NumberFormatException e) {
			return IProcedureAPI.CLONEPCD_PARENT_CHILDREN;
		}
	}

	public boolean draftExists(int groupId) throws ISPACException {

		String query = new StringBuffer(" WHERE ID_GROUP=").append(groupId)
				.append(" AND ESTADO=").append(IProcedure.PCD_STATE_DRAFT)
				.toString();

		IInvesflowAPI invesFlowAPI = ctx.getAPI();
		IItemCollection coll = invesFlowAPI.getProcedures(query);

		return (coll != null) && coll.next();
	}

	public boolean isBlank() {
		return blank;
	}

	public void setBlank(boolean blank) {
		this.blank = blank;
	}
}
