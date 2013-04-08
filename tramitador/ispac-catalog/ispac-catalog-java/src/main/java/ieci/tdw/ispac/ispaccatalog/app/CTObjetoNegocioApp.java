package ieci.tdw.ispac.ispaccatalog.app;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaccatalog.messages.Messages;
import ieci.tdw.ispac.ispaclib.app.SimpleEntityApp;
import ieci.tdw.ispac.ispaclib.bean.ValidationError;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

public class CTObjetoNegocioApp extends SimpleEntityApp {
	
	public CTObjetoNegocioApp(ClientContext context) {
		super(context);
	}
	
	public boolean validate() throws ISPACException {
		
		ValidationError error = null;

		String nombre = (String)getProperty("NOMBRE");
		if(StringUtils.isBlank(nombre)){
			error = new ValidationError("NOMBRE", "errors.required", new String[]{Messages.getString(mContext.getAppLanguage(), "form.app.propertyLabel.name")});
			addError(error);
			return false;
		}
		return true;
	}
	
}
