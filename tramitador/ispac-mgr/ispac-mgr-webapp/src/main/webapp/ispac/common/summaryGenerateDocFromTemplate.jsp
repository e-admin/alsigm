<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<html>
  <head>
    <link rel="stylesheet" href='<ispac:rewrite href="css/styles.css"/>'/>
    <link rel="stylesheet" href='<ispac:rewrite href="css/menus.css"/>'/>
    <link rel="stylesheet" href='<ispac:rewrite href="css/nicetabs.css"/>'/>
    <script type="text/javascript" src='<ispac:rewrite href="../scripts/menus.js"/>'></script>
    <script type="text/javascript" src='<ispac:rewrite href="../scripts/sorttable.js"/>'> </script>
    <script type="text/javascript" src='<ispac:rewrite href="../scripts/utilities.js"/>'> </script>
    <ispac:javascriptLanguage/>
  </head>
  <body>

	<table cellpadding="0" cellspacing="1" border="0" class="boxGris" width="99%" style="margin-top:6px; margin-left:4px">
	<tr>
		<td width="100%">
			<table cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td class="title" height="18px" width="80%">
						<table cellpadding="0" cellspacing="0" border="0" width="100%">
							<tr>
								<td><img src='<ispac:rewrite href="img/pixel.gif"/>' height="18px"/>
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
									<input type="button" value='<bean:message key="common.message.ok"/>' class="form_button_white" onclick='javascript:top.ispac_needToConfirm=false;<ispac:hideframe refresh="true"/>'/>
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
			  	  	  	<table cellpadding="1" cellspacing="1" border="0" align="left" width="100%" height="100%">
			  	  	  		<tr>
								<td><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="20px"/></td>
							</tr>
							<tr>
								<td class="loading" align="center" valign="middle">
									<html:errors/>
								</td>
							</tr>
							<logic:notPresent name="errors">
								<tr>
									<td class="loading" align="left" valign="middle">
					        			<c:set var="okList" value="${appConstants.actions.OK_UPLOAD_FILES_LIST}"/>
										<jsp:useBean id="okList" type="java.lang.String"/>
										<bean:message key="template.file.summary"/><br/><br/>
										<logic:notEmpty name='<%=okList%>'>
											<bean:message key="template.files.ok.text"/>:<br/>
										   	<ul>
												<logic:iterate name='<%=okList%>' id='object'>
													<li><bean:write name="object" filter="false"/></li>
												</logic:iterate>
											</ul>
										</logic:notEmpty>
					        			<c:set var="errorList" value="${appConstants.actions.ERROR_UPLOAD_FILES_LIST}"/>
										<jsp:useBean id="errorList" type="java.lang.String"/>
										<logic:notEmpty name='<%=errorList%>'>
											<bean:message key="template.files.error.text"/>:<br/>
											<ul>
												<logic:iterate name='<%=errorList%>' id='object'>
													<li><bean:write name="object" filter="false"/></li>
												</logic:iterate>
											</ul>
										</logic:notEmpty>
									</td>
								</tr>
							</logic:notPresent>
			  	  	  		<tr>
								<td><img src='<ispac:rewrite href="img/pixel.gif"/>' border="0" height="20px"/></td>
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

