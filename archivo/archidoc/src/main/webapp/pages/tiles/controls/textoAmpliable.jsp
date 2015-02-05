<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>

<c:set var="rows" value="20"/>
<tiles:importAttribute name="property" ignore="true" />
<tiles:importAttribute name="urlImages" ignore="false"/>
<center>
<a class=etiquetaAzul12Bold href="javascript:ampliarTexto(this,'<c:out value="${property}"/>')" id="<c:out value="${property}"/>Bampliar">
	<html-el:img page="/pages/images/controles/ampliar.gif"
		border="0"
		altKey="archigest.archivo.campo.texto.ampliar"
		titleKey="archigest.archivo.campo.texto.ampliar"
		styleClass="imgTextBottom"
		styleId="${property}Iampliar"
	/>
</a>

<a class=etiquetaAzul12Bold href="javascript:reducirTexto(this,'<c:out value="${property}"/>')" id="<c:out value="${property}"/>Breducir">
	<html-el:img page="/pages/images/controles/reducir.gif"
		border="0"
		altKey="archigest.archivo.campo.texto.reducir"
		titleKey="archigest.archivo.campo.texto.reducir"
		styleClass="imgTextBottom"
		styleId="${property}Ireducir"
	/>
</a>

<a class=etiquetaAzul12Bold href="javascript:restaurarTexto(this,'<c:out value="${property}"/>')" id="<c:out value="${property}"/>Brestaurar">
	<html-el:img page="/pages/images/controles/restaurar.gif"
		border="0"
		altKey="archigest.archivo.campo.texto.restaurar"
		titleKey="archigest.archivo.campo.texto.restaurar"
		styleClass="imgTextBottom"
		styleId="${property}Irestaurar"
	/>
</a>&nbsp;
<input type="hidden" name="<c:out value="${property}"/>Url"  id="<c:out value="${property}"/>Url" value="<c:out value="${urlImages}"/>"/>
</center>
<script type="text/javascript"/>
	activarBotones('<c:out value="${property}"/>',true, false,false);
</script>
