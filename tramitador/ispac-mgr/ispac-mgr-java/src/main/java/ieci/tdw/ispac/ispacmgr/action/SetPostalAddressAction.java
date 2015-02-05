package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IThirdPartyAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.Properties;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.item.GenericItem;
import ieci.tdw.ispac.ispaclib.thirdparty.IPostalAddressAdapter;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacmgr.common.constants.ActionsConstants;

import java.sql.Types;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SetPostalAddressAction extends BaseAction {

	public ActionForward executeAction(ActionMapping mapping,
									   ActionForm form,
									   HttpServletRequest request,
									   HttpServletResponse response,
									   SessionAPI session) throws Exception {

		IPostalAddressAdapter postalAddress = getPostalAddress(request, session);
		if (postalAddress != null) {
	        Properties properties = getProperties();
	        IItem item = new GenericItem(properties, "ID");
        	item.set("IDDIRECCIONPOSTAL", postalAddress.getId());
	        item.set("DOMICILIO", postalAddress.getDireccionPostal());
        	item.set("CPOSTAL", postalAddress.getCodigoPostal());
        	item.set("CIUDAD", postalAddress.getMunicipio());
        	//item.set("REGIONPAIS", postalAddress. getProvincia());
        	String regionPais = postalAddress.getProvincia();
        	if (StringUtils.isNotEmpty(postalAddress.getPais()))
        		regionPais += "/"+postalAddress.getPais();
        	item.set("REGIONPAIS", regionPais);
        	item.set("TFNOFIJO", postalAddress.getTelefono());
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
        properties.add( new Property(ordinal++, "DOMICILIO", Types.VARCHAR));
        properties.add( new Property(ordinal++, "CPOSTAL", Types.VARCHAR));
        properties.add( new Property(ordinal++, "CIUDAD", Types.VARCHAR));
        properties.add( new Property(ordinal++, "REGIONPAIS", Types.VARCHAR));
        properties.add( new Property(ordinal++, "TFNOFIJO", Types.VARCHAR));
        properties.add( new Property(ordinal++, "TFNOMOVIL", Types.VARCHAR));
        properties.add( new Property(ordinal++, "IDDIRECCIONPOSTAL", Types.VARCHAR));
        
        return properties;
    }
    
    protected IPostalAddressAdapter getPostalAddress(HttpServletRequest request, SessionAPI session) throws ISPACException {
    	IPostalAddressAdapter postalAddress = (IPostalAddressAdapter) request.getAttribute(ActionsConstants.POSTAL_ADDRESS);
		String id = request.getParameter("id");
		if (StringUtils.isNotBlank(id)) {
			IThirdPartyAPI thirdPartyAPI = session.getAPI().getThirdPartyAPI();
			if (thirdPartyAPI != null) {
				postalAddress = thirdPartyAPI.getPostalAddress(id);
			}
		}
    	return postalAddress;
    }
}