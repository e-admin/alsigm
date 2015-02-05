package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IThirdPartyAPI;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.Properties;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.item.GenericItem;
import ieci.tdw.ispac.ispaclib.thirdparty.IElectronicAddressAdapter;
import ieci.tdw.ispac.ispaclib.thirdparty.IPostalAddressAdapter;
import ieci.tdw.ispac.ispaclib.thirdparty.IThirdPartyAdapter;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacmgr.action.form.ThirdPartyForm;

import java.sql.Types;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SetInterestedAction extends BaseAction {

	public ActionForward executeAction(ActionMapping mapping,
									   ActionForm form,
									   HttpServletRequest request,
									   HttpServletResponse response,
									   SessionAPI session) throws Exception {
		
   	    IInvesflowAPI invesFlowAPI = session.getAPI();
		IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();

		// Obtener la información del tercero
		IThirdPartyAdapter thirdParty = getThirdParty((ThirdPartyForm)form, request, session);

		if (thirdParty != null) {

			// INTERESADO PRINCIPAL
	        Properties properties = getProperties();
	        IItem item = new GenericItem(properties, "ID");
        	item.set("TIPOPERSONA", thirdParty.getTipoPersona());
        	item.set("IDTITULAR", thirdParty.getIdExt());
        	item.set("IDENTIDADTITULAR", thirdParty.getNombreCompleto());
        	item.set("NIFCIFTITULAR",thirdParty.getIdentificacion());
        	
        	IPostalAddressAdapter dirPostal = thirdParty.getDefaultDireccionPostal();
        	if (dirPostal != null) {
            	item.set("IDDIRECCIONPOSTAL", dirPostal.getId());
        		item.set("DOMICILIO", dirPostal.getDireccionPostal());
        		item.set("CPOSTAL", dirPostal.getCodigoPostal());
        		item.set("CIUDAD", dirPostal.getMunicipio());
        		item.set("TFNOFIJO", dirPostal.getTelefono());

            	String regionPais = dirPostal.getProvincia();
            	if (StringUtils.isNotEmpty(dirPostal.getPais()))
            		regionPais += "/"+dirPostal.getPais();
            	
            	item.set("REGIONPAIS", regionPais);
        	}
        	
        	IElectronicAddressAdapter dirElectronica = thirdParty.getDefaultDireccionElectronica();
        	if (dirElectronica != null) {
        		if (dirElectronica.getTipo() == IElectronicAddressAdapter.MOBILE_PHONE_TYPE) {
        			item.set("TFNOMOVIL", dirElectronica.getDireccion());
        		} else {
        			item.set("DIRECCIONTELEMATICA", dirElectronica.getDireccion());
        		}
        	}
        	
        	String addressType = IThirdPartyAdapter.ADDRESS_TYPE_POSTAL;
        	if (thirdParty.isNotificacionTelematica()) {
        		addressType = IThirdPartyAdapter.ADDRESS_TYPE_TELEMATIC;
        	}
        	item.set("TIPODIRECCIONINTERESADO", addressType);
        	item.set("SUSTITUTOTIPODIRECCIONINTERESADO", entitiesAPI.getSubstitute(SpacEntities.SPAC_TBL_005, addressType));

			// Salva en la petición el bean del interviniente
			request.setAttribute("Substitute", new ItemBean(item));
		}
		else {
			// Salva en la petición un bean vacío
			request.setAttribute("Substitute", new ItemBean());
		}
		
		// Nombre de la variable de sesión que mantiene los parámetros
		// del tag de búsqueda.
		String parameters = request.getParameter("parameters");
//		if (StringUtils.isNotBlank(parameters)) {
//
//			// Obtiene los parámetros del tag de búsqueda y los salva en el request
//			String sParameters = session.getClientContext()
//				.getSsVariable(parameters);
//			if (sParameters != null) {
//				request.setAttribute(parameters, ActionFrameTag.toMap(sParameters));
//			}
//		}

		if (StringUtils.isNotBlank(parameters)) {

			// Obtiene los parametros del tag y los salva en la request
	        Map sParameters = (Map)request.getSession().getAttribute(parameters);
	        if (sParameters != null) {
	            request.setAttribute("parameters", sParameters);
	        }
		}

		return mapping.findForward("success");
	}
	
    private Properties getProperties() {
    	
        int ordinal = 0;
        Properties properties = new Properties();
        properties.add( new Property(ordinal++, "ID", Types.VARCHAR));
        properties.add( new Property(ordinal++, "TIPOPERSONA", Types.VARCHAR));
        properties.add( new Property(ordinal++, "IDTITULAR", Types.VARCHAR));
        properties.add( new Property(ordinal++, "IDENTIDADTITULAR", Types.VARCHAR));  
        properties.add( new Property(ordinal++, "DOMICILIO", Types.VARCHAR));
        properties.add( new Property(ordinal++, "CPOSTAL", Types.VARCHAR));
        properties.add( new Property(ordinal++, "CIUDAD", Types.VARCHAR));
        properties.add( new Property(ordinal++, "REGIONPAIS", Types.VARCHAR));
        properties.add( new Property(ordinal++, "TFNOFIJO", Types.VARCHAR));
        properties.add( new Property(ordinal++, "TFNOMOVIL", Types.VARCHAR));
        properties.add( new Property(ordinal++, "IDDIRECCIONPOSTAL", Types.VARCHAR));
        properties.add( new Property(ordinal++, "DIRECCIONTELEMATICA", Types.VARCHAR));
        properties.add( new Property(ordinal++, "TIPODIRECCIONINTERESADO", Types.VARCHAR));
        properties.add( new Property(ordinal++, "SUSTITUTOTIPODIRECCIONINTERESADO", Types.VARCHAR));
        properties.add(new Property(ordinal++, "NIFCIFTITULAR", Types.VARCHAR));

        return properties;
    }
    
    protected IThirdPartyAdapter getThirdParty(ThirdPartyForm form, HttpServletRequest request, SessionAPI session) throws ISPACException {
    	IThirdPartyAdapter thirdParty = (IThirdPartyAdapter) request.getAttribute("ThirdParty");
    	if (thirdParty == null) {
    		if (StringUtils.isNotBlank(form.getId())) {
    			IThirdPartyAPI thirdPartyAPI = session.getAPI().getThirdPartyAPI();
    			if (thirdPartyAPI != null) {
    				thirdParty = thirdPartyAPI.lookupById(form.getId());
    				if (StringUtils.isNotEmpty(form.getPostalAddressId())){
    					IPostalAddressAdapter postalAddress = thirdPartyAPI.getPostalAddress(form.getPostalAddressId());
    					thirdParty.setDefaultDireccionPostal(postalAddress);
    				}
    				if (StringUtils.isNotEmpty(form.getElectronicAddressId())){
    					IElectronicAddressAdapter electronicAddress = thirdPartyAPI.getElectronicAddress(form.getElectronicAddressId());
    					thirdParty.setDefaultDireccionElectronica(electronicAddress);
    				}
    			}
    		}
    	}
    	return thirdParty;
    }
}