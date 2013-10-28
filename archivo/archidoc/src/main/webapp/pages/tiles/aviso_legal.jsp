<%@ taglib uri="/WEB-INF/struts-bean.tld"  prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<%@ page import="common.OrganizationMessages" %>

<tiles:insert template="/pages/tiles/SimpleLayout.jsp">
  <tiles:put name="head" value="head.jsp" />
  <tiles:put name="cabecera" value="cabecera.jsp" />

  <tiles:put name="cuerpo" direct="true">
	<div id="cuerpo">
		<div id="captioncuerpo">
			<font class="tituloapartado">&nbsp;<bean:message key="aviso_legal.titulo_parrafo"/></font><p>
		</div>
		<div id="bodycuerpo">
			<table cellspacing="0" cellpadding="0"><tr><td height="30"></td></tr></table>
			<blockquote>
				<font class="textos">
					<p><bean:message key="aviso_legal.parrafo1" arg0="<%=OrganizationMessages.getLegalNoticeP1()%>"/></p>
					<p><bean:message key="aviso_legal.parrafo2" arg0="<%=OrganizationMessages.getLegalNoticeP2()%>"/></p>
					<p><bean:message key="aviso_legal.parrafo3" arg0="<%=OrganizationMessages.getLegalNoticeP3()%>"/></p>
					<p><bean:message key="aviso_legal.parrafo4" arg0="<%=OrganizationMessages.getLegalNoticeP4()%>"/></p>
					<p><bean:message key="aviso_legal.parrafo5" arg0="<%=OrganizationMessages.getLegalNoticeP5()%>"/></p>
					<p><bean:message key="aviso_legal.parrafo6" arg0="<%=OrganizationMessages.getLegalNoticeP6()%>"/></p>

					<CENTER><FONT face="arial" size="2">
						<bean:message key="aviso_legal.pie_pagina" arg0="<%=OrganizationMessages.getCopyright()%>"/>
					</FONT></CENTER>
			</font>
			</blockquote>
	    </div>
		<div id="piecuerpo">
			<fmt:message key="archigest.archivo.version"/>
			<!-- <fmt:message key="archigest.archivo.fecha.version"/> -->
		</div>
	</div>
  </tiles:put>
</tiles:insert>
