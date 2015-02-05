<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<!-- Para informar si se intenta salir del formulario sin guardar -->

<%-- Si no hay errores en el formulario
	 se comprobara si alguno de los campos ha sido modificado
	 y en caso afirmativo se mostrara el mensaje de confirmacion al salir --%>
<logic:notPresent name="org.apache.struts.action.ERROR">

<script language='JavaScript' type='text/javascript'><!--

	ispac_formobserver_install(this, '<bean:message key="forms.message.confirm.exit"/>');
	ispac_load_formvalues();
	
//--></script>

</logic:notPresent>

<%-- Si hay errores en el formulario
	 se mostrara siempre el mensaje de confirmacion al salir --%>
<logic:present name="org.apache.struts.action.ERROR">

<script language='JavaScript' type='text/javascript'><!--

	ispac_confirmexitobserver_install(this, '<bean:message key="forms.message.confirm.exit"/>');

//--></script>

</logic:present>
