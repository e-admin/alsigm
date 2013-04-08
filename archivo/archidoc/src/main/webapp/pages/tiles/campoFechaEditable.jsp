<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/taglibs/string" prefix="str" %>

<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<tiles:importAttribute name="editable" ignore="true" />
<tiles:importAttribute name="nombreCampo"/>
<tiles:importAttribute name="suffix"/>
<tiles:importAttribute name="valorCampo"/>
<tiles:importAttribute name="nombreFuncionAceptar" />
<tiles:importAttribute name="tipo"/>

<c:set var="divTexto">
	divText<c:out value="${nombreCampo}"/>
</c:set>

<c:set var="divTextbox">
	divTextbox<c:out value="${nombreCampo}"/>
</c:set>

<c:set var="idCampo">
	textbox<c:out value="${nombreCampo}"/><c:out value="${suffix}"/>
</c:set>

<c:set var="parametrosFuncion">
	'<c:out value="${divTexto}"/>','<c:out value="${divTextbox}"/>','<c:out value="${suffix}"/>'
</c:set>
<c:set var="parametrosFuncionCancelar">
	<c:out value="${parametrosFuncion}" escapeXml='false'/>,'<c:out value="${valorCampo}" escapeXml="false"/>','<c:out value="${idCampo}" escapeXml="false"/>'
</c:set>

<c:set var="parametrosFuncionAceptar">
	<c:out value="${parametrosFuncion}" escapeXml='false'/>,'<c:out value="${tipo}"/>','<c:out value="${valorCampo}" escapeXml="false"/>','<c:out value="${idCampo}" escapeXml="false"/>','<c:out value="${nombreFuncionAceptar}" escapeXml="false"/>'
</c:set>

<div id="<c:out value='${divTexto}'/><c:out value="${suffix}"/>">
    <c:out value="${valorCampo}"/>
    <c:if test="${editable}">
         <a class="etiquetaAzul12Bold" href="javascript:editarCampo(<c:out value='${parametrosFuncion}' escapeXml='false'/>)">
              <html:img page="/pages/images/editDoc.gif" altKey="archigest.archivo.modificar" titleKey="archigest.archivo.modificar" styleClass="imgTextMiddle" />
         </a>
	</c:if>
</div>

<c:if test="${editable}">
   <div id="<c:out value='${divTextbox}'/><c:out value="${suffix}"/>" class="hidden">

	<input type="text" class="input" size="12" maxlength="10" value='<c:out value="${valorCampo}"/>' id='<c:out value="${idCampo}"/>'/>
	      <a class="etiquetaAzul12Bold" href="javascript:aceptarEdicionCampo(<c:out value='${parametrosFuncionAceptar}' escapeXml='false'/>)">
             <html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextMiddle" />
           </a>
           <a class="etiquetaAzul12Bold" href="javascript:cancelarEdicionCampo(<c:out value='${parametrosFuncionCancelar}' escapeXml='false' />)">
             <html:img page="/pages/images/Ok_No.gif" altKey="archigest.archivo.cancelar" titleKey="archigest.archivo.cancelar" styleClass="imgTextMiddle" />
           </a>
     </div>
</c:if>