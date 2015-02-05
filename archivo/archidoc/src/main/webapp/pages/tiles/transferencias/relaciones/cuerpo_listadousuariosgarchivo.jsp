<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>

<%@ page import="transferencias.actions.RelacionTransfControlGArchivoAction" %>

<div id="contenedor_ficha">

<div class="ficha">

<h1><span>
<div class="w100">
<TABLE class="w98" cellpadding=0 cellspacing=0>
    <TD class="etiquetaAzul12Bold" width="30%" height="25px">
		<bean:message key="archigest.archivo.transferencias.cederControl"/>
   		<bean:message key="archigest.archivo.transferencias.gestorArchivo"/>&nbsp;
	</TD>
    <TD width="70%" align="right">
	<TABLE cellpadding=0 cellspacing=0>
	  <TR>
		<TD>
		<html:form action="/relacionTransfControlGArchivo?method=transfcontrolpaso1" styleId="atrasForm">
			<a class=etiquetaAzul12Bold href="javascript:document.getElementById('atrasForm').submit();">
				<html:img page="/pages/images/Previous.gif" border="0" altKey="archigest.archivo.atras" titleKey="archigest.archivo.atras" styleClass="imgTextBottom" />
			   	<bean:message key="wizard.atras"/>&nbsp;
			</a>
		</TD>
		</html:form>
		<TD width="10">&nbsp;</TD>


		<logic:notEmpty name="<%=RelacionTransfControlGArchivoAction.LISTA_USERSGESTORES_KEY%>">
	       <TD>
				<a class="etiquetaAzul12Bold" href="javascript:document.getElementById('usersForm').submit();" >
					<html:img page="/pages/images/Next.gif" border="0" altKey="archigest.archivo.siguiente" titleKey="archigest.archivo.siguiente" styleClass="imgTextBottom" />
					<bean:message key="wizard.siguiente"/>&nbsp;
				</a>
		   </TD>
	       <TD width="10">&nbsp;</TD>
		</logic:notEmpty>

       <TD>
			<html:form action="/relacionTransfControlGArchivo?method=cancelar" styleId="cancelForm">
			<a class=etiquetaAzul12Bold href="javascript:document.getElementById('cancelForm').submit();" >
				<html:img page="/pages/images/Ok_No.gif" border="0" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextBottom" />
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

		<html:form action="/relacionTransfControlGArchivo?method=transfcontrolpaso3" styleId="usersForm">

		<div class="titulo_left_bloque">
		<bean:message key="archigest.archivo.transferencias.userCederRelArch" />
		</div>


			<display:table name='<%="requestScope."+RelacionTransfControlGArchivoAction.LISTA_USERSGESTORES_KEY%>' 
				style="width:99%;margin-left:auto;margin-right:auto"
				id="listaRegistros" 
				export="false">
				<display:setProperty name="basic.msg.empty_list">
					<bean:message key="archigest.archivo.transferencias.noUserArchivo" />
				</display:setProperty>
				<display:column title="&nbsp;" style="width:1%">
					<c:if test="${RelacionesTransfControlForm.idusuarioseleccionadoa == listaRegistros.iduser}">
					<INPUT type=radio name="idusuarioseleccionadoa" value='<bean:write name="listaRegistros" property="iduser"/>' checked>
					</c:if>
					<c:if test="${RelacionesTransfControlForm.idusuarioseleccionadoa != listaRegistros.iduser}">
					<INPUT type=radio name="idusuarioseleccionadoa" value='<bean:write name="listaRegistros" property="iduser"/>' >
					</c:if>
				</display:column>
				<display:column titleKey="archigest.archivo.idUsuario" property="iduser"/>
				<display:column titleKey="archigest.archivo.nombre">
					<c:out value="${listaRegistros.apellidos}"/>, <c:out value="${listaRegistros.nombre}"/> 
				</display:column>
			</display:table>  

			<span class="separador5"></span>


</DIV> <%--bloque --%>

</div> <%--cuerpo_izda --%>
</div> <%--cuerpo_drcha --%>

<h2><span>&nbsp;
</span></h2>

</div> <%--ficha --%>
</div> <%--contenedor_ficha --%>

</html:form>

