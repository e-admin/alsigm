<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<html>
  <head>
    <link rel="stylesheet" href='<ispac:rewrite href="css/styles.css"/>'/>
    <link rel="stylesheet" href='<ispac:rewrite href="css/menus.css"/>'/>
    <link rel="stylesheet" href='<ispac:rewrite href="css/nicetabs.css"/>'/>
     <link rel="stylesheet" href='<ispac:rewrite href="css/estilos.css"/>'/>
		<!--[if lte IE 5]>
			<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos_ie5.css"/>'/>
		<![endif]-->	

		<!--[if IE 6]>
			<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos_ie6.css"/>'>
		<![endif]-->	

		<!--[if gte IE 7]>
			<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos_ie7.css"/>'>
		<![endif]-->
    
    <script type="text/javascript" src='<ispac:rewrite href="../scripts/utilities.js"/>'> </script>
    <ispac:javascriptLanguage/>
    <script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-1.3.2.min.js"/>'></script>
  	<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-ui-1.7.2.custom.min.js"/>'></script>
    <script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery.alerts.js"/>'></script>
    <script>
      function showHideErrorException(id) {
        $('#div'+id).toggle('slow', function() {
          if ($('#div'+id).is(":visible")) {
            $('#img'+id).attr("src",'<ispac:rewrite href="img/minus.gif"/>');
          } else {
            $('#img'+id).attr("src",'<ispac:rewrite href="img/plus.gif"/>');
          }
        });
      }
    </script>
  </head>
  <body>
  
    <div id="contenido" class="move" >
      <div class="ficha">
        <div class="encabezado_ficha">
          <div class="titulo_ficha">
            <div class="acciones_ficha">
              <input type="button" value='<bean:message key="common.message.close"/>' class="btnCancel" 
                onclick='javascript:top.ispac_needToConfirm=false;<ispac:hideframe refresh="true"/>'/>
            </div>
          </div>
        </div>

        <div class="cuerpo_ficha">
          <div class="seccion_ficha">
            <html:errors/>
            <logic:notPresent name="errors">
              
              <c:set var="okList" value="${sessionScope[appConstants.actions.OK_SCANNED_FILES_LIST]}"/>
              <c:if test="${!empty okList}">
                <p><label class="popUpInfo"><nobr><bean:message key="upload.from.repository.ok.text"/></nobr></label></p>
                <ul>
                  <c:forEach var="okInfo" items="${okList}" varStatus="status">
                    <li>
                      <c:if test="${!empty okInfo['NUMEXP']}">
                        <c:out value="${okInfo['NUMEXP']}"/>:
                      </c:if>
                      <c:out value="${okInfo['FILE_NAME']}"/>
                    </li>
                  </c:forEach>
                </ul>
              </c:if>

              <c:set var="errorList" value="${sessionScope[appConstants.actions.ERROR_SCANNED_FILES_LIST]}"/>
              <c:if test="${!empty errorList}">
                  <p><label class="popUpInfo"><nobr><bean:message key="upload.from.repository.error.text"/></nobr></label></p>
                  <ul>
                    <c:forEach var="errorInfo" items="${errorList}" varStatus="status">
                      <li>
                        <c:if test="${!empty errorInfo['NUMEXP']}">
                          <c:out value="${errorInfo['NUMEXP']}"/>:
                        </c:if>
                        <c:out value="${errorInfo['FILE_NAME']}"/>
                        <div class="repoUploadErrorMessage">
                          <c:out value="${errorInfo['ERROR_MESSAGE']}"/>
                          <a href="#" onclick='javascript:showHideErrorException("<c:out value="${status.count}"/>");return false;'>
                            <img id='img<c:out value="${status.count}"/>' class="imgTextMiddle"
                              src='<ispac:rewrite href="img/plus.gif"/>'/>
                          </a>
                        </div>
                        <c:if test="${!empty errorInfo['ERROR_EXCEPTION']}">
                          <div id="div<c:out value="${status.count}"/>" class="repoUploadErrorException" style="display:none;">
                            <c:out value="${errorInfo['ERROR_EXCEPTION']}"/>
                          </div>
                        </c:if>
                      </li>
                    </c:forEach>
                  </ul>
              </c:if>
              
              <c:if test="${empty okList && empty errorList}">
                <p><label class="popUpInfo"><nobr><bean:message key="upload.from.repository.error.upload"/></nobr></label></p>
              </c:if>
              
            </logic:notPresent>
          </div>
        </div>
      </div>
    </div>

  </body>
  
  <script>
    positionMiddleScreen('contenido');
    $(document).ready(function(){
      $("#contenido").draggable();
    });
  </script> 
  
</html>