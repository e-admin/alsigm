<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//w3c//dtd html 4.0 transitional//en">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<title>Página de búsqueda</title>
<link rel="stylesheet" href='<ispac:rewrite href="css/styles.css"/>' />
<link rel="stylesheet" href='<ispac:rewrite href="css/estilos.css"/>' />
<!--[if lte IE 5]>
			<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos_ie5.css"/>'/>
		<![endif]-->

<!--[if IE 6]>
			<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos_ie6.css"/>'>
		<![endif]-->

<!--[if gte IE 7]>
			<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos_ie7.css"/>'>
		<![endif]-->

<script type="text/javascript"
	src='<ispac:rewrite href="../scripts/jquery-1.3.2.min.js"/>'></script>
<script type="text/javascript"
	src='<ispac:rewrite href="../scripts/jquery-ui-1.7.2.custom.min.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/utilities.js"/>'></script>
<ispac:javascriptLanguage/>


<script language='JavaScript' type='text/javascript'><!--


function storeImage(){
document.getElementById('wait').style.display='block';
document.getElementById('formulario').style.display='none';
document.getElementById('buttomSave').style.display='none';
document.getElementById('buttomCancel').style.display='none';
document.defaultForm.action = '<%=request.getContextPath() + "/geoLocalizacion.do?method=storePlano"%>';
document.defaultForm.submit();

}
function  show_plano(target, action, width, height, msgConfirm, doSubmit, needToConfirm, form) {

	planos=document.getElementById('listadoPlanos');
	action=action+"&idMapa="+$("#listadoPlanos").find(':selected').val();
	showFrame(target, action, width, height, msgConfirm, doSubmit, needToConfirm, parent.document.forms[0]) ;

}

function showFrame(target, action, width, height, msgConfirm, doSubmit, needToConfirm, form) {

	if (needToConfirm != null) {

		if (typeof ispac_needToConfirm != 'undefined') {
			ispac_needToConfirm = needToConfirm;
		}
	}

  	if ((msgConfirm != null) && (msgConfirm != ''))
  	  	if (confirm(msgConfirm) == false)
  	      	return;

  	if ((form == null) && (document.forms.length > 0)) {
  		form = document.forms[0];
  	}


  	var element;
  	/*if (width == null)
      	width = 640;
  	if (height == null)
      	height = 480;*/

		width=getWidthWindow();
		height=getHeightWindow();


  	eval('element = document.getElementById("' + target + '")');
	if(element==null){
		eval('element = parent.document.getElementById("' + target + '")');
	}

	if (element != null) {


	   	showLayer();

    	var x = (document.body.clientWidth - width) / 2;
    	//var y = (document.body.clientHeight - height) / 2;
    	var y = document.body.scrollTop + (document.body.clientHeight - height) / 2;
    	if (y < 10) {
    		y = 10;
    	}
		element.style.height = height;
		element.style.width = width;
		element.style.position = "absolute";
		element.style.left = x;
		element.style.top = y;
		element.style.visibility = "visible";

		if (document.forms.length > 0) {

			var oldTarget = form.target;
			var oldAction = form.action;

	    	eval('form.target = "' + target + '"');

	    	if (action.substring(0,4) == 'http')
	        	form.action = action;
		    else if (action.substring(0,1) != '/')
				form.action = replaceActionForm(form.action, action);
			else
				form.action = action;

			// Hacer un submit al workframe o abrir una url
			if ((doSubmit != null) && (!doSubmit)) {
				window.frames[target].location = form.action;;
			}
			else {
				form.submit();
			}

			form.target = oldTarget;
			form.action = oldAction;
		}
		else {

			eval("frames['" + target + "'].location.href='" + action + "'");

		}
  	}


}


		//--></script>

</head>

<body>
<div id="contenido" class="move">
	<div class="ficha">
		<div class="encabezado_ficha">
			<div class="titulo_ficha">
			<logic:notPresent name="refValidada">
				<h4><bean:message key="titulo.verPlano" /></h4>
			</logic:notPresent>
			<div class="acciones_ficha">
				<input id="buttomSave" type="button"
					value='<bean:message key="forms.button.save"/>' class="btnStore"
					onclick='javascript:storeImage();'/>
				<input  id="buttomCancel" type="button"
					value='<bean:message key="common.message.close"/>' class="btnCancel"
					onclick='<ispac:hideframe/>' />
			</div><%--fin acciones ficha --%>
		</div><%--fin titulo ficha --%>
		</div><%--fin encabezado ficha --%>

	<div class="cuerpo_ficha">
		<div class="seccion_ficha">
			<div class="cabecera_seccion">
					<c:set var="keySeccion" value="${requestScope['keySeccion']}"/>
					<bean:define id="keySeccion" name="keySeccion" type="java.lang.String"/>
						<h4><bean:message key='<%=keySeccion%>'/></h4>
					</div>
					<div style="display:none" id="wait" />
					<p><label class="popUpInfo" ><nobr><bean:message key="localgis.plano.enCurso"/></nobr></label></p>
					</div>
<html:form action="geoLocalizacion.do"
	method="post" styleId="formulario">


			<p>
				<label class="popUpInfo" ><nobr><bean:message key="geolocalizacion.label.municipio"/>:</nobr></label>
				<html:hidden  styleId="idMunicipio" property="property(SGL_OBRAS_MENORES:ID_MUNICIPIO)" />
				<html:text    styleClass="inputReadOnly"  readonly="true" size="60" 	property="property(SGL_OBRAS_MENORES:MUNICIPIO)" ></html:text>
			</p>


				<logic:present name="via">
					<p>
						<label class="popUpInfo" ><nobr><bean:message key="geolocalizacion.label.via"/>:</nobr></label>
						<html:hidden  styleId="idVia" property="property(SGL_OBRAS_MENORES:ID_VIA)" />
						<html:text    styleClass="inputReadOnly"  readonly="true" size="60" 	property="property(SGL_OBRAS_MENORES:VIA)" ></html:text>
					</p>
					<p>
						<label class="popUpInfo" ><nobr><bean:message key="geolocalizacion.label.plano"/>:</nobr></label>
						<html:select  styleId="listadoPlanos" styleClass="inputSelect"   property="criterio" >
						     <html:options collection="listadoPlanos"  property="property(ID)"  labelProperty="property(SUSTITUTO)"/>
						</html:select>
					<ispac:imageframe
					  id="RELOAD_PLANO"
					  target="workframe"
					  action='<%= "geoLocalizacion.do?method=via&keySeccion="+keySeccion%>'
					  image="img/map.gif"
					  width="640" height="480"
					  showFrame="true"
					  inputField="SGL_OBRAS_MENORES:ID_MUNICIPIO"
					  jsShowFrame="show_plano" >
					  	</ispac:imageframe>
					</p>
				</logic:present>
				<logic:present name="referenciaCatastral">
					<p>
						<label class="popUpInfo" ><nobr><bean:message key="geolocalizacion.label.referenciaCatastral"/>:</nobr></label>

						<html:text    styleClass="inputReadOnly"  readonly="true" size="60" 	property="property(SGL_OBRAS_MENORES:REFERENCIA_CATASTRAL)" ></html:text>
					</p>
					<p>
						<label class="popUpInfo" ><nobr><bean:message key="geolocalizacion.label.plano"/>:</nobr></label>
						<html:select  styleId="listadoPlanos" styleClass="inputSelect" property="criterio" >
							 <html:options collection="listadoPlanos"  property="property(ID)"  labelProperty="property(SUSTITUTO)"/>
						</html:select>
						<ispac:imageframe
						id="RELOAD_PLANO"
						target="workframe"
						action='<%= "geoLocalizacion.do?method=referenciaCatastral&keySeccion="+keySeccion%>'
						image="img/map.gif"
						width="640" height="480"
						showFrame="true"
						inputField="SGL_OBRAS_MENORES:ID_MUNICIPIO"
						jsShowFrame="show_plano" >	</ispac:imageframe>
					</p>
				</logic:present>
				<logic:present name="coordenadas">
				<p>
					<label class="popUpInfo" ><nobr><bean:message key="geolocalizacion.label.coordenadas"/>:</nobr></label>
					<html:text    styleClass="inputReadOnly"  readonly="true" size="30" 	property="property(SGL_OBRAS_MENORES:COOR_X)" ></html:text>
					<html:text    styleClass="inputReadOnly"  readonly="true" size="30" 	property="property(SGL_OBRAS_MENORES:COOR_Y)" ></html:text>
				</p>
				<p>
						<label class="popUpInfo" ><nobr><bean:message key="geolocalizacion.label.plano"/>:</nobr></label>
						<html:select styleId="listadoPlanos"  styleClass="inputSelect" property="criterio"  >
						     <html:options collection="listadoPlanos"  property="property(ID)"  labelProperty="property(SUSTITUTO)"/>
						</html:select>
					<ispac:imageframe
					  id="RELOAD_PLANO"
					  target="workframe"
					  action='<%= "geoLocalizacion.do?method=coordenadas&keySeccion="+keySeccion%>'
					  image="img/map.gif"
					  width="640" height="480"
					  showFrame="true"
					  inputField="SGL_OBRAS_MENORES:ID_MUNICIPIO"
					  jsShowFrame="show_plano" >	</ispac:imageframe>

				</p>
				</logic:present>

				<c:set var="urlMapa" value="${sessionScope['urlMapa']}"/>
				<bean:define id="urlMapa" name="urlMapa" type="java.lang.String"/>
				<img  id="urlImage" src='<%=urlMapa%>' />
				<input type="hidden" name="urlMapa" value='<%=urlMapa%>' />
				</div>


		</html:form>
	</div><%--seccion ficha --%>
	</div><%--fin cuerpo ficha --%>

	</div><%--fin  ficha --%>
<div><%--fin contenido --%>
</body>

</html>

<script>
	positionMiddleScreen('contenido');
	$(document).ready(function(){
		$("#contenido").draggable();
		<c:set var="idMapa" value="${requestScope['idMapa']}"/>
		<bean:define id="idMapa" name="idMapa" type="java.lang.String"/>
		idMapa=<%=idMapa%>;
		$("#listadoPlanos").val(idMapa);
	});


</script>