<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>      

<%@ page import="transferencias.actions.TransferenciaControlAction" %>


<bean:define id="pathaction" name="<%=TransferenciaControlAction.PATH_ACTION_KEY%>" />
<bean:define id="tipotransferencia" name="<%=TransferenciaControlAction.TIPO_TRANSFERENCIA_KEY%>" />
<bean:define id="TIPOPREVISION" value="<%=TransferenciaControlAction.TIPO_PREVISION%>" />
<bean:define id="TIPORELACION" value="<%=TransferenciaControlAction.TIPO_RELACION%>" />


<div id="contenedor_ficha">

<div class="ficha">

<h1><span>
<div class="w100">
<TABLE class="w98" cellpadding=0 cellspacing=0>
    <TD class="etiquetaAzul12Bold" width="30%" height="25px">
		<bean:message key="archigest.archivo.transferencias.cederControl"/>
	</TD>
    <TD width="70%" align="right">
	<TABLE cellpadding=0 cellspacing=0>
	  <TR>
		<TD>
			<html:form action='<%=pathaction+"?method=transfcontrolpaso3"%>' styleId="atrasForm">
			<a class=etiquetaAzul12Bold href="javascript:document.getElementById('atrasForm').submit();">
				<html:img page="/pages/images/Previous.gif" altKey="archigest.archivo.atras" titleKey="archigest.archivo.atras" styleClass="imgTextBottom" />
			   	<bean:message key="wizard.atras"/>&nbsp;
			</a>
	   </TD>
			</html:form>
       <TD width="10">&nbsp;</TD>

       <TD>
			<a class=etiquetaAzul12Bold href="javascript:document.getElementById('cesionForm').submit();" >
				<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextBottom" />
			   	&nbsp;<bean:message key="archigest.archivo.aceptar"/>
			</a>
	   </TD>

       <TD width="10">&nbsp;</TD>

       <TD>
			<html:form action='<%=pathaction+"?method=cancelar"%>' styleId="cancelForm">
			<a class=etiquetaAzul12Bold href="javascript:document.getElementById('cancelForm').submit();" >
				<html:img page="/pages/images/Ok_No.gif" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextBottom" />
			   	&nbsp;<bean:message key="archigest.archivo.cancelar"/>
			</a>
	   </TD>
			</html:form>
     </TR>
	</TABLE>
	</TD>
  </TR>
</TABLE>

</div>
</span></h1>

<div class="cuerpo_drcha">
<div class="cuerpo_izda">


<DIV id="barra_errores">
		<archivo:errors />
</DIV>

<span class="separador1"></span>
<DIV class="bloque">


	<html:form action='<%=pathaction+"?method=transfcontrolpaso5"%>' styleId="cesionForm">

			<div class="titulo_left_bloque">
			<c:if test="${tipotransferencia==TIPOPREVISION}">
				<bean:message key="archigest.archivo.transferencias.cesionPrevs"/>:
			</c:if>
			<c:if test="${tipotransferencia==TIPORELACION}">
				<bean:message key="archigest.archivo.transferencias.cesionRels"/>:
			</c:if>
			</div>

			<display:table name='sessionScope.TransferenciaControlForm.idsprevseleccionadas' 
				style="width:99%;margin-left:auto;margin-right:auto"
				id="listaRegistros" 
				decorator="transferencias.decorators.ViewPrevisionDecorator"
				requestURI='<%=request.getContextPath()+"/action"+pathaction+"?method=transfcontrolpaso4"%>'
				export="false">

				<c:if test="${tipotransferencia==TIPOPREVISION}">
				<display:column titleKey="archigest.archivo.transferencias.prevision" sortable="true" headerClass="sortable" style="white-space: nowrap;">
					<c:out value="${listaRegistros.codigoTransferencia}"/>
				</display:column>
				</c:if>

				<c:if test="${tipotransferencia==TIPORELACION}">
				<display:column titleKey="archigest.archivo.transferencias.relacion" sortable="true" headerClass="sortable" style="white-space: nowrap;">
					<c:out value="${listaRegistros.codigoTransferencia}"/>
				</display:column>
				</c:if>


				<display:column titleKey="archigest.archivo.transferencias.gestor">
					<c:out value="${TransferenciaControlForm.apellidosusuarioseleccionadode}"/>,&nbsp; 
					<c:out value="${TransferenciaControlForm.nombreusuarioseleccionadode}"/>
				</display:column>
				<display:column titleKey="archigest.archivo.transferencias.previsiones.nuevoGestor">
					<c:out value="${TransferenciaControlForm.apellidosusuarioseleccionadoa}"/>,&nbsp; 
					<c:out value="${TransferenciaControlForm.nombreusuarioseleccionadoa}"/>
				</display:column>
			</display:table>  
			<div class="separador5">&nbsp;</div>

</DIV> <%--bloque --%>

</div> <%--cuerpo_izda --%>
</div> <%--cuerpo_drcha --%>

<h2><span>&nbsp;
</span></h2>

</div> <%--ficha --%>
</div> <%--contenedor_ficha --%>

</html:form>

