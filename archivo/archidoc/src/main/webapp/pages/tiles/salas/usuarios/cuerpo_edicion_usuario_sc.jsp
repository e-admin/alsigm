<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/taglibs/securityTag.tld" prefix="security"%>

<bean:struts id="actionMapping" mapping="/gestionUsuarioSalasConsultaAction" />
<c:set var="formBean" value="${requestScope[actionMapping.name]}"/>
<c:set var="listaArchivosSeleccionar" value="${sessionScope[appConstants.salas.LISTA_ARCHIVOS_SELECCIONAR_KEY]}"/>
<c:set var="esEditableUsuario" value="${sessionScope[appConstants.salas.PERMITIR_MODIFICAR_USUARIO_KEY]}" />
<tiles:insert template="/pages/tiles/PABoxLayout.jsp">
  <tiles:put name="boxTitle" direct="true">
    <c:choose>
    <c:when test="${!empty formBean.id}">
      <bean:message key="archigest.archivo.salas.usuario.editar"/>
    </c:when>
    <c:otherwise>
      <bean:message key="archigest.archivo.salas.usuario.crear"/>
    </c:otherwise>
    </c:choose>
  </tiles:put>
  <tiles:put name="buttonBar" direct="true">
    <table cellpadding="0" cellspacing="0">
      <tr>
        <security:permissions action="${appConstants.salasActions.MODIFICAR_USUARIO_ACTION}">
        <td nowrap>
          <script>
            function save()
            {
              if (window.top.showWorkingDiv) {
                var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
                var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
                window.top.showWorkingDiv(title, message);
              }

              var form = document.forms["<c:out value="${actionMapping.name}" />"];
              form.submit();
            }


            function cargarDatosUsuarioExterno(){
              if(window.confirm('<fmt:message key="archigest.archivo.salas.cargar.datos.usuario.externo.msg"/>')){
                var form = document.forms["<c:out value="${actionMapping.name}" />"];
                form.method.value = "cargarDatosUsuarioExterno";
                if (window.top.showWorkingDiv) {
                  var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
                  var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
                  window.top.showWorkingDiv(title, message);
                }


                form.submit();
              }
            }

            function addUsuarioExterno(){
                var form = document.forms["<c:out value="${actionMapping.name}" />"];
                form.method.value = "addUsuarioExterno";
                if (window.top.showWorkingDiv) {
                  var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
                  var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
                  window.top.showWorkingDiv(title, message);
                }
                form.submit();
            }

           function cargarUsuariosExternos(){
               var form = document.forms["<c:out value="${actionMapping.name}" />"];
               form.method.value = "cargarUsuariosExternos";
               if (window.top.showWorkingDiv) {
                 var title = '<bean:message key="archigest.archivo.msg.titulo"/>';
                 var message = '<bean:message key="archigest.archivo.msg.proceso.en.ejecucion"/>';
                 window.top.showWorkingDiv(title, message);
               }
               form.submit();
           }

          </script>
          <a class="etiquetaAzul12Bold"
            href="javascript:save()">
            <html:img page="/pages/images/Ok_Si.gif"
            titleKey="archigest.archivo.aceptar"
            altKey="archigest.archivo.aceptar"
            styleClass="imgTextMiddle"/>
            &nbsp;<bean:message key="archigest.archivo.aceptar"/>
          </a>
        </td>
        <td width="10">&nbsp;</td>
        </security:permissions>
        <td nowrap>
          <tiles:insert definition="button.closeButton" flush="true">
            <tiles:put name="labelKey" direct="true">archigest.archivo.cancelar</tiles:put>
            <tiles:put name="imgIcon" direct="true">/pages/images/Ok_No.gif</tiles:put>
          </tiles:insert>
        </td>
      </tr>
    </table>
  </tiles:put>
  <tiles:put name="boxContent" direct="true">
    <div class="bloque">
      <html:form action="/gestionUsuarioSalasConsultaAction">
        <html:hidden property="id" />
        <input type="hidden" name="method" value="guardar">


        <script language="javascript">
          function comprobarAcceso(){
            if(document.getElementById("accesoAplicacion").checked){
              setVisible("divAccesoAplicacion",true);
            }
            else{
              setVisible("divAccesoAplicacion",false);
            }
          }
        </script>

        <table class="formulario">
          <tr>
            <td width="200px" class="tdTitulo">
              <fmt:message key="archigest.archivo.salas.usuario.aplicacion"/>:&nbsp;

            </td>
            <td class="tdDatos">
              <c:choose>
              <c:when test="${esEditableUsuario}">
              <html:checkbox property="accesoAplicacion" styleId="accesoAplicacion" onclick="javascript:comprobarAcceso()" styleClass="checkbox"></html:checkbox>
              <c:choose>
                <c:when test="${formBean.accesoAplicacion == true}">
                  <c:set var="claseDiv" value="visible"/>
                </c:when>
                <c:otherwise>
                  <c:set var="claseDiv" value="hidden"/>
                </c:otherwise>
              </c:choose>

              <div id="divAccesoAplicacion"
                class="<c:out value="${claseDiv}" escapeXml="false" />"
              >
                <c:set var="usuariosExternos" value="${sessionScope[appConstants.salas.LISTA_USUARIOS_EXTERNOS_KEY]}" />
                <html:select property="idscausr">
                  <html:option value=""></html:option>
                  <html:options collection="usuariosExternos" property="key" labelProperty="value" />
                </html:select>
                &nbsp;
					<html:text styleId="filtroNombreUsuarioExterno" property="filtroNombreUsuarioExterno"  styleClass="input"/>
					<a class="etiquetaAzul12Bold" href="javascript:cargarUsuariosExternos();">
						<html:img page="/pages/images/searchDoc.gif" border="0" altKey="archigest.archivo.solicitudes.filtrar" titleKey="archigest.archivo.solicitudes.filtrar" styleClass="imgTextBottom" />
						&nbsp;<bean:message key="archigest.archivo.solicitudes.filtrar" />
					</a>

                &nbsp;

                    <a class="etiquetaAzul12Bold"
                      href="javascript:cargarDatosUsuarioExterno()">
                      <html:img page="/pages/images/go.gif"
                      titleKey="archigest.archivo.salas.cargar.datos.usuario.externo"
                      altKey="archigest.archivo.salas.cargar.datos.usuario.externo"
                      styleClass="imgTextMiddle"/>
                </a>



                  &nbsp;&nbsp;

                    <a class="etiquetaAzul12Bold"
                          href="javascript:addUsuarioExterno()">
                          <html:img page="/pages/images/addDoc_small.gif"
                          titleKey="archigest.archivo.salas.aniadir.usuario.externo"
                          altKey="archigest.archivo.salas.aniadir.usuario.externo"
                          styleClass="imgTextMiddle"/>
                </a>
              </div>
              </c:when>
              <c:otherwise>
	            <c:choose>
					<c:when test="${formBean.accesoAplicacion == true}">
					 <html:img page="/pages/images/checkbox-yes.gif"
						altKey="archigest.archivo.si"
						titleKey="archigest.archivo.si"
						styleClass="imgTextMiddle" />&nbsp;<c:out value="${formBean.nombre}"/>&nbsp;<c:out value="${formBean.apellidos}"/>
					</c:when>
					<c:otherwise>
						<html:img page="/pages/images/checkbox-no.gif"
						altKey="archigest.archivo.no"
						titleKey="archigest.archivo.no"
						styleClass="imgTextMiddle" />
					</c:otherwise>
				</c:choose>
              </c:otherwise>
              </c:choose>
            </td>
          </tr>

          <tr>
            <td width="200px" class="tdTitulo">
              <fmt:message key="archigest.archivo.doc.identificativo"/>:&nbsp;
            </td>
            <td class="tdDatos">
              <html:text property='numDocIdentificacion' styleClass="input" maxlength="32" />
              <c:set var="tiposDoc" value="${sessionScope[appConstants.salas.LISTA_TIPOS_DOC_IDENTIFICATIVO_KEY]}" />
              <html:select property="tipoDocIdentificacion">
                <html:option value=""></html:option>
                <html:options collection="tiposDoc" property="id" labelProperty="nombre" />
              </html:select>
            </td>
          </tr>

          <tr>
            <td  class="tdTitulo">
              <fmt:message key="archigest.archivo.nombre"/>:&nbsp;
            </td>
            <td class="tdDatos">
              <html:text property='nombre' styleClass="input80" maxlength="64" />
            </td>
          </tr>

          <tr>
            <td  class="tdTitulo">
              <fmt:message key="archigest.archivo.apellidos"/>:&nbsp;
            </td>
            <td class="tdDatos">
              <html:text property='apellidos' styleClass="input99" maxlength="254" />
            </td>
          </tr>
          <tr>
            <td class="tdTitulo">
              <bean:message key="archigest.archivo.nacionalidad"/>:&nbsp;
            </td>
            <td class="tdDatos">
              <html:text property='nacionalidad' styleClass="input80" maxlength="64" />
            </td>
          </tr>

          <tr>
            <td class="tdTitulo">
              <bean:message key="archigest.archivo.telefonos"/>:&nbsp;
            </td>
            <td class="tdDatos">
              <html:text property='telefonos' styleClass="input99" maxlength="128" />
            </td>
          </tr>

          <tr>
            <td class="tdTitulo">
              <bean:message key="archigest.archivo.email"/>:&nbsp;
            </td>
            <td class="tdDatos">
              <html:text property='email' styleClass="input99" maxlength="128" />
            </td>
          </tr>

          <tr>
            <td class="tdTitulo">
              <bean:message key="archigest.archivo.direccion"/>:&nbsp;
            </td>
            <td class="tdDatos">
              <html:textarea styleClass="input95" style="height:40px" rows="4" property="direccion" onkeypress="maxlength(this,254,false)" onchange="maxlength(this,254,true)" />
            </td>
          </tr>
          <tr>
            <td class="tdTitulo">
              <bean:message key="archigest.archivo.salas.usuario.vigente"/>:&nbsp;
            </td>
            <td class="tdDatos">
              <html:checkbox property="vigente" styleClass="checkbox"></html:checkbox>
            </td>
          </tr>
            <c:if test="${empty formBean.id}">
              <tr>
                <td colspan="2">
              <div class="separador5">&nbsp;</div>
              <tiles:insert template="/pages/tiles/PABlockLayout.jsp">
                <tiles:put name="blockTitle" direct="true"><bean:message key="archigest.archivo.asociar.archivos"/></tiles:put>

                <tiles:put name="blockContent" direct="true">
                  <tiles:insert template="/pages/tiles/PADataBlockLayout.jsp">
                    <tiles:put name="blockContent" direct="true">
                      <div class="bloque">
                        <div class="separador5">&nbsp;</div>
                          <display:table name="pageScope.listaArchivosSeleccionar"
                            id="archivo"
                            style="width:99%;margin-left:auto;margin-right:auto">
                            <display:setProperty name="basic.msg.empty_list">
                              <bean:message key="archigest.archivo.salas.usuario.sin.archivos.asociados"/>
                            </display:setProperty>
                            <display:column media="html" style="width:10px">
                              <html-el:multibox property="idsArchivo" value="${archivo.idArchivo}" styleClass="checkbox"></html-el:multibox>
                            </display:column>
                            <display:column titleKey="archigest.archivo.archivo">
                              <c:out value="${archivo.nombreArchivo}"/>
                            </display:column>
                          </display:table>
                          <div class="separador5">&nbsp;</div>
                      </div>

                    </tiles:put>
                  </tiles:insert>
                </tiles:put>
              </tiles:insert>
              </td>
            </tr>
          </c:if>
        </table>
      </html:form>
    </div>
    <div class="separador5">&nbsp;</div>

  </tiles:put>
</tiles:insert>