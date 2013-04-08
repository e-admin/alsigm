<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>

<%@ page errorPage="/jsp/error_1.jsp" %>
<%@ page import="ieci.tecdoc.sgm.core.services.catalogo.Documento" %>
<%@ page import="ieci.tecdoc.sgm.core.services.catalogo.Documentos" %>
<%@page import="ieci.tecdoc.sgm.core.services.catalogo.ServicioCatalogoTramites"%>
<%@page import="ieci.tecdoc.sgm.core.services.LocalizadorServicios"%>
<%@page import="ieci.tecdoc.sgm.core.admin.web.SesionAdminHelper"%>

<html:html>
	<bean:define id="parentMenuNumber" value="1" type="java.lang.String"/>
	<%
	
		String readWriteToken = (String)session.getAttribute("readWriteToken");
		
		String writeFlag = "0";
		if(readWriteToken != null){
			if(readWriteToken.charAt(Integer.valueOf(parentMenuNumber).intValue()) == '1'){
				writeFlag = "1";
			}else{
				writeFlag = "0";
			}
		}else{
			writeFlag = "1";
		}
	%>
    <%
    
    int i, len;

    %>
	<head>
		<%
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		%>
		<base href="<%= basePath %>" />


		<link rel="stylesheet" href="css/estilos.css" type="text/css" />

		<!--[if lte IE 5]>
			<link rel="stylesheet" type="text/css" href="css/estilos_ie5.css"/>
		<![endif]-->	
	
		<!--[if IE 6]>
			<link rel="stylesheet" type="text/css" href="css/estilos_ie6.css"/>
		<![endif]-->	
	
		<!--[if IE 7]>
			<link rel="stylesheet" type="text/css" href="css/estilos_ie7.css"/>
		<![endif]-->	


	    <script language="javascript">
	
	        var selectedIndex = -1;
	        var docs = new Array();
	    
			function doc(id, code, mandatory)
	        {
	            this.id = id;
	            this.code = code;
	            this.mandatory = mandatory;
	        }
	    
	        function load()
	        {
	            Frm_1.id.focus();
	        }
	
	        function addDocTramitPD(id, description, code, mandatory)
	        {
	            var ctrl = document.getElementById("documentsTramit");
				ctrl.options[ctrl.length] = new Option(description, id);
	            docs[docs.length] = new doc(id, code, mandatory);
	
	        }
	        
	        function addDoc()
	        {
	            var ctrl = document.getElementById("documents");
	            var index = ctrl.selectedIndex;
	            var ctrl1 = document.getElementById("documentsTramit");
	            var elem, exists, value;
	
	            if (index == -1)
	                return;
	
	            value = ctrl.value;
	            exists = false;
	            //for (i = 0; i < ctrl1.options.length; i++)
	            //{
	            //    if (ctrl1.options[i].value == value)
	            //    {
	            //        exists = true;
	            //        break;
	            //    }
	            //}
	
	           // if (exists)
	           // {
	           //     alert('<bean:message key="procedures.docs.error1"/>');
	           // }
	           // else
	           // {
	           
	           //si se estaba editando un documento y se añade otro (guarda informacion)
	           	if (selectedIndex != -1 && selectedIndex < docs.length)
	            {
	                docs[selectedIndex].code = Frm_1.code.value;
	                docs[selectedIndex].mandatory = Frm_1.mandatory.value;
	            }
	           
	           
               	ctrl1.options[ctrl1.length] = new Option(ctrl.options[index].text, value);
                selectedIndex = ctrl1.length-1;
				ctrl1.selectedIndex = selectedIndex;

                docs[docs.length] = new doc(value, "", "1");
                parent.proc.addDoc(ctrl.options[index].text, value);
                docs[selectedIndex].code = "";
                docs[selectedIndex].mandatory = "";
                Frm_1.code.value = docs[selectedIndex].code;
            	Frm_1.mandatory.value = docs[selectedIndex].mandatory;

	          //  }
	        }
	        
	        function deleteDoc()
	        {
	            var ctrl = document.getElementById("documentsTramit");
	            var j;
	
	            if (selectedIndex == -1)
	                return;
	
	            delete docs[selectedIndex];
	            for (j = selectedIndex; j < docs.length -1; j++)
	            {
	                docs[j] = docs[j+1];
	            }
	            docs.length = docs.length - 1;
	
	
	            ctrl.removeChild(ctrl.options[selectedIndex]);
	
	            parent.proc.deleteDoc(selectedIndex);
	
	            selectedIndex = -1;
	        }
	        
	        function cleanProcDocuments()
			{
	            var ctrl = document.getElementById("documentsTramit");
	            var i;
	            for (i = ctrl.options.length - 1; i >= 0 ; i--)
	            {
	                ctrl.removeChild(ctrl.options[i]);
	            }
	           // for (i = 0; i < docs.length; i++)
	            //{
	                //delete docs[i];
	                docs.splice (0,docs.length);
	            //}
	           // docs.length = 0;
	        }
	        
	        function changeDoc()
	        {
	            if (selectedIndex != -1 && selectedIndex < docs.length)
	            {
	                docs[selectedIndex].code = Frm_1.code.value;
	                docs[selectedIndex].mandatory = Frm_1.mandatory.value;
	            }
	            selectedIndex = document.getElementById("documentsTramit").selectedIndex;

	            Frm_1.code.value = docs[selectedIndex].code;
	            Frm_1.mandatory.value = docs[selectedIndex].mandatory;
	        }
	        
	        function setDoc()
	        {
	            var i ;
	           
	            if (selectedIndex != -1)
	            {
	                docs[selectedIndex].code = Frm_1.code.value;
	                docs[selectedIndex].mandatory = Frm_1.mandatory.value;
	                parent.proc.updateInfoDoc(Frm_1.code.value, Frm_1.mandatory.value)
	            } 
	            
	            for (i = 0; i < docs.length; i++)
	            {
	               if (docs[i].code.length == 0)
	               {
	                  alert("<bean:message key='procedures.docs.error2'/>");
	                  return;
	               }
	               if(docs[i].mandatory.length == 0){
	               	  alert("<bean:message key='procedures.docs.error3'/>");
	               	  return;
	               }         
	            }
	            
	            parent.manageProc();
	        }
	        
	        function nuevo(codigo, descripcion) {
		        var ctrl = document.getElementById("documents");
	        	ctrl.options[ctrl.length] = new Option(descripcion, codigo);      
	        }
	        
	        function modificar(codigo, descripcion) {
	        	var ctrl = document.getElementById("documents");
	        	var i=0;
	        	for (i=0; i<ctrl.length; i++)
	        		if (ctrl.options[i].value == codigo) {
	        			ctrl.options[i].text = descripcion;
	        			break;
	        		}
	        }
	        
	        function eliminar(codigo) {
	        	var ctrl = document.getElementById("documents");
	        	var i=0;
	        	for (i=0; i<ctrl.length; i++)
	        		if (ctrl.options[i].value == codigo) {
	        			ctrl.removeChild(ctrl.options[i]);
	        			break;
	        		}
	        }
	        
	    </script>
    	<script type="text/javascript" language="javascript" src="js/navegador.js"></script>
	</head>
    <body>
    
    
	<div class="cuerpo">
      		<div class="cuerporight">
        		<div class="cuerpomid">
        			<h1><bean:message key="menu.configurador" /></h1>

				<div class="tabs_submenus">
					<ul>
						<li class="subOff">
							<h3><a href="javascript:setDoc()"><bean:message key="procedures.prococedures" /></a></h3>
						</li>
						<li class="subOn">
							<h3><a><bean:message key="menu.documentos_tramite" /></a></h3>
						</li>
					</ul>
				</div>
				<div class="cuerpo_subs">

					<div class="cuerpo_sub visible clearfix">

					        <form name="Frm_1" action="/jsp/procedure/new_procedure.jsp" method="post" target="work"> 

						<div class="seccion_large"> 
							<div class="cuerpo_sec fila_sub clearfix">
								<ul>
									<li>
										<a href="javascript:setDoc();" class="col_aceptar">
										<bean:message key="button.label.accept"/>
										</a>
									</li>
									 <%if(writeFlag == "1"){%> 
									<li id="deleteDocument" name="deleteDocument">
										<a href="javascript:deleteDoc();" class="col_eliminar">
										<bean:message key="button.label.delete"/>
										</a>
									</li>
									<%}%>	
								</ul>
							</div>
						</div>

						<div class="seccion_bottom"> 
							<p class="fila">
						                <label class="sml sub"><bean:message key="procedures.docs2"/></label>
								<div class="barraHz mdl">
						                <select class="multiple minmdl" id="documentsTramit" name="documentsTramit"  size="6" onchange="changeDoc()" >
						                </select>
						                </div>
							</p>
						</div> <!-- fin seccion -->
	
						<div class="seccion_bottom"> 
							<p class="fila_sub">
						                <label class="sml" ><bean:message key="procedures.code"/></label>
						                <input class="xsml" type="text" name="code" id="code" value=""/>
							</p>
							<p class="fila_sub">
						                <label class="sml" ><bean:message key="procedures.docneeded"/></label>
						                <select type="text" class="single" name="mandatory" id="mandatory">
									<option value="0"><bean:message key="opciones.no"/></option>
									<option value="1" selected="true"><bean:message key="opciones.si"/></option>
						                </select>
							</p>
							
						</div> <!-- fin seccion -->

						<div class="clear">
						</div> <!-- fin clear -->

						<div class="seccion_large_top"> 
							<div class="cuerpo_sec fila_sub clearfix">
								<ul>
								<%if(writeFlag == "1"){%>                                             	            
									<li id="addDocument" name="addDocument">
										<a href="javascript:addDoc();" class="col_nuevo">
										<bean:message key="button.label.add"/>
										</a>
									</li>
								 <%}%> 
								</ul>
							</div>
						</div>

						<div class="seccion_large"> 

							<p class="fila">
								<label class="sml sub"><bean:message key="procedures.tiposDocs"/></label>				                       
						                <select class="multiple xlrg" id="documents" name="documents" size="6">
						                    <%
						                    ServicioCatalogoTramites oServicio = LocalizadorServicios.getServicioCatalogoTramites();
						                    Documentos documents = oServicio.getDocuments(SesionAdminHelper.obtenerEntidad(request));
						                    Documento document;
						                    for (i=0; i < documents.count(); i++) {
						                        document = documents.get(i);
						                        out.write("<option value=\"" + document.getId() + "\">" + document.getDescription() + "</option>\n");
						                    }
						                    %>
						                </select>
							</p>
						</div> <!-- fin seccion -->

						<div class="clear">
						</div> <!-- fin clear -->



					</form>
					</div> <!-- fin cuerpo_sub visible -->
				</div> <!-- fin cuerpo_subs -->

			</div>
      		</div>
    	</div>
    	<div class="cuerpobt">
      		<div class="cuerporightbt">
        		<div class="cuerpomidbt">&nbsp;</div>
      		</div>
	</div>		

    </body>
</html:html>