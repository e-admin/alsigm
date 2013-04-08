package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IThirdPartyAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.Properties;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.item.GenericItem;
import ieci.tdw.ispac.ispaclib.thirdparty.IElectronicAddressAdapter;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacmgr.common.constants.ActionsConstants;

import java.sql.Types;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SetElectronicAddressAction extends BaseAction {

	public ActionForward executeAction(ActionMapping mapping,
									   ActionForm form,
									   HttpServletRequest request,
									   HttpServletResponse response,
									   SessionAPI session) throws Exception {

		IElectronicAddressAdapter electronicAddress = getElectronicAddress(request, session);
		if (electronicAddress != null) {
	        Properties properties = getProperties();
	        IItem item = new GenericItem(properties, "ID");
        	item.set("DIRECCIONTELEMATICA", electronicAddress.getDireccion());
			// Salva en la petición el bean del interviniente
			request.setAttribute("Substitute", new ItemBean(item));
		}
		else {
			// Salva en la petición un bean vacío
			request.setAttribute("Substitute", new ItemBean());
		}
		return mapping.findForward("success");
	}
	
    private Properties getProperties() {
    	
        int ordinal = 0;
        Properties properties = new Properties();
        properties.add( new Property(ordinal++, "ID", Types.VARCHAR));
        properties.add( new Property(ordinal++, "DIRECCIONTELEMATICA", Types.VARCHAR));

        return properties;
    }
    
    protected IElectronicAddressAdapter getElectronicAddress(HttpServletRequest request, SessionAPI session) throws ISPACException {
    	IElectronicAddressAdapter postalAddress = (IElectronicAddressAdapter) request.getAttribute(ActionsConstants.ELECTRONIC_ADDRESS);
		String id = request.getParameter("id");
		if (StringUtils.isNotBlank(id)) {
			IThirdPartyAPI thirdPartyAPI = session.getAPI().getThirdPartyAPI();
			if (thirdPartyAPI != null) {
				postalAddress = thirdPartyAPI.getElectronicAddress(id);
			}
		}
    	return postalAddress;
    }
}