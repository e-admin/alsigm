<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<html>
	<head>
		<title><bean:message key="pageError.head.title"/></title>
		<link rel='stylesheet' type='text/css' href='<ispac:rewrite href="css/styles.css"/>'>
	</head>
	<body marginwidth='0' marginheight='0'>
		<table cellpadding="5" cellspacing="0" border="0" width="90%" align="center">
			<tr>
				<td>
		   		<table cellpadding="0" cellspacing="1" border="0" width="100%" class="box">
						<tr>
							<td class="title" height="18px">
								<table cellpadding="0" cellspacing="0" border="0" width="100%">
									<tr>
										<td width="100%">
										<div id="appErrors">
											<bean:message key="pageError.title"/>
											<br>
										</div>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td class="blank">
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
							e.printStackTrace(new java.io.PrintWriter(out));
						%>
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
