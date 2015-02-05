<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>

<title><bean:message key="head.title"/></title>

<link rel="shortcut icon" href='<ispac:rewrite href="img/favicon.ico"/>' type="image/x-icon"/>
<link rel="icon" href='<ispac:rewrite href="img/favicon.ico"/>' type="image/x-icon"/>
<link rel="stylesheet" href='<ispac:rewrite href="css/menus.css"/>'/>
<link rel="stylesheet" href='<ispac:rewrite href="css/nicetabs.css"/>'/>
<link rel="stylesheet" href='<ispac:rewrite href="css/styles.css"/>'/>
<link rel="stylesheet" href='<ispac:rewrite href="css/tablist.css"/>'/>
<link rel="stylesheet" href='<ispac:rewrite href="css/newstyles.css"/>'/>
<link rel="stylesheet" href='<ispac:rewrite href="css/diseniadorGrafico.css"/>'/>

	<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos_cab.css"/>'/>

	<!--[if lte IE 5]>
		<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos_cab_ie5.css"/>'/>
	<![endif]-->	

	<!--[if IE 6]>
		<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos_cab_ie6.css"/>'/>
	<![endif]-->	

	<!--[if gte IE 7]>
		<link rel="stylesheet" type="text/css" href='<ispac:rewrite href="css/estilos_cab_ie7.css"/>'/>
	<![endif]-->	


<!-- ARBOL DE PROCEDIMIENTOS -->
<script type="text/javascript" src='<ispac:rewrite href="../scripts/mktree.js"/>'></script>
<link rel="stylesheet" href='<ispac:rewrite href="css/mktree.css"/>'/>
<!-- FIN ARBOL DE PROCEDIMIENTOS -->

<!-- BARRA DE MENU -->
<link rel="stylesheet" href='<ispac:rewrite href="css/menuDropdown.css"/>'/>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/menuDropdown.js"/>'></script>
<!-- FIN BARRA DE MENU -->

<script type="text/javascript" src='<ispac:rewrite href="../scripts/menus.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/sorttable.js"/>'> </script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/utilities.js"/>'> </script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/newutilities.js"/>'> </script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/ispac-forms.js"/>'> </script>

<!-- AJAX -->
<script type="text/javascript" src='<ispac:rewrite href="../scripts/prototype.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/scriptaculous/scriptaculous.js"/>'></script>
<!--
<script type="text/javascript" src='<ispac:rewrite href="../scripts/rico.js"/>'></script>
-->

<!-- Diseñador Gráfico -->
<script type="text/javascript" src='<ispac:rewrite href="../scripts/designer/eventutil.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/designer/libreria.js"/>'></script>
<script type="text/javascript" src='<ispac:rewrite href="../scripts/designer/menuflotante.js"/>'></script>
<script>
//<!--
	// Funcionalidad de Rico para redondear bloques
	function __debug(str) {
    	new Insertion.Bottom( $('debug'), str + "</br>" );
	    $('debug').scrollTop = 99999;
	}

	var onloads = new Array();
	
	function bodyOnLoad() {
		new Effect.Round( null, 'roundNormal' );
		new Effect.Round( null, 'roundCompact', {compact:true} );
		new Effect.Round( null, 'roundTop', {corners:"top"} );

		for ( var i = 0 ; i < onloads.length ; i++ ) {
	        onloads[i]();
	    }
	}
//-->
</script>