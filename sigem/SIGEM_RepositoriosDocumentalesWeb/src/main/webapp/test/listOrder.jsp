<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html:html locale="true">
<head>
	<title></title>
	
	<script language="javascript">
		function up()
		{

			var select = document.getElementById('listas');
			var lengh = select.length;
			var selectedIndex = select.selectedIndex;
			
			if (selectedIndex != 0 && selectedIndex != -1 )
			{
				var optionSelected = select.options[selectedIndex];
				var optionSelectedUp = select.options[selectedIndex-1];
				
				var oldValue = optionSelected.value;
				var oldText = optionSelected.text;
				
				optionSelected.value = optionSelectedUp.value;
				optionSelected.text = optionSelectedUp.text;
				
				optionSelectedUp.value = oldValue;
				optionSelectedUp.text = oldText;
				
				select.selectedIndex = selectedIndex -1;
			}
			// alert (" optionSelected: " + optionSelected.value + ": " + optionSelected.text + "\n " + " optionSelectedUp: " + optionSelectedUp.value + ": " + optionSelectedUp.text + "\n " );
		}
		function down()
		{
			var select = document.getElementById('listas');
			var lengh = select.length;
			var selectedIndex = select.selectedIndex;
			if (selectedIndex != lengh - 1 && selectedIndex != -1 ){
				var optionSelected = select.options[selectedIndex];
				var optionSelectedUp = select.options[selectedIndex+1];
				
				var oldValue = optionSelected.value;
				var oldText = optionSelected.text;
				
				optionSelected.value = optionSelectedUp.value;
				optionSelected.text = optionSelectedUp.text;
				
				optionSelectedUp.value = oldValue;
				optionSelectedUp.text = oldText;
				
				select.selectedIndex = selectedIndex +1;
			}
		}
		
		function add()
		{
			var select1 = document.getElementById('listasTotal');
			var select2 = document.getElementById('listas');
			var lengh = select2.length;
			
			var selectedIndex = select1.selectedIndex; // Lista para añadir
			var optionSelected = select1.options[selectedIndex];

			select2[lengh] = new Option(optionSelected.text, optionSelected.value);
			
			select1.selectedIndex = -1;
			select2.selectedIndex = lengh;
		}
		
		function delete_()
		{
			var select2 = document.getElementById('listas');
			var selectedIndex = select2.selectedIndex; // Lista para eliminar
			select2[selectedIndex] = null;
		}
	</script>
</head>
<body>

<form name="miFormulario" id="miId">
<table border align="center" width="30%" style="margin-top: 50px;">
<tr>
	<td rowspan="2">
	<select id="listasTotal" multiple="multiple" style="width: 150px;">
		<option value="1">Lista 1</option>
		<option value="2">Lista 2</option>
		<option value="3">Lista 3</option>
		<option value="4">Lista 4</option>
	</select>
	</td>
	<td>
		<input type="button" value="&gt;" onclick="add();" />
	</td>
	
	<td rowspan="2">
	<select id="listas" multiple="multiple" style="width: 150px;">
		<option value="2">Lista 2</option>
		<option value="4">Lista 4</option>
	</select>
	<td>
		<input type="button" value="Arriba" onclick="up();"/>
	</td>	
</tr>
<tr>
	<td>
		<input type="button" value="&lt;" onclick="delete_();"/>
	</td>	
	<td>
		<input type="button" value="Abajo" onclick="down();"/>
	</td>	
</tr>
</table>
</form>
</body>
</html:html>