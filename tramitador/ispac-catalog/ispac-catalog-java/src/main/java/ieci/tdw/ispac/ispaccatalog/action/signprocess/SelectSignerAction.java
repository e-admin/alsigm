package ieci.tdw.ispac.ispaccatalog.action.signprocess;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.ISignAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.errors.sign.SameMinhapSignerException;
import ieci.tdw.ispac.api.errors.sign.SameSignerException;
import ieci.tdw.ispac.api.errors.sign.SignerAlreadyExistsException;
import ieci.tdw.ispac.api.errors.sign.SignerMinhapAlreadyExistsException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.ispaccatalog.action.form.UsersForm;
import ieci.tdw.ispac.ispaccatalog.action.usrmanager.ViewUsersManagerAction;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.sign.portafirmas.ProcessSignConnectorFactory;
import ieci.tdw.ispac.ispaclib.utils.TypeConverter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SelectSignerAction extends ViewUsersManagerAction {
	
	protected static final String PARAM_SIGN_PROCESS_ID = "circuitoId";
	protected static final String PARAM_REPLACE_SIGNER_ID = "replSigner";
	protected static final String PARAM_SIGNER_NAME = "signerName";
	protected static final String PARAM_SIGNER_TYPE = "signerType";
	
	public ActionForward executeAction(ActionMapping mapping,
	                                   ActionForm form,
                                       HttpServletRequest request,
                                       HttpServletResponse response,
                                       SessionAPI session) throws Exception {
		
 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_INV_SIGN_CIRCUITS_EDIT });

        IInvesflowAPI invesFlowAPI = session.getAPI();
        ISignAPI signAPI = invesFlowAPI.getSignAPI();
		
		UsersForm userForm = (UsersForm) form;
		if(request.getParameterValues("seleccion")!=null){
			userForm.setMultibox(request.getParameterValues("seleccion"));
		}
        
		try {
			 String [] multibox=null;
	        // Obtener el firmante seleccionado :Caso a�adir firmante
			if(request.getAttribute("selVarios")!=null || request.getParameter("selVarios")!=null){
			// En multibox tendremos los uids de los usuarios
	       multibox = userForm.getMultibox();
	        if(multibox==null || multibox.length==0){
	        	String uid = userForm.getUid();
	        	multibox=new String[1];
	        	multibox[0]=uid;
		       
	        }
			}
			
			//Caso sustitute
			else{
				multibox=new String[1];
	        	multibox[0]=userForm.getUid();
			}
	        
	        for(int i=0; i<multibox.length; i++){
	        	String uid=multibox[i];
		        int keyId = TypeConverter.parseInt(request.getParameter(PARAM_REPLACE_SIGNER_ID), -1);
		        int circuitId = TypeConverter.parseInt(request.getParameter(PARAM_SIGN_PROCESS_ID), -1);
		        
				if(ProcessSignConnectorFactory.getInstance().isDefaultConnector()){
			        if (keyId > 0) {
			        	signAPI.substituteSigner(circuitId, keyId, uid);
			        } else {
			        	signAPI.addSigner(circuitId, uid);
			        }
				} else {
			        String signerName = request.getParameter(PARAM_SIGNER_NAME);
			        String signerType = request.getParameter(PARAM_SIGNER_TYPE);
			        if (keyId > 0) {
			        	signAPI.substituteSigner(circuitId, keyId, uid, signerName, signerType);
			        } else {
			        	signAPI.addSigner(circuitId, uid, signerName, signerType);
			        }
				}
	        }
            // Refrescar la pantalla desde la que se llama para que se actualice la lista de firmantes 
            request.setAttribute("Refresh", "true");
	        
	        return mapping.findForward("success");

		} catch (SameSignerException e) {
			request.setAttribute(PARAM_INVALID_SELECT, Boolean.TRUE);
			String respName = (e.getResponsible() != null ? e.getResponsible().getName(): "");
			throw new ISPACInfo("exception.signprocess.signerSubstituted", new String[] { respName });
		} catch (SameMinhapSignerException e) {
			request.setAttribute(PARAM_INVALID_SELECT, Boolean.TRUE);
			String respName = (e.getResponsible() != null ? e.getResponsible(): "");
			throw new ISPACInfo("exception.signprocess.signerSubstituted", new String[] { respName });			
		} catch (SignerAlreadyExistsException e) {
			request.setAttribute(PARAM_INVALID_SELECT, Boolean.TRUE);
			String respName = (e.getResponsible() != null ? e.getResponsible().getName(): "");
			throw new ISPACInfo("exception.signprocess.signerRepeated", new String[] { respName });
		} catch (SignerMinhapAlreadyExistsException e) {
			request.setAttribute(PARAM_INVALID_SELECT, Boolean.TRUE);
			String respName = (e.getResponsible() != null ? e.getResponsible(): "");
			throw new ISPACInfo("exception.signprocess.signerRepeated", new String[] { respName });			
		} catch (ISPACException e) {
			throw new ISPACInfo(e.getMessage(), e.getArgs());
		}
    }

}