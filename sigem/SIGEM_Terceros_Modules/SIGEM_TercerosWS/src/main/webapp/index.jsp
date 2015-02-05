<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<html>
<head>
  <title><fmt:message key="head.title"/></title>
  <link rel="stylesheet" href="css/styles.css"/>
  <link rel="shortcut icon" href="img/favicon.ico" type="image/x-icon"/>
  <link rel="icon" href="img/favicon.ico" type="image/x-icon"/>
</head>
<body leftMargin="0" rightMargin="0" topMargin="0" marginheight="0" marginwidth="0" >
<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
	<tr>
	    <td width="100%">
			<div id="cabecera">
				<div id="logo">
					<img src="img/minetur.jpg" alt="GOBIERNO DE ESPAÑA. MINISTERIO DE INDUSTRIA, ENERGÍA Y TURISMO " />
					<img src="img/logo.gif" border="0" />
				</div>
				<div id="app">
				    <fmt:message key="main.title"/>
				</div>
			</div>
		</td>
	</tr>
	<tr>
		<td height="100%" width="100%" colspan="2" align="center" valign="top">
			<div style="width:721px; padding-top:60px;">
			    <div class="cuerpo">
			      <div class="cuerporight">
			        <div class="cuerpomid">
			          <div class="submenu3">
			          </div>
			          <div class="cuadro">
						<table border="0" cellpadding="0" cellspacing="0" width="707px" height="231px" class="table_index">
							<tr>
							  <td width="100%" height="100%">
							    <table cellpadding="0" cellspacing="0" border="0" align="center" valign="middle">
							      <tr>
							        <td style="width:50%">
							        </td>
							        <td>
						              <table border="0" cellpadding="0" cellspacing="2" width="100%">
						                <tr><td><img src="img/pixel.gif" height="8px"/></td></tr>
						                <tr>
						                  <td class="index_title">
						                    <fmt:message key="index.title"/><br/><br/>
						                  </td>
						                </tr>
						                <tr><td><img src="img/pixel.gif" height="20px"/></td></tr>
						                <tr>
						                  <td>
						                    <a href="<c:url value="/services/TercerosWebService?wsdl"/>">
						                    	<img src="img/wsdl.gif" border="0" style="vertical-align:middle;"/>
						                    	<fmt:message key="index.wsdl.link"/>
						                    </a>
						                  </td>
						                </tr>
						              </table>
							        </td>
							      </tr>
							    </table>
							  </td>
							</tr>
						</table>
			          </div>
			        </div>
			      </div>
			    </div>
			    <div class="cuerpobt">
			      <div class="cuerporightbt">
			        <div class="cuerpomidbt"></div>
			      </div>
			    </div>
			</div>
		</td>
	</tr>
</table>
</body>
</html>

