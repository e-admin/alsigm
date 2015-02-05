<!--
JSP para obtener fecha y hora del dia.
-->

<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page contentType="text/html"%>
<%@ page isErrorPage="false" %>
<%@ page session="true"%>
<%@ page isThreadSafe="true" %>
<%@ page autoFlush="true" %>
<%@ page errorPage="__exception.jsp" %>


<!DOCTYPE HTML PUBLIC "-//w3c//dtd html 4.0 transitional//en">

<%@ page import="com.ieci.tecdoc.isicres.desktopweb.Keys" %>

<%@ page import="com.ieci.tecdoc.isicres.desktopweb.utils.ResponseUtils" %>
<%@ page import="com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil" %>
<%@ page import="com.ieci.tecdoc.isicres.usecase.UseCaseConf" %>
<%@ page import="com.ieci.tecdoc.isicres.usecase.book.BookUseCase" %>
<%@ page import="com.ieci.tecdoc.isicres.session.book.BookSession" %>
<%@ page import="com.ieci.tecdoc.common.isicres.SessionInformation" %>
<%@ page import="org.apache.log4j.*"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>


<%!Logger _logger = Logger.getLogger(getClass());%>

<%!BookUseCase bookUseCase = new BookUseCase();%>

<%
	response.setDateHeader("Expires", 0);
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");


    // Texto del idioma. Ej: EU_
	String idioma = (String) session.getAttribute(Keys.J_IDIOMA);
    // Número del idioma. Ej: 10
    Long numIdioma = (Long) session.getAttribute(Keys.J_NUM_IDIOMA);
    // Obtenemos el objeto de configuración del servidor de aplicaciones y el identificador
    // de sesión para este usuario en el servidor de aplicaciones.
    UseCaseConf useCaseConf = (UseCaseConf) session.getAttribute(Keys.J_USECASECONF);
    String dtTime = "";
    String caseSensitive = "";
    try{
	    Date fecha = bookUseCase.getDBDateServer(useCaseConf);
	 	SimpleDateFormat formatter = new SimpleDateFormat (RBUtil.getInstance(useCaseConf.getLocale()).getProperty("date.longFormat"));
		dtTime = formatter.format(fecha);
		dtTime = dtTime.substring(dtTime.indexOf(":")-2, dtTime.length());

		SessionInformation sessionInformation = BookSession.getSessionInformation(useCaseConf.getSessionID(),useCaseConf.getLocale(), useCaseConf.getEntidadId());
		caseSensitive = sessionInformation.getCaseSensitive();
	} catch (Exception e) {
		_logger.fatal("Error al obtener fecha y hora del dia",e);
		ResponseUtils.generateJavaScriptLog(out, RBUtil.getInstance(useCaseConf.getLocale()).getProperty(Keys.I18N_EXCEPTION_REMOTEEXCEPTION));
	}
%>

<HTML>
	<HEAD>
		<title>&nbsp;</title>
		<META http-equiv="Content-Type" content="text/html; charset=utf-8">
		<script language="javascript">														
			document.write('<link REL=\"stylesheet\" TYPE=\"text/css\" HREF="' + top.urlSkinCSS + '"/>');									
		</script>
		<LINK REL="stylesheet" TYPE="text/css" HREF="./css/table.css">
		<LINK REL="stylesheet" TYPE="text/css" HREF="./css/font.css">
		<LINK REL="stylesheet" TYPE="text/css" HREF="./css/global.css" />
		<SCRIPT TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/genmsg.js"></SCRIPT>
		<SCRIPT TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/global.js"></SCRIPT>
		<SCRIPT TYPE="text/javascript" LANGUAGE="javascript" SRC="./scripts/report.js"></SCRIPT>
		<script type="text/javascript" language="javascript" src="./scripts/calendar.js" charset="ISO-8859-1"></SCRIPT>
		<SCRIPT LANGUAGE="javascript">
			document.title = top.GetIdsLan( "IDS_OPCIMPRIMIR" );
			top.Idioma = top.GetDlgParam(7);
		</SCRIPT>

		<SCRIPT LANGUAGE="javascript">
			var caseSensitive = '<%= caseSensitive%>';

			function InitDate()
			{
				var fecha = new Date();
				var hora = '<%=dtTime%>';

				document.getElementById("date").value = FormatDate(fecha);
				document.getElementById("hora2").value= hora.substring(0, hora.lastIndexOf(":"));

				document.getElementById("date").focus();
			}

			function EnableHours()
			{
				if (document.getElementById("FilterHour").checked == false)
				{
					document.getElementById("hora1").disabled = 1;
					document.getElementById("hora2").disabled = 1;
				}
				else
				{
					document.getElementById("hora1").disabled = 0;
					document.getElementById("hora2").disabled = 0;
				}
			}
		</SCRIPT>
	</HEAD>
	<BODY onload="InitDate();" TABINDEX="-1">
		<TABLE WIDTH="99%" height="99%" CLASS="report">
			<THEAD>
				<TR>
					<TH CLASS="report" ALIGN="left">
						<SCRIPT LANGUAGE="javascript">
							document.write(top.GetIdsLan( "IDS_TITSETDATE"));
						</SCRIPT>
					</TH>
				</TR>
			</THEAD>
			<TR VALIGN="middle" height="25%">
				<TD CLASS="report" STYLE="padding:10; padding-bottom:30;">
					<SCRIPT LANGUAGE="javascript">
							document.write(top.GetIdsLan( "IDS_LABEL_DATE"));
					</SCRIPT>
					<INPUT CLASS="input" TYPE="text" VALUE="" NAME="date" ID="date" SIZE="10" MAXLENGTH="10" onblur="ValidateDate(this);"
						TABINDEX="1"> </INPUT>
					<img src="images/calendarM.gif" id="imgDate"  name="imgDate"
    					onclick='showCalendarEx(date, date, top.Idioma)'"/>
    										 
					<BR />
					<BR />
					<INPUT CLASS="check" TYPE="checkbox" ID="FilterHour" TABINDEX="1" onclick="EnableHours();"> </INPUT>
					<SCRIPT LANGUAGE="javascript">
							document.write(top.GetIdsLan( "IDS_LABEL_HOUR_FILTER"));
					</SCRIPT>
					<BR />
					<BR />
					<SCRIPT LANGUAGE="javascript">
							document.write(top.GetIdsLan( "IDS_LABEL_FROMHOUR"));
					</SCRIPT>
					<INPUT TYPE="text" CLASS="input" VALUE="00:00" NAME="hora1" SIZE="5" MAXLENGTH="5" onblur="ValidateHour(this);"
						TABINDEX="1" DISABLED="1" ID="hora1"> </INPUT>
					<SCRIPT LANGUAGE="javascript">
							document.write(top.GetIdsLan( "IDS_LABEL_UNTILHOUR"));
					</SCRIPT>
					<INPUT TYPE="text" CLASS="input" VALUE="" NAME="hora2" SIZE="5" MAXLENGTH="5" onblur="ValidateHour(this);"
						TABINDEX="1" DISABLED="1" id="hora2"> </INPUT>
				</TD>
			</TR>
			<TR>
				<TD CLASS="header1" ID="tdHeader2">
					<SCRIPT LANGUAGE="javascript">
						document.write(top.GetIdsLan( "IDS_TITSETUNITDEST"));
					</SCRIPT>
				</TD>
			</TR>
			<TR valign="top" rowspan="2">
				<TD CLASS="report" STYLE="padding:10; padding-top:20;">
					<LABEL CLASS="label" ID="oLbCode">
						<SCRIPT LANGUAGE="javascript">
							document.write(top.GetIdsLan("IDS_LABEL_CODE") + ":")
						</SCRIPT>
					</LABEL>
					<SCRIPT LANGUAGE="javascript">

						if (caseSensitive == 'CS'){
							document.write('<INPUT CLASS="input" ID="oCode" TYPE="text" NAME="oCode" TABINDEX="1" onblur="this.value=this.value.toUpperCase();ValidateCode(this);"	onhelp="ShowUnitList(caseSensitive);" onfocus="SetFocus(this);" MAXLENGTH="16" STYLE="position:absolute;left:73;width:205; text-transform:uppercase;"></INPUT>');
						} else {
							document.write('<INPUT CLASS="input" ID="oCode" TYPE="text" NAME="oCode" TABINDEX="1" onblur="ValidateCode(this);"	onhelp="ShowUnitList(caseSensitive);" onfocus="SetFocus(this);" MAXLENGTH="16" STYLE="position:absolute;left:73;width:205;"></INPUT>');
						}
						
						document.write('<IMG ID="imgHelp" SRC="images/buscar2.gif" class="imgHelpReports" onclick="ShowUnitList(caseSensitive);" onkeydown="if (top.GetKeyCode(event)==13){ShowUnitList(caseSensitive);}" TABINDEX="1"/>');						
					</SCRIPT>
					<BR/>
					<BR/>
					<BR/>
					<TEXTAREA CLASS="textarea" NAME="oDesc" ID="oDesc" STYLE="overflow-x:hidden;height:100;width:275"
						READONLY="true" TABINDEX="-1">
					</TEXTAREA>
				</TD>
			</TR>
		</TABLE>
	</BODY>
</HTML>
