package ieci.tdw.ispac.ispaccatalog.app;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaclib.app.SimpleEntityApp;
import ieci.tdw.ispac.ispaclib.context.ClientContext;

public class PSincNodeApp extends SimpleEntityApp {

	public PSincNodeApp(ClientContext context) {
		super(context);
	}

	public IItem processItem(IItem item) throws ISPACException {
		return item;
	}

	public boolean validate() throws ISPACException {
	    return super.validate();
	}

	public void initiate() throws ISPACException {
		super.initiate();
    }

	public void store() throws ISPACException {
		super.store();
	}
}
