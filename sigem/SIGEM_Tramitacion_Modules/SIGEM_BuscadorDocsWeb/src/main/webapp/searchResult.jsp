<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<html:html>
	<head>
		<%
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		%> 
		<base href="<%= basePath %>" />
		<link rel="stylesheet" href="css/estilos.css" type="text/css" />
		<script type="text/javascript" language="javascript" src="js/idioma.js"></script>
	</head>
	<body>
		<div id="cabecera">
    		<div id="logo">
    			<img src="<%=request.getContextPath()%>/resourceServlet/logos/logo.gif" alt="sigem" />
    		</div>
			<div class="salir">
				<img src="img/exit.gif" alt="<bean:message key="salir"/>" width="26" height="20" class="icono" />
				<span class="titular">
					<a href="/portal"><bean:message key="salir"/></a>
				</span>
			</div>
  		</div>
  		<div class="usuario">
    		<div class="usuarioleft">
      			<p><bean:message key="aplicacion"/></p>
			</div>
    		<div class="usuarioright">
    		</div>
  		</div>
  		<br />
    	<br />
		<div align="center">
			<div class="cuerpo" style="width:707px;text-align:left">
		      	<div class="cuerporight">
		        	<div class="cuerpomid">
		          		<h1><bean:message key="search.form.title"/></h1>
		          		<div class="submenu3"></div>
		          		<div class="cuadro">
		            		<br/>
		            		<html:errors/>
		            		<logic:equal name="invalid_user" value="true">
		            			<label style="position:relative; left:350px" class="error"><bean:message key="search.error"/></label>
							</logic:equal>
		            		<br/><br/>
		            		<html:form action="/searchResult" method="post">
		                		<label for="cotejo" class="gr" style="position:relative; left:0px"><bean:message key="search.form.codigoCotejo"/></label>
		                		<html:text property="cotejo1" maxlength="5"/>
		                		<html:text property="cotejo2" maxlength="5"/>
		                		<html:text property="cotejo3" maxlength="5"/>
		                		<html:text property="cotejo4" maxlength="5"/>
		                		<br/><br/>
		                		<input type="submit" class="ok" value='<bean:message key="search.form.button"/>' style="position:relative; left:535px;"/>
		              			<br/><br/>
		            		</html:form>
		            		<br/><br/>
		          		</div>
		        	</div>
		      	</div>
		    </div>
		    <div class="cuerpobt" style="width:707px;">
	      		<div class="cuerporightbt">
	        		<div class="cuerpomidbt">
	        		</div>
	      		</div>
	    	</div>
	    	<br/>
			<div class="cuerpo" style="width:707px;text-align:left">
		      	<div class="cuerporight">
		        	<div class="cuerpomid">
        				<h1><bean:message key="search.result.title"/></h1>
		          		<div class="submenu3"></div>
		          		<div class="cuadro">
		          			<logic:messagesNotPresent>
			          			<logic:notPresent name="searchBean">
			          				<bean:message key="search.error.notFound"/>
			          			</logic:notPresent>
			          			<logic:present name="searchBean">
				        			<div>
				        				<h2><bean:message key="search.result.codigoCotejo"/></h2>
				        				<bean:write name="searchBean" property="codCotejo"/>
				        			</div>
				        			<div>
				        				<h2><bean:message key="search.result.documentName"/></h2>
				        				<bean:write name="searchBean" property="nombre"/>
				        			</div>
				        			<div>
				        				<h2><bean:message key="search.result.expNum"/></h2>
				        				<bean:write name="searchBean" property="numExp"/>
				        			</div>
				        			<div>
				        				<h2><bean:message key="search.result.creationDate"/></h2>
				        				<bean:write name="searchBean" property="fechaDoc"/>
				        			</div>
				        			<div>
				        				<h2><bean:message key="search.result.regType"/></h2>
				        				<bean:write name="searchBean" property="tpReg"/>
				        			</div>
				        			<br/>
				        			<div>
				        				<a href='<%=request.getContextPath()%>/showDocument.do?id=<bean:write name="searchBean" property="id"/>'><bean:message key="search.result.viewDocument"/></a>
				        			</div>
			        			</logic:present>
			        		</logic:messagesNotPresent>
		        		</div>
		        	</div>
		        </div>
		    </div>
		    <div class="cuerpobt" style="width:707px;">
	      		<div class="cuerporightbt">
	        		<div class="cuerpomidbt">
	        		</div>
	      		</div>
	    	</div>
  		</div>
  		
  		<!-- script language="Javascript">
  			document.getElementById('cotejo1').focus();
  		</script-->
	</body>
</html:html>