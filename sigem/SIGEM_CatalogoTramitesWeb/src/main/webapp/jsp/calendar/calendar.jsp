<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<%@page import="java.util.ArrayList"%>
<%@page import="ieci.tecdoc.sgm.core.services.calendario.CalendarioDia"%>
<%@page import="ieci.tecdoc.sgm.catalogo_tramites.utils.Defs"%>
<%@page import="ieci.tecdoc.sgm.core.services.calendario.Calendario"%>
<html:html>

	<bean:define id="parentMenuNumber" value="3" type="java.lang.String"/>
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
		
		boolean existeCalendario = ((Boolean)request.getAttribute("existeCalendario")).booleanValue();
		String[] _horas = (String[])request.getSession().getServletContext().getAttribute(Defs.PLUGIN_HORAS);
		String[] _meses = (String[])request.getSession().getServletContext().getAttribute(Defs.PLUGIN_MESES);
		String[] _dias = (String[])request.getSession().getServletContext().getAttribute(Defs.PLUGIN_DIAS);
		String[] _minutos = (String[])request.getSession().getServletContext().getAttribute(Defs.PLUGIN_MINUTOS);
		
		ArrayList diasFijos = (ArrayList)request.getAttribute("diasFijos");
		ArrayList diasVariables = (ArrayList)request.getAttribute("diasVariables");
	%>

<script language="javascript">

	//------------------------------------------------------------------------------------------------------------------
	//------------------------------------------------------------------------------------------------------------------
	//------------------------------------------------------------------------------------------------------------------
	//------------------------------------------------------------------------------------------------------------------
	
	<bean:define id="_fecha" type="java.lang.String">
		<bean:message key="calendar.fecha"/>
	</bean:define>
	<bean:define id="_descripcion" type="java.lang.String">
		<bean:message key="calendar.descripcion.js"/>
	</bean:define>
	
	
	var arrayDF = new Array(<%=(diasFijos != null) ? diasFijos.size() : 0%>);
	<%
	if (diasFijos != null) {
		for(int in1=0; in1<diasFijos.size(); in1++){
			CalendarioDia __dia = (CalendarioDia)diasFijos.get(in1);
	%>
		arrayDF[<%=in1%>] = new Array('<%=__dia.getId()%>',"<%=__dia.getDescripcion()%>",'<%=__dia.getFecha()%>');
	<%	}
	}%>
	
	var arrayDV = new Array(<%=(diasVariables != null) ? diasVariables.size() : 0%>);
	<%
	if (diasVariables != null) {
		for(int in2=0; in2<diasVariables.size(); in2++){
			CalendarioDia __dia = (CalendarioDia)diasVariables.get(in2);
	%>
		arrayDV[<%=in2%>] = new Array('<%=__dia.getId()%>',"<%=__dia.getDescripcion()%>",'<%=__dia.getFecha()%>');
	<%	}
	}%>
	
	
	function getUserAction(theAction){   
		var form = document.getElementById("calendarForm"); 
		if ((theAction == 'addDay')||(theAction == 'updateDay')){
			if(!validateDayForm()){
				return;
			}
		}else if ((theAction == 'addCalendar')||(theAction == 'updateCalendar')){
			if(!validateCalendarForm()){
				return;
			}
		}else if (theAction == 'deleteCalendar') {
			if (confirm("<bean:message key='calendar.mensaje.borrado' />"))
				form.borrarDias.value = 'true';
			else form.borrarDias.value = 'false';
		}
		form.userAction.value=theAction;
		form.submit();
	}
   
	function validateDayForm(){
		var formulario = document.getElementById("calendarForm");
    	if (formulario._fechaDia.value == "" || formulario._fechaMes.value == ""){
    		alert("<bean:message key='errors.required' arg0='<%=_fecha%>'/>");
           	return false;
        }
    	if (formulario.description.value == ""){
    		alert("<bean:message key='errors.required' arg0='<%=_descripcion%>'/>");
            return false;
		}
		formulario.day.value = formulario._fechaMes.value + "/" + formulario._fechaDia.value;
        return true;
    }
    
    function validateCalendarForm(){
		var formulario = document.getElementById("calendarForm");
		if (formulario._horaInicio.value != "" || formulario._horaFin.value != "" ||
			formulario._minutoInicio.value != "" || formulario._minutoFin.value != ""){
	    	if (formulario._horaInicio.value == "" || formulario._horaFin.value == "" ||
	    		formulario._minutoInicio.value == "" || formulario._minutoFin.value == ""){
	    		alert("<bean:message key='errors.required.relleno'/>");
	           	return false;
	        } else {
	        	if (formulario._horaInicio.value > formulario._horaFin.value) {
	        		alert("<bean:message key='errors.required.horaMenor'/>");
	        		return false;
	        	} else if (formulario._horaInicio.value == formulario._horaFin.value) {
	        		if (formulario._minutoInicio.value > formulario._minutoFin.value) {
	        			alert("<bean:message key='errors.required.horaMenor'/>");
	        			return false;
	        		} else if (formulario._minutoInicio.value == formulario._minutoFin.value) {
	        			alert("<bean:message key='errors.required.horaIgual'/>");
	        			return false;
	        		}
	        	}
	        }
	    }
	    var numNoLaborables = 0;
	    var laborables = "";
	    var i=0;
	    for(i=0; i<7; i++){
	    	if (document.getElementById("_days[" + i + "]").checked == true) {
	    		// Dia no laborable
	    		laborables = laborables + "<%=Calendario.TIPO_NO_LABORABLE%>";
	    		numNoLaborables++;
	    	}
	    	else {
	    		// Dia laborable
	    		laborables = laborables + "<%=Calendario.TIPO_LABORABLE%>";
	    	}
	    	if (i+1 < 7)
	    		laborables = laborables + ";";
	    }
	    
	    if (numNoLaborables == 7) {
			alert("<bean:message key='errors.alldays.noLaborables'/>");
			return false;
		}
	    
	    formulario.days.value = laborables;
	    formulario.horaInicio.value = formulario._horaInicio.value;
	    formulario.horaFin.value = formulario._horaFin.value;
	    formulario.minutoInicio.value = formulario._minutoInicio.value;
	    formulario.minutoFin.value = formulario._minutoFin.value;
	    
	    afterLoadCalendar(<%=existeCalendario%>, false);
	    
        return true;
    }
   
	function nuevoDia(){
		activarDiaCalendario(true);
		activarCalendario(false);
		
   		var form = document.getElementById("calendarForm");
   		form._fechaDia.selectedIndex = 0;
   		form._fechaMes.selectedIndex = 0;
   		form.fix.selectedIndex = 0;
   		form.description.value = "";
   		
   		document.getElementById("diasFijos").selectedIndex = -1;
   		document.getElementById("diasVariables").selectedIndex = -1;
   		
   		showButton("addCalendarDayButton");
   		hideButton("updateCalendarDayButton");
   		hideButton("updateCalendarButton");
   		hideButton("deleteCalendarDayButton");
   		hideButton("deleteCalendarButton");
   		if (<%=existeCalendario%> == true){
	   		showButton("editCalendarButton");
	   	} else {
	   		hideButton("editCalendarButton");
		   	showButton("newCalendarButton");
		}
	}
	
	function nuevoCalendario(){
		activarCalendario(true);
		activarDiaCalendario(false);
		
   		var form = document.getElementById("calendarForm");
   		document.getElementById("_days[0]").checked = false;
   		document.getElementById("_days[1]").checked = false;
   		document.getElementById("_days[2]").checked = false;
   		document.getElementById("_days[3]").checked = false;
   		document.getElementById("_days[4]").checked = false;
   		document.getElementById("_days[5]").checked = false;
   		document.getElementById("_days[6]").checked = false;
   		form._horaInicio.selectedIndex = 0;
   		form._horaFin.selectedIndex = 0;
   		form._minutoInicio.selectedIndex = 0;
   		form._minutoFin.selectedIndex = 0;
   		
   		document.getElementById("diasFijos").selectedIndex = -1;
   		document.getElementById("diasVariables").selectedIndex = -1;
   		
   		showButton("addCalendarButton");
   		hideButton("addCalendarDayButton");
   		hideButton("updateCalendarDayButton");
   		hideButton("updateCalendarButton");
   		hideButton("deleteCalendarDayButton");
   		hideButton("deleteCalendarButton");
	}
	
	function editarCalendario(){
		activarCalendario(true);
		activarDiaCalendario(false);
		
   		var form = document.getElementById("calendarForm");

   		document.getElementById("diasFijos").selectedIndex = -1;
   		document.getElementById("diasVariables").selectedIndex = -1;

   		hideButton("editCalendarButton");   		
   		hideButton("addCalendarDayButton");
   		hideButton("updateCalendarDayButton");
   		showButton("updateCalendarButton");
   		hideButton("deleteCalendarDayButton");
   		showButton("deleteCalendarButton");
	}
	
	function showButton(idButton){
   		var button = document.getElementById(idButton);
   		button.style.display = "";   	
   	}
   
   	function hideButton(idButton){
   		var button = document.getElementById(idButton);
   		button.style.display = "none";
   	}
   	
   	function afterLoadCalendarDay(){
	<% if(writeFlag == "1"){ %>                                             	                           
		showButton("updateCalendarDayButton");
		showButton("deleteCalendarDayButton");
		hideButton("addCalendarDayButton");
   		hideButton("updateCalendarButton");
   		hideButton("deleteCalendarButton");
	<% } %>                                             	                           	
 	}
 	
 	function afterLoadCalendar(existeCalendario, mostrarBorrado){
	<% if(writeFlag == "1"){ %> 
		if (existeCalendario)                                            	                           
			if (mostrarBorrado)
		   		showButton("updateCalendarButton");
		   	else
		   		showButton("editCalendarButton");
	   		
		hideButton("updateCalendarDayButton");
		hideButton("deleteCalendarDayButton");
		hideButton("addCalendarDayButton");
		
		if (mostrarBorrado)
	   		showButton("deleteCalendarButton");
	   	else hideButton("deleteCalendarButton");
		
	<% } %>
	    var form = document.getElementById("calendarForm");
   		selectFromCombo("_horaInicio", form.horaInicio.value);
  		selectFromCombo("_horaFin", form.horaFin.value);
   		selectFromCombo("_minutoInicio", form.minutoInicio.value);
   		selectFromCombo("_minutoFin", form.minutoFin.value);
 	}
	
	function selectOneCalendarDay(){
		var form = document.getElementById("calendarForm");
   		var index = searchDay(form.id.value);
		selectFromCombo("_fechaDia", form.fechaDia.value);
		selectFromCombo("_fechaMes", form.fechaMes.value);
	}
	
	function searchDay(idDay){
		var ctrl = document.getElementById("diasFijos");
		var i=0;
		for (i=0; i<ctrl.length; i++){
			if (idDay == ctrl.options[i].value) {
				ctrl.selectedIndex = i;
				return i;
			}
		}
		ctrl = document.getElementById("diasVariables");
		for (i=0; i<ctrl.length; i++){
			if (idDay == ctrl.options[i].value) {
				ctrl.selectedIndex = i;
				return i;
			}
		}
		return ctrl.selectedIndex;
	}
	
	function activarCalendario(valor){
		var form = document.getElementById("calendarForm");
		document.getElementById("divChecks").disabled = !valor;
		form._horaInicio.disabled = !valor;
		form._minutoInicio.disabled = !valor;
		form._horaFin.disabled = !valor;
		form._minutoFin.disabled = !valor;
	}
	
	function activarDiaCalendario(valor){
		var form = document.getElementById("calendarForm");
		form._fechaDia.disabled = !valor;
		form._fechaMes.disabled = !valor;
		form.description.disabled = !valor;
		form.fix.disabled = !valor;
	}
	
	function selectFromCombo(comboName, comboValue){
		var ctrl = document.getElementById(comboName);
		var i=0;
		for (i=0; i<ctrl.length; i++){
			if (comboValue == ctrl.options[i].value) {
				ctrl.selectedIndex = i;
				return;
			}
		}
	}
	
	function obtenerDia(tipo) {
		var ctrl = document.getElementById(tipo);
		var valor = ctrl.options[ctrl.selectedIndex].value;
		var i=0;
		var array = null;
		if (tipo == 'diasFijos'){
			for(i=0; i<arrayDF.length; i++)
				if (valor == arrayDF[i][0]) {
					array = arrayDF[i];
					break;
				}
		}else if (tipo == 'diasVariables'){
			for(i=0; i<arrayDV.length; i++)
				if (valor == arrayDV[i][0]) {
					array = arrayDV[i];
					break;
				}
		}
		if (array != null) {
			var form = document.getElementById("calendarForm");
			form.id.value = array[0];
			form.description.value = array[1];
			form.day.value = array[2];
			if (tipo == 'diasFijos') {
				document.getElementById('diasVariables').selectedIndex = -1;
				form.fix.value = '<%=CalendarioDia.TIPO_FIJO%>';
			} else if (tipo == 'diasVariables') {
				document.getElementById('diasFijos').selectedIndex = -1;
				form.fix.value = '<%=CalendarioDia.TIPO_VARIABLE%>';
			}
			selectFromCombo("_fechaDia", array[2].substring(3,5));
			selectFromCombo("_fechaMes", array[2].substring(0,2));
		}
		
		activarCalendario(false);
		activarDiaCalendario(true);
		
		hideButton("addCalendarDayButton");
   		showButton("updateCalendarDayButton");
   		hideButton("updateCalendarButton");
   		showButton("deleteCalendarDayButton");
   		hideButton("deleteCalendarButton");
   		if (<%=existeCalendario%> == true){
	   		showButton("editCalendarButton");
	   	} else {
	   		hideButton("editCalendarButton");
		   	showButton("newCalendarButton");
		}
	}
</script>
    
	<HEAD>
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


		<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
		<script type="text/javascript" language="javascript" src="js/navegador.js"></script>
		<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<TITLE></TITLE>
	</HEAD>

	<body>

		<div class="cuerpo">
      			<div class="cuerporight">
        			<div class="cuerpomid">

       				<h1><bean:message key="menu.configurador" /></h1>

				<div class="tabs_submenus">
					<ul>
						<li class="subOff">
							<h3><a href="procedure.do"><bean:message key="procedures.prococedures" /></a></h3>
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
						<li class="subOn">
							<h3><a><bean:message key="menu.calendario" /></a></h3>
						</li>
					</ul>
				</div>
				<div class="cuerpo_subs">

					<div class="cuerpo_sub visible clearfix">

					<html:form styleId="calendarForm" action="calendar" method="post">	
					<html:hidden property="day"/>
					<html:hidden property="id"/>
					<html:hidden property="fechaDia"/>
					<html:hidden property="fechaMes"/>
					<html:hidden property="days"/>
					<html:hidden property="horaInicio"/>
					<html:hidden property="horaFin"/>
					<html:hidden property="minutoInicio"/>
					<html:hidden property="minutoFin"/>
					<html:hidden property="borrarDias"/>

					<html:hidden property="userAction"/>


					<div class="seccion_large"> 
						<div class="cuerpo_sec fila_sub clearfix">
							<ul>
							<%if(writeFlag == "1"){
								if (!existeCalendario) {%>
								<li id="newCalendarButton" name="newCalendarButton">
								<a href="javascript:nuevoCalendario();" class="col_nuevo">
									<bean:message key="button.label.newCalendar"/>
								</a>
								</li>
							<%}%>
							<li id="addCalendarButton" name="addCalendarButton" style="display: none">
							<a href="javascript:getUserAction('addCalendar')" class="col_nuevo" >
								<bean:message key="button.label.addCalendar"/>
							</a>
							</li> 
							<li id="updateCalendarButton" name="updateCalendarButton" style="display: none">
							<a href="javascript:getUserAction('updateCalendar')" class="col_editar" >
								<bean:message key="button.label.updateCalendar"/>
							</a>
							</li> 
							<li id="editCalendarButton" name="editCalendarButton" style="display: none" >
							<a href="javascript:editarCalendario();" class="col_guardar">
								<bean:message key="button.label.editCalendar"/>
							</a>
							</li> 
							<li id="deleteCalendarButton" name="deleteCalendarButton" style="display: none">
							<a href="javascript:getUserAction('deleteCalendar')" class="col_eliminar" >
								<bean:message key="button.label.deleteCalendar"/>
							</a>
							</li> 
							<%}%>

							</ul>
						</div>
					</div> <!-- fin seccion -->

					<div class="clear">
					</div> <!-- fin clear -->

					<div class="seccion_large_bottom"> 
						<p class="fila">
							<label class="mdl tit"><bean:message key="calendar.datos.calendario"/></label>
						</p>
						<p class="fila">
							<label class="sml"><bean:message key="calendar.laborables"/></label>
							<div id="divChecks" class="sub">
								<% 
								String[] laborables = (String[])request.getAttribute("laborables"); 
								for(int i=0; i<laborables.length; i++) {
									String name = "calendar.dia." + i;
								%>
								<label><bean:message key="<%=name%>"/>&nbsp;</label>
								<input type="checkbox" class="checkbox" id="_days[<%=i%>]" name="_days[<%=i%>]" <%=laborables[i].equals("0") ? "" : "checked" %> />
								<% } %>
							</div>
						</p>
						<p class="fila">
							<label class="sml sub"><bean:message key="calendar.horaInicio"/></label>
							<select class="single" id="_horaInicio" name="_horaInicio"> 
								<option value=""></option>
								<% for(int i=0; i<_horas.length; i++) { %>
								 	<option value="<%=_horas[i]%>"><%=_horas[i]%></option>
								<% } %>
							</select>
							:&nbsp;&nbsp;
							<select class="single" id="_minutoInicio" name="_minutoInicio" >
								<option value=""></option>
								<% for(int i=0; i<_minutos.length; i++) { %>
									<option value="<%=_minutos[i]%>"><%=_minutos[i]%></option>
								<% } %>
							</select>
						</p>
						<p class="fila">
							<label class="sml sub"><bean:message key="calendar.horaFin"/></label>
							<select class="single" id="_horaFin" name="_horaFin" >
								<option value=""></option>
								<% for(int i=0; i<_horas.length; i++) { %>
									<option value="<%=_horas[i]%>"><%=_horas[i]%></option>
								<% } %>
							</select>
							:&nbsp;&nbsp;
							<select class="single" id="_minutoFin" name="_minutoFin">
								<option value=""></option>
								<% for(int i=0; i<_minutos.length; i++) { %>
									<option value="<%=_minutos[i]%>"><%=_minutos[i]%></option>
								<% } %>
							</select>
						</p>
					</div> <!-- fin seccion -->

					<div class="clear">
					</div> <!-- fin clear -->

					<div class="seccion_large"> 
						<div class="cuerpo_sec fila_sub clearfix">
						<ul>
							<%if(writeFlag == "1"){ %>
							<li id="newCalendarDayButton" name="newCalendarDayButton" >
							<a href="javascript:nuevoDia();" class="col_nuevo">
								<bean:message key="button.label.newCalendarDay"/>
							</a>
							</li> 
							<li id="addCalendarDayButton" name="addCalendarDayButton" style="display: none">
							<a href="javascript:getUserAction('addDay')" class="col_nuevo" >
								<bean:message key="button.label.addCalendarDay"/>
							</a>
							</li> 
							<li id="updateCalendarDayButton" name="updateCalendarDayButton" style="display: none">
							<a href="javascript:getUserAction('updateDay')" class="col_guardar" >
								<bean:message key="button.label.updateCalendarDay"/>
							</a>
							</li> 
							<li id="deleteCalendarDayButton" name="deleteCalendarDayButton" style="display: none">
							<a href="javascript:getUserAction('deleteDay')" class="col_eliminar" >
								<bean:message key="button.label.deleteCalendarDay"/>
							</a>
							</li> 
							<%}%>
						</ul>
						</div>
					</div>

					<div class="seccion_large_bottom"> 
						<p class="fila">
							<label class="mdl tit"><bean:message key="calendar.datos.festivo"/></label>
						</p>
						<p class="fila">
							<label class="sml sub" ><bean:message key="calendar.fecha"/></label>
							<select class="single" id="_fechaDia" name="_fechaDia" >
								<option value=""></option>
								<% for(int i=0; i<_dias.length; i++) { %>
								<option value="<%=_dias[i]%>"><%=_dias[i]%></option>
								<% } %>
							</select>
							/&nbsp;&nbsp;
							<select class="sml" id="_fechaMes" name="_fechaMes" >
								<option value=""></option>
								<% for(int i=0; i<_meses.length; i++) { %>
								<option value='<%=( ((i + 1) >= 10) ? "" : "0" ) + (i + 1)%>'><bean:message key="<%=_meses[i]%>"/></option>
								<% } %>
							</select>
						</p>
						<p class="fila">
							<label class="sml sub"><bean:message key="calendar.descripcion"/></label>
							<html:text property="description" styleClass="mdl" ></html:text>
						</p>
						<p class="fila">
							<label class="sml sub"><bean:message key="calendar.fijo"/></label>
							<html:select property="fix" styleClass="single" >
								<html:option value="0"><bean:message key="opciones.si"/></html:option>
								<html:option value="1"><bean:message key="opciones.no"/></html:option>
							</html:select>

						</p>

					</div> <!-- fin seccion -->

					<div class="seccion"> 
						<p class="fila">
							<label class="sml sub" ><bean:message key="calendar.diasFijos"/></label>
							<%
							if (diasFijos == null) diasFijos = new ArrayList();
							%>
							<div class="barraHz mdl">
							<select class="multiple minmdl" name="diasFijos" id="diasFijos" size="15" onchange="obtenerDia('diasFijos');">
							<%for(int index = 0; index < diasFijos.size(); index++) { %>
								<option value="<%=((CalendarioDia)diasFijos.get(index)).getId()%>"><%=((CalendarioDia)diasFijos.get(index)).getDescripcion()%></option>
							<% } %>
							</select>
							</div>
						</p>
					</div>

					<div class="seccion"> 
						<p class="fila">
							<label class="sml sub" ><bean:message key="calendar.diasVariables"/></label>
							<%
							if (diasVariables == null) diasVariables = new ArrayList();
							%>
							<div class="barraHz mdl">
							<select class="multiple minmdl" name="diasVariables" id="diasVariables" size="15" onchange="obtenerDia('diasVariables');">
							<%for(int index = 0; index < diasVariables.size(); index++) { %>
								<option value="<%=((CalendarioDia)diasVariables.get(index)).getId()%>"><%=((CalendarioDia)diasVariables.get(index)).getDescripcion()%></option>
							<% } %>
							</select>
							</div>
						</p>
					</div>



					</html:form>

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
		
		<script language="Javascript">
			var form = document.getElementById("calendarForm");
	  		var acc = form.userAction;
			if (acc != null && acc != undefined){
				if (acc.value == "") {
					afterLoadCalendar(<%=existeCalendario%>, false);
					activarCalendario(false);
					activarDiaCalendario(false);
				} else if (acc.value == "loadDay"){
					afterLoadCalendar(<%=existeCalendario%>, false);
					activarDiaCalendario(true);
					afterLoadCalendarDay();
					selectOneCalendarDay();
					activarCalendario(false);
				} else if (acc.value == "loadCalendar"){
					activarCalendario(true);
					afterLoadCalendar(<%=existeCalendario%>, true);
					activarDiaCalendario(false);
				}
			}
		</script>
	</body>
</html:html>
