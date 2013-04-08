<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/Config">
		<html>
	<head>
   	<script TYPE="text/javascript" LANGUAGE="javascript" SRC="scripts/genmsg.js"></script>
	<script language="javascript">
	function GetParameterIdsLan ( parametro )
	{
		switch(parametro)
		{
			case (parametro = "DataDir"):
				return top.GetIdsLan("IDS_CONFIG_PARAM_DATADIR");
			break;
			case (parametro = "Cache"):
				return top.GetIdsLan("IDS_CONFIG_PARAM_CACHE");
			break;
			case (parametro = "CacheSize"):
				return top.GetIdsLan("IDS_CONFIG_PARAM_CACHESIZE");
			break;
			case (parametro = "Name"):
				return top.GetIdsLan("IDS_CONFIG_PARAM_NAME");
			break;
			case (parametro = "User"):
				return top.GetIdsLan("IDS_CONFIG_PARAM_USER");
			break;
			case (parametro = "Password"):
				return top.GetIdsLan("IDS_CONFIG_PARAM_PASSWORD");
			break;
			case (parametro = "MaxRelCount"):
				return top.GetIdsLan("IDS_CONFIG_PARAM_MAXRELCOUNT");
			break;
			case (parametro = "DisplayUnit"):
				return top.GetIdsLan("IDS_CONFIG_PARAM_DISPLAYUNIT");
			break;
			case (parametro = "DisplayOffice"):
				return top.GetIdsLan("IDS_CONFIG_PARAM_DISPLAYOFFICE");
			break;
			case (parametro = "DisplaySubject"):
				return top.GetIdsLan("IDS_CONFIG_PARAM_DISPLAYSUBJECT");
			break;
			default:
				return "Param";
			break;
		}
		return;
	}
	
   function ValidateConfigForm()
   {
   	var aux = document.ConfigParams.CacheSize.value - 0;
   	
      if ((isNaN(aux)) || (0 >= aux))
      {
         alert( top.GetIdsLan( "IDS_ERROR_INVALID_DATA" ) );
         document.ConfigParams.CacheSize.focus();
      	return (false);
      }
      aux = document.ConfigParams.MaxRelCount.value - 0;
      if (isNaN(aux) || (0 >= aux))
      {
         alert( top.GetIdsLan( "IDS_ERROR_INVALID_DATA" ) );
         document.ConfigParams.MaxRelCount.focus();
         return(false);
      }
      aux = document.ConfigParams.DisplayUnit.value - 0;
      if (isNaN(aux) || (0 > aux) || (aux > 2))
      {
         alert( top.GetIdsLan( "IDS_ERROR_INVALID_DATA" ) );
         document.ConfigParams.DisplayUnit.focus();
         return(false);
      }
      aux = document.ConfigParams.DisplayOffice.value - 0;
      if (isNaN(aux) || (0 > aux) || (aux > 2))
      {
         alert( top.GetIdsLan( "IDS_ERROR_INVALID_DATA" ) );
         document.ConfigParams.DisplayOffice.focus();
         return(false);
      }
      aux = document.ConfigParams.DisplaySubject.value - 0;
      if (isNaN(aux) || (0 > aux) || (aux > 1))
      {
         alert( top.GetIdsLan( "IDS_ERROR_INVALID_DATA" ) );
         document.ConfigParams.DisplaySubject.focus();
         return(false);
      }

      return(true);
   }

	   document.title = top.GetIdsLan("IDS_TITULO_CONFIG");
	</script>
	   <title>Configurar-Configurar invesicres Corporate Server</title>
	   	<script language="javascript">														
			document.write('<link REL="stylesheet" TYPE="text/css" HREF="' + top.urlSkinCSS + '"/>');									
		</script>
      <LINK REL="stylesheet" TYPE="text/css" HREF="css/global.css" />
      <LINK REL="stylesheet" TYPE="text/css" HREF="css/table.css" />
	</head>
			<body>
			<form name="ConfigParams" METHOD="POST" ACTION="configupdate.asp" OnSubmit="return(ValidateConfigForm());">
				<xsl:for-each select="RegKeys">
					<br/>
					<xsl:for-each select="RegKey">
						<xsl:if test="position()=1">
							<table border="1" align="center">
								<thead>
									<tr>
										<td><B>
												<script language="javascript">
													document.write(top.GetIdsLan("IDS_CONFIG_PARAM_LABEL_PARAM"));
												</script>
											</B></td>
										<td><B>
												<script language="javascript">
													document.write(top.GetIdsLan("IDS_CONFIG_PARAM_LABEL_VALUE"));
												</script>
											</B></td>
									</tr>
								</thead>
								<tbody>
									<xsl:for-each select="../RegKey">
										<tr>
											<td>
												<xsl:for-each select="Name">
												<script language="javascript">
													document.write(GetParameterIdsLan ("<xsl:value-of select="." />"));
												</script>
												</xsl:for-each>
											</td>
											<td>
												<xsl:for-each select="Value">
													<input size="49" value="">
														<xsl:attribute name="value"><xsl:value-of select="." /></xsl:attribute>
														<xsl:attribute name="name"><xsl:value-of select="../Name" /></xsl:attribute>
														<xsl:variable name="paramName" select="../Name"/>
														<xsl:if test="$paramName='Password'">
															<xsl:attribute name="type">password</xsl:attribute>
														</xsl:if>
													</input>
												</xsl:for-each>
											</td>
										</tr>
									</xsl:for-each>
								</tbody>
							</table>
						</xsl:if>
					</xsl:for-each>
				</xsl:for-each>
	 	      <TABLE ID="oTableBut" valign="middle" align="center">
	 	         <TR valign="middle" align="center">
	 	            <TD align="middle">
	                  <INPUT class="button" type="submit" ID="btnOK" style="width:80;" value="Aceptar" />
	               </TD>
	            </TR>
	         </TABLE>
			</form>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>
