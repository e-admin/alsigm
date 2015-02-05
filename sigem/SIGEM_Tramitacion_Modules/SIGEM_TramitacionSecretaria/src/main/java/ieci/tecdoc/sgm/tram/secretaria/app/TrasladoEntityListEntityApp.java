package ieci.tecdoc.sgm.tram.secretaria.app;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.app.SubstituteEntityListEntityApp;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tecdoc.sgm.tram.secretaria.helper.SecretariaConstants;

public class TrasladoEntityListEntityApp extends SubstituteEntityListEntityApp {

	private static final long serialVersionUID = -4388852799326434685L;

	public TrasladoEntityListEntityApp(ClientContext context) {
		super(context);
	}

	protected void addFilter(StringBuffer sqljoinquery) throws ISPACException {
		sqljoinquery.append(  " AND ROL='"+ DBUtil.replaceQuotes(SecretariaConstants.ROL_TRASLADO)+ "'");
	}




}