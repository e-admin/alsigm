

<html>
<head>

<title>gwt-diagrams example</title>

<script language='javascript'
	src='ieci.tdw.ispac.designer.Designer.nocache.js'></script>

<script language="Javascript"> 


		document.oncontextmenu = function(){return false} 
		document.onselectstart = function(){return false}
		document.ondragstart = function(){return false}
	
		
   		window.onResize = handlerResize();


function setSizeLayer(){
handlerResize();
}
function handlerResize() {
	

  var myWidth = 0, myHeight = 0;
  if( typeof( window.innerWidth ) == 'number' ) {
    //Non-IE
    myWidth = window.innerWidth;
    myHeight = window.innerHeight;
  } else if( document.documentElement && ( document.documentElement.clientWidth || document.documentElement.clientHeight ) ) {
    //IE 6+ in 'standards compliant mode'
    myWidth = document.documentElement.clientWidth;
    myHeight = document.documentElement.clientHeight;
  } else if( document.body && ( document.body.clientWidth || document.body.clientHeight ) ) {
    //IE 4 compatible
    myWidth = document.body.clientWidth;
    myHeight = document.body.clientHeight;
  }
  var layer= document.getElementById('layer');
  if(layer!=null){
  layer.style.height=myHeight;
  layer.style.width=myWidth;
  }

  
if(document.getElementById('area')!=null){
 document.getElementById('area').style.height=myHeight-5;
 //document.getElementById('area').style.height=600;
  document.getElementById('area').style.width=myWidth-30;
  document.getElementById('layer').style.height=myHeight;
  document.getElementById('layer').style.width=myWidth;
  document.getElementById('body').style.overflow='hidden';
  document.getElementById('area').style.overflow='auto';
}
  
   return false;
}
	
	
</script>



</head>
<body id="body" onResize="handlerResize()" style="overflow:hidden ; "  onload="setSizeLayer()" >

 <div id="contenedor">
 
<iframe src="javascript:''" id="__gwt_historyFrame"
	style="width: 0; height: 0; border: 0" ></iframe>
<table width="100%"  height="100%">
	<tr>
		<td valign="middle">
		
		<div id="diagrama" style="display: table;width:100%">
		
		</div>
	

		</td>
	</tr>
</table>



<div   id="layer" style="z-index:1030;position: absolute; top:0px; left:0px;background:white; filter:alpha(opacity=50);-moz-opacity:.50;opacity:.50;" >
</div>



<DIV id=listado  style="display:none;z-index:1050;"></DIV>

<DIV id=tramites  style="display:none;z-index:1050;"></DIV>

<DIV id=subProceso  style="display:none;z-index:1050;"></DIV>
</div>
</body>

</html>

