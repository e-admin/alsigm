<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-bean-el.tld" prefix="bean-el" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<bean:struts id="actionMapping" mapping="/gestionNivelesCuadro" />
<c:set var="nivel" value="${sessionScope[appConstants.deposito.NIVEL_DEPOSITO_KEY]}"/>
<c:set var="nivelPadre" value="${sessionScope[appConstants.deposito.NIVEL_PADRE_DEPOSITO_KEY]}"/>
<c:set var="elementosDefecto" value="${sessionScope[appConstants.deposito.LISTA_ELEMENTOS_DEFECTO]}"/>


<script language="javascript">
function aceptar(){
	if (window.top.showWorkingDiv) {
		var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
		var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
		window.top.showWorkingDiv(title, message);
	}
	enviarFormulario("formulario", "guardar");
}

function cambioTipo(){
	var isAsignable =  xGetElementById("asignable").checked;
	if(isAsignable){
		xDisplay("trOrdenacion","");
	}
	else{
		xDisplay("trOrdenacion","none");
	}
}
</script>

<div id="contenedor_ficha">

<div class="ficha">

<h1><span>
<div class="w100">
<table class="w98" cellpadding=0 cellspacing=0>
  <tr>
    <td class="etiquetaAzul12Bold" height="25px">
    	<c:choose>
			<c:when test="${!empty nivel.id}">
				<bean:message key="NavigationTitle_DEPOSITO_NIVELES_EDICION"/>
			</c:when>
			<c:otherwise>
				<bean:message key="NavigationTitle_DEPOSITO_NIVELES_ALTA"/>
			</c:otherwise>
		</c:choose>
    </td>
    <td align="right">
		<table cellpadding=0 cellspacing=0>
		 <tr>
	      	  <td>
				<a class="etiquetaAzul12Bold" href="javascript:aceptar()" >
					<html:img page="/pages/images/Ok_Si.gif" altKey="archigest.archivo.aceptar" titleKey="archigest.archivo.aceptar" styleClass="imgTextMiddle" />
				&nbsp;<bean:message key="archigest.archivo.aceptar"/></a>
	      	  </td>
			  <td width="10">&nbsp;</td>
	      	  <td>
				<tiles:insert definition="button.closeButton" flush="false" />
		   </td>
		 </tr>
		</table>
	</td>
  </tr>
</table>
</div>
</span></h1>

<div class="cuerpo_drcha">
<div class="cuerpo_izda">

<DIV id="barra_errores">
	<archivo:errors/>
</DIV>

<div class="separador1">&nbsp;</div>

<DIV class="bloque"> <%--segundo bloque de datos --%>
	<html:form action="/gestionNivelesDeposito" styleId="formulario">
		<input type="hidden" name="method" id="method" value="guardar"/>
		<html:hidden  property="id"/>
		<html:hidden  property="idPadre"/>
		<table class="formulario" width="99%">
	   	<c:if test="${not empty nivelPadre}">
	   	<tr>
	        <td class="tdTitulo" width="200px">
	           <bean:message key="archigest.archivo.deposito.elementoPadre"/>:&nbsp;
	         </td>
	         <td class="tdDatos">
				<html-el:img page="/pages/images/arboles/${nivelPadre.imagen}" styleClass="imgTextMiddle" altKey="${nivelPadre.nombre}" titleKey="${nivelPadre.nombre}"/>
	         	<c:out value="${nivelPadre.nombre}"/>
	         </td>
	    </tr>
	    </c:if>
		<c:if test="${empty nivel.id}">
		<tr>
			<td class="tdTitulo" width="200px">
	           <bean:message key="archigest.archivo.depositos.niveles.icono"/>:&nbsp;
	        </td>
	        <td class="tdDatos">
				<html-el:radio property="icono" value=""  styleClass="radio"/><html-el:img page="/pages/images/arboles/icono_raiz.gif" styleClass="imgTextMiddle"
					altKey="archigest.archivo.depositos.niveles.icono" titleKey="archigest.archivo.depositos.niveles.icono"/>
	        	<c:forEach items="${elementosDefecto}" var="icono">
					<html-el:radio property="icono" value="${icono.key}"  styleClass="radio"/>
					<html-el:img page="/pages/images/arboles/${icono.value}" styleClass="imgTextMiddle"
								altKey="archigest.archivo.depositos.niveles.icono" titleKey="archigest.archivo.depositos.niveles.icono"/>

	        	</c:forEach>
	        </td>
		</tr>
		</c:if>
	   	<tr>
	        <td class="tdTitulo" width="200px">
	           <bean:message key="archigest.archivo.depositos.niveles.nombre"/>:&nbsp;
	         </td>
	         <td class="tdDatos">
	         	<html:text property="nombre" maxlength="64" styleClass="input60"/>
	         </td>
	    </tr>
	    <tr>
	       <td class="tdTitulo">
	          <bean:message key="archigest.archivo.depositos.niveles.nombre.abreviado"/>:&nbsp;
	       </td>
	       <td class="tdDatos">
	       		<html:text property="nombreAbreviado" maxlength="16" size="17"/>
		 	</td>
	     </tr>
        <tr>
          <td class="tdTitulo">
            <bean:message key="archigest.archivo.depositos.niveles.descripcion"/>:&nbsp;
          </td>
          <td class="tdDatos"><html:text property="descripcion" maxlength="254" styleClass="input99"/>
		  </td>
        </tr>
	        <tr>
	          <td class="tdTitulo">
	            <bean:message key="archigest.archivo.depositos.niveles.caracter.ordenacion"/>:&nbsp;
	          </td>
	          <td class="tdDatos">
	          	<c:choose>
	          		<c:when test="${nivel.elementoReferenciado}">
	          			<bean:write name="nivel" property="caracterorden"/>
	          			<html:hidden property="caracterOrden"/>
	          		</c:when>
	          		<c:otherwise>
	          			<html:text property="caracterOrden" maxlength="1" size="1"/>
	          		</c:otherwise>
	          	</c:choose>

			  </td>
	        </tr>

	    	<tr>
	          <td class="tdTitulo">
	            <bean:message key="archigest.archivo.depositos.niveles.asignable"/>:&nbsp;
	          </td>
	          <td class="tdDatos">
	    	<c:choose>
				<c:when test="${nivelPadre.tipoDeposito}">
					<bean:message key="archigest.archivo.no"/><html:hidden property="asignable"/>
				</c:when>
	    		<c:otherwise>
	    			<c:choose>
					<c:when test="${nivel.elementoReferenciado || nivel.hasSubniveles}">
						<c:choose>
			          		<c:when test="${nivel.tipoAsignable}">
								<bean:message key="archigest.archivo.si"/>
			          		</c:when>
			          		<c:otherwise>
			          			<bean:message key="archigest.archivo.no"/>
			          		</c:otherwise>
			          	</c:choose>
	          			<html:hidden property="asignable"/>
	          		</c:when>
	          		<c:otherwise>
						<html:checkbox property="asignable" styleClass="checkbox"  onclick="cambioTipo()" styleId="asignable"></html:checkbox>
	          		</c:otherwise>
					</c:choose>
	    		</c:otherwise>
	    	</c:choose>
	          </td>
	        </tr>

	    	<tr id="trOrdenacion"
	    	<c:if test="${!nivel.tipoAsignable || nivel.hasSubniveles}">
	    		style="display:none"
	    	</c:if>
	    	>
	          <td class="tdTitulo">
	            <bean:message key="archigest.archivo.depositos.niveles.tipo.ordenacion"/>:&nbsp;
	          </td>
	          <td class="tdDatos">
    		    <c:choose>
	          		<c:when test="${nivel.elementoReferenciado}">
	          				<c:choose>
	          				<c:when test="${nivel.tipoord == 1}">
								<bean:message key="archigest.archivo.depositos.niveles.tipo.ordenacion.profundidad"/>
							</c:when>
							<c:when test="${nivel.tipoord == 2}">
								<bean:message key="archigest.archivo.depositos.niveles.tipo.ordenacion.anchura"/>
							</c:when>
	          				</c:choose>
	          			<html:hidden property="tipoOrd"/>
	          		</c:when>
	          		<c:otherwise>
		    			<html:select property="tipoOrd">
							<html:option value="1">
								<bean:message key="archigest.archivo.depositos.niveles.tipo.ordenacion.profundidad"/>
							</html:option>
							<html:option value="2">
								<bean:message key="archigest.archivo.depositos.niveles.tipo.ordenacion.anchura"/>
							</html:option>
						</html:select>
	          		</c:otherwise>
	          	</c:choose>



		          </td>
		        </tr>
	    </table>
	</html:form>

</div> <%--bloque --%>


</div> <%--cuerpo_drcha --%>
</div> <%--cuerpo_izda --%>

<h2><span>&nbsp;
</span></h2>

</div> <%--ficha --%>
</div> <%--contenedor_ficha --%>

