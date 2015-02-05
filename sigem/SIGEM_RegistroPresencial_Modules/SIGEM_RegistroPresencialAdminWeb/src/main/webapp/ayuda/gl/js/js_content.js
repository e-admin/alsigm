	
	
	//Del ayuda_menu.html	
	//Flag que controla que imagen ha sido seleccionada
	flag = 0;
	if (document.images)
		//Flag para controlar los clicks
		roll = 1;
	else
		roll = 0;
	roll = 1;
	if (roll){
		menos = new Image(); menos.src = "images/comun/carpetaAbierta.gif";
		mas = new Image(); mas.src = "images/comun/carpeta.gif";
	}

	var tipos = new Array( "ImgmasF", "ImgmasC","ImgmasO", "ImgmasG");
											// "ImgmasM", "ImgmasI", "ImgmasN", "ImgmasS", "ImgmasP", "ImgmasE", 
											// "ImgmasR", "ImgmasA" );

	function abrirCarpeta( tipo )
	{
		var imgObjName = "";
		var tt = document.images;
		for( i = 0 ; i < tipos.length; i++ )
		{
			if( tipo == tipos[i] ) imgObjName = "menos";
			else imgObjName = "mas";	
			document.images[tipos[i]].src = eval(imgObjName + ".src");
		}
		
	}
	function cerrarCarpeta()
	{
		var tt = document.images;
		for( i = 0 ; i < tipos.length; i++ )
			document.images[tipos[i]].src = eval("mas.src");
		
	}
	function carpetaOpen( tipo )
	{
		abrirCarpeta( tipo ); 
		document.f1.estado.value = tipo.substring( tipo.length-1, tipo.length );	
	}
	function carpetaClose( tipo )
	{
		cerrarCarpeta();
		document.f1.estado.value = tipo.substring( tipo.length-1, tipo.length );
	}

	function cambiarImg(imgDocID,imgObjName){
		//indicamos los enlaces de los menus principales
		//if(imgObjName == "prop") parent.content.location = '';
		//"mas" se refiere a la imagen de carpeta abierta
		//"menos" se refiere a la imagen de carpeta cerrada
		//alert("fuera:"+roll);
		
		/*
		if (roll == 1)
		{
			j = 1;
			for( z = 0; z < tipos.length; z++ )
			{
				if( imgDocID == tipos[z]) {
					abrirCarpeta( imgDocID ); flag = j;
					document.f1.estado.value = tipos[z].substring(tipos[z].length-1,tipos[z].length); 
				}	else j = j+1;
			}
			roll = 0;
		} else { 
			//alert("entra:"+roll);
			for ( k = 1; k < (tipos.length+1); k++)
			{
					//alert("flag:"+flag);
					switch(flag){
						case k:
									//alert(imgDocID+"****"+tipos[k-1]);
									if(imgDocID == tipos[k-1]) carpetaClose( imgDocID ); 
									else carpetaOpen( imgDocID ); 
									break;
					}
			}
			//limpiamos los parámetros
			roll = 1;
		}//end if
		*/
	}


/*
function detectKey() {
	if (event.ctrlKey) {
		event.ctrlkey=0;
		return false; 
	} 
	if(window.event && (window.event.keyCode == 116 || window.event.keyCode == 122)){
		window.event.keyCode = 0;
		return false;
	}
}


function clickIE() {
	if (document.all) {return false;}
}

	function clickNS(e) {
		if (document.layers||(document.getElementById&&!document.all)) {
			if (e.which==2||e.which==3) {
				 return false;
			}
		}
	}
	
if (document.layers) {
	document.captureEvents(Event.MOUSEDOWN);document.onmousedown=clickNS;
} else{
	document.onmouseup=clickNS;document.oncontextmenu=clickIE;
}


document.oncontextmenu=new Function("return false")
document.onkeydown = detectKey;
*/



 
