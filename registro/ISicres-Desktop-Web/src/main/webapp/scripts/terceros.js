		function infoUser() {
			var img = document.getElementById('imgInfoUser').src;

			if (document.getElementById('infoUser').className == 'usuario_plegado') {
				document.getElementById('infoUser').className = 'usuario_desplegado';
				document.getElementById('imgInfoUser').className = 'imgInfoUserUp';
			}	else {
				document.getElementById('infoUser').className = 'usuario_plegado';
				document.getElementById('imgInfoUser').className = 'imgInfoUserDown';
			}
		}

		function menuTree() {
			if (document.getElementById('contenido_tree').className == 'botones_plegados') {
				document.getElementById('contenido_tree').className = 'menu_desplegado';
				document.getElementById('imgMenu').className = 'imgMenuOn';
				document.getElementById('imgTree').className = 'imgTreeOff';
			}	else if (document.getElementById('contenido_tree').className == 'tree_desplegado'){
				document.getElementById('contenido_tree').className = 'menu_desplegado';
				document.getElementById('imgMenu').className = 'imgMenuOn';
				document.getElementById('imgTree').className = 'imgTreeOff';
			}	else if (document.getElementById('contenido_tree').className == 'menu_desplegado'){
				document.getElementById('contenido_tree').className = 'botones_plegados';
				document.getElementById('imgMenu').className = 'imgMenuOff';
				document.getElementById('imgTree').className = 'imgTreeOff';
			}
		}

		function tree() {
			if (document.getElementById('contenido_tree').className == 'botones_plegados') {
				document.getElementById('contenido_tree').className = 'tree_desplegado';
				document.getElementById('imgTree').className = 'imgTreeOn';
				document.getElementById('imgMenu').className = 'imgMenuOff';
			}	else if (document.getElementById('contenido_tree').className == 'menu_desplegado'){
				document.getElementById('contenido_tree').className = 'tree_desplegado';
				document.getElementById('imgTree').className = 'imgTreeOn';
				document.getElementById('imgMenu').className = 'imgMenuOff';
			}	else if (document.getElementById('contenido_tree').className == 'tree_desplegado'){
				document.getElementById('contenido_tree').className = 'botones_plegados';
				document.getElementById('imgTree').className = 'imgTreeOff';
				document.getElementById('imgMenu').className = 'imgMenuOff';
			}
		}

		function menu() {

			if (document.getElementById('contenido_menu').className == 'botones_plegados') {
				document.getElementById('contenido_menu').className = 'menu_desplegado';
				document.getElementById('imgMenu').className = 'imgMenuOn';
			}	else if (document.getElementById('contenido_menu').className == 'menu_desplegado'){
				document.getElementById('contenido_menu').className = 'botones_plegados';
				document.getElementById('imgMenu').className = 'imgMenuOff';
			}
		}

		var tabAct = 1;
		function showTab(i) {
			if (i != tabAct) {
				document.getElementById('tab'+i).className = 'tabOn';
				document.getElementById('tab'+tabAct).className = 'tabOff';
				document.getElementById('cuerpo'+i).className = 'grupo_seccion_tab visible';
				document.getElementById('cuerpo'+tabAct).className = 'grupo_seccion_tab oculto';
				tabAct = i;
			}
		}

		var subAct = 1;
		function showSub(i) {
			if (i != subAct) {
				document.getElementById('sub'+i).className = 'subOn';
				document.getElementById('sub'+subAct).className = 'subOff';
				document.getElementById('subcuerpo'+i).className = 'grupo_sub visible';
				document.getElementById('subcuerpo'+subAct).className = 'grupo_sub oculto';
				subAct = i;
			}
		}

		var subAct2 = 4;
		function showSub2(i) {
			if (i != subAct2) {
				document.getElementById('sub'+i).className = 'subOn';
				document.getElementById('sub'+subAct2).className = 'subOff';
				document.getElementById('subcuerpo'+i).className = 'grupo_sub visible';
				document.getElementById('subcuerpo'+subAct2).className = 'grupo_sub oculto';
				subAct2 = i;
			}
		}


		function showItem(i) {
			if (document.getElementById('item'+i).className == 'item_plegado') {
				document.getElementById('item'+i).className = 'item_desplegado';
			}	else if (document.getElementById('item'+i).className == 'item_desplegado'){
				document.getElementById('item'+i).className = 'item_plegado';
			}
		}
