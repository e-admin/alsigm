package ieci.tdw.ispac.ispacweb.security;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.InvesflowAPI;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.resp.User;
import ieci.tdw.ispac.ispaclib.utils.CollectionUtils;
import junit.framework.Assert;
import junit.framework.TestCase;

/** 
 * @author Iecisa 
 * @version $Revision$ 
 *
 */
public class UserCredentialsTest extends TestCase {

	protected ClientContext getClientContext() throws ISPACException {
		ClientContext ctx = new ClientContext();
		ctx.setAPI(new InvesflowAPI(ctx));
		ctx.setUser(new User("1-4", "tramitador"));
		return ctx;
	}

	public void testContainsAnyFunction() throws ISPACException {
		
		UserCredentials credentials = new UserCredentials(getClientContext());
		
		boolean result = credentials.containsAnyFunction(CollectionUtils
				.createList(new String[] { 
						"NO_EXISTE_ESTA_FUNCION",
						"FUNC_INV_PROCEDURES_READ",
						"FUNC_INV_PROCEDURES_EDIT" }));
		Assert.assertFalse(result);

		result = credentials.containsAnyFunction(CollectionUtils
				.createList(new String[] { 
						"NO_EXISTE_ESTA_FUNCION",
						"FUNC_INV_PROCEDURES_READ",
						"FUNC_INV_PROCEDURES_EDIT",
						"FUNC_ENTERCATALOG" }));
		Assert.assertTrue(result);

	}

	public void testContainsFunction() throws ISPACException {
		
		UserCredentials credentials = new UserCredentials(getClientContext());
		
		Assert.assertFalse(credentials.containsFunction("NO_EXISTE_ESTA_FUNCION"));
		Assert.assertFalse(credentials.containsFunction("FUNC_INV_PROCEDURES_READ"));
		Assert.assertTrue(credentials.containsFunction("FUNC_ENTERCATALOG"));
	}

}
