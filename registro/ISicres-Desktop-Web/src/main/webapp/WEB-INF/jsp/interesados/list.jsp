<%@page pageEncoding="ISO-8859-1" %>

<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<spring:url value="tercero/fisico/retrieve.action" var="terceroFisicoEditUrl" />
<spring:url value="tercero/juridico/retrieve.action" var="terceroJuridicoEditUrl" />

<div id="resultSearchInteresados" style="width: 100%; overflow: auto; position: relative;">
	<display:table name="list" id="interesados" requestURI="interesado/crud.action?method=listInteresados" decorator="es.ieci.tecdoc.sicres.terceros.web.view.decorator.InteresadosTableDecorator" pagesize="10">
	  <!-- INTERESADO -->
	  <display:column>
	    <c:choose>
	      <c:when test="${interesados.tercero['class'].simpleName eq 'TerceroValidadoFisicoVO'}">
	        <input type="checkbox" name="tercero.id" value="${interesados.tercero.id}" class="interesado fisico"/>
	      </c:when>
	      <c:otherwise>
	        <input type="checkbox" name="tercero.id" value="${interesados.tercero.id}" class="interesado juridico"/>
	      </c:otherwise>
	    </c:choose>
	  </display:column>
	  <!-- nombre completo del interesado-->
	  <display:column property="interesado" titleKey="label.list.interesados.interesado.descripcion"/>
	  <!-- direccion notificacion del interesado -->
	  <display:column property="direccionNotificacionInteresado" titleKey="label.list.interesados.interesado.direccion" />

	  <!-- REPRESENTANTE -->
	  <display:column>
	    <spring:url value="representante/search.action" var="searchRepresentanteUrl">
	      <spring:param name="tercero.id" value="${interesados.tercero.id}"></spring:param>
	    </spring:url>
	    <a class="searchRepresentanteLink" href="${searchRepresentanteUrl}"></a>
	    <c:if test="${interesados.representante.representante != null}">
	      <c:choose>
	        <c:when test="${interesados.representante.representante['class'].simpleName eq 'TerceroValidadoJuridicoVO'}">
	          <input type="checkbox" name="tercero.id" value="${interesados.representante.representante.id}" class="representante juridico" interesado="${interesados.tercero.id}"/>
	        </c:when>
	        <c:otherwise>
	          <input type="checkbox" name="tercero.id" value="${interesados.representante.representante.id}" class="representante fisico" interesado="${interesados.tercero.id}"/>
	        </c:otherwise>
	      </c:choose>
	      <input type="hidden" name="representante[${interesados.representante.representante.id}]" value="${interesados.tercero.id}" />
	    </c:if>
	  </display:column>
	  <!-- nombre completo del representante -->
	  <display:column property="representante" titleKey="label.list.interesados.representante.descripcion" />
	  <!-- direccion notificacion del representante -->
	  <display:column property="direccionNotificacionRepresentante" titleKey="label.list.interesados.representante.direccion"  />
	  <display:setProperty name="paging.banner.item_name">
	    <spring:message code="label.list.interesados.search.item" />
	  </display:setProperty>
	  <display:setProperty name="paging.banner.items_name">
	    <spring:message code="label.list.interesados.search.items" />
	  </display:setProperty>
	</display:table>
</div>
<script type="text/javascript">
<!--
  jQuery(document).ready(function($){

    // La busqueda de representantes se hace por Ajax y se muestra con en plugin fancybox
    $('.searchRepresentanteLink').click(function(e){
      e.preventDefault();

      $.fancybox.showActivity();

      $.ajax({
        type: 'GET',
        cache: false,
        url: $(this).attr('href'),
        dataType: 'html',
        success:function(data){
          $.fancybox({
            modal:true,
            content:data
            });
          }
      });
    });

    // La navegacion de resultados se realiza por Ajax para mantener el foco en la ventana abierta
    $('.pagelinks > a, .sortable > a').click(function(e){
      e.preventDefault();

      $.ajax({
               type: 'POST',
               cache: false,
               url: $(this).attr('href'),
               dataType: 'html',
               success:function(data){
                   $('#interesadosRegistro').html(data);
               }
          });
      });
  });
//-->
</script>
