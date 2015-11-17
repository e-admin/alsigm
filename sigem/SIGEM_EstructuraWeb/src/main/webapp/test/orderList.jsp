<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html:html locale="true">
<head>
	<title></title>

	<script language="javascript">
		var rowIndex;

		var volumes = new Array();
		volumes['VOL1']=1;
		volumes['VOL2']=3;
		volumes['VOL3']=5;

		var nameColum = 1;
		function getId(name){
			return volumes[name];
		}

		function up()
		{
			var lista = document.getElementById('lista');

			if (rowIndex && rowIndex >1)
			{
				changeRow(lista, rowIndex,rowIndex - 1);
				selectRow(rowIndex - 1);
			}
		}
		function down()
		{
			var table = document.getElementById('lista');
			var tam = table.rows.length;
			if (rowIndex && rowIndex != tam -1)
			{
				changeRow(lista, rowIndex,rowIndex + 1);
				selectRow(rowIndex + 1);
			}
		}
		function changeRow(table,id1, id2){
			var row1 = table.rows[id1];
			numCells = row1.cells.length;
			var array1 = new Array();
			for (i = 0; i < numCells; i++ ){
				array1[i] = row1.cells[i].innerHTML;
			}
			var row2 = table.rows[id2];
			for (i = 0; i < numCells; i++ ){
				row1.cells[i].innerHTML = row2.cells[i].innerHTML;
				row2.cells[i].innerHTML = array1[i];
			}
		}

		function selectRow(index){
			var table = document.getElementById('lista');
			if (rowIndex){
				var rowSelected = table.rows[rowIndex];
				rowSelected.style.backgroundColor = "#ffffff";
			}
			rowIndex = index;
			var rowSelected = table.rows[rowIndex];
			rowSelected.style.backgroundColor = "#ff0000";

		}

		function submit()
		{
			var table = document.getElementById('lista');
			var n = table.rows.length;
			var i = 1;
			var arrayId = "";

			for ( i = 1; i < n; i ++ )
			{
				var name = table.rows[i].cells[nameColum].innerHTML;
				var id = getId(name);
				arrayId += id + "/";
			}
		}
	</script>
</head>
<body>
	<table border style="margin-top: 50px;" align="center" width="30%" id="lista" >
		<tr>
			<td>Orden</td>
			<td>Nombre</td>
		</tr>

		<tr onclick="selectRow(this.rowIndex)">
			<td>1</td>
			<td>VOL1</td>
		</tr>
		<tr onclick="selectRow(this.rowIndex)">
			<td>2</td>
			<td>VOL2</td>
		</tr>
		<tr onclick="selectRow(this.rowIndex)">
			<td>3</td>
			<td>VOL3</td>
		</tr>
	</table>
	<table style="margin-top: 50px;" align="center" width="30%">
		<tr>
		<td><input type="button" value="Subir" onclick="up();"></td>
		<td><input type="button" value="Bajar" onclick="down();"></td>
		<td><input type="button" value="Enviar" onclick="submit();"></td>
		</tr>
	</table>
</body>

</html:html>