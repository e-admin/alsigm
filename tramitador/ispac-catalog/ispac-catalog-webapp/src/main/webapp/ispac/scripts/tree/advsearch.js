/***************************************************************/
/*******	Búsqueda avanzada		****************************/

function getTableRow(rowIndex)
{
    var row = document.createElement("<tr valign='top'>");
    var td = document.createElement("td");
    var tdContent = document.createElement("<input type='checkbox' name='check' style='border:0' />");
    td.appendChild(tdContent);
    row.appendChild(td);

    td = document.createElement("td");
    tdContent = document.createElement("<select name='booleano'>");
    tdContent.add(new Option("   ", ""));
    for (var i = 0; i < BOOLEANOS.length; i++)
    {
        tdContent.add(new Option(BOOLEANOS[i][1], BOOLEANOS[i][0]));
        if (rowIndex > 1 && BOOLEANOS[i][2])
            tdContent.options.selectedIndex = i+1;
    }
    td.appendChild(tdContent);
    row.appendChild(td);

    td = document.createElement("td");
    tdContent = document.createElement("<select name='abrirpar'>");
    tdContent.add(new Option("   ", ""));
    tdContent.add(new Option("(", "("));
    td.appendChild(tdContent);
    row.appendChild(td);

    td = document.createElement("td");
    tdContent = document.createElement("<select name='campo' onchange='javascript:cambioEnCampo(this)'>");
    tdContent.add(new Option("   ", ""));
    for (var i = 0; i < CAMPOS.length; i++)
    	tdContent.add(new Option(CAMPOS[i][1], CAMPOS[i][0]));
    td.appendChild(tdContent);
    tdContent = document.createElement("<input type='hidden' name='tipoCampo'/>");
    td.appendChild(tdContent);
    row.appendChild(td);

    td = document.createElement("td");
    tdContent = document.createElement("<select name='operador' onchange='javascript:cambioEnOperador(this)'>");
    tdContent.add(new Option("   ", ""));
    td.appendChild(tdContent);
    row.appendChild(td);

    td = document.createElement("td");
	td.innerHTML = "<input type='hidden' name='valor1'/><input type='hidden' name='valor1D'/><input type='hidden' name='valor1M'/><input type='hidden' name='valor1A'/><input type='hidden' name='nombreDesc'/>";
    row.appendChild(td);

    td = document.createElement("td");
	td.innerHTML = "<input type='hidden' name='valor2'/><input type='hidden' name='valor2D'/><input type='hidden' name='valor2M'/><input type='hidden' name='valor2A'/>";
    row.appendChild(td);

    td = document.createElement("td");
    tdContent = document.createElement("<select name='cerrarpar'>");
    tdContent.add(new Option("   ", ""));
    tdContent.add(new Option(")", ")"));
    td.appendChild(tdContent);
    row.appendChild(td);
    
    return row;
}
function insertTableRow()
{
	var tabla = document.getElementById("tblCondiciones");
	var checkboxes = document.getElementsByName("check");
	for (var i = 0; i < checkboxes.length; i++)
	{
        if (checkboxes[i].checked)
        {
		    var tbody = tabla.getElementsByTagName("tbody").item(0);
            tbody.insertBefore(getTableRow(checkboxes[i].parentNode.parentNode.rowIndex), checkboxes[i].parentNode.parentNode);
            break;
        }
    }
}
function addTableRow()
{
	var tabla = document.getElementById("tblCondiciones");
    var tbody = tabla.getElementsByTagName("tbody").item(0);
    tbody.appendChild(getTableRow(tbody.rows.length+1));
}

function removeTableRows()
{
	var tabla = document.getElementById("tblCondiciones");
	var checkboxes = document.getElementsByName("check");
	for (var i = checkboxes.length - 1; i >= 0; i--)
        if (checkboxes[i].checked)
            tabla.deleteRow(checkboxes[i].parentNode.parentNode.rowIndex);
}
function cambioEnCampo(select)
{
	var tabla = document.getElementById("tblCondiciones");
	var tbody = tabla.getElementsByTagName("tbody").item(0);
	var tr = tbody.rows[select.parentNode.parentNode.rowIndex-1];
	var tdOperador = tr.childNodes(4);
	var selectOperador = tdOperador.childNodes(0);
	var inputTipoCampo = select.parentNode.getElementsByTagName("input").item(0);
	removeAllOptions(selectOperador.options);

	var idCampo = select.options[select.selectedIndex].value;
	if (idCampo == "")
	{
		inputTipoCampo.value = "";
		selectOperador.add(new Option("   ", ""));
    	var tdValor1 = tr.childNodes(5);
    	var tdValor2 = tr.childNodes(6);
		tdValor1.innerHTML = "<input type='hidden' name='valor1'/><input type='hidden' name='valor1D'/><input type='hidden' name='valor1M'/><input type='hidden' name='valor1A'/><input type='hidden' name='nombreDesc'/>";
		tdValor2.innerHTML = "<input type='hidden' name='valor2'/><input type='hidden' name='valor2D'/><input type='hidden' name='valor2M'/><input type='hidden' name='valor2A'/>";
	}
	else
	{
		var infoCampo = getInfoCampo(idCampo);
		inputTipoCampo.value = infoCampo[2];
		
		if (infoCampo[2] == 1) //Enlace
		{
			selectOperador.add(new Option(OPERADORES[0][1], OPERADORES[0][0]));
		}
		else if (infoCampo[2] == 2) //Fecha
		{
			selectOperador.add(new Option(OPERADORES[0][1], OPERADORES[0][0]));
			selectOperador.add(new Option(OPERADORES[1][1], OPERADORES[1][0]));
			selectOperador.add(new Option(OPERADORES[2][1], OPERADORES[2][0]));
			selectOperador.add(new Option(OPERADORES[3][1], OPERADORES[3][0]));
			selectOperador.add(new Option(OPERADORES[4][1], OPERADORES[4][0]));
			selectOperador.add(new Option(OPERADORES[5][1], OPERADORES[5][0]));
			selectOperador.add(new Option(OPERADORES[8][1], OPERADORES[8][0]));
			selectOperador.add(new Option(OPERADORES[9][1], OPERADORES[9][0]));
		}
		else if (infoCampo[2] == 3) //Número
		{
			selectOperador.add(new Option(OPERADORES[0][1], OPERADORES[0][0]));
			selectOperador.add(new Option(OPERADORES[1][1], OPERADORES[1][0]));
			selectOperador.add(new Option(OPERADORES[2][1], OPERADORES[2][0]));
			selectOperador.add(new Option(OPERADORES[3][1], OPERADORES[3][0]));
			selectOperador.add(new Option(OPERADORES[4][1], OPERADORES[4][0]));
			selectOperador.add(new Option(OPERADORES[5][1], OPERADORES[5][0]));
		}
		else if (infoCampo[2] == 4) //Texto largo
		{
			selectOperador.add(new Option(OPERADORES[6][1], OPERADORES[6][0]));
		}
		else if (infoCampo[2] == 6) //Texto corto
		{
			selectOperador.add(new Option(OPERADORES[0][1], OPERADORES[0][0]));
			selectOperador.add(new Option(OPERADORES[1][1], OPERADORES[1][0]));
			selectOperador.add(new Option(OPERADORES[2][1], OPERADORES[2][0]));
			selectOperador.add(new Option(OPERADORES[3][1], OPERADORES[3][0]));
			selectOperador.add(new Option(OPERADORES[4][1], OPERADORES[4][0]));
			selectOperador.add(new Option(OPERADORES[7][1], OPERADORES[7][0]));
			selectOperador.add(new Option(OPERADORES[6][1], OPERADORES[6][0]));
		}

		cambioEnOperador(selectOperador);
	}
}
function cambioEnOperador(select)
{
	var operador = select.options[select.selectedIndex].value;
	var tabla = document.getElementById("tblCondiciones");
	var tbody = tabla.getElementsByTagName("tbody").item(0);
	var tr = tbody.rows[select.parentNode.parentNode.rowIndex-1];
	var tdCampo = tr.childNodes(3);
	var selectCampo = tdCampo.childNodes(0);
	var idCampo = selectCampo.options[selectCampo.selectedIndex].value;
	var infoCampo = getInfoCampo(idCampo);
	var tdValor1 = tr.childNodes(5);
	var tdValor2 = tr.childNodes(6);
	
	var contentValor1 = "";
	var contentValor2 = "";
	var idRelacionesDesc = "";
	
	
	if (operador == "QC" || operador == "QCC")
	{
		contentValor1 = "<input type='text' name='valor1'/><input type='hidden' name='valor1D'/><input type='hidden' name='valor1M'/><input type='hidden' name='valor1A'/><input type='hidden' name='nombreDesc'/>";
		contentValor2 = "<input type='hidden' name='valor2'/><input type='hidden' name='valor2D'/><input type='hidden' name='valor2M'/><input type='hidden' name='valor2A'/>";
	}
	else if (operador == "=" || operador == "<" || operador == "<="
		|| operador == ">" || operador == ">=")
	{
		if (infoCampo[2] == 1)
		{
		    tdValor1.style.width = "200px";
		    
	        idRelacionesDesc = "" + new Date().getTime();
		    contentValor1 = "<input type='text' name='nombreDesc' class='inputRO90' readonly='true'/>"
		        + "<input type='hidden' name='valor1'/><input type='hidden' name='valor1D'/><input type='hidden' name='valor1M'/><input type='hidden' name='valor1A'/>"
		        + "<a href='javascript:";
		    
		    if (idCampo == -1)
		        contentValor1 = contentValor1 + "searchTesauro";
		    else //if (idCampo == -1)
		        contentValor1 = contentValor1 + "searchDescriptor";
		        
		    contentValor1 = contentValor1 + "(" 
		        + select.parentNode.parentNode.rowIndex
		        + ")'><img src='../../pages/images/pixel.gif' width='2px' class='imgTextMiddle'/>"
		        + "<img src='../../pages/images/expand.gif' class='imgTextMiddle'/></a>";

		    if (idCampo == -1 && RELACIONES_TESAURO && RELACIONES_TESAURO[1] && RELACIONES_TESAURO[1].length > 0)
		    {
    		    contentValor1 = contentValor1 + "<br/>" + RELACIONES_TESAURO[0] + ":&nbsp;<br/>";
                contentValor1 = contentValor1 + "<table class='multiSeleccion'>";
    		    for (var i = 0; i < RELACIONES_TESAURO[1].length; i++)
    		    {
    		        if (i % 3 == 0)
    		            contentValor1 = contentValor1 + "<tr>";

                    contentValor1 = contentValor1 + "<td class='data'>";
                    contentValor1 = contentValor1 + "<input type='checkbox' name='relacionesDesc("
                        + idRelacionesDesc
                        + ")' value='"
                        + RELACIONES_TESAURO[1][i][0] + "' style='border:0'/>";
                    contentValor1 = contentValor1 + RELACIONES_TESAURO[1][i][1] + "</td>";
                    
    		        if (i % 3 == 2)
    		            contentValor1 = contentValor1 + "</tr>";
    		    }
    		    contentValor1 = contentValor1 + "</table>";
		    }
		}
		else if (infoCampo[2] == 3 || infoCampo[2] == 6)
			contentValor1 = "<input type='text' name='valor1'/><input type='hidden' name='valor1D'/><input type='hidden' name='valor1M'/><input type='hidden' name='valor1A'/><input type='hidden' name='nombreDesc'/>";
		else
			contentValor1 = "<input type='hidden' name='valor1'/><input type='text' name='valor1D' size='2' maxlength='2' title='dd'/>-<input type='text' name='valor1M' size='2' maxlength='2' title='mm'/>-<input type='text' name='valor1A' size='4' maxlength='4' title='aaaa'/><input type='hidden' name='nombreDesc'/>";
        
		contentValor2 = "<input type='hidden' name='valor2'/><input type='hidden' name='valor2D'/><input type='hidden' name='valor2M'/><input type='hidden' name='valor2A'/>";
	}
	else if (operador == "[..]")
	{
		if (infoCampo[2] == 3 || infoCampo[2] == 6)
		{
			contentValor1 = "<input type='text' name='valor1'/><input type='hidden' name='valor1D'/><input type='hidden' name='valor1M'/><input type='hidden' name='valor1A'/><input type='hidden' name='nombreDesc'/>";
			contentValor2 = "<input type='text' name='valor2'/><input type='hidden' name='valor2D'/><input type='hidden' name='valor2M'/><input type='hidden' name='valor2A'/>";
		}
		else
		{
			contentValor1 = "<input type='hidden' name='valor1'/><input type='text' name='valor1D' size='2' maxlength='2' title='dd'/>-<input type='text' name='valor1M' size='2' maxlength='2' title='mm'/>-<input type='text' name='valor1A' size='4' maxlength='4' title='aaaa'/><input type='hidden' name='nombreDesc'/>";
			contentValor2 = "<input type='hidden' name='valor2'/><input type='text' name='valor2D' size='2' maxlength='2' title='dd'/>-<input type='text' name='valor2M' size='2' maxlength='2' title='mm'/>-<input type='text' name='valor2A' size='4' maxlength='4' title='aaaa'/>";
		}	
	}
	else if (operador == "QCN" || operador == "EX")
	{
		contentValor1 = "<input type='hidden' name='valor1'/><input type='text' name='valor1D' size='2' maxlength='2' title='dd'/>-<input type='text' name='valor1M' size='2' maxlength='2' title='mm'/>-<input type='text' name='valor1A' size='4' maxlength='4' title='aaaa'/><input type='hidden' name='nombreDesc'/>";
		contentValor2 = "<input type='hidden' name='valor2'/><input type='text' name='valor2D' size='2' maxlength='2' title='dd'/>-<input type='text' name='valor2M' size='2' maxlength='2' title='mm'/>-<input type='text' name='valor2A' size='4' maxlength='4' title='aaaa'/>";
	}
	else
	{
		contentValor1 = "<input type='hidden' name='valor1'/><input type='hidden' name='valor1D'/><input type='hidden' name='valor1M'/><input type='hidden' name='valor1A'/><input type='hidden' name='nombreDesc'/>";
		contentValor2 = "<input type='hidden' name='valor2'/><input type='hidden' name='valor2D'/><input type='hidden' name='valor2M'/><input type='hidden' name='valor2A'/>";
	}

    contentValor1 = contentValor1 + "<input type='hidden' name='idRelacionesDesc' value='" + idRelacionesDesc + "'/>";

	tdValor1.innerHTML = contentValor1;
	tdValor2.innerHTML = contentValor2;
}
function getInfoCampo(idCampo)
{
	for (var i = 0; i < CAMPOS.length; i++)
		if (CAMPOS[i][0] == idCampo)
			return CAMPOS[i];
}
