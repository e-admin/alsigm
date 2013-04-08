<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld" prefix="archivo"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<div id="busqDescriptores" style="display:none;z-index:100;position:absolute;width:700px;height:400px;top:0px;left:0px">

	<div class="block_title" style="text-align:right;">
		<a class="etiquetaAzul12Bold" href="javascript:hideAllDivs()">
			<html:img page="/pages/images/close.gif"
				styleClass="imgTextMiddle"
				titleKey="archigest.archivo.cerrar"
				altKey="archigest.archivo.cerrar"/>
			&nbsp;<bean:message key="archigest.archivo.cerrar"/>
		</a>
		<html:img page="/pages/images/pixel.gif" styleClass="imgTextMiddle" width="5px"/>
	</div>

	<div class="block_content" style="background: #FFFFFF;padding-right:10px">
		<iframe name="frmSeleccionDescriptor" id="frmSeleccionDescriptor"
			width="100%" height="370px" scrolling="auto"
			frameborder="0" style="overflow:visible;"
			src="<c:url value="javascript:''"/>"></iframe>
	</div>
</div>