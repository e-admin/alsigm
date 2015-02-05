<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="es" xml:lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<meta name="author" content="IECISA" />
<title>sigem</title>
<link href="css/estilos.css" rel="stylesheet" type="text/css" />
</head>
<body>
<script language="javascript">
function enviar(){
	return;
}

</script>
<div id="contenedora">
	<div id="cabecera">
   		<div id="logo">
   			<img src="img/minetur.jpg" alt="GOBIERNO DE ESPAÑA. MINISTERIO DE INDUSTRIA, ENERGÍA Y TURISMO " />
   			<img src="img/logo.gif" alt="sigem" />
   		</div>
 		</div>
 		<div class="usuario">
   		<div class="usuarioleft">
     		<p>Cat&aacute;logo de tr&aacute;mites</p>
		</div>
   		<div class="usuarioright">
     			<div style="padding-top: 8px; padding-right: 24px;">
	     		<a href="#"><img src="img/help.gif" style="border: 0px" alt="ayuda" width="16" height="16" /></a>
	     	</div>
   		</div>
 	</div>
  	<!-- Fin Cabecera -->
	<div><br/><br/></div>
  	<!-- Inicio Contenido -->
  	<table align="center">
  	<tr>
  	<td>
  	<div id="contenedora">
		<div class="cuerpo" style="width:830px">
   			<div class="cuerporight">
     			<div class="cuerpomid">
     				<h1>Acceso al configurador de tr&aacute;mites</h1>
     				<br/>
					<div class="cuadro" style="height: 120px;">
						<table>
							<html:form action="/login.do">                    
					           	<tr>
						           	<td width="10%">&nbsp;
					            	</td>
					            	<td width="10%">&nbsp;
					            	</td>
					            	<td width="20%" style="text-align:right;">
					            	<bean:message key="login.user"/>
					            	</td>
					            	<td width="20%" style="text-align:left;">
					            	<html:text property="user" maxlength="20" />
					            	</td>
					            	<td width="35%">&nbsp;
					            	</td>                    	
					           	</tr>
					           	<tr>
					           		<td width="10%">&nbsp;
					            	</td>
					            	<td width="10%">&nbsp;
					            	</td>
					            	<td width="20%" style="text-align:right;">
					            	<bean:message key="login.password"/>
					            	</td>
					            	<td width="20%" style="text-align:left;">
					            	<html:password property="pass"/>
					            	</td>
					            	<td width="35%">&nbsp;
					            	</td>                    	
					           	</tr>
					           	<tr>
						           	<td width="10%">&nbsp;
					            	</td>
					            	<td width="10%">&nbsp;
					            	</td>
					            	<td width="20%" style="text-align:right;">
					            	&nbsp;
					            	</td>
					            	<td width="20%" style="text-align:left;">
					            		<input type="button" name="entrar" id="entrar" class="ok" value='<bean:message key="login.enter"/>' onclick="document.forms[0].submit();" />
					            	</td>
					            	<td width="35%">&nbsp;
					            	</td>                    	
					           	</tr>
							</html:form>
						</table>							                    		
           			</div>
           		</div>
      		</div>
    	</div>
		<div class="cuerpobt" style="width:830px">
      		<div class="cuerporightbt">
        		<div class="cuerpomidbt"></div>
      		</div>
		</div>
		
  	</div>
  	</td>
  	</tr>
  	</table>
</div>
</body>
</html>
