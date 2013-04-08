package ieci.tdw.ispac.api.impl;

import ieci.tdw.ispac.api.IProcedureAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.context.ClientContext;

import java.io.File;
import java.util.Locale;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.apache.log4j.Logger;

/** 
 * @author Iecisa 
 * @version $Revision$ 
 *
 */
public class ProcedureAPITest extends TestCase {
	
	private static final Logger logger = Logger.getLogger("TEST");
	
	private static final int PROCEDURE_ID = 3;

	protected ClientContext getClientContext() {
		ClientContext ctx = new ClientContext();
		ctx.setAPI(new InvesflowAPI(ctx));
		ctx.setLocale(Locale.getDefault());
		return ctx;
	}

	protected IProcedureAPI getProcedureAPI() throws ISPACException {
		return getClientContext().getAPI().getProcedureAPI();
	}

	public void testExportProcedure() throws Exception {
		
		File file = null;
		
		try {
			file = getProcedureAPI().exportProcedure(PROCEDURE_ID, null);
			logger.info("Fichero de exportación: " + file);
			
			Assert.assertNotNull(file);
			Assert.assertTrue(file.isFile());
		} catch (Exception e) {
			logger.error("Error en testExportProcedure", e);
			throw e;
		} finally {
			if (file != null) {
				file.delete();
			}
		}
	}

	public void testImportProcedure() throws Exception {
		
		File file = null;
		
		try {
			
			file = getProcedureAPI().exportProcedure(PROCEDURE_ID, null);
			logger.info("Fichero de exportación: " + file);

			String res = getProcedureAPI().importProcedure(PROCEDURE_ID, true, file, true);
			logger.info(res);

		} catch (Exception e) {
			logger.error("Error en testImportProcedure", e);
			throw e;
		} finally {
			if (file != null) {
				file.delete();
			}
		}
	}
}
