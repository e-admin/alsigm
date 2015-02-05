package com.ieci.tecdoc.isicres.desktopweb.utils;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import com.ieci.tecdoc.common.exception.AttributesException;
import com.ieci.tecdoc.common.exception.BookException;
import com.ieci.tecdoc.common.exception.DistributionException;
import com.ieci.tecdoc.common.exception.ReportException;
import com.ieci.tecdoc.common.exception.SecurityException;
import com.ieci.tecdoc.common.exception.SessionException;

/**
 * @author LMVICENTE
 * @creationDate 30-abr-2004 11:43:12
 * @version
 * @since
 */
public class ResponseUtils {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	/***************************************************************************
	 * Constructors
	 **************************************************************************/

	/***************************************************************************
	 * Public methods
	 **************************************************************************/

	public static void generateJavaScriptError(Writer writer,
			String javascriptCode) throws IOException {
		writer.write("<script>alert(top.GetIdsLan(\"" + javascriptCode
				+ "\"));</script>");
	}

	public static void generateJavaScriptErrorForQuery(Writer writer,
			int numFdrs) throws IOException {
		writer.write("<script>");
		writer
				.write("alert(top.GetIdsLan(\"IDS_LABEL_TOTAL_FDRS_FOUND\") + \" : "
						+ numFdrs
						+ ". \" + top.GetIdsLan(\"IDS_LABEL_SHOW_FIRST_FDRS\"));");
		writer.write("</script>");
	}

	public static void generateJavaScriptErrorForSave(Writer writer)
			throws IOException {
		writer.write("<script language=javascript>");
		writer.write("top.Main.Folder.FolderBar.bLoadForm = true;");
		writer.write("g_CloseFolder=0;");
		writer.write("</script>");
	}

	public static void generateJavaScriptErrorForUpdateFolderBadCtrls(
			Writer writer, List badCtrls) throws IOException {
		writer.write("<SCRIPT LANGUAGE=\"javascript\">");
		writer.write("top.Main.Folder.FolderBar.ResetForm();");
		writer.write("top.Main.Folder.ToolBarFrm.ToolBarEnabled();");
		for (Iterator it = badCtrls.iterator(); it.hasNext();) {
			writer.write("top.Main.Folder.FolderBar.SetBadField(\""
					+ it.next().toString() + "\");");
		}
		writer.write("top.Main.Folder.FolderBar.ShowFirstBadField();");
		writer.write("</SCRIPT>");
	}

	public static void generateJavaScriptErrorForUpdateFolderNoBadCtrls(
			Writer writer) throws IOException {
		writer.write("<SCRIPT LANGUAGE=\"javascript\">");
		writer.write("top.Main.Folder.FolderBar.ResetForm();");
		writer.write("top.Main.Folder.ToolBarFrm.ToolBarEnabled();");
		writer.write("</SCRIPT>");
	}

	public static void generateJavaScriptErrorForPersistFields(Writer writer)
			throws IOException {
		writer.write("<script language=javascript>");
		writer
				.write("parent.frPersistBtn.document.getElementById('btnCancel').disabled = false;");
		writer.write("</script>");
	}

	public static void generateJavaScriptErrorDtrReject(
			Writer writer, int initValue) throws IOException {
		generateJavaScriptErrorDtrChangeDestination(writer, initValue);
	}

	public static void generateJavaScriptErrorDtrChangeDestination(
			Writer writer, int initValue) throws IOException {
		writer.write("<script>");
		writer.write("top.Main.Distr.DoOnLoad(" + initValue + ");");
		writer.write("</script>");
	}

	public static void generateJavaScriptErrorDtrAceptRechEx(Writer writer,
			int initValue) throws IOException {
		writer.write("<script>");
		writer.write("top.Main.Folder.FolderBar.EnabledToolbar();");
		writer.write("</script>");
	}

	public static void generateJavaScriptError(Writer writer,
			SecurityException sE, Locale locale) throws IOException {
		generateJavaScriptLog(writer, sE.getMessage(locale));
	}

	public static void generateJavaScriptError(Writer writer,
			SecurityException sE) throws IOException {
		if (sE.getCode().equals(SecurityException.ERROR_USER_NOTFOUND)) {
			generateJavaScriptError(writer, "IDS_INVALIDUSERNAME");
		} else if (sE.getCode().equals(
				SecurityException.ERROR_PASSWORD_INCORRECT)) {
			generateJavaScriptError(writer, "IDS_INVALIDPASSWORD");
		} else if (sE.getCode().equals(SecurityException.ERROR_USER_ISLOCKED)) {
			generateJavaScriptError(writer, "IDS_USERLOCKED");
		} else if (sE.getCode().equals(SecurityException.ERROR_USER_WASLOCKED)) {
			generateJavaScriptError(writer, "IDS_TOOMANYCONN");
		} else if (sE.getCode().equals(SecurityException.ERROR_IUSERUSERTYPE_NOT_FOUND)) {
			generateJavaScriptError(writer, "IDS_ERROR_IUSERUSERTYPE_NOT_FOUND");
		}
		else {
			generateJavaScriptLog(writer, sE.getMessage());
		}
	}

	public static void generateJavaScriptError(Writer writer, BookException bE)
			throws IOException {
		generateJavaScriptLog(writer, bE.getMessage());
	}

	public static void generateJavaScriptError(Writer writer,
			DistributionException bE) throws IOException {
		generateJavaScriptLog(writer, bE.getMessage());
	}

	public static void generateJavaScriptError(Writer writer, Exception bE)
			throws IOException {
		generateJavaScriptLog(writer, bE.getMessage());
	}

	public static void generateJavaScriptError(Writer writer, ReportException bE)
			throws IOException {
		generateJavaScriptLog(writer, bE.getMessage());
	}

	public static void generateJavaScriptError(Writer writer,
			AttributesException bE, Locale locale) throws IOException {
		generateJavaScriptLog(writer, bE.getMessage(locale));
	}

	/*
	 * public static void generateJavaScriptError(Writer writer,
	 * AttributesException bE) throws IOException {
	 * generateJavaScriptLog(writer, bE.getMessage()); }
	 */

	public static void generateJavaScriptError(Writer writer,
			SessionException sE) throws IOException {
		generateJavaScriptLog(writer, sE.getMessage());
	}

	public static void generateJavaScriptErrorSessionExpired(Writer writer,
			SessionException sE, String idioma, Long numIdioma)
			throws IOException {
		generateJavaScriptLogSessionExpired(writer, sE.getMessage(), idioma,
				numIdioma);
	}

	public static void generateJavaScriptErrorSessionExpiredOpenFolder(
			Writer writer, SessionException sE, String idioma, Long numIdioma,
			Boolean form) throws IOException {
		writer.write("alert(\"" + sE.getMessage() + "\");");
		if (form.booleanValue()) {
			writer.write("window.open(\"./__index.jsp?Idioma=" + idioma
					+ "&numIdioma=" + numIdioma
					+ "\" , \"_top\",\"location=no\",true);");
		} else {
			writer
					.write("top.window.opener.location.href=\"./__index.jsp?Idioma="
							+ idioma
							+ "&numIdioma="
							+ numIdioma
							+ "\";top.window.close();");
		}
		// generateJavaScriptLogSessionExpiredOpenFolder(writer,
		// sE.getMessage(), idioma, numIdioma, form);
	}

	public static void generateJavaScriptVldBooks(Writer writer, String html,
			int initValue) throws IOException {
		String scriptString = "<script language=javascript>top.Main.Distr.SelectBook(\""
				+ html + "\", \"" + initValue + "\");</script>";
		writer.write(scriptString);
	}

	public static void generateJavaScriptVldBooksDtrAcceptEx(Writer writer,
			String html, int initValue) throws IOException {
		String scriptString = "<script language=javascript>top.Main.Folder.FolderBar.SelectBook(\""
				+ html + "\", \"" + initValue + "\");</script>";
		writer.write(scriptString);
	}

	public static void generateJavaScriptLog(Writer writer, String message)
			throws IOException {
		writer.write("<script>alert(\"" + message + "\");</script>");
	}

	public static void generateJavaScriptLogDtrDist(Writer writer,
			String rNoDistributed, String rDistributed, String message)
			throws IOException {
		StringBuffer message1 = new StringBuffer();
		message1.append("<script language=javascript>");
		if (rNoDistributed != null) {
			if (rDistributed != null) {
				message1.append("top.SetRegDist(\"" + rDistributed + "\");");
				message1.append("alert(\"" + rNoDistributed + "\");");
			} else {
				message1.append("alert(\"" + rNoDistributed + "\");");
			}
		} else if (rDistributed != null) {
			message1.append("top.SetRegDist(\"" + rDistributed + "\");");
			message1.append("alert(\"" + message + "\");");

		} else {
			message1.append("alert(\"" + message + "\");");
		}
		message1.append("</script>");
		writer.write(message1.toString());
	}

	public static String generateJavaScriptLogFileDownLoad(String message)
			throws IOException {
		String messageError = "<script language=\"javascript\">"
				+ "top.g_ActivateTree=true;if (!top.g_FolderView){top.g_OpcAval=true;top.Main.Folder.ToolBarFrm.habilitar();}else{top.Main.Folder.ToolBarFrm.ToolBarEnabled();}"
				+ "alert(\"" + message + "\");" + "</script>";
		return messageError;
	}

	public static String generateJavaScriptLogReportDoc(String message)
			throws IOException {
		//generamos un alert con el mensaje y cerramos la pantalla que se intenta abrir
		String messageError = "<script language=\"javascript\">alert(\"" + message + "\");window.close();</script>";

		return messageError;
	}

	public static void ResponseErrorMsg(Writer writer, String message)
			throws IOException {
		writer.write("alert(\"" + message + "\");");
	}

	public static void generateJavaScriptLogVldDirection(Writer writer,
			String message) throws IOException {
		writer.write("<script>alert(\"" + message
				+ "\");window.close();</script>");
	}

	public static void generateJavaScriptLogGetSearchDist(Writer writer,
			String message) throws IOException {
		writer.write("<html><head>");
		writer.write("<script language=javascript>");
		writer.write("alert(\"" + message + "\");");
		writer.write("top.close();");
		writer.write("</script>");
		writer.write("</head>");
		writer.write("<body style=\"cursor:default\"></body></html>");
	}

	public static void generateJavaScriptLogGetSearchAsocRegs(Writer writer,
			String message) throws IOException {
		writer.write("<html><head>");
		writer.write("<script language=javascript>");
		writer.write("alert(\"" + message + "\");");
		writer.write("top.close();");
		writer.write("</script>");
		writer.write("</head>");
		writer.write("<body style=\"cursor:default\"></body></html>");
	}

	public static void generateJavaScriptLogValidateSearchDist(Writer writer,
			String message) throws IOException {
		writer.write("<script language=javascript>");
		writer.write("alert(\"" + message + "\");");
		writer.write("</script>");
	}

	public static void generateJavaScriptLogValidateSearchAsocRegs(Writer writer,
			String message) throws IOException {
		writer.write("<script language=javascript>");
		writer.write("alert(\"" + message + "\");");
		writer.write("</script>");
	}

	public static void generateJavaScriptLogForSave(Writer writer,
			String message) throws IOException {
		writer.write("<script>");
		writer.write("if (top.g_ErrorOnValidate == false){alert(\"" + message
				+ "\");}");
		writer.write("top.g_ErrorOnValidate = false;");
		writer.write("</script>");
	}

	public static void generateJavaScriptLogSessionExpiredCloseFolder(
			Writer writer, String message) throws IOException {
		/*
		 * writer.write("<script language=\"javascript\">alert(\"" + message +
		 * "\");</script>"); writer.write("<html>"); writer.write("<body
		 * onload=\"window.close();\">"); writer.write("</body>");
		 * writer.write("</html>");
		 */}

	public static void generateJavaScriptLogFrmPersistFields(Writer writer,
			String fields) throws IOException {
		writer.write("<script language=\"javascript\">");
		writer.write("top.strFlds = \"" + fields + "\";");
		writer.write("</script>");
	}

	public static void generateJavaScriptLogSetPersistFields(Writer writer,
			String fields) throws IOException {
		writer.write("<script language=\"javascript\">");
		writer.write("var sFields = \"" + fields + "\";");
		writer.write("top.g_ArrFldsToPersist = sFields.split(';');");
		writer.write("</script>");
	}

	public static void generateJavaScriptLogSessionExpired(Writer writer,
			String message, String idioma, Long numIdioma) throws IOException {
		writer.write("<script language=\"javascript\">alert(\"" + message
				+ "\");");
		writer.write("window.open(\"./__index.jsp?Idioma=" + idioma
				+ "&numIdioma=" + numIdioma
				+ "\" , \"_top\",\"location=no\",true);");
		writer.write("</script>");
	}

	public static void generateJavaScriptLogSessionExpiredDtrAcceptRechEx(
			Writer writer, String message, String idioma, Long numIdioma)
			throws IOException {
		writer.write("<script language=\"javascript\">alert(\"" + message
				+ "\");");
		writer.write("</script>");
	}

	public static void generateJavaScriptLogSessionExpiredGetSearchDist(
			Writer writer, String message, String idioma, Long numIdioma)
			throws IOException {
		writer.write("<script language=\"javascript\">alert(\"" + message
				+ "\");");
		writer
				.write("if (top.window.opener){top.window.opener.location.href=\"./__index.jsp?Idioma="
						+ idioma
						+ "&numIdioma="
						+ numIdioma
						+ "\";top.window.close();}");
		writer.write("else {window.close();}");
		writer.write("</script>");
	}

	public static void generateJavaScriptLogSessionExpiredValidateUnit(
			Writer writer, String message, String idioma, Long numIdioma)
			throws IOException {
		writer.write("alert(\"" + message + "\");");
		writer
				.write("if(top.window.opener != null){top.window.opener.location.href=\"./__index.jsp?Idioma="
						+ idioma
						+ "&numIdioma="
						+ numIdioma
						+ "\";top.window.close();}else{top.window.close();} ");
		// writer.write("</script>");
	}

	public static void generateJavaScriptLogSessionExpiredOpenFolder(
			Writer writer, String message, String idioma, Long numIdioma,
			Boolean form) throws IOException {
		writer.write("<script language=\"javascript\">alert(\"" + message
				+ "\");");
		if (form.booleanValue()) {
			writer.write("window.open(\"./__index.jsp?Idioma=" + idioma
					+ "&numIdioma=" + numIdioma
					+ "\" , \"_top\",\"location=no\",true);");
		} else {
			writer
					.write("top.window.opener.location.href=\"./__index.jsp?Idioma="
							+ idioma
							+ "&numIdioma="
							+ numIdioma
							+ "\";top.window.close();");
		}
		writer.write("</script>");

	}

	public static void generateJavaScriptLogSessionExpiredDtrfdr(Writer writer,
			String message, String idioma, Long numIdioma) throws IOException {
		writer.write("<script language=\"javascript\">alert(\"" + message
				+ "\");");
		writer
				.write("if(!top.g_FolderView){window.open(\"./__index.jsp?Idioma="
						+ idioma
						+ "&numIdioma="
						+ numIdioma
						+ "\" , \"_top\",\"location=no\",true);}");
		writer
				.write("else{top.g_FolderPId=-1;top.window.opener.location.href=\"./__index.jsp?Idioma="
						+ idioma
						+ "&numIdioma="
						+ numIdioma
						+ "\";top.window.close();}");
		writer.write("</script>");

	}

	public static void generateJavaScriptLogSessionExpiredFrmData(
			Writer writer, String message, String idioma, Long numIdioma)
			throws IOException {
		writer.write("alert(\"" + message + "\");");
		writer
				.write("if(!top.g_FolderView){window.open(\"./__index.jsp?Idioma="
						+ idioma
						+ "&numIdioma="
						+ numIdioma
						+ "\" , \"_top\",\"location=no\",true);}");
		writer
				.write("else{top.g_FolderPId=-1;top.window.opener.location.href=\"./__index.jsp?Idioma="
						+ idioma
						+ "&numIdioma="
						+ numIdioma
						+ "\";top.window.close();}");
	}

	public static void generateJavaScriptLogSessionExpiredSetPersistFields(
			Writer writer, String message, String idioma, Long numIdioma,
			int enabled) throws IOException {
		writer.write("<script language=\"javascript\">");
		if (enabled == 1) {
			writer
					.write("if (top.window.opener){top.window.opener.location.href=\"./__index.jsp?Idioma="
							+ idioma
							+ "&numIdioma="
							+ numIdioma
							+ "\";top.window.close();}");
			writer.write("else {window.close();}");
		} else {
			writer.write("window.open(\"./__index.jsp?Idioma=" + idioma
					+ "&numIdioma=" + numIdioma
					+ "\" , \"_top\",\"location=no\",true);");
		}
		writer.write("</script>");

	}

	public static void generateJavaScriptLogSessionExpiredVldRes(Writer writer,
			String message, String idioma, Long numIdioma, int enabled)
			throws IOException {
		writer
				.write("<script language=\"javascript\">try{parent.VldTbl.document.getElementById('Close').disabled = false;}catch(e){}</script>");
		writer.write("<script language=\"javascript\">alert(\"" + message
				+ "\");");
		if (enabled == 1) {
			writer
					.write("try{parent.VldTbl.document.getElementById('Close').onclick();}catch(e){}");
			writer
			.write("if (top.window.opener){top.window.opener.location.href=\"./__index.jsp?Idioma="
							+ idioma
							+ "&numIdioma="
							+ numIdioma
							+ "\";top.window.close();}");
			writer.write("else {window.close();}");
		} else {
			writer.write("window.open(\"./__index.jsp?Idioma=" + idioma
					+ "&numIdioma=" + numIdioma
					+ "\" , \"_top\",\"location=no\",true);");
		}
		writer.write("</script>");

	}

	public static void generateJavaScriptLogSessionExpiredReportDoc(
			Writer writer, String message) throws IOException {
		writer.write("<script>alert( \"" + message + "\" );</script>");
	}

	public static void generateJavaScriptLogSessionExpiredProvCityDir(
			Writer writer, String message) throws IOException {
		generateJavaScriptLogAlertCloseWindow(writer, message);

	}

	/**
	 * Metodo que genera un alert con el mensaje de error y ademas cierra la ventana que se intenta abrir
	 *
	 * @param writer
	 * @param message
	 * @throws IOException
	 */
	private static void generateJavaScriptLogAlertCloseWindow(Writer writer,
			String message) throws IOException {
		writer.write("<script language=\"javascript\">alert(\"" + message
				+ "\");");
		writer.write("window.close();");
		writer.write("</script>");

	}

	/**
	 * Metodo que se ejecuta cuando la sesion del usuario a expirado al
	 * intentar acceder a la pantalla de Relaciones.
	 *
	 * @param writer
	 * @param idioma
	 * @param numIdioma
	 * @param message
	 * @param enabled
	 * @throws IOException
	 */
	public static void generateJavaScriptLogSessionExpiredRelation(Writer writer, String idioma, Long numIdioma, String message, int enabled) throws IOException{
		writer.write("<script language=\"javascript\">alert( \"" + message + "\" );");
		if (enabled == 1) {
			//comprobamos si existe el argumento que identifica a la ventana padre
			writer.write("if (window.dialogArguments[8].opener){window.dialogArguments[8].opener.location.href=\"./__index.jsp?Idioma="
					+ idioma
					+ "&numIdioma="
					+ numIdioma
					+ "\";window.top.close();}");
			writer.write("else {window.close();}");
		} else {
			writer.write("window.open(\"./__index.jsp?Idioma=" + idioma
					+ "&numIdioma=" + numIdioma
					+ "\" , \"_top\",\"location=no\",true);");
		}
		writer.write("</script>");
	}

	public static void generateJavaScriptLogInterSave(Writer writer,
			String message) throws IOException {
		writer.write("alert(\"" + message + "\");");
	}

	// Valores g_OpcAval y ShowForm no estan en la ASP
	public static void generateJavaScriptCallOpenFolder(Writer writer,
			String javascript) throws IOException {
		writer.write(" <script language=\"javascript\">");
		writer.write("window.open(top.g_URL + \"" + javascript
				+ "\",\"FolderFormTree\",\"location=no\",true);");
		writer.write("</script>");

	}

	public static void generateJavaScriptCallFrmNavigate(Writer writer,
			String javascript, int index) throws IOException {
		writer.write(" <script language=\"javascript\">");
		writer.write("window.open(top.g_URL + \"" + javascript
				+ "\",\"FolderFormTree\",\"location=no\",true);");
		writer.write("top.g_ActivateTree=true;");
		writer.write("top.g_OpcAval=true;");
		writer.write("top.g_FirstReg=" + index + ";");
		writer.write("top.Main.Folder.FolderBar.LoadCounter();");
		writer.write("top.Main.Folder.ToolBarFrm.ToolBarEnabled();");
		writer.write("</script>");

	}

	public static void generateJavaScriptCallFlushFdr(Writer writer,
			String javascript) throws IOException {
		writer.write(" <script language=\"javascript\">");
		writer.write("top.Main.Folder.FolderBar.DeleteScanFiles();");
		writer.write("top.g_bIsSaved=true;");
		writer.write("window.open(top.g_URL + \"" + javascript
				+ "\",\"FolderFormTree\",\"location=no\",true);");
		writer.write("</script>");

	}

	public static void generateJavaScriptCallFirmaJsp(Writer writer,
			String javascript) throws IOException {
		writer.write(" <script language=\"javascript\">");
		writer.write("window.open(top.g_URL + \"" + javascript
				+ "\",\"FolderFormData\",\"location=no\",true);");
		writer.write("</script>");

	}

	public static void generateJavaScriptCompulsa(Writer writer, String message)
			throws IOException {
		writer.write(" <script language=\"javascript\">");
		writer.write("alert( \"" + message + "\" );");
		writer.write("top.Main.Folder.FolderData.FolderFormTree.ReLoad();");
		writer.write("</script>");

	}

	public static void generateJavaScriptCompulsaBackUploadFile(Writer writer,
			String path, String pathBlank, int cancel) throws IOException {
		writer.write(" <script language=\"javascript\">");
		if (cancel == 0) {
			writer
					.write("top.Main.Folder.FolderData.FolderFormData.document.getElementById('frmPage').src = \""
							+ path + "\";");
		} else {
			writer
					.write("top.Main.Folder.FolderData.document.getElementById('FolderFormData').src = \""
							+ pathBlank + "\";");
		}
		writer.write("</script>");

	}

	/***************************************************************************
	 * Protected methods
	 **************************************************************************/

	/***************************************************************************
	 * Private methods
	 **************************************************************************/

	/***************************************************************************
	 * Inner classes
	 **************************************************************************/

	/***************************************************************************
	 * Test brench
	 **************************************************************************/

}
