package ieci.tdw.ispac.ispaccatalog.breadcrumbs;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.dao.entity.EntityDAO;

import javax.servlet.http.HttpServletRequest;

public class BreadCrumbsManager {

	private ICatalogAPI catalogAPI;

	private static BreadCrumbsManager _instance;

	private BreadCrumbsManager(ICatalogAPI catalogAPI){
		this.catalogAPI = catalogAPI;
	}

	public static BreadCrumbsManager getInstance(ICatalogAPI catalogAPI){
		if(_instance == null){
			_instance = new BreadCrumbsManager(catalogAPI);
		}
		return _instance;
	}

	public BreadCrumbsContainer getBreadCrumbs(HttpServletRequest request){
		BreadCrumbsContainer breadCrumbs = new BreadCrumbsContainer();
		String sEntityId = request.getParameter("entityId");
		String sKeyId = request.getParameter("regId");
		int entityId = -1;
		int keyId = -1;
		request.getServletPath();

		if(	("/showTemplatesList.do".equals(request.getServletPath()))
				|| ("/showTemplate.do".equals(request.getServletPath()))
		){
			setDocTypeBC(breadCrumbs, request);
			return breadCrumbs;
		}
		if(	("/showPermissions.do".equals(request.getServletPath()))
				|| ("/showTemplate.do".equals(request.getServletPath()))
		){
			setProcPermissionsBC(breadCrumbs, request);
			return breadCrumbs;
		}
		// A partir del tipo de entidad conforma la pila de migas
		// de pan.
		if((sEntityId != null) && (sKeyId != null)){
			entityId = Integer.parseInt(sEntityId);
			keyId = Integer.parseInt(sKeyId);
			switch(entityId){
				case ICatalogAPI.ENTITY_P_PROCEDURE:
					setProcBC(entityId, keyId, breadCrumbs, request);
					break;
				case ICatalogAPI.ENTITY_P_STAGE:
					setProcStageBC(entityId, keyId, breadCrumbs, request);
					break;
				case ICatalogAPI.ENTITY_P_TASK:
					setProcStageTaskBC(entityId, keyId, breadCrumbs, request);
			}
		}
		return breadCrumbs;
	}

	private void setProcStageTaskBC(int entity, int reg, BreadCrumbsContainer breadCrumbs, HttpServletRequest request){
		//Obtención de los datos de la tarea
		String nombre = null;
		int idStage = -1;
		try {
			IItem item = getEntity(entity, reg);
			nombre = item.getString("NOMBRE");
			//Obtención del camino hasta la tarea
			idStage = item.getInt("ID_FASE");
			setProcStageBC(ICatalogAPI.ENTITY_P_STAGE, idStage, breadCrumbs, request);
		} catch (ISPACException e) {
			nombre = "";
		}
		//Se deja la miga de pan
		String URL = getURL("showPTask.do", entity, reg, request);
		BreadCrumb bc = new BreadCrumb(nombre, URL, "Tarea: ");
		breadCrumbs.pushBreadCrumb(bc);

	}
	private void setProcStageBC(int entity, int reg, BreadCrumbsContainer breadCrumbs, HttpServletRequest request){
		//Obtención de los datos de la fase
		String nombre = null;
//		int idproc = -1;
		try {
			IItem item = getEntity(entity, reg);
			nombre = item.getString("NOMBRE");
			//Obtención del camino hasta la fase
//			idproc = item.getInt("ID_PCD");
//			setProcBC(ICatalogAPI.ENTITY_P_PROCEDURE, idproc, breadCrumbs, request);
		} catch (ISPACException e) {
			nombre = "";
		}

		//Se deja la miga de pan en la pila
		String URL = getURL("showPStage.do", entity, reg, request);
		BreadCrumb bc = new BreadCrumb(nombre, URL, "Fase: ");
		breadCrumbs.pushBreadCrumb(bc);
	}

	private void setProcBC(int entity, int reg, BreadCrumbsContainer breadCrumbs, HttpServletRequest request){
		String nombre = null;
		String version = null;
		//Obtención de los datos del procedimiento
		try {
			IItem item = getEntity(entity, reg);
			nombre = item.getString("NOMBRE");
			int iversion = item.getInt("NVERSION");
			version = String.valueOf(iversion + 1);
		} catch (ISPACException e) {
			nombre = "";
			version = "";
		}

		//Se deja la miga de pan
		String texto = "";
		if((nombre != null)&&(!nombre.equals(""))){
			texto = nombre;
			if((version != null) && (!version.equals(""))){
				texto = texto + " - Ver." + version;
			}
		}
		String URL = getURL("showPProcedure.do", entity, reg, request);
		BreadCrumb bc = new BreadCrumb(texto, URL, "Procedimiento: ");
		breadCrumbs.pushBreadCrumb(bc);
	}

	private void setDocTypeBC(BreadCrumbsContainer breadCrumbs, HttpServletRequest request){
		String nombre = null;

		String stype = request.getParameter("type");

 		if (stype == null)
 		{
 			stype = request.getParameter("property(ID_TPDOC)");
 		}

 		if(stype == null){
 			return;
 		}
		//Obtención de los datos del procedimiento
		try {
			IItem item = getEntity(7, Integer.parseInt(stype));
			nombre = item.getString("NOMBRE");
		} catch (ISPACException e) {
			nombre = "";
		}

		//Se deja la miga de pan
		String URL = getURL("showCTEntity.do", 7, Integer.parseInt(stype), request);
		BreadCrumb bc = new BreadCrumb(nombre, URL, "Tipo de Documento: ");
		breadCrumbs.pushBreadCrumb(bc);
	}

	private String getURL(String action, int entityId, int regId, HttpServletRequest request){
		String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() +
						request.getContextPath() + "/" + action + "?" +
						"entityId=" + String.valueOf(entityId) + "&" +
						"regId=" + String.valueOf(regId);
		return url;
	}

	private IItem getEntity(int entity, int regid) throws ISPACException{
        try
        {
            IItemCollection collection =
            catalogAPI.queryCTEntities(entity, "WHERE ID = " + regid);

			if (!collection.next())
			    throw new ISPACException(" CTEntityApp: No existe la aplicación");

			return (EntityDAO)collection.value();
        }
        catch (ISPACException e)
        {
            throw new ISPACException(" BreadCrumbsManager: getEntity()", e);
        }
	}

	private void setProcPermissionsBC(BreadCrumbsContainer breadCrumbs, HttpServletRequest request){
		String sregId = request.getParameter("regId");

 		if (sregId != null){
 			this.setProcBC(ICatalogAPI.ENTITY_P_PROCEDURE, Integer.parseInt(sregId), breadCrumbs, request);
 		}
	}
}
