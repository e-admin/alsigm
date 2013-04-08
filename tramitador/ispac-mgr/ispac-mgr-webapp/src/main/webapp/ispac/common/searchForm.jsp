<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<script type="text/javascript" src='<ispac:rewrite href="../../dwr/interface/SearchFormAPI.js"/>'></script>
<ispac:javascriptLanguage/>
<script>

	
		$(document).ready(function(){
			formSelect=<%=request.getAttribute("formSelect")%>;
			$("#formularios").val(formSelect );
			
		});
		
		function resetChild(childFieldHierarchical){
			
			if(DWRUtil.byId("select"+childFieldHierarchical)){
				
					DWRUtil.removeAllOptions("select"+childFieldHierarchical);
					//Se lanza evento x si el hijo a su vez ejerciera el rol de padre en otra jerarquica
					$("#select"+childFieldHierarchical).change();
			}
			else{
			
				DWRUtil.setValue("code"+childFieldHierarchical ,'');
				DWRUtil.setValue("id"+childFieldHierarchical ,'');
				DWRUtil.setValue("value"+childFieldHierarchical ,'');
				$("#code"+childFieldHierarchical).blur();
			}
		}
		function updateValuesToChild (valor, hierarchicalId ,fieldHierarchical ,nombreCodigo, nombreValor , nombre ) {
		
		
			if(DWRUtil.byId("parent"+nombre+"code")){
				if(DWRUtil.byId("select"+nombre) && valor){
					idCodeSel='';
					idCodeSel=$("#selectIds"+nombre+" option[value='"+valor+"']").text();
					DWRUtil.setValue("parent"+nombre+"id" , idCodeSel);
					
					}
			}
			
			if(DWRUtil.byId("select"+fieldHierarchical)){
		
				//Si el campo hijo de la entidad jerarquica está con un select cargamos los valores asociados
				SearchFormAPI.getChildValues (valor, hierarchicalId , nombreCodigo, nombreValor, function(data){
					DWRUtil.removeAllOptions("select"+fieldHierarchical);
					if(DWRUtil.byId("parent"+fieldHierarchical+"id")){
						DWRUtil.removeAllOptions("selectIds"+fieldHierarchical);
					}
					if(data){
							DWRUtil.addOptions("select"+fieldHierarchical, data, "key", "value");
							if(DWRUtil.byId("parent"+fieldHierarchical+"id")){
								DWRUtil.addOptions("selectIds"+fieldHierarchical, data, "key", "id");
								
							}
							$("#select"+fieldHierarchical).change();
			
					}
					
				
				});
			}
			//En caso contrario reseteamos el valor
			else{
				
				DWRUtil.setValue("code"+fieldHierarchical ,"" );
				DWRUtil.setValue("value"+fieldHierarchical ,"" );
				DWRUtil.setValue("id"+fieldHierarchical ,"" );
				if(DWRUtil.byId("parent"+fieldHierarchical+"id")){
								DWRUtil.setValue("parent"+fieldHierarchical+"id" , '');
				}
				$("#code"+fieldHierarchical).blur();
				
			}
			
		}
		function loadDescripcion(valor, tabla, clausula, nombre, nombreCodigo, nombreValor ,parentHierarchicalId ,parentFieldHierarchical ,childHierarchicalId ,childFieldHierarchical ){
		
			var parentValue='';
			var getValue=1;
			var mensaje='';
			var rolPadre=0;
			var valorAnt='';
			var descAnt='';
			var rolHijo=0;
		
			
			if(DWRUtil.byId("parent"+nombre+"code")){
					DWRUtil.setValue("parent"+nombre+"code" ,valor);
					DWRUtil.setValue("parent"+nombre+"id" ,'');
					valorAnt=DWRUtil.getValue("parent"+nombre+"codeAnt");
					DWRUtil.setValue("parent"+nombre+"codeAnt" ,valor);
					rolPadre=1;
					
			}
		
			if(valor){
				//Si es padre y no ha cambiado el valor no hacemos nada
				if(rolPadre && valorAnt==valor){
					getValue=0;
				}
				
				if(DWRUtil.byId("parent"+parentFieldHierarchical+"code")){
					
					parentValue=DWRUtil.getValue("parent"+parentFieldHierarchical+"code");
					
					rolHijo=1;
				}
				//En caso de que vengamos de retornar de un resultado de búsqueda o de un error
				if(!parentValue){
					parentValue=DWRUtil.getValue("code"+parentFieldHierarchical);
					if(!parentValue)
						parentValue=DWRUtil.getValue("select"+parentFieldHierarchical);
				}
			
				if(!parentValue && parentHierarchicalId && rolHijo){
					DWRUtil.setValue("parent"+nombre+"codeAnt" ,valorAnt);
					mensaje="<bean:message key='search.getHierarchicalValueByCode.parentEmpty'  />";
					jAlert(mensaje,'<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>');
					DWRUtil.setValue("value"+nombre , "" );
					DWRUtil.setValue("code"+nombre , "" );
					DWRUtil.setValue("id"+nombre , "" );
					getValue=0;
					if(rolPadre){
					resetChild(childFieldHierarchical)
					}
				
				}
			
			}
				
			else{
				
				getValue=0;
				DWRUtil.setValue("code"+nombre ,'');
				DWRUtil.setValue("value"+nombre ,'');
				DWRUtil.setValue("id"+nombre ,'');
				
				if(rolPadre){
					
					resetChild(childFieldHierarchical);
				}
					
			}
			
			
			if(getValue){
				DWRUtil.setValue("parent"+nombre+"codeAnt" ,valor);
				SearchFormAPI.getHierarchicalValueByCode(valor, tabla, clausula, nombreCodigo, nombreValor,parentValue,parentHierarchicalId,function(data){
					if(data){
						
						DWRUtil.setValue("value"+nombre , data["value"] );
						DWRUtil.setValue("code"+nombre , data["key"] );
						DWRUtil.setValue("id"+nombre ,data["id"]);
						
						//Si es padre y el  hijo un select hay que cargar los valores del hijo
						if(rolPadre){
								updateValuesToChild (data["key"], childHierarchicalId ,childFieldHierarchical ,nombreCodigo, nombreValor , nombre)
						}
						
			
					}
					else{
						mensaje="<bean:message key='searchForm.value.notfound' arg0='"+valor+"'  />";
						DWRUtil.setValue("parent"+nombre+"codeAnt" ,valorAnt);
						if(DWRUtil.byId("parent"+parentFieldHierarchical+"code")){
							var padre='';
							if(DWRUtil.byId("select"+parentFieldHierarchical)){
								padre=DWRUtil.getText("select"+parentFieldHierarchical);
							}
							else{
								padre=DWRUtil.getValue("value"+parentFieldHierarchical);
							}
							mensaje="<bean:message key='searchForm.value.hierarchical.notfound' arg0='"+valor+"' arg1='"+padre+"' />";
						}
					
						jAlert(mensaje,'<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>');
						DWRUtil.setValue("value"+nombre , "" );
						DWRUtil.setValue("code"+nombre , "" );
						DWRUtil.setValue("id"+nombre , "");
						if(rolPadre){
							resetChild(childFieldHierarchical)
						}
					
					}
				});
				}
			
			
		}

	
            // Establecer el gestor de errores
            dwr.engine.setErrorHandler(errorHandler);
            
            
		   function errorHandler(message, exception) {
            jAlert(message, '<bean:message key="common.alert"/>', '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>');
        	}
</script>

<ispac:message/>

<logic:present name="Form">

	<script language="javascript">
		function submitform ()
		{
			dest = '<%=request.getContextPath()%>' + "/showSearch.do";
			document.queryform.action = dest;
			document.queryform.submit();
		}
		
		function doSubmit(formSelect){
			dest ='<%=request.getContextPath()%>'+ "/showProcedureList.do?search=true&formSelect="+formSelect;
			
			document.queryform.action = dest;
			document.queryform.submit();
		}
		function recargar(){
		
			formSelect=<%=request.getAttribute("formSelect")%>;
			doSubmit(formSelect);
			
			
		}
		function changeForm(){
		
			formSelect='';
			$("#formularios option:selected").each(function () {
					formSelect=$(this).val();
				});
			doSubmit(formSelect);
			
		}
	 
	</script>
	
	<script language="JavaScript">
	//<!--
	var language = 'es';
	var enablePast = 0; 
	var fixedX = -1; 
	var fixedY = -1; 
	var startAt = 1; 
	var showWeekNumber = 0; 
	var showToday = 1;
	var context = '<%=request.getContextPath()%>';
	var imgDir = context+'/pages/images/calendario/';
	var dayName = '';
	//-->
	</script>
	<!-- Aqui viene la definicion del calendariorrrr -->
	<ispac:rewrite id="imgcalendar" href="img/calendar/"/>
	<ispac:rewrite id="jscalendar" href="../scripts/calendar.js"/>
	<ispac:rewrite id="buttoncalendar" href="img/calendar/calendarM.gif"/>
	
	<ispac:calendar-config imgDir='<%= imgcalendar %>' scriptFile='<%= jscalendar %>'/>
	
	<table border="0" width="100%">
		<tr>
			<td align="right">
				<%--
				<a href="javascript:;" 
					onclick="javascript:window.open('<%=request.getContextPath() + "/help/" + request.getLocale().getLanguage() + "/search.html"%>','help','status=no,scrollbars=no,location=no,toolbar=no,top=100,left=100,width=610,height=410');"
					class="help">
					<img src='<ispac:rewrite href="img/help.gif"/>' style="vertical-align:middle" border="0"/>
					<bean:message key="header.help"/>				
				</a>
				--%>
			
			<c:set var="idForm"><c:out value="${requestScope.formSelect}" /></c:set>
			<bean:define id="idForm" name="idForm" type="java.lang.String"/>
			 <ispac:onlinehelp tipoObj="1" idObj='<%= idForm %>'  image="img/help.gif" titleKey="header.help"/>
				<!-- <ispac:onlinehelp tipoObj="1" idObj="${requestScope.formSelect}"  image="img/help.gif" titleKey="header.help"/>-->
			</td>
		</tr>
	</table>
	
        <table cellpadding="2" cellspacing="0" class="box" width="98%">
    <html:form action="forms.do" method="post">
          <tr >
            <td align="right" valign="middle" width="45%" class="field">
              <bean:message key="forms.title"/>
            </td>
             <td class="field" align="left">
              <html:select styleId="formularios" property="urlForm" styleClass="input" onchange="changeForm();">
                <logic:iterate id="form" name="formList" type="ieci.tdw.ispac.ispaclib.bean.ItemBean">
	                <html:option value='<%= form.getProperty("ID").toString() %>'><%= (String)form.getProperty ("DESCRIPCION") %></html:option>
	            </logic:iterate>
              </html:select>
             </td>
          </tr>
    </html:form>


	<form name="queryform" method="post">
		
    	<bean:write name="Form" filter='false'/>
    	
			<input type="hidden" name="idxml" value='<%= request.getAttribute("formSelect")%>'/>
		
	</form>
        </table>


</logic:present>




