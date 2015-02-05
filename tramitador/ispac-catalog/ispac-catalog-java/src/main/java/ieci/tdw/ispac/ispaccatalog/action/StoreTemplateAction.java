package ieci.tdw.ispac.ispaccatalog.action;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISPACEntities;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.ITemplateAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaccatalog.action.form.UploadForm;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.app.EntityApp;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.bean.ValidationError;
import ieci.tdw.ispac.ispaclib.configuration.ConfigurationMgr;
import ieci.tdw.ispac.ispaclib.dao.cat.CTTemplate;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.MimetypeMapping;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;

public class StoreTemplateAction extends BaseAction
{
	public ActionForward executeAction(
	ActionMapping mapping,
	ActionForm form,
	HttpServletRequest request,
	HttpServletResponse response,
	SessionAPI session)
	throws Exception
	{
		
 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_INV_DOCTYPES_EDIT,
				ISecurityAPI.FUNC_INV_TEMPLATES_EDIT });
		
		IInvesflowAPI invesFlowAPI = session.getAPI();
        ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();
    	ITemplateAPI templateAPI = invesFlowAPI.getTemplateAPI();

    	// Formulario asociado a la acción
		UploadForm defaultForm = (UploadForm) form;

		int keyId = Integer.parseInt(defaultForm.getKey());
		int entityId = Integer.parseInt(defaultForm.getEntity());
		String name = defaultForm.getProperty("NOMBRE");
		String code = defaultForm.getProperty("COD_PLANT");
		CTTemplate template = null;

		try {
			
			if (keyId == ISPACEntities.ENTITY_NULLREGKEYID) {
				
				int type = Integer.parseInt(defaultForm.getProperty("ID_TPDOC"));				
				String expresion = defaultForm.getProperty("EXPRESION");
				FormFile fichero = defaultForm.getUploadFile();
				EntityApp entityapp = catalogAPI.getCTDefaultEntityApp(entityId, getRealPath(""));
				
				// Comprobar si existe otra plantilla con el mismo nombre
				IItemCollection itemcol = catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_CT_TEMPLATE, 
						" WHERE NOMBRE = '" + DBUtil.replaceQuotes(name) + "'");				
				if (itemcol.next() && !isGeneric(templateAPI, itemcol)) {					
					ActionMessages errors = new ActionMessages();
					errors.add("property(NOMBRE)", new ActionMessage("error.template.nameDuplicated", new String[] {name}));
					saveAppErrors(request, errors);
					
					return new ActionForward(mapping.getInput());
				}
				
				// Comprobar si existe otra plantilla con el mismo código
				if (StringUtils.isNotBlank(code)) {
					itemcol = catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_CT_TEMPLATE, 
							" WHERE COD_PLANT = '" + DBUtil.replaceQuotes(code) + "'");				
					if (itemcol.next()) {					
						ActionMessages errors = new ActionMessages();
						errors.add("property(COD_PLANT)", new ActionMessage("error.template.codeDuplicated", new String[] {code}));
						saveAppErrors(request, errors);
						
						return new ActionForward(mapping.getInput());
					}
				}

				if (!entityapp.validate()) {
					
					ActionMessages errors = new ActionMessages();
					List errorList = entityapp.getErrors();
					Iterator iteError = errorList.iterator();
					while (iteError.hasNext()) {
						
						ValidationError validError = (ValidationError) iteError.next();
						ActionMessage error = new ActionMessage(validError.getErrorKey(), validError.getArgs());
						errors.add(ActionMessages.GLOBAL_MESSAGE, error);
					}
					saveAppErrors(request, errors);
					
					return new ActionForward(mapping.getInput());
				}				
				
				if (fichero.getFileName().equals("")) {
					template = templateAPI.newTemplate(type, name, code, 0, expresion, null);
				} else {
					if (fichero.getFileSize() > 0) {
						
						// Comprobar si el tipo MIME de la plantilla está soportado
						String mimeType = MimetypeMapping.getFileMimeType(fichero.getFileName());
						//Se comprueba si esta habilitado el uso de plantillas ODT
						if (StringUtils.equals(mimeType, "application/vnd.oasis.opendocument.text") && !ConfigurationMgr.getVarGlobalBoolean(session.getClientContext(), ConfigurationMgr.USE_ODT_TEMPLATES, false)){
							throw new ISPACInfo(getResources(request).getMessage("exception.template.odt.disabled"));
						}
						
						if (templateAPI.isMimeTypeSupported(mimeType)) {
							template = templateAPI.newTemplate(type, name, code, 0, expresion, fichero.getInputStream(), mimeType);
						} else { 
							throw new ISPACInfo(getResources(request).getMessage("exception.template.document.invalidFile"));
						}
					} else { 
						throw new ISPACInfo("exception.uploadfile.empty");
					}
				}
			}
			else {				
				EntityApp entityapp = catalogAPI.getCTDefaultEntityApp(entityId, getRealPath(""));
				
				// Comprobar si existe otra plantilla con el mismo nombre
				IItemCollection itemcol = catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_CT_TEMPLATE, 
						" WHERE NOMBRE = '" + DBUtil.replaceQuotes(name) + "' AND ID != " + keyId);				
 				if (itemcol.next() && !isGeneric(templateAPI, itemcol)) {					
					ActionMessages errors = new ActionMessages();
					errors.add("property(NOMBRE)", new ActionMessage("error.template.nameDuplicated", new String[] {name}));
					saveAppErrors(request, errors);
					
					return new ActionForward(mapping.getInput());
				}
 				
				// Comprobar si existe otra plantilla con el mismo código
				if (StringUtils.isNotBlank(code)) {
					itemcol = catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_CT_TEMPLATE,
							" WHERE COD_PLANT = '" + DBUtil.replaceQuotes(code) + "' AND ID != " + keyId);
					if (itemcol.next()) {					
						ActionMessages errors = new ActionMessages();
						errors.add("property(COD_PLANT)", new ActionMessage("error.template.codeDuplicated", new String[] {code}));
						saveAppErrors(request, errors);
						
						return new ActionForward(mapping.getInput());
					}
				}
 				
				defaultForm.processEntityApp(entityapp);
				entityapp.getItem().set("FECHA", new Date());
				entityapp.store();
			}
			
		} catch(Exception e) {
			ActionForward action = mapping.findForward("success");
			String url = action.getPath()
							  + "?entity="+ entityId
							  + "&type="+ defaultForm.getProperty("ID_TPDOC")
							  + "&key="+ keyId;

			request.getSession().setAttribute(BaseAction.LAST_URL_SESSION_KEY, url);
			
			if (e instanceof ISPACInfo) {
				throw e;
			} else {
				throw new ISPACInfo(e.getMessage());
			}
		}
		
		if (template != null) {
			keyId = template.getInt("TEMPLATE:ID");
		}
		
		ActionForward action = mapping.findForward("success");
		String redirected = action.getPath()
						  + "?entity="+ entityId
						  + "&type="+ defaultForm.getProperty("ID_TPDOC")
						  + "&key="+ keyId;
		return new ActionForward( action.getName(), redirected, true);
	}
	
	/**
	 * Para saber si el nombre de la plantilla que se esta creando coincide con una plantilla genérica o especifica.
	 * En este caso si el nombre de la nueva plantilla coincide con una plantilla específica no pasaría nada.
	 * @param templateAPI
	 * @param itemcol
	 * @return
	 * @throws ISPACException 
	 */
	private boolean isGeneric(ITemplateAPI templateAPI, IItemCollection itemcol) throws ISPACException {
		
		List plantillas = CollectionBean.getBeanList(itemcol);
		for(Iterator iter = plantillas.iterator(); iter.hasNext();) {
			ItemBean item = (ItemBean) iter.next();
			if(templateAPI.isProcedureTemplate(item.getItem().getInt("ID")))
				return true;
		}
		return false;
	}
	
}