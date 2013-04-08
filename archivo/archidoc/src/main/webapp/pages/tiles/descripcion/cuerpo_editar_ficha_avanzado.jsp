<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglibs/displaydepositotags.tld"
  prefix="archivo"%>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el"%>

<bean:struts id="actionMapping" mapping="/gestionFicha" />
<c:set var="formName" value="${actionMapping.name}" />
<c:set var="formBean" value="${sessionScope[actionMapping.name]}" />

<script language="javascript">
  function actualizar(){
    document.getElementById("method").value = "actualizarAvanzado";

	if (window.top.showWorkingDiv) {
		var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
		var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
		window.top.showWorkingDiv(title, message);
	}
    document.getElementById("formulario").submit();
  }
</script>


<div id="contenedor_ficha"><html:form action="/gestionFicha"
  styleId="formulario">
  <input type="hidden" id="method" name="method" />
  <html:hidden property="id" />

  <div class="ficha">

  <h1><span>
  <div class="w100">
  <table class="w98" cellpadding=0 cellspacing=0>
    <tr>
      <td class="etiquetaAzul12Bold" height="25px"><bean:message
        key="NavigationTitle_FICHAS_EDICION_AVANZADA" /></td>
      <td align="right">
      <table cellpadding=0 cellspacing=0>
        <tr>
          <c:if test="${not empty formBean.id}">

          <td><a class="etiquetaAzul12Bold"
            href="javascript:actualizar()"><input
            type="image" src="../images/Ok_Si.gif"
            alt="<bean:message key="archigest.archivo.aceptar"/>"
            title="<bean:message key="archigest.archivo.aceptar"/>"
            class="imgTextMiddle" />&nbsp;<bean:message
            key="archigest.archivo.aceptar" /></a></td>
           <td width="10">&nbsp;</td>
          </c:if>

          <td><a class="etiquetaAzul12Bold"
            href="javascript:call(document.forms['fichasForm'],'goBack')"><html:img
            page="/pages/images/Ok_No.gif" border="0"
            altKey="archigest.archivo.cancelar"
            titleKey="archigest.archivo.cancelar" styleClass="imgTextMiddle" />&nbsp;<bean:message
            key="archigest.archivo.cancelar" /></a></td>

        </tr>
      </table>
      </td>
    </tr>
  </table>
  </div>
  </span></h1>

  <div class="cuerpo_drcha">
  <div class="cuerpo_izda">

  <div id="barra_errores"><archivo:errors /></div>

  <div class="separador1">&nbsp;</div>



  <div class="bloque"><%--bloque datos tabla --%>
  <table class="formulario">
    <tr>
      <td class="tdTitulo" width="180"><bean:message
        key="archigest.archivo.cf.ficha" />:&nbsp;</td>
      <td class="tdDatos"><c:out value="${formBean.nombre}"/> &nbsp;</td>
    </tr>
  </table>
  </div>
  <%--bloque datos tabla --%>

  <div class="separador5">&nbsp;</div>


  <div class="cabecero_bloque"><%--cabecero valores de la tabla --%>
  <table class="w98m1" cellpadding="0" cellspacing="0">
    <tbody>
      <tr>
        <td class="etiquetaAzul12Bold"><bean:message key="archigest.fichas.mapeo.descripcion"/></td>
        <td align="right">
        </td>
      </tr>
    </tbody>
  </table>
  </div>
  <%--cabecero valores de la tabla --%>

  <div class="bloque"><%--bloque valores tabla --%>
  <div class="separador1">&nbsp;</div>


  <div class="w100">
  <table class="formulario" cellpadding="0" cellspacing="0">
    <tr>

      <td class="tdTitulo" width="150" style="vertical-align: top;"><bean:message
        key="archigest.archivo.descripcion.fichas.form.definicion" />:&nbsp;</td>
      <td class="tdDatos"><html:textarea property="definicionMapeo" rows="20" />&nbsp;</td>

    </tr>
  </table>
  </div>
  </div>
  </div>
  <%--cuerpo_izda --%></div>
  <%--cuerpo_drcha --%>

  <h2><span>&nbsp;</span></h2>
  </div>
  <%--ficha --%>
</html:form></div>
<%--contenedor_ficha --%>


