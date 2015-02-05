<script>
	/*
	 * Funcion que se encarga de obtener el width del iframe para ponerselo al div
	 * que se sale de la pantalla, asignandole un scroll horizontal para poder ver el contenido
	 */
	function resizeTable(){
		if(document.getElementById('divTbl') && (window.top) && (window.top.getIframeWidthSize)){
			var tamDisponible = window.top.getIframeWidthSize();
			var divTable = document.getElementById('divTbl');
			divTable.style.width = tamDisponible + 10;
			divTable.style.overflowX="scroll";
			divTable.style.overflowY="hidden";
		}
		var divTblFicha = document.getElementsByName('divTblFicha');
		if(divTblFicha && divTblFicha.length > 0){
			var tamDisponible = window.top.getIframeWidthSize();
			for(var i=0; i<divTblFicha.length; i++){
				divTblFicha[i].style.width = tamDisponible - 250;
				divTblFicha[i].style.overflowX="scroll";
				divTblFicha[i].style.overflowY="hidden";
			}
		}
		if(document.getElementById('divTblValoracion') && (window.top) && (window.top.getIframeWidthSize)){
			var tamDisponible = window.top.getIframeWidthSize();
			var divTable = document.getElementById('divTblValoracion');
			divTable.style.width = tamDisponible - 360;
			divTable.style.overflowX="scroll";
			divTable.style.overflowY="hidden";
		}
	}
</script>


