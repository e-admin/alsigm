<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<html>
	<head>
		<title><bean:message key="pageError.head.title"/></title>
		<link rel='stylesheet' type='text/css' href='<ispac:rewrite href="css/styles.css"/>'>
	</head>
	<body marginwidth='0' marginheight='0'>

	<table cellpadding="0" cellspacing="1" border="0" class="boxGris" width="99%" style="margin-top:6px; margin-left:4px">
	<tr>
		<td width="100%">
			<table cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td class="title" height="18px" width="80%">
						<table cellpadding="0" cellspacing="0" border="0" width="100%">
							<tr>
								<td><img src='<ispac:rewrite href="img/pixel.gif"/>' height="18px"/>
								<td width="100%" class="menuhead">
									<bean:message key="pageError.title"/>
								</td>
							</tr>
						</table>
					</td>
					<td width="20%" style="text-align:right">
						<table border="0" cellspacing="0" cellpadding="0" width="100%">
							<tr>
								<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="6px"/></td>
							</tr>
							<tr>
								<td style="text-align:right">
									<input type="button" value='<bean:message key="pageError.back"/>' class="form_button_white" onclick="javascript:history.back();"/>
									<img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" width="10px"/>
								</td>
							</tr>
							<tr>
								<td colspan="2"><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="6px"/></td>
							</tr>
						</table>						
					</td>
					<td><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="8px"/></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td width="100%" class="blank">
			<table border="0" cellspacing="2" cellpadding="2" width="100%">
				<tr>
					<td width="100%">
							<table cellpadding="6" cellspacing="0" border="0" width="100%">
							   	<tr>
						 				<td class='titleBig' align='left'>
						 					<bean:message key="pageError.exception"/>
						 				</td>
									</tr>
									<tr>
										<td class="error">
											<%
												Exception e = (Exception) request.getAttribute(org.apache.struts.Globals.EXCEPTION_KEY);
												if (e != null)
												out.write(e.toString());
											%>
										</td>
									</tr>
									<tr>
						 				<td class='titleBig' align='left'>
						 					<bean:message key="pageError.trace.exception"/>
						 				</td>
									</tr>
									<tr>
										<td class="traza">
											<%
											if (e != null)	
												e.printStackTrace(new java.io.PrintWriter(out));
											%>
										</td>
									</tr>
									<tr>
						 				<td>
											<a href='javascript:history.back();' class='schema'><bean:message key="pageError.back"/></a>
										</td>
									</tr>
							</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	</table>

	</body>
</html>
