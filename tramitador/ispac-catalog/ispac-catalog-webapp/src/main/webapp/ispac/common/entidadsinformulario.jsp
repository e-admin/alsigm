<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-1.3.2.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-ui-1.7.2.custom.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery.alerts.js"/>'></script>

<c:set var="refresh">
   <c:out value="${requestScope['refresh']}" default="false"/>
</c:set>
<jsp:useBean id="refresh" type="java.lang.String"/>



<div id="move">
<div class="encabezado_ficha">
	<div class="titulo_ficha">
		<h4><bean:message key="message.entidadSinFormulario"/></h4>
		<div class="acciones_ficha">
			<a href="#" id="btnOk" class="btnOk"  onclick='<ispac:hideframe id="workframemsg" refresh='<%=refresh%>'/>'><bean:message key="forms.button.accept"/></a>
		</div>
	</div>
</div>


<TABLE width="100%" align="center">
<TR><TD>&nbsp;</TD></TR>
<TR><TD>&nbsp;</TD></TR>
<TR align="center"><TD align="center"><div id="appErrors"><bean:message key="message.entidadSinFormulario"/></div></TD></TR>
<TR><TD>&nbsp;</TD></TR>
<TR><TD>&nbsp;</TD></TR>
</TABLE>

</div>
<script>
	$(document).ready(function(){
		$("#move").draggable();
	});
</script>