<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<%@ page import="ieci.tecdoc.sgm.core.services.catalogo.Tramite" %>
<%@ page import="ieci.tecdoc.sgm.core.services.catalogo.Tramites" %>
<%@ page import="ieci.tecdoc.sgm.core.services.catalogo.OrganoDestinatario" %>
<%@ page import="ieci.tecdoc.sgm.core.services.catalogo.OrganosDestinatarios" %>
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

<head>
	<%
			String path = request.getContextPath();
			String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	%>
	<base href="<%= basePath %>">

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

	<script language="Javascript">
    	var selectedIndex;

		<bean:define id="ide" type="java.lang.String">
			<bean:message key="procedures.label.id"/>
		</bean:define>
		<bean:define id="desc" type="java.lang.String">
			<bean:message key="procedures.label.alert.description"/>
		</bean:define>
		<bean:define id="topic" type="java.lang.String">
			<bean:message key="procedures.label.topic"/>
		</bean:define>
		<bean:define id="addr" type="java.lang.String">
			<bean:message key="procedures.label.addressee"/>
		</bean:define>
		<bean:define id="ofic" type="java.lang.String">
			<bean:message key="procedures.label.alert.oficina"/>
		</bean:define>

		function validateFieldsLengths(){
			return true;
		}

    	function validateForm(){
    		var formulario = document.getElementById("Frm_1");
    		if (formulario.id.value == ""){
    			alert('<bean:message key="errors.required" arg0="<%=ide%>"/>');
                return false;
			}
    		if (formulario.description.value == ""){
    			alert('<bean:message key="errors.required" arg0="<%=desc%>"/>');
				return false;
			}
    		if (formulario.topic.value == ""){
    			alert('<bean:message key="errors.required" arg0="<%=topic%>"/>');
				return false;
			}
    		if (formulario.addressee.value == ""){
    			alert('<bean:message key="errors.required" arg0="<%=addr%>"/>');
				return false;
			}
			if (formulario.oficina.value == ""){
    			alert('<bean:message key="errors.required" arg0="<%=ofic%>"/>');
				return false;
			}
			return validateFieldsLengths();
    	}

		function disableInput(inputobject){
			inputobject.disabled = true;
		}

		function enableInput(inputobject){
			inputobject.disabled = false;
		}

		function load() {
        	var formulario = document.getElementById("Frm_1");
           	formulario.id.focus();
        }

        function changeDoc(){
            var formulario = document.getElementById("Frm_1");
            var idx = formulario.documentsTramit.selectedIndex;
            formulario.code.value = parent.docs.docs[idx].code;
            formulario.mandatory.value = parent.docs.docs[idx].mandatory;
        }

        function showDocsButton(){
        	var formulario = document.getElementById("Frm_1");
        	document.getElementById("manageDocuments").style.visibility = "visible";
        }

        function hideDocsButton(){
        	var formulario = document.getElementById("Frm_1");
        	document.getElementById("manageDocuments").style.visibility = "hidden";
        }

        function showUpdateButton(){
        	var formulario = document.getElementById("Frm_1");
        	document.getElementById('updateProcedure').style.display = "";
        }

        function hideUpdateButton(){
        	document.getElementById('updateProcedure').style.display = "none";
        }

        function showAddButton(){
        	var formulario = document.getElementById("Frm_1");
        	document.getElementById('addProcedure').style.display = "";
        }

        function hideAddButton(){
        	var formulario = document.getElementById("Frm_1");
        	document.getElementById('addProcedure').style.display = "none";
        }
        function showDelButton(){
        	var formulario = document.getElementById("Frm_1");
        	document.getElementById('deleteProcedure').style.display = "";
        }

        function hideDelButton(){
        	var formulario = document.getElementById("Frm_1");
        	document.getElementById('deleteProcedure').style.display = "none";
        }

        function updateProc(){
            var formulario = document.getElementById("Frm_1");
            var ctrl = document.getElementById("procedures");
            var elem, exists, i, tag, id;
            var value = formulario.id.value;

	    	if(!validateForm()){
	    		return;
		    }

            exists = false;
            for (i = 0; i < ctrl.options.length; i++)
            {
               if (ctrl.options[i].value == value)
               {
                  exists = true;
                  break;
               }
            }

            if (!exists)
            {
                alert("<bean:message key="procedures.error1"/>");
            }
            else
            {
               for (i = 0; i < parent.docs.docs.length; i++)
               {
                  id = parent.docs.docs[i].id;

                  //tag  = '<input type=\"hidden\" name=\"docIds\" id=\"docIds\" value=\"" + id + "\" />';
                  //elem = document.createElement(tag);
                  //formulario.appendChild(elem);
                  elem = document.createElement('input');
                  elem.setAttribute("type", "hidden");
                  elem.setAttribute("name", "docIds");
                  elem.setAttribute("id", "docIds");
				  elem.setAttribute("value", id);
				  formulario.appendChild(elem);

                  //tag  = '<input type=\"hidden\" name=\"" + id + "_code\" id=\"" + id + "_code\" value=\"" + parent.docs.docs[i].code + "\" />';
                  //elem = document.createElement(tag);
                  //formulario.appendChild(elem);
				  elem = document.createElement('input');
                  elem.setAttribute("type", "hidden");
                  elem.setAttribute("name", id + "_" + i + "_code");
                  elem.setAttribute("id", id + "_" + i + "_code");
				  elem.setAttribute("value", parent.docs.docs[i].code);
				  formulario.appendChild(elem);

                  //tag  = '<input type=\"hidden\" name=\"" + id + "_mandatory\" id=\"" + id + "_mandatory\" value=\"" + parent.docs.docs[i].mandatory + "\" />';
                  //elem = document.createElement(tag);
                  //formulario.appendChild(elem);
                  elem = document.createElement('input');
                  elem.setAttribute("type", "hidden");
                  elem.setAttribute("name", id + "_" + i + "_mandatory");
                  elem.setAttribute("id", id + "_" + i + "_mandatory");
				  elem.setAttribute("value", parent.docs.docs[i].mandatory);
				  formulario.appendChild(elem);
               }
               formulario.action = '<html:rewrite page="/updateprocedure.do"/>';
               formulario.submit();
            }
        }

        function error() {

        	var formulario = document.getElementById("Frm_1");
           	ctrlDocs = document.getElementById('docIds');

			var index = 0;
			while (ctrlDocs) {
				tagId = ctrlDocs.value + "_" + index + "_code";
				ctrl = document.getElementById(tagId);
				formulario.removeChild(ctrl);

				tagId = ctrlDocs.value + "_" + index + "_mandatory";
				ctrl = document.getElementById(tagId);
				formulario.removeChild(ctrl);

				formulario.removeChild(ctrlDocs);
				ctrlDocs = document.getElementById('docIds');
				index++;
			}
        }

        function doneUpdate()
        {
            var formulario = document.getElementById("Frm_1");
            var ctrl = document.getElementById("procedures");
            var elem, ctrlDocs, tagId;

            //ctrl.removeChild(ctrl.options[ctrl.selectedIndex]);

            elem = document.createElement("OPTION");
            //elem.text     = formulario.description.value;
            //elem.value    = formulario.id.value;
            elem.setAttribute("text", formulario.description.value);
            elem.setAttribute("value", formulario.id.value);
            //ctrl.appendChild(elem);
            ctrl.options[ctrl.selectedIndex] = new Option(formulario.description.value, formulario.id.value);
            elem.selected = true;

           	ctrlDocs = document.getElementById('docIds');

			var index = 0;
			while (ctrlDocs) {
				tagId = ctrlDocs.value + "_" + index + "_code";
				ctrl = document.getElementById(tagId);
				formulario.removeChild(ctrl);

				tagId = ctrlDocs.value + "_" + index + "_mandatory";
				ctrl = document.getElementById(tagId);
				formulario.removeChild(ctrl);

				formulario.removeChild(ctrlDocs);
				ctrlDocs = document.getElementById('docIds');
				index++;
			}

	        sortlist(document.getElementById("id").value);
			disableInput(formulario.id);
            showUpdateButton();
            hideAddButton();
            showDocsButton();
        }

		function addProc(){
            var formulario = document.getElementById("Frm_1");
            var ctrl = document.getElementById("procedures");
            var elem, exists, i, tag, id;
            var value = document.getElementById("id").value;

	    	if(!validateForm()){
	    		return;
	    	}

            exists = false;
            for (i = 0; i < ctrl.options.length; i++)
            {
               if (ctrl.options[i].value == value)
               {
                  exists = true;
                  break;
               }
            }

            if (exists)
            {
                alert("<bean:message key="procedures.error2"/>");
            }
            else
            {
               for (i = 0; i < parent.docs.docs.length; i++)
               {
                  id = parent.docs.docs[i].id;

//                  tag  = '<input type=\"hidden\" name=\"docIds\" id=\"docIds\" value=\"" + id + "\" />';
                  elem = document.createElement("input");
                  elem.setAttribute("type","hidden");
                  elem.setAttribute("name","docIds");
                  elem.setAttribute("id","docIds");
                  elem.setAttribute("value",id);
                  formulario.appendChild(elem);

//                  tag  = '<input type=\"hidden\" name=\"" + id + "_code\" id=\"" + id + "_code\" value=\"" + parent.docs.docs[i].code + "\" />';
                  elem = document.createElement("input");
                  elem.setAttribute("type", "hidden");
                  elem.setAttribute("name", id + "_" + i + "_code");
                  elem.setAttribute("id", id + "_" + i + "_code");
                  elem.setAttribute("value", parent.docs.docs[i].code);
                  formulario.appendChild(elem);

//                  tag  = '<input type=\"hidden\" name=\"" + id + "_mandatory\" id=\"" + id + "_mandatory\" value=\"" + parent.docs.docs[i].mandatory + "\" />';
                  elem = document.createElement("input");
                  elem.setAttribute("type", "hidden");
                  elem.setAttribute("name", id + "_" + i + "_mandatory");
                  elem.setAttribute("id", id + "_" + i + "_mandatory");
                  elem.setAttribute("value", parent.docs.docs[i].mandatory);
                  formulario.appendChild(elem);
               }

               formulario.action = '<html:rewrite page="/newprocedure.do"/>';
               formulario.submit();
            }

        }

        function addDocTramit(id, description, code, mandatory){
            var ctrl = document.getElementById("documentsTramit");
			ctrl.options[ctrl.length] = new Option(description, id);
			//top.frames['docs'].addDocTramitPD(id, description, code, mandatory);
			try {
		        parent.docs.addDocTramitPD(id, description, code, mandatory);
		    } catch(err) {
			    parent.document.all.docs.addDocTramitPD(id, description, code, mandatory);
		    }
        }


        function addDoc(text, value){
            var ctrl = document.getElementById("documentsTramit");
            ctrl.options[ctrl.length] = new Option(text, value);
            ctrl.selectedIndex = ctrl.length - 1;
        }

        function deleteDoc(index){
           parent.manageProc();
           var ctrl = document.getElementById("documentsTramit");
           ctrl.removeChild(ctrl.options[index]);
           document.getElementById('code').value = '';
           document.getElementById('mandatory').value = '';
           parent.manageDocs();
        }

        function updateInfoDoc(code_value, mandatory_value){
           document.getElementById('code').value = code_value;
           document.getElementById('mandatory').value = mandatory_value;
        }

        function doneAdd(){
            var formulario = document.getElementById("Frm_1");
            var ctrl = document.getElementById("procedures");
            var elem, ctrlDocs, tagId;

            /*elem = document.createElement("OPTION");
            elem.text     = document.getElementById("description").value;
            elem.value    = document.getElementById("id").value;
            ctrl.add(elem);
            elem.selected = true;*/
            ctrl.options[ctrl.length] = new Option(document.getElementById("description").value, document.getElementById("id").value);

            // Se eliminan los input de documentos
	        ctrlDocs = document.getElementById('docIds');

           	var index = 0;
           	while (ctrlDocs) {
   	         	tagId = ctrlDocs.value + "_" + index + "_code";
           	 	ctrl = document.getElementById(tagId);
            		formulario.removeChild(ctrl);

             	tagId = ctrlDocs.value + "_" + index + "_mandatory";
				ctrl = document.getElementById(tagId);
				formulario.removeChild(ctrl);

				formulario.removeChild(ctrlDocs);
				ctrlDocs = document.getElementById('docIds');
				index++;
       	   }

           sortlist(document.getElementById("id").value);
           disableInput(document.getElementById("id"));
           hideAddButton();
           showUpdateButton();
           showDocsButton();
        }

        function sortlist(sel) {
			var lb = document.getElementById("procedures");
			arrTexts = new Array();
			for(i=0; i<lb.length; i++)  {
				arrTexts[i] = lb.options[i].text;
			}
			arrTexts.sort();
			arrValues = new Array();
			for(i=0; i<lb.length; i++)  {
				arrValues[i] = getValue(lb, arrTexts[i]);
			}
			for(i=0; i<lb.length; i++)  {
				lb.options[i].text = arrTexts[i];
				lb.options[i].value = arrValues[i];
				if (arrValues[i] == sel)
					lb.selectedIndex = i;
			}
		}

        function getValue(ctrl, texto){
			var i=0;
			for(i=0; i<ctrl.options.length; i++){
				if(texto == ctrl.options[i].text)
					return ctrl.options[i].value;
			}
		}

        function doneLoad(id, description, topic, addressee, limit, firma, oficina, idProcedimiento){
            var formulario = document.getElementById("Frm_1");
            var ctrl = document.getElementById("documentsTramit");
            var i;
            for (i = ctrl.options.length - 1; i >= 0 ; i--)
            {
                ctrl.removeChild(ctrl.options[i]);
            }

            formulario.id.value = id;
            formulario.description.value = description;
            formulario.topic.value = topic;
            formulario.addressee.value = addressee;
            if (limit == "1")
                formulario.limitDocs[1].selected = true;
            else
                formulario.limitDocs[0].selected = true;
			if (firma == "1")
                formulario.firma[1].selected = true;
            else
                formulario.firma[0].selected = true;
            formulario.oficina.value = oficina;
            formulario.idProcedimiento.value = idProcedimiento;

			parent.docs.cleanProcDocuments();
            formulario.code.value = "";
            formulario.mandatory.value = "";
		    <%
            if(writeFlag == "1"){
            %>
            	hideAddButton();
            	showDelButton();
            	showUpdateButton();
		 	<% } %>
            showDocsButton();
			<%
			if(writeFlag == "1"){
			%>
            	disableInput(formulario.id);
			<% } %>
        }

        function cleanProcDocuments() {
            var ctrl = document.getElementById("documentsTramit");
            var i;

            for (i = ctrl.options.length - 1; i >= 0 ; i--)
            {
                ctrl.removeChild(ctrl.options[i]);
            }
        }

        function loadProcedure() {
            var formulario = document.getElementById("Frm_1");
            var procedures = document.getElementById("procedures");
            var id = procedures.options[procedures.selectedIndex].value;
            <% if(writeFlag == "1"){ %>
	    		//showUpdateButton();
			    showDocsButton();
		 	<% } %>
            formulario.action = "<html:rewrite page="/loadprocedure.do"/>?id=" + id;
            formulario.submit();
        }

        function doneDelete() {
			var formulario = document.getElementById("Frm_1");
            var ctrl = document.getElementById("procedures");
            ctrl.removeChild(ctrl.options[selectedIndex]);

            formulario.id.value = "";
            formulario.description.value = "";
            formulario.topic.value = "";
            formulario.addressee.value = "";
            formulario.limitDocs.value = "0";
            formulario.firma.value = "1";
			formulario.oficina.value = "";
			formulario.idProcedimiento.value = "";

            cleanProcDocuments();
            parent.docs.cleanProcDocuments();

            enableInput(formulario.id);
            hideUpdateButton();
            hideDocsButton();
            hideAddButton();
        }

        function newProc() {
            var formulario = document.getElementById("Frm_1");
        	hideUpdateButton();
        	showDocsButton();
            formulario.id.value = "";
            formulario.description.value = "";
            formulario.topic.value = "";
            formulario.addressee.value = "";
            formulario.limitDocs.value = "0";
            formulario.firma.value = "1";
            formulario.oficina.value = "";
			formulario.idProcedimiento.value = "";

            var ctrl = document.getElementById("procedures");
            ctrl.selectedIndex = -1;

            cleanProcDocuments();

            hideUpdateButton();
            showAddButton();
            showDocsButton();
            enableInput(formulario.id);
            formulario.id.focus();

           window.open('<html:rewrite page="/procedureDocuments.do"/>', "docs");
        }

        function deleteProc() {
            var formulario = document.getElementById("Frm_1");
            var ctrl = document.getElementById("procedures");
            selectedIndex = ctrl.selectedIndex;

            if (selectedIndex == -1)
                return;

            var id = ctrl.options[selectedIndex].value;

            formulario.action = "<html:rewrite page="/delprocedure.do"/>?id=" + id;
            formulario.submit();
        }

	</script>
	<script type="text/javascript" language="javascript" src="js/navegador.js"></script>
</head>

	<body>
			<div class="cuerpo">
      			<div class="cuerporight">
        			<div class="cuerpomid">
        				<h1><bean:message key="menu.configurador" /></h1>

<!-- NUEVO -->
				<div class="tabs_submenus">
					<ul>
						<li class="subOn">
							<h3><a><bean:message key="procedures.prococedures" /></a></h3>
						</li>
						<li class="subOff">
							<h3><a href="documents.do"><bean:message key="menu.tiposDocs" /></a></h3>
						</li>
						<li class="subOff">
							<h3><a href="hooks.do"><bean:message key="menu.auth.conn" /></a></h3>
						</li>
						<li class="subOff">
							<h3><a href="authHook.do"><bean:message key="menu.conectores_tramites" /></a></h3>
						</li>
						<li class="subOff">
							<h3><a href="addresseeList.do"><bean:message key="menu.organos" /></a></h3>
						</li>
						<li class="subOff">
							<h3><a href="calendar.do"><bean:message key="menu.calendario" /></a></h3>
						</li>
					</ul>
				</div>
				<div class="cuerpo_subs" >

					<div class="cuerpo_sub visible clearfix" >

					<form name="Frm_1" id="Frm_1" action='<html:rewrite page="/newprocedure.do"/>' method="post" target="work">



<!-- NUEVO ESQUEMA-->

					<div class="seccion_large">
						<div class="cuerpo_sec fila_sub clearfix">
							<ul>
							<%if(writeFlag == "1"){%>
								<li id="newProcedure" name="newProcedure">
								 	<a href="javascript:newProc();" class="col_nuevo">
									<bean:message key="button.label.new"/>
									</a>
								</li>
				 			<%}%>

				 			<%if(writeFlag == "1"){%>
								<li id="updateProcedure" name="updateProcedure" style="display: none">
									<a href="javascript:updateProc();" class="col_guardar">
									<bean:message key="button.label.save"/>
									</a>
								</li>
								<li id="addProcedure" name="addProcedure" style="display: none">
									<a href="javascript:addProc();" class="col_nuevo">
									<bean:message key="button.label.add"/>
									</a>
								</li>
								<li id="deleteProcedure" name="deleteProcedure">
									<a href="javascript:deleteProc();" class="col_eliminar">
									<bean:message key="button.label.delete"/>
									</a>
								</li>
							<%}%>
							</ul>
						</div>
					</div> <!-- fin seccion -->

					<div class="clear">
					</div> <!-- fin clear -->

					<div class="seccion">
						<p class="fila">
							<label class="sml sub"><bean:message key="procedures.prococedures"/></label>
							<div class="barraHz mdl">
							<select class="multiple minmdl" id="procedures" name="procedures" size="6" onchange="loadProcedure()" >
							                <%
									ServicioCatalogoTramites oServicio = LocalizadorServicios.getServicioCatalogoTramites();

							                Tramites procedures = oServicio.getProcedures(SesionAdminHelper.obtenerEntidad(request));
							                Tramite procedure = null;
							                for (int i=0; i < procedures.count(); i++)
							                {
							                    procedure = procedures.get(i);
							                    out.write("<option value=\"" + procedure.getId() + "\">" + procedure.getDescription() + "</option>\n");
							                }
							                %>
							</select>
							</div>
						</p>

					</div> <!-- fin seccion -->

					<div class="seccion">
						<p class="fila_sub" >
							<label class="sml" ><bean:message key="procedures.label.id" /></label>
							<input class="xsml" type="text" name="id" id="id" value="" maxlength="32" />
						</p>
						<p class="fila_sub" >
							<label class="sml" ><bean:message key="procedures.label.topic"/></label>
							<input class="mdl" type="text" name="topic" id="topic" value="" maxlength="32" />
						</p>
						<p class="fila_sub" >
							<label class="sml" ><bean:message key="procedures.label.description"/></label>
							<!--input type="text" name="description" id="description" value="" maxlength="256" style="width:260px;" /-->
							<textarea rows="2" class="mdl" name="description" id="description" value="" maxlength="256" ></textarea>
						</p>
						<p class="fila" >
							<label class="sml sub" ><bean:message key="procedures.label.addressee"/></label>
							<div class="barraHz mdl">
							<select class="single minmdl" id="addressee" name="addressee" >
							<%
								OrganosDestinatarios addresseeObjects = oServicio.getAddressees(SesionAdminHelper.obtenerEntidad(request));
						            	OrganoDestinatario addressee;
						            	for (int j=0; j<addresseeObjects.count(); j++) {
						                	addressee = addresseeObjects.get(j);
						                	out.write("<option value=\"" + addressee.getId() + "\">" + addressee.getDescription() + "</option>\n");
						                }
							%>
							</select>
							</div>
						</p>
						<p class="fila_sub" >
							<label class="sml" ><bean:message key="procedures.label.documentlimit"/></label>
							<select class="single" name="limitDocs" id="limitDocs">
								<option value="0" selected="true"><bean:message key="opciones.no"/></option>
								<option value="1"><bean:message key="opciones.si"/></option>
							</select>
						</p>
						<p class="fila_sub" >
							<label class="sml" ><bean:message key="procedures.label.firmar"/></label>
							<select class="single" name="firma" id="firma" >
								<option value="0"><bean:message key="opciones.no"/></option>
								<option value="1" selected="true"><bean:message key="opciones.si"/></option>
							</select>
						</p>
						<p class="fila_sub" >
							<label class="sml" ><bean:message key="procedures.label.oficina"/></label>
							<input class="xsml" type="text" name="oficina" id="oficina" value="" maxlength="32"  /><br />
						</p>
						<p class="fila_sub" >
							<label class="sml" ><bean:message key="procedures.label.idProcedimiento"/></label>
							<input class="xsml" type="text" name="idProcedimiento" id="idProcedimiento" value="" maxlength="32" /><br />
						</p>
					        <!--Documentos del Trámite-->
						<p class="fila" >
						        <label class="sml sub" ><bean:message key="procedures.docs"/></label>
							<div class="barraHz mdl">
						        <select class="multiple minmdl" id="documentsTramit" name="documentsTramit" size="6" onchange="changeDoc()" >
						        </select>
						        </div>
						</p>
						<p class="fila_sub" >
						        <label class="sml" ><bean:message key="procedures.code"/></label>
						        <input class="sml" type="text" name="code" id="code" value="" readonly="true" maxlength="32" />
						</p>
						<p class="fila_sub" >
						            <label class="sml" ><bean:message key="procedures.docneeded"/></label>
						            <select class="single" type="text" name="mandatory" id="mandatory" disabled="true" >
								<option value="0"><bean:message key="opciones.no"/></option>
								<option value="1" selected="true"><bean:message key="opciones.si"/></option>
						            </select>
						</p>
						<p class="fila" >
							<input type="button" name="manageDocuments" id="manageDocuments" class="botonFondo" value='<bean:message key="procedures.docs"/>' onclick="parent.manageDocs()" style="visibility: hidden"/>
						</p>


					</div> <!-- fin seccion -->

					<div class="clear">
					</div> <!-- fin clear -->

<!-- FIN NUEVO ESQUEMA-->

					</form>


					</div> <!-- fin cuerpo_sub visible -->
				</div> <!-- fin cuerpo_subs -->

<!-- FIN NUEVO -->


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