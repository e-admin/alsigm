		var selectedField;
		var selectedIndex;
		
		function showFieldDetail( obj )
		{
			id = obj.id;
			
			var re = /^(field.)([0-9]+)$/;
			var matchArray = re.exec(id);
			index = matchArray[2];

			var selectedFieldDetail = "fieldDetail."+index;
			var i = 0;
			fin = false;
			while (!fin)
			{
				var elem = "fieldDetail." + i;
				i++;
				if (!document.getElementById(elem) )
					fin = true;
				else
					document.getElementById(elem).style.visibility='hidden';	
			}
			document.getElementById(selectedFieldDetail).style.visibility='visible';			
		}
		
		function setActiveField (obj)
		{
			selectedField = obj.id;
			var i=0;
			fin = false;
			while (!fin)
				 {
				var elem = "field."+i;
				i++;

				if (!document.getElementById(elem))
					fin = true;
				else
					document.getElementById(elem).className="inactivebgcolor";
				 }
			obj.className="activebgcolor";			 
		}
		
		function fieldOver(obj)
		{
			id = obj.id;
			if (id != selectedField)
				document.getElementById(id).className="overbgcolor";
 		}
	
		function fieldOut(obj)
		{
			id = obj.id
			if (id != selectedField)
				document.getElementById(id).className="inactivebgcolor";
		}
		
		function indexOver(obj)
		{
			id = obj.id;
			if (id != selectedIndex)
				document.getElementById(id).className="overbgcolor";
 		}
	
		function indexOut(obj)
		{
			id = obj.id
			if (id != selectedIndex)
				document.getElementById(id).className="inactivebgcolor";
		}
		
		function showIndexDetail(obj)
		{
			id = obj.id;
			
			var re = /^(index.)([0-9]+)$/;
			var matchArray = re.exec(id);
			index = matchArray[2];

			var selectedIndexDetail = "indexDetail."+index;
			var i = 0;
			fin = false;
			while (!fin)
			{
				var elem = "indexDetail." + i;
				i++;
				if (!document.getElementById(elem) )
					fin = true;
				else
					document.getElementById(elem).style.visibility='hidden';	
			}
			document.getElementById(selectedIndexDetail).style.visibility='visible';
		}
		function setActiveIndex(obj)
		{
			selectedIndex = obj.id;
			var i=0;
			fin = false;
			while (!fin)
				 {
				var elem = "index."+i;
				i++;

				if (!document.getElementById(elem))
					fin = true;
				else
					document.getElementById(elem).className="inactivebgcolor";
				 }
			obj.className="activebgcolor";			 
			
		}
		/*
		function addField()
		{
			var form = document.getElementById('addFieldForm');
			sendTabSelected(form);
			form.submit();
		}
		function addIndex()
		{
			var form = document.getElementById('addIndexForm');
			sendTabSelected(form);
			form.submit();
		}
		*/
		function changeVolListId(obj)
		{
			var k = obj.selectedIndex;
			var optionSelected = obj.options[k].value;
			var typeVolListDynamic = document.getElementsByName("general.volListType");
			var nullId = 2147483645;
			
			if (optionSelected != nullId )
				typeVolListDynamic[0].checked = true;
			else
				typeVolListDynamic[0].checked = false;
		}

		function changeType(typeSelect)
		{
			var k = typeSelect.selectedIndex;
			var optionSelected = typeSelect.options[k].value;

			var name = typeSelect.name;
			var baseName = name.substring(0, name.lastIndexOf(".") );
			var mult = baseName + ".mult";
			var doc = baseName + ".doc";
			var length = baseName +	".length";
			
			document.getElementsByName(doc)[0].disabled = false;
			document.getElementsByName(mult)[0].disabled = false;
			var isText = false;
			if (optionSelected == 2 ){ // LONG_TEXT = 2
				document.getElementsByName(mult)[0].checked = false ;
				document.getElementsByName(mult)[0].disabled = true;
				document.getElementsByName(length)[0].disabled = false;
				isText = true;
			}
			else if (optionSelected == 1 ){ // SHORT_TEXT    = 1;
				if (document.getElementsByName(mult)[0].checked){ // Un campo de texto puede ser documental o multivalor
					document.getElementsByName(doc)[0].disabled = true;
					document.getElementsByName(doc)[0].checked = false;
				}
				else if ( document.getElementsByName(doc)[0].checked ){
					document.getElementsByName(mult)[0].disabled = true;
					document.getElementsByName(mult)[0].checked = false;
				}
				else{
					document.getElementsByName(mult)[0].disabled = false;
					document.getElementsByName(doc)[0].disabled = false;
				}
				isText = true;
				document.getElementsByName(length)[0].disabled = false;
			}
			else
				document.getElementsByName(length)[0].disabled = true;
			document.getElementsByName(length)[0].value = "";
			if (!hasFtsConfig){
				document.getElementsByName(doc)[0].disabled = true;
				document.getElementsByName(doc)[0].checked = false;
			}
			else if (hasFtsConfig && isText)
				document.getElementsByName(doc)[0].disabled = false;
			else if (!isText){
				document.getElementsByName(doc)[0].disabled = true;
				document.getElementsByName(doc)[0].checked = false;
			}
		}
		function setDocOrMult(obj){
		
			var pos = obj.name.indexOf('mult');
			var name;
			var changeMult = false;
			if (pos != -1 ){ // Se tiene que deshabilitar el checkbox de documental
				name = obj.name.substring(0,pos);
				name += 'doc'
				}		
			else {
				pos = obj.name.indexOf('doc');
				name = obj.name.substring(0,pos);
				name += 'mult'
				changeMult =true;
			}
			
			checkBox = document.getElementsByName(name);
			
			var typeSelectName = name.substring(0, name.lastIndexOf(".") ) + ".type";
			var typeSelect = document.getElementsByName(typeSelectName)[0];
			
			var k = typeSelect.selectedIndex;
			var optionSelected = typeSelect.options[k].value;
			
			isLongText = false;
			if (optionSelected == 2 ){ // LONG_TEXT = 2
				isLongText = true;
				}
			isText = false;			
			if (optionSelected == 2 || optionSelected == 1 ){
				isText = true;
			}
			
			if (obj.checked){
				checkBox[0].disabled = true;
			}
			else{
				// Activar solo la casilla documental si el campo es texto,  y hay un motor de busqueda
				if ( ( changeMult && !isLongText ) || ( !changeMult && isText && hasFtsConfig) ) { 
					checkBox[0].disabled = false ;
					}
				}
		}		
		function setFdrName(obj)
		{
			var fdrNameObj = document.getElementById('fdrName');
			fdrNameValue = fdrNameObj.value;
			fdrNameValue += "@FLD(\'"+ obj + "\')";
			fdrNameObj.value = fdrNameValue;
			fdrNameObj.focus();
		}
		
		function isField(s)
		{	
			// fields.fldsList[0].type
			var validField = /^(fields.fldsList\[)[0-9]+(\].name)$/;
			return validField.test(s);
		}
		function isIndex(s)
		{
			// indexs.indexsList[0].name
			var validField = /^(indexs.indexsList\[)[0-9]+(\].name)$/;
			return validField.test(s);
		}		