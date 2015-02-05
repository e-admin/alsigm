<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<c:if test="${param.method == 'showOrganos'}">

<bean:struts id="mapping" mapping="/buscarPrestamos" />

<c:set var="organos" value="${sessionScope[appConstants.prestamos.LISTA_ORGANOS_KEY]}" />

<div id="buscadorOrganos" class="input90">

	<c:if test="${!empty organos}">
		<script>
			function selectOrgano(item) 
			{
				var form = document.forms['<c:out value="${mapping.name}" />'];
				form.organo.value = item.getAttribute("id");
				form.nombreOrgano.value = item.getAttribute("nombreOrgano");
			}
		</script>
		<div id="organos" style="width:100%;height:100px;overflow:auto;background-color:#efefef">
			<c:forEach var="organo" items="${organos}">
				<div class="etiquetaGris12Normal" 
					id='<c:out value="${organo.idOrg}" />' 
					nombreOrgano='<c:out value="${organo.nombre}" />' 
					onmouseOver="this.style.backgroundColor='#dedede'"
					onmouseOut="this.style.backgroundColor='#efefef'"
					onclick="selectOrgano(this)" 
					style='width:100%;padding:4px; cursor:hand; cursor:pointer; text-align:left;' >
					<c:if test="${!empty organo.codigo}">
						<c:out value="${organo.codigo}" />
						&nbsp;&nbsp;
					</c:if>
					<c:out value="${organo.nombre}" />
				</div>
			</c:forEach>
		</div>
	</c:if>
</div>

</c:if>