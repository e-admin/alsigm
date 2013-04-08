<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<html>
	<head>
		<LINK href="css/estilos.css" type=text/css rel=stylesheet />
		
		<SCRIPT language=javascript>
			function getCheck(numero) {
				return document.getElementById('entidadAsociada['+numero+']');
			}
		
			function mostrarEntidades() {
				var entidades = parent.getEntidades();
				if (entidades == undefined || entidades.length==0) {
					entidades = new Array(0);
					document.getElementById('cargando').style.visibility = 'hidden';
					document.getElementById('sin_entidades').style.visibility = 'visible';
				}
				var i;
				var _tabla = document.getElementById('tabla_entidades');
				for (i=0; i<entidades.length; i++){
					var _tbody = document.createElement('TBODY');
					var _tr = document.createElement('TR');
					var _td1 = document.createElement('TD');
					var _td2 = document.createElement('TD');
					var _text = document.createTextNode(entidades[i][1]);
					var _check = document.createElement('INPUT');
					var _style = document.createElement('STYLE');
					var cadena = 'mostrarAplicaciones('+i+')';
					_td2.onclick = new Function(cadena);
					cadena = 'ocultarAplicaciones('+i+')';
					_tr.onmouseout = new Function(cadena);
					_check.type = 'checkbox';
					_check.id='entidadAsociada['+i+']';
					_td1.appendChild(_check);
					_td1.setAttribute('width', '26px');
					_td2.appendChild(_text);
					_tr.appendChild(_td1);
					_tr.appendChild(_td2);
					if (i%2==0)
						_tr.setAttribute('className', 'odd_list');
					else _tr.setAttribute('className', 'even_list');
					_tbody.appendChild(_tr);
					_tabla.appendChild(_tbody);
				}
				document.getElementById('cargando').style.visibility = 'hidden';
				document.getElementById('listado_asignado').style.visibility = 'visible';
			}
			
			function mostrarAplicaciones(posicion) {
				var _apps = document.getElementById('aplicaciones');
				var _desc = parent.getDescripciones();
				var _li = document.createElement('LI');
				var i;
				var permisos_i = parent.getPermisos()[posicion];
				var _list = document.getElementById('listado_aplicaciones');
				if (_list != undefined)
					_apps.removeChild(_list);				
				var _ul = document.createElement('UL');
				_ul.id = 'listado_aplicaciones';
				for(i=0; i<	permisos_i.length; i++){
					var _li = document.createElement('LI');
					var _text = document.createTextNode(_desc[permisos_i[i]]);
					_li.appendChild(_text);
					_ul.appendChild(_li);
				}
				_apps.appendChild(_ul);
				document.getElementById('aplicaciones').style.visibility = 'visible';
			}
			
			function ocultarAplicaciones(posicion) {
				document.getElementById('aplicaciones').style.visibility = 'hidden';
			}
		</SCRIPT>
	</head>
	<body class="frame_barra" onload="javascript:mostrarEntidades();">
		<form id="formulario_entidades_asociadas">
			<div id=listado_asignado style="margin:0px; width: 100%; visibility: hidden" align="center">
				<table id="tabla_entidades" width="100%" style="margin: 0px" cellpadding="0px" cellspacing="0px">
					
				</table>
			</div>
			<div id=cargando style="width: 100%; height: 100%; visibility: visible; top: 50%" align="center">
				<bean:message key="usuario.nuevo.cargando"/>
			</div>
			<div id=sin_entidades style="width: 100%; height: 100%; visibility: hidden; top: 50%" align="center">
				<bean:message key="usuario.nuevo.sin_entidades"/>
			</div>
			<div id=aplicaciones class=ventana_aplicaciones style="padding: 5px; position: absolute; top: 30px; width: 90%; visibility: hidden">
				<br/>
			</div>
		</form>
	</body>
</html>