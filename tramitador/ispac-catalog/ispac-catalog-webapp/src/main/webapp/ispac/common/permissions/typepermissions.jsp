<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>


<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-1.3.2.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-ui-1.7.2.custom.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery.alerts.js"/>'></script>

<script>


function hide(){

	<ispac:hideframe />
	<ispac:hideframe id="workframesearch" />
}
</script>
<ispac:layer/>
<div id="move">

			<div class="encabezado_ficha">
				<div class="titulo_ficha">
					<h4><bean:message key="usrmgr.permisos"/></h4>
					<div class="acciones_ficha">
						<a href="javascript:ispac_submit('<%= request.getContextPath() + "/storePermissions.do"%>')"  id="btnOk" class="btnOk"><bean:message key="forms.button.accept"/></a>				
						<a href="javascript:hide();" id="btnCancel" class="btnCancel"><bean:message key="forms.button.cancel"/></a>
					</div>
				</div>
			</div>

			<div class="cuerpo_ficha">
				<div class="seccion_ficha">
				
					<div class="cabecera_seccion">
						<h4><bean:write name="uidName"/> / <bean:write name="procName"/></h4>
					</div>
				</div>
			</div>	




<div id="stdform">
	<html:form action="/selectTypePermissions.do">
		<html:hidden name="defaultForm" property="property(uid)" />
		<html:hidden name="defaultForm" property="property(procId)" />
		
		
		<div id="fondoAzul">
			<div class="labelPermisos">
				<html:multibox name="defaultForm" property="multibox" >
					<c:out value="0"/>
				</html:multibox>
				<bean:message key="permission.type.0"/>
			</div>
			<div class="labelPermisos">
				<html:multibox name="defaultForm" property="multibox" >
					<c:out value="1"/>
				</html:multibox>
				<bean:message key="permission.type.1"/>
			</div>
			<div class="labelPermisos">
				<html:multibox name="defaultForm" property="multibox" >
					<c:out value="2"/>
				</html:multibox>
				<bean:message key="permission.type.2"/>
			</div>
			<div class="labelPermisos">
				<html:multibox name="defaultForm" property="multibox" >
					<c:out value="3"/>
				</html:multibox>
				<bean:message key="permission.type.3"/>
			</div>
		</div>
							

	</html:form>	
</div>
</div>
<script>
	$(document).ready(function(){
		$("#move").draggable();
	});
</script>