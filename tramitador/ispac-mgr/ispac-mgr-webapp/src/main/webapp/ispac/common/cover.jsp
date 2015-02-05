<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<c:set var="reloadPeriod" value="${requestScope.reloadPeriod}" />

<ispac:message/>

<div class="help">
<ispac:onlinehelp tipoObj="0" image="img/help.gif" titleKey="header.help"/>
</div>

<script type="text/javascript" src='<ispac:rewrite href="../../dwr/interface/WorkListAPI.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../../dwr/engine.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../../dwr/util.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery-1.3.2.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/jquery.alerts.js"/>'></script>

<script type="text/javascript">
		
        function errorHandler(message, exception) {
            jAlert(message, '<bean:message key="common.alert"/>', '<bean:message key="common.message.ok"/>' , '<bean:message key="common.message.cancel"/>');
        }

		function renderWebComponent(webComponent) {
			var htmlCode = "";
			
			if (webComponent != null) {

                var hidden = isHidden(document.getElementById('block'+webComponent.id));
                
				htmlCode += "<div class=\"ficha\">";
				htmlCode += "<div class=\"encabezado_ficha\"><h3></h3></div>";
				
				htmlCode += "<div class=\"cuerpo_ficha\">";
				htmlCode += "<div class=\"cuerpo_subs\">";
				
				htmlCode += "<div class=\"titulo\">";
				htmlCode += "<h4 id=\"title" + webComponent.id + "\">" + webComponent.title + "</h4>";
				htmlCode += "<div class=\"acciones_submenus\">";
				htmlCode += "<a href=\"javascript:showHidden('"
					+ webComponent.id + "');\"><img id='img" + webComponent.id
					+ "' src='" + '<ispac:rewrite href="."/>';
                if (hidden) {
                    htmlCode += "/img/arrow_down.gif";
                } else {
                    htmlCode += "/img/arrow_up.gif";
                }
                htmlCode += "'/></a>";
                    
				htmlCode += "</div>";
				htmlCode += "</div>";

				htmlCode += "<div id=\"block" + webComponent.id + "\" class=\"cuerpo_sub\"";
                if (hidden) {
                    htmlCode += " style=\"display:none\"";
                }
                htmlCode += ">";
                
                if (webComponent.content != null) {
					htmlCode += webComponent.content;
				}
			
				htmlCode += "</div>";

				htmlCode += "</div>";
				htmlCode += "</div>";
				htmlCode += "</div>";
			}
			
			return htmlCode;
		}
		
		function loadWorkListContent() {
			WorkListAPI.getWebComponents(function(data) {
				if (data) {
					var html = "";
					for (var i = 0; i < data.length; i++) {
                        if (data[i] != null) {
                            html += renderWebComponent(data[i]);
                        }
					}
					$("#worklist").html(html);
                    
					<c:if test="${!empty reloadPeriod && (reloadPeriod > 0)}">
					window.setTimeout ('loadWorkListContent()', <c:out value="${reloadPeriod}"/>);
					</c:if> 
				}
			});
		}
		
		$(document).ready(function() {

            // Establecer el gestor de errores
            dwr.engine.setErrorHandler(errorHandler);

            // Cargar la información de la bandeja de entrada
			loadWorkListContent();
		});
		
</script>

<div id="worklist">
    <div class="ficha">
        <div class="encabezado_ficha">
            <h3></h3>
        </div>
        <div class="cuerpo_ficha">
            <div class="cuerpo_subs">
                <div id="block" class="cuerpo_sub">
                    <img src='<ispac:rewrite href="img/loading.gif"/>' border="0" class="imgTextMiddle"/>
                    &nbsp;<bean:message key="loading.text"/>
                </div>
            </div>
        </div>
    </div>
</div>
