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
import ieci.tdw.ispac.ispaccatalog.action.procedure.ManageVistaCuadroProcedimientoAction;
import ieci.tdw.ispac.ispaccatalog.action.procedure.tree.CuadroEntidadTreeView;
import ieci.tdw.ispac.ispaccatalog.action.procedure.tree.ElementoCuadro;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.app.EntityApp;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.bean.TreeNode;
import ieci.tdw.ispac.ispaclib.bean.TreeView;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.MimetypeMapping;

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

public class StoreTemplateFromCuadroAction extends BaseAction{
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
						ISecurityAPI.FUNC_INV_PROCEDURES_EDIT });

				IInvesflowAPI invesFlowAPI = session.getAPI();
		        ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();

		    	ITemplateAPI templateAPI = invesFlowAPI.getTemplateAPI();

				// Formulario asociado a la acción
				UploadForm defaultForm = (UploadForm) form;
				
				int keyId = Integer.parseInt(defaultForm.getKey());
				int entityId = Integer.parseInt(defaultForm.getEntity());

				try {
					
					if (keyId == ISPACEntities.ENTITY_NULLREGKEYID) {
						//obtener el id del procedimiento
						CuadroEntidadTreeView tree = (CuadroEntidadTreeView)request.getSession().getAttribute(ManageVistaCuadroProcedimientoAction.CUADRO_PROCEDIMIENTO);
						ElementoCuadro proc = (ElementoCuadro)((TreeNode)tree.getRootNodes().iterator().next()).getModelItem();
						int type = Integer.parseInt(defaultForm.getProperty("ID_TPDOC"));
						String name = defaultForm.getProperty("NOMBRE");
						String code = defaultForm.getProperty("COD_PLANT");
						String expresion = defaultForm.getProperty("EXPRESION");
						FormFile fichero = defaultForm.getUploadFile();
						IItemCollection itemcol = catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_CT_TEMPLATE, " WHERE NOMBRE = '" + DBUtil.replaceQuotes(name) + "'");
						if (itemcol.next() && isGeneric(templateAPI, itemcol)) {
							ActionMessages errors = new ActionMessages();
							errors.add("property(NOMBRE)", new ActionMessage("error.template.nameDuplicated", new String[] {name}));
							saveAppErrors(request, errors);
							
							return new ActionForward(mapping.getInput());
						}
						
						if(fichero.getFileName().equals(""))
							templateAPI.createTemplateProc(type,name, code, expresion, Integer.parseInt(proc.getRegId()), null);
						else {
							if (fichero.getFileSize() > 0) {
								
								// Comprobar si el tipo MIME de la plantilla está soportado
								String mimeType = MimetypeMapping.getFileMimeType(fichero.getFileName());
								if (templateAPI.isMimeTypeSupported(mimeType)) {
									templateAPI.createTemplateProc(type,name, code, expresion, Integer.parseInt(proc.getRegId()), fichero.getInputStream(), mimeType);
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
						defaultForm.processEntityApp(entityapp);
						entityapp.store();
					}
				
				}catch(ISPACException e) {
					throw new ISPACInfo(e.getMessage());
				
				}finally{
				}
				
				TreeView tree = (TreeView)request.getSession().getAttribute(ManageVistaCuadroProcedimientoAction.CUADRO_PROCEDIMIENTO);
				if (tree!=null)
					tree.getSelectedNode().refresh();
				
				request.setAttribute("Refresh","true");
				
				return mapping.findForward("success");
			}
	
			/**
			 * Para saber si el nombre de la plantilla que se esta creando coincide con una plantilla genérica o especifica.
			 * En este caso si el nombre de la nueva plantilla coincide con una plantilla específica no pasaría nada.
			 * @param itemcol
			 * @param templateAPI
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
