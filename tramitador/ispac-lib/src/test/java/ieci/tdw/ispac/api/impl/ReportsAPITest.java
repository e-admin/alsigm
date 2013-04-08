package ieci.tdw.ispac.api.impl;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IReportsAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaclib.context.ClientContext;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Locale;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.apache.log4j.Logger;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class ReportsAPITest extends TestCase {

	private static final Logger logger = Logger.getLogger("TEST");

	private static final int REPORT_ID = 1;

	protected ClientContext getClientContext() {
		ClientContext ctx = new ClientContext();
		ctx.setAPI(new InvesflowAPI(ctx));
		return ctx;
	}

	protected IReportsAPI getReportsAPI() throws ISPACException {
		return getClientContext().getAPI().getReportsAPI();
	}

	protected IReportsAPI getReportsAPI(ClientContext ctx)
			throws ISPACException {
		return ctx.getAPI().getReportsAPI();
	}

	protected IItem getReportItem() throws ISPACException {
		IInvesflowAPI invesflowAPI = getClientContext().getAPI();
		IItem reportItem = invesflowAPI.getCatalogAPI().getCTReport(REPORT_ID);
		Assert.assertNotNull(reportItem);

		return reportItem;
	}

	public void testCheckReport() throws ISPACException {

		logger.info("testCheckReport");

		Assert.assertTrue(getReportsAPI().checkReport(
				getReportItem().getString("XML")));
	}

	public void testCompileReport() throws ISPACException {

		logger.info("testCompileReport");

		File reportFile = null;
		
		try {
			reportFile = getReportsAPI().compileReport(getReportItem());
			Assert.assertNotNull(reportFile);
			Assert.assertTrue(reportFile.isFile());
			Assert.assertTrue(reportFile.length() > 0);
		} finally {
			if (reportFile != null) {
				reportFile.delete();
			}
		}
	}

	public void testGeneratePDFReportCtReport() throws ISPACException {

		logger.info("testGeneratePDFReportCtReport");

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		IItem reportItem = getReportItem();
		
		getReportsAPI().generatePDFReport(reportItem, null, "", Locale.getDefault(), baos, 0, 0, null);
		
		Assert.assertTrue(baos.size() > 0);
	}

	public void testGeneratePDFReportCtReportParams() throws ISPACException {

		logger.info("testGeneratePDFReportCtReportParams");
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		IItem reportItem = getReportItem();

		getReportsAPI().generatePDFReport(reportItem, null, "", Locale.getDefault(), baos, 0, 0, null, null);
		
		Assert.assertTrue(baos.size() > 0);
	}

	public void testGeneratePDFReportInputStream() throws ISPACException,
			FileNotFoundException {

		logger.info("testGeneratePDFReportInputStream");

		ClientContext ctx = getClientContext();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		File reportFile = getReportsAPI(ctx).compileReport(getReportItem());

		getReportsAPI().generatePDFReport(new FileInputStream(reportFile),
				new HashMap(), ctx.getConnection().getConnection(), baos);

		Assert.assertTrue(baos.size() > 0);
	}

	public void testGenerateRTFReportCtReport() throws ISPACException,
			FileNotFoundException {

		logger.info("testGenerateRTFReportCtReport");

		ClientContext ctx = getClientContext();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		File reportFile = getReportsAPI(ctx).compileReport(getReportItem());

		getReportsAPI().generateRTFReport(new FileInputStream(reportFile),
				new HashMap(), ctx.getConnection().getConnection(), baos);

		Assert.assertTrue(baos.size() > 0);
	}

}
